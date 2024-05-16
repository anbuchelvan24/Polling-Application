import React, { useEffect, useState } from 'react';
import axios from 'axios';
import DraftItem from './DraftItem';
import './DraftList.css';

const DraftList = () => {
  const [polls, setPolls] = useState([]);
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

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (credentials.length > 0) {
          const response = await axios.get('http://localhost:8080/listDrafts',{
            params: {
              loggeduser: credentials[0],
            }
          });
          setPolls(response.data);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, [credentials]);


  return (
    <div className="background-container">
      <div className="overlay-container">
        <div className="poll-list-container">
          {polls.length > 0 ? (
            <>
              <h2 style={{ color: "whitesmoke" }}>List of Polls</h2>
              {polls.map((poll, index) => (
                <DraftItem key={index} poll={poll} />
              ))}
            </>
          ) : (
            <p className='not-found'>No polls found</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default DraftList;
