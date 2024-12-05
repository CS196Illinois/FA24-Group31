// app/loading-screen/page.tsx
'use client'

import {useState, useEffect} from 'react'
import {motion, AnimatePresence} from 'framer-motion'
import styles from './loading.module.css'

export default function LoadingScreen() {
    const [loading, setLoading] = useState(true)
    const [loadingText, setLoadingText] = useState('Finding your perfect match in Runeterra...')

    const loadingPhrases = [
        'Finding your perfect match in Runeterra...',
        'Consulting with Heimerdinger\'s matchmaking algorithm...',
        'Casting love spells with Lux...',
        'Asking Cupid Varus for advice...',
        'Searching the Crystal Rose garden...'
    ]

    useEffect(() => {
        let phraseIndex = 0

        // Rotate through loading phrases
        const textInterval = setInterval(() => {
            phraseIndex = (phraseIndex + 1) % loadingPhrases.length
            setLoadingText(loadingPhrases[phraseIndex])
        }, 3000)

        // Simulate API call
        const fetchData = async () => {
            try {
                const response = await fetch('https://api.yourdatingapp.com/auth') // Replace with your actual API endpoint
                const data = await response.json()

                setLoading(false)
            } catch (error) {
                console.error('Login failed:', error)
                // Handle error state
            }
        }

        fetchData()

        return () => clearInterval(textInterval)
    }, [])

    return (
        <div className={styles.container}>
            <AnimatePresence>
                {loading && (
                    <motion.div
                        className={styles.loadingContainer}
                        initial={{opacity: 0}}
                        animate={{opacity: 1}}
                        exit={{opacity: 0}}
                    >
                        <motion.div
                            className={styles.logoContainer}
                            animate={{
                                scale: [1, 1.2, 1],
                                rotate: [0, 360],
                            }}
                            transition={{
                                duration: 2,
                                repeat: Infinity,
                                ease: "easeInOut"
                            }}
                        >
                            {/* Replace with your app logo */}
                            <div className={styles.logo}>❤️</div>
                        </motion.div>

                        <motion.p
                            className={styles.loadingText}
                            initial={{opacity: 0}}
                            animate={{opacity: 1}}
                            transition={{duration: 0.5}}
                        >
                            {loadingText}
                        </motion.p>
                    </motion.div>
                )}
            </AnimatePresence>
        </div>
    )
}
