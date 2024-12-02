'use client';
import React from 'react';
import {Container, Button, Text} from '@mantine/core';
import {HeaderSimple} from "../header";
import Head from 'next/head';
import {useDiscordLogin, DiscordLoginParams} from 'react-discord-login';
import {FaDiscord} from 'react-icons/fa';

const GetStarted = () => {
    const discordLoginParams: DiscordLoginParams = {
        /* this is not an API token im not stupid, its a client ID which is irrelevant unless used with the client secret which is on the backend
        * equivalent of a public key.*/
        clientId: '1292671408026943532',
        redirectUri: 'http://localhost:3000/login',
        responseType: 'token', // or 'code'
        scopes: ['identify'],
    }
    const {buildUrl, isLoading} = useDiscordLogin(discordLoginParams);

    return (
        <>
            <HeaderSimple/>
            <Head>
                <title>Get Started</title>
            </Head>
            <Container size="xl" style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '75vh',
                flexDirection: 'column'
            }}>
                <Text size="xl">Welcome to the Get Started Page!</Text>
                <Text size="md">This is a blank page for new users.</Text>

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
            </Container>
        </>
    );
};

export default GetStarted;
