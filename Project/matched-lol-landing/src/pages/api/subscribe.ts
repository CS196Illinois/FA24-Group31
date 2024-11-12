import type { NextApiRequest, NextApiResponse } from "next";
import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

type Data = {
	message: string;
};

export default async function handler(
	req: NextApiRequest,
	res: NextApiResponse<Data>,
) {
	if (req.method === "POST") {
		const { email } = req.body;

		// Basic email validation
		if (!email || !/\S+@\S+\.\S+/.test(email)) {
			res.status(400).json({ message: "Invalid email address." });
			return;
		}

		try {
			// Check if the email already exists
			const existingSubscriber = await prisma.subscriber.findUnique({
				where: { email },
			});

			if (existingSubscriber) {
				res.status(400).json({ message: "This email is already subscribed." });
				return;
			}

			// Create a new subscriber
			await prisma.subscriber.create({
				data: { email },
			});

			res.status(200).json({ message: "Successfully subscribed!" });
		} catch (error) {
			console.error("Error subscribing email:", error);
			res.status(500).json({ message: "Internal server error." });
		} finally {
			await prisma.$disconnect();
		}
	} else {
		res.setHeader("Allow", "POST");
		res.status(405).json({ message: `Method ${req.method} Not Allowed` });
	}
}
