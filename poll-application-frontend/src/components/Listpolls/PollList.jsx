import React, { useEffect, useState } from 'react';
import axios from 'axios';
import PollItem from './PollItem';
import './PollList.css';

const PollList = () => {
  const [polls, setPolls] = useState([]);
  const endval = false;

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/listPolls');
        setPolls(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="background-container">
      <div className="overlay-container">
        <div className="poll-list-container">
          {polls.length > 0 ? (
            <>
              <h2 style={{ color: "whitesmoke" }}>List of Polls</h2>
              {polls.map((poll, index) => (
                <PollItem key={index} poll={poll} />
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

export default PollList;
