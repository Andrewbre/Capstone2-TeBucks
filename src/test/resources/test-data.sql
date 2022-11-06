BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id serial NOT NULL,
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	CONSTRAINT pk_users PRIMARY KEY (user_id),
	CONSTRAINT uq_username UNIQUE (username)
);

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER'); -- 1
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER'); -- 2
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER'); -- 3

COMMIT TRANSACTION;

BEGIN TRANSACTION;

);

DROP TABLE IF EXISTS transactions2;

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

INSERT INTO transactions (transferId, userTo, userFrom, amount, transferType, transferStatus) VALUES ('TransferId','User1', 'User4', 'Amount','Send','Approved'); -- 1
INSERT INTO transactions (transferId, userTo, userFrom, amount, transferType, transferStatus) VALUES ('TransferId', 'User2', 'User5', 'Amount','Request', 'Pending'); -- 2
INSERT INTO transactions (TransferId, userTo, userFrom, amount, transferType, transferStatus) VALUES ('TransferId', 'User3', 'User6', 'Amount','Send', 'Rejected'); -- 3


COMMIT TRANSACTION;

