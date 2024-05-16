import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginForm from './components/LoginForm/LoginForm.jsx';
import RegisterForm from './components/Register/RegisterForm.jsx';
import PollForm from './components/CreatePoll/createPoll.jsx';
import PollList from './components/Listpolls/PollList.jsx';
import NavBar from './components/NavBar/NavBar.jsx';
import DraftList from './components/Drafts/DraftList.jsx';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* No NavBar for LoginForm and RegisterForm */}
        <Route path='/' element={<LoginForm />} />
        <Route path='login' element={<LoginForm />} />
        <Route path='register' element={<RegisterForm />} />

        {/* NavBar for other pages */}
        <Route
          path='/*'
          element={
            <>
              <NavBar />
              <Routes>
                <Route path='createpoll' element={<PollForm />} />
                <Route path='polls' element={<PollList />} />
                <Route path='drafts' element={<DraftList/>}/>
              </Routes>
            </>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
