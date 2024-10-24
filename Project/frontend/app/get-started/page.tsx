// Get started page
// Will contain a button to login with discord

import React from 'react';
import { Container, Button, Text } from '@mantine/core';
import { HeaderSimple } from "../header";
import Head from 'next/head';
import Link from 'next/link';
import classes from '../HeaderSimple.module.css';




const GetStarted = () => {
  return (
    <>
    <HeaderSimple />
      <Head>
        <title>Get Started</title>
      </Head>
      <Container size="xl" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '75vh', flexDirection: 'column' }}> 
        
        <Text size="xl">Welcome to the Get Started Page!</Text>
        <Text size="md">This is a blank page for new users.</Text>

        {/* Dicsord Login Link INSERT HERE*/}
        {/* <Button component={Link} className={classes.loginButton} mt="xl" justify="center"> Login with Discord </Button> */}
        
        {/* Add any other login methods below if needed...*/}
      </Container>
    </>
  );
};

export default GetStarted;
