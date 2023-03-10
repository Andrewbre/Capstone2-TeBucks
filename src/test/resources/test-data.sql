BEGIN TRANSACTION;

DROP TABLE IF EXISTS transfers;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id serial NOT NULL,
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	balance money NOT NULL DEFAULT 1000.00,
	CONSTRAINT pk_users PRIMARY KEY (user_id),
	CONSTRAINT uq_username UNIQUE (username)
);

CREATE TABLE transfers (

	transfer_id serial NOT NULL,
	user_id int NOT NULL ,
	recipient_id int NOT NULL,
	transfer_status varchar(20),
	logged_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	amount money,
	irs_eligible boolean,
	transfer_type varchar(10),
	is_completed boolean,

	CONSTRAINT pk_transaction PRIMARY KEY (transfer_id),
	CONSTRAINT fk_transaction_users FOREIGN KEY (user_id) references users(user_id)

);
INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER'); -- 1
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER'); -- 2
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER'); -- 3

INSERT INTO transfers (user_id, recipient_id, amount, transfer_type, transfer_status) VALUES (1, 2, 750.00, 'Send','Approved'); -- 1
INSERT INTO transfers (user_id, recipient_id, amount, transfer_type, transfer_status) VALUES (2, 1, 50.60,'Request', 'Rejected'); -- 2
INSERT INTO transfers (user_id, recipient_id, amount, transfer_type, transfer_status) VALUES (3, 2, 1001.00, 'Send', 'Pending'); -- 3

COMMIT;

