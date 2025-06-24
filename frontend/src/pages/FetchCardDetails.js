import React, { useState } from 'react';
import axios from 'axios';
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
const FetchCardDetails = () => {
  const [mobileNumber, setMobileNumber] = useState('');
  const [cardData, setCardData] = useState(null);
  const [error, setError] = useState('');

  const fetchCard = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/fetch`, {
        params: { mobileNumber }
      });
      setCardData(response.data);
      setError('');
    } catch (err) {
      console.error(err);
      setCardData(null);
      setError('Error fetching card details.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Fetch Card Details</h2>
      <input
        type="text"
        value={mobileNumber}
        onChange={(e) => setMobileNumber(e.target.value)}
        placeholder="Enter 10-digit mobile number"
      />
      <button onClick={fetchCard}>Fetch</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {cardData && (
        <div>
          <h3>Card Info</h3>
          <p><strong>Card Number:</strong> {cardData.cardNumber}</p>
          <p><strong>Card Type:</strong> {cardData.cardType}</p>
          <p><strong>Limit:</strong> {cardData.totalLimit}</p>
          <p><strong>Mobile Number:</strong> {cardData.mobileNumber}</p>
          <p><strong>Available Amount:</strong> {cardData.availableAmount}</p>
        </div>
      )}
    </div>
  );
};

export default FetchCardDetails;
