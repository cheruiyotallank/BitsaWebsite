import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import "../styles/blogs.css";

export default function Blogs() {
  const [blogs, setBlogs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [query, setQuery] = useState("");
  const [page, setPage] = useState(0);
  const [size] = useState(10);
  const [totalPages, setTotalPages] = useState(0);

  async function fetchBlogs(q = query, p = page) {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get("/api/blogs/search", { params: { q: q || undefined, page: p, size } });
      const data = response.data;
      setBlogs(Array.isArray(data.content) ? data.content : []);
      setTotalPages(data.totalPages || 0);
      setPage(data.number || 0);
    } catch (err) {
      setError("Failed to fetch blogs.");
      console.error("Error fetching blogs:", err);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchBlogs();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
    fetchBlogs(query, 0);
  };

  const prevPage = () => {
    if (page > 0) {
      fetchBlogs(query, page - 1);
    }
  };

  const nextPage = () => {
    if (page + 1 < totalPages) {
      fetchBlogs(query, page + 1);
    }
  };

  if (loading) return <section className="blogs-page"><h2>Loading blogs...</h2></section>;
  if (error) return <section className="blogs-page" style={{ color: "red" }}><h2>{error}</h2></section>;

  return (
    <section className="blogs-page">
      <h2>BITSA Blogs</h2>
      <form onSubmit={handleSearch} className="search-form">
        <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search blogs..." />
        <button type="submit">Search</button>
      </form>
      <div className="blogs-grid">
        {blogs.length === 0 ? (
          <p>No blogs available yet.</p>
        ) : (
          blogs.map((blog) => (
            <div key={blog.id} className="blog-card">
              {blog.imageUrl && <img src={blog.imageUrl} alt={blog.title} className="blog-image" />}
              <h3>{blog.title}</h3>
              <p><em>by {blog.authorName || blog.author?.name || 'Anonymous'} on {new Date(blog.createdAt).toLocaleDateString()}</em></p>
              <p>{(blog.content || "").slice(0, 150)}...</p>
              <Link to={`/blog/${blog.id}`} className="read-more">
                Read More
              </Link>
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
