module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      colors: {
        primary: "#3EB489",
        secondary: "#6C63FF",
        bg: "#F8FAFC",
        textPrimary: "#1F2937",
        textSecondary: "#6B7280",
        success: "#22C55E",
        error: "#EF4444",
        border: "#E5E7EB"
      },
      borderRadius: { xl: "1rem", '2xl': "1.25rem" },
      boxShadow: {
        card: "0 4px 12px rgba(0, 0, 0, 0.08)"
      },
      fontFamily: { sans: ["Inter", "Poppins", "sans-serif"] }
    }
  },
  plugins: []
};
