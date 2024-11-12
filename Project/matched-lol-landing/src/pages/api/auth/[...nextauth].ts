import NextAuth from "next-auth";
import GitHubProvider from "next-auth/providers/github";
import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

export default NextAuth({
	providers: [
		GitHubProvider({
			clientId: process.env.GITHUB_CLIENT_ID || "",
			clientSecret: process.env.GITHUB_CLIENT_SECRET || "",
		}),
	],
	secret: process.env.NEXTAUTH_SECRET,
	callbacks: {
		async signIn({ user, account, profile }) {
			// Optionally, restrict sign-in to specific GitHub usernames or organizations
			const allowedUsernames =
				process.env.ALLOWED_GITHUB_USERNAMES?.split(",") || [];

			if (allowedUsernames.includes(profile?.login as string)) {
				return true;
			} else {
				return false; // Reject sign-in
			}
		},
	},
});
