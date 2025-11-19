import React, { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import axios from "axios";
import "../styles/adminDashboard.css";

export default function AdminDashboard() {
  const [stats, setStats] = useState({ users: 0, events: 0, posts: 0 });
  const [blogs, setBlogs] = useState([]);
  const [galleryItems, setGalleryItems] = useState([]);
  const [form, setForm] = useState({ title: "", content: "", imageUrl: "", authorName: "" });
  const [galleryForm, setGalleryForm] = useState({ file: null, caption: "" });
  const [loading, setLoading] = useState(false);
  const [galleryLoading, setGalleryLoading] = useState(false);
  const { user } = useAuth();

  useEffect(() => {
    fetchStats();
    fetchBlogs();
    fetchGalleryItems();
  }, []);

  const fetchStats = async () => {
    try {
      const response = await axios.get("/api/stats/admin");
      setStats(response.data);
    } catch (error) {
      console.error("Error fetching stats:", error);
    }
  };

  const fetchBlogs = async () => {
    try {
      const response = await axios.get("/api/blogs");
      setBlogs(response.data);
    } catch (error) {
      console.error("Error fetching blogs:", error);
    }
  };

  const fetchGalleryItems = async () => {
    try {
      const response = await axios.get("/api/gallery");
      setGalleryItems(response.data);
    } catch (error) {
      console.error("Error fetching gallery items:", error);
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
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
        authorName: form.authorName || user?.name || "Admin"
      };

      await axios.post("/api/blogs", blogData);
      setForm({ title: "", content: "", imageUrl: "", authorName: "" });
      fetchBlogs(); // Refresh the list
      fetchStats(); // Refresh stats
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
      fetchStats(); // Refresh stats
    } catch (error) {
      console.error("Error deleting blog:", error);
      alert("Failed to delete blog post");
    }
  };

  const handleGalleryChange = (e) => {
    setGalleryForm({ ...galleryForm, [e.target.name]: e.target.value });
  };

  const addGalleryItem = async (e) => {
    e.preventDefault();
    if (!galleryForm.file) return;

    setGalleryLoading(true);
    try {
      const formData = new FormData();
      formData.append("file", galleryForm.file);
      if (galleryForm.caption) {
        formData.append("caption", galleryForm.caption);
      }

      await axios.post("/api/gallery", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      setGalleryForm({ file: null, caption: "" });
      fetchGalleryItems(); // Refresh the list
    } catch (error) {
      console.error("Error uploading gallery item:", error);
      alert("Failed to upload gallery item");
    } finally {
      setGalleryLoading(false);
    }
  };

  const deleteGalleryItem = async (itemId) => {
    if (!confirm("Are you sure you want to delete this gallery item?")) return;

    try {
      await axios.delete(`/api/gallery/${itemId}`);
      fetchGalleryItems(); // Refresh the list
    } catch (error) {
      console.error("Error deleting gallery item:", error);
      alert("Failed to delete gallery item");
    }
  };

  return (
    <div className="admin-dashboard">
      <h2 className="admin-title">Admin Dashboard 🧭</h2>
      <p className="admin-subtitle">Overview of BITSA platform statistics and management.</p>

      <div className="admin-stats">
        <div className="stat-card">
          <h3>Total Users</h3>
          <p>{stats.users || 0}</p>
        </div>
        <div className="stat-card">
          <h3>Events</h3>
          <p>{stats.events || 0}</p>
        </div>
        <div className="stat-card">
          <h3>Blog Posts</h3>
          <p>{stats.posts || 0}</p>
        </div>
      </div>

      <div className="admin-section">
        <h3>📝 Manage Blog Posts</h3>
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
          <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
            <input
              name="imageUrl"
              placeholder="Image URL (optional)"
              value={form.imageUrl}
              onChange={handleChange}
              style={{ flex: 1 }}
            />
            <span style={{ color: 'var(--text-muted, #666)', fontSize: '0.9rem' }}>OR</span>
            <input
              type="file"
              accept="image/*"
              onChange={(e) => {
                const file = e.target.files[0];
                if (file) {
                  // For now, just show the file name. In a real app, you'd upload to server
                  setForm({ ...form, imageUrl: `📎 ${file.name}` });
                }
              }}
              style={{ flex: 1 }}
            />
          </div>
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
          {blogs.length === 0 && <p>No blog posts yet.</p>}
          {blogs.map((b) => (
            <div key={b.id} className="blog-item">
              {b.imageUrl && !b.imageUrl.startsWith('📎') && (
                <img
                  src={b.imageUrl}
                  alt={b.title}
                  style={{
                    width: '100%',
                    height: '200px',
                    objectFit: 'cover',
                    borderRadius: '8px',
                    marginBottom: '1rem'
                  }}
                />
              )}
              {b.imageUrl && b.imageUrl.startsWith('📎') && (
                <div style={{
                  width: '100%',
                  height: '200px',
                  background: 'var(--bg-secondary, #f8f9fa)',
                  borderRadius: '8px',
                  marginBottom: '1rem',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  border: '2px dashed var(--text-muted, #ccc)'
                }}>
                  <span style={{ fontSize: '1.2rem', color: 'var(--text-muted, #666)' }}>
                    {b.imageUrl}
                  </span>
                </div>
              )}
              <h4>{b.title}</h4>
              <p style={{ fontStyle: 'italic', color: 'var(--text-muted, #666)', margin: '0.5rem 0' }}>
                by {b.authorName || b.author?.name || 'Anonymous'} on {new Date(b.createdAt).toLocaleDateString()}
              </p>
              <p style={{ margin: '0.5rem 0', lineHeight: '1.5' }}>
                {b.content.slice(0, 150)}...
              </p>
              <button
                onClick={() => deleteBlog(b.id)}
                style={{
                  background: '#dc3545',
                  color: 'white',
                  border: 'none',
                  padding: '0.5rem 1rem',
                  borderRadius: '4px',
                  cursor: 'pointer',
                  fontSize: '0.85rem',
                  marginTop: '1rem',
                  transition: 'background 0.2s'
                }}
                onMouseOver={(e) => e.target.style.background = '#c82333'}
                onMouseOut={(e) => e.target.style.background = '#dc3545'}
              >
                Delete
              </button>
            </div>
          ))}
        </div>
      </div>

      <div className="admin-section">
        <h3>🖼️ Manage Gallery</h3>
        <form onSubmit={addGalleryItem} className="blog-form">
          <input
            type="file"
            accept="image/*"
            onChange={(e) => {
              const file = e.target.files[0];
              if (file) {
                setGalleryForm({ ...galleryForm, file });
              }
            }}
            required
          />
          <input
            name="caption"
            placeholder="Image Caption (optional)"
            value={galleryForm.caption}
            onChange={handleGalleryChange}
          />
          <button type="submit" disabled={galleryLoading}>
            {galleryLoading ? "Uploading..." : "Upload Image"}
          </button>
        </form>

        <div className="blog-list">
          {galleryItems.length === 0 && <p>No gallery items yet.</p>}
          {galleryItems.map((item) => (
            <div key={item.id} className="blog-item">
              <img
                src={item.url}
                alt={item.caption || "Gallery image"}
                style={{
                  width: '100%',
                  height: '200px',
                  objectFit: 'cover',
                  borderRadius: '8px',
                  marginBottom: '1rem'
                }}
              />
              {item.caption && (
                <p style={{ fontStyle: 'italic', color: 'var(--text-muted, #666)', margin: '0.5rem 0' }}>
                  {item.caption}
                </p>
              )}
              <p style={{ fontSize: '0.85rem', color: 'var(--text-muted, #666)', margin: '0.5rem 0' }}>
                Uploaded on {new Date(item.uploadedAt).toLocaleDateString()}
              </p>
              <button
                onClick={() => deleteGalleryItem(item.id)}
                style={{
                  background: '#dc3545',
                  color: 'white',
                  border: 'none',
                  padding: '0.5rem 1rem',
                  borderRadius: '4px',
                  cursor: 'pointer',
                  fontSize: '0.85rem',
                  marginTop: '1rem',
                  transition: 'background 0.2s'
                }}
                onMouseOver={(e) => e.target.style.background = '#c82333'}
                onMouseOut={(e) => e.target.style.background = '#dc3545'}
              >
                Delete
              </button>
            </div>
          ))}
        </div>
      </div>

      <div className="admin-section">
        <h3>📊 Platform Analytics</h3>
        <div className="chart-placeholder">
          <p>Chart Area (Future Integration with Recharts or Chart.js)</p>
        </div>
      </div>
    </div>
  );
}
