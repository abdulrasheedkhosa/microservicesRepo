import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/profile.css';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:9000/api';

const Profile = () => {
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchProfile = async () => {
      const token = localStorage.getItem('token');
      const username = localStorage.getItem('username');

      if (!token) {
        setError('⚠️ No token found. Please login first.');
        return;
      }

      try {
        const response = await axios.get(`${API_BASE_URL}/profile?username=${username}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setProfile(response.data);
      } catch (err) {
        if (err.response && err.response.status === 401) {
          setError('❌ Unauthorized: Please login again.');
        } else {
          setError('⚠️ Error fetching profile. Try again later.');
        }
      }
    };

    fetchProfile();
  }, []);

  if (error) return <div className="profile-error">{error}</div>;
  if (!profile) return <div className="profile-loading">Loading profile...</div>;

  return (
        <div className="profile-container">
          <div className="profile-header">👤 Profile Information</div>
          <div className="profile-field"><strong>Username:</strong> {profile.username}</div>
          <div className="profile-field"><strong>Email:</strong> {profile.email}</div>
        </div>

  );
};

export default Profile;
