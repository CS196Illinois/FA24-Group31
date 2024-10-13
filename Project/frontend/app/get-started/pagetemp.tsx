// NOT BEING USED, EXAMPLE THAT JEFFREY MADE

'use client';

import Image from 'next/image';
import { Button, Input, Flex } from '@mantine/core';
import { useEffect, useState } from 'react';

const Test2Page = () => {
    const [count, setCount] = useState(0);

    useEffect(() => { // a useEffect hook is commonly used to run code when the component mounts, to load in data and update/remount the component when certain elements change
        console.log("result page", 10, "useEffect ran"); // logs that useEffect ran
        document.title = `You clicked ${count} times`;
    }, [count]); // here, count is a "dependency array", meaning useEffect will run again when count changes. This is a very useful property to keep in mind
    

    const handleButtonClick = () => {
        console.log("search page", 14, "Button clicked"); // logs the button click to the console
    };

    return (
        <div className="flex justify-center items-center min-h-screen bg-blue-200">
          <div className="bg-white text-black shadow-md rounded-lg p-8 max-w-md w-full">
            <h1 className="text-3xl font-bold text-center mb-4">
              Count: {count}
            </h1>
            
            <Flex
                direction="column"
                align="center"
                justify="center"
            >
            <div className="mb-4">
              <Button onClick={() => setCount(count + 1)}>
                Count++
              </Button>
            </div>
              <span>VV Click to go back VV</span>
            <div className="mt-4">
              <Button component="a" href="/test1" onClick={handleButtonClick}>
                Go Back
              </Button>
            </div>
            </Flex>
          </div>
        </div>
      );
};

export default Test2Page;
