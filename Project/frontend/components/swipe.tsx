import React, { useState, useEffect, useCallback } from 'react';

interface SwipeProfilesProps {
  profiles: string[];  // Array of profiles to display
}

const SwipeProfiles: React.FC<SwipeProfilesProps> = ({ profiles }) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const swipeLeft = useCallback(() => {
    if (currentIndex < profiles.length) {
      setCurrentIndex((prevIndex) => prevIndex + 1);
    }
  }, [currentIndex, profiles.length]);

  const swipeRight = useCallback(() => {
    if (currentIndex < profiles.length) {
      setCurrentIndex((prevIndex) => prevIndex + 1);
    }
  }, [currentIndex, profiles.length]);

  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'ArrowRight') {
        swipeRight();
      } else if (event.key === 'ArrowLeft') {
        swipeLeft();
      }
    };

    document.addEventListener('keydown', handleKeyDown);
    
    // Clean up event listener on component unmount
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [swipeLeft, swipeRight]);

  return (
    <div className="relative w-80 h-96">
      {profiles.map((profile, index) => (
        <div
          key={index}
          className={`absolute w-full h-full bg-white rounded-lg shadow-lg flex justify-center items-center text-2xl transition-transform duration-300 ${
            index === currentIndex ? 'transform-none' : 'translate-x-full'
          }`}
          style={{
            display: index >= currentIndex ? 'block' : 'none',
          }}
        >
          {profile}
        </div>
      ))}
      <div className="mt-4 flex justify-between">
        <button
          onClick={swipeLeft}
          className="px-4 py-2 bg-blue-500 text-white rounded-md"
        >
          Swipe Left
        </button>
        <button
          onClick={swipeRight}
          className="px-4 py-2 bg-green-500 text-white rounded-md"
        >
          Swipe Right
        </button>
      </div>
    </div>
  );
};

export default SwipeProfiles;
