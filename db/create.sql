create table category
(
  id   int auto_increment
    primary key,
  name varchar(255) null,
  constraint category_name_uindex
  unique (name)
)engine = InnoDB default charset = utf8;

create table product
(
  id            int auto_increment
    primary key,
  name          varchar(255) null,
  title         varchar(255) null,
  originalPrice float        null
  comment '原始价格',
  promotePrice  float        null
  comment '折扣价格',
  stock         int          null
  comment '库存',
  createDate    timestamp    null
  comment '创建时间',
  cid           int          null,
  constraint fk_product_category
  foreign key (cid) references category (id)
)engine = InnoDB default charset = utf8;

create table picture
(
  id   int auto_increment
    primary key,
  type varchar(255) null,
  pid  int          null,
  constraint fk_picture_product
  foreign key (pid) references product (id)
)engine = InnoDB default charset = utf8;

create table property
(
  id   int auto_increment
    primary key,
  cid  int          null,
  name varchar(255) null,
  constraint fk_property_category
  foreign key (cid) references category (id)
)engine = InnoDB default charset = utf8;

create table propertyvalue
(
  id    int auto_increment
    primary key,
  value varchar(255) null,
  pdid  int          null,
  ppid  int          null,
  constraint fk_propertyvalue_product
  foreign key (pdid) references product (id),
  constraint fk_propervalue_property
  foreign key (ppid) references property (id)
)engine = InnoDB default charset = utf8;

create table t_comment
(
  id         int auto_increment
    primary key,
  pid        int           null,
  content    varchar(4000) null,
  createTime timestamp     null,
  uid        int           null,
  constraint fk_comment_product
  foreign key (pid) references product (id)
)engine = InnoDB default charset = utf8;

create index comment_t_user_id_fk
  on t_comment (uid);

create table t_my_user
(
  id       int auto_increment
    primary key,
  name     varchar(255) null,
  password varchar(255) null,
  constraint t_my_user_name_uindex
  unique (name)
)engine = InnoDB default charset = utf8;

create table t_order
(
  id           int auto_increment
    primary key,
  ordercode    varchar(50)  null
  comment '订单号',
  address      varchar(255) null,
  post         varchar(255) null
  comment '邮编',
  receiver     varchar(255) null
  comment '收件人信息',
  mobile       int          null,
  message      varchar(255) null
  comment '用户备注信息',
  createDate   timestamp    null,
  payDate      timestamp    null,
  deliveryDate timestamp    null
  comment '发货日期',
  confirmDate  timestamp    null
  comment '收获日期',
  status       varchar(255) null
  comment '订单状态',
  uid          int          null
)engine = InnoDB default charset = utf8;

create table orderitem
(
  id     int auto_increment
    primary key,
  number int null,
  pid    int null,
  oid    int null,
  uid    int null,
  constraint fk_orderitem_order
  foreign key (oid) references t_order (id),
  constraint fk_orderitem_product
  foreign key (pid) references product (id),
  constraint fk_orderitem_user
  foreign key (uid) references t_my_user (id)
)engine = InnoDB default charset = utf8;

create index t_order_t_user_id_fk
  on t_order (uid);