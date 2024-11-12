import { getSession, useSession, signIn, signOut } from "next-auth/react";
import { useEffect, useState } from "react";
import {
	Container,
	Table,
	Text,
	Button,
	Loader,
	Center,
	Stack,
	Group,
	Notification,
} from "@mantine/core";
import {
	IconHeart,
	IconLogout,
	IconBrandGithub,
	IconHome,
	IconCopy,
	IconCheck,
	IconX,
} from "@tabler/icons-react";
import { useRouter } from "next/router";
import classes from "../styles/Admin.module.css";
import Link from "next/link";

interface Subscriber {
	email: string;
	subscribedAt: string;
}

const Admin = () => {
	const { data: session, status } = useSession();
	const [subscribers, setSubscribers] = useState<Subscriber[]>([]);
	const [loading, setLoading] = useState<boolean>(true);
	const [copySuccess, setCopySuccess] = useState(false);
	const [error, setError] = useState<string | null>(null);
	const router = useRouter();
	const copyEmails = () => {
		const emailList = subscribers.map((sub) => sub.email).join(",");
		navigator.clipboard.writeText(emailList).then(() => {
			setCopySuccess(true);
			setTimeout(() => setCopySuccess(false), 2000); // Reset after 2 seconds
		});
	};
	useEffect(() => {
		// Handle error from URL
		const errorType = router.query.error as string;
		if (errorType) {
			handleAuthError(errorType);
			// Remove error from URL without full page reload
			router.replace("/admin", undefined, { shallow: true });
		}
	}, [router.query.error]);

	useEffect(() => {
		if (status === "authenticated") {
			fetchSubscribers();
		}
	}, [status]);
	const handleAuthError = async (errorType: string) => {
		let errorMessage = "";

		switch (errorType) {
			case "AccessDenied":
				errorMessage =
					"You are not authorized to access this page. Please try with a different account.";
				break;
			case "AuthenticationFailed":
				errorMessage = "Login failed. Please try again.";
				break;
			default:
				errorMessage =
					"An error occurred during authentication. Please try again.";
		}

		setError(errorMessage);
		signOut({ redirect: false });

		clearGitHubSession();
	};

	const fetchSubscribers = async () => {
		try {
			const response = await fetch("/api/get-subscribers");
			const data = await response.json();
			setSubscribers(data.subscribers);
			setLoading(false);
		} catch (error) {
			console.error("Error fetching subscribers:", error);
			setLoading(false);
		}
	};
	const clearGitHubSession = () => {
		// Clear GitHub OAuth state from localStorage
		localStorage.removeItem("github-oauth-state");

		// Clear all session storage
		sessionStorage.clear();

		// Clear specific cookies
		document.cookie.split(";").forEach((c) => {
			document.cookie = c
				.replace(/^ +/, "")
				.replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/");
		});
	};

	const handleSignIn = async () => {
		setError(null);
		// Clear any existing sessions before starting new sign in
		await signOut({ redirect: false });
		clearGitHubSession();

		// Small delay to ensure cleanup is complete
		setTimeout(() => {
			signIn("github", { callbackUrl: "/admin" });
		}, 100);
	};

	if (status === "loading") {
		return (
			<div className={classes.wrapper}>
				<Center style={{ height: "100vh" }}>
					<Loader size="lg" color="white" />
				</Center>
			</div>
		);
	}
	const HomeButton = () => (
		<Link href="/" className={classes.linkReset}>
			<Button
				leftSection={<IconHome size={18} />}
				className={classes.homeButton}
			>
				Back to Home
			</Button>
		</Link>
	);

	if (!session) {
		return (
			<div className={classes.wrapper}>
				<Center style={{ height: "100vh" }}>
					<Stack align="center" gap="md">
						{error && (
							<Notification
								icon={<IconX size="1.1rem" />}
								color="red"
								className={classes.notification}
								onClose={() => setError(null)}
							>
								{error}
							</Notification>
						)}
						<Button
							leftSection={<IconBrandGithub size={20} />}
							onClick={handleSignIn}
							className={classes.githubButton}
							styles={{
								root: {
									height: "auto",
									padding: "16px 28px",
								},
								inner: {
									gap: "12px",
								},
							}}
						>
							Sign in with GitHub
						</Button>
						<HomeButton />
					</Stack>
				</Center>
			</div>
		);
	}

	return (
		<div className={classes.wrapper}>
			<Container size="md" className={classes.container}>
				<div className={classes.header}>
					<div className={classes.logo}>
						<IconHeart size={48} stroke={1.5} className={classes.logoIcon} />
						<Text className={classes.logoText}>matched.lol</Text>
					</div>
					<Group gap="md">
						<HomeButton />
						<Button
							onClick={copyEmails}
							leftSection={
								copySuccess ? <IconCheck size={18} /> : <IconCopy size={18} />
							}
							className={`${classes.actionButton} ${copySuccess ? classes.successButton : ""}`}
						>
							{copySuccess ? "Copied!" : "Copy Emails"}
						</Button>
						<Button
							onClick={() => signOut()}
							leftSection={<IconLogout size={18} />}
							className={classes.signOutButton}
						>
							Sign Out
						</Button>
					</Group>
				</div>
				<Stack className={classes.content}>
					<Text className={classes.title}>Subscriber List</Text>
					{loading ? (
						<Center>
							<Loader color="white" />
						</Center>
					) : (
						<div className={classes.tableWrapper}>
							<Table highlightOnHover className={classes.table}>
								<thead>
									<tr>
										<th>Email</th>
										<th>Subscribed At</th>
									</tr>
								</thead>
								<tbody>
									{subscribers.map((subscriber) => (
										<tr key={subscriber.email}>
											<td>{subscriber.email}</td>
											<td>
												{new Date(subscriber.subscribedAt).toLocaleString()}
											</td>
										</tr>
									))}
								</tbody>
							</Table>
						</div>
					)}
				</Stack>
			</Container>
		</div>
	);
};

export async function getServerSideProps(context: any) {
	const session = await getSession(context);

	if (session) {
		const allowedUsernames =
			process.env.ALLOWED_GITHUB_USERNAMES?.split(",") || [];
		// Use the GitHub username from the profile
		const githubUsername = session.user?.name; // or wherever GitHub username is stored

		if (!allowedUsernames.includes(githubUsername as string)) {
			// If user is not authorized, return error
			return {
				props: {
					error: "AccessDenied",
				},
			};
		}
	}

	return {
		props: {
			session,
		},
	};
}

export default Admin;
