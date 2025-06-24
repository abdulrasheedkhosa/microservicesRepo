import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/Navbar.css';

const Navbar = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  return (
      <div className="navbar">
        <div className="navbar-title">🚀 My App</div>
        <div className="nav-actions">
          <button className="nav-button" onClick={handleLogout}>Logout</button>
        </div>
      </div>
  );
};

export default Navbar;
