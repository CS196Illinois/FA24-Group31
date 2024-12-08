"use client";

import React, { useState } from 'react';
import SwipeProfiles from './components/SwipeProfiles';
import FilterProfiles from './components/FilterProfiles'; // Importing FilterProfiles component
import classes from './style.module.css'; // Importing CSS file
import { HeaderSimple } from '../header';

/*
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
    age: 25,
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
    age: 28,
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
    age: 30,
  },
];
*/

/*
const profilesData = [
	{
		name: 'Alex Johnson',
		image: 'base64 string',
		age: 25,
		pronouns: [],
		bio: 'I love volibear',
		riotId: 'Alex#5678',
		discordId: 'AlexJ319',
		roles: ['Top', 'Support'],
		rank: 'Diamond',
	},
]
*/


const MatchingPage: React.FC = () => {
const [selectedRoles, setSelectedRoles] = useState<string[]>([]);
  const [selectedRanks, setSelectedRanks] = useState<string[]>([]);
  const [ageRange, setAgeRange] = useState<[number, number]>([18, 99]);
  const [filters, setFilters] = useState({ roles: [], rank: '', ageRange: [18, 99] });
  const [currentName, setCurrentName] = useState<string>('');
  const [currentImage, setCurrentImage] = useState<string>('');
  const [currentAge, setCurrentAge] = useState<number>(0);
  const [currentRank, setCurrentRank] = useState<string>('');
  const [currentPronouns, setCurrentPronouns] = useState<string[]>('');
  const [currentRoles, setCurrentRoles] = useState<string[]>('');
  const [currentBio, setCurrentBio] = useState<string>('');
  const [currentRiotId, setCurrentRiotId] = useState<string>('');
  const [currentDiscordId, setCurrentDiscordId] = useState<string>('');
  const [nextName, setNextName] = useState<string>('');
  const [nextImage, setNextImage] = useState<string>('');
  const [nextAge, setNextAge] = useState<number>(0);
  const [nextRank, setNextRank] = useState<string>('');
  const [nextPronouns, setNextPronouns] = useState<string[]>('');
  const [nextRoles, setNextRoles] = useState<string[]>('');
  const [nextBio, setNextBio] = useState<string>('');
  const [nextDiscordId, setNextDiscordId] = useState<string>('');

  let profilesData = [
	{
		name: currentName,
		image: currentImage,
		age: currentAge,
		bio: currentBio,
		pronouns: currentPronouns,
		riotId: currentRiotId,
		discordId: currentDiscordId,
		roles: currentRoles,
		rank: currentRank,
	},
	{
		name: nextName,
		image: nextImage,
		age: nextAge,
		pronouns: nextPronouns,
		bio: nextBio,
		riotId: currentRiotId,
		discordId: nextDiscordId,
		roles: nextRoles,
		rank: nextRank,
	},
];



  return (
    <div>
      <HeaderSimple />
      <div className={classes.homepageContainer}>
      	<FilterProfiles selectedRoles={selectedRoles} setSelectedRoles={setSelectedRoles} selectedRanks={selectedRanks} setSelectedRanks={setSelectedRanks} ageRange={ageRange} setAgeRange={setAgeRange} filters={filters} setFilters={setFilters} currentName={currentName} setCurrentName={setCurrentName} currentImage={currentImage} setCurrentImage={setCurrentImage} currentAge={currentAge} setCurrentAge={setCurrentAge} currentRank={currentRank} setCurrentRank={setCurrentRank} currentRoles={currentRoles} setCurrentRoles={setCurrentRoles} currentBio={currentBio} setCurrentBio={setCurrentBio} currentRiotId={currentRiotId} setCurrentRiotId={setCurrentRiotId} currentDiscordId={currentDiscordId} setCurrentDiscordId={setCurrentDiscordId} nextName={nextName} setNextName={setNextName} nextImage={nextImage} setNextImage={setNextImage} nextAge={nextAge} setNextAge={setNextAge} nextRank={nextRank} setNextRank={setNextRank} nextRoles={nextRoles} setNextRoles={setNextRoles} nextBio={nextBio} setNextBio={setNextBio} nextDiscordId={nextDiscordId} setNextDiscordId={setNextDiscordId} currentPronouns={currentPronouns} setCurrentPronouns={setCurrentPronouns} nextPronouns={nextPronouns} setNextPronouns={setNextPronouns} />
	<SwipeProfiles profiles={profilesData} filter={filters} />
      </div>
    </div>
  );
};

export default MatchingPage;
