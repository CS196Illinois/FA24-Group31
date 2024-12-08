
"use client";

import { MantineProvider, Text, createTheme, Space, Container } from "@mantine/core";
import React from "react"
import { useState } from 'react';
import classes from './AboutUs.module.css';



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
  

  
export function AboutUs() {
    return (
        <MantineProvider theme={theme}>
            <section className={classes.section}>
                <Container className={classes.content}>

                
                    <Text size="xxl" color="white" ta = "center" fw={500}> About Us </Text>
                    <Space h = "lg" />
                    <Space h = "lg" />
                    <Space h = "lg" />
                    <Space h = "lg" />
                    <Text size="lg" color="white"> <b> Project Manager:</b> Jeffrey Huang </Text>
                    {/* Put other names here */}
                    <Text size="lg" color="white"> <b> Frontend and Mobile Developer:</b> Ekansh Arora </Text>
                    <Text size="lg" color="white"> <b> Frontend Developer and Designer:</b> Enya Chen </Text>
                    <Text size="lg" color="white"> <b> Frontend Developer:</b> Maya Swaminathan </Text>
                    <Text size="lg" color="white"> <b> Frontend Developer:</b> Barry Liu </Text>
                    <Text size="lg" color="white"> <b> Frontend Developer:</b> Carlton Yuan Bo Woo </Text>
                    <Text size="lg" color="white"> <b> Backend Developer:</b> Raymond Yang </Text>
                    <Text size="lg" color="white"> <b> Backend Developer:</b> Vinay Rajagopalan </Text>
                    <Text size="lg" color="white"> <b> Backend Developer:</b> Adhi Thirumala </Text>

                </Container>
            </section>

        </MantineProvider>
    );
}