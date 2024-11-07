"use client";

import React from 'react';
import SwipeProfiles from './components/SwipeProfiles';
import classes from './style.module.css'; // Importing CSS file
import { HeaderSimple } from '../header';

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
    <div>
      <HeaderSimple/>
    
      <div className={classes.homepageContainer}>
          <h1 className={classes.homepageTitle}>Swipe Profiles</h1>
          <SwipeProfiles profiles={profilesData} />
      </div>
    </div>
  );
};

export default MatchingPage;