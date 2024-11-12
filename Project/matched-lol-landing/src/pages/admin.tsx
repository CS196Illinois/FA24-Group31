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
} from "@mantine/core";
import {
	IconHeart,
	IconLogout,
	IconBrandGithub,
	IconHome,
	IconCheck,
	IconCopy,
} from "@tabler/icons-react";
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

	const copyEmails = () => {
		const emailList = subscribers.map((sub) => sub.email).join(",");
		navigator.clipboard.writeText(emailList).then(() => {
			setCopySuccess(true);
			setTimeout(() => setCopySuccess(false), 2000); // Reset after 2 seconds
		});
	};

	useEffect(() => {
		if (status === "authenticated") {
			fetchSubscribers();
		}
	}, [status]);

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

	if (status === "loading") {
		return (
			<div className={classes.wrapper}>
				<Center style={{ height: "100vh" }}>
					<Loader size="lg" color="white" />
				</Center>
			</div>
		);
	}

	if (!session) {
		return (
			<div className={classes.wrapper}>
				<Center style={{ height: "100vh" }}>
					<Stack align="center" gap="md">
						<Button
							leftSection={<IconBrandGithub size={20} />}
							onClick={() => signIn("github")}
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

	return {
		props: {
			session,
		},
	};
}

export default Admin;
