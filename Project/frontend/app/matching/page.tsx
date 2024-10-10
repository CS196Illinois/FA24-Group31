"use client";

import React from 'react';
import SwipeProfiles from '../../components/swipe';

const HomePage: React.FC = () => {
  const profiles = ['Profile 1', 'Profile 2', 'Profile 3'];

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h1 className="text-4xl font-bold mb-8">Swipe Profiles</h1>
      <SwipeProfiles profiles={profiles} />
    </div>
  );
};

export default HomePage;
