
"use client";

import { MantineProvider, Text, createTheme, Space, Container } from "@mantine/core";
import React from "react"
import { useState } from 'react';
import classes from './Ourmission.module.css';



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
  

  
export function OurMission() {
    return (
        <MantineProvider theme={theme}>
            <div id="our-mission"> 
                <section className={classes.section}>
                    <Container className={classes.content}>

                    
                        <Text size="xxl" variant="gradient" gradient={{from: 'white', to: 'pink', deg:90}} ta = "center" fw={500}> Our Mission </Text>
                        <Space h = "lg" />
                        <Space h = "lg" />
                        <Space h = "lg" />
                        <Space h = "lg" />
                        <Text size="lg" color="pink.5" ta="center"> We strives to unite League of Legends players through shared passion and camaraderie. <b>Matched.lol</b> goes beyond just matchmaking, 
                        it's a space where Summoners can connect, team up, and build meaningful relationships both in and out of the Rift. 
                        Whether you're looking for a duo partner, a gaming companion, or a romantic connection, <b>Matched.lol</b> is designed to help you.
                        Our advanced algorithms are meticulously designed to match players with similar playstyles and personalities. 
                        Together, let’s turn every match into a meaningful bond! Don't miss out! Join hundreds of League of Legends players on this amazing platform. </Text>

                        <br/>
                        <br/>
                        <br/>
                        
                        <Text size="xxl" color="pink.5" ta="center"> ♡ </Text>


                    </Container>
                </section>
            </div>

        </MantineProvider>
    );
}