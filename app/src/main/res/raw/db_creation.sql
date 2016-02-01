CREATE TABLE searchHistory (
    _id	INTEGER PRIMARY KEY AUTOINCREMENT,
	query	TEXT NOT NULL,
	queryName TEXT NOT NULL,
	longitude REAL NOT NULL,
	latitude REAL NOT NULL,
	rent	INTEGER DEFAULT 0,
    sell	INTEGER DEFAULT 0,
	rentResults INTEGER DEFAULT 0,
	sellResults INTEGER DEFAULT 0,
	rawResults	TEXT,
	timestamp INTEGER NOT NULL UNIQUE
);

CREATE TABLE favorite {
	_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL,
	rent INTEGER DEFAULT 0,
	cityName TEXT NOT NULL,
	postalCode TEXT NOT NULL,
	description TEXT,
	address TEXT,
	ownerMail TEXT,
	latitude FLOAT NOT NULL,
	longitude FLOAT NOT NULL,
	squareFootage FLOAT NOT NULL,
	price FLOAT NOT NULL,
	ownerPhoneNumber TEXT
};
