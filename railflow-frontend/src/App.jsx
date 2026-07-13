import { useState } from 'react'

function App() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center p-4">
      <div className="glass-card p-12 rounded-2xl max-w-2xl w-full text-center space-y-6">
        <h1 className="text-5xl font-extrabold tracking-tight">
          Welcome to <span className="gradient-text">RailFlow</span>
        </h1>
        <p className="text-railflow-muted text-lg">
          The next-generation railway reservation platform powered by Spring Boot, Redis, Kafka, and React.
        </p>
        
        <div className="grid grid-cols-2 gap-4 mt-8">
          <button className="bg-railflow-primary hover:bg-blue-600 text-white px-6 py-3 rounded-lg font-medium transition-all shadow-lg shadow-blue-500/20">
            Search Trains
          </button>
          <button className="bg-transparent border border-railflow-border hover:bg-railflow-border text-white px-6 py-3 rounded-lg font-medium transition-all">
            Admin Dashboard
          </button>
        </div>
      </div>
    </div>
  )
}

export default App
