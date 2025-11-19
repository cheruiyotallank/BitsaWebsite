import React, { useState } from "react";
import "../styles/lecturerDashboard.css";



export default function LecturerDashboard() {
  const [materials, setMaterials] = useState([]);
  const [announcements, setAnnouncements] = useState([]);
  const [events, setEvents] = useState([]);

  const handleAddMaterial = (e) => {
    e.preventDefault();
    const title = e.target.title.value;
    const description = e.target.description.value;
    const file = e.target.file.files[0];
    if (file) {
      const fileURL = URL.createObjectURL(file);
      setMaterials([...materials, { title, description, fileURL }]);
    }
    e.target.reset();
  };

  const handleAddAnnouncement = (e) => {
    e.preventDefault();
    const title = e.target.title.value;
    const content = e.target.content.value;
    setAnnouncements([...announcements, { title, content }]);
    e.target.reset();
  };

  const handleAddEvent = (e) => {
    e.preventDefault();
    const name = e.target.name.value;
    const date = e.target.date.value;
    const details = e.target.details.value;
    setEvents([...events, { name, date, details }]);
    e.target.reset();
  };

  return (
    <div className="lecturer-dashboard">
      <h2 className="lecturer-title">Lecturer Dashboard 🎓</h2>

      <div className="lecturer-grid">
        <div className="lecturer-card">
          <h3>📚 Upload Study Material</h3>
          <form onSubmit={handleAddMaterial}>
            <input type="text" name="title" placeholder="Material Title" required />
            <textarea name="description" placeholder="Short Description" required />
            <input type="file" name="file" accept=".pdf,.ppt,.mp4,.docx" required />
            <button type="submit">Upload</button>
          </form>
          <ul>
            {materials.map((m, i) => (
              <li key={i}>
                <strong>{m.title}</strong> — {m.description} <br />
                <a href={m.fileURL} target="_blank" rel="noopener noreferrer">View File</a>
              </li>
            ))}
          </ul>
        </div>

        <div className="lecturer-card">
          <h3>📢 Post Announcements</h3>
          <form onSubmit={handleAddAnnouncement}>
            <input type="text" name="title" placeholder="Announcement Title" required />
            <textarea name="content" placeholder="Content" required />
            <button type="submit">Post</button>
          </form>
          <ul>
            {announcements.map((a, i) => (
              <li key={i}>
                <strong>{a.title}</strong> — {a.content}
              </li>
            ))}
          </ul>
        </div>

        <div className="lecturer-card">
          <h3>🎤 Add Academic Events</h3>
          <form onSubmit={handleAddEvent}>
            <input type="text" name="name" placeholder="Event Name" required />
            <input type="date" name="date" required />
            <textarea name="details" placeholder="Event Details" required />
            <button type="submit">Add Event</button>
          </form>
          <ul>
            {events.map((e, i) => (
              <li key={i}>
                <strong>{e.name}</strong> — {e.date} <br />
                {e.details}
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}
