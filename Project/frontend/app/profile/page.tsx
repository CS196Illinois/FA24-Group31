// Profile edit page
// Should be able to customize your own aspects but there's some stuff pre-filled out already
'use client';
import React from 'react';
import { MantineProvider, TextInput, Container } from '@mantine/core';
import { HeaderSimple } from '../header';

// first name
// last name

export default function Profile() {
  return (
    <MantineProvider>
      <HeaderSimple />
      <Container
        size="xs"
        style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          flexDirection: 'column',
          height: '90vh',
        }}
      >
        <TextInput
          label="First Name"
          placeholder="Enter your first name"
          style={{ marginBottom: '20px', width: '100%' }}
        />
        <TextInput
          label="Last Name"
          placeholder="Enter your last name"
          style={{ marginBottom: '20px', width: '100%' }}
        />
        <TextInput
          label="Riot ID (with Tag #)"
          placeholder="Enter your Riot ID with tag (e.g., Ekansh#1234)"
          style={{ marginBottom: '20px', width: '100%' }}
        />
        <TextInput
          //label="Discord ID"
          disabled label ="Disabled label"
          placeholder="Discord ID"
          style={{ marginBottom: '20px', width: '100%' }}
        />
      </Container>
    </MantineProvider>
  );
}


