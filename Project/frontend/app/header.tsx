"use client";

import React, { useState } from "react";
import { Container, Group, Burger, Button } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
import classes from './HeaderSimple.module.css';
import { Text } from '@mantine/core';
import { createTheme } from '@mantine/core';
import { MantineProvider } from "@mantine/core";
import Link from 'next/link';

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

// Updated links for smooth scrolling
const links = [
  { link: '#our-mission', label: 'Our Mission' },
  { link: '#about-us', label: 'About Us' },
  { link: '#community', label: 'Community' },
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
        // Smooth scroll to the target section
        event.preventDefault();
        const target = document.querySelector(link.link);
        if (target) {
          window.scrollTo({
            top: target.getBoundingClientRect().top + window.pageYOffset - 100, // Adjust the offset as needed
            behavior: 'smooth'
          });
          setActive(link.link); // Set the active state
        }
      }}
    >
      {link.label}
    </a>
  ));

  return (
    <header className={classes.header}>
      <Container size="md" className={classes.inner}>
        <MantineProvider theme={theme}>
          <Link href="/">
            <Text size="xl" fw={600} variant="gradient" gradient={{ from: 'red', to: 'blue', deg: 90 }}>
              Matched.lol
            </Text>
          </Link>
          <Group gap={5} visibleFrom="xs">
            {items}
          </Group>

          <Burger opened={opened} onClick={toggle} hiddenFrom="xs" size="sm" />
        </MantineProvider>
      </Container>
    </header>
  );
}
