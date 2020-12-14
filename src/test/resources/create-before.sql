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

insert into task(id, ready_to_review, name, task_description)
values (3, false, 'eee', 'edm task test');

insert into department(id, name)
values (4, 'test department');

insert into document(id, name, content, user_id)
values (5, 'test document', 'test content', 2);

insert into attribute_value(id, value)
values (6, 'test attribute value');

insert into document_attribute(id, name, attribute_value_id, document_id)
VALUES (7, 'test attribute', 6, 5);


