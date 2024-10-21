// Profile edit page
// Should be able to customize your own aspects but there's some stuff pre-filled out already
'use client';
import React, { useState, useEffect, useRef } from 'react';
import { MantineProvider, TextInput, Container, Button, Text } from '@mantine/core';
import { HeaderSimple } from '../header';

// Define the Profile component
export default function Profile() {
  const [firstName, setFirstName] = useState<string>(''); // Store first name
  const [lastName, setLastName] = useState<string>(''); // Store last name
  const [riotId, setRiotId] = useState<string>(''); // Store Riot ID
  const [errors, setErrors] = useState<{ firstName?: string; lastName?: string; riotId?: string }>({}); // Store validation error messages
  const [isSubmitDisabled, setIsSubmitDisabled] = useState<boolean>(true); // Disable submit button initially

  const debounceTimeout = useRef<NodeJS.Timeout | null>(null); // Ref to store timeout for debouncing

  // Validation function for alphabetic names (first and last)
  const validateName = (name: string) => {
    const namePattern = /^[a-zA-Z]+$/; // Regex for alphabetic characters only
    return namePattern.test(name);
  };

  // Riot ID validation function
  const validateRiotId = (id: string) => {
    if (id.length < 3 || id.length > 22) {
      return 'Riot ID must be between 3 and 22 characters long, including the tag.';
    }
    
    if (!id.includes('#')) {
      return 'Riot ID must contain a # separating the name and tag.';
    }

    const [namePart, tagPart] = id.split('#');
    
    if (namePart.length < 3 || namePart.length > 16) {
      return 'The part before the # must be between 3 and 16 characters long.';
    }

    if (tagPart?.length < 3 || tagPart.length > 5) {
      return 'The tag after the # must be between 3 and 5 characters long.';
    }

    const riotIdPattern = /^[a-zA-Z0-9_-]+$/;
    if (!riotIdPattern.test(namePart) || !riotIdPattern.test(tagPart)) {
      return 'Only alphanumeric characters, hyphens (-), and underscores (_) are allowed.';
    }

    return ''; // No error, return an empty string
  };

  // Debounced validation function
  const debounceValidation = (callback: () => void, delay: number = 500) => {
    if (debounceTimeout.current) clearTimeout(debounceTimeout.current);
    debounceTimeout.current = setTimeout(() => {
      callback();
    }, delay);
  };

  // Handle the input change event for Riot ID
  const handleRiotIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setRiotId(value);
    debounceValidation(() => {
      const riotError = validateRiotId(value);
      setErrors((prev) => ({ ...prev, riotId: riotError ? riotError : undefined })); // Set error for Riot ID
    });
  };

  // Handle the input change event for First Name
  const handleFirstNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setFirstName(value); // Allow the update first
    debounceValidation(() => {
      if (!validateName(value)) {
        setErrors((prev) => ({ ...prev, firstName: 'First Name should only contain alphabets (a-z, A-Z).' }));
      } else {
        setErrors((prev) => ({ ...prev, firstName: undefined })); // Clear error if valid
      }
    });
  };

  // Handle the input change event for Last Name
  const handleLastNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setLastName(value); // Allow the update first
    debounceValidation(() => {
      if (!validateName(value)) {
        setErrors((prev) => ({ ...prev, lastName: 'Last Name should only contain alphabets (a-z, A-Z).' }));
      } else {
        setErrors((prev) => ({ ...prev, lastName: undefined })); // Clear error if valid
      }
    });
  };

  // Check if all fields are valid and not empty
  useEffect(() => {
    const isFormValid = 
      firstName !== '' && 
      lastName !== '' && 
      riotId !== '' && 
      validateName(firstName) && 
      validateName(lastName) && 
      !validateRiotId(riotId); // Ensure Riot ID has no error message

    setIsSubmitDisabled(!isFormValid);
  }, [firstName, lastName, riotId]); // Revalidate when any of these fields change

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
          value={firstName}
          onChange={handleFirstNameChange} // Validate first name on input change
          required // Make field required
          style={{ marginBottom: '20px', width: '100%' }}
        />
        {errors.firstName && ( // Show error message for First Name
          <Text color="red" style={{ marginBottom: '10px' }}>{errors.firstName}</Text>
        )}
        <TextInput
          label="Last Name"
          placeholder="Enter your last name"
          value={lastName}
          onChange={handleLastNameChange} // Validate last name on input change
          required // Make field required
          style={{ marginBottom: '20px', width: '100%' }}
        />
        {errors.lastName && ( // Show error message for Last Name
          <Text color="red" style={{ marginBottom: '10px' }}>{errors.lastName}</Text>
        )}
        <TextInput
          label="Riot ID (with Tag #)"
          placeholder="Enter your Riot ID with tag (e.g., Ekansh#1234)"
          value={riotId}
          onChange={handleRiotIdChange} // Handle event with properly typed function
          required // Make field required
          style={{ marginBottom: '20px', width: '100%' }}
        />
        {errors.riotId && ( // Show error message for Riot ID
          <Text color="red" style={{ marginBottom: '10px' }}>{errors.riotId}</Text>
        )}
        <TextInput
          disabled
          label="Discord ID"
          placeholder="Discord ID"
          style={{ marginBottom: '20px', width: '100%' }}
        />
        <Button 
          onClick={() => {}} 
          disabled={isSubmitDisabled} // Disable if the form is invalid or fields are empty
        >
          Submit
        </Button>
      </Container>
    </MantineProvider>
  );
}