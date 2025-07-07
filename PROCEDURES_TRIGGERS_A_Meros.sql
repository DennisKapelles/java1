			/* PROCEDURE 1 */
DROP PROCEDURE IF EXISTS emfanish_aithseis_aksiologiseis_aksiologitis;
DELIMITER $
CREATE PROCEDURE emfanish_aithseis_aksiologiseis_aksiologitis(empl_name VARCHAR(25), empl_surname VARCHAR(35))
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
     
     SELECT evaluation.Interview_1 AS 'Grade 1', evaluation.Report_2 AS 'Grade 2', evaluation.4pragmata_3 AS 'Grade 3'
     FROM evaluation
     INNER JOIN employee ON employee.username=evaluation.empl_username
     INNER JOIN users ON employee.username=users.username
     WHERE users.name=empl_name AND users.surname=empl_surname;
     
     SELECT evaluator.name AS 'Evaluator Name', evaluator.surname AS 'Evaluator Surname'
     FROM evaluator
     INNER JOIN evaluation ON evaluator.username=evaluation.eval_username
     INNER JOIN employee ON evaluation.empl_username=employee.username
     INNER JOIN users ON employee.username=users.username
     WHERE users.name=empl_name AND users.surname=empl_surname;
    END IF;
     FETCH empl_cursor INTO katastash;
 END WHILE;
 CLOSE empl_cursor;
 END$
DELIMITER ;

			/* PROCEDURE 2 */
DROP PROCEDURE IF EXISTS check_evaluation;
DELIMITER $
CREATE PROCEDURE check_evaluation(IN jobid INT(4), IN eval_usern VARCHAR(12))
BEGIN
 DECLARE employee VARCHAR(12);
 DECLARE grade_1 INT;
 DECLARE grade_2 INT;
 DECLARE grade_3 INT;
 DECLARE final_grade INT;
 DECLARE katastasi VARCHAR(12);

 DECLARE finishedflag INT;
 DECLARE eval_cursor CURSOR FOR
  SELECT Interview_1, Report_2, 4pragmata_3, state, empl_username
  FROM Evaluation 
  WHERE eval_username=eval_usern;
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET finishedflag=1;
 SET finishedflag=0;
 OPEN eval_cursor;
 FETCH eval_cursor INTO grade_1, grade_2, grade_3, katastasi, employee;
 WHILE (finishedflag=0) DO
  IF ((grade_1 IS NOT NULL) AND (grade_2 IS NOT NULL) AND (grade_3 IS NOT NULL) AND (katastasi='Temporary')) THEN
   SET final_grade = grade_1 + grade_2 + grade_3;
   UPDATE evaluationresult SET grade=final_grade WHERE jobid=job_id AND emp_usrname=employee;
   UPDATE Evaluation SET state='Final' WHERE eval_username=eval_usern AND empl_username=employee;
  END IF;
  FETCH eval_cursor INTO grade_1, grade_2, grade_3, katastasi, employee;
 END WHILE;
 CLOSE eval_cursor;
END$
DELIMITER ;

			/* PROCEDURE 3 */
DROP PROCEDURE IF EXISTS final_table;
DELIMITER $
CREATE PROCEDURE final_table(IN jobid INT(4))
BEGIN
 DECLARE count_final INT;
 DECLARE count_with_null INT;
 DECLARE diafora INT;
 SELECT COUNT(*)
 INTO count_with_null
 FROM evaluationresult
 WHERE job_id=jobid AND grade IS NULL;
 SELECT COUNT(*)
 INTO count_final
 FROM evaluationresult
 WHERE job_id=jobid AND grade IS NOT NULL;
 IF(count_with_null=0 AND jobid IN(select job_id from evaluationresult)) THEN
  SELECT 'Oristikopoihmenoi Pinakes' AS 'Status';
  SELECT emp_usrname AS 'Employee Username', grade AS 'Final Grade'
  FROM evaluationresult
  WHERE job_id=jobid
  ORDER BY grade DESC;
 ELSE
  IF(count_final=0) THEN
   SELECT 'Den uparxoun olokliromenes aksiologiseis' AS 'Status Of Evaluations';
  ELSE
   SELECT emp_usrname AS 'Employee Username', grade AS 'Final Grade'
   FROM evaluationresult
   WHERE job_id=jobid AND grade IS NOT NULL
   ORDER BY grade DESC;
  END IF;
  SELECT 'Aksiologish se ekseliksh ...ekremoun...' AS 'Status ', count_with_null AS 'Requests', 'aksiologiseis';
 END IF;
END$
DELIMITER ;

			/* TRIGGERS 1 */

DROP PROCEDURE IF EXISTS log_actions;
DELIMITER $
CREATE PROCEDURE log_actions(IN usern VARCHAR(12), IN action VARCHAR(5), IN table_name VARCHAR(20), IN state INT)
BEGIN
 DECLARE cur_datetime DATETIME;
 SET cur_datetime = NOW();
 IF (state=1) THEN
  INSERT INTO log VALUES (usern, cur_datetime, 'YES', action, table_name);
 ELSE
  INSERT INTO log VALUES (usern, cur_datetime, 'NO', action, table_name);
 END IF;
END$
DELIMITER ;

DROP TRIGGER IF EXISTS trig_insert_job;
DELIMITER $
CREATE TRIGGER trig_insert_job AFTER INSERT ON job
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM job WHERE id=NEW.id;
 IF (check_OK=1) THEN
  CALL log_actions(@user, 'INS', 'job', 1);
 ELSE
  CALL log_actions(@user, 'INS', 'job', 0);
 END IF;
END$
DELIMITER ;


DROP TRIGGER IF EXISTS trig_update_job;
DELIMITER $
CREATE TRIGGER trig_update_job AFTER UPDATE ON job
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM job WHERE id=NEW.id AND salary=NEW.salary;
 IF (check_OK=1) THEN
  CALL log_actions(@user, 'UPD', 'job', 1);
 ELSE
  CALL log_actions(@user, 'UPD', 'job', 0);
 END IF;
END$
DELIMITER ;

DROP TRIGGER IF EXISTS trig_delete_job;
DELIMITER $
CREATE TRIGGER trig_delete_job AFTER DELETE ON job
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM job WHERE id=OLD.id;
 IF (check_OK=0) THEN
  CALL log_actions(@user, 'DEL', 'job', 1);
 ELSE
  CALL log_actions(@user, 'DEL', 'job', 0);
 END IF;
END$
DELIMITER ;


DROP TRIGGER IF EXISTS trig_insert_employee;
DELIMITER $
CREATE TRIGGER trig_insert_employee AFTER INSERT ON employee
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM employee WHERE username=NEW.username;
 IF (check_OK=1) THEN
  CALL log_actions(@user, 'INS', 'employee', 1);
 ELSE
  CALL log_actions(@user, 'INS', 'employee', 0);
 END IF;
END$
DELIMITER ;


DROP TRIGGER IF EXISTS trig_update_employee;
DELIMITER $
CREATE TRIGGER trig_update_employee AFTER UPDATE ON employee
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM employee WHERE username=NEW.username AND bio=NEW.bio;
 IF (check_OK=1) THEN
  CALL log_actions(@user, 'UPD', 'employee', 1);
 ELSE
  CALL log_actions(@user, 'UPD', 'employee', 0);
 END IF;
END$
DELIMITER ;


DROP TRIGGER IF EXISTS trig_delete_employee;
DELIMITER $
CREATE TRIGGER trig_delete_employee AFTER DELETE ON employee
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM employee WHERE username=OLD.username;
 IF (check_OK=0) THEN
  CALL log_actions(@user, 'DEL', 'employee', 1);
 ELSE
  CALL log_actions(@user, 'DEL', 'employee', 0);
 END IF;
END$
DELIMITER ;


DROP TRIGGER IF EXISTS trig_insert_requestsevaluation;
DELIMITER $
CREATE TRIGGER trig_insert_requestsevaluation AFTER INSERT ON requestsevaluation
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM requestsevaluation WHERE job_id=NEW.job_id;
 IF (check_OK=1) THEN
  CALL log_actions(@user, 'INS', 'requestsevaluation', 1);
 ELSE
  CALL log_actions(@user, 'INS', 'requestsevaluation', 0);
 END IF;
END$
DELIMITER ;


DROP TRIGGER IF EXISTS trig_update_requestsevaluation;
DELIMITER $
CREATE TRIGGER trig_update_requestsevaluation AFTER UPDATE ON requestsevaluation
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM requestsevaluation WHERE job_id=NEW.job_id AND empl_usrname=NEW.empl_usrname;
 IF (check_OK=1) THEN
  CALL log_actions(@user, 'UPD', 'requestsevaluation', 1);
 ELSE
  CALL log_actions(@user, 'UPD', 'requestsevaluation', 0);
 END IF;
END$
DELIMITER ;


DROP TRIGGER IF EXISTS trig_delete_requestsevaluation;
DELIMITER $
CREATE TRIGGER trig_delete_requestsevaluation AFTER DELETE ON requestsevaluation
FOR EACH ROW
BEGIN
 DECLARE check_OK INT;
 SELECT COUNT(*) INTO check_OK FROM requestsevaluation WHERE job_id=OLD.job_id;
 IF (check_OK=0) THEN
  CALL log_actions(@user, 'DEL', 'requestsevaluation', 1);
 ELSE
  CALL log_actions(@user, 'DEL', 'requestsevaluation', 0);
 END IF;
END$
DELIMITER ;



			/*TRIGGER 2 */
DROP TRIGGER IF EXISTS notchange_afm_doy_name;
DELIMITER $
CREATE TRIGGER notchange_afm_doy_name BEFORE UPDATE ON company 
FOR EACH ROW
 BEGIN 
  SET NEW.AFM=OLD.AFM;
  SET NEW.DOY=OLD.DOY;
  SET NEW.Name=OLD.Name; 
 END$
DELIMITER ;


			/*TRIGGER 3 */
DROP TRIGGER IF EXISTS notchange_if_not_administrator;
DELIMITER $
CREATE TRIGGER notchange_if_not_administrator BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
IF NEW.reg_date<>OLD.reg_date AND (NEW.rating='MAN'OR NEW.rating='EMP'OR NEW.rating='EVAL') THEN
SIGNAL SQLSTATE VALUE '45000'
SET MESSAGE_TEXT = 'Only administrators acceptable!';
END IF;
END$
DELIMITER ;


