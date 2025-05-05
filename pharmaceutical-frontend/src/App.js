import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProductCodePage from './pages/ProductCodePage';
import MedicationPage from './pages/MedicationPage';
import MedicationTypePage from './pages/MedicationTypePage';
import Navigation from './components/Navigation';

function App() {
  return (
    <Router>
      <Navigation />
      <Routes>
        <Route path="/product-codes" element={<ProductCodePage />} />
        <Route path="/medications" element={<MedicationPage />} />
        <Route path="/medication-types" element={<MedicationTypePage />} />
      </Routes>
    </Router>
  );
}

export default App;