create table orders
(
  id varchar(36) not null,
  name varchar(255) not null,
  temp varchar(255) not null,
  shelf_life integer not null,
  decay_rate float(4) not null,
  creation_time datetime not null,
  status integer not null,
  primary key(id)
);

create table input_files
(
  file_name varchar(255) not null,
  status integer not null,
  primary key(file_name)
);