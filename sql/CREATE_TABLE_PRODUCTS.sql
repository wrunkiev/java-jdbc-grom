CREATE TABLE PRODUCTS(
ID NUMBER NOT NULL ENABLE,
CONSTRAINT PRODUCT_PK PRIMARY KEY(ID),
NAME NVARCHAR2(20) NOT NULL,
DESCRIPTION CLOB,
PRICE NUMBER NOT NULL
);