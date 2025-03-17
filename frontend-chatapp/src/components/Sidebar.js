import React from "react";

const Sidebar = ({ contacts, onSelect, selectedContact }) => {
  return (
    <div className="w-1/4 h-screen bg-gray-800 text-white overflow-y-auto">
      <h2 className="p-4 text-lg bg-gray-700">Contacts</h2>
      <ul>
        {contacts.map((contact, index) => (
          <li
            key={index}
            className={`p-4 border-b border-gray-600 cursor-pointer transition ${
              selectedContact === contact ? "bg-green-600" : "hover:bg-gray-700"
            }`}
            onClick={() => onSelect(contact)}
            aria-label={`Select chat with ${contact}`}
          >
            {contact}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;
