insert into member(member_id, userid, password, phone, nickname, address, role)
values(4, "ow@naver.com", "12341234", "01023456789", "owner", "서울시 강남구", "OWNER");

insert into member(userid, password, phone, nickname, address, role)
values("jeon@naver.com", "1234000", "01012342345", "jw", "서울시 구로구", "CUSTOMER");

insert into member(userid, password, phone, nickname, address, role)
values("roskvkf@naver.com", "1234000", "01037286657", "jjw", "서울시 구로구", "CUSTOMER");

insert into store(name, address, content, phone, min_delivery_price, delivery_tip, min_delivery_time, max_delivery_time, member_id)
values("BBQ 강남역점", "서울시 강남구", "bbq 강남역점입니다.", "021231234", 20000, 2000, 10, 30, 1);

insert into store(name, address, content, phone, min_delivery_price, delivery_tip, min_delivery_time, max_delivery_time, member_id)
values("버거킹 강남역점", "서울시 강서구", "버거킹 강남역점입니다.", "0267345567", 18000, 1000, 20, 40, 1);

insert into menu(category, name, explanation, price, store_id)
values("POPULAR", "황금올리브 치킨", "bbq 대표 치킨입니다", 20000, 1);

insert into menu(category, name, explanation, price, store_id)
values("NEW", "bbq 양념치킨", "bbq 대표 양념치킨!", 22000, 2);

insert into menu(category, name, explanation, price, store_id)
values("NEW", "반반 치킨", "bbq 반반 치킨!", 23000, 2);


