'use client';

import React, {useState, useEffect} from 'react';
import {MantineProvider, Container, RangeSlider, MultiSelect, Button, Text} from '@mantine/core';
import {Profile} from '../types'; // Import the Profile interface

interface FilterProfilesProps {
    profilesData: Profile[];
}

const FilterProfiles: React.FC<FilterProfilesProps> = ({profilesData}) => {
    const [selectedRoles, setSelectedRoles] = useState<string[]>([]);
    const [selectedRanks, setSelectedRanks] = useState<string[]>([]);
    const [ageRange, setAgeRange] = useState<[number, number]>([18, 99]);
    const [filters, setFilters] = useState({
        roles: [] as string[],
        ranks: [] as string[],
        ageRange: [18, 99] as [number, number],
    });
    const [token, setToken] = useState<string>('');

    useEffect(() => {
        if (typeof window !== 'undefined') {
            setToken(localStorage.getItem('sessionToken') || '');
        }
    }, []);

    const applyFilter = () => {
        const updatedFilters = {
            roles: selectedRoles.map((role: string) => role.toLowerCase()),
            ranks: selectedRanks.map((rank: string) => rank.toLowerCase()),
            ageRange: ageRange,
        };
        setFilters(updatedFilters);

        const jsonBody = JSON.stringify({
            roles: updatedFilters.roles,
            ranks: updatedFilters.ranks,
            minAge: updatedFilters.ageRange[0],
            maxAge: updatedFilters.ageRange[1],
            sessionToken: token,
        });

        fetch('http://10.195.197.251:8080/api/v1/update_prefs', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: jsonBody,
        })
            .then((data) => {
                console.log(data.status);
            })
            .catch((err) => {
                console.error(err);
            });
    };

    const matching = () => {
	window.location.href = '/matching';
    }

    return (
        <>
            <MantineProvider>
                <Container
                    style={{
                        display: 'flex',
                        flexDirection: 'column',
                        justifyContent: 'center',
                        alignItems: 'start',
                        width: '30%',
                    }}
                >
                    <Text size="sm" mt="xl">
                        Age range
                    </Text>
                    <RangeSlider
                        minRange={1}
                        min={18}
                        max={99}
                        step={1}
                        value={ageRange}
                        onChangeEnd={setAgeRange}
                        style={{width: '100%'}}
                    />
                    <MultiSelect
                        label="Selected Roles"
                        placeholder="Enter your roles"
                        value={selectedRoles}
                        onChange={setSelectedRoles}
                        data={['Top', 'Jungle', 'Mid', 'ADC', 'Support']}
                        style={{width: '100%'}}
                    />
                    <MultiSelect
                        label="Selected Ranks"
                        placeholder="Enter your ranks"
                        value={selectedRanks}
                        onChange={setSelectedRanks}
                        data={[
                            'Iron',
                            'Bronze',
                            'Silver',
                            'Gold',
                            'Platinum',
                            'Emerald',
                            'Diamond',
                            'Master',
                            'Grandmaster',
                            'Challenger',
                        ]}
                        style={{marginBottom: '1rem', width: '100%'}}
                    />
                    <Button onClick={applyFilter} style={{width: '100%', marginBottom: '1rem'}}>
                        Apply Filter
                    </Button>
		    <Button onClick={matching} style={{width: '100%'}}>
                        Go to Matching Page
                    </Button>

                </Container>
            </MantineProvider>
        </>
    );
};

export default FilterProfiles;
