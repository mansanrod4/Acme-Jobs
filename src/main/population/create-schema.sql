
    create table `administrator` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        primary key (`id`)
    ) engine=InnoDB;

    create table `announcement` (
       `id` integer not null,
        `version` integer not null,
        `moment` datetime(6),
        `more_info` varchar(255),
        `text` varchar(1024),
        `title` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `anonymous` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        primary key (`id`)
    ) engine=InnoDB;

    create table `application` (
       `id` integer not null,
        `version` integer not null,
        `creation_moment` datetime(6),
        `justification` varchar(1024),
        `last_modification` datetime(6),
        `qualifications` varchar(1024),
        `reference_number` varchar(255),
        `skills` varchar(1024),
        `statement` varchar(1024),
        `status` integer,
        `job_id` integer not null,
        `worker_id` integer not null,
        primary key (`id`)
    ) engine=InnoDB;

    create table `audit` (
       `id` integer not null,
        `version` integer not null,
        `body` varchar(1024),
        `moment` datetime(6),
        `status` integer,
        `title` varchar(255),
        `auditor_id` integer not null,
        `job_id` integer not null,
        primary key (`id`)
    ) engine=InnoDB;

    create table `auditor` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        `firm` varchar(255),
        `statement` varchar(1024),
        primary key (`id`)
    ) engine=InnoDB;

    create table `auditor_rol_request` (
       `id` integer not null,
        `version` integer not null,
        `approved` bit not null,
        `user_id` integer not null,
        primary key (`id`)
    ) engine=InnoDB;

    create table `authenticated` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        primary key (`id`)
    ) engine=InnoDB;

    create table `challenge` (
       `id` integer not null,
        `version` integer not null,
        `deadline` datetime(6),
        `description` varchar(1024),
        `goal_bronze` varchar(255),
        `goal_gold` varchar(255),
        `goal_silver` varchar(255),
        `moment` datetime(6),
        `reward_bronze_amount` double precision,
        `reward_bronze_currency` varchar(255),
        `reward_gold_amount` double precision,
        `reward_gold_currency` varchar(255),
        `reward_silver_amount` double precision,
        `reward_silver_currency` varchar(255),
        `title` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `commercial_banner` (
       `id` integer not null,
        `version` integer not null,
        `picture` varchar(255),
        `slogan` varchar(255),
        `targeturl` varchar(255),
        `sponsor_id` integer not null,
        `credit_card_number` varchar(255),
        `cvv` varchar(255),
        `expiration_date` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `company_record` (
       `id` integer not null,
        `version` integer not null,
        `activdescription` varchar(1024),
        `ceoname` varchar(255),
        `companyname` varchar(255),
        `email` varchar(255),
        `incorporated` bit not null,
        `phone` varchar(255),
        `rating` integer,
        `sector` varchar(255),
        `website` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `consumer` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        `company` varchar(255),
        `sector` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `duty` (
       `id` integer not null,
        `version` integer not null,
        `description` varchar(1024),
        `percentage_time_week` double precision,
        `title` varchar(255),
        `job_id` integer not null,
        primary key (`id`)
    ) engine=InnoDB;

    create table `employer` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        `company` varchar(255),
        `sector` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `investor` (
       `id` integer not null,
        `version` integer not null,
        `investing_statement` varchar(1024),
        `name` varchar(255),
        `sector` varchar(255),
        `star` integer,
        primary key (`id`)
    ) engine=InnoDB;

    create table `job` (
       `id` integer not null,
        `version` integer not null,
        `dead_line` datetime(6),
        `description` varchar(1024),
        `final_mode` bit not null,
        `more_info` varchar(255),
        `reference` varchar(255),
        `salary_amount` double precision,
        `salary_currency` varchar(255),
        `title` varchar(255),
        `employer_id` integer not null,
        primary key (`id`)
    ) engine=InnoDB;

    create table `message` (
       `id` integer not null,
        `version` integer not null,
        `body` varchar(1024),
        `moment` datetime(6),
        `tags` varchar(255),
        `title` varchar(255),
        `author_id` integer not null,
        primary key (`id`)
    ) engine=InnoDB;

    create table `messagethread` (
       `id` integer not null,
        `version` integer not null,
        `moment` datetime(6),
        `title` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `messagethread_message` (
       `messagethread_id` integer not null,
        `messages_id` integer not null
    ) engine=InnoDB;

    create table `non_commercial_banner` (
       `id` integer not null,
        `version` integer not null,
        `picture` varchar(255),
        `slogan` varchar(255),
        `targeturl` varchar(255),
        `sponsor_id` integer not null,
        `jingle` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `offer` (
       `id` integer not null,
        `version` integer not null,
        `dead_line` datetime(6),
        `moment` datetime(6),
        `money_amount` double precision,
        `money_currency` varchar(255),
        `text` varchar(1024),
        `ticker` varchar(255),
        `title` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `provider` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        `company` varchar(255),
        `sector` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `request` (
       `id` integer not null,
        `version` integer not null,
        `creation_moment` datetime(6),
        `dead_line` datetime(6),
        `reward_amount` double precision,
        `reward_currency` varchar(255),
        `text` varchar(1024),
        `ticker` varchar(255),
        `title` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `sponsor` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        `credit_card_number` varchar(255),
        `cvv` varchar(255),
        `expiration_date` varchar(255),
        `org_name` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `sysconfig` (
       `id` integer not null,
        `version` integer not null,
        `spamwords` varchar(255),
        `threshold` double precision,
        primary key (`id`)
    ) engine=InnoDB;

    create table `user_account` (
       `id` integer not null,
        `version` integer not null,
        `enabled` bit not null,
        `identity_email` varchar(255),
        `identity_name` varchar(255),
        `identity_surname` varchar(255),
        `password` varchar(255),
        `username` varchar(255),
        primary key (`id`)
    ) engine=InnoDB;

    create table `userthread` (
       `id` integer not null,
        `version` integer not null,
        `creator` bit,
        `authenticated_id` integer not null,
        `thread_id` integer not null,
        primary key (`id`)
    ) engine=InnoDB;

    create table `worker` (
       `id` integer not null,
        `version` integer not null,
        `user_account_id` integer,
        `qualifications` varchar(1024),
        `skills` varchar(1024),
        primary key (`id`)
    ) engine=InnoDB;

    create table `hibernate_sequence` (
       `next_val` bigint
    ) engine=InnoDB;

    insert into `hibernate_sequence` values ( 1 );
create index IDXnhikaa2dj3la6o2o7e9vo01y0 on `announcement` (`moment`);
create index IDX2q2747fhp099wkn3j2yt05fhs on `application` (`status`);
create index IDX1qe6h389w3v57lxb8b5w5llql on `auditor_rol_request` (`approved`);

    alter table `auditor_rol_request` 
       add constraint UK_b9gvggyngpcfiv0t6xnkfa1lt unique (`user_id`);
create index IDX3o72alr4ryyvjly6hxvavkqwx on `company_record` (`rating`);
create index IDX2psiob2l625wbcjcq6rac7jxd on `company_record` (`sector`);
create index IDX1slmmcr1g0wv9jbgun6rny0oy on `investor` (`sector`);
create index IDXe5jvxco33n96220y2wcjaqi98 on `investor` (`star`);
create index IDXt84ibbldao4ngscmvo7ja0es on `job` (`final_mode`);

    alter table `job` 
       add constraint UK_7jmfdvs0b0jx7i33qxgv22h7b unique (`reference`);

    alter table `messagethread_message` 
       add constraint UK_onjw1avx2wwkebc469bjmn2kt unique (`messages_id`);

    alter table `offer` 
       add constraint UK_iex7e8fs0fh89yxpcnm1orjkm unique (`ticker`);

    alter table `request` 
       add constraint UK_9mxq3powq8tqctclj0fbi2nih unique (`ticker`);

    alter table `user_account` 
       add constraint UK_castjbvpeeus0r8lbpehiu0e4 unique (`username`);

    alter table `administrator` 
       add constraint FK_2a5vcjo3stlfcwadosjfq49l1 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);

    alter table `anonymous` 
       add constraint FK_6lnbc6fo3om54vugoh8icg78m 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);

    alter table `application` 
       add constraint `FKoa6p4s2oyy7tf80xwc4r04vh6` 
       foreign key (`job_id`) 
       references `job` (`id`);

    alter table `application` 
       add constraint `FKmbjdoxi3o93agxosoate4sxbt` 
       foreign key (`worker_id`) 
       references `worker` (`id`);

    alter table `audit` 
       add constraint `FK7x4vmrfrh2nyj9mwha7np1ab4` 
       foreign key (`auditor_id`) 
       references `auditor` (`id`);

    alter table `audit` 
       add constraint `FKijp0sxquetnc9erybuvwrg2e4` 
       foreign key (`job_id`) 
       references `job` (`id`);

    alter table `auditor` 
       add constraint FK_clqcq9lyspxdxcp6o4f3vkelj 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);

    alter table `auditor_rol_request` 
       add constraint `FKte3wl47eegqj91ujx5w5g4vl` 
       foreign key (`user_id`) 
       references `user_account` (`id`);

    alter table `authenticated` 
       add constraint FK_h52w0f3wjoi68b63wv9vwon57 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);

    alter table `commercial_banner` 
       add constraint FK_q9id3wc65gg49afc5tlr1c00n 
       foreign key (`sponsor_id`) 
       references `sponsor` (`id`);

    alter table `consumer` 
       add constraint FK_6cyha9f1wpj0dpbxrrjddrqed 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);

    alter table `duty` 
       add constraint `FKs2uoxh4i5ya8ptyefae60iao1` 
       foreign key (`job_id`) 
       references `job` (`id`);

    alter table `employer` 
       add constraint FK_na4dfobmeuxkwf6p75abmb2tr 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);

    alter table `job` 
       add constraint `FK3rxjf8uh6fh2u990pe8i2at0e` 
       foreign key (`employer_id`) 
       references `employer` (`id`);

    alter table `message` 
       add constraint `FKe1edpykjs39o98sfkjafa0dtn` 
       foreign key (`author_id`) 
       references `authenticated` (`id`);

    alter table `messagethread_message` 
       add constraint `FK4v12fabi148oatb5ldiqhq6k5` 
       foreign key (`messages_id`) 
       references `message` (`id`);

    alter table `messagethread_message` 
       add constraint `FKu9f1tx0chqv0svcgpexg0hbn` 
       foreign key (`messagethread_id`) 
       references `messagethread` (`id`);

    alter table `non_commercial_banner` 
       add constraint FK_2l8gpcwh19e7jj513or4r9dvb 
       foreign key (`sponsor_id`) 
       references `sponsor` (`id`);

    alter table `provider` 
       add constraint FK_b1gwnjqm6ggy9yuiqm0o4rlmd 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);

    alter table `sponsor` 
       add constraint FK_20xk0ev32hlg96kqynl6laie2 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);

    alter table `userthread` 
       add constraint `FKg6g6iilyp0nbcmtyuhh9iveor` 
       foreign key (`authenticated_id`) 
       references `authenticated` (`id`);

    alter table `userthread` 
       add constraint `FKmctquc72kciwec2m7b0mte2t2` 
       foreign key (`thread_id`) 
       references `messagethread` (`id`);
       
    alter table `worker` 
       add constraint FK_l5q1f33vs2drypmbdhpdgwfv3 
       foreign key (`user_account_id`) 
       references `user_account` (`id`);
