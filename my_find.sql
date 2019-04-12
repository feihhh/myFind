create database if not exists find;

use find;

create table if not exists files(
  name varchar (1024),
  depth int ,
  file_type varchar (10),
  path varchar (2048)
);

