-- ChangeSet create-roles-table (author: cbilgic)
CREATE TABLE public.roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);

-- ChangeSet create-users-table (author: cbilgic)
CREATE TABLE public.users
(
    id       INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    guid     VARCHAR(50)  NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled  BOOLEAN      NOT NULL
);

-- ChangeSet create-user-role-table (author: cbilgic)
CREATE TABLE public.users_roles
(
    users_id INT NOT NULL,
    roles_id INT NOT NULL,
    CONSTRAINT fk_user_role_user FOREIGN KEY (users_id) REFERENCES public.users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_role_role FOREIGN KEY (roles_id) REFERENCES public.roles (id) ON DELETE CASCADE
);

-- ChangeSet create-customer-table (author: cbilgic)
CREATE TABLE public.customer
(
    id                INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    guid              VARCHAR(50)    NOT NULL,
    name              VARCHAR(255)   NOT NULL,
    surname           VARCHAR(255)   NOT NULL,
    credit_limit      NUMERIC(10, 2) NOT NULL,
    used_credit_limit NUMERIC(10, 2) NOT NULL
);

-- ChangeSet create-loan-table (author: cbilgic)
CREATE TABLE public.loan
(
    loan_id                INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    loan_amount            NUMERIC(10, 2) NOT NULL,
    number_of_installments INT            NOT NULL,
    create_date            TIMESTAMP      NOT NULL,
    is_paid                BOOLEAN        NOT NULL,
    customer_guid          VARCHAR(50)    NOT NULL
);

-- ChangeSet create-loan-installment-table (author: cbilgic)
CREATE TABLE public.loan_installment
(
    id           INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    amount       NUMERIC(10, 2) NOT NULL,
    paid_amount  NUMERIC(10, 2) NOT NULL,
    due_date     TIMESTAMP      NOT NULL,
    payment_date TIMESTAMP,
    is_paid      BOOLEAN        NOT NULL,
    loan_id      INT            NOT NULL,
    CONSTRAINT fk_loan_installment_loan FOREIGN KEY (loan_id) REFERENCES public.loan (loan_id) ON DELETE CASCADE
);

