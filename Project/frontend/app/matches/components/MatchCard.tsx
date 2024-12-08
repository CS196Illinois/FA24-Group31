// app/matches/components/MatchCard.tsx
'use client';

import React from 'react';
import classes from '../matches.module.css';

interface MatchCardProps {
    match: {
        name: string;
        discordId: string;
    };
    isMutual: boolean;
}

const MatchCard: React.FC<MatchCardProps> = ({ match, isMutual }) => {
    return (
        <div className={`${classes.matchCard} ${isMutual ? classes.mutualMatch : classes.oneWayMatch}`}>
            <div className={classes.matchInfo}>
                <h3 className={classes.matchName}>{match.name}</h3>
                <div className={classes.matchIds}>
                    <p>Discord: {match.discordId}</p>
                </div>
            </div>
        </div>
    );
};

export default MatchCard;
