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
    <div className="w-500 h-80 bg-white rounded-lg shadow-lg flex flex-col items-center justify-center">
      <img src={image} alt={name} className="w-24 h-24 rounded-full mb-2" />
      <h2 className="text-2xl font-bold">{name}</h2>
      <p className="text-gray-600">{bio}</p>
    </div>
  );1
};

export default ProfileCard;
