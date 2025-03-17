import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const ChatPage = () => {
  const { username: contactUsername } = useParams();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const navigate = useNavigate();
  const currentUser = localStorage.getItem("username");
  const token = localStorage.getItem("token");

  // Get messages between two users
  const fetchMessages = async () => {
    try {
      const response = await axios.get("http://localhost:8080/messages", {
        params: {
          sender: currentUser,
          receiver: contactUsername
        },
        headers: { Authorization: `Bearer ${token}` }
      });
      setMessages(response.data);
    } catch (err) {
      console.error("Error fetching messages:", err);
    }
  };

  // Send new message
  const sendMessage = async () => {
    if (!newMessage.trim()) return;

    try {
      await axios.post("http://localhost:8080/messages/send", 
        {
          sender: currentUser,
          receiver: contactUsername,
          content: newMessage
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setNewMessage("");
      fetchMessages();
    } catch (err) {
      console.error("Error sending message:", err);
    }
  };

  useEffect(() => {
    if (!currentUser || !token) {
      navigate("/");
      return;
    }
    
    fetchMessages();
    const interval = setInterval(fetchMessages, 3000); // Poll every 3 seconds
    
    return () => clearInterval(interval);
  }, [contactUsername]);

  return (
    <div className="flex flex-col h-screen bg-gray-900 text-white">
      <div className="p-4 bg-gray-700 flex items-center">
        <button className="mr-4" onClick={() => navigate("/contacts")}>←</button>
        <h2 className="text-lg">Chat with {contactUsername}</h2>
      </div>

      <div className="flex-1 overflow-y-auto p-4">
        {messages.map((message, index) => (
          <div
            key={index}
            className={`mb-4 ${message.sender === currentUser ? "text-right" : "text-left"}`}
          >
            <div className={`inline-block p-2 rounded-lg ${
              message.sender === currentUser 
                ? "bg-blue-600" 
                : "bg-gray-700"
            }`}>
              <p className="text-sm text-gray-300">{message.sender}</p>
              <p>{message.content}</p>
              <p className="text-xs text-gray-400 mt-1">
                {new Date(message.timestamp).toLocaleTimeString()}
              </p>
            </div>
          </div>
        ))}
      </div>

      <div className="p-4 bg-gray-800 flex">
        <input
          type="text"
          placeholder="Type a message..."
          className="flex-1 p-2 bg-gray-700 text-white rounded"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          onKeyPress={(e) => e.key === "Enter" && sendMessage()}
        />
        <button
          className="ml-2 bg-green-500 p-2 rounded hover:bg-green-600"
          onClick={sendMessage}
        >
          Send
        </button>
      </div>
    </div>
  );
};

export default ChatPage;