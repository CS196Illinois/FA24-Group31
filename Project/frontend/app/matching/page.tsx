"use client";

import React from 'react';
import SwipeProfiles from '../../components/SwipeProfiles';

const profilesData = [
  {
    name: 'Person A',
    image: '/images/Person_A.jpg',
    bio: 'Lorem ipsum odor amet, consectetuer adipiscing elit.',
  },
  {
    name: 'Person B',
    image: '/images/Person_B.jpg',
    bio: 'Lorem ipsum odor amet, consectetuer adipiscing elit.',
  },
  {
    name: 'Person C',
    image: '/images/Person_C.jpg',
    bio: 'Lorem ipsum odor amet, consectetuer adipiscing elit.',
  },
  // Add more profiles as needed
];


const profilesData = [
  {
    name: 'Person A',
    image: '/images/Person_A.jpg',
    bio: 'Lorem ipsum odor amet, consectetuer adipiscing elit.',
  },
  {
    name: 'Person B',
    image: '/images/Person_B.jpg',
    bio: 'Lorem ipsum odor amet, consectetuer adipiscing elit.',
  },
  {
    name: 'Person C',
    image: '/images/Person_C.jpg',
    bio: 'Lorem ipsum odor amet, consectetuer adipiscing elit.',
  },
  // Add more profiles as needed
];

const MatchingPage: React.FC = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h1 className="text-4xl font-bold mb-8">Swipe Profiles</h1>
      <SwipeProfiles profiles={profilesData} />
    </div>
  );
};

export default MatchingPage;
