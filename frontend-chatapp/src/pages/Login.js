import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    setError(""); // Reset error state

    if (!username.trim() || !password.trim()) {
      setError("Please enter both username and password.");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/auth/login", {
        username,
        password,
      });

      if (response.data.token) {
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("username", username); // ✅ Store logged-in user
        navigate("/contacts"); // ✅ Redirect to contacts after login
      } else {
        setError("Invalid response from server. Please try again.");
      }
    } catch (err) {
      console.error("Login error:", err.response || err);
      setError(err.response?.data?.message || "Invalid username or password.");
    }
  };

  return (
    <div className="flex h-screen items-center justify-center bg-gray-900 text-white">
      <div className="bg-gray-800 p-8 rounded-lg shadow-lg w-96">
        <h2 className="text-2xl font-semibold mb-4 text-center">Login</h2>

        {error && <p className="text-red-500 text-center mb-3">{error}</p>}

        <input
          type="text"
          placeholder="Username"
          className="w-full p-2 mb-3 bg-gray-700 text-white rounded border border-gray-600 focus:outline-none focus:ring-2 focus:ring-green-500"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          className="w-full p-2 mb-3 bg-gray-700 text-white rounded border border-gray-600 focus:outline-none focus:ring-2 focus:ring-green-500"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          className="w-full p-2 bg-green-500 rounded hover:bg-green-600 transition-all duration-200"
          onClick={handleLogin}
        >
          Login
        </button>

        {/* ✅ Register Link */}
        <p className="text-center mt-4 text-gray-400">
          Don't have an account?{" "}
          <span
            className="text-blue-400 cursor-pointer hover:underline"
            onClick={() => navigate("/register")}
          >
            Register here
          </span>
        </p>
      </div>
    </div>
  );
};

export default Login;
