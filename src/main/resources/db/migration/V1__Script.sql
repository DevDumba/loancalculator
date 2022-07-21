create table result (
	id serial not null,
	totalpayments decimal(9,2) not null,
	totalinterest decimal(9,2) not null,
	primary key (id)
);

create table request (
	id serial not null,
	loanamount decimal(9,2) not null,
	interestrate integer not null,
	numofpayments integer not null,
	paymentfreq integer not null,
	idresult integer not null,
	primary key (id),
	constraint fk_result_idrequest foreign key (idresult)
	references result (id)
);

create table schedule (
	id serial not null,
	number integer not null,
	paymentamount decimal(9,2) not null,
	principalamount decimal(9,2) not null,
	interestamount decimal(9,2) not null,
	balanceowed decimal(9,2) not null,
	idresult integer not null,
	primary key (id),
	constraint fk_schedule_idresult foreign key (idresult)
	references result (id) match simple
	on update no action
	on delete no action
);