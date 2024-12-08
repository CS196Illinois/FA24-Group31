// pages/api/get-subscribers.ts

import type { NextApiRequest, NextApiResponse } from "next";
import { getSession } from "next-auth/react";
import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

type Data =
	| {
			subscribers: {
				email: string;
				subscribedAt: string;
			}[];
	  }
	| {
			message: string;
	  };

export default async function handler(
	req: NextApiRequest,
	res: NextApiResponse<Data>,
) {
	const session = await getSession({ req });

	if (!session) {
		res.status(401).json({ message: "Unauthorized" });
		return;
	}

	if (req.method === "GET") {
		try {
			const subscribers = await prisma.subscriber.findMany({
				orderBy: {
					subscribedAt: "desc",
				},
			});

			// Format the subscribedAt date
			const formattedSubscribers = subscribers.map((subscriber) => ({
				email: subscriber.email,
				subscribedAt: new Date(subscriber.subscribedAt).toLocaleString(),
			}));

			res.status(200).json({ subscribers: formattedSubscribers });
		} catch (error) {
			console.error("Error fetching subscribers:", error);
			res.status(500).json({ message: "Internal server error." });
		} finally {
			await prisma.$disconnect();
		}
	} else {
		res.setHeader("Allow", "GET");
		res.status(405).json({ message: `Method ${req.method} Not Allowed` });
	}
}
