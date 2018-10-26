USE stockwatchdb;

/*Remove existing holidays*/
DELETE FROM trading_holidays;

/*2017 list of holidays for NSE*/
INSERT INTO trading_holidays(`market`,`holidaydate`)
	VALUES 	('NSE' , STR_TO_DATE('26-01-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('24-02-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('13-03-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('04-04-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('14-04-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('01-05-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('26-06-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('15-08-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('25-08-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('02-10-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('19-10-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('20-10-2017', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('25-12-2017', '%d-%m-%Y') );
			
/*2018 list of holidays for NSE*/
INSERT INTO trading_holidays(`market`,`holidaydate`)
	VALUES 	('NSE' , STR_TO_DATE('26-01-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('13-02-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('02-03-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('29-03-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('30-03-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('01-05-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('15-08-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('22-08-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('13-09-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('20-09-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('02-10-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('18-10-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('07-11-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('08-11-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('23-11-2018', '%d-%m-%Y') ),
			('NSE' , STR_TO_DATE('25-12-2018', '%d-%m-%Y') );