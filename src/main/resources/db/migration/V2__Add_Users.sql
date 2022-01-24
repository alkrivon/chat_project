insert into role_table (id, name)
    values ("1", "ROLE_ADMIN"), ("2", "ROLE_MODERATOR"), ("3", "ROLE_USER");

insert into users (id, login, password, status, username, role_id)
    values ("1", "Admin", "$2a$10$UbnmeKQQ8FeP7ObqQU8P3OMw5UYAxRjGRr4ZXQGwJDzd/ajHsgRuO", true, "Alex", "1"),
           ("2", "Moderator", "$2a$10$BoWshf2qKAgjFWV0DQAXtugOX8lHKcxGpRzkyiRZteExxTabwq04.", true, "Misha", "2"),
           ("3", "User", "$2a$10$ACN0vUVAgOmbynMAS5rWxeOcnzV7H1McJU.62tEXmKJZ0A8Pxa5u6", true, "Ivan", "3");
