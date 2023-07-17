CREATE TABLE version (
	id INTEGER PRIMARY KEY,
	fileName TEXT NOT NULL,
	date_time TEXT NOT NULL,
	success INTEGER CHECK(success == 0 OR success == 1)
)
/
CREATE TABLE property (
	name TEXT PRIMARY KEY,
	value TEXT NOT NULL
)
/
insert into property values("version", "0.1");
/
insert into property values("appname", "Easyword");
/

select * from property;
/


