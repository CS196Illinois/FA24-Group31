// types.ts
export interface Profile {
    name: string;
    age: number;
    image: string;
    bio: string;
    pronouns: string[];
    riotId: string;
    discordId: string;
    description?: string;
    roles: string[];
    rank: string;
}