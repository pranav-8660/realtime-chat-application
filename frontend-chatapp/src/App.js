import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ContactsPage from "./pages/ContactsPage";
import ChatPage from "./pages/ChatPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/contacts" element={<ContactsPage />} /> {/* ✅ Fixed ContactsPage Route */}
        <Route path="/chat/:contact" element={<ChatPage />} />
      </Routes>
    </Router>
  );
}

export default App;
