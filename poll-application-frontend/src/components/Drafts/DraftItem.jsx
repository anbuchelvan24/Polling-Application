import React, { useState, useEffect } from 'react';
import './DraftItem.css';
import axios from 'axios';

const DraftItem = ({ poll }) => {
  const [selectedOption, setSelectedOption] = useState(null);
  const [credentials, setCredentials] = useState([]);
  const [editTgl, setEditTgl] = useState(false);
  
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


  const handleOptionChange = (option) => {
    setSelectedOption(option);
  };

  const handleEdit =()=>{
    setEditTgl(!editTgl);
  }
  const handleDelete = async () => {
    try {
      await axios.get('http://localhost:8080/deletedraft', {
        params: {
          documentname: poll.poll_name,
          currentuser: credentials[0],
        },
      });

      // Perform any additional actions after successful deletion if needed

      console.log('Poll deleted successfully.');
      window.location.reload(); // Reload the page
    } catch (error) {
      console.error('Error deleting poll:', error);
    }
  };

const incrementOption = () =>{
  const a = option + 1;
}

  const handleSubmit = async () => {
    const optionsArray = Object.entries(poll.options).map(([option, votes]) => option);
    const selectedIndex = optionsArray.findIndex((option) => option === selectedOption);

    try {
      const response = await axios.get(`http://localhost:8080/draft/${poll.poll_name}`, {
        params: {
          loggeduser: credentials[0],
        },
      });

      console.log(response.data);
      window.location.reload(); // Reload the page
    } catch (error) {
      console.error('Error submitting poll:', error);
    }
  };

  return (
    <div className="poll-container">
      <div className="background-image1"></div>
      <div className="poll-header">
        <span className="creator-name">{poll.creator}</span>
        <span className="timestamp">{poll.timestamp}</span>
        {credentials[0] === poll.creator && (<><button className="delete-button" onClick={handleDelete}>
            Delete
          </button>
           <button className="delete-button" onClick={handleEdit}>
           Edit
         </button></>
          
        )}
      </div>
      <div className="separator-line"></div>
      {editTgl? <input placeholder="Edit your question" defaultValue={poll.question}></input> : <h3 className="question">{poll.question}</h3> }
      <div className="separator-line1"></div>
      <ol className="options-list">
        {Object.entries(poll.options).map(([option, votes], index) => (
          <li key={option} className="option-item1">
            <label className='option1'>
              <text
                
                name={`poll_${poll.poll_name}`}
                value={option}
                checked={selectedOption === option}
                onChange={() => handleOptionChange(option)}
              />
              {editTgl? <input placeholder="Edit your options" value={updatedoption} defaultValue={option}></input> : <span className="option-text">{option}</span>}
              
            </label>
          </li>
        ))}
      </ol>
      {<button className="submit-button" onClick={handleSubmit}>
        Publish
      </button>}
    </div>
  );
};

export default DraftItem;
