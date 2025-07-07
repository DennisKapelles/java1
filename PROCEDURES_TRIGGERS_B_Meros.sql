/* PROCEDURES AND TRIGGERS for MEROS 2 - GUI */

/* Procedure and trigger for manger last button*/
DROP PROCEDURE IF EXISTS emfanish_aithseis_aksiologiseis_aksiologitis_final;
DELIMITER $
CREATE PROCEDURE emfanish_aithseis_aksiologiseis_aksiologitis_final(empl_name VARCHAR(25), empl_surname VARCHAR(35))
 BEGIN
   DECLARE katastash VARCHAR(10);
   DECLARE finishedflag INT;
   DECLARE empl_cursor CURSOR FOR
    SELECT DISTINCT evaluation.state
    FROM evaluation
    INNER JOIN employee ON evaluation.empl_username=employee.username
    INNER JOIN users ON employee.username=users.username
    WHERE users.name=empl_name AND users.surname=empl_surname;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET finishedflag=1;
   SET finishedflag=0;
   OPEN empl_cursor;
   FETCH empl_cursor INTO katastash;

   WHILE (finishedflag=0) DO
   IF(katastash='Final') THEN
     SELECT count(requestsevaluation.job_id) AS 'Number Of Requests'
     FROM requestsevaluation
     INNER JOIN employee ON employee.username=requestsevaluation.empl_usrname
     INNER JOIN users ON employee.username=users.username
     WHERE users.name=empl_name AND users.surname=empl_surname;
     
     SELECT evaluationresult.grade AS 'Grade Of Final Evaluation'
     FROM evaluationresult
     INNER JOIN employee ON employee.username=evaluationresult.emp_usrname
     INNER JOIN users ON employee.username=users.username
     WHERE users.name=empl_name AND users.surname=empl_surname;
     
     SELECT evaluator.name AS 'Evaluator Name', evaluator.surname AS 'Evaluator Surname'
     FROM evaluator
     INNER JOIN evaluation ON evaluator.username=evaluation.eval_username
     INNER JOIN employee ON evaluation.empl_username=employee.username
     INNER JOIN users ON employee.username=users.username
     WHERE users.name=empl_name AND users.surname=empl_surname;

     ELSEIF(katastash='Temporary') THEN
     
     SELECT 'Evaluation in progress!' AS Status;
     SELECT count(requestsevaluation.job_id) AS 'Number Of Requests'
     FROM requestsevaluation
     INNER JOIN employee ON employee.username=requestsevaluation.empl_usrname
     INNER JOIN users ON employee.username=users.username
     WHERE users.name=empl_name AND users.surname=empl_surname;
    END IF;
     FETCH empl_cursor INTO katastash;
 END WHILE;
 CLOSE empl_cursor;
 END$
DELIMITER ;

DROP TRIGGER IF EXISTS notchange_eval_username;
DELIMITER $
CREATE TRIGGER notchange_eval_username BEFORE UPDATE ON evaluator 
FOR EACH ROW
 BEGIN 
  SET NEW.username=OLD.username; 
 END$
DELIMITER ;

/*------------------------------------------------------------------------------*/

/* Procedure and Trigger to create a user on users TABLE when administrator inserts a new manager on manager Table */

DROP PROCEDURE IF EXISTS AdminManInsert_Frane1;
DELIMITER $
CREATE PROCEDURE AdminManInsert_Frane1(IN man_username VARCHAR(12))
BEGIN
 DECLARE cur_datetime DATETIME;
 SET cur_datetime = NOW();
 INSERT INTO users(username, reg_date, rating) VALUES (man_username, cur_datetime, "MAN");
END$
DELIMITER ;

DROP TRIGGER IF EXISTS trig_AdminManInsert_Frane1;
DELIMITER $
CREATE TRIGGER trig_AdminManInsert_Frane1 BEFORE INSERT ON manager
FOR EACH ROW
BEGIN
 CALL AdminManInsert_Frane1(NEW.managerUsername);
END$
DELIMITER ;


/* Procedure and Trigger to create a user on users TABLE when administrator inserts a new evaluator on evaluator Table */

DROP PROCEDURE IF EXISTS AdminEvalInsert_Frane1;
DELIMITER $
CREATE PROCEDURE AdminEvalInsert_Frane1(IN eval_username VARCHAR(12))
BEGIN
 DECLARE cur_datetime DATETIME;
 SET cur_datetime = NOW();
 INSERT INTO users(username, reg_date, rating) VALUES (eval_username, cur_datetime, "EVAL");
END$
DELIMITER ;

DROP TRIGGER IF EXISTS trig_AdminEvalInsert_Frane1;
DELIMITER $
CREATE TRIGGER trig_AdminEvalInsert_Frane1 BEFORE INSERT ON evaluator
FOR EACH ROW
BEGIN
 CALL AdminEvalInsert_Frane1(NEW.username);
END$
DELIMITER ;


/* Procedure and Trigger to create a user on users TABLE when administrator inserts a new employee on employee Table */

DROP PROCEDURE IF EXISTS AdminEmpInsert_Frane1;
DELIMITER $
CREATE PROCEDURE AdminEmpInsert_Frane1(IN emp_username VARCHAR(12))
BEGIN
 DECLARE cur_datetime DATETIME;
 SET cur_datetime = NOW();
 INSERT INTO users(username, reg_date, rating) VALUES (emp_username, cur_datetime, "EMP");
END$
DELIMITER ;

DROP TRIGGER IF EXISTS trig_AdminEmpInsert_Frane1;
DELIMITER $
CREATE TRIGGER trig_AdminEmpInsert_Frane1 BEFORE INSERT ON employee
FOR EACH ROW
BEGIN
 CALL AdminEmpInsert_Frane1(NEW.username);
END$
DELIMITER ;

