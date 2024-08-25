import * as React from 'react';

import { request } from '../helpers/axios-helper';

export default class PatientsListContent extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            data : []
        };
    };

    componentDidMount(){
        request(
            "GET",
            "/messages",
            {}
        ).then((response)=>{
            this.setState({data:response.data})
        }  
        );
    };

    render(){
        return(
            <div>
                {this.state.data && this.state.data.map((line)=><p>{line}</p>)}
            </div>
        );
    };
}
