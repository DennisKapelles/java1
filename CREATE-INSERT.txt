DROP DATABASE IF EXISTS StaffEvaluation;
CREATE DATABASE StaffEvaluation;
USE StaffEvaluation;


CREATE TABLE users
(
  username VARCHAR(12) NOT NULL,
  password VARCHAR(10) NOT NULL,
  name VARCHAR(25) NOT NULL,
  surname VARCHAR(35) NOT NULL,
  reg_date DATETIME NOT NULL,
  email VARCHAR(30) NOT NULL,
  rating ENUM ('MAN', 'EMP', 'EVAL', 'ADMIN') NOT NULL,
  PRIMARY KEY (username)
);

CREATE TABLE log
(
  username VARCHAR(12),
  date DATETIME NOT NULL,
  success VARCHAR(5) NOT NULL,
  kind ENUM('INS', 'UPD', 'DEL') NOT NULL,
  table_name VARCHAR(20) NOT NULL,
  CONSTRAINT LOG_USER
  FOREIGN KEY (username) REFERENCES users(username)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE company
(
  AFM CHAR(9) NOT NULL,
  DOY VARCHAR(15) NOT NULL,
  Name VARCHAR(35) NOT NULL,
  phone BIGINT(16) NOT NULL,
  street VARCHAR(15) NOT NULL,
  num TINYINT(4) NOT NULL,
  city VARCHAR(15) NOT NULL,
  country VARCHAR(15) NOT NULL,
  PRIMARY KEY (AFM)
);

CREATE TABLE sector
(
  tomeas VARCHAR(20) NOT NULL,
  afm_company CHAR(9) NOT NULL,
  CONSTRAINT SECTOR_COMPANY
  FOREIGN KEY (afm_company) REFERENCES company(AFM)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE manager
(
  managerUsername VARCHAR(12) NOT NULL,
  exp_years TINYINT(4) NOT NULL,
  firm CHAR(9) NOT NULL,
  PRIMARY KEY (managerUsername),
  CONSTRAINT MANAGER_USER
  FOREIGN KEY (managerUsername) REFERENCES users(username)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT MANAGER_COMPANY
  FOREIGN KEY (firm) REFERENCES company(AFM)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE evaluator
(
  username VARCHAR(12) NOT NULL,
  name VARCHAR(25) NOT NULL,
  surname VARCHAR(35) NOT NULL,
  exp_years TINYINT(4) NOT NULL,
  code INT NOT NULL,
  firm CHAR(9) NOT NULL,
  MO FLOAT(6,1) DEFAULT '0.0' NOT NULL,
  PRIMARY KEY (username),
  CONSTRAINT EVAL_COMPANY
  FOREIGN KEY (firm) REFERENCES company(AFM)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT EVAL_USER
  FOREIGN KEY (username) REFERENCES users(username)
  ON DELETE CASCADE ON UPDATE CASCADE,
  UNIQUE (code)
);

CREATE TABLE employee
(
  username VARCHAR(12) NOT NULL,
  bio TEXT NOT NULL,
  sistatikes VARCHAR(35) NOT NULL,
  certificates VARCHAR(35) NOT NULL,
  awards VARCHAR(35) NOT NULL,
  exp_years TINYINT(4) NOT NULL,
  AM INT NOT NULL,
  afm_company CHAR(9) NOT NULL,
  PRIMARY KEY (username),
  CONSTRAINT EMPL_COMPANY
  FOREIGN KEY (afm_company) REFERENCES company(AFM)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT EMPL_USER
  FOREIGN KEY (username) REFERENCES users(username)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE project
(
  empl VARCHAR(12) NOT NULL,
  num TINYINT(4) NOT NULL,
  url VARCHAR(60) NOT NULL,
  descr TEXT NOT NULL,
  PRIMARY KEY (num, empl),
  CONSTRAINT PROJECT_EMPLOYEE
  FOREIGN KEY (empl) REFERENCES employee(username)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE languages
(
  employee VARCHAR(12) NOT NULL,
  lang SET('EN', 'FR', 'SP', 'GR') NOT NULL,
  PRIMARY KEY (lang, employee),
  CONSTRAINT LANGUAGE_EMPLOYEE
  FOREIGN KEY (employee) REFERENCES employee(username)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE job
(
  id INT(4) NOT NULL,
  start_date DATE NOT NULL,
  salary FLOAT(6,1) NOT NULL,
  position VARCHAR(40) NOT NULL,
  edra VARCHAR(45) NOT NULL,
  announce_date DATETIME NOT NULL,
  submission_date DATE NOT NULL,
  Title VARCHAR(20) NOT NULL,
  evaluator VARCHAR(12) NOT NULL,
  AFM CHAR(9) NOT NULL, 
  PRIMARY KEY (id),
  CONSTRAINT JOB_EVALUATOR
  FOREIGN KEY (evaluator) REFERENCES evaluator(username)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT JOB_COMPANY
  FOREIGN KEY (AFM) REFERENCES company(AFM)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE antikeim
(
  title VARCHAR(36) NOT NULL,
  descr TINYTEXT NOT NULL,
  belongs_to VARCHAR(36),
  PRIMARY KEY (title),
  CONSTRAINT ANTIKEIM_ANT
  FOREIGN KEY (belongs_to) REFERENCES antikeim(title)
  ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE NEEDS
(
  job_id INT NOT NULL,
  antikeim_title VARCHAR(36) NOT NULL,
  PRIMARY KEY (antikeim_title, job_id),
  CONSTRAINT NEEDS_ANTIKEIM
  FOREIGN KEY (antikeim_title) REFERENCES antikeim(title)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT NEEDS_JOB
  FOREIGN KEY (job_id) REFERENCES job(id)
  ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE requestsevaluation
(
  job_id INT NOT NULL,
  empl_usrname VARCHAR(12) NOT NULL,
  PRIMARY KEY (job_id, empl_usrname),
  CONSTRAINT REQEVAL_JOB
  FOREIGN KEY (job_id) REFERENCES job(id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT REQEVAL_EMPLOYEE
  FOREIGN KEY (empl_usrname) REFERENCES employee(username)
  ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE evaluationresult
(
  Evld INT(4) NOT NULL,
  emp_usrname VARCHAR(12) NOT NULL,
  job_id INT NOT NULL,
  grade INT(4),
  comments VARCHAR(255),
  PRIMARY KEY (Evld, emp_usrname),
  CONSTRAINT EVALRESULT_JOB
  FOREIGN KEY (job_id) REFERENCES job(id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT EVALRESULT_EMPLOYEE
  FOREIGN KEY (emp_usrname) REFERENCES employee(username)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Evaluation
(
  eval_username VARCHAR(12) NOT NULL,
  Interview_1 INT,
  Report_2 INT,
  4pragmata_3 INT,
  state VARCHAR(10) NOT NULL,
  empl_username VARCHAR(12) NOT NULL,
  jobid INT(4) NOT NULL,
  CONSTRAINT EVALUATION_EVALUATOR
  FOREIGN KEY (eval_username) REFERENCES evaluator(username)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT EVALUATION_EMPLOYEE
  FOREIGN KEY (empl_username) REFERENCES employee(username)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT EVALUATION_EVALRESULT
  FOREIGN KEY (jobid) REFERENCES evaluationresult(job_id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT EVALUATION_JOB
  FOREIGN KEY (jobid) REFERENCES job(id)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE HAS_DEGREE
(
  empl_usrname VARCHAR(12) NOT NULL,
  degr_idryma VARCHAR(40) NOT NULL,
  degr_title VARCHAR(50) NOT NULL,
  etos YEAR(4) NOT NULL,
  grade FLOAT(3,1) NOT NULL,
  PRIMARY KEY (degr_idryma, degr_title, empl_usrname),
  CONSTRAINT HAS_DEGREE_EMPLOYEE
  FOREIGN KEY (empl_usrname) REFERENCES employee(username)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE degree
(
  idryma VARCHAR(40) NOT NULL,
  titlos VARCHAR(50) NOT NULL,
  bathmida ENUM('LYKEIO', 'UNIV', 'MASTER', 'PHD') NOT NULL,
  PRIMARY KEY (idryma, titlos),
  CONSTRAINT DEGREE_HAS_DEGREE
  FOREIGN KEY (idryma, titlos) REFERENCES HAS_DEGREE(degr_idryma, degr_title)
  ON DELETE CASCADE ON UPDATE CASCADE
);

/* add MANAGERS */
INSERT INTO users (username, password, name, surname, reg_date, email, rating) VALUES 
('MariaDB', 'mr57db', 'Maria', 'Dimitriadou', '2018-02-13 12:23:34', 'mairidim@gmail.com', 'MAN'),
('teoman', 'tmn83', 'Teofilo', 'Manila', '2017-05-11 14:08:23', 'teoman@yahoo.com', 'MAN'),
('diomitos', 'dio62', 'Dimitar', 'Tosar', '2018-10-07 20:09:10', 'dioman@ezra.co.uk', 'MAN'),
('kostdim', '12dim84', 'Kostas', 'Dimitriou', '2018-05-22 17:03:01', 'kdim1284@gmail.com', 'MAN');

/* add EMPLOYEES */
INSERT INTO users (username, password, name, surname, reg_date, email, rating) VALUES
('Johnny', '15acdf4', 'John', 'Nikolaou', '2018-10-19 12:10:00', 'jnik@yahoo.com', 'EMP'),
('Libelen', '45dvcs6', 'Eleni', 'Libada', '2017-03-05 09:50:38', 'elib@gmail.com', 'EMP'),
('PetrAl', '48w32dqc', 'Petros', 'Alexiou', '2014-02-20 11:12"03', 'petral83@howmail.com', 'EMP'),
('Alekos', '123321ab', 'Alexandros', 'Staurou', '2020-04-12 13:15:26', 'alekstaur@gmail.com', 'EMP');

/* add EVALUATORS */
INSERT INTO users (username, password, name, surname, reg_date, email, rating) VALUES 
('conmcgr', 'we3wd', 'Conor', 'McGregor', '2017-06-23 13:12:34', 'king@unigram.com', 'EVAL'),
('varcon82', 'gotop@s$', 'Nick', 'Varcon', '2018-12-03 18:12:39', 'varcni@incode.com', 'EVAL'),
('theonik', 'jUn38@', 'Theofilaktos', 'Nikolaidis', '2017-04-12 12:23:10', 'thnik@softsol.gr', 'EVAL'),
('papad', 'pdfr45t', 'Kostas', 'Papadatos', '2018-11-17 23:10:08', 'kospa@softsol.gr', 'EVAL'),
('elsa', '123456', 'Elisabet', 'Antiniou', '2019-10-20 10:00:00', 'elsa@gmail.com', 'EVAL');

/* add ADMINISTRATORS */
INSERT INTO users (username, password, name, surname, reg_date, email, rating) VALUES 
('mkzuk', 'm73z84', 'Mark', 'Zuckerberg', '2017-06-23 13:12:34', 'mark@gmail.com', 'ADMIN'),
('sakarp', '12uh21', 'Sakis', 'Karpas', '2018-12-03 18:12:39', 'cybersak@yahoo.com', 'ADMIN'),
('vemojohn', 'vemo23', 'Giannis', 'Venogiannis', '2017-04-12 12:23:10', '54vjhn71@softsol.gr', 'ADMIN'),
('johnkag', 'jk523', 'John', 'Kagioulis', '2018-11-17 23:10:08', 'kag73@howmail.gr', 'ADMIN');


INSERT INTO log(username, date, success, kind, table_name) VALUES
('MariaDB', '2019-01-01', 'YES', 'INS', 'job'),
('teoman', '2019-02-01', 'YES', 'DEL', 'employee'),
('diomitos', '2019-02-01', 'NO', 'UPD', 'requestsevaluation'),
('kostdim', '2018-12-25', 'NO', 'DEL', 'employee'),
('Johnny', '2019-03-01', 'NO', 'UPD', 'requestsevaluation'),
('Libelen', '2019-03-01', 'YES', 'INS','job'),
('PetrAl', '2019-05-01', 'NO', 'UPD','requestsevaluation'),
('Alekos', '2019-05-01', 'YES', 'INS','employee'),
('theonik', '2019-05-01', 'YES', 'DEL','employee'),
('papad', '2019-04-01', 'NO', 'INS', 'job'),
('conmcgr', '2019-02-01', 'YES', 'DEL', 'requestsevaluation'),
('varcon82', '2019-03-01', 'NO', 'UPD', 'job');


INSERT INTO company(AFM, DOY, Name, phone, street, num, city,country) VALUES
('936274028', 'A Patras', 'Typology Ltd', 2610231452, 'Korinthou', 56, 'Patra', 'Greece'),
('673815904', 'C Peiraia', 'Unigram', 2103452672, 'Karaiskaki', 10, 'Peiraias', 'Greece'),
('492601853', 'GS 35321 L', 'InCodeWeTrust', 1242345612, 'Oxford', 12, 'London', 'United Kingdom'), 
('573960142', 'SF 1234 BG', 'SocialSc', 3200123451, 'General Sklevi', 35, 'Sofia', 'Bulgaria');

INSERT INTO sector(tomeas, afm_company) VALUES
('Computers', '492601853'),
('PCB Creations', '492601853'),
('Science', '573960142'),
('Computers', '673815904'),
('Logistics', '936274028');

INSERT INTO manager(managerUsername, exp_years, firm) VALUES
('MariaDB', 7, '673815904'),
('kostdim', 4, '936274028'),
('teoman', 11, '573960142'),
('diomitos', 6, '492601853');


INSERT INTO evaluator(username, name, surname, exp_years, code, firm, MO) VALUES
('conmcgr', 'Conor', 'McGregor', 4, 0123, '492601853', 6.5),
('varcon82', 'Nick', 'Varcon', 13, 4567, '936274028', 4),
('theonik', 'Theofilaktos', 'Nikolaidis', 7, 8910, '573960142', 7),
('papad', 'Kostas', 'Papadatos', 2, 1029, '673815904', 6),
('elsa', 'Elisabet', 'Antiniou', 15, 2014, '492601853', 0);


INSERT INTO employee(username, bio, sistatikes, certificates, awards, exp_years, AM, afm_company) VALUES
('Johnny', 'Johnny has become a skilled data-database analyst through the years and knows staf', 'johN_lett.pdf', 'johN_cert.pdf', 'johN_awards.pdf', 7, 1045823, '936274028'),
('Libelen', 'Eleni graduated from computer engeenies university and become expert on web developer', 'libEl_lett.pdf', 'libEl_cert.pdf', 'libEl_awards.pdf', 9, 1022541, '673815904'),
('PetrAl', 'Petros designed a lot of android & ios apps and knows very well many programming languages as JAVA', 'petrale_lett.pdf', 'petrale_cert.pdf', 'petrale_awards.pdf', 5, 1064463, '492601853'),
('Alekos', 'Alexandros, has many skills in photography, video editing and graphical applications with 3d models', 'AlexS_lett.pdf', 'AlexS_cert.pdf', 'AlexS_awards.pdf', 10, 1011547, '573960142');


INSERT INTO project(empl, num, url, descr) VALUES
('Johnny', 1, 'https://github.com/john_N/book_db', 'A database used by bookshops with a lot of books'),
('Libelen', 1, 'https://github.com/libelen/clothes_shop', 'An eshop to browse through clothes'),
('Libelen', 2, 'https://github.com/libelen/electr_shop', 'An eshop to browse through electronics'),
('PetrAl', 1, 'https://github.com/Petr_Al/shoot', 'A shooting android game designed by Petr_AL'),
('Alekos', 1, 'https://github.com/Aleko_S/3dcar', '3D rendered car'),
('Alekos', 2, 'https://github.com/Aleko_S/logo', 'logo designes');


INSERT INTO languages(employee, lang) VALUES
('Johnny', 'EN,GR,SP'),
('Libelen', 'EN,GR,FR'),
('PetrAl', 'EN,GR'),
('Alekos', 'EN,GR');


INSERT INTO job(id, start_date, salary, position, edra, announce_date, submission_date, Title, evaluator, AFM) VALUES
(1, '2018-03-12', 1240.5, 'Data Analyst', 'Patra, Greece', '2017-12-10 10:12:00', '2018-01-10', 'Data Manager', 'conmcgr', '492601853'),
(2, '2018-06-13', 835.5, 'Web Developer', 'Athina, Greece', '2018-02-12 12:00:00', '2018-04-10', 'Web Page Architect', 'varcon82', '936274028'),
(3, '2017-02-03', 1550.9, 'App developer', 'Kalamata, Greece', '2016-10-22 10:30:00', '2016-11-24', 'App guru', 'papad', '673815904'),
(4, '2019-10-20', 1000.0, 'Graghics Designer', 'Thessaloniki, Greece', '2019-06-15 11:20:00', '2019-08-15', 'Master Artist', 'conmcgr', '492601853'),
(5, '2019-04-18', 1240.5, 'DB expert', 'Patra, Greece', '2019-01-10 15:55:00', '2019-03-20', 'Data Baser', 'theonik', '573960142'),
(6, '2020-02-18', 1240.5, 'Visualization expert', 'Patra, Greece', '2019-10-10 15:55:00', '2020-01-20', 'Visual Ninja', 'varcon82', '936274028'),
(7, '2020-04-18', 1400.0, 'Visualization Master', 'Athina, Greece', '2020-01-10 15:55:00', '2020-04-20', 'Visual Yoda', 'elsa', '492601853');

INSERT INTO requestsevaluation(job_id, empl_usrname) VALUES
(7, 'johnny'),
(1, 'Johnny'),
(2, 'Johnny'),
(2, 'Libelen'),
(4, 'Libelen'),
(2, 'PetrAl'),
(3, 'PetrAl'),
(4, 'Alekos');


INSERT INTO evaluationresult(Evld, emp_usrname, job_id, grade, comments) VALUES
(0124, 'Johnny', 1, 7, 'Knows what he doing!'),
(0125, 'Libelen', 2, NULL, 'Expert knowledge on WEB'),
(0126, 'PetrAl', 3, 6, 'Semi qualified, for further check'),
(0127, 'Alekos', 4, NULL, 'Very qualyfied for the job'),
(0128, 'Libelen', 4, NULL, 'Good knowledge on WEB app development'),
(0129, 'Johnny', 2, 4, 'Expert knowledge on WEB'),
(0130, 'PetrAl', 2, 6, 'Expert knowledge on WEB'),
(0131, 'Johnny', 7, NULL, NULL);

INSERT INTO Evaluation(eval_username, Interview_1, Report_2, 4pragmata_3, state, empl_username, jobid) VALUES
('conmcgr', 3, 2, 2, 'Final', 'Johnny', 1),
('varcon82', NULL, NULL, 2, 'Temporary', 'Libelen', 2), 
('papad', 4, 1, 1, 'Final', 'PetrAl', 3), 
('conmcgr', 3, 2, 1, 'Temporary', 'Alekos', 4),
('conmcgr', 2, 3, 2, 'Temporary', 'Libelen', 4),
('varcon82', 3, 1, 0, 'Final', 'Johnny', 2),
('varcon82', 2, 3, 1, 'Final', 'PetrAl', 2),
('elsa', NULL, NULL, NULL, 'Temporary', 'Johnny', 7);


INSERT INTO antikeim (title, descr, belongs_to) VALUES
('Computer Science', 'Root element, no more general antikeim', NULL), 
('Databases', 'Level one element, child of Computer Science', 'Computer Science'),
('AI', 'Level one element, child of Computer Science', 'Computer Science'),
('Algorithms', 'Level one element, child of Computer Science', 'Computer Science'),
('Graphics', 'Level one element, child of Computer Science', 'Computer Science'),
('2D', 'Level two element, child of Graphics', 'Graphics'),
('3D', 'Level two element, child of Graphics', 'Graphics'),
('Programming', 'Level one element, child of Computer Science', 'Computer Science'),
('Web Programming', 'Level two element, child of Programming', 'Programming'),
('Mobile Apps', 'Level two element, child of Programming', 'Programming'),
('Robotics', 'Level two element, child of AI', 'AI'),
('NLP', 'Level two element, child of AI', 'AI'),
('Information Retieval', 'Level three element, child of NLP', 'NLP'),
('Data structures', 'Level two element, child of Algorithms', 'Algorithms'),
('Complexity and Efficiency', 'Level two element, child of Algorithms', 'Algorithms');


INSERT INTO NEEDS (job_id, antikeim_title) VALUES 
(1, 'Databases'),
(1, 'Algorithms'),
(1, 'Complexity and Efficiency'),
(2, 'Programming'),
(2, 'Web Programming'),
(3, 'Mobile Apps'),
(3, 'Data structures'),
(4, 'AI'),
(4, 'Robotics'),
(4, 'NLP'),
(4, 'Information Retieval'),
(5, 'Graphics'),
(5, '2D'),
(5, '3D'),
(6, 'Graphics'),
(6, 'Algorithms'),
(6, 'Data structures'),
(6, 'Programming'); 


INSERT INTO HAS_DEGREE(empl_usrname, degr_idryma, degr_title, etos, grade) VALUES
('Johnny', '2o Lykeio Peiraia', 'High School Diploma', 2014, 17.8),
('Libelen', '10o Lykeio Athinas', 'High School Diploma', 2008, 19.8),
('Libelen', 'Patras University', 'Master Thesis', 2012, 10),
('Alekos', '5o Lykeio Thesalias', 'High School Diploma', 2005, 17.5),
('Alekos', 'Patras University', 'Bachelor Degree', 2009, 8.9),
('Alekos', 'Patras University', 'Master Thesis', 2012, 10),
('Alekos', 'Patras University', 'PHD', 2014, 9.8);


INSERT INTO degree(idryma, titlos, bathmida) VALUES
('Patras University', 'Bachelor Degree', 'UNIV'),
('Patras University', 'Master Thesis', 'MASTER'),
('Patras University', 'PHD', 'PHD'),
('5o Lykeio Thesalias', 'High School Diploma', 'LYKEIO'),
('10o Lykeio Athinas', 'High School Diploma', 'LYKEIO'),
('2o Lykeio Peiraia', 'High School Diploma', 'LYKEIO');