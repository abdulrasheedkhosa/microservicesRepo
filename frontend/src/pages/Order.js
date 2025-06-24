import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import { Toast, ToastContainer } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/order.css';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:9000/api';

const Order = () => {
  const [showToast, setShowToast] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  const formik = useFormik({
    initialValues: {
      name: '',
      email: '',
      mobileNumber: '',
      eventDate: '',
      eventType: '',
      address: '',
      numberOfGuests: '',
      specialRequests: '',
    },
    validationSchema: Yup.object({
      name: Yup.string().required('Name is required'),
      email: Yup.string().email('Invalid email').required('Email is required'),
      mobileNumber: Yup.string().required('Mobile number is required'),
      eventDate: Yup.date().required('Event date is required'),
      eventType: Yup.string().required('Event type is required'),
      address: Yup.string().required('Address is required'),
      numberOfGuests: Yup.number()
      .positive('Must be a positive number')
      .required('Guest count is required'),
      specialRequests: Yup.string()
    }),
    onSubmit: async (values, { setSubmitting, resetForm }) => {
      setErrorMsg('');
      try {
        const token = localStorage.getItem('token');
        const response = await axios.post(`${API_BASE_URL}/placeOrder`, values, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (response.status === 201) {
          setShowToast(true);
          resetForm();
        } else {
          setErrorMsg('❌ Failed to place order. Please try again.');
        }
      } catch (error) {
        const message = error.response?.data?.message || '❌ Failed to place order. Please login again.';
        setErrorMsg(message);
      } finally {
        setSubmitting(false);
      }
    }
  });

  return (
      <div className="container mt-5 col-md-8 order-container">
        <h2 className="mb-4">📋 Place Walima Order</h2>

        {errorMsg && <div className="alert alert-danger">{errorMsg}</div>}

        <form onSubmit={formik.handleSubmit}>
          {/* Name */}
          <div className="form-group mb-3">
            <label>Name</label>
            <input
                type="text"
                name="name"
                className={`form-control ${formik.touched.name && formik.errors.name ? 'is-invalid' : ''}`}
                value={formik.values.name}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.name && formik.errors.name && (
                <div className="invalid-feedback">{formik.errors.name}</div>
            )}
          </div>

          {/* Email */}
          <div className="form-group mb-3">
            <label>Email</label>
            <input
                type="email"
                name="email"
                className={`form-control ${formik.touched.email && formik.errors.email ? 'is-invalid' : ''}`}
                value={formik.values.email}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.email && formik.errors.email && (
                <div className="invalid-feedback">{formik.errors.email}</div>
            )}
          </div>

          {/* Mobile Number */}
          <div className="form-group mb-3">
            <label>Mobile Number</label>
            <input
                type="text"
                name="mobileNumber"
                className={`form-control ${formik.touched.mobileNumber && formik.errors.mobileNumber ? 'is-invalid' : ''}`}
                value={formik.values.mobileNumber}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.mobileNumber && formik.errors.mobileNumber && (
                <div className="invalid-feedback">{formik.errors.mobileNumber}</div>
            )}
          </div>

          {/* Event Date */}
          <div className="form-group mb-3">
            <label>Event Date</label>
            <input
                type="date"
                name="eventDate"
                className={`form-control ${formik.touched.eventDate && formik.errors.eventDate ? 'is-invalid' : ''}`}
                value={formik.values.eventDate}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.eventDate && formik.errors.eventDate && (
                <div className="invalid-feedback">{formik.errors.eventDate}</div>
            )}
          </div>

          {/* Event Type */}
          <div className="form-group mb-3">
            <label>Event Type</label>
            <input
                type="text"
                name="eventType"
                placeholder="e.g. Walima, Mehndi"
                className={`form-control ${formik.touched.eventType && formik.errors.eventType ? 'is-invalid' : ''}`}
                value={formik.values.eventType}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.eventType && formik.errors.eventType && (
                <div className="invalid-feedback">{formik.errors.eventType}</div>
            )}
          </div>

          {/* Address */}
          <div className="form-group mb-3">
            <label>Venue Address</label>
            <input
                type="text"
                name="address"
                className={`form-control ${formik.touched.address && formik.errors.address ? 'is-invalid' : ''}`}
                value={formik.values.address}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.address && formik.errors.address && (
                <div className="invalid-feedback">{formik.errors.address}</div>
            )}
          </div>

          {/* Number of Guests */}
          <div className="form-group mb-3">
            <label>Number of Guests</label>
            <input
                type="number"
                name="numberOfGuests"
                className={`form-control ${formik.touched.numberOfGuests && formik.errors.numberOfGuests ? 'is-invalid' : ''}`}
                value={formik.values.numberOfGuests}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.numberOfGuests && formik.errors.numberOfGuests && (
                <div className="invalid-feedback">{formik.errors.numberOfGuests}</div>
            )}
          </div>

          {/* Special Requests */}
          <div className="form-group mb-4">
            <label>Special Requests</label>
            <textarea
                name="specialRequests"
                className="form-control"
                rows="3"
                value={formik.values.specialRequests}
                onChange={formik.handleChange}
            />
          </div>

          <button type="submit" className="btn btn-primary w-100" disabled={formik.isSubmitting}>
            {formik.isSubmitting ? 'Placing Order...' : 'Place Order'}
          </button>
        </form>

        {/* Toast */}
        <ToastContainer position="top-end" className="p-3">
          <Toast show={showToast} onClose={() => setShowToast(false)} delay={2500} autohide bg="success">
            <Toast.Header>
              <strong className="me-auto">Success</strong>
              <small>Now</small>
            </Toast.Header>
            <Toast.Body className="text-white">🎉 Order placed successfully!</Toast.Body>
          </Toast>
        </ToastContainer>
      </div>
  );
};

export default Order;
