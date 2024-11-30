/*
Create and populate "public" table
*/
CREATE TABLE IF NOT EXISTS public(discord_id TEXT PRIMARY KEY, riot_id TEXT, first_name TEXT, last_name TEXT, pronouns TEXT, description TEXT, roles TEXT [], rank TEXT);
INSERT INTO public(discord_id, riot_id, first_name, last_name, pronouns, description, roles, rank)
  VALUES
    ('n8UMm', 'iUR6fvHAPmAqg#Tizc4', 'Joe', 'Black', 'they/them', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', ARRAY ['top','jungle','middle','bottom','suppport'], 'challenger'),
    ('FDhibSs', 'd0VEfX5j#l9ade', 'Bill', 'Adams', 'she/her', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', ARRAY ['top','jungle','middle','bottom'], 'silver'),
    ('HA8mzS', 'aeVJ2BzlLQlZ#S3u', 'Bill', 'White', 'any/any', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', ARRAY ['jungle','middle','bottom','suppport'], 'emerald'),
    ('nu5cY4OaWdmfmziw3s', 'ZsNeK#HLAWz', 'Rob', 'White', 'they/them', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', ARRAY ['jungle','middle','bottom'], 'master'),
    ('VAnmg3joCnW0Qdr9R', 'C2hGFJvmFZuX1z7#D0c', 'John', 'Lincoln', 'he/him', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', ARRAY ['top','jungle','middle','bottom','suppport'], 'grandmaster');
SELECT * FROM public;
