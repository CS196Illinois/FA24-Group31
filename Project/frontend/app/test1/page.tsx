// PAGE NOT BEING USED, EXAMPLE THAT JEFREY MADE
'use client';

import { useState } from 'react';
import { Button, Input, Flex } from '@mantine/core';
import '../globals.css';


const TestPage = () => {
  // Define a state variable to hold user input
  const [name, setName] = useState('');  // a useState hook is used to create a state variable, which is a variable that can be updated and will cause the component to rerender when it changes
  // state variables are declared with the useState hook, which takes an initial value as an argument and returns an array with two elements: the state variable itself and a function to update it
  // Handle button click
  const handleButtonClick = () => {
    console.log("search page", 14, "Button clicked"); // logs the button click to the console
  };
  const onNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newName = e.currentTarget.value; // get the new name from the text input
    setName(newName); // update state
    console.log("search page", 19, newName); // logs the name to the console
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-blue-200">
      <div className="bg-white text-black shadow-md rounded-lg p-8 max-w-md w-full">
        <h1 className="text-3xl font-bold text-center mb-4">
          Find a Summoner
        </h1>

        <div className="py-4">
        <p className="text-gray-600 text-center mb-4">
          <span>(This won't lead to anything)</span>
          <br />
          <span>Inspect element and go to "console" to see logging!</span>
        </p>
        </div>

        <div className="flex justify-center mb-4">
          <Input
            className="w-full"
            placeholder="Enter your Riot ID"
            value={name}
            onChange={onNameChange}
          />
        </div>
        
        <div className="flex justify-center">
          <Button component="a" href="/test2" onClick={handleButtonClick}>
            Search
          </Button>
        </div>
      </div>
    </div>
  );
};

export default TestPage;
