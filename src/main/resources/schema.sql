use finlake;
create TABLE IF NOT EXISTS `user` (
  `id` varchar(36) NOT NULL,
  `request_id` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(36) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_type` varchar(36) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create TABLE IF NOT EXISTS `finance_room` (
  `id` varchar(36) NOT NULL,
  `request_id` varchar(50) NOT NULL,
  `finance_room_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(36) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create TABLE IF NOT EXISTS `room_user` (
  `id` varchar(36) NOT NULL,
  `request_id` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `finance_room_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create TABLE IF NOT EXISTS `transaction` (
  `id` varchar(36) NOT NULL,
  `request_id` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `finance_room_id` varchar(36) DEFAULT NULL,
  `paid_by_user_id` varchar(36) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create TABLE IF NOT EXISTS `transaction_split` (
  `id` varchar(36) NOT NULL,
  `request_id` varchar(50) NOT NULL,
  `transaction_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `received_by_user_id` varchar(36) DEFAULT NULL,
  `paid_by_user_id` varchar(36) DEFAULT NULL,
  `split_percent` int DEFAULT NULL,
  `amount` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create TABLE IF NOT EXISTS `response_mapper` (
	`id` bigint NOT NULL auto_increment,
    `response_code` varchar(20) NOT NULL,
    `http_status_code` varchar(20) NOT NULL,
    `response_constant` varchar(100) NOT NULL,
    `response_message` TEXT NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY response_code (response_code),
    UNIQUE KEY response_constant (response_constant)
);

insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1000', '400', 'Internal Server Exception', 'Request is not valid.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1001', '400', 'Bad Request', 'Request is not valid.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1002', '412', 'Missing Mandatory Params', 'Request is not valid.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1004', '401', 'Unauthorized', 'Authorization failed due to various reasons.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1005', '500', 'Data Access Error', 'Not able to access the database.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1006', '500', 'Data Converion Error', 'Not able to convert the data.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1007', '400', 'User does not exists', 'User profile does not exists.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1008', '400', 'Authentication Failed', 'User Authentication Failed.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1009', '400', 'Token Generation Error', 'User Token Generation Failed.');

insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2000', '200', 'User Registered Succesfully', 'User registered successfully.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2001', '200', 'User Authorized Succesfully', 'User authorized successfully.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2002', '200', 'All Users Fetched', 'All User profile fetched.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2003', '202', 'User already exists', 'User profile already exists for this email.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2004', '200', 'Finance Room Created', 'A new finance room created.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2005', '200', 'Finance Room Fetched', 'All finance room fetched.');

insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2006', '200', 'Room User Created', 'A new user has added to the room.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2007', '200', 'Room Users fetched', 'Room users fetched.');