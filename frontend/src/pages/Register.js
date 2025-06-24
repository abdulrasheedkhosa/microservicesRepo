import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Toast, ToastContainer } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/register.css';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

const Register = () => {
  const navigate = useNavigate();
  const [showToast, setShowToast] = useState(false);

  const formik = useFormik({
    initialValues: {
      username: '',
      email: '',
      password: ''
    },
    validationSchema: Yup.object({
      username: Yup.string().required('Username is required'),
      email: Yup.string().email('Invalid email').required('Email is required'),
      password: Yup.string().min(6, 'Minimum 6 characters').required('Password is required'),
    }),
    onSubmit: async (values, { setSubmitting, setStatus, setFieldError }) => {
      try {
        const response = await axios.post(`${API_BASE_URL}/register`, values);
        setShowToast(true);

        // Wait 2 seconds, then navigate to login
        setTimeout(() => {
          navigate('/');
        }, 2000);
      } catch (error) {
        const msg = error.response?.data?.errorMessage || error.errorMessage;
        if (msg.includes('Username already exists')) {
          setFieldError('username', 'Username is already taken');
        } else if (msg.includes('Email already exists')) {
          setFieldError('email', 'Email is already registered');
        } else {
          setStatus('Registration failed: ' + msg);
        }
      } finally {
        setSubmitting(false);
      }
    }
  });

  return (
      <div className="container col-md-12 mt-5 register-container">
        <h2 className="mb-4">Register</h2>

        {formik.status && <div className="alert alert-danger">{formik.status}</div>}

        <form onSubmit={formik.handleSubmit}>
          {/* Username Field */}
          <div className="form-group mb-3">
            <label htmlFor="username" className="form-label">Username</label>
            <input
                id="username"
                name="username"
                className={`form-control ${formik.touched.username && formik.errors.username ? 'is-invalid' : ''}`}
                value={formik.values.username}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.username && formik.errors.username && (
                <div className="invalid-feedback">{formik.errors.username}</div>
            )}
          </div>

          {/* Email Field */}
          <div className="form-group mb-3">
            <label htmlFor="email" className="form-label">Email</label>
            <input
                id="email"
                name="email"
                type="email"
                className={`form-control ${formik.touched.email && formik.errors.email ? 'is-invalid' : ''}`}
                value={formik.values.email}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.email && formik.errors.email && (
                <div className="invalid-feedback">{formik.errors.email}</div>
            )}
          </div>

          {/* Password Field */}
          <div className="form-group mb-4">
            <label htmlFor="password" className="form-label">Password</label>
            <input
                id="password"
                name="password"
                type="password"
                className={`form-control ${formik.touched.password && formik.errors.password ? 'is-invalid' : ''}`}
                value={formik.values.password}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
            />
            {formik.touched.password && formik.errors.password && (
                <div className="invalid-feedback">{formik.errors.password}</div>
            )}
          </div>

          <button type="submit" className="btn btn-primary w-100" disabled={formik.isSubmitting}>
            {formik.isSubmitting ? 'Registering...' : 'Register'}
          </button>
        </form>

        {/* Toast Notification */}
        <ToastContainer position="top-end" className="p-3">
          <Toast show={showToast} onClose={() => setShowToast(false)} delay={2000} autohide bg="success">
            <Toast.Header>
              <strong className="me-auto">Success</strong>
              <small>Just now</small>
            </Toast.Header>
            <Toast.Body className="text-white">🎉 Registration successful!</Toast.Body>
          </Toast>
        </ToastContainer>
      </div>
  );
};

export default Register;
