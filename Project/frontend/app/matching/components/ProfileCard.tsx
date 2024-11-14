import React from 'react';
import classes from './profiles.module.css';

interface ProfileCardProps {
  name: string;
  image: string;
  bio: string;
}

const ProfileCard: React.FC<ProfileCardProps> = ({ name, image, bio }) => {
  return (
    <div className={classes.profileCard}>
      {/* Scrollable content wrapper around all parameters */}
      <div className={classes.scrollableContent}>
        <img src={image} alt={name} className={classes.profileCardImage} />
        <h2 className={classes.profileCardName}>{name}</h2>
        <p className={classes.profileCardBio}>{bio}</p>
        {/* Future parameters can be added here */}
      </div>
    </div>
  );
};

export default ProfileCard;