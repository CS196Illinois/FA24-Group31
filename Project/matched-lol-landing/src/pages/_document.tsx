// pages/_document.tsx
import { Html, Head, Main, NextScript } from "next/document";

export default function Document() {
	return (
		<Html lang="en">
			<Head>
				<meta name="robots" content="noindex,nofollow" />
				<meta
					httpEquiv="Cache-Control"
					content="no-cache, no-store, must-revalidate"
				/>
				<meta httpEquiv="Pragma" content="no-cache" />
				<meta httpEquiv="Expires" content="0" />
			</Head>
			<body>
				<Main />
				<NextScript />
			</body>
		</Html>
	);
}
