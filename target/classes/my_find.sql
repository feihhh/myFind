create table if not exists files(
  id int primary key auto_increment,
  name varchar (1024),
  depth int ,
  file_type varchar (10),
  path varchar (2048)
);