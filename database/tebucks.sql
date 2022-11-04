BEGIN TRANSACTION;

DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS transfers;
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

CREATE TABLE transfers (
	

	transfer_id serial NOT NULL,
	transfer_status varchar (30),
	user_id int NOT NULL ,
	logged_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	recipient_id int NOT NULL,
	amount money NOT NULL, 
	irs_eligible boolean, 
	transfer_type varchar(10), 
	is_completed boolean, 
	
	CONSTRAINT pk_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT fk_transfers_users FOREIGN KEY (user_id) references users(user_id)
);

CREATE TABLE requests (
	user_id int NOT NULL,
	transfer_id int NOT NULL,
	recipient_id int NOT NULL,
	amount money NOT NULL,
	is_solved boolean NOT NULL,
	
	CONSTRAINT pk_requests PRIMARY KEY (user_id, transfer_id),
	CONSTRAINT fk_requests_users FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT fk_requests_transfers FOREIGN KEY (transfer_id) REFERENCES transfers(transfer_id)
);

COMMIT;

select * from transfers;

