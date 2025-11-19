import React from "react";
import "../styles/studentDashboard.css";



export default function StudentDashboard() {
  const announcements = [
    { title: "Hackathon 2025", content: "Join our annual BITSA Hackathon on Dec 5th!" },
    { title: "Networking Bootcamp", content: "Sign up for the Cisco networking workshop this week!" },
  ];

  const events = [
    { name: "AI Seminar", date: "2025-12-01", description: "Introduction to Machine Learning" },
    { name: "Cloud Masterclass", date: "2025-12-08", description: "AWS, Azure, and GCP Overview" },
  ];

  const materials = [
    { title: "React Basics", link: "#" },
    { title: "Database Design", link: "#" },
  ];

  const achievements = [
    { name: "Hackathon Participant", date: "2024" },
    { name: "Web Dev Module Complete", date: "2025" },
  ];

  return (
    <div className="student-dashboard">
      <h2 className="student-title">Welcome to your BITSA Dashboard 🎓</h2>
      <p className="student-subtitle">Stay up to date with your academic journey and events!</p>

      <div className="student-section">
        <h3>📢 Announcements</h3>
        <ul>
          {announcements.map((a, i) => (
            <li key={i}><strong>{a.title}</strong>: {a.content}</li>
          ))}
        </ul>
      </div>

      <div className="student-section">
        <h3>📅 Upcoming Events</h3>
        <ul>
          {events.map((e, i) => (
            <li key={i}>
              <strong>{e.name}</strong> — {e.date} <br /> {e.description}
            </li>
          ))}
        </ul>
      </div>

      <div className="student-section">
        <h3>📚 Study Materials</h3>
        <ul>
          {materials.map((m, i) => (
            <li key={i}>
              <a href={m.link}>{m.title}</a>
            </li>
          ))}
        </ul>
      </div>

      <div className="student-section">
        <h3>🏅 Achievements</h3>
        <ul>
          {achievements.map((a, i) => (
            <li key={i}><strong>{a.name}</strong> — {a.date}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}
