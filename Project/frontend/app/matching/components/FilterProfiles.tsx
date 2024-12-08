'use client';

import React, { useState, useEffect } from 'react';
import SwipeProfiles from './SwipeProfiles';
import { MantineProvider, Container, RangeSlider, MultiSelect, Button, Text } from '@mantine/core';

const FilterProfiles: React.FC<FilterProfilesProps> = ({
  selectedRoles,
  setSelectedRoles,
  selectedRanks,
  setSelectedRanks,
  ageRange,
  setAgeRange,
  filters,
  setFilters,
  profilesData // Add this prop
}) => {
  const [token, setToken] = useState<string>('');

  useEffect(() => {
    if (typeof window !== 'undefined') {
      setToken(localStorage.getItem('sessionToken') || '');
    }
  }, []);

  const applyFilter = () => { // Changed to proper function declaration
    const jsonBody = JSON.stringify({
      roles: selectedRoles.toLowerCase(),
      ranks: selectedRanks.toLowerCase(),
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
  };

  return (
    <>
      <MantineProvider>
        <Container style={{ 
          display: 'flex', 
          flexDirection: 'column', 
          justifyContent: 'center', 
          alignItems: 'start', 
          width: '30%', 
          transform: 'translateY(-6rem)' 
        }}>
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
          <Button onClick={applyFilter} style={{ width: '100%' }}>Apply Filter</Button>
        </Container>
      </MantineProvider>
      <SwipeProfiles profiles={profilesData} filters={filters} />
    </>
  );
};

export default FilterProfiles;
