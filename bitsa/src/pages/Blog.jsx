import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "../styles/blog.css";

export default function Blog() {
  const { id } = useParams();
  const [blog, setBlog] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchBlog() {
      try {
        const response = await axios.get(`/api/blogs/${id}`);
        setBlog(response.data);
      } catch (err) {
        setError("Failed to fetch blog post.");
        console.error("Error fetching blog post:", err);
      } finally {
        setLoading(false);
      }
    }
    fetchBlog();
  }, [id]);

  if (loading) return <div className="blog-detail-container">Loading blog post...</div>;
  if (error) return <div className="blog-detail-container" style={{ color: "red" }}>{error}</div>;
  if (!blog) return <div className="blog-detail-container">Blog not found.</div>;

  return (
    <div className="blog-detail-container">
      {blog.imageUrl && <img src={blog.imageUrl} alt={blog.title} className="blog-detail-image" />}
      <h1>{blog.title}</h1>
      <p className="blog-meta">
        By {blog.authorName || blog.author?.name || 'Anonymous'} on {new Date(blog.createdAt).toLocaleDateString()}
      </p>
      <div className="blog-content">
        {blog.content.split("\n").map((paragraph, index) => (
          <p key={index}>{paragraph}</p>
        ))}
      </div>
    </div>
  );
}
