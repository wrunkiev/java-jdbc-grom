CREATE TABLE ROOMS(
ID NUMBER NOT NULL ,
ROOM_NUMBER_OF_GUESTS NUMBER NOT NULL,
ROOM_PRICE DECIMAL(10,2) NOT NULL,
ROOM_BREAKFAST_INCLUDED NUMBER(1) NOT NULL,
ROOM_PETS_ALLOWED NUMBER(1) NOT NULL,
ROOM_DATE_AVAILABLE_FROM TIMESTAMP NOT NULL,
ROOM_HOTEL_ID NUMBER,
CONSTRAINT ROOM_ID PRIMARY KEY(ID),
CONSTRAINT ROOM_HOTEL_ID_FK FOREIGN KEY(ROOM_HOTEL_ID) REFERENCES HOTELS(ID)
);