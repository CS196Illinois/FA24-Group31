// Profile edit page
// Should be able to customize your own aspects but there's some stuff pre-filled out already
'use client';
import React, { useState, useEffect, useRef } from 'react';
import { MantineProvider, TextInput, Container, Button, Text, MultiSelect, Select, FileInput, FileInputProps} from '@mantine/core';
import { DatePickerInput } from '@mantine/dates';
import { HeaderSimple } from '../header';

import '@mantine/dates/styles.css';

// Define the Profile component
export default function Profile() {
  const [firstName, setFirstName] = useState<string>(''); // Store first name
  const [lastName, setLastName] = useState<string>(''); // Store last name
  const [dob, setDob] = useState<Date | null>(null)
  const [riotId, setRiotId] = useState<string>(''); // Store Riot ID
  const [pronouns, setPronouns] = useState<string[]>([]);
  const [description, setDescription] = useState<string>('');
  const [roles, setRoles] = useState<string[]>([]);
  const [rank, setRank] = useState<string>('');
  const [profilePicture, setProfilePicture] = useState<File>(null);
  const [b64ProfilePicture, setB64ProfilePicture] = useState<string>('');
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

  const handleDescription = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setDescription(value); // Allow the update first
  };

interface RolesOption {
  readonly value: string;
  readonly label: string;
  readonly isFixed?: boolean;
  readonly isDisabled?: boolean;
}

interface PronounsOption {
  readonly value: string;
  readonly label: string;
  readonly isFixed?: boolean;
  readonly isDisabled?: boolean;
}

interface RankOption {
  readonly value: string;
  readonly label: string;
  readonly isFixed?: boolean;
  readonly isDisabled?: boolean;
}


const pronounsOptions: readonly PronounsOption[] = [
  // populate with pronouns
  { value: 'he/him', label: 'He/Him' },
  { value: 'she/her', label: 'She/Her' },
  { value: 'they/them', label: 'They/Them' },
  { value: 'xe/xem', label: 'Xe/Xem' },
  { value: 'ze/zir', label: 'Ze/Zir' },
  { value: 'other', label: 'Other' },
];

const rolesOptions: readonly RolesOption[] = [
  // populate with league of legends roles
  { value: 'top', label: 'Top' },
  { value: 'jungle', label: 'Jungle' },
  { value: 'middle', label: 'Middle' },
  { value: 'bottom', label: 'Bottom' },
  { value: 'support', label: 'Support' },
];

const rankOptions: readonly RolesOption[] = [
  // populate with league of legends ranks
  { value: 'iron', label: 'Iron' },
  { value: 'bronze', label: 'Bronze' },
  { value: 'silver', label: 'Silver' },
  { value: 'gold', label: 'Gold' },
  { value: 'platinum', label: 'Platinum' },
  { value: 'emerald', label: 'Emerald' },
  { value: 'diamond', label: 'Diamond' },
  { value: 'master', label: 'Master' },
  { value: 'grandmaster', label: 'Grandmaster' },
  { value: 'challenger', label: 'Challenger' },
];


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

  // Handle the input change event for Profile Picture by converting to file to base64
  const handleProfilePictureChange = (file: File | null) => {
    if (file) {
      const reader = new FileReader(); // Create a new FileReader
      reader.onload = (e) => {
        const base64 = e.target?.result as string; // Get the base64 data
        setB64ProfilePicture(base64); // Set the base64 data
        setProfilePicture(file); // Set the file
      };
      reader.readAsDataURL(file); // Read the file as data URL
    }
  }

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

  function createAccountRequest() {
    // Send the form data to the server
    const formData = new FormData();
    formData.append('first_name', firstName);
    formData.append('last_name', lastName);
    formData.append('dob', dob.toString());
    formData.append('riot_id', riotId);
    formData.append('pronouns', pronouns.join(','));
    formData.append('description', description);
    formData.append('roles', roles.join(','));
    formData.append('rank', rank);
    formData.append('b64_profile_pic', b64ProfilePic);

    fetch('/api/v1/users/create_user', {
      method: 'POST',
      body: formData,
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        // Redirect to the profile page
        window.location.href = '/profile';
      })
      .catch((err) => {
        console.error(err);
      });
  }

  return (
    <MantineProvider>
      <HeaderSimple />
      <Container
        size="xs"
        style={{
          display: 'flex',
          justifyContent: 'end',
          alignItems: 'center',
          flexDirection: 'column',
          height: '95vh',
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
        <DatePickerInput
          label="Date of Birth"
          placeholder="Enter your date of birth"
          value={dob}
          onChange={setDob}
          required
          // generate style make calendar header control icon smaller
          style={{ marginBottom: '20px', width: '100%'}}
        />
        <MultiSelect
          label="Pronouns"
          placeholder="Enter your pronouns"
          value={pronouns}
          onChange={setPronouns}
          data={pronounsOptions}
          required
          style={{ marginBottom: '20px', width: '100%' }}
        />
        <MultiSelect
          label="Roles"
          placeholder="Enter your roles"
          value={roles}
          onChange={setRoles}
          data={rolesOptions}
          required
          style={{ marginBottom: '20px', width: '100%' }}
        />
        <Select
          label="Rank"
          placeholder="Enter your rank"
          value={rank}
          onChange={setRank}
          data={rankOptions}
          required
          style={{ marginBottom: '20px', width: '100%' }}
        />
        <TextInput
          label="Description"
          placeholder="Enter a description about yourself"
          value={description}
          onChange={handleDescription}
          required // Make field required
          style={{ marginBottom: '20px', width: '100%' }}
        />
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
        <FileInput
          label="Profile Picture"
          placeholder="Upload a profile picture"
          type="file"
          value={profilePicture}
          onChange={handleProfilePictureChange}
          style={{ marginBottom: '20px', width: '100%' }}
          accept="image/png,image/jpeg,image/jpg"
          required
        />
        <TextInput
          disabled
          label="Discord Username"
          placeholder="Discord Username"
          style={{ marginBottom: '20px', width: '100%' }}
        />
        <Button
          onClick={() => {createAccountRequest()}}
          disabled={isSubmitDisabled} // Disable if the form is invalid or fields are empty
        >
          Submit
        </Button>
      </Container>
    </MantineProvider>
  );
}
