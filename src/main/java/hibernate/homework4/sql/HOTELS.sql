CREATE TABLE HOTELS(
ID NUMBER NOT NULL,
HOTEL_NAME NVARCHAR2(50) NOT NULL,
HOTEL_COUNTRY NVARCHAR2(50),
HOTEL_CITY NVARCHAR2(50),
HOTEL_STREET NVARCHAR2(50),
CONSTRAINT HOTEL_ID PRIMARY KEY(ID)
);