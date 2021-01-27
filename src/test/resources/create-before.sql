delete
from user_role;
delete
from document_attribute;
delete
from attribute_value;
delete
from document_state;
delete
from document;
delete
from department_employee;
delete
from task_employee;
delete
from message;
delete
from task;
delete
from employee;
delete
from "user";
delete
from department;

alter sequence hibernate_sequence restart with 20;

insert into "user"(id, password, username)
values (1, '$2a$08$zDD0FuCTBwXOrgeNs3QuDOh1q/hOJ1I8p/VqSJT73QTsq.h2oUyzK', '1'),
       (2, '$2a$08$mbvBZSffNx6CJzqwmkWyZ.LnxtYm3RqrIgoJBojY84qz1yDzMPymy', '2');

insert into user_role(user_id, role)
values (1, 'LEAD'),
       (1, 'ADMIN');

insert into task(id, ready_to_review, name, task_description)
values (3, false, 'eee', 'edm task test');

insert into department(id, left_key, level, name, parent_id, right_key)
values (1, 1, 1, '1', 1, 2);

insert into employee (user_id, full_name, phone)
values (1, 'test employee', 1);

insert into document(id, name, content)
values (5, 'test document', 'test content');

insert into attribute_value(id, value)
values (6, 'test attribute value');

insert into document_attribute(id, name, attribute_value_id, document_id)
VALUES (7, 'test attribute', 6, 5);

insert into message (id, creation_date, text, user_id, task_id)
values (8, '2020-12-18 19:26:57.216188', 'hello message hi privet', 1, 3);


