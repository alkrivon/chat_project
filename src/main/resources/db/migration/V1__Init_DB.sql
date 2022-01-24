 create table hibernate_sequence (next_val bigint);
 insert into hibernate_sequence values ( 1 );
 insert into hibernate_sequence values ( 1 );
 insert into hibernate_sequence values ( 1 );
 insert into hibernate_sequence values ( 1 );

 create table message (
        id bigint not null,
        content varchar(255),
        author bigint,
        room bigint,
        primary key (id));

 create table role_table (
        id bigint not null,
        name varchar(255),
        primary key (id));

 create table room (
        id bigint not null,
        name varchar(255),
        private_status bit,
        owner_id bigint,
        primary key (id));

 create table users (
        id bigint not null,
        ban_end datetime,
        login varchar(255),
        password varchar(255),
        status bit,
        username varchar(255),
        role_id bigint,
        primary key (id));

 create table user_room (
        user_id bigint not null,
        room_id bigint not null);

 alter table message
     add constraint FK3w1yhajbdybb5eei6c2l01jou
     foreign key (author) references users (id);

alter table message
    add constraint FKgv80pbinm2xc6e29upb9ieqxj
    foreign key (room) references room (id);

alter table room
    add constraint FKbx9snvq7gghcs7i2hjasjln6f
    foreign key (owner_id) references users (id);

alter table users
    add constraint FKerifq1qk6nnbs8ahyb6t2ijxc
    foreign key (role_id) references role_table (id);

alter table user_room
    add constraint FKt69dqc3yclx55jpu6lal8xna8
    foreign key (room_id) references room (id);

alter table user_room
    add constraint FK1cpknce05q8qk8vdddrf97r2u
    foreign key (user_id) references users (id);