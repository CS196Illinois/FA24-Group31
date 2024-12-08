'use client';

import React, {useState, useEffect} from 'react';
import SwipeProfiles from './components/SwipeProfiles';
import FilterProfiles from './components/FilterProfiles';
import classes from './style.module.css';
import {HeaderSimple} from '../header';

interface MatchingPageProps {
}

const MatchingPage: React.FC<MatchingPageProps> = () => {
    const [selectedRoles, setSelectedRoles] = useState<string[]>([]);
    const [selectedRanks, setSelectedRanks] = useState<string[]>([]);
    const [ageRange, setAgeRange] = useState<[number, number]>([18, 99]);
    const [filters, setFilters] = useState({roles: [], rank: '', ageRange: [18, 99]});
    const [profilesData, setProfilesData] = useState<any[]>([]);

    const fetchProfiles = async () => {
        try {
            console.log('Fetching profiles...');
            const response = await fetch('http://10.195.197.251:8080/api/v1/next_user', {
		    method: 'POST',
		    headers: {'content-type': 'application/json'},
		    body: JSON.stringify({session_token: localStorage.getItem('sessionToken')})
	    });
		
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const profiles = await response.json();
            setProfilesData(profiles);
            console.log('Profiles fetched:', profiles);
        } catch (error) {
            console.error('Error fetching profiles:', error);
        }
    };

    useEffect(() => {
        console.log('Component mounted, fetching profiles...');
        fetchProfiles();
    }, []);

    return (
        <div>
            <HeaderSimple/>
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

export default MatchingPage;
