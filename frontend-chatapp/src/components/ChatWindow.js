import React, { useEffect, useState } from "react";
import { IoSend } from "react-icons/io5";
import MessageInput from "./MessageInput";
import axios from "axios";

const ChatWindow = ({ socket, selectedContact }) => {
  const [messages, setMessages] = useState([]);
  const username = localStorage.getItem("username");
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!selectedContact) return;

    // ✅ Fetch chat history when a contact is selected
    axios
      .get(`http://localhost:8080/messages/${username}/${selectedContact}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => setMessages(response.data))
      .catch((error) => console.error("Error fetching messages:", error));

    // ✅ Listen for new messages
    socket.on("receive_message", (data) => {
      setMessages((prev) => [...prev, data]);
    });

    return () => {
      socket.off("receive_message");
    };
  }, [selectedContact, socket, username, token]);

  const sendMessage = (message) => {
    if (!message.trim()) return;

    const msgData = { sender: username, receiver: selectedContact, message };

    socket.emit("send_message", msgData);
    setMessages((prev) => [...prev, msgData]);

    // ✅ Store message in the database
    axios.post("http://localhost:8080/messages/send", msgData, {
      headers: { Authorization: `Bearer ${token}` },
    });
  };

  return (
    <div className="flex flex-col flex-1 h-screen bg-gray-900 text-white">
      <div className="bg-green-600 p-4 text-lg">{selectedContact || "Select a contact"}</div>
      <div className="flex-1 p-4 overflow-y-auto">
        {messages.map((msg, index) => (
          <div
            key={index}
            className={`p-2 rounded my-1 ${
              msg.sender === username ? "bg-green-500 self-end text-right" : "bg-gray-700"
            }`}
          >
            <strong>{msg.sender}: </strong>
            {msg.message}
          </div>
        ))}
      </div>
      <MessageInput onSend={sendMessage} />
    </div>
  );
};

export default ChatWindow;
