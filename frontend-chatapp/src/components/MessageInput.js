import React, { useState } from "react";
import { IoSend } from "react-icons/io5";

const MessageInput = ({ onSend }) => {
  const [message, setMessage] = useState("");

  const handleSend = () => {
    if (message.trim()) {
      onSend(message);
      setMessage("");
    }
  };

  return (
    <div className="flex p-4 bg-gray-800">
      <input
        type="text"
        className="flex-1 p-2 bg-gray-700 text-white rounded outline-none"
        placeholder="Type a message..."
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyDown={(e) => e.key === "Enter" && handleSend()}
        aria-label="Message Input"
      />
      <button
        className={`ml-2 p-2 rounded transition ${
          message.trim() ? "bg-green-500 hover:bg-green-600" : "bg-gray-500 cursor-not-allowed"
        }`}
        onClick={handleSend}
        disabled={!message.trim()}
        aria-label="Send Message"
      >
        <IoSend size={24} />
      </button>
    </div>
  );
};

export default MessageInput;
