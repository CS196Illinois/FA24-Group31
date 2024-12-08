// pages/index.js
'use client'
import SwipeCard from '../../components/SwipeCard';
import {HeaderSimple} from '../header';
import { Link } from 'next/link';

export default function Home() {
    return (
        <>
            <HeaderSimple/>
            <SwipeCard/>
        </>
    );
}
