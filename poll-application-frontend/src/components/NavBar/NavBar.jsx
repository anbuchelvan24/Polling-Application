import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import './NavBar.css';
import '../assets/google.png'

const NavBar = () =>{

    const navigate = useNavigate();
    const [credentials, setCredentials] = useState([]);

    useEffect(() => {
        const fetchCredentials = async () => {
          try {
            const response = await axios.get('http://localhost:8080/getcred');
            setCredentials(response.data);
          } catch (error) {
            console.error('Error fetching credentials:', error);
          }
        };
    
        fetchCredentials();
      }, []);

    const logoutUser = async() =>{
    
        try{
            await axios.get('http://localhost:8080/logout');
            navigate('/login')
        }
        catch(error){
            console.error(error)
        }
    }
    
    return(
    <header className="header">
        <h2 className="logo">Poll Wise</h2>
        {/* {credentials[1] === "google" ? (
        <img src='../assets/google.png' alt="Google Logo" />
        ) : (
        <img src='../assets/sap.jpeg' alt="SAP Logo" />
        )} */}
        <nav className="navbar">
            <a href="/createpoll">Create Polls</a>
            <a href="/polls">Poll List</a>
            <a href="/drafts">My Drafts</a>
            <button onClick={logoutUser}>Logout</button>
        </nav>
    </header>
    );
}

export default NavBar;