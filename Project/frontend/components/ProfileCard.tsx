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
      <img src={image} alt={name} className={classes.profileCardImage} />
      <h2 className={classes.profileCardName}>{name}</h2>
      <p className={classes.profileCardBio}>{bio}</p>
    </div>
  );
};

export default ProfileCard;