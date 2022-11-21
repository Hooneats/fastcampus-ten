show databases;

create database board;

create user 'hooneats'@'localhost' identified by 'hooneats';

select `user` from `mysql`.`user`;

show grants for 'hooneats'@'localhost';
# USAGE 만 있다는것은 아무런 권한이 없다는 것

grant all on `board`.* to 'hooneats'@'localhost' with grant option;
# with grant option 을 함께 주어 grant 명령어 또한 사용할 수 있도록

flush privileges;

# if docker, you use option 'localhost' ==> '172.17.0.1'