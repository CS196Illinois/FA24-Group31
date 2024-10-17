
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
                    <Text size="xl" color="white"> Project Manager: Jeffrey Huang </Text>
                    {/* Put other names here */}
                    <Text color="white"> add other names and group pic</Text>

                </Container>
            </section>

        </MantineProvider>
    );
}