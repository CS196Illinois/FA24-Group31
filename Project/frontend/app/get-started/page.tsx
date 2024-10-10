// Get started page
import React from 'react';
import { Container, Button, Text } from '@mantine/core';
import { HeaderSimple } from "../header";
import Head from 'next/head';
import Link from 'next/link';
import classes from '../HeaderSimple.module.css';




const GetStarted = () => {
  return (
    <>
      <Head>
        <title>Get Started</title>
      </Head>
      <Container size = "xl"> 
        <HeaderSimple />
        <Text size="xl">Welcome to the Get Started Page!</Text>
        <p>This is a blank page for new users.</p>

        {/* Change href for discord link later on*/}
        <Button component={Link} href="/get-started" className={classes.loginButton} mt="xl"></Button>
      </Container>
    </>
  );
};

export default GetStarted;
