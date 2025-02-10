-- ChangeSet insert-default-roles-1 (author: cbilgic)
INSERT INTO public.roles (ID, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles (ID, name) VALUES (2, 'ROLE_CUSTOMER');

-- ChangeSet insert-default-users-1 (author: cbilgic)
INSERT INTO public.users (ID, guid, username, password, enabled) VALUES (1, 'admin@inghubs.com', 'admin', '$2b$12$pbq27AjADOMA0VY/dUiG/uij5KDX6PLSjKgif4COIuXuI3whcPWZ2', true);  -- Encoded password 'admin'
INSERT INTO public.users (ID, guid, username, password, enabled) VALUES (2, 'tester1@inghubs.com', 'tester1', '$2b$12$pbq27AjADOMA0VY/dUiG/uij5KDX6PLSjKgif4COIuXuI3whcPWZ2', true);
INSERT INTO public.users (ID, guid, username, password, enabled) VALUES (3, 'tester2@inghubs.com', 'tester2', '$2b$12$pbq27AjADOMA0VY/dUiG/uij5KDX6PLSjKgif4COIuXuI3whcPWZ2', true);
INSERT INTO public.users (ID, guid, username, password, enabled) VALUES (4, 'tester3@inghubs.com', 'tester3', '$2b$12$pbq27AjADOMA0VY/dUiG/uij5KDX6PLSjKgif4COIuXuI3whcPWZ2', true);

-- ChangeSet assign-roles-to-users-1 (author: cbilgic)
INSERT INTO public.users_roles (users_id, roles_id) VALUES (1, 1);
INSERT INTO public.users_roles (users_id, roles_id) VALUES (2, 2);
INSERT INTO public.users_roles (users_id, roles_id) VALUES (3, 2);

-- ChangeSet insert-sample-customer-data-1 (author: cbilgic)
INSERT INTO public.customer (ID, guid, name, surname, credit_limit, used_credit_limit)
VALUES (1, 'admin@inghubs.com', 'admin', 'admin', 0.00, 0.00);
INSERT INTO public.customer (ID, guid, name, surname, credit_limit, used_credit_limit)
VALUES (2, 'tester1@inghubs.com', 'tester1', 'tester1', 100000.00, 0.00);
INSERT INTO public.customer (ID, guid, name, surname, credit_limit, used_credit_limit)
VALUES (3, 'tester2@inghubs.com', 'tester2', 'tester2', 150000.00, 5000.00);
INSERT INTO public.customer (ID, guid, name, surname, credit_limit, used_credit_limit)
VALUES (4, 'tester3@inghubs.com', 'tester3', 'tester3', 150000.00, 5000.00);