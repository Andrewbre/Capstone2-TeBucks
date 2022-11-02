BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS requests;

CREATE TABLE users (
	user_id serial NOT NULL,
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	CONSTRAINT pk_users PRIMARY KEY (user_id),
	CONSTRAINT uq_username UNIQUE (username)
);

CREATE TABLE requests (
	user_id int NOT NULL,
	transaction_id int NOT NULL,
	recipient_id int NOT NULL,
	amount money NOT NULL,
	is_solved boolean NOT NULL,
	CONSTRAINT pk_requests PRIMARY KEY (user_id, transaction_id)
);

COMMIT TRANSACTION;

