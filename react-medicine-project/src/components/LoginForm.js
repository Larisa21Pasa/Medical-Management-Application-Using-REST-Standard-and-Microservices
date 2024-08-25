import * as React from 'react';
import classNames from 'classnames';
import EnumSpecializations from './EnumSpecializations';

export default class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cnp: "", // PATIENT
            isactive: 1, // PATIENT

            specialization: "", //DOCTOR

            firstname: "", // PATIENT + DOCTOR 
            lastname: "", // PATIENT + DOCTOR
            email: "", // PATIENT + DOCTOR
            phone: "", // PATIENT + DOCTOR
            birthdate: "", // PATIENT
            password: "", // PATIENT + DOCTOR
            role: "PATIENT", // PATIENT + DOCTOR

            active: 'login',
            login: "",
            onLogin: props.onLogin,
            onRegisterPatient: props.onRegisterPatient,
            onRegisterDoctor: props.onRegisterDoctor
        };
    };
    onChangeHandler = (event) => {
        let name = event.target.name;
        let value = event.target.value;

        // Convertește data în formatul dorit
        if (name === "birthdate" && value) {
            const dateObject = new Date(value);
            const formattedDate = dateObject.toISOString().split('T')[0];
            value = formattedDate;
        }

        this.setState({ [name]: value });
    };


    // onSubmitLogin = (e) => {
    //     this.state.onLogin(e,
    //         this.state.email,
    //         this.state.password
    //     );
    // };

    onSubmitLogin = (e) => {
        e.preventDefault();

        const { role, email, password } = this.state;

        // Call the appropriate login function based on the selected role
        if (role === 'PATIENT') {
            this.props.onLoginPatient(e, email, password);
        } else if (role === 'DOCTOR') {
            this.props.onLoginDoctor(e, email, password);
        }
    };


    // onSubmitRegister = (e) => {
    //     this.state.onRegister(
    //         e,
    //         this.state.cnp,
    //         this.state.lastname,
    //         this.state.firstname,
    //         this.state.email,
    //         this.state.phone,
    //         this.state.birthdate,
    //         this.state.password,
    //         this.state.role,
    //         this.state.isactive
    //     );
    // };
    onSubmitRegister = (e) => {
        e.preventDefault();

        const {
            role,
            cnp,
            lastname,
            firstname,
            email,
            phone,
            birthdate,
            password,
            isactive,
            specialization,
        } = this.state;


        if (role === 'PATIENT') {
            this.props.onRegisterPatient(e, cnp, lastname, firstname, email, phone, birthdate, password, role, isactive);
        } else if (role === 'DOCTOR') {
            this.props.onRegisterDoctor(e, lastname, firstname, email, phone, specialization, password, role);
        }
    };

    render() {
        return (
            <div className="row justify-content-center">
                <div className="col-4">
                    {/* ALEGERE FORM */}
                    <ul className="nav nav-pills nav-justified mb-3" id="ex1" role="tablist">
                        <li className="nav-item" role="presentation">
                            <button className={classNames("nav-link", this.state.active === "login" ? "active" : "")} id="tab-login"
                                onClick={() => this.setState({ active: "login" })}>Login</button>
                        </li>

                        <li className="nav-item" role="presentation">
                            <button className={classNames("nav-link", this.state.active === "register" ? "active" : "")} id="tab-register"
                                onClick={() => this.setState({ active: "register" })}>Register</button>
                        </li>
                        <li className="nav-item dropdown">
                            <button className="nav-link dropdown-toggle" id="userTypeDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                {this.state.role === 'PATIENT' ? 'PATIENT' : 'DOCTOR'}
                            </button>
                            <ul className="dropdown-menu" aria-labelledby="userTypeDropdown">
                                <li><button className="dropdown-item" onClick={() => this.setState({ role: 'PATIENT' })}>Patient</button></li>
                                <li><button className="dropdown-item" onClick={() => this.setState({ role: 'DOCTOR' })}>Doctor</button></li>
                            </ul>
                        </li>
                    </ul>

                    {/* LOGIN */}
                    <div className="tab-content">
                        <div className={classNames("tab-pane", "fade", this.state.active === "login" ? "show active" : "")} id="pills-login">
                            <form onSubmit={this.onSubmitLogin}>
                                <div className="form-outline mb-4">
                                    <input type="email" id="loginEmail" name="email" className="form-control" onChange={this.onChangeHandler} value={this.state.email} />
                                    <label className="form-label" htmlFor="loginEmail">Email</label>
                                </div>

                                <div className="form-outline mb-4">
                                    <input type="password" id="loginPassword" name="password" className="form-control" onChange={this.onChangeHandler} value={this.state.password} />
                                    <label className="form-label" htmlFor="loginPassword">Password</label>
                                </div>


                                <button type="submit" className="btn btn-primary btn-block mb-4">Sign in</button>
                            </form>
                        </div>
                        {/* REGISTER */}
                        <div className={classNames("tab-pane", "fade", this.state.active === "register" ? "show active" : "")} id="pills-register">
                            {this.state.role === 'PATIENT' ? (
                                // REGISTER PATIENT
                                <form onSubmit={this.onSubmitRegister}>
                                    <div className="form-outline mb-4">
                                        <input type="text" id="cnp" name="cnp" className="form-control" onChange={this.onChangeHandler} value={this.state.cnp} />
                                        <label className="form-label" htmlFor="cnp">CNP</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="text" id="lastName" name="lastname" className="form-control" onChange={this.onChangeHandler} value={this.state.lastname} />
                                        <label className="form-label" htmlFor="lastName">Last name</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="text" id="firstName" name="firstname" className="form-control" onChange={this.onChangeHandler} value={this.state.firstname} />
                                        <label className="form-label" htmlFor="firstName">First name</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="email" id="email" name="email" className="form-control" onChange={this.onChangeHandler} value={this.state.email} />
                                        <label className="form-label" htmlFor="email">Email</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="tel" id="phone" name="phone" className="form-control" onChange={this.onChangeHandler} value={this.state.phone} />
                                        <label className="form-label" htmlFor="phone">Phone</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="date" id="birthdate" name="birthdate" className="form-control" onChange={this.onChangeHandler} value={this.state.birthdate} />
                                        <label className="form-label" htmlFor="birthdate">Birthdate</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="password" id="registerPassword" name="password" className="form-control" onChange={this.onChangeHandler} value={this.state.password} />
                                        <label className="form-label" htmlFor="registerPassword">Password</label>
                                    </div>

                                    <button type="submit" className="btn btn-primary btn-block mb-3">Sign up</button>
                                </form>
                            ) : (
                                // REGISTER DOCTOR
                                <form onSubmit={this.onSubmitRegister}>
                                    <div className="form-outline mb-4">
                                        <input type="text" id="lastName" name="lastname" className="form-control" onChange={this.onChangeHandler} value={this.state.lastname} />
                                        <label className="form-label" htmlFor="lastName">Last name</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="text" id="firstName" name="firstname" className="form-control" onChange={this.onChangeHandler} value={this.state.firstname} />
                                        <label className="form-label" htmlFor="firstName">First name</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="email" id="email" name="email" className="form-control" onChange={this.onChangeHandler} value={this.state.email} />
                                        <label className="form-label" htmlFor="email">Email</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="tel" id="phone" name="phone" className="form-control" onChange={this.onChangeHandler} value={this.state.phone} />
                                        <label className="form-label" htmlFor="phone">Phone</label>
                                    </div>

                                    <div className="form-outline mb-4">
                                        {/* <input type="text" id="specialization" name="specialization" className="form-control" onChange={this.onChangeHandler} value={this.state.specialization} />
                                        <label className="form-label" htmlFor="specialization">Specialization</label> */}
                                        <label className="form-label" htmlFor="specialization">Specialization</label>
                                        <select
        id="specialization"
        name="specialization"
        className="form-control"
        onChange={this.onChangeHandler}
        value={this.state.specialization}
    >
        <option value="">Selectează specializarea</option>
        {Object.values(EnumSpecializations).map((specialization, index) => (
            <option
                key={index}
                value={specialization} // Setează valoarea opțiunii cu specializarea
            >
                {specialization}
            </option>
        ))}
    </select>
                                    </div>

                                    <div className="form-outline mb-4">
                                        <input type="password" id="registerPassword" name="password" className="form-control" onChange={this.onChangeHandler} value={this.state.password} />
                                        <label className="form-label" htmlFor="registerPassword">Password</label>
                                    </div>

                                    <button type="submit" className="btn btn-primary btn-block mb-3">Sign up</button>
                                </form>
                            )}
                        </div>
                    </div>
                </div>
            </div>





        )
    }
}

