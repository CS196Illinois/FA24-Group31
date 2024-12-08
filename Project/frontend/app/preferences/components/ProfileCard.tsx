'use client';
import React from 'react';
import classes from './profiles.module.css';
import {Profile} from '../types'; // Import the Profile interface

const ProfileCard: React.FC<Profile> = ({
                                            name,
                                            age,
                                            image,
                                            bio,
                                            pronouns,
                                            riotId,
                                            discordId,
                                            description,
                                            roles,
                                            rank,
                                        }) => {
    return (
        <div className={classes.profileCard}>
            <div className={classes.scrollableContent}>
                <img src={image} alt={name} className={classes.profileCardImage}/>
                <h2 className={classes.profileCardName}>
                    {name}, {age}
                </h2>
                <p className={classes.profileCardPronouns}>
                    Pronouns: {pronouns.join(', ')}
                </p>
                <p className={classes.profileCardBio}>{bio}</p>
                <p className={classes.profileCardId}>Riot ID: {riotId}</p>
                <p className={classes.profileCardId}>Discord ID: {discordId}</p>
                <p className={classes.profileCardDescription}>{description}</p>
                <div className={classes.profileCardRoles}>
                    <span className={classes.roleTitle}>Roles: {roles.join(', ')}</span>
                </div>
                <div className={classes.profileCardRank}>
                    <span className={classes.rankTitle}>Rank: {rank}</span>
                </div>
            </div>
        </div>
    );
};

export default ProfileCard;