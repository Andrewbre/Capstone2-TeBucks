BEGIN TRANSACTION;

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


COMMIT TRANSACTION;

BEGIN TRANSACTION;

DROP TABLE IF EXISTS transactions;

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
)

Rollback; 
commit;

select * from transactions join users using (user_id);
