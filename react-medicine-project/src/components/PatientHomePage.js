import React, { Component } from 'react';
import { Fragment } from 'react';

import { request, getAuthToken, setAuthHeader, setEmail, setRole, getEmail, getRole } from '../helpers/axios-helper';
import { jwtDecode } from "jwt-decode";
import moment from 'moment';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import axios from 'axios';

class PatientHomePage extends Component {
    constructor(props) {
        super(props);
        this.loadDoctors = this.loadPageableDoctors.bind(this);
        this.updateLinks = this.updateLinks.bind(this);
        this.onNextPageSelected = this.onNextPageSelected.bind(this);
        this.onPrevPageSelected = this.onPrevPageSelected.bind(this);


        this.state = {
            patientData: null,
            isEditing: false,
            editedData: {},
            patientAppointments: [],
            showConsultations: false,
            consultationsData: [],
            allAppointments: [],
            newAppointment: {
                cnp: "", // Va fi completat din patientData și va fi disabled în form
                appointmentTime: "",
                idDoctor: null, // Va fi selectat din dropdown
                status: "PROCESSING",
            },
            alldoctors: [],

            doctors: [],
            totalPages: 0,
            currentPage: 0,
            itemsPerPage: 8,

            prevPage: null,
            nextPage: null,

        };
    }
    /* ====================================================== TOKEN ====================================================== */
    isTokenValid = () => {
        const token = getAuthToken();

        if (!token) {
            console.log("Token missing.");
            alert("Token missing.");
            console.log("Token expired");
            this.props.handleTokenExpiration();
            return false;
        }

        try {
            const decodedToken = jwtDecode(token);
            const currentTime = Date.now();

            console.log("Decoded Token:", decodedToken);

            if (decodedToken.exp * 1000 < currentTime) {
                // Token expired
                alert("Token has expired. Please log in again.");
                console.log("Token expired");
                this.props.handleTokenExpiration();
                return false;
            } else {
                // Token still valid
                console.log("Token is still valid " + decodedToken.role);
                return true;
            }
        } catch (error) {
            // Handle errors decoding the token
            console.error("Error decoding token:", error);
            localStorage.removeItem('access_token');
            localStorage.removeItem('email');
            localStorage.removeItem('role');

            this.setState({ componentToShow: "welcome", roleForHomePage: "" });
            return false;
        }
    };

    async componentDidMount() {
        if (!this.isTokenValid()) {
            return;
        }

        console.log("Before loadPatientData");

        try {
            await this.loadPatientData();
            console.log("After loadPatientData");
            await this.loadPageableDoctors()
            await this.loadAllDoctors();
        } catch (error) {
            console.error("Error loading patient data:", error);
        }
    }

    async componentDidUpdate(prevProps, prevState) {

        if (prevState.patientData !== this.state.patientData) {
            console.log("Before loadPatientAppointments");
            await this.loadPatientAppointments();
            console.log("After loadPatientAppointments");

        }
        if (prevState.patientAppointments !== this.state.patientAppointments) {
            await this.loadConsultationsForAppointments(this.state.patientAppointments);
            console.log("After loadConsultationsForAppointments");

            console.log("Before loadAllAppointments");
            await this.loadAllAppointments();
            console.log("After loadAllAppointments");

            console.log("Before loadDoctors");
            //await this.loadDoctors();
            if (prevState.currentPage !== this.state.currentPage) {
                this.loadPageableDoctors()
            }

            console.log("After loadDoctors");
        }


    }


    /* ====================================================== PATIENT DATA ====================================================== */

    async loadPatientData() {
        console.log("Inside loadPatientData");

        if (!this.isTokenValid()) {
            return;
        }

        const token = getAuthToken();
        const email = getEmail();

        if (token && email) {
            console.log("Fetching patient data...");

            try {
                const response = await request("GET", "/patients?email=" + email, {}, { Authorization: `Bearer ${token}` });

                console.log("Response from the server:", response);

                if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                    const patientsList = response.data._embedded.linkedHashMapList;
                    if (patientsList.length > 0) {
                        const patient = patientsList[0];
                        console.log("Patient data loaded successfully:", patient);
                        this.setState({ patientData: patient });

                    } else {
                        console.log("No patients found.");
                        this.setState({ patientData: null });
                    }
                } else {
                    console.error("Error loading patient:", response.data);
                    alert("Error loading patient: " + response.data);
                }
            } catch (error) {
                console.error("Error fetching patient data:", error);
                alert("Error fetching patient data: " + error.message);
                throw error;
            }
        } else {
            console.error("Token or email missing. Cannot fetch patient data.");
            alert("Token or email missing. Cannot fetch patient data.");
        }
    }

    handleEditClick() {
        this.setState({
            isEditing: true,
            editedData: { ...this.state.patientData },
        });
    }


    handleSaveClick() {
        if (!this.isTokenValid()) {
            return;
        }
        const { cnp } = this.state.patientData;


        request("PUT", `/patients/edit/${cnp}`, this.state.editedData)
            .then((response) => {
                this.setState({
                    isEditing: false,
                });
                this.loadPatientData();
            })
            .catch((error) => {
                console.error("Error saving patient data:", error);
                if (error.response) {
                    const errorMessage = error.response.data;
                    alert("Error saving patient data: " + errorMessage);
                } else if (error.request) {
                    console.error("No response received from the server");
                    alert("No response received from the server");

                } else {
                    console.error("Error setting up the request:", error.message);
                    alert("Error setting up the request:", error.message);

                }
            });
    }

    handleCancelClick() {
        this.setState({
            isEditing: false,
        });
    }

    handleInputChange(e) {
        const { name, value } = e.target;
        this.setState((prevState) => ({
            editedData: { ...prevState.editedData, [name]: value },
        }));
    }



    /* ====================================================== ALL + PATIENT;S APPOINTMENTS ====================================================== */

    async loadAllAppointments() {
        console.log("Loading all appointments...");

        const token = getAuthToken();

        if (!token) {
            console.error("Token missing.");
            return;
        }

        try {
            const response = await request("GET", "/appointments", {}, { Authorization: `Bearer ${token}` });

            if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                const appointmentsList = response.data._embedded.linkedHashMapList;

                console.log("All appointments loaded successfully:", appointmentsList);
                this.setState({ allAppointments: appointmentsList });

            } else if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                const errorMessage = response.data._embedded.errorsTableList;
                console.error("Server error:", errorMessage);
                alert("Server error: " + errorMessage);
            } else {
                console.error("Unexpected response from the server:", response);
                alert("Unexpected response from the server");
            }
        } catch (error) {
            console.error("Error fetching all appointments:", error);
            if (error.response.status !== 404) {
                alert("Error fetching all appointments: " + error.message);
            }
            this.setState({ allAppointments: null });
        }
    }



    async loadPatientAppointments() {
        console.log("AICI loadPatientAppointments");
        const { patientData } = this.state;
        console.log("petientdata : " + patientData);

        if (!patientData) {
            console.error("Patient data not available.");
            return;
        }

        const token = getAuthToken();
        const cnp = patientData.cnp;

        if (token && cnp) {
            console.log("Fetching patient appointments...");
            try {
                const response = await request("GET", `/appointments/patient/${cnp}`);
                console.log("Response from the server programari:", response);

                if (
                    response &&
                    response.data &&
                    response.data._embedded &&
                    response.data._embedded.appointmentsTableList !== undefined
                ) {
                    const appointmentsList = response.data._embedded.appointmentsTableList;

                    if (appointmentsList === null) {
                        this.setState({ patientAppointments: null });
                    } else {
                        this.setState({ patientAppointments: appointmentsList });
                    }
                } else if (
                    response &&
                    response.data &&
                    response.data._links &&
                    response.data._links.parent
                ) {
                    // Handle the case when appointmentsTableList is not present
                    console.log("No appointments for the patient.");
                    this.setState({ patientAppointments: null });
                } else if (response && response.data && response.data._embedded && response.data._embedded.errorsTableList) {
                    const errorMessage = response.data._embedded.errorsTableList;
                    console.error("Server error:", errorMessage);
                    alert("Server error: " + errorMessage);
                } else {
                    // Handle other unexpected scenarios, but not as an error
                    console.log("Unexpected response from the server:", response.status, response.statusText);
                }
            } catch (error) {
                console.error("Error fetching patient appointments:", error);
                if (error.response && error.response.status !== 404) {
                    alert("Error fetching patient appointments: " + error.response.status);
                }
                this.setState({ patientAppointments: null });
            }


        } else {
            console.error("Token or CNP missing. Cannot fetch patient appointments.");
            alert("Token or CNP missing. Cannot fetch patient appointments.");
        }
    }


    handleNewAppointmentChange(e) {
        const { name, value } = e.target;
        this.setState((prevState) => ({
            newAppointment: { ...prevState.newAppointment, [name]: value },
        }));
    }

    async handleCreateAppointment() {
        if (!this.isTokenValid()) {
            return;
        }

        const { cnp } = this.state.patientData;
        const formattedAppointmentTime = moment(this.state.newAppointment.appointmentTime).format("YYYY-MM-DD HH:mm");


        try {
            const response = await request("PUT", `/appointments/patient/${cnp}/doctor/${this.state.newAppointment.idDoctor}`, {
                appointmentsTablePK: {
                    cnp,
                    idDoctor: this.state.newAppointment.idDoctor,
                    appointmentTime: formattedAppointmentTime,
                },
                status: this.state.newAppointment.status,
            }, {
                Authorization: `Bearer ${getAuthToken()}`,
            });

            console.log("Appointment created successfully:", response);

            await this.loadPatientAppointments();
            await this.loadAllAppointments();
        } catch (error) {
            const errorMessage = error.response.data;
            alert("Error saving patient data: " + errorMessage);

        }
    }


    /* ====================================================== PATIENT'S CONSULTATIONS ====================================================== */

    async loadConsultationsForAppointments(appointmentsList) {
        console.log("loadConsultationsForAppointments ()");
        console.log("appointmentsList:", appointmentsList);
        const consultationsList = [];
        if (appointmentsList === null) {
            this.setState({ consultationsData: null });
            console.log("consultationsData cu  appointmentsList gol = > " + this.state.consultationsData)
        }
        else {
            for (const appointment of appointmentsList) {


                console.log("loadConsultationsForAppointments AICI : " + appointment);
                const { cnp, idDoctor, appointmentTime } = appointment.appointmentsTablePK;
                const formattedAppointmentTime = moment(appointmentTime).format("YYYY-MM-DD HH:mm");

                try {
                    const consultationsResponse = await request("GET", `/consultations?idPatient=${cnp}&idDoctor=${idDoctor}&appointmentTime=${formattedAppointmentTime}`);
                    if (consultationsResponse && consultationsResponse.data && consultationsResponse.data._embedded && consultationsResponse.data._embedded.linkedHashMapList) {
                        consultationsList.push({
                            appointmentPK: appointment.appointmentsTablePK,
                            consultations: consultationsResponse.data._embedded.linkedHashMapList,
                        });
                    }

                    console.log("AFISEZ CONSULTATIONS: " + consultationsList.consultations);
                } catch (error) {
                    console.error("Error fetching consultations for appointment:", error);
                    alert("Error fetching consultations for appointment:" + error.message);
                    this.setState({ consultationsData: null });
                }
            }

            this.setState({ consultationsData: consultationsList });
            console.log("consultationsData = > " + this.state.consultationsData)
        }

    }


    /* ====================================================== INFO DOCTORS ====================================================== */

    async loadAllDoctors() {

        const token = getAuthToken();

        if (!token) {
            console.error("Token missing.");
            return;
        }

        try {
            const response = await request("GET", "/doctors", {}, { Authorization: `Bearer ${token}` });

            if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                const doctorsList = response.data._embedded.linkedHashMapList;

                console.log("All appointments loaded successfully:", doctorsList);

                this.setState({ alldoctors: doctorsList });
            } else if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                const errorMessage = response.data._embedded.errorsTableList;
                console.error("Server error:", errorMessage);
                alert("Server error: " + errorMessage);
            } else {
                console.error("Unexpected response from the server:", response);
                alert("Unexpected response from the server");
            }
        } catch (error) {
            console.error("Error fetching all appointments:", error);
            if (error.response.status !== 404) {
                alert("Error fetching all appointments: " + error.message);
            }
            this.setState({ doctors: null });
        }
    }


    updateLinks(links) {


        if (links.prev) {
            console.log("AM updateLinks() prev => " + links.prev.href);
            this.setState({
                prevPage: links.prev.href
            });
        }
        else {
            console.log("NU AM updateLinks()prev  ");
            this.setState({
                prevPage: null
            });
        }

        if (links.next) {
            console.log("AM updateLinks()next => " + links.next.href);

            this.setState({
                nextPage: links.next.href
            });
        }
        else {
            console.log("NU AM updateLinks() next");

            this.setState({
                nextPage: null
            });
        }

    }

    async loadPageableDoctors() {
        const { currentPage, itemsPerPage } = this.state;
        const token = getAuthToken();
        console.log("loadDoctors() -> loadDoctors  items : " + currentPage + " " + itemsPerPage);

        if (!token) {
            console.error("Token missing.");
            return;
        }
        try {
            const response = await request(
                "GET",
                `/doctors?page=${currentPage}&&itemsPerPage=${itemsPerPage}`,
                {},
                { Authorization: `Bearer ${token}` }
            );

            if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                const doctorsList = response.data._embedded.linkedHashMapList;
                this.setState({ doctors: doctorsList });

                if (response.data._links) {
                    const links = response.data._links;
                    this.updateLinks(links);
                }
            } else if (response && response.data && response.data._embedded && response.data._embedded.errorsTableList) {
                const errorMessage = response.data._embedded.errorsTableList;
                console.error("Server error:", errorMessage);
                alert("Server error: " + errorMessage);
            } else {
                console.error("Unexpected response from the server:", response);
                alert("Unexpected response from the server");
            }
        } catch (error) {
            console.error("Error fetching doctors:", error);
            if (error.response && error.response.status !== 404) {
                alert("Error fetching doctors: " + error.message);
            }
            this.setState({ doctors: null });
        }
    }


    onPrevPageSelected(e) {
        const prevPage = this.state.prevPage;
        this.setPageProperties(prevPage);
    }

    onNextPageSelected(e) {
        const nextPage = this.state.nextPage;
        this.setPageProperties(nextPage);
    }

    setPageProperties(page) {
        const pageValue = new URLSearchParams(new URL(page).search).get("page");
        const pageSize = new URLSearchParams(new URL(page).search).get("itemsPerPage");

        this.setState({
            currentPage: parseInt(pageValue),
            itemsPerPage: parseInt(pageSize),
        }, () => {
            this.loadPageableDoctors();
        });
    }







    render() {
        const { patientData, isEditing, editedData, patientAppointments, consultationsData, allAppointments, doctors, alldoctors, newAppointment, totalPages, currentPage, itemsPerPage, nextPage, prevPage } = this.state;


        return (
            <div className="container mt-5">
                {/* EDIT PATIENT */}
                <div className="card p-4 shadow">
                    <h1 className="mb-4">Patient Details</h1>

                    {patientData === null ? (
                        <p>Loading...</p>
                    ) : (
                        <div className="row">
                            <div className="col-md-6">
                                <div className="mb-3">
                                    <label className="form-label">
                                        CNP:
                                        <input type="text" className="form-control" name="cnp" value={patientData.cnp} disabled />
                                    </label>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        User ID:
                                        <input type="text" className="form-control" name="idUser" value={patientData.idUser} disabled />
                                    </label>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        First Name:
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="firstname"
                                            value={isEditing ? editedData.firstname : patientData.firstname}
                                            onChange={(e) => this.handleInputChange(e)}
                                            disabled={!isEditing}
                                        />
                                    </label>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        Last Name:
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="lastname"
                                            value={isEditing ? editedData.lastname : patientData.lastname}
                                            onChange={(e) => this.handleInputChange(e)}
                                            disabled={!isEditing}
                                        />
                                    </label>
                                </div>
                            </div>

                            <div className="col-md-6">
                                <div className="mb-3">
                                    <label className="form-label">
                                        Phone:
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="phone"
                                            value={isEditing ? editedData.phone : patientData.phone}
                                            onChange={(e) => this.handleInputChange(e)}
                                            disabled={!isEditing}
                                        />
                                    </label>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        Birthdate:
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="birthdate"
                                            value={isEditing ? editedData.birthdate : patientData.birthdate}
                                            onChange={(e) => this.handleInputChange(e)}
                                            disabled={!isEditing}
                                        />
                                    </label>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        Email:
                                        <input type="text" className="form-control" name="email" value={patientData.email} disabled />
                                    </label>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        Is Active:
                                        <input type="text" className="form-control" name="isactive" value={patientData.isactive ? 'Yes' : 'No'} disabled />
                                    </label>
                                </div>
                            </div>
                        </div>
                    )}

                    {isEditing ? (
                        <div className="mb-3">
                            <button className="btn btn-primary" onClick={() => this.handleSaveClick()}>Save</button>
                            <button className="btn btn-secondary ms-2" onClick={() => this.handleCancelClick()}>Cancel</button>
                        </div>
                    ) : (
                        <button className="btn btn-primary" onClick={() => this.handleEditClick()}>Edit</button>
                    )}
                </div>
                {/* STOP EDIT PATIENT */}

                {/* START DOCTORS SECTION */}


                <div className="card p-4 shadow" style={{ height: "400px", overflowY: "auto" }}>
                    <h1 className="mb-4">Available Doctors</h1>

                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                                <tr>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Email</th>
                                    <th>Specialization</th>
                                </tr>
                            </thead>
                            <tbody>
                                {doctors ? (
                                    doctors.map((doctor) => (
                                        <tr key={doctor.idDoctor}>
                                            <td>{doctor.firstname}</td>
                                            <td>{doctor.lastname}</td>
                                            <td>{doctor.email}</td>
                                            <td>{doctor.specialization}</td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan="3">Loading doctors...</td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>

                    <div className="d-flex justify-content-center mt-4">
                        <nav aria-label="Page navigation">
                            <Button label="Prev" className="p-button-link" disabled={!prevPage} onClick={this.onPrevPageSelected} />
                            <Button className="p-button-rounded p-button-info p-button-outlined">{parseInt(currentPage)}</Button>
                            <Button label="Next" className="p-button-link" disabled={!nextPage} onClick={this.onNextPageSelected} />
                        </nav>
                    </div>
                </div>

                {/* STOP DOCTORS SECTION */}

                {/* START CREARE PROGRAMARE */}
                <div className="card p-4 shadow">
                    <h1 className="mb-4">Create new appointment</h1>

                    {patientData && doctors ? (
                        <form>
                            <div className="mb-3">
                                <label className="form-label">
                                    CNP:
                                    <input type="text" className="form-control" name="cnp" value={patientData.cnp} disabled />
                                </label>
                            </div>
                            <div className="mb-3">
                                <label className="form-label">
                                    Appointment Time:
                                    <input
                                        type="datetime-local"
                                        className="form-control"
                                        name="appointmentTime"
                                        value={newAppointment.appointmentTime}
                                        onChange={(e) => this.handleNewAppointmentChange(e)}
                                    />
                                </label>
                            </div>
                            <div className="mb-3">
                                <label className="form-label">
                                    Doctor:
                                    <select
                                        className="form-select"
                                        name="idDoctor"
                                        value={newAppointment.idDoctor}
                                        onChange={(e) => this.handleNewAppointmentChange(e)}
                                    >
                                        <option value="" disabled>Selectează doctorul</option>
                                        {alldoctors.map((doctor) => (
                                            <option key={doctor.idDoctor} value={doctor.idDoctor}>
                                                {doctor.idDoctor}  {doctor.firstname} {doctor.lastname} - {doctor.specialization}
                                            </option>
                                        ))}
                                    </select>
                                </label>
                            </div>
                            <div className="mb-3">
                                <button type="button" className="btn btn-primary" onClick={() => this.handleCreateAppointment()}>Creează Programare</button>
                            </div>
                        </form>
                    ) : (
                        <p>Loading...</p>
                    )}
                </div>
                {/* STOP CREARE PROGRAMARE */}


                {/* START MEDICAL HISTORY */}
                <div className="card p-4 shadow">
                    <h1 className="mb-4">Patient Appointments</h1>

                    {patientAppointments ? (
                        <ul>
                            {patientAppointments.map((appointment, index) => (
                                <li key={index}>
                                    <p>CNP: {appointment.appointmentsTablePK.cnp}</p>
                                    <p>Doctor ID: {appointment.appointmentsTablePK.idDoctor}</p>
                                    <p>Appointment Time: {appointment.appointmentsTablePK.appointmentTime}</p>
                                    <p>Status: {appointment.status}</p>

                                    {/* Check if consultationsData exists and has data */}
                                    {consultationsData && consultationsData.length > 0 ? (
                                        consultationsData.map((consultation, index) => {
                                            if (
                                                consultation.appointmentPK.cnp === appointment.appointmentsTablePK.cnp &&
                                                consultation.appointmentPK.idDoctor === appointment.appointmentsTablePK.idDoctor &&
                                                consultation.appointmentPK.appointmentTime === appointment.appointmentsTablePK.appointmentTime
                                            ) {
                                                return (
                                                    <div key={index}>
                                                        <p>Consultations:</p>
                                                        {consultation.consultations && consultation.consultations.length > 0 ? (
                                                            <ul>
                                                                {consultation.consultations.map((consult, index) => (
                                                                    <li key={index}>
                                                                        {/* Display consultation details */}
                                                                        <p>Diagnostic: {consult.diagnostic}</p>
                                                                        {/* Display investigations details */}
                                                                        {consult.investigations && consult.investigations.length > 0 ? (
                                                                            <ul>
                                                                                {consult.investigations.map((investigation, index) => (
                                                                                    <li key={index}>
                                                                                        <p>Investigation: {investigation.naming}</p>
                                                                                        <p>Processing Hours: {investigation.processingHours}</p>
                                                                                        <p>Result: {investigation.result}</p>
                                                                                    </li>
                                                                                ))}
                                                                            </ul>
                                                                        ) : (
                                                                            <p>No investigations found for this consultation.</p>
                                                                        )}
                                                                    </li>
                                                                ))}
                                                            </ul>
                                                        ) : (
                                                            <p>No consultations found for this appointment.</p>
                                                        )}
                                                    </div>
                                                );
                                            }
                                            return null;
                                        })
                                    ) : (
                                        <p>No consultations found.</p>
                                    )}




                                    <hr />
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No appointments found.</p>
                    )}
                </div>



                {/* STOP MEDICAL HISTORY */}


                {/* START ALL APPOINTMENTS */}

                <div className="card p-4 shadow" style={{ maxHeight: "400px", overflowY: "auto" }}>
                    <h1 className="mb-4">All Appointments</h1>

                    {allAppointments ? (
                        <ul>
                            {allAppointments.map((appointment, index) => (
                                <li key={index}>
                                    {/* Render appointment details here */}
                                    <p>CNP: {appointment.appointmentsTablePK.cnp}</p>
                                    <p>Doctor ID: {appointment.appointmentsTablePK.idDoctor}</p>
                                    <p>Appointment Time: {appointment.appointmentsTablePK.appointmentTime}</p>
                                    <p>Status: {appointment.status}</p>
                                    <hr />
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No appointments found.</p>
                    )}
                </div>

                {/* STOP ALL APPOINTMENTS */}








            </div>
        );
    }


}

export default PatientHomePage;
