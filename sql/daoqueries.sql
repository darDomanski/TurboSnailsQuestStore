-- queries made for PreparedStatement


-- retrieving user data from qs_user - with id found in login_data
SELECT qs_user.id, first_name, last_name, email, class_.name AS class, user_type.user_type_name AS user_type, user_status.user_status_name AS status
FROM qs_user
INNER JOIN login_data ON qs_user.id = login_data.id
INNER JOIN class_ ON qs_user.class_id = class_.class_id
INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id
INNER JOIN user_status ON qs_user.status = user_status.user_status_id
WHERE login=? AND password=?

-- inserting new user into login_data (values: id, login, password) 
INSERT INTO login_data VALUES (?, ?, ?)

-- 

