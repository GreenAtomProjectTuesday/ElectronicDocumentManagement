insert into department(id, left_key, level, name, parent_id, right_key)
values (2, 2, 2, '2', 1, 9);
insert into department(id, left_key, level, name, parent_id, right_key)
values (3, 10, 2, '3', 1, 23);
insert into department(id, left_key, level, name, parent_id, right_key)
values (4, 24, 2, '4', 1, 31);
insert into department(id, left_key, level, name, parent_id, right_key)
values (5, 3, 3, '5', 2, 8);
insert into department(id, left_key, level, name, parent_id, right_key)
values (6, 11, 3, '6', 3, 12);
insert into department(id, left_key, level, name, parent_id, right_key)
values (7, 13, 3, '7', 3, 20);
insert into department(id, left_key, level, name, parent_id, right_key)
values (8, 21, 3, '8', 3, 22);
insert into department(id, left_key, level, name, parent_id, right_key)
values (9, 25, 3, '9', 4, 30);
insert into department(id, left_key, level, name, parent_id, right_key)
values (10, 4, 4, '10', 5, 5);
insert into department(id, left_key, level, name, parent_id, right_key)
values (11, 6, 4, '11', 5, 7);
insert into department(id, left_key, level, name, parent_id, right_key)
values (12, 14, 4, '12', 7, 15);
insert into department(id, left_key, level, name, parent_id, right_key)
values (13, 16, 4, '13', 7, 17);
insert into department(id, left_key, level, name, parent_id, right_key)
values (14, 18, 4, '14', 7, 19);
insert into department(id, left_key, level, name, parent_id, right_key)
values (15, 26, 4, '15', 9, 27);
insert into department(id, left_key, level, name, parent_id, right_key)
values (16, 28, 4, '16', 9, 29);
UPDATE department
set right_key = 32
where id = 1;
