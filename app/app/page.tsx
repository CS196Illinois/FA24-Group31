"use client";

import React from "react"
import { useState } from 'react';
import { Container, Group, Burger, Button } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
// import { MantineLogo } from '@mantinex/mantine-logo';
import classes from './HeaderSimple.module.css';
import { Title } from '@mantine/core';
import { Text } from '@mantine/core';
import { createTheme } from '@mantine/core';
import { MantineProvider } from "@mantine/core";

  const theme = createTheme({
  fontSizes: {
    xs: '0.5rem',
    sm: '0.75rem',
    md: '1rem',
    lg: '1.25rem',
    xl: '1.9rem', // Adjust this value to your desired size
  },
});



const links = [
  { link: '/about', label: 'Our Mission  ' },
  { link: '/pricing', label: 'About Us  ' },
  { link: '/learn', label: 'Community  ' },
];

export function HeaderSimple() {
  const [opened, { toggle }] = useDisclosure(false);
  const [active, setActive] = useState(links[0].link);

  const items = links.map((link) => (
    <a
      key={link.label}
      href={link.link}
      className={classes.link}
      data-active={active === link.link || undefined}
      onClick={(event) => {
        event.preventDefault();
        setActive(link.link);
      }}
    >
      {link.label}
    </a>
  ));

  return (
    <header className={classes.header}>
      <Container size="md" className={classes.inner}>
        <Text size="xl" fw={600} variant="gradient" gradient={{from: 'red', to: 'blue', deg:90}}> Matched.gg </Text>
        <Group gap={5} visibleFrom="xs">
          {items}
        </Group>

        <Burger opened={opened} onClick={toggle} hiddenFrom="xs" size="sm" />
      </Container>
    </header>
  );
}




export default function Home() {
  return (
    // test 
    <MantineProvider theme={theme}> 
      <div>
          <HeaderSimple/>
          <section className={classes.section}>
          <Container className={classes.content}>
            <Title order={1} size="h1"> A League of Legends themed matching app. </Title>
            <p>Find your perfect partner, in-game or in-real-life.</p>
            <Button className={classes.getStartedButton} mt="xl"> {/* Add a margin-top for spacing */}
              Get Started
            </Button>
          </Container>
        </section>
      </div>
    </MantineProvider>
    
  );
}
