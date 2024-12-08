import React, { useState, useEffect, useCallback } from 'react';
import ProfileCard from './ProfileCard'; // Import the ProfileCard component
import classes from './profiles.module.css';

interface Filter {
  roles: string[];
  rank: string;
  ageRange: [number, number];
}

interface SwipeProfilesProps {
  profiles: {
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
    age: number; // Add age property
  }[];
  filters: Filter;
}

const SwipeProfiles: React.FC<SwipeProfilesProps> = ({ profiles, filters }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [filteredProfiles, setFilteredProfiles] = useState(profiles);
  const [swipeDirection, setSwipeDirection] = useState<'left' | 'right' | null>(null);
  const [dragging, setDragging] = useState(false);
  const [startPosition, setStartPosition] = useState<number | null>(null);
  const [dragDistance, setDragDistance] = useState(0);

  useEffect(() => {
    const { roles, rank, ageRange } = filters;
    const filtered = profiles.filter(profile => {
      const roleMatch = roles.length ? roles.some(role => profile.roles.includes(role)) : true;
      const rankMatch = rank ? profile.rank.title === rank : true;
      const ageMatch = profile.age >= ageRange[0] && profile.age <= ageRange[1];
      return roleMatch && rankMatch && ageMatch;
    });
    setFilteredProfiles(filtered);
  }, [filters, profiles]);

  const swipeLeft = useCallback(() => {
    if (currentIndex < filteredProfiles.length) {
      setSwipeDirection('left');
      setTimeout(() => {
        setCurrentIndex((prevIndex) => prevIndex + 1);
        setSwipeDirection(null);
        setDragDistance(0);
      }, 300); // Match with CSS transition duration
    }
  }, [currentIndex, filteredProfiles.length]);

  const swipeRight = useCallback(() => {
    if (currentIndex < filteredProfiles.length) {
      setSwipeDirection('right');
      setTimeout(() => {
        setCurrentIndex((prevIndex) => prevIndex + 1);
        setSwipeDirection(null);
        setDragDistance(0);
      }, 300); // Match with CSS transition duration
    }
  }, [currentIndex, filteredProfiles.length]);

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

      if (dragDistance > 100) {
        swipeRight();
      } else if (dragDistance < -100) {
        swipeLeft();
      } else {
        setDragDistance(0);
      }

      setStartPosition(null);
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
    <div className={classes.profilesContainer}>
      <div className={classes.profileWrapper}>
        {currentIndex < filteredProfiles.length ? (
          filteredProfiles.map((profile, index) => {
            const isCurrent = index === currentIndex;
            const translateX =
              isCurrent && dragDistance !== 0
                ? dragDistance
                : isCurrent && swipeDirection === 'left'
                ? -400
                : isCurrent && swipeDirection === 'right'
                ? 400
                : 0;

            return (
              <div
                key={index}
                className={classes.swipeCard}
                style={{
                  transform: `translateX(${translateX}px)`,
                  zIndex: isCurrent ? 20 : 10,
                  opacity: isCurrent ? 1 : 0.5,
                  display: isCurrent || index > currentIndex ? 'block' : 'none',
                }}
                onMouseDown={handleMouseDown}
              >
                <ProfileCard {...profile} />
              </div>
            );
          })
        ) : (
          <div className={classes.noMoreProfiles}>
            No more profiles to display.
          </div>
        )}
      </div>
      {currentIndex < filteredProfiles.length && (
        <div className={classes.buttonContainer}>
          <button onClick={swipeLeft} className={classes.swipeButtonLeft}>⟨</button>
          <button onClick={swipeRight} className={classes.swipeButtonRight}>⟩</button>
        </div>
      )}
    </div>
  );
};

export default SwipeProfiles;
