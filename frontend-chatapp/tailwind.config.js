/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: "#075E54", // WhatsApp Green
        secondary: "#128C7E",
        accent: "#25D366",
      },
    },
  },
  plugins: [],
};
