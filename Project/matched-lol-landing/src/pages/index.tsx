// pages/index.tsx

import React, { useState } from "react";
import {
	Container,
	Button,
	Text,
	TextInput,
	Box,
	Notification,
} from "@mantine/core";

import classes from "../styles/Home.module.css";
import { HeaderSimple } from "../components/HeaderSimple";
import axios from "axios";

const Home: React.FC = () => {
	const [email, setEmail] = useState("");
	const [notification, setNotification] = useState<{
		type: "success" | "error";
		message: string;
	} | null>(null);

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();

		try {
			const response = await axios.post("/api/subscribe", { email });
			if (response.status === 200) {
				setNotification({
					type: "success",
					message: "Thank you for signing up!",
				});
				setEmail("");
			} else if (response.status == 400) {
				setNotification({
					type: "error",
					message: "You've already signed up.",
				});
			} else {
				setNotification({
					type: "error",
					message: "Something went wrong. Please try again.",
				});
			}
		} catch (error: unknown) {
			const errorMessage =
				error instanceof Error
					? error.message
					: "Something went wrong. Please try again.";

			setNotification({
				type: "error",
				message: errorMessage,
			});
		}
	};

	return (
		<div>
			<section className={classes.section} id="hero">
				<Container className={classes.content}>
					{/* Hero text */}
					<Text size="xxl" fw={700} c="white" ta="center">
						Coming Soon
					</Text>
					<Text size="md" fw={500} c="white" ta="center" mt="sm">
						We&apos;re working hard to bring you the best dating experience.
					</Text>

					{/* Sign-Up Form */}
					<Box
						component="form"
						onSubmit={handleSubmit}
						mt="xl"
						style={{ width: "100%", maxWidth: 400, margin: "0 auto" }}
					>
						<TextInput
							placeholder="Enter your email"
							required
							value={email}
							onChange={(e) => setEmail(e.currentTarget.value)}
							mb="sm"
						/>
						<Button type="submit" fullWidth>
							Sign Up for Updates
						</Button>
					</Box>

					{/* Notification */}
					{notification && (
						<Notification
							title={notification.type === "success" ? "Success" : "Error"}
							color={notification.type}
							onClose={() => setNotification(null)}
							mt="md"
						>
							{notification.message}
						</Notification>
					)}
				</Container>
			</section>
		</div>
	);
};

export default Home;
