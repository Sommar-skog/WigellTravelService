
--Customers
INSERT INTO travel_customer(customer_name, username, password) VALUES
                                                                   ('Amanda', 'amanda', '{noop}amanda'),
                                                                   ('Sara','sara','{noop}sara'),
                                                                   ('Alexander', 'alex','{noop}alex');

-- TravelPaclages
INSERT INTO travel_package (hotel_name, destination, week_price) VALUES
                                                                     ('Hotel Lumière', 'Paris, France', 8500.00),
                                                                     ('Hotel Colosseo', 'Rome, Italy', 7800.00),
                                                                     ('Hotel Branden', 'Berlin, Germany', 7200.00),
                                                                     ('Hotel Sol', 'Madrid, Spain', 6900.00),
                                                                     ('Hotel Mar', 'Lisbon, Portugal', 7100.00);

-- Amanda
INSERT INTO travel_booking (start_date, end_date, number_of_weeks, total_price, cancelled, customer_id, trip_id) VALUES
                                                                                                                     ('2025-10-01', '2025-10-15', 2, 17000.00, false, 1, 1), -- kommande
                                                                                                                     ('2025-09-10', '2025-09-24', 2, 14200.00, false, 1, 3), -- pågående
                                                                                                                     ('2025-07-01', '2025-07-15', 2, 13800.00, false, 1, 4), -- avslutad
                                                                                                                     ('2025-06-01', '2025-06-15', 2, 17000.00, true, 1, 1),  -- avbokad
                                                                                                                     ('2025-08-01', '2025-08-15', 2, 15600.00, true, 1, 2);  -- avbokad

-- Sara
INSERT INTO travel_booking (start_date, end_date, number_of_weeks, total_price, cancelled, customer_id, trip_id) VALUES
                                                                                                                     ('2025-10-10', '2025-10-24', 2, 15600.00, false, 2, 2), -- kommande
                                                                                                                     ('2025-09-05', '2025-09-19', 2, 14200.00, false, 2, 3), -- pågående
                                                                                                                     ('2025-05-01', '2025-05-15', 2, 17000.00, false, 2, 1), -- avslutad
                                                                                                                     ('2025-07-01', '2025-07-15', 2, 13800.00, true, 2, 4),  -- avbokad
                                                                                                                     ('2025-06-10', '2025-06-24', 2, 14200.00, true, 2, 3);  -- avbokad

-- Alexander
INSERT INTO travel_booking (start_date, end_date, number_of_weeks, total_price, cancelled, customer_id, trip_id) VALUES
                                                                                                                     ('2025-11-01', '2025-11-15', 2, 14200.00, false, 3, 3), -- kommande
                                                                                                                     ('2025-09-12', '2025-09-26', 2, 13800.00, false, 3, 4), -- pågående
                                                                                                                     ('2025-04-01', '2025-04-15', 2, 17000.00, false, 3, 1), -- avslutad
                                                                                                                     ('2025-07-15', '2025-07-29', 2, 14200.00, true, 3, 3),  -- avbokad
                                                                                                                     ('2025-08-10', '2025-08-24', 2, 15600.00, true, 3, 2);  -- avbokad
