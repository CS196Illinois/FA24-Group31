'use client';

import React, { useState } from 'react';
import SwipeProfiles from './components/SwipeProfiles';
import FilterProfiles from './components/FilterProfiles';
import classes from './style.module.css';
import { HeaderSimple } from '../header';

// Define interface for the page component
interface MatchingPageProps {}

const MatchingPage: React.FC<MatchingPageProps> = () => {
  // State declarations
  const [selectedRoles, setSelectedRoles] = useState<string[]>([]);
  const [selectedRanks, setSelectedRanks] = useState<string[]>([]);
  const [ageRange, setAgeRange] = useState<[number, number]>([18, 99]);
  const [filters, setFilters] = useState({ roles: [], rank: '', ageRange: [18, 99] });
  const [currentName, setCurrentName] = useState<string>('');
  const [currentImage, setCurrentImage] = useState<string>('');
  const [currentAge, setCurrentAge] = useState<number>(0);
  const [currentRank, setCurrentRank] = useState<string>('');
  const [currentPronouns, setCurrentPronouns] = useState<string[]>([]);
  const [currentRoles, setCurrentRoles] = useState<string[]>([]);
  const [currentBio, setCurrentBio] = useState<string>('');
  const [currentRiotId, setCurrentRiotId] = useState<string>('');
  const [currentDiscordId, setCurrentDiscordId] = useState<string>('');
  const [nextName, setNextName] = useState<string>('');
  const [nextImage, setNextImage] = useState<string>('');
  const [nextAge, setNextAge] = useState<number>(0);
  const [nextRank, setNextRank] = useState<string>('');
  const [nextPronouns, setNextPronouns] = useState<string[]>([]);
  const [nextRoles, setNextRoles] = useState<string[]>([]);
  const [nextBio, setNextBio] = useState<string>('');
  const [nextDiscordId, setNextDiscordId] = useState<string>('');



  const profilesData = [
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
        <FilterProfiles
          selectedRoles={selectedRoles}
          setSelectedRoles={setSelectedRoles}
          selectedRanks={selectedRanks}
          setSelectedRanks={setSelectedRanks}
          ageRange={ageRange}
          setAgeRange={setAgeRange}
          filters={filters}
          setFilters={setFilters}
          profilesData={profilesData}
        />
      </div>
    </div>
  );
};

// Make sure to export the component as default
export default MatchingPage;
