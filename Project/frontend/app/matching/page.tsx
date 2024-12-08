"use client";

import React from 'react';
import SwipeProfiles from './components/SwipeProfiles';
import classes from './style.module.css'; // Importing CSS file
import { HeaderSimple } from '../header';

const profilesData = [
  {
    name: 'Alex Johnson',
    image: '/images/Alex.jpg',
    bio: 'A versatile gamer and tech enthusiast.',
    pronouns: 'They/Them',
    riotId: 'Alex#5678',
    discordId: 'AlexJ#1234',
    description: 'Alex enjoys exploring new games and pushing their limits in competitive play. A strong advocate for inclusivity in gaming.',
    roles: ['/images/role_tank.png', '/images/role_support.png'],
    rank: {
      title: 'Diamond',
      image: '/images/diamond_rank.png',
    },
  },
  {
    name: 'Jamie Lee',
    image: '/images/Jamie.jpg',
    bio: 'Strategist and problem solver.',
    pronouns: 'She/Her',
    riotId: 'Jamie#9999',
    discordId: 'JamieL#5678',
    description: 'Jamie thrives in team-based games where strategy and communication are key. Outside gaming, she enjoys coding and solving puzzles.',
    roles: ['/images/role_dps.png', '/images/role_support.png'],
    rank: {
      title: 'Platinum',
      image: '/images/platinum_rank.png',
    },
  },
  {
    name: 'Chris Taylor',
    image: '/images/Chris.jpg',
    bio: 'Hardcore gamer with a knack for mechanics.',
    pronouns: 'He/Him',
    riotId: 'Chris#3210',
    discordId: 'ChrisT#9876',
    description: 'Chris is known for mastering complex game mechanics and helping others improve. He is a respected member of his gaming community.',
    roles: ['/images/role_tank.png', '/images/role_dps.png'],
    rank: {
      title: 'Gold',
      image: '/images/gold_rank.png',
    },
  },
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