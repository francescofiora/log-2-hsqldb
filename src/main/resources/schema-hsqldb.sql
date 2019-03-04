DROP TABLE eventlog IF EXISTS;
CREATE TABLE eventlog  (
	id VARCHAR(50),
    estart INT,
    eend INT,
    duration INT,
    etype VARCHAR(200),
    host VARCHAR(200),
    alert CHAR(1),
	PRIMARY KEY (id)
);
