// pages/api/auth/[...nextauth].ts
import NextAuth from "next-auth";
import GitHubProvider from "next-auth/providers/github";

export default NextAuth({
	providers: [
		GitHubProvider({
			clientId: process.env.GITHUB_CLIENT_ID || "",
			clientSecret: process.env.GITHUB_CLIENT_SECRET || "",
		}),
	],
	secret: process.env.NEXTAUTH_SECRET,
	callbacks: {
		async signIn({ profile }) {
			const allowedUsernames =
				process.env.ALLOWED_GITHUB_USERNAMES?.split(",") || [];

			if (allowedUsernames.includes(profile?.name as string)) {
				return true;
			}

			// Return error and clear session
			return "/admin?error=AccessDenied";
		},
	},
	pages: {
		error: "/admin",
		signIn: "/admin",
	},
	session: {
		maxAge: 24 * 60 * 60, // 24 hours
		updateAge: 24 * 60 * 60, // 24 hours
	},
});
