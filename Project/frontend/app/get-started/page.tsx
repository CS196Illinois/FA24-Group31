// pages/get-started/get-started.tsx
import React from 'react';
import { Container } from '@mantine/core';
import { HeaderSimple } from "../header";
import Head from 'next/head';

const GetStarted = () => {
  return (
    <>
      <Head>
        <title>Get Started</title>
      </Head>
      <Container size = "xl"> {/* You can adjust the size to suit your layout */}
        <HeaderSimple />
        <h1>Welcome to the Get Started Page!</h1>
        <p>This is a blank page for new users.</p>
      </Container>
    </>
  );
};

export default GetStarted;
