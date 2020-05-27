# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table mark (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  subject_id                    varchar(255),
  value                         float not null,
  constraint pk_mark primary key (id)
);

create table subject (
  id                            varchar(255) not null,
  name                          varchar(255),
  weight                        bigint not null,
  constraint pk_subject primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  login                         varchar(255),
  constraint pk_user primary key (id)
);

create index ix_mark_user_id on mark (user_id);
alter table mark add constraint fk_mark_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_mark_subject_id on mark (subject_id);
alter table mark add constraint fk_mark_subject_id foreign key (subject_id) references subject (id) on delete restrict on update restrict;


# --- !Downs

alter table mark drop constraint if exists fk_mark_user_id;
drop index if exists ix_mark_user_id;

alter table mark drop constraint if exists fk_mark_subject_id;
drop index if exists ix_mark_subject_id;

drop table if exists mark;

drop table if exists subject;

drop table if exists user;

