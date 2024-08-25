import axios from 'axios';


// export const getAuthToken = () => {
//     return window.localStorage.getItem('access_token');
// };

// export const setAuthHeader = (token) => {
//     window.localStorage.setItem('access_token', token);
// };
export const getAuthToken = () => {
    return localStorage.getItem('access_token');
};
export const setAuthHeader = (token) => {
    localStorage.setItem('access_token', token);
};

export const setEmail = (email) => {
    localStorage.setItem('email', email);
}; 
export const getEmail = () => {
    return localStorage.getItem('email');
};

export const setRole = (role) => {
    localStorage.setItem('role', role);
}; 
export const getRole = () => {
    return localStorage.getItem('role');
};

axios.defaults.baseURL = 'http://localhost:8082/api/medical_office/gateway'
axios.defaults.headers.post['Content-Type'] = 'application/json'


export const request = (method, url, data) => {

    let headers = {};
    if (getAuthToken() !== null && getAuthToken() !== "null") {
        headers = {'Authorization': `Bearer ${getAuthToken()}`};
    }

    return axios({
        method: method,
        url: url,
        headers: headers,
        data: data});
};