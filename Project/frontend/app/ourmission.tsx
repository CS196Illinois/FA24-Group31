
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
                        <Text size="md" color="pink.8"> To connect League of Legends players together and make it a friendlier game yada yada </Text>
                        <Text size="md" color="pink.8"> insert some picture here </Text>


                    </Container>
                </section>
            </div>

        </MantineProvider>
    );
}