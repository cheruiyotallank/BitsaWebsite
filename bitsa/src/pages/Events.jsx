import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";
import "../styles/events.css";

export default function Events() {
  const { user } = useAuth();
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [query, setQuery] = useState("");
  const [page, setPage] = useState(0);
  const [size] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [creating, setCreating] = useState(false);
  const [form, setForm] = useState({
    title: "",
    description: "",
    dateTime: "",
    startTime: "",
    endTime: "",
    location: "",
    image: null
  });
  const isAdminOrLecturer = user?.role === "ADMIN" || user?.role === "LECTURER";

  async function fetchEvents(q = query, p = page) {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get("/api/events/search", { params: { q: q || undefined, page: p, size } });
      const data = response.data;
      setEvents(Array.isArray(data.content) ? data.content : []);
      setTotalPages(data.totalPages || 0);
      setPage(data.number || 0);
    } catch (err) {
      setError("Failed to fetch events.");
      console.error("Error fetching events:", err);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchEvents();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
    fetchEvents(query, 0);
  };

  const prevPage = () => {
    if (page > 0) {
      fetchEvents(query, page - 1);
    }
  };

  const nextPage = () => {
    if (page + 1 < totalPages) {
      fetchEvents(query, page + 1);
    }
  };

  const handleFormChange = (e) => {
    const { name, value, files } = e.target;
    if (name === "image") {
      setForm({ ...form, image: files[0] });
    } else {
      setForm({ ...form, [name]: value });
    }
  };

  const createEvent = async (e) => {
    e.preventDefault();
    if (!form.title || !form.description || !form.dateTime) return;

    setCreating(true);
    try {
      const eventData = {
        title: form.title,
        description: form.description,
        dateTime: new Date(form.dateTime).toISOString(),
        startTime: form.startTime ? new Date(form.startTime).toISOString() : null,
        endTime: form.endTime ? new Date(form.endTime).toISOString() : null,
        location: form.location || null
      };

      const response = await axios.post("/api/events", eventData);
      const newEvent = response.data;

      if (form.image) {
        const formData = new FormData();
        formData.append("file", form.image);
        await axios.post(`/api/events/${newEvent.id}/image`, formData, {
          headers: { "Content-Type": "multipart/form-data" }
        });
      }

      setForm({
        title: "",
        description: "",
        dateTime: "",
        startTime: "",
        endTime: "",
        location: "",
        image: null
      });
      setShowCreateForm(false);
      fetchEvents(); // Refresh list
    } catch (error) {
      console.error("Error creating event:", error);
      alert("Failed to create event");
    } finally {
      setCreating(false);
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

  if (loading) return <section className="events-page"><h2>Loading events...</h2></section>;
  if (error) return <section className="events-page" style={{ color: "red" }}><h2>{error}</h2></section>;

  return (
    <section className="events-page">
      <h2>Upcoming Events</h2>
      <form onSubmit={handleSearch} className="search-form">
        <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search events..." />
        <button type="submit">Search</button>
      </form>
      <div className="events-grid">
        {events.length === 0 ? (
          <p>No events available yet.</p>
        ) : (
          events.map((event) => (
            <div key={event.id} className="event-card">
              {event.imageUrl && <img src={event.imageUrl} alt={event.title} className="event-image" />}
              <h3>{event.title}</h3>
              <p><strong>Date:</strong> {event.dateTime ? new Date(event.dateTime).toLocaleDateString() : "N/A"}</p>
              {event.startTime && <p><strong>Start Time:</strong> {new Date(event.startTime).toLocaleTimeString()}</p>}
              {event.endTime && <p><strong>End Time:</strong> {new Date(event.endTime).toLocaleTimeString()}</p>}
              <p>{event.description}</p>
              {event.location && <p><strong>Location:</strong> {event.location}</p>}
            </div>
          ))
        )}
      </div>

      <div className="pagination">
        <button onClick={prevPage} disabled={page <= 0}>Prev</button>
        <span> Page {page + 1} of {totalPages} </span>
        <button onClick={nextPage} disabled={page + 1 >= totalPages}>Next</button>
      </div>
    </section>
  );
}
