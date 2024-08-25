import * as React from 'react'
import WelcomeContent  from './WelcomeContent'
import PatientsListContent from './PatientsListContent'
import LoginForm from './LoginForm'
import { request, getAuthToken, setAuthHeader, setEmail, setRole, getEmail, getRole } from '../helpers/axios-helper';
import Buttons from './Buttons';
import { jwtDecode } from "jwt-decode";
import PatientHomePage from './PatientHomePage';
import DoctorHomePage from './DoctorHomePage';




export default class AppContent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            componentToShow: "welcome", //initial state -> welcome page 
            roleForHomePage:""
        }
    };

    login = () => {
        this.setState({componentToShow: "login"}) // after that, i can go on login page from welcome state
    };

   



    logout = () => {
        console.log("intru in logout functie");
        const token = localStorage.getItem('access_token');
    
        if (token) {
            request("POST", "/logout", {}, { Authorization: `Bearer ${token}` })
                .then(response => {

                    console.log("Logout successful:", response.data);
                    localStorage.removeItem('access_token');
                    localStorage.removeItem('email');
                    localStorage.removeItem('role');
                    this.setState({ componentToShow: "welcome" , roleForHomePage: ""}); //on logout -> always go on welcome page so have to choose login or register as further action
                })
                .catch(error => {
                    console.error("Logout error:", error);
                    if (error.response && error.response.status) {
                        // Unauthorized (401) indicates expired token
                        alert("Logout error: [ "+error.response +" ][ "+error.response.status+" ]" );
                        // Perform automatic logout
                        this.setState({ componentToShow: "welcome" });
                        localStorage.removeItem('access_token');
                        localStorage.removeItem('email');
                        localStorage.removeItem('role');

                    } else {
                        // Handle other errors
                        alert("Logout failed. Please check console for details.");
                    }
                });
        } else {
            console.warn("Token not available");
            alert("Token not available. Please check console for details.");
        }
    };

    

    onLoginPatient = (e, email, password) => {
        e.preventDefault();
        request(
            "POST",
            "/patients/authenticate",
            {
                email: email,
                password: password
            })
            .then((response) => {
                setAuthHeader(response.data.access_token);
                setEmail(email);
                setRole("PATIENT");
                this.setState({ componentToShow: "patientHomePage", roleForHomePage:"PATIENT" });

            })
            .catch((error) => {
                setAuthHeader(null);
                setEmail("");
    
                if (error.response) {
                    const errorMessage = error.response.data || "Authentication failed";
                    alert(errorMessage); 
                } else if (error.request) {
                    // The request was made but no response was received
                    console.error("No response received from the server");
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.error("Error setting up the request:", error.message);
                }
            });
    };
    onLoginDoctor = (e, email, password) => {
        e.preventDefault();
        request(
            "POST",
            "/doctors/authenticate",
            {
                email: email,
                password: password
            })
            .then((response) => {
                setAuthHeader(response.data.access_token);
                setEmail(email);
                setRole("DOCTOR");

                this.setState({ componentToShow: "doctorHomePage" , roleForHomePage: "DOCTOR"});

            })
            .catch((error) => {
                setAuthHeader(null);
                setEmail("");
                if (error.response) {
                    const errorMessage = error.response.data || "Authentication failed";
                    alert(errorMessage); 
                } else if (error.request) {
                    // The request was made but no response was received
                    console.error("No response received from the server");
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.error("Error setting up the request:", error.message);
                }
            });
    };
//  PATIENT REGISTER 
    onRegisterPatient = (event, cnp, lastname, firstname, email, phone, birthdate, password, role, isactive) => {
        event.preventDefault();
        request(
            "POST",
            "/patients/register/"+cnp,
            {
                cnp: cnp,
                lastname: lastname,
                firstname: firstname,
                email: email,
                phone: phone,
                birthdate: birthdate,
                password: password,
                role: role,
                isactive: isactive
            }).then(
            (response) => {
                setAuthHeader(response.data.access_token);
                setEmail(email);
                setRole("PATIENT");

                this.setState({componentToShow: "patientHomePage", roleForHomePage:"PATIENT"});

            }).catch(
            (error) => {
                // setAuthHeader(null);
                // this.setState({componentToShow: "welcome"})
                setAuthHeader(null);
                setEmail("");
    
                if (error.response) {
                    const errorMessage = error.response.data || "Authentication failed";
                    alert(errorMessage); 
                } else if (error.request) {
                    // The request was made but no response was received
                    console.error("No response received from the server");
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.error("Error setting up the request:", error.message);
                }
            }
        );
    };
    
//DOCTOR REGISTER 
    onRegisterDoctor = (event, lastname, firstname, email, phone, specialization, password, role) => {
        event.preventDefault();
        request(
            "POST",
            "/doctors/register",
            {
                lastname: lastname,
                firstname: firstname,
                email: email,
                phone: phone,
                specialization: specialization,
                password: password,
                role: role,
            }).then(
            (response) => {
                setAuthHeader(response.data.access_token);
                setEmail(email);
                setRole("DOCTOR");
                this.setState({componentToShow: "doctorHomePage", roleForHomePage:"DOCTOR"});

            }).catch(
            (error) => {
                // setAuthHeader(null);
                // this.setState({componentToShow: "welcome"})
                setAuthHeader(null);
                setEmail("");
                if (error.response) {
                    const errorMessage = error.response.data || "Authentication failed";
                    alert(errorMessage); 
                } else if (error.request) {
                    // The request was made but no response was received
                    console.error("No response received from the server");
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.error("Error setting up the request:", error.message);
                }
            }
        );
    };


    componentDidMount() {
     
        const token = getAuthToken();
        const storedEmail = getEmail();
        const storedRole = getRole();
        if (token) {
            try {
                const decodedToken = jwtDecode(token);
                const currentTime = Date.now(); // current time in milliseconds
    
                console.log("Decoded Token:", decodedToken);
    
                if (decodedToken.exp * 1000 < currentTime) {
                    // Token expired, perform automatic logout
                    alert("Token has expired. Please log in again.");
                    console.log("Token expired");
                    this.logout();  
                } else {
                    // Token is still valid, set the componentToShow state accordingly
                    console.log("Token is still valid "+decodedToken.role);
                    // this.setState({ componentToShow: "messages" , roleForHomePage: decodedToken.role});
                    if(storedRole === "PATIENT"){
                        // this.setState({  roleForHomePage: decodedToken.role});
                        this.setState({  componentToShow: "patientHomePage",  roleForHomePage: decodedToken.role});


                    }else if (storedRole === "DOCTOR"){

                        this.setState({  componentToShow: "doctorHomePage",  roleForHomePage: decodedToken.role});

                    }
                    else{
                        console.log("ROL NECUNOSCUT");
                    }

                }
            } catch (error) {
                // Handle errors decoding the token
                console.error("Error decoding token:", error);
                localStorage.removeItem('access_token');
                localStorage.removeItem('email');
                localStorage.removeItem('role');

                this.setState({ componentToShow: "welcome" , roleForHomePage: ""});
            }
        } else {
            // No token available, set the componentToShow state accordingly
            console.log("No token available");
            this.setState({ componentToShow: "welcome", roleForHomePage:"" });
        }
    }
    
    
  render() {
    return (
      <>
        <Buttons
          login={this.login}
          logout={this.logout}
        />

        {this.state.componentToShow === "welcome" && <WelcomeContent /> }
        {this.state.componentToShow === "login" && <LoginForm onLoginPatient={this.onLoginPatient} onLoginDoctor={this.onLoginDoctor} onRegisterPatient={this.onRegisterPatient} onRegisterDoctor={this.onRegisterDoctor}/>}
        {this.state.componentToShow === "messages" && <PatientsListContent />}
        {/* {this.state.componentToShow === "patientHomePage" && (<PatientHomePage />)} */}
        {this.state.componentToShow === "patientHomePage" && (
    <PatientHomePage handleTokenExpiration={this.logout} />
)}
        {this.state.componentToShow === "doctorHomePage" && <DoctorHomePage />}
        

      </>
    );
  };
}





