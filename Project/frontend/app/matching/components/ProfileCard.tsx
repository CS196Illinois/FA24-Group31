import React from 'react';
import classes from './profiles.module.css';

interface ProfileCardProps {
  name: string;
  age: number;
  image: string;
  bio: string;
  pronouns: string[];
  riotId: string;
  discordId: string;
  description: string;
  roles: string[]; // Array of image URLs
  rank: string;
}

const ProfileCard: React.FC<ProfileCardProps> = ({
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
      {/* Scrollable content wrapper */}
      <div className={classes.scrollableContent}>
        <img src={image} alt={name} className={classes.profileCardImage} />
        <h2 className={classes.profileCardName}>{name}</h2>
        <p className={classes.profileCardBio}>{age}</p>
        <p className={classes.profileCardBio}>{bio}</p>
        <p className={classes.profileCardPronouns}>Pronouns: {pronouns}</p>
        <p className={classes.profileCardId}>Riot ID: {riotId}</p>
        <p className={classes.profileCardId}>Discord ID: {discordId}</p>
        <p className={classes.profileCardDescription}>{description}</p>
        <div className={classes.profileCardRoles}>
	  <span className={classes.roleTitle}>Roles: {roles}</span>
        </div>
        <div className={classes.profileCardRank}>
          <span className={classes.rankTitle}>Rank: {rank.title}</span>
        </div>
      </div>
    </div>
  );
};

export default ProfileCard;
