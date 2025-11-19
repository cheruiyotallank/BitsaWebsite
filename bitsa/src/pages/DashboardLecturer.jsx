import React, { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import axios from "axios";
import "../styles/dashboard.css";
import "../styles/events.css";

export default function DashboardLecturer() {
  const [blogs, setBlogs] = useState([]);
  const [form, setForm] = useState({ title: "", content: "", imageUrl: "", authorName: "" });
  const [loading, setLoading] = useState(false);
  const [events, setEvents] = useState([]);
  const [eventForm, setEventForm] = useState({
    title: "",
    description: "",
    dateTime: "",
    startTime: "",
    endTime: "",
    location: "",
    image: null
  });
  const [creatingEvent, setCreatingEvent] = useState(false);
  const { user } = useAuth();

  useEffect(() => {
    fetchBlogs();
    fetchEvents();
  }, []);

  const fetchBlogs = async () => {
    try {
      const response = await axios.get("/api/blogs");
      setBlogs(response.data);
    } catch (error) {
      console.error("Error fetching blogs:", error);
    }
  };

  const fetchEvents = async () => {
    try {
      const response = await axios.get("/api/events");
      setEvents(response.data);
    } catch (error) {
      console.error("Error fetching events:", error);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleEventFormChange = (e) => {
    const { name, value, files } = e.target;
    if (name === "image") {
      setEventForm({ ...eventForm, image: files[0] });
    } else {
      setEventForm({ ...eventForm, [name]: value });
    }
  };

  const addBlog = async (e) => {
    e.preventDefault();
    if (!form.title || !form.content) return;

    setLoading(true);
    try {
      const blogData = {
        title: form.title,
        content: form.content,
        imageUrl: form.imageUrl || null,
        authorName: form.authorName || user?.name || "Lecturer"
      };

      await axios.post("/api/blogs", blogData);
      setForm({ title: "", content: "", imageUrl: "", authorName: "" });
      fetchBlogs(); // Refresh the list
    } catch (error) {
      console.error("Error creating blog:", error);
      alert("Failed to create blog post");
    } finally {
      setLoading(false);
    }
  };

  const deleteBlog = async (blogId) => {
    if (!confirm("Are you sure you want to delete this blog post?")) return;

    try {
      await axios.delete(`/api/blogs/${blogId}`);
      fetchBlogs(); // Refresh the list
    } catch (error) {
      console.error("Error deleting blog:", error);
      alert("Failed to delete blog post");
    }
  };

  const createEvent = async (e) => {
    e.preventDefault();
    if (!eventForm.title || !eventForm.description || !eventForm.dateTime) return;

    setCreatingEvent(true);
    try {
      const eventData = {
        title: eventForm.title,
        description: eventForm.description,
        dateTime: new Date(eventForm.dateTime).toISOString(),
        startTime: eventForm.startTime ? new Date(eventForm.startTime).toISOString() : null,
        endTime: eventForm.endTime ? new Date(eventForm.endTime).toISOString() : null,
        location: eventForm.location || null
      };

      const response = await axios.post("/api/events", eventData);
      const newEvent = response.data;

      if (eventForm.image) {
        const formData = new FormData();
        formData.append("file", eventForm.image);
        await axios.post(`/api/events/${newEvent.id}/image`, formData, {
          headers: { "Content-Type": "multipart/form-data" }
        });
      }

      setEventForm({
        title: "",
        description: "",
        dateTime: "",
        startTime: "",
        endTime: "",
        location: "",
        image: null
      });
      fetchEvents(); // Refresh list
    } catch (error) {
      console.error("Error creating event:", error);
      alert("Failed to create event");
    } finally {
      setCreatingEvent(false);
    }
  };

  const deleteEvent = async (eventId) => {
    if (!confirm("Are you sure you want to delete this event?")) return;

    try {
      await axios.delete(`/api/events/${eventId}`);
      fetchEvents(); // Refresh list
    } catch (error) {
      console.error("Error deleting event:", error);
      alert("Failed to delete event");
    }
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h2>Welcome, {user?.name} 🎓 (Lecturer)</h2>
      </div>

      <section className="blog-admin">
        <h3>Manage Blog Posts</h3>
        <form onSubmit={addBlog} className="blog-form">
          <input
            name="title"
            placeholder="Blog Title"
            value={form.title}
            onChange={handleChange}
            required
          />
          <textarea
            name="content"
            placeholder="Write your blog content..."
            value={form.content}
            onChange={handleChange}
            required
          />
          <input
            name="imageUrl"
            placeholder="Image URL (optional)"
            value={form.imageUrl}
            onChange={handleChange}
          />
          <input
            name="authorName"
            placeholder="Author Name"
            value={form.authorName}
            onChange={handleChange}
          />
          <button type="submit" disabled={loading}>
            {loading ? "Publishing..." : "Publish Blog"}
          </button>
        </form>

        <div className="blog-list">
          {blogs.length === 0 && <p>No posts yet.</p>}
          {blogs.map((b) => (
            <div key={b.id} className="blog-item">
              <h4>{b.title}</h4>
              <p><em>by {b.authorName || b.author?.name || 'Anonymous'} on {new Date(b.createdAt).toLocaleDateString()}</em></p>
              <p>{b.content.slice(0, 100)}...</p>
              <button onClick={() => deleteBlog(b.id)}>Delete</button>
            </div>
          ))}
        </div>
      </section>

      <section className="event-admin">
        <h3>Manage Events</h3>
        <form onSubmit={createEvent} className="event-form">
          <input
            name="title"
            placeholder="Event Title"
            value={eventForm.title}
            onChange={handleEventFormChange}
            required
          />
          <textarea
            name="description"
            placeholder="Event Description"
            value={eventForm.description}
            onChange={handleEventFormChange}
            required
          />
          <input
            name="dateTime"
            type="datetime-local"
            value={eventForm.dateTime}
            onChange={handleEventFormChange}
            required
          />
          <input
            name="startTime"
            type="datetime-local"
            placeholder="Start Time (optional)"
            value={eventForm.startTime}
            onChange={handleEventFormChange}
          />
          <input
            name="endTime"
            type="datetime-local"
            placeholder="End Time (optional)"
            value={eventForm.endTime}
            onChange={handleEventFormChange}
          />
          <input
            name="location"
            placeholder="Location (optional)"
            value={eventForm.location}
            onChange={handleEventFormChange}
          />
          <input
            name="image"
            type="file"
            accept="image/*"
            onChange={handleEventFormChange}
          />
          <button type="submit" disabled={creatingEvent}>
            {creatingEvent ? "Creating..." : "Create Event"}
          </button>
        </form>

        <div className="event-list">
          {events.length === 0 && <p>No events yet.</p>}
          {events.map((event) => (
            <div key={event.id} className="event-item">
              <h4>{event.title}</h4>
              <p><strong>Date:</strong> {event.dateTime ? new Date(event.dateTime).toLocaleDateString() : "N/A"}</p>
              {event.startTime && <p><strong>Start Time:</strong> {new Date(event.startTime).toLocaleTimeString()}</p>}
              {event.endTime && <p><strong>End Time:</strong> {new Date(event.endTime).toLocaleTimeString()}</p>}
              <p>{event.description.slice(0, 100)}...</p>
              <button onClick={() => deleteEvent(event.id)}>Delete</button>
            </div>
          ))
        </div>
      </section>
    </div>
  );
}
