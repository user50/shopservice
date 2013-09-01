# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table client_settings (
  id                        varchar(255) not null,
  site_name                 varchar(255),
  site_url                  varchar(255),
  database_url              varchar(255),
  path_to_product_page      varchar(255),
  path_to_product_image     varchar(255),
  constraint pk_client_settings primary key (id))
;

create table product_id_entry (
  id                        varchar(255) not null,
  client_settings_id        varchar(255) not null,
  product_id                varchar(255),
  constraint pk_product_id_entry primary key (id))
;

alter table product_id_entry add constraint fk_product_id_entry_client_settings_1 foreign key (client_settings_id) references client_settings (id) on delete restrict on update restrict;
create index ix_product_id_entry_client_settings_1 on product_id_entry (client_settings_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table client_settings;

drop table product_id_entry;

SET FOREIGN_KEY_CHECKS=1;

