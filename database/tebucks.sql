BEGIN TRANSACTION;

DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id serial NOT NULL,
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	balance money Not null default 1000.00,
	CONSTRAINT pk_users PRIMARY KEY (user_id),
	CONSTRAINT uq_username UNIQUE (username)
);

CREATE TABLE transactions (
	
	transaction_id serial NOT NULL, 
	user_id int NOT NULL ,
	logged_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	amount money, 
	irs_eligible boolean, 
	transaction_type varchar(10), 
	is_completed boolean, 
	
	CONSTRAINT pk_transaction PRIMARY KEY (transaction_id),
	CONSTRAINT fk_transaction_users FOREIGN KEY (user_id) references users(user_id)
);

CREATE TABLE requests (
	user_id int NOT NULL,
	transaction_id int NOT NULL,
	recipient_id int NOT NULL,
	amount money NOT NULL,
	is_solved boolean NOT NULL,
	
	CONSTRAINT pk_requests PRIMARY KEY (user_id, transaction_id),
	CONSTRAINT fk_requests_users FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT fk_requests_transactions FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);

COMMIT ;
