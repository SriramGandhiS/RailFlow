-- Add latitude and longitude columns to stations
ALTER TABLE stations ADD COLUMN latitude DOUBLE PRECISION;
ALTER TABLE stations ADD COLUMN longitude DOUBLE PRECISION;

-- Populate default coordinate values for existing records (e.g., Chennai Central)
UPDATE stations SET latitude = 13.0827, longitude = 80.2707 WHERE code = 'MAS';

-- Enforce NOT NULL constraint on coordinates
ALTER TABLE stations ALTER COLUMN latitude SET NOT NULL;
ALTER TABLE stations ALTER COLUMN longitude SET NOT NULL;

-- Add capacities to vehicles
ALTER TABLE vehicles ADD COLUMN sleeper_capacity INT DEFAULT 0 NOT NULL;
ALTER TABLE vehicles ADD COLUMN ac_capacity INT DEFAULT 0 NOT NULL;
ALTER TABLE vehicles ADD COLUMN general_capacity INT DEFAULT 0 NOT NULL;
