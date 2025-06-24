import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/dashboard.css';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:9000/api';

const Dashboard = () => {
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`${API_BASE_URL}/orders`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setOrders(response.data);
      } catch (err) {
        setError('⚠️ Failed to fetch orders. Please check login or server.');
      }
    };

    fetchOrders();
  }, []);

  return (
      <div className="dashboard-container container mt-5">
        <h2 className="mb-4">📋 All Event Orders</h2>

        {error && <div className="alert alert-danger">{error}</div>}

        {orders.length === 0 ? (
            <p>No orders found.</p>
        ) : (
            <div className="table-responsive">
              <table className="table table-bordered table-striped">
                <thead className="table-dark">
                <tr>
                  <th>#</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Mobile</th>
                  <th>Event Date</th>
                  <th>Event Type</th>
                  <th>Guests</th>
                  <th>Venue</th>
                  <th>Special Requests</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((order, index) => (
                    <tr key={index}>
                      <td>{index + 1}</td>
                      <td>{order.name}</td>
                      <td>{order.email}</td>
                      <td>{order.mobileNumber}</td>
                      <td>{order.eventDate}</td>
                      <td>{order.eventType}</td>
                      <td>{order.numberOfGuests}</td>
                      <td>{order.address}</td>
                      <td>{order.specialRequests}</td>
                    </tr>
                ))}
                </tbody>
              </table>
            </div>
        )}
      </div>
  );
};

export default Dashboard;
