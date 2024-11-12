import React, { useState, FormEvent } from "react";
import {
	Container,
	Button,
	Text,
	TextInput,
	Notification,
} from "@mantine/core";
import {
	IconHeart,
	IconMail,
	IconCheck,
	IconX,
	IconLock,
} from "@tabler/icons-react";
import styles from "../styles/Home.module.css";
import Link from "next/link";

const Landing = () => {
	const [email, setEmail] = useState("");
	const [submitted, setSubmitted] = useState(false);
	const [notification, setNotification] = useState({ message: "", type: "" });

	const handleSubmit = async (e: FormEvent) => {
		e.preventDefault();
		try {
			const response = await fetch("/api/subscribe", {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({ email }),
			});
			const data = await response.json();
			if (response.ok) {
				setNotification({ message: data.message, type: "success" });
			} else {
				setNotification({ message: data.message, type: "error" });
			}
		} catch (error) {
			setNotification({
				message: "An error occurred. Please try again.",
				type: "error",
			});
		}
		setSubmitted(true);
		setEmail("");
		setTimeout(() => setSubmitted(false), 3000);
	};

	return (
		<div className={styles.wrapper}>
			<Container size="xl" className={styles.container}>
				<div className={styles.content}>
					<div className={styles.logo}>
						<IconHeart size={48} stroke={1.5} className={styles.logoIcon} />
						<Text className={styles.logotext}>matched.lol</Text>
					</div>

					<Text className={styles.title}>
						Coming Soon! Sign up for updates.
					</Text>

					<Text className={styles.description}>
						From your favorite CS124H group, get ready for matched.lol, the
						ultimate platform to find your perfect League of Legends match!
					</Text>

					<form onSubmit={handleSubmit} className={styles.form}>
						<TextInput
							placeholder="enter your email"
							value={email}
							onChange={(e) => setEmail(e.currentTarget.value)}
							className={styles.input}
							type="email"
							styles={{
								input: {
									fontFamily: "Comfortaa, sans-serif",
									"&::placeholder": {
										fontFamily: "Comfortaa, sans-serif",
									},
								},
							}}
						/>
						<Button type="submit" className={styles.button}>
							sign up for updates
						</Button>
					</form>

					{submitted && (
						<Notification
							className={styles.notification}
							icon={
								notification.type === "success" ? (
									<IconCheck size={18} />
								) : (
									<IconX size={18} />
								)
							}
							color={notification.type === "success" ? "green" : "red"}
							onClose={() => setSubmitted(false)}
						>
							{notification.message}
						</Notification>
					)}
				</div>

				<Link href="/admin" className={styles.adminLink}>
					<button className={styles.adminButton}>
						<span className={styles.adminButtonContent}>
							<IconLock size={18} />
							<span className={styles.adminText}>Admin Dashboard Log In</span>
						</span>
					</button>
				</Link>
			</Container>
		</div>
	);
};

export default Landing;
