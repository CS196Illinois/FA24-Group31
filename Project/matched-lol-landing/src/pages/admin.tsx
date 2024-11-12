import { getSession, useSession, signIn, signOut } from "next-auth/react";
import { useEffect, useState } from "react";
import { Container, Table, Text, Button, Loader, Center } from "@mantine/core";
import { createTheme, MantineProvider } from "@mantine/core";
import classes from "../styles/Admin.module.css";

interface Subscriber {
	email: string;
	subscribedAt: Date;
}

const theme = createTheme({
	fontSizes: {
		xs: "0.5rem",
		sm: "0.75rem",
		md: "1rem",
		lg: "1.25rem",
		xl: "1.9rem",
		xxl: "2.5rem",
	},
});

const Admin = () => {
	const { data: session, status } = useSession();
	const [subscribers, setSubscribers] = useState<Subscriber[]>([]);
	const [loading, setLoading] = useState<boolean>(true);

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

	if (status === "loading") {
		return (
			<Center style={{ height: "100vh" }}>
				<Loader size="lg" />
			</Center>
		);
	}

	if (!session) {
		return (
			<Center style={{ height: "100vh" }}>
				<Button onClick={() => signIn("github")}>Sign in with GitHub</Button>
			</Center>
		);
	}

	return (
		<MantineProvider theme={theme}>
			<Container className={classes.container} mt="xl">
				<Text size="xl" fw={700} ta="center" mb="md">
					Admin Dashboard
				</Text>
				<Button onClick={() => signOut()} mb="md">
					Sign Out
				</Button>
				{loading ? (
					<Loader />
				) : (
					<Table highlightOnHover>
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
									<td>{subscriber.subscribedAt.toLocaleString()}</td>
								</tr>
							))}
						</tbody>
					</Table>
				)}
			</Container>
		</MantineProvider>
	);
};

export async function getServerSideProps(context: never) {
	const session = await getSession(context);

	if (!session) {
		return {
			props: {},
		};
	}

	return {
		props: { session },
	};
}

export default Admin;
