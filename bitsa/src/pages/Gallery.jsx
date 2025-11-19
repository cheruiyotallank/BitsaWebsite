
import React, { useState, useEffect } from "react";
import axios from "axios";
import "../styles/gallery.css";

export default function Gallery() {
  const [galleryItems, setGalleryItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [active, setActive] = useState(null);

  useEffect(() => {
    async function fetchGalleryItems() {
      try {
        const response = await axios.get("/api/gallery");
        setGalleryItems(response.data);
      } catch (err) {
        setError("Failed to fetch gallery items.");
        console.error("Error fetching gallery items:", err);
      } finally {
        setLoading(false);
      }
    }
    fetchGalleryItems();
  }, []);

  const handleKeyDown = (e, item) => {
    if (e.key === "Enter") {
      setActive(item);
    }
  };

  const handleLightboxClose = () => {
    setActive(null);
  };

  const handleLightboxKeyDown = (e) => {
    if (e.key === "Escape") {
      handleLightboxClose();
    }
  };

  return (
    <section className="gallery-page">
      <h2>BITSA Moments</h2>
      {loading ? (
        <h2>Loading gallery...</h2>
      ) : error ? (
        <h2 style={{ color: "red" }}>{error}</h2>
      ) : (
        <div className="gallery-grid">
          {galleryItems.length === 0 ? (
            <p>No gallery items available yet.</p>
          ) : (
            galleryItems.map((item) => (
              <div key={item.id} className="gallery-item">
                <img
                  src={item.url}
                  alt={item.caption || "Gallery image"}
                  onClick={() => setActive(item)}
                  onKeyDown={(e) => handleKeyDown(e, item)}
                  role="button"
                  tabIndex="0"
                />
                {item.caption && (
                  <div className="gallery-caption">
                    {item.caption}
                  </div>
                )}
              </div>
            ))
          )}
        </div>
      )}

      {active && (
        <div className="lightbox" onClick={handleLightboxClose} onKeyDown={handleLightboxKeyDown} role="dialog" tabIndex="-1" aria-modal="true">
          <img src={active.url} alt={active.caption || "Gallery image"} />
          {active.caption && (
            <div className="lightbox-caption">
              {active.caption}
            </div>
          )}
        </div>
      )}
    </section>
  );
}
