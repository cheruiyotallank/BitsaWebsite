import React from "react";
import "../styles/hero.css";
import LetterGlitch from "./LetterGlitch";

export default function HeroSection() {
  return (
    <section className="hero-section">
      <LetterGlitch
        glitchSpeed={50}
        centerVignette={true}
        outerVignette={false}
        smooth={true}
      />
      <div className="hero-content">
        <h1 className="glitch" data-text="Welcome to BITSA">Welcome to BITSA</h1>
        <p className="hero-sub">Connecting Software Engineering, BBIT & Networking students at ISC.</p>
        <div className="hero-ctas">
          <a href="/login" className="btn">Join BITSA</a>
          <a href="/events" className="btn-ghost">Explore Events</a>
        </div>
      </div>
    </section>
  );
}
