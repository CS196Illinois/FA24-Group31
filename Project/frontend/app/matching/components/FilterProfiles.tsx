'use client';

import React, { useState, useEffect } from 'react';
import SwipeProfiles from './SwipeProfiles';
import { MantineProvider, Container, RangeSlider, MultiSelect, Button, Text } from '@mantine/core';

const FilterProfiles: React.FC = ({ selectedRoles, setSelectedRoles, selectedRanks, setSelectedRanks, ageRange, setAgeRange, filters, setFilters, currentName, setCurrentName, currentImage, setCurrentImage, currentAge, setCurrentAge, currentRank, setCurrentRank, currentRoles, setCurrentRoles, currentBio, setCurrentBio, currentRiotId, setCurrentRiotId, currentDiscordId, setCurrentDiscordId, nextName, setNextName, nextImage, setNextImage, nextAge, setNextAge, nextRank, setNextRank, nextRoles, setNextRoles, nextBio, setNextBio, nextDiscordId, setNextDiscordId, currentPronouns, setCurrentPronouns, nextPronouns, setNextPronouns }) => {
  const [token, setToken] = useState<string>('');
  /*
  const [selectedRoles, setSelectedRoles] = useState<string[]>([]);
  const [selectedRanks, setSelectedRanks] = useState<string[]>([]);
  const [ageRange, setAgeRange] = useState<[number, number]>([18, 99]);
  const [filters, setFilters] = useState({ roles: [], rank: '', ageRange: [18, 99] });
  const [currentName, setCurrentName] = useState<string>('');
  const [currentImage, setCurrentImage] = useState<string>('');
  const [currentAge, setCurrentAge] = useState<number>(0);
  const [currentRank, setCurrentRank] = useState<string>('');
  const [currentRoles, setCurrentRoles] = useState<string[]>('');
  const [currentBio, setCurrentBio] = useState<string>('');
  const [currentRiotId, setCurrentRiotId] = useState<string>('');
  const [currentDiscordId, setCurrentDiscordId] = useState<string>('');
  const [nextName, setNextName] = useState<string>('');
  const [nextImage, setNextImage] = useState<string>('');
  const [nextAge, setNextAge] = useState<number>(0);
  const [nextRank, setNextRank] = useState<string>('');
  const [nextRoles, setNextRoles] = useState<string[]>('');
  const [nextBio, setNextBio] = useState<string>('');
  const [nextDiscordId, setNextDiscord] = useState<string>('');
  */

  useEffect(() => {
	  if (typeof window !== 'undefined') {
		  setToken(localStorage.getItem('sessionToken'));
	  }
  });

  function applyFilter() {
		const jsonBody = JSON.stringify({
			roles: selectedRoles,
			ranks: selectedRanks,
			minAge: ageRange[0],
			maxAge: ageRange[1],
			sessionToken: token
		});
		fetch('http://10.195.197.251:8080/api/v1/update_prefs', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: jsonBody
		})
		.then((res) => res.json())
		.then((data) => {
			console.log(jsonBody);
			console.log(data);
		})
		.catch((err) => {
			console.error(err);
		});
		fetch('http://10.195.197.251:8080/api/v1/get_next_user', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: token
		})
		.then((res) => res.json())
		.then((data) => {
			console.log(data);
			setNextName(data.name);
			setNextImage(data.image);
			setNextAge(data.age);
			setNextRank(data.rank);
			setNextRoles(data.roles);
			setNextBio(data.bio);
			setNextRiotId(data.riotId);
			setNextPronouns(data.pronouns);
		})
		.catch((err) => {
			console.error(err);
		})
	}

return (
	<>
    <MantineProvider>
    	<Container style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'start', width: '30%', transform: 'translateY(-6rem)' }}>
		<Text size="sm" mt="xl">Age range</Text>
    		<RangeSlider
			minRange={1}
    			min={18}
    			max={99}
    			step={1}
    			value={ageRange}
    			onChangeEnd={setAgeRange}
			style={{ width: '100%'}}
    		/>
		<MultiSelect
		  label="Selected Roles"
		  placeholder="Enter your roles"
		  value={selectedRoles}
		  onChange={setSelectedRoles}
		  data={['Top', 'Jungle', 'Mid', 'ADC', 'Support']}
		  style={{ width: '100%' }}
		/>
		<MultiSelect
		  label="Selected Ranks"
		  placeholder="Enter your ranks"
		  value={selectedRanks}
		  onChange={setSelectedRanks}
		  data={['Iron', 'Bronze', 'Silver', 'Gold', 'Platinum', 'Emerald', 'Diamond', 'Master', 'Grandmaster', 'Challenger']}
		  style={{ marginBottom: '1rem', width: '100%' }}
		/>
		<Button onClick={applyFilter()} style={{ width: '100%' }}>Apply Filter</Button>
	</Container>
    </MantineProvider>
    <SwipeProfiles profiles={profilesData} filters={filters} />
    </>
  );
};

export default FilterProfiles;
