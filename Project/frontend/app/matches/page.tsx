// app/matches/page.tsx
'use client';

import React, { useState, useEffect } from 'react';
import { HeaderSimple } from '../header';
import MatchCard from './components/MatchCard';
import classes from './matches.module.css';

interface Match {
	name: string;
    discordId: string;
}

const MatchesPage = () => {
    const [matches, setMatches] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchMatches = async () => {
            try {
                const response = await fetch('http://10.195.197.251:8080/api/v1/get_matches', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        session_token: localStorage.getItem('sessionToken')
                    })
                });

                if (!response.ok) {
                    throw new Error('Failed to fetch matches');
                }

                const data = await response.json();
                setMatches(data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'Failed to fetch matches');
            } finally {
                setLoading(false);
            }
        };

        fetchMatches();
    }, []);

    //const twoWayMatches = matches.filter(match => match.isMutual);
    const twoWayMatches = matches
    console.log(twoWayMatches);

    if (loading) return <div className={classes.loading}>Loading matches...</div>;
    if (error) return <div className={classes.error}>{error}</div>;

    return (
        <div>
            <div className={classes.matchesContainer}>
                <div className={classes.matchesSection}>
                    <h2 className={classes.sectionTitle}>Two-Way Matches</h2>
                    <div className={classes.matchesGrid}>
                        {twoWayMatches.length > 0 ? (
                            twoWayMatches.map((match) => (
                                <MatchCard
                                    key={match.userId}
                                    match={match}
                                    isMutual={true}
                                />
                            ))
                        ) : (
                            <p className={classes.noMatches}>No mutual matches yet!</p>
                        )}
                    </div>
		    <button className="h-10 self-center">Go back to matching</button>
                </div>
            </div>
        </div>
    );
};

export default MatchesPage;
