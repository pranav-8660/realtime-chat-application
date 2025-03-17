import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const ContactsPage = () => {
  const [contacts, setContacts] = useState([]);
  const [newContact, setNewContact] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const username = localStorage.getItem("username");
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!username || !token) {
      navigate("/");
      return;
    }

    fetchContacts();
  }, [username, token, navigate]);

  const fetchContacts = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/contacts/${username}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      // ✅ Ensure we extract the 'contact' username field correctly
      setContacts(response.data.map(c => c.contact.username));
      setMessage("");
    } catch (error) {
      console.error("Error fetching contacts:", error);
      setMessage("Failed to load contacts.");
    }
  };

  const addContact = async () => {
    if (!newContact.trim()) {
      setMessage("Please enter a valid contact username.");
      return;
    }

    if (contacts.includes(newContact)) {
      setMessage("This contact is already in your list.");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/contacts/add",
        null,
        {
          params: { username, contactUsername: newContact },
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      if (response.status === 200) {
        setNewContact("");
        setMessage("Contact added successfully!");
        fetchContacts();
      }
    } catch (err) {
      console.error("Error adding contact:", err);
      setMessage("User not found or already added.");
    }
  };

  return (
    <div className="flex flex-col h-screen bg-gray-900 text-white">
      <h2 className="p-4 text-lg bg-gray-700 text-center">Contacts</h2>

      {message && <p className="p-2 text-center text-red-500">{message}</p>}

      {contacts.length > 0 ? (
        <ul className="flex-1 overflow-y-auto">
          {contacts.map((contact, index) => (
            <li
              key={index}
              onClick={() => navigate(`/chat/${contact}`)}
              className="p-4 border-b border-gray-600 cursor-pointer hover:bg-gray-700"
            >
              {contact}
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-center p-4">No contacts yet. Add some!</p>
      )}

      <div className="p-4 flex bg-gray-800">
        <input
          type="text"
          placeholder="Enter username to add"
          className="flex-1 p-2 bg-gray-700 text-white rounded"
          value={newContact}
          onChange={(e) => setNewContact(e.target.value)}
        />
        <button className="ml-2 bg-green-500 p-2 rounded hover:bg-green-600" onClick={addContact}>
          Add
        </button>
      </div>
    </div>
  );
};

export default ContactsPage;
