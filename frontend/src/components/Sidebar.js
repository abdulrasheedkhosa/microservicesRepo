import React from 'react';
import { NavLink } from 'react-router-dom';
import '../css/Sidebar.css';

const Sidebar = () => {
  return (
      <div className="sidebar">
        <h2>Dashboard</h2>
        <div className="nav-links">
          <NavLink to="/dashboard" className={({ isActive }) => isActive ? 'active' : ''}>Home</NavLink>
          <NavLink to="/profile" className={({ isActive }) => isActive ? 'active' : ''}>Profile</NavLink>
          <NavLink to="/order" className={({ isActive }) => isActive ? 'active' : ''}>Place Order</NavLink> {/* ✅ Added */}

        </div>
      </div>
  );
};

export default Sidebar;
