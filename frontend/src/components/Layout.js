import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';
import Sidebar from './Sidebar';
import '../css/layout.css';

const Layout = () => {
  return (
      <>
        <Navbar />
        <div className="layout-container">
          <Sidebar />
          <main className="main-content">
            <Outlet />
          </main>
        </div>
      </>
  );
};

export default Layout;
