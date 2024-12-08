/*
Create and populate "private" table
*/
CREATE TABLE IF NOT EXISTS private(discord_id TEXT PRIMARY KEY,  dob DATE, one_way_matched TEXT[], two_way_matched TEXT[]);
INSERT INTO private(discord_id, dob, one_way_matched, two_way_matched)
  VALUES
    ('n8UMm', '1999-01-08', NULL, NULL),
    ('FDhibSs', '1900-01-01', ARRAY ['n8UMm','HA8mzS'], ARRAY ['nu5cY4OaWdmfmziw3s','VAnmg3joCnW0Qdr9R']),
    ('HA8mzS', '1980-05-02', ARRAY ['VAnmg3joCnW0Qdr9R'], ARRAY ['n8UMm']),
    ('nu5cY4OaWdmfmziw3s', '2000-12-31', ARRAY ['n8UMm', 'FDhibSs'], NULL),
    ('VAnmg3joCnW0Qdr9R', '2005-10-24', NULL, ARRAY ['nu5cY4OaWdmfmziw3s', 'HA8mzS']);
SELECT * FROM private;
