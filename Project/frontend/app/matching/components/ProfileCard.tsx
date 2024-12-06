import React from 'react';
import classes from './profiles.module.css';

interface ProfileCardProps {
  name: string;
  image: string;
  bio: string;
  pronouns: string;
  riotId: string;
  discordId: string;
  description: string;
  roles: string[]; // Array of image URLs
  rank: {
    title: string; // Rank as a string
    image: string; // Rank image URL
  };
}

const ProfileCard: React.FC<ProfileCardProps> = ({
  name,
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
        <p className={classes.profileCardBio}>{bio}</p>
        <p className={classes.profileCardPronouns}>Pronouns: {pronouns}</p>
        <p className={classes.profileCardId}>Riot ID: {riotId}</p>
        <p className={classes.profileCardId}>Discord ID: {discordId}</p>
        <p className={classes.profileCardDescription}>{description}</p>
        <div className={classes.profileCardRoles}>
          {roles.map((role, index) => (
            <img
              key={index}
              src={role}
              alt={`Role ${index + 1}`}
              className={classes.roleImage}
            />
          ))}
        </div>
        <div className={classes.profileCardRank}>
          <img
            src={rank.image}
            alt={rank.title}
            className={classes.rankImage}
          />
          <span className={classes.rankTitle}>{rank.title}</span>
        </div>
      </div>
    </div>
  );
};

export default ProfileCard;