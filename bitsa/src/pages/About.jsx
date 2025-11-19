import React from "react";
import "../styles/about.css";

export default function About() {
  return (
    <section className="section">
      <div className="about-content">
        <div className="about-hero">
          <img
            src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=800&h=400&fit=crop"
            alt="BITSA Club Members"
            className="about-hero-image"
          />
          <h2 className="hero-title">BITSA CLUB</h2>
          <p className="hero-subtitle">Building Innovative Tech Solutions Association</p>
        </div>

        <div className="about-cards">
          <div className="about-card">
            <div className="card-icon"></div>
            <h3>Our Mission</h3>
            <p>
              To empower students with cutting-edge technical skills, industry connections, and
              entrepreneurial mindset through hands-on projects, workshops, and networking opportunities.
            </p>
          </div>

          <div className="about-card">
            <div className="card-icon"></div>
            <h3>What We Offer</h3>
            <div className="card-list">
              <div className="list-item">
                <span className="bullet"></span>
                <span>Technical Workshops </span>
              </div>
              <div className="list-item">
                <span className="bullet"></span>
                <span>Hackathons & Coding Competitions</span>
              </div>
              <div className="list-item">
                <span className="bullet"></span>
                <span>Industry Networking Events</span>
              </div>
              <div className="list-item">
                <span className="bullet"></span>
                <span>Study Groups for BBIT, Software Engineering & Networking</span>
              </div>
              <div className="list-item">
                <span className="bullet"></span>
                <span>Guest Speakers from Tech Industry</span>
              </div>
              <div className="list-item">
                <span className="bullet"></span>
                <span>Community Projects & Outreach</span>
              </div>
            </div>
          </div>

          <div className="about-card">
            <div className="card-icon"></div>
            <h3>Our Values</h3>
            <div className="values-grid">
              <div className="value-item">
                <div className="value-icon"></div>
                <h5>Innovation</h5>
                <p>Embracing new technologies and creative problem-solving</p>
              </div>
              <div className="value-item">
                <div className="value-icon"></div>
                <h5>Collaboration</h5>
                <p>Working together to achieve greater outcomes</p>
              </div>
              <div className="value-item">
                <div className="value-icon"></div>
                <h5>Inclusion</h5>
                <p>Welcoming all students regardless of background or experience level</p>
              </div>
              <div className="value-item">
                <div className="value-icon"></div>
                <h5>Growth</h5>
                <p>Continuous learning and professional development</p>
              </div>
            </div>
          </div>

          <div className="about-card">
            <div className="card-icon"></div>
            <h3>Join Our Community</h3>
            <p>
              Whether you're a complete beginner or an experienced developer, BITSA welcomes you!
              Join us in building the future of technology and creating lasting connections in the tech community.
            </p>
            <div className="join-cta">
              <span>Ready to get started?</span>
              <button className="join-button">Join BITSA Today</button>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
