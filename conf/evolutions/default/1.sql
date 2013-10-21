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
  password                  varchar(255),
  constraint pk_client_settings primary key (id))
;

create table product_entry (
  id                        varchar(255) not null,
  client_settings_id        varchar(255) not null,
  product_id                varchar(255),
  product_name              varchar(255),
  category_id               varchar(255),
  constraint pk_product_entry primary key (id))
;

create table site (
  id                        integer auto_increment not null,
  client_settings_id        varchar(255) not null,
  name                      varchar(255),
  constraint pk_site primary key (id))
;

create table site2product (
  id                        integer auto_increment not null,
  checked                   tinyint(1) default 0,
  site_id                   integer,
  product_entry_id          varchar(255),
  constraint pk_site2product primary key (id))
;

alter table product_entry add constraint fk_product_entry_client_settings_1 foreign key (client_settings_id) references client_settings (id) on delete restrict on update restrict;
create index ix_product_entry_client_settings_1 on product_entry (client_settings_id);
alter table site add constraint fk_site_client_settings_2 foreign key (client_settings_id) references client_settings (id) on delete restrict on update restrict;
create index ix_site_client_settings_2 on site (client_settings_id);
alter table site2product add constraint fk_site2product_site_3 foreign key (site_id) references site (id) on delete restrict on update restrict;
create index ix_site2product_site_3 on site2product (site_id);
alter table site2product add constraint fk_site2product_productEntry_4 foreign key (product_entry_id) references product_entry (id) on delete restrict on update restrict;
create index ix_site2product_productEntry_4 on site2product (product_entry_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table client_settings;

drop table product_entry;

drop table site;

drop table site2product;

SET FOREIGN_KEY_CHECKS=1;

