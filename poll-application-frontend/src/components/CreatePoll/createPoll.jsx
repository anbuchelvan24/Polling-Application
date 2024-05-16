import React, { useState, useEffect } from 'react';
import { TbTrashFilled } from "react-icons/tb";
import axios from 'axios';
import './createPoll.css';
import { useNavigate } from 'react-router-dom';

const PollForm = ({ onSubmit }) => {
  const [question, setQuestion] = useState('');
  const [options, setOptions] = useState(['', '']);
  const [pollname, setPollname] = useState("");
  const [organization, setOrganization] = useState("");
  const [team, setTeam] = useState("");
  const [credentials, setCredentials] = useState([]);
  const navigate = useNavigate();

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
  }, []);  // Empty dependency array to run once on mount

  const createpoll = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/create', {
        "poll_name": credentials[0] +"-"+pollname,
        "creator": credentials[0],
        "question": question,
        "options": options,
        "answered": null,
        "organization": organization,
        "team": team
      });

      const message = response.data;
      alert(message);

      navigate('/polls');
    } catch (error) {
      console.error(error);
    }
  };

  const createdraft = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/draft', {
        "poll_name": credentials[0] +"-"+pollname,
        "creator": credentials[0],
        "question": question,
        "options": options,
        "answered": null,
        "organization": organization,
        "team": team
      });

      const message = response.data;
      alert(message);

      navigate('/drafts');
    } catch (error) {
      console.error(error);
    }
  };

  const handleOptionChange = (index, value) => {
    const newOptions = [...options];
    newOptions[index] = value;
    setOptions(newOptions);
  };

  const addOption = () => {
    setOptions([...options, '']);
  };

  const removeOption = (index) => {
    if (options.length > 2) {
      const newOptions = [...options];
      newOptions.splice(index, 1);
      setOptions(newOptions);
    } else {
      alert('You must have at least two options.');
    }
  };

  return (
    <div className="body-container">
      <div className="background-image" />
      <div className="overlay">
        <form className="form-container" onSubmit={createpoll}>
          <h1 className="title">Create a Poll</h1>
          <label style={{ fontWeight: 'bold' }}>
            Organization & Team:
          </label>
          <input type='text' value={organization} onChange={(e) => setOrganization(e.target.value)} placeholder="Enter organization name"></input>
          <input type='text' value={team} onChange={(e) => setTeam(e.target.value)} placeholder="Enter team name"></input>
          <label style={{ fontWeight: 'bold' }}>
            Poll Name & Question:
          </label>
          <input type='text' value={pollname} onChange={(e) => setPollname(e.target.value)} placeholder="Enter poll name (Must be different from other polls you've created! )"></input>
          <textarea
            rows="3"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            placeholder="Type your question here..."
            className="input"
          />

          <label style={{ fontWeight: 'bold' }}>Options:</label>
          <div className='options-list1'>
          {options.map((option, index) => (
            <OptionInput
              key={index}
              index={index}
              value={option}
              required
              onChange={(value) => handleOptionChange(index, value)}
              onRemove={() => removeOption(index)}
            />
          ))}
          </div>
        
          <div className="button-container">
            <button type="button" onClick={addOption} className="button">
              Add Option
            </button>
            <button type="submit" className="button">Create Poll</button>
            <button className="button" onClick={createdraft}>Create Draft</button>
          </div>
        </form>
      </div>
    </div>
  );
};

const OptionInput = ({ index, value, onChange, onRemove }) => {
  const optionNumber = typeof index === 'number' ? index + 1 : '';

  return (
    <div className="input-container">
      <textarea
        rows="2"
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder={`Enter option ${optionNumber}`}
        className="input"
      />
      <button type="button" onClick={onRemove} className="remove-button">
        <TbTrashFilled />
      </button>

    </div>
  );
};

export default PollForm;
