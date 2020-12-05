delete
from user_roles;
delete
from document_attributes;
delete
from attribute_values;
delete
from document_states;
delete
from documents;
delete
from tasks;
delete
from users;
delete
from departments;

alter sequence hibernate_sequence restart with 15;

insert into users(id, password, username)
values (1, '$2a$08$zDD0FuCTBwXOrgeNs3QuDOh1q/hOJ1I8p/VqSJT73QTsq.h2oUyzK', '1'),
       (2, '$2a$08$mbvBZSffNx6CJzqwmkWyZ.LnxtYm3RqrIgoJBojY84qz1yDzMPymy', '2');

insert into user_roles(user_id, role_set)
values (1, 'LEAD'),
       (1, 'ADMIN');

insert into tasks(id, ready_to_review, task_name, task_description)
values (3, false, 'eee', 'edm task test');

insert into departments(id, department_name)
values (4, 'test department');

insert into documents(id, file_name, content, user_id)
values (5, 'test document', 'test content', 2);

insert into attribute_values(id, value)
values (6, 'test attribute value');

insert into document_attributes(id, attribute_name, attribute_value_id, document_id)
VALUES (7, 'test attribute', 6, 5);


