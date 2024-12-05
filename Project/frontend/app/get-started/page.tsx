'use client';
import React from 'react';
import {Container, Button, Text} from '@mantine/core';
import {HeaderSimple} from "../header";
import Head from 'next/head';
import {useDiscordLogin, DiscordLoginParams} from 'react-discord-login';
import {FaDiscord} from 'react-icons/fa';
import styles from './getStarted.module.css';

const GetStarted = () => {
    const discordLoginParams: DiscordLoginParams = {
        clientId: '1292671408026943532',
        redirectUri: 'http://localhost:3000/login',
        responseType: 'token',
        scopes: ['identify'],
    }
    const {buildUrl, isLoading} = useDiscordLogin(discordLoginParams);

    return (
        <>
            <HeaderSimple/>
            <Head>
                <title>Get Started</title>
            </Head>
            <div className={styles.container}>
                <div className={styles.getStartedContainer}>
                    <div className={styles.logoContainer}>
                        <div className={styles.logo}>💖</div>
                    </div>
                    <Text className={styles.getStartedText}>Welcome to the Get Started Page!</Text>
                    <Text className={styles.getStartedText}>This is a blank page for new
                        users.</Text>
                    <Button
                        onClick={() => (window.location.href = buildUrl())}
                        disabled={isLoading}
                        leftSection={<FaDiscord/>}
                        style={{
                            backgroundColor: '#7289da',
                            color: 'white',
                            marginTop: '20px',
                            padding: '10px 20px',
                            fontSize: '16px',
                            fontWeight: 'bold',
                        }}
                    >
                        Sign in with Discord
                    </Button>
                </div>
            </div>
        </>
    );
};

export default GetStarted;