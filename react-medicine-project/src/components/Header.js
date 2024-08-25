import * as React from 'react'

export default function Header(props){
    return(
        <header className='HeaderPOS'>
                <img src={props.logoSrc} className = "logoPOS" alt="logo"/>
                <h1 className='titleHeaderPOS'>{props.pageTitle}</h1>
        </header>
    )
}