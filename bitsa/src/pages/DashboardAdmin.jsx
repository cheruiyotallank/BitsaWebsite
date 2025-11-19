import React, { useEffect, useState } from "react";
import "../styles/dashboard.css";

export default function DashboardAdmin() {
  const [stats, setStats] = useState({ users: 0, events: 0, posts: 0 });
  const [users, setUsers] = useState([]);
  const [events, setEvents] = useState([]);
  const [form, setForm] = useState({ title: "", date: "", desc: "" });

  useEffect(() => {
    // Load mock stats + users
    const storedUsers = JSON.parse(localStorage.getItem("bitsaUsers")) || [
      { name: "Lynn Bitok", role: "admin", email: "admin@bitsa.com" },
      { name: "John Doe", role: "lecturer", email: "lecturer@bitsa.com" },
      { name: "Jane Student", role: "student", email: "student@bitsa.com" },
    ];
    setUsers(storedUsers);
    setStats({
      users: storedUsers.length,
      events: (JSON.parse(localStorage.getItem("bitsaEvents")) || []).length,
      posts: (JSON.parse(localStorage.getItem("bitsaBlogs")) || []).length,
    });
    const storedEvents = JSON.parse(localStorage.getItem("bitsaEvents")) || [];
    setEvents(storedEvents);
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const addEvent = (e) => {
    e.preventDefault();
    if (!form.title || !form.date) return;
    const updated = [...events, form];
    setEvents(updated);
    localStorage.setItem("bitsaEvents", JSON.stringify(updated));
    setForm({ title: "", date: "", desc: "" });
  };

  const deleteEvent = (index) => {
    const updated = events.filter((_, i) => i !== index);
    setEvents(updated);
    localStorage.setItem("bitsaEvents", JSON.stringify(updated));
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h2>Welcome, Admin 👑</h2>
      </div>

      <div className="dashboard-cards">
        <div className="dashboard-card">
          <h3>Total Users</h3>
          <p>{stats.users}</p>
        </div>
        <div className="dashboard-card">
          <h3>Events</h3>
          <p>{stats.events}</p>
        </div>
        <div className="dashboard-card">
          <h3>Blog Posts</h3>
          <p>{stats.posts}</p>
        </div>
      </div>

      <section className="user-table">
        <h3>User Management</h3>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
            </tr>
          </thead>
          <tbody>
            {users.map((u, i) => (
              <tr key={i}>
                <td>{u.name}</td>
                <td>{u.email}</td>
                <td>{u.role}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

      <section className="events-admin">
        <h3>Manage Events</h3>
        <form onSubmit={addEvent} className="event-form">
          <input
            name="title"
            placeholder="Event Title"
            value={form.title}
            onChange={handleChange}
            required
          />
          <input
            name="date"
            type="date"
            value={form.date}
            onChange={handleChange}
            required
          />
          <textarea
            name="desc"
            placeholder="Description"
            value={form.desc}
            onChange={handleChange}
          />
          <button type="submit">Add Event</button>
        </form>

        <div className="events-list">
          {events.length === 0 && <p>No events added yet.</p>}
          {events.map((ev, i) => (
            <div key={i} className="event-item">
              <h4>{ev.title}</h4>
              <p><strong>Date:</strong> {ev.date}</p>
              <p>{ev.desc}</p>
              <button onClick={() => deleteEvent(i)}>Delete</button>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
