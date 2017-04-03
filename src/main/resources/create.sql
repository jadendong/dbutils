create table account(
	       id integer primary key,
	       name varchar2(20),
	       balance number(10,2),
	       lastdate date
);
	
create sequence account_seq;
	
insert into account values(account_seq.nextval,'小明','1897.56',sysdate);
insert into account values(account_seq.nextval,'小张','1347.60',sysdate);
insert into account values(account_seq.nextval,'小王','200',sysdate);
insert into account values(account_seq.nextval,'小李','1000',sysdate);
insert into account values(account_seq.nextval,'小黑','0.56',sysdate);
	
commit;