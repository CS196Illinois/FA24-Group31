import React, { useState, useEffect, useCallback } from 'react';
import ProfileCard from './ProfileCard'; // Import the ProfileCard component

interface SwipeProfilesProps {
  profiles: {
    name: string;
    image: string;
    bio: string;
  }[]; // Update the type for profiles
}

const SwipeProfiles: React.FC<SwipeProfilesProps> = ({ profiles }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [swipeDirection, setSwipeDirection] = useState<'left' | 'right' | null>(null);
  const [dragging, setDragging] = useState(false);
  const [startPosition, setStartPosition] = useState<number | null>(null);
  const [dragDistance, setDragDistance] = useState(0);

  const swipeLeft = useCallback(() => {
    if (currentIndex < profiles.length) {
      setSwipeDirection('left');
      // Set a timeout to delay updating the currentIndex to allow for the animation to complete
      setTimeout(() => {
        setCurrentIndex((prevIndex) => prevIndex + 1);
        setSwipeDirection(null);
        setDragDistance(0);
      }, 300); // Match with CSS transition duration
    }
  }, [currentIndex, profiles.length]);

  const swipeRight = useCallback(() => {
    if (currentIndex < profiles.length) {
      setSwipeDirection('right');
      // Set a timeout to delay updating the currentIndex to allow for the animation to complete
      setTimeout(() => {
        setCurrentIndex((prevIndex) => prevIndex + 1);
        setSwipeDirection(null);
        setDragDistance(0);
      }, 300); // Match with CSS transition duration
    }
  }, [currentIndex, profiles.length]);

  const handleMouseDown = (e: React.MouseEvent) => {
    setDragging(true);
    setStartPosition(e.clientX);
  };

  const handleMouseMove = (e: MouseEvent) => {
    if (dragging && startPosition !== null) {
      const distance = e.clientX - startPosition;
      setDragDistance(distance);
    }
  };

  const handleMouseUp = () => {
    if (dragging) {
      setDragging(false);

      // Check if the drag distance exceeds the threshold
      if (dragDistance > 100) {
        swipeRight(); // Trigger right swipe if dragged right beyond threshold
      } else if (dragDistance < -100) {
        swipeLeft(); // Trigger left swipe if dragged left beyond threshold
      } else {
        // Reset position if threshold not met
        setDragDistance(0);
      }

      setStartPosition(null); // Clear start position
    }
  };

  useEffect(() => {
    if (dragging) {
      document.addEventListener('mousemove', handleMouseMove);
      document.addEventListener('mouseup', handleMouseUp);
    } else {
      document.removeEventListener('mousemove', handleMouseMove);
      document.removeEventListener('mouseup', handleMouseUp);
    }

    return () => {
      document.removeEventListener('mousemove', handleMouseMove);
      document.removeEventListener('mouseup', handleMouseUp);
    };
  }, [dragging, handleMouseMove, handleMouseUp]);

  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'ArrowRight') {
        swipeRight();
      } else if (event.key === 'ArrowLeft') {
        swipeLeft();
      }
    };

    document.addEventListener('keydown', handleKeyDown);
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [swipeLeft, swipeRight]);

  return (
    <div className="relative w-80 h-96 flex flex-col items-center">
      <div className="flex-1 w-full relative flex items-center justify-center">
        {currentIndex < profiles.length ? (
          profiles.map((profile, index) => {
            // Calculate the transform for the current profile
            const isCurrent = index === currentIndex;
            const translateX =
              isCurrent && dragDistance !== 0
                ? dragDistance
                : isCurrent && swipeDirection === 'left'
                ? -400 // Adjust this value to fit your card width
                : isCurrent && swipeDirection === 'right'
                ? 400 // Adjust this value to fit your card width
                : 0;

            return (
              <div
                key={index}
                className={`absolute w-500 h-80 bg-white rounded-lg shadow-lg flex justify-center items-center text-2xl transition-transform duration-300 ease-in-out`}
                style={{
                  transform: `translateX(${translateX}px)`,
                  zIndex: isCurrent ? 20 : 10, // Higher z-index for the current card
                  opacity: isCurrent ? 1 : 0.5, // Dim other cards
                  display: isCurrent || index > currentIndex ? 'block' : 'none', // Keep previous cards in the DOM
                }}
                onMouseDown={handleMouseDown}
              >
          <ProfileCard
            name={profile.name}
            image={profile.image}
            bio={profile.bio}
          />
              </div>
            );
          })
        ) : (
          <div className="w-64 h-80 flex justify-center items-center text-2xl text-gray-500">
            No more profiles to display.
          </div>
        )}
      </div>

      {/* Button Container Positioned at the Bottom */}
      {currentIndex < profiles.length && (
        <div className="fixed bottom-4 flex w-full justify-around">
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
      )}
    </div>
  );
};

export default SwipeProfiles;
