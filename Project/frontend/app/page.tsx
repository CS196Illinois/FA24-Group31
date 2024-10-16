// Homepage

"use client";

import React from "react"
import { useState } from 'react';
import { Container, Group, Burger, Button } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
import classes from './HeaderSimple.module.css';
import { Text } from '@mantine/core';
import { createTheme } from '@mantine/core';
import { MantineProvider } from "@mantine/core";
import Link from 'next/link';
import { HeaderSimple } from "./header";
import { OurMission } from "./ourmission";
import { AboutUs } from "./aboutus";



const theme = createTheme({
  fontSizes: {
    xs: '0.5rem',
    sm: '0.75rem',
    md: '1rem',
    lg: '1.25rem',
    xl: '1.9rem', 
    xxl: '2.5rem'
  },
});




{/* Homepage */}
export default function Home() {
  return (
    <MantineProvider theme={theme}> 
      <div>
        <HeaderSimple />
        <section className={classes.section}>
          <Container className={classes.content}>

            {/* Hero text */}

            <Text size="xxl" fw={550} c="white"> A League of Legends themed matching app.</Text>
            <Text size="md" fw={500} c="white">Find your perfect partner, in-game or in-real-life.</Text>

            {/* Get started button */}

            <Link href="/get-started/get-started"> 
            <Button component={Link} href="/get-started" className={classes.getStartedButton} mt="xl">
            Get Started
            </Button>
            </Link>
          </Container>
  
        </section>  
        <OurMission/>
        <AboutUs/>
      </div>
    </MantineProvider>
  );
}

