use finlake;
create TABLE `user` (
  `id` varchar(36) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(36) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_type` varchar(36) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create TABLE `finance_room` (
  `id` varchar(36) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `created_by` varchar(36) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_finance_rooms_users` (`created_by`),
  CONSTRAINT `FK_finance_rooms_users` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
);

create TABLE `room_user` (
  `id` varchar(36) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `finance_room` varchar(36) DEFAULT NULL,
  `user` varchar(36) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_room_users_finance_rooms` (`finance_room`),
  KEY `FK_room_users_users` (`user`),
  CONSTRAINT `FK_room_users_finance_rooms` FOREIGN KEY (`finance_room`) REFERENCES `finance_room` (`id`),
  CONSTRAINT `FK_room_users_users` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
);

create TABLE `transaction` (
  `id` varchar(36) NOT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `finance_room` varchar(36) DEFAULT NULL,
  `paid_by_user` varchar(36) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_transactions_finance_rooms` (`finance_room`),
  KEY `FK_transactions_users` (`paid_by_user`),
  CONSTRAINT `FK_transactions_finance_rooms` FOREIGN KEY (`finance_room`) REFERENCES `finance_room` (`id`),
  CONSTRAINT `FK_transactions_users` FOREIGN KEY (`paid_by_user`) REFERENCES `user` (`id`)
);

create TABLE `transaction_split` (
  `id` varchar(36) NOT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `transaction` varchar(36) DEFAULT NULL,
  `user` varchar(36) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_transaction_splits_transactions` (`transaction`),
  KEY `FK_transaction_splits_users` (`user`),
  CONSTRAINT `FK_transaction_splits_transactions` FOREIGN KEY (`transaction`) REFERENCES `transaction` (`id`),
  CONSTRAINT `FK_transaction_splits_users` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
);

create TABLE `response_mapper` (
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
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1004', '401', 'Unauthorized', 'Authorization failed due to various reasons.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('1005', '500', 'Data Access Error', 'Not able to access the database.');

insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2003', '200', 'User already exists', 'User profile already exists for this email.');
insert into `response_mapper` (`response_code`, `http_status_code`, `response_constant`, `response_message`) values ('2004', '200', 'User does not exists', 'User profile does not exists for this email.');
