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
  encoding                  varchar(255),
  constraint pk_client_settings primary key (id))
;

create table group2product (
  id                        integer auto_increment not null,
  checked                   tinyint(1) default 0,
  product_group_id          integer,
  product_entry_id          varchar(255),
  constraint pk_group2product primary key (id))
;

create table linked_product_entry (
  id                        integer auto_increment not null,
  name                      varchar(255),
  product_provider_id       integer,
  product_entry_id          varchar(255),
  constraint pk_linked_product_entry primary key (id))
;

create table product_entry (
  id                        varchar(255) not null,
  client_settings_id        varchar(255) not null,
  product_id                varchar(255),
  category_id               varchar(255),
  constraint pk_product_entry primary key (id))
;

create table product_group (
  id                        integer auto_increment not null,
  client_settings_id        varchar(255) not null,
  name                      varchar(255),
  format                    integer,
  regional_currency         integer,
  product_currency          integer,
  rate                      varchar(255),
  constraint ck_product_group_format check (format in (0,1)),
  constraint ck_product_group_regional_currency check (regional_currency in (0,1,2,3,4,5,6)),
  constraint ck_product_group_product_currency check (product_currency in (0,1,2,3,4,5,6)),
  constraint pk_product_group primary key (id))
;

create table product_provider (
  id                        integer auto_increment not null,
  client_settings_id        varchar(255) not null,
  name                      varchar(255),
  url                       varchar(255),
  constraint pk_product_provider primary key (id))
;

alter table group2product add constraint fk_group2product_productGroup_1 foreign key (product_group_id) references product_group (id) on delete restrict on update restrict;
create index ix_group2product_productGroup_1 on group2product (product_group_id);
alter table group2product add constraint fk_group2product_productEntry_2 foreign key (product_entry_id) references product_entry (id) on delete restrict on update restrict;
create index ix_group2product_productEntry_2 on group2product (product_entry_id);
alter table linked_product_entry add constraint fk_linked_product_entry_productProvider_3 foreign key (product_provider_id) references product_provider (id) on delete restrict on update restrict;
create index ix_linked_product_entry_productProvider_3 on linked_product_entry (product_provider_id);
alter table linked_product_entry add constraint fk_linked_product_entry_productEntry_4 foreign key (product_entry_id) references product_entry (id) on delete restrict on update restrict;
create index ix_linked_product_entry_productEntry_4 on linked_product_entry (product_entry_id);
alter table product_entry add constraint fk_product_entry_client_settings_5 foreign key (client_settings_id) references client_settings (id) on delete restrict on update restrict;
create index ix_product_entry_client_settings_5 on product_entry (client_settings_id);
alter table product_group add constraint fk_product_group_client_settings_6 foreign key (client_settings_id) references client_settings (id) on delete restrict on update restrict;
create index ix_product_group_client_settings_6 on product_group (client_settings_id);
alter table product_provider add constraint fk_product_provider_client_settings_7 foreign key (client_settings_id) references client_settings (id) on delete restrict on update restrict;
create index ix_product_provider_client_settings_7 on product_provider (client_settings_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table client_settings;

drop table group2product;

drop table linked_product_entry;

drop table product_entry;

drop table product_group;

drop table product_provider;

SET FOREIGN_KEY_CHECKS=1;

