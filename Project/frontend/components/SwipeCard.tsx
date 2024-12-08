'use client';

import React, {useState, useEffect} from 'react';
import {Container, Button, Card, Image, Text, Badge, Group} from '@mantine/core';
import styles from './SwipeCard.module.css';

interface User {
    discord_id: string;
    name: string;
    pronouns: string[];
    bio: string;
    roles: string[];
    rank: string;
    image: string;
}

export default function SwipeCard() {
    const [user, setUser] = useState<User | null>(null);
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
                setSwipeDirection(null); // Reset swipe direction
            })
            .catch((err) => {
                console.error(err);
            });
    };

    useEffect(() => {
        fetchNextUser();
    }, []);

    const handleSwipe = (direction: 'left' | 'right') => {
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
                            <Image src={user.image} height={160} alt={`${user.name}`}/>
                        </Card.Section>
                        <Group position="apart" style={{marginBottom: 5, marginTop: '10px'}}>
                            <Text weight={500}>{user.name}</Text>
                            <Badge color="pink" variant="light">
                                {user.rank}
                            </Badge>
                        </Group>
                        <Text size="sm" style={{color: '#555', lineHeight: 1.5}}>
                            {user.bio}
                        </Text>
                        <Group spacing="xs" style={{marginTop: '10px'}}>
                            {user.pronouns.map((pronoun) => (
                                <Badge key={pronoun} color="blue" variant="light">
                                    {pronoun}
                                </Badge>
                            ))}
                        </Group>
                        <Group spacing="xs" style={{marginTop: '10px'}}>
                            {user.roles.map((role) => (
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
        </Container>
    );
}
