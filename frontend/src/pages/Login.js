import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import '../css/login.css'; // If you’re using a CSS file

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:9000/api';

const Login = key => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ username: '', password: '' });
  const [errorMsg, setErrorMsg] = useState('');

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMsg('');

    try {
      const response = await axios.post(`${API_BASE_URL}/login`, formData);
      if (response.data && response.data.statusCode === "200") {
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("username", response.data.username);
        console.log("Login response:", response.data.username);
        console.log("Login response token:", response.data.token);

        // Optional: store JWT
        const username = localStorage.getItem("username");
        const token = localStorage.getItem("token");
        console.log("username:", username);
        console.log("token:", token);

        navigate('/dashboard');
      } else {
        setErrorMsg("Invalid credentials");
      }
    } catch (error) {
      setErrorMsg("Login failed. Please try again.");
    }
  };

  return (
      <div className="login-box">
        <h2>Login</h2>
        {errorMsg && <div className="error">{errorMsg}</div>}

        <form onSubmit={handleLogin} className="login-form">
          <div className="form-group">
            <label>Username</label>
            <input
                type="text"
                name="username"
                value={formData.username}
                onChange={handleChange}
                required
                placeholder="Enter username"
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                required
                placeholder="Enter password"
            />
          </div>

          <button type="submit" className="btn-login">Login</button>
        </form>

        {/* ✅ Register Link */}
        <p style={{ marginTop: '10px' }}>
          Don't have an account? <Link to="/register">Register here</Link>
        </p>
      </div>
  );
};

export default Login;
