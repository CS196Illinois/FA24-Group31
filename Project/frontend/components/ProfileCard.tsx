import React from 'react';

interface ProfileCardProps {
  name: string;
  image: string;
  bio: string;
}

const ProfileCard: React.FC<ProfileCardProps> = ({ name, image, bio }) => {
  return (
    <div className="w-500 h-80 bg-white rounded-lg shadow-lg flex flex-col items-center justify-ceter">
      <img src={image} alt={name} className="w-24 h-24 rounded-full mb-2" />
      <h2 className="text-2xl font-bold">{name}</h2>
      <p className="text-gray-600">{bio}</p>
    </div>
  );
};

export default ProfileCard;
