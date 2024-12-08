'use client'
import React, {useState, useEffect, useCallback} from 'react';
import ProfileCard from './ProfileCard';
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
        pronouns: string[];
        riotId: string;
        discordId: string;
        roles: string[];
        rank: string;
        age: number;
    }[];
    filters: Filter;
}

const SwipeProfiles: React.FC<SwipeProfilesProps> = ({profiles, filters}) => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [filteredProfiles, setFilteredProfiles] = useState(profiles);
    const [swipeDirection, setSwipeDirection] = useState<'left' | 'right' | null>(null);
    const [dragging, setDragging] = useState(false);
    const [startPosition, setStartPosition] = useState<number | null>(null);
    const [dragDistance, setDragDistance] = useState(0);

    // Function to fetch new profiles from the backend
    const fetchProfiles = async () => {
        try {
            const response = await fetch('http://10.195.197.251:8080/api/v1/next_user');
            console.log(response);
            const newProfiles = await response.json();
            setFilteredProfiles(newProfiles);
        } catch (error) {
            console.error('Error fetching profiles:', error);
        }
    };

    // Define swipe functions first
    const swipeLeft = useCallback(async () => {
        if (currentIndex < filteredProfiles.length) {
            setSwipeDirection('left');
            setTimeout(() => {
                setCurrentIndex((prevIndex) => prevIndex + 1);
                setSwipeDirection(null);
                setDragDistance(0);
                // Fetch new profiles when the user swipes left
                fetchProfiles();
            }, 300);
        }
    }, [currentIndex, filteredProfiles.length]);

    const swipeRight = useCallback(async () => {
        if (currentIndex < filteredProfiles.length) {
            setSwipeDirection('right');
            setTimeout(() => {
                setCurrentIndex((prevIndex) => prevIndex + 1);
                setSwipeDirection(null);
                setDragDistance(0);
                // Fetch new profiles when the user swipes right
                fetchProfiles();
            }, 300);
        }
    }, [currentIndex, filteredProfiles.length]);

    // Then define mouse handling functions
    const handleMouseDown = useCallback((e: React.MouseEvent) => {
        setDragging(true);
        setStartPosition(e.clientX);
    }, []);

    const handleMouseMove = useCallback((e: MouseEvent) => {
        if (dragging && startPosition !== null) {
            const distance = e.clientX - startPosition;
            setDragDistance(distance);
        }
    }, [dragging, startPosition]);

    const handleMouseUp = useCallback(() => {
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
    }, [dragging, dragDistance, swipeLeft, swipeRight]);

    // Filter effect
    /*
    useEffect(() => {
        const {roles, rank, ageRange} = filters;
        const filtered = profiles.filter(profile => {
            const roleMatch = roles.length === 0 || roles.some(role => profile.roles.includes(role));
            const rankMatch = !rank || profile.rank === rank;
            const ageMatch = profile.age >= ageRange[0] && profile.age <= ageRange[1];
            return roleMatch && rankMatch && ageMatch;
        });
        setFilteredProfiles(filtered);
    }, [filters, profiles]);
    */

    // Mouse event listeners
    useEffect(() => {
        if (dragging) {
            window.addEventListener('mousemove', handleMouseMove);
            window.addEventListener('mouseup', handleMouseUp);
        }

        return () => {
            window.removeEventListener('mousemove', handleMouseMove);
            window.removeEventListener('mouseup', handleMouseUp);
        };
    }, [dragging, handleMouseMove, handleMouseUp]);

    // Keyboard event listeners
    useEffect(() => {
        const handleKeyDown = (event: KeyboardEvent) => {
            if (event.key === 'ArrowRight') {
                swipeRight();
            } else if (event.key === 'ArrowLeft') {
                swipeLeft();
            }
        };

        window.addEventListener('keydown', handleKeyDown);
        return () => {
            window.removeEventListener('keydown', handleKeyDown);
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
                                <ProfileCard
					name={profile.name}
					age={profile.age}
					image={profile.image}
					bio={profile.bio}
					pronouns={profile.pronouns}
					riotId={profile.riotId}
					discordId={profile.discordId}
					description={profile.description}
				    roles={profile.roles}
                                    rank={profile.rank}
                                />
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
