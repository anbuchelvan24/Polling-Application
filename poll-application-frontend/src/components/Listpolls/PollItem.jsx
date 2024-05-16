import React, { useState, useEffect } from 'react';
import './PollItem.css';
import axios from 'axios';

const PollItem = ({ poll }) => {
  const [selectedOption, setSelectedOption] = useState(null);
  const [credentials, setCredentials] = useState([]);
  const [already, setAlready] = useState(false);
  const [endval, setEndval] = useState(false);

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
    const fetchEndVal = async () => {
      try {
        const response = await axios.get('http://localhost:8080/endvals', {
          params: {
            pollname: poll.poll_name,
          },
        });
        setEndval(response.data);
      } catch (error) {
        console.error('Error fetching end value:', error);
      }
    };

    fetchEndVal();
  }, [poll.poll_name]);

  const answeredAlready = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/alreadyanswered/${poll.poll_name}`, {
        params: {
          participant: credentials[0]
        }
      });
      setAlready(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  answeredAlready();

  const handleOptionChange = (option) => {
    setSelectedOption(option);
  };

  const handleDelete = async () => {
    try {
      await axios.get('http://localhost:8080/delete', {
        params: {
          documentname: poll.poll_name,
          currentuser: credentials[0],
        },
      });
      console.log('Poll deleted successfully.');
      window.location.reload(); // Reload the page
    } catch (error) {
      console.error('Error deleting poll:', error);
    }
  };

  const endpolling = async () => {
    try {
      await axios.get('http://localhost:8080/endpoll', {
        params: {
          pollname: poll.poll_name,
        },
      });
      setEndval(true); // Assuming the poll is immediately ended after the button is clicked
    } catch (error) {
      console.error('Error ending poll:', error);
    }
  };

  const handleSubmit = async () => {
    const optionsArray = Object.entries(poll.options).map(([option, votes]) => option);
    const selectedIndex = optionsArray.findIndex((option) => option === selectedOption);

    try {
      const response = await axios.get(`http://localhost:8080/${poll.poll_name}`, {
        params: {
          participant: poll.creator,
          answer: selectedIndex,
        },
      });

      console.log(response.data);
      window.location.reload(); // Reload the page
    } catch (error) {
      console.error('Error submitting poll:', error);
    }
  };

  if (endval) {
    return null; // Don't render anything if the poll has ended
  }

  return (
    <div className="poll-container">
      <div className="background-image1"></div>
      <div className="poll-header">
        <span className="creator-name">Created by: {poll.creator}</span>
        <span className="timestamp">{poll.timestamp}</span>
        {credentials[0] === poll.creator && (
          <>
            <button className="delete-button" onClick={handleDelete}>
              Delete
            </button>
            <button className="submit-button" onClick={endpolling}>
              End
            </button>
          </>
        )}
      </div>
      <div className="separator-line"></div>
      <h3 className="question">{poll.question}</h3>
      <ul className="options-list">
        {Object.entries(poll.options).map(([option, votes], index) => (
          <li key={option} className="option-item">
            <label className='option1'>
              <input
                type="radio"
                name={`poll_${poll.poll_name}`}
                value={option}
                checked={selectedOption === option}
                onChange={() => handleOptionChange(option)}
              />
              <span className="option-text">{option}</span>
            </label>
            <span className="vote-count">{votes} votes</span>
          </li>
        ))}
      </ul>
      {already ? <h2 id='already-message1'>Already Answered</h2> :
        <button className="submit-button" onClick={handleSubmit}>
          Submit
        </button>
      }
    </div>
  );
};

export default PollItem;
