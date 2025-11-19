import React from "react";
import "../styles/dashboard.css";
import { jsPDF } from "jspdf";

export default function DashboardUser() {
  const user = JSON.parse(localStorage.getItem("bitsaUser"));

  const events = [
    { title: "AI Bootcamp", desc: "Join our 3-day workshop on AI and Data Science." },
    { title: "Networking Night", desc: "Meet industry professionals and alumni." },
    { title: "Hackathon 2025", desc: "Collaborate to build innovative tech projects." },
  ];

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h2>Welcome, {user?.name} 👋 (Student)</h2>
      </div>

      <div className="dashboard-cards">
        {events.map((e, i) => (
          <div key={i} className="dashboard-card">
            <h3>{e.title}</h3>
            <p>{e.desc}</p>
          </div>
        ))}
      </div>

      <section className="certificates">
        <h3>My Certificates</h3>
        <div className="cert-list">
          <div className="cert-item">
            <h4>Hackathon 2025 Participant</h4>
            <p>Issued on: {new Date().toLocaleDateString()}</p>
            <button
              onClick={() => {
                const doc = new jsPDF();
                doc.text("BITSA Club Certificate of Participation", 20, 30);
                doc.text("Awarded to : Student Name", 20, 50);
                doc.text("Event: Hackathon 2025", 20, 70);
                doc.save("BITSA_Certificate.pdf");
              }}
            >
              Download PDF
            </button>
          </div>
        </div>
      </section>
    </div>
  );
}
