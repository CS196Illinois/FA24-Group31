'use client';

import React, {useState, useEffect} from 'react';
import {Container, Button, Card, Image, Text, Badge, Group} from '@mantine/core';
import styles from './SwipeCard.module.css';

interface User {
    riotId: string,
    discordId: string,
    name: string;
    pronouns: string[];
    bio: string;
    roles: string[];
    rank: string;
    image: string;
    age: number;
}

export default function SwipeCard() {
	const [user, setUser] = useState<User | null>(null);
    const [riotId, setRiotId] = useState<User | null>(null);
    const [discordId, setDiscordId] = useState<User | null>(null);
    const [name, setName] = useState<User | null>(null);
    const [pronouns, setPronouns] = useState<User | null>(null);
    const [bio, setBio] = useState<User | null>(null);
    const [roles, setRoles] = useState<User | null>(null);
    const [rank, setRank] = useState<User | null>(null);
    const [image, setImage] = useState<User | null>(null);
    const [age, setAge] = useState<User | null>(null);
    const [swipeDirection, setSwipeDirection] = useState<string | null>(null);

    const fetchNextUser = () => {
        fetch('http://10.195.197.251:8080/api/v1/next_user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({session_token: localStorage.getItem("sessionToken")})
        })
            .then((res) => res.json())
            .then((data) => {
                setUser(data);
		setRiotId(data.riotId);
		setDiscordId(data.discordId);
		setName(data.name);
		setPronouns(data.pronouns);
		setBio(data.bio);
		setRoles(data.roles);
		setRank(data.rank);
		setImage(data.image);
		setAge(data.age);
                setSwipeDirection(null); // Reset swipe direction
            })
            .catch((err) => {
                console.error(err);
            });
    };

    useEffect(() => {
        fetchNextUser();
    }, []);

    const preferences = () => {
	    window.location.href = '/preferences';
    }

    const handleSwipe = (direction: 'left' | 'right') => {
	if (direction == 'right') {
	    fetch('http://10.195.197.251:8080/api/v1/update_matches', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({sessionToken: localStorage.getItem("sessionToken"), matchedId: riotId})
        })
            .then((res) => res.json())
            .then((data) => {
                setUser(data);
                setSwipeDirection(null); // Reset swipe direction
            })
            .catch((err) => {
                console.error(err);
            });
	}
        setSwipeDirection(direction);
        setTimeout(() => {
            setUser(null); // Remove the card from the DOM
            fetchNextUser();
        }, 500); // Wait for the animation to complete
    };


    useEffect(() => {
        const handleKeyDown = (event: KeyboardEvent) => {
            if (event.key === 'ArrowLeft') {
                handleSwipe('left');
            } else if (event.key === 'ArrowRight') {
                handleSwipe('right');
            }
        };

        window.addEventListener('keydown', handleKeyDown);
        return () => {
            window.removeEventListener('keydown', handleKeyDown);
        };
    }, []);

    return (
        <Container
            size="xs"
            style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                flexDirection: 'column',
                height: '95vh',
            }}
        >
            {user ? (
                <div
                    className={`${styles['swipe-card']} ${swipeDirection === 'left' ? styles['swipe-left'] : swipeDirection === 'right' ? styles['swipe-right'] : ''}`}
                    onAnimationEnd={() => {
                        if (swipeDirection) {
                            setUser(null); // Remove the card from the DOM
                            fetchNextUser();
                        }
                    }}
                >
                    <Card shadow="sm" padding="lg" style={{width: '100%', marginBottom: '20px'}}>
                        <Card.Section>
                            <Image src={image} height={160} alt={`${name}`}/>
                        </Card.Section>
                        <Group position="apart" style={{marginBottom: 5, marginTop: '10px'}}>
                            <Text weight={800}>{name}</Text>
                        	<Text weight={800}>{age}</Text>
                            <Text weight={800}>{riotId}</Text>
                            <Text weight={800}>{discordId}</Text>
                        </Group>
                        <Text size="sm" style={{color: '#555', lineHeight: 1}}>
                            {bio}
                        </Text>
                        <Group spacing="xs" style={{marginTop: '10px'}}>
			<Badge color="pink" variant="light">
                                {rank}
                            </Badge>

			    {console.log(pronouns)}
                            {pronouns.map((pronouns) => (
                                <Badge key={pronouns} color="blue" variant="light">
                                    {pronouns}
                                </Badge>
                            ))}
                        </Group>
                        <Group spacing="xs" style={{marginTop: '10px'}}>
                            {roles.map((role) => (
                                <Badge key={role} color="green" variant="light">
                                    {role}
                                </Badge>
                            ))}
                        </Group>
                    </Card>
                </div>
            ) : (
                <Text>Loading...</Text>
            )}
            <Group position="center" style={{marginTop: '20px'}}>
                <Button className={styles.button + ' ' + styles.red}
                        onClick={() => handleSwipe('left')}>
                    Swipe Left
                </Button>
                <Button className={styles.button + ' ' + styles.green}
                        onClick={() => handleSwipe('right')}>
                    Swipe Right
                </Button>
            </Group>
		<Button className={styles.button + ' ' + styles.blue} style={{marginTop: '20px'}}
                        onClick={preferences}>
                    Set Preferences
                </Button>

        </Container>
    );
}
