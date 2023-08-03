INSERT INTO demo.users (username, password, phone, email)
SELECT 'admin', '$2a$10$zJlMf5GKepVwcZtyaIg5/ed2aJ0PJ0OyHgKbn.afvY7gr2Vm153gW', '2555555', 'admin@mail.com'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');
