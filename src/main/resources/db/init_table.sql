
drop table if exists cart_item;
drop table if exists cart;
drop table if exists favorite;
drop table if exists order_item;
drop table if exists orders;
drop table if exists review;
drop table if exists menu;
drop table if exists store;
drop table if exists member;

create table cart (
                      cart_id bigint not null auto_increment,
                      member_id bigint not null,
                      store_id bigint not null,
                      primary key (cart_id)
);
create table cart_item (
                           cart_item_id bigint not null auto_increment,
                           quantity integer,
                           cart_id bigint not null,
                           menu_id bigint not null,
                           primary key (cart_item_id)
);
create table favorite (
                          favorite_id bigint not null auto_increment,
                          member_id bigint not null,
                          menu_id bigint not null,
                          primary key (favorite_id)
);
create table member (
                        member_id bigint not null auto_increment,
                        address varchar(255),
                        nickname varchar(255),
                        password varchar(255),
                        phone varchar(255),
                        role enum ('CUSTOMER','OWNER'),
                        userid varchar(255),
                        primary key (member_id)
);
create table menu (
                      menu_id bigint not null auto_increment,
                      category enum ('POPULAR','NEW','SIDE','BEVERAGE'),
                      explanation varchar(255),
                      name varchar(255),
                      price integer,
                      store_id bigint not null,
                      primary key (menu_id)
);
create table order_item (
                            order_item_id bigint not null auto_increment,
                            quantity integer,
                            menu_id bigint not null,
                            order_id bigint not null,
                            primary key (order_item_id)
);
create table orders (
                        order_id bigint not null auto_increment,
                        created_at datetime(6) not null,
                        updated_at datetime(6) not null,
                        payment_method enum ('CREDIT_CARD','KAKAO','CASH'),
                        requests varchar(255),
                        status enum ('WAITING_PAY','COMPLETE_PAY','CONFIRMED','CANCEL','DE_START','DE_ING','DE_FINISH'),
                        total_price integer,
                        member_id bigint not null,
                        store_id bigint not null,
                        primary key (order_id)
);
create table review (
                        review_id bigint not null auto_increment,
                        created_at datetime(6) not null,
                        updated_at datetime(6) not null,
                        content varchar(255),
                        member_id bigint not null,
                        menu_id bigint not null,
                        store_id bigint not null,
                        primary key (review_id)
);
create table store (
                       store_id bigint not null auto_increment,
                       address varchar(255),
                       content varchar(255),
                       delivery_tip integer,
                       max_delivery_time integer,
                       min_delivery_price integer,
                       min_delivery_time integer,
                       name varchar(255),
                       phone varchar(255),
                       member_id bigint not null,
                       primary key (store_id)
);