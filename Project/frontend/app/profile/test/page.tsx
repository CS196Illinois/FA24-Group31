
"use client";

import { MantineProvider, Text, createTheme, Space, Container } from "@mantine/core";
import React from "react"
import { useState } from 'react';
import { HeaderSimple } from "../../header";
import { Select, TextInput, Button } from '@mantine/core';
import Link from 'next/link';




// List items 
const champions : string[] = ['Azir'];
const ranks : string[] = ['Unranked', 'Iron', 'Bronze'];
const roles : string[] = ['Top', 'Jungle', 'Mid', 'Bot', 'Support']


export default function test() {
    const [description, setDescription] = useState<string>('');
     

    const handledescriptionChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;
        setDescription(value);
    };

    return (
        <div>
            <HeaderSimple/>
            {/* <Container style={{justifyContent: 'center', padding: '5em'}}>
                <Text size='lg'> Additional Profile Items </Text>
            </Container> */}
            
        

            {/* Put any other potential fields here */}
            <Container min-height='500px' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '40em', flexDirection: 'column' }}> 
                <Text size='lg' fw={550}> Additional Profile Items</Text>
                {/* Searchable dropdown list */}
                <Select
                    label="Your favorite champions"
                    placeholder="Search here"
                    data={champions}
                    searchable
                    nothingFoundMessage="Nothing found"
                    style={{ marginBottom: '20px', width: '100%' }}
                />

                {/* Unsearchable deselectable list */}
                <Select
                    label="Rank"
                    placeholder="Choose here"
                    data={ranks}
                    defaultValue="React"
                    allowDeselect
                    mt="md"
                    style={{ marginBottom: '20px', width: '100%' }}
                />

                <Select
                    label="Role"
                    placeholder="Choose here"
                    data={roles}
                    defaultValue="React"
                    allowDeselect
                    mt="md"
                    style={{ marginBottom: '20px', width: '100%' }}
                />

                <TextInput
                label="Description"
                placeholder="Write something about yourself!"
                value={description}
                onChange={handledescriptionChange} 
                style={{ marginBottom: '20px', width: '100%' }}
                />
                <Link href="/get-started/get-started"> 
                <Button component={Link} href="/get-started" mt="xl">
                    Save information
                </Button>       
                </Link>


            </Container>
        </div>
    );
}