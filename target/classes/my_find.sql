create table if not exists files(
  name varchar (1024),
  name_length int,
  depth int ,
  file_type varchar (10),
  path varchar (2048)
);
create index if not exists index_name on files(name);