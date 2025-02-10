-- ChangeSet insert-sample-loan-data-3 (author: cbilgic)
-- Loan with 6 installments and 0.2 interest rate
INSERT INTO public.loan (LOAN_ID, loan_amount, number_of_installments, create_date, is_paid, customer_guid)
VALUES (10003, 5000.00, 6, '2024-01-01', false, 'tester1@inghubs.com');

-- Loan with 9 installments and 0.2 interest rate
INSERT INTO public.loan (LOAN_ID, loan_amount, number_of_installments, create_date, is_paid, customer_guid)
VALUES (10004, 7000.00, 9, '2025-01-15', false, 'tester1@inghubs.com');

-- Loan with 9 installments and 0.2 interest rate
INSERT INTO public.loan (LOAN_ID, loan_amount, number_of_installments, create_date, is_paid, customer_guid)
VALUES (10005, 10000.00, 9, '2025-02-15', false, 'tester2@inghubs.com');

-- ChangeSet insert-sample-loan-data-4 (author: cbilgic)
-- Loan with 6 installments and 0.3 interest rate
INSERT INTO public.loan (LOAN_ID, loan_amount, number_of_installments, create_date, is_paid, customer_guid)
VALUES (10006, 5000.00, 6, '2024-10-01', false, 'tester3@inghubs.com');

-- ChangeSet insert-sample-loan-installment-data-3 (author: cbilgic)
-- Installments for loan with 6 installments and 0.2 interest rate (10003)
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10024, 1000.00, 0.00, '2025-02-01', false, 10003);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10025, 1000.00, 0.00, '2025-03-01', false, 10003);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10026, 1000.00, 0.00, '2025-04-01', false, 10003);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10027, 1000.00, 0.00, '2025-05-01', false, 10003);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10028, 1000.00, 0.00, '2025-06-01', false, 10003);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10029, 1000.00, 0.00, '2025-07-01', false, 10003);

-- Installments for loan with 9 installments and 0.2 interest rate (10004)
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10030, 933.33, 0.00, '2025-02-01', false, 10004);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10031, 933.33, 0.00, '2025-03-01', false, 10004);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10032, 933.33, 0.00, '2025-04-01', false, 10004);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10033, 933.33, 0.00, '2025-05-01', false, 10004);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10034, 933.33, 0.00, '2025-06-01', false, 10004);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10035, 933.33, 0.00, '2025-07-01', false, 10004);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10036, 933.33, 0.00, '2025-08-01', false, 10004);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10037, 933.33, 0.00, '2025-09-01', false, 10004);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10038, 933.33, 0.00, '2025-10-01', false, 10004);

-- Installments for loan with 9 installments and 0.2 interest rate (10005)
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10039, 1333.33, 0.00, '2025-03-01', false, 10005);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10040, 1333.33, 0.00, '2025-04-01', false, 10005);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10041, 1333.33, 0.00, '2025-05-01', false, 10005);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10042, 1333.33, 0.00, '2025-06-01', false, 10005);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10043, 1333.33, 0.00, '2025-07-01', false, 10005);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10044, 1333.33, 0.00, '2025-08-01', false, 10005);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10045, 1333.33, 0.00, '2025-09-01', false, 10005);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10046, 1333.33, 0.00, '2025-10-01', false, 10005);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10047, 1333.33, 0.00, '2025-11-01', false, 10005);

-- ChangeSet insert-sample-loan-installment-data-4 (author: cbilgic)
-- Installments for loan with 6 installments and 0.3 interest rate (10006)
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10048, 1150.00, 0.00, '2024-11-01', false, 10006);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10049, 1150.00, 0.00, '2024-12-01', false, 10006);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10050, 1150.00, 0.00, '2025-01-01', false, 10006);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10051, 1150.00, 0.00, '2025-02-01', false, 10006);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10052, 1150.00, 0.00, '2025-03-01', false, 10006);
INSERT INTO public.loan_installment (ID, amount, paid_amount, due_date, is_paid, loan_id)
VALUES (10053, 1150.00, 0.00, '2025-04-01', false, 10006);
