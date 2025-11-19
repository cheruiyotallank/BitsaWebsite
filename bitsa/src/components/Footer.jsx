import React from "react";
import "../styles/footer.css";

export default function Footer() {
  return (
    <footer className="site-footer">
      <div className="container footer-inner">
        <div>
          <h4>BITSA Club</h4>
          <p>University of Eastern Afica, Baraton</p>
        </div>
        <div>
          <h5>Quick Links</h5>
          <ul>
            <li><a href="/">Home</a></li>
            <li><a href="/events">Events</a></li>
            <li><a href="/blogs">Blog</a></li>
          </ul>
        </div>
        <div>
          <h5>Follow</h5>
          <div className="socials">Instagram | Twitter | Facebook</div>
        </div>
      </div>
    </footer>
  );
}
