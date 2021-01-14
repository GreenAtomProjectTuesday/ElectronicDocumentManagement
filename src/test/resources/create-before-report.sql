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
from task;
delete
from employee;
delete
from "user";
delete
from department;

alter sequence hibernate_sequence restart with 15;

insert into "user"(id, password, username)
values (1, '$2a$08$zDD0FuCTBwXOrgeNs3QuDOh1q/hOJ1I8p/VqSJT73QTsq.h2oUyzK', '1'),
       (2, '$2a$08$mbvBZSffNx6CJzqwmkWyZ.LnxtYm3RqrIgoJBojY84qz1yDzMPymy', '2');

insert into user_role(user_id, role)
values (1, 'LEAD'),
       (1, 'ADMIN');

insert into task(id, creation_date,expiry_date,ready_to_review, name, task_description)
values (3, '2020-01-08 10:23:54','2020-02-18 12:00:00',false, 'eee', 'edm task test');

insert into department(id, name)
values (4, 'test department');

insert into document(id, name, content, user_id,creation_date,task_id)
values (1, 'test document', 'test content', 2,'2020-01-10 10:00:00',3),
     (2, 'test document2', 'test content2', 1,'2020-01-11 10:00:00',3),
     (3, 'test document3', 'test content3', 2,'2020-01-12 10:00:00',3);

insert into employee(user_id, full_Name,phone)
values (1, 'Aidar', '1111111111'),
     (2, 'Victor', '222222');

insert into task_employee(id, employee_id,task_id, assignment_date)
values (1, 1, 3,'2020-01-09 10:00:00'),
     (2, 2,  3,'2020-01-09 10:00:00');



