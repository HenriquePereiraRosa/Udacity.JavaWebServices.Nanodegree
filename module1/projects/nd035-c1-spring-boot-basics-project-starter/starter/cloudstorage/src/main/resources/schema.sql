CREATE TABLE IF NOT EXISTS USERS (
  id INT PRIMARY KEY auto_increment,
  username VARCHAR(50),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(50),
  lastname VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS NOTES (
    id INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(50),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(id)
);

CREATE TABLE IF NOT EXISTS FILES (
    id INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize INT,
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(id)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    id INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (50),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(id)
);