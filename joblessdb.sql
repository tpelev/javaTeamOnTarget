CREATE TABLE places (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   PLACES_NAME NVARCHAR(50) NOT NULL
);

CREATE TABLE categories (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   CATEGORIE_NAME NVARCHAR(50) NOT NULL
);

CREATE TABLE admins (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   LOGIN_NAME NVARCHAR(50) NOT NULL,
   LOGIN_PASSWORD NVARCHAR(200) NOT NULL,
   SAULT NVARCHAR(500) NOT NULL,
   TOKEN NVARCHAR(500) NOT NULL
);

CREATE TABLE accounters (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   LOGIN_NAME NVARCHAR(50) NOT NULL,
   LOGIN_PASSWORD NVARCHAR(200) NOT NULL,
   SAULT NVARCHAR(500) NOT NULL,
   TOKEN NVARCHAR(500) NOT NULL
);

CREATE TABLE users (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   LOGIN_NAME NVARCHAR(50) NOT NULL,
   LOGIN_PASSWORD NVARCHAR(200) NOT NULL,
   SAULT NVARCHAR(500) NOT NULL,
   TOKEN NVARCHAR(500) NOT NULL
);

CREATE TABLE user_profiles (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   USER_ID INT NOT NULL,
   FIRST_NAME NVARCHAR(100) NOT NULL,
   LAST_NAME NVARCHAR(100) NOT NULL,
   EMAIL NVARCHAR(50) NOT NULL,
   FOREIGN KEY (USER_ID) REFERENCES users (ID) on delete cascade on update cascade
);

CREATE TABLE companies (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   LOGIN_NAME NVARCHAR(50) NOT NULL,
   LOGIN_PASSWORD NVARCHAR(200) NOT NULL,
   SAULT NVARCHAR(500) NOT NULL,
   TOKEN NVARCHAR(500) NOT NULL
);

CREATE TABLE company_profiles (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   COMPANY_ID INT NOT NULL,
   COMPANY_NAME NVARCHAR(100) NOT NULL,
   ADRESS NVARCHAR(500) NOT NULL,
   MOL NVARCHAR(50) NOT NULL,
   EIK NVARCHAR(20) NOT NULL,
   EMAIL NVARCHAR(50) NOT NULL,
   COMPANY_TYPE NVARCHAR (100) NOT NULL,
   STATUS INT NOT NULL,
   FOREIGN KEY (COMPANY_ID) REFERENCES companies (ID) on delete cascade on update cascade
);

CREATE TABLE owner (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   COMPANY_NAME NVARCHAR(100) NOT NULL,
   ADRESS NVARCHAR(500) NOT NULL,
   MOL NVARCHAR(50) NOT NULL,
   EIK NVARCHAR(20) NOT NULL,
   EMAIL NVARCHAR(50) NOT NULL,
   IBAN NVARCHAR(50) NOT NULL,
   BIC NVARCHAR(20) NOT NULL,
   PHONE_NUMBER NVARCHAR(20) NOT NULL
);

CREATE TABLE advertisements (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CATEGORY_ID INT NOT NULL,
	TITLE NVARCHAR(100) NOT NULL,
   COMPANY_ID INT NOT NULL,
   CONTENT NVARCHAR(4000) NOT NULL,
   TEST NVARCHAR(4000),
   PRICE DOUBLE NOT NULL,
   ACTIVITY_DAYS INT NOT NULL,
   EXPIRATION_DATE DATE,
   PLACE_ID INT NOT NULL,
   IS_VIP BOOLEAN NOT NULL,
   IS_APPROVED BOOLEAN NOT NULL,
   IS_PAID BOOLEAN NOT NULL,
   IS_EXPIRED BOOLEAN NOT NULL,
   FOREIGN KEY (CATEGORY_ID) REFERENCES  categories (ID) on delete cascade on update cascade,
   FOREIGN KEY (COMPANY_ID) REFERENCES company_profiles (ID) on delete cascade on update cascade,
   FOREIGN KEY (PLACE_ID) REFERENCES places (ID) on delete cascade on update cascade
);

CREATE TABLE INVOICES (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	INVOICE_DATE DATE NOT NULL,
   OWNER_ID INT NOT NULL,
   COMPANY_ID INT NOT NULL,
   ADV_ID INT NOT NULL,
   QUANTITY DOUBLE NOT NULL,
   DISCOUNT DOUBLE NOT NULL,
   PRICE DOUBLE NOT NULL,
   TAX DOUBLE NOT NULL,
   TAX_AMMOUNT DOUBLE NOT NULL,
   TOTAL_PRICE DOUBLE NOT NULL,
   EVENT_DATE DATE NOT NULL,
   LATE_PAYMENT INT NOT NULL,
   IS_CASH BOOLEAN NOT NULL,
   IS_PAYED BOOLEAN NOT NULL,
   DUE_PAYMENT BOOLEAN NOT NULL,
   FOREIGN KEY (OWNER_ID) REFERENCES  OWNER (ID) on update cascade,
   FOREIGN KEY (COMPANY_ID) REFERENCES company_profiles (ID) on delete cascade on update cascade
);

alter table INVOICES auto_increment=1000000001;

INSERT INTO owner (COMPANY_NAME, ADRESS, MOL, EIK, EMAIL, IBAN, BIC, PHONE_NUMBER)
VALUES 
   ('Jobless LTD.', 'Sofia, Vitosha blvd. 1','Bai Ivan', '12345689', 'shefa@jobless.com', 'BG12BPBI70000123412345',
   'BPBIBGSF', '+359888123456');
   
INSERT INTO places (PLACES_NAME)
VALUES 
   ('Sofia'), 
   ('Plovdiv'),
   ('Varna'), 
   ('Burgas'),
   ('Ruse'),
   ('Pleven'), 
   ('Stara Zagora'),
   ('Lovech'), 
   ('Gabrovo'),
   ('Montana'),
   ('Lom'), 
   ('Blagoevgrad'),
   ('Pernik'),
   ('Haskovo'), 
   ('Yambol'),
   ('Sliven');

INSERT INTO categories (CATEGORIE_NAME)
VALUES 
   ('IT'), 
   ('Law'),
   ('Finance'), 
   ('Accounting'),
   ('Tourism'),
   ('Advertising'), 
   ('Entertainment'),
   ('Transport'), 
   ('Energetics'),
   ('Health'),
   ('Insurance'), 
   ('Marketing'),
   ('Security'),
   ('Sport'), 
   ('HR'),
   ('Education'),
   ('Call Centers');
   
INSERT INTO companies (LOGIN_NAME, LOGIN_PASSWORD, SAULT, TOKEN)
VALUES 
   ('company', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 
   '4a44dc15364204a80fe80e9039455cc1608281820fe2b24f1e5233ade6af1dd5', '');
   
INSERT INTO company_profiles (COMPANY_ID, COMPANY_NAME, ADRESS, MOL, EIK, EMAIL, COMPANY_TYPE, STATUS)
VALUES 
   (1, 'Pesho EOOD', 'Dupnitsa town, center 1', 'Pesho Peshev', '987654321', 'ilian@dupnishko.guze.org',
   'organisation', 0);
   
INSERT INTO advertisements (CATEGORY_ID, TITLE, COMPANY_ID, CONTENT, TEST, PRICE, ACTIVITY_DAYS, EXPIRATION_DATE,
	PLACE_ID, IS_VIP, IS_APPROVED, IS_PAID, IS_EXPIRED)
VALUES 
   (1, 'Junior Backup Support Engineer', 1, 'Currently we are looking for Windows support engineers to be trained into various data backup and restore tools and become BACKUP AND RECOVERY ENGINEERS, providing remote server and application support to HP customers.',
   '', 100, 30, null, 1, true, false, false, false),
   (11, 'ACCOUNTANT', 1, 'Preparation of journal entries, recording and reconciliation of accounting transactions; Responsibilities within monthly closing process and accounts analysis; Preparation of various reports and reconciliations around operating and finance systems; Maintenance of balance sheet schedules for assigned accounts; Support to preparation of regular and ad-hoc analysis and reports. Participation in finance operations projects.',
   '', 0, 30, null, 4, false, false, false, false),
   (15, 'Office Manager', 1, 'Virtual Affairs is looking for an enthusiastic and well-organized Office Manager for our Sofia office. Joining us you will be able to work within a team of highly motivated professionals and have different opportunities for career and personal growth.',
   '', 100, 30, null, 2, true, false, false, false);

INSERT INTO INVOICES (INVOICE_DATE, OWNER_ID, COMPANY_ID, ADV_ID, QUANTITY, DISCOUNT, PRICE, TAX, TAX_AMMOUNT,
	TOTAL_PRICE, EVENT_DATE, LATE_PAYMENT, IS_CASH, IS_PAYED, DUE_PAYMENT)
VALUES 
   (CURRENT_DATE, 1, 1, 1, 1, 0, 100, 0.2, 20, 120, CURRENT_DATE, 5, false, false, false),
   (CURRENT_DATE, 1, 1, 3, 1, 0, 100, 0.2, 20, 120, CURRENT_DATE, 5, false, false, false);  
