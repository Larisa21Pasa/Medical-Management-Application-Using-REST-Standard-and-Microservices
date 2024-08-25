import React, { Component } from 'react';
import { Fragment } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form, FormGroup, Label, Input } from 'reactstrap'; // Folosim componente din Reactstrap

import { request, getAuthToken, setAuthHeader, setEmail, setRole, getEmail, getRole } from '../helpers/axios-helper';
import { jwtDecode } from "jwt-decode";
import moment from 'moment';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import axios from 'axios';

export default class DoctorHomePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            doctorData: null,
            allAppointments: [],
            allConsultations: [],
            modalOpen: false,

            investigation: {
                naming: "",
                processingHours: "",
                result: "",
            },
            selectedConsultationId: null,
            newConsultation: {
                idPatient: "",
                idDoctor: "",
                appointmentTime: "",
                diagnostic: "",
                investigations: [],

            },

        };
    }

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


        try {
            await this.loadDoctorData();
            console.log("Before loadAllAppointments");
            await this.loadAllAppointments();

            console.log("After loadAllAppointments");
        } catch (error) {
            console.error("Error loading doctor data:", error);

        }
    }

    async componentDidUpdate(prevProps, prevState) {

        if (prevState.allAppointments !== this.state.allAppointments) {
            console.log("--------loadAllConsultations()")
            await this.loadAllConsultations();

        }
    }
    /* ====================================================== DOCTOR DATA ====================================================== */

    async loadDoctorData() {
        console.log("Inside loadPatientData");

        if (!this.isTokenValid()) {
            return;
        }

        const token = getAuthToken();
        const email = getEmail();

        if (token && email) {
            console.log("Fetching doctor data...");

            try {
                const response = await request("GET", "/doctors?email=" + email, {}, { Authorization: `Bearer ${token}` });

                console.log("Response from the server:", response);

                if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                    const patientsList = response.data._embedded.linkedHashMapList;
                    if (patientsList.length > 0) {
                        const patient = patientsList[0];
                        console.log("Doctor data loaded successfully:", patient);
                        this.setState({ doctorData: patient });

                    } else {
                        console.log("No Doctor found.");
                        this.setState({ doctorData: null });
                    }
                } else {
                    console.error("Error loading Doctor:", response.data);
                    alert("Error loading Doctor: " + response.data);
                }
            } catch (error) {
                console.error("Error fetching Doctor data:", error);
                alert("Error fetching Doctor data: " + error.message);
                throw error;
            }
        } else {
            console.error("Token or email missing. Cannot fetch Doctor data.");
            alert("Token or email missing. Cannot fetch Doctor data.");
        }
    }


    /* ====================================================== ALL APPOINTMENTS ====================================================== */

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


    /* ====================================================== ALL CONSULTATIONS ====================================================== */

    async loadAllConsultations() {
        console.log("Loading all consultations...");
        if (!this.isTokenValid()) {
            return;
        }

        const token = getAuthToken();


        try {
            const response = await request("GET", "/consultations", {}, { Authorization: `Bearer ${token}` });

            if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                const consultationsList = response.data._embedded.linkedHashMapList;

                console.log("All consultations loaded successfully:", consultationsList);
                this.setState({ allConsultations: consultationsList });

            } else if (response && response.data && response.data._embedded && response.data._embedded.linkedHashMapList) {
                const errorMessage = response.data._embedded.errorsTableList;
                console.error("Server error:", errorMessage);
                alert("Server error: " + errorMessage);
            } else {
                console.error("Unexpected response from the server:", response);
                alert("Unexpected response from the server");
            }
        } catch (error) {
            console.error("Error fetching all consultations:", error);
            if (error.response.status !== 404) {
                alert("Error fetching all consultations: " + error.message);
            }
            this.setState({ allConsultations: null });
        }
    }



    toggleModal = (consultationId) => {
        this.setState(prevState => ({
            modalOpen: !prevState.modalOpen,
            selectedConsultationId: consultationId
        }));
    }

    handleChange = (e) => {
        const { name, value } = e.target;
        this.setState((prevState) => ({
            investigation: { ...prevState.investigation, [name]: value },
        }));
    }


    async handleSubmit() {
        if (!this.isTokenValid()) {
            return;
        }

        const { selectedConsultationId, investigation } = this.state;

        try {
            const response = await request("POST", `/consultation/${selectedConsultationId}/investigation`,
                {
                    investigations: [
                        {
                            naming: investigation.naming,
                            processingHours: investigation.processingHours,
                            result: investigation.result

                        }
                    ],
                    _id: selectedConsultationId,
                }, {
                Authorization: `Bearer ${getAuthToken()}`,
            });

            console.log("Inevstigation created successfully:", response);

            this.toggleModal(null);
            await this.loadAllConsultations();
        } catch (error) {
            console.error("Error creating investigation data:", error);
            if (error.response) {
                const errorMessage = error.response.data;
                alert("Error saving investigation data: " + errorMessage);
            } else if (error.request) {
                console.error("No response received from the server");
                alert("No response received from the server");

            } else {
                console.error("Error setting up the request:", error.message);
                alert("Error setting up the request:", error.message);

            }

        }
    }








    render() {

        const { doctorData, allAppointments, allConsultations, modalOpen, investigation } = this.state;

        return (
            <div className="container mt-5">
                {/* START INFO DOCTOR */}
                <div className="card p-4 shadow">
                    <h1 className="mb-4">Doctor Details</h1>

                    {doctorData === null ? (
                        <p>Loading...</p>
                    ) : (
                        <div className="row">
                            <div className="col-md-6">

                                <div className="mb-3">
                                    <label className="form-label">
                                        First Name:
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="firstname"
                                            value={doctorData.firstname}
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
                                            value={doctorData.lastname}

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
                                            value={doctorData.phone}

                                        />
                                    </label>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        Specialization:
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="birthdate"
                                            value={doctorData.specialization}

                                        />
                                    </label>
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">
                                        Email:
                                        <input type="text" className="form-control" name="email" value={doctorData.email} disabled />
                                    </label>
                                </div>

                            </div>
                        </div>
                    )}

                </div>
                {/* STOP INFO DOCTOR */}

                {/* ALL CONSULTATIONS */}
                <div className="card p-4 shadow" style={{ maxHeight: "400px", overflowY: "auto" }}>
                    <h1 className="mb-4">All Consultations</h1>
                    {allConsultations ? (
                        <ul>
                            {allConsultations.map((consultation, index) => (
                                <li key={index}>
                                    <p>ID Patient: {consultation.idPatient}</p>
                                    <p>ID Doctor: {consultation.idDoctor}</p>
                                    <p>Appointment Time: {consultation.appointmentTime}</p>
                                    <p>Diagnostic: {consultation.diagnostic}</p>
                                    <ul>
                                        {consultation.investigations.map((investigation, i) => (
                                            <li key={i}>
                                                <p>Naming: {investigation.naming}</p>
                                                <p>Processing Hours: {investigation.processingHours}</p>
                                                <p>Result: {investigation.result}</p>

                                            </li>

                                        ))}
                                        <Button color="primary" onClick={() => this.toggleModal(consultation._id)}>Add Investigation</Button>

                                    </ul>
                                    <hr />
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No consultations found.</p>
                    )}
                </div>
                {/* START ADD investigation */}
                <Modal isOpen={modalOpen} toggle={this.toggleModal}>
                    <ModalHeader toggle={this.toggleModal}>Add Investigation</ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup>
                                <Label for="naming">Naming</Label>
                                <Input type="text" name="naming" id="naming" value={investigation.naming} onChange={(e) => this.handleChange(e)} required />
                            </FormGroup>
                            <FormGroup>
                                <Label for="processingHours">Processing Hours</Label>
                                <Input type="text" name="processingHours" id="processingHours" value={investigation.processingHours} onChange={(e) => this.handleChange(e)}
                                    required />
                            </FormGroup>
                            <FormGroup>
                                <Label for="result">Result</Label>
                                <Input type="text" name="result" id="result" value={investigation.result} onChange={(e) => this.handleChange(e)} required />
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" onClick={() => this.handleSubmit()}>Save</Button>
                        <Button color="secondary" onClick={this.toggleModal}>Cancel</Button>
                    </ModalFooter>
                </Modal>
                {/* STOP ADD INVESTIGATION */}

            </div>


        );
    }

}
