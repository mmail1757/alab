create table PERSON (
    ID int primary key,
    FIRST_NAME varchar(100) not null,
    LAST_NAME varchar(100) not null
);

INSERT INTO PERSON VALUES (1, 'Sue', 'Bird');
INSERT INTO PERSON VALUES (2, 'Swin', 'Cash');
INSERT INTO PERSON VALUES (3, 'Charyl', 'Ford');

create table DOCUMENT (
    ID int primary key,
    DOC_TYPE varchar(100) not null,
    DOC_NUM varchar(100) not null,
    PERSON_ID int not null,
    CONSTRAINT FK_DOC_PERSON FOREIGN KEY (PERSON_ID) REFERENCES PERSON(ID)

);

INSERT INTO DOCUMENT VALUES (1, 'passport', '778-8777', 2);
INSERT INTO DOCUMENT VALUES (2, 'passport', '777-999', 1);
INSERT INTO DOCUMENT VALUES (3, 'driver license', '12 11111', 1);
INSERT INTO DOCUMENT VALUES (4, 'driver license', '12 11112', 3);

