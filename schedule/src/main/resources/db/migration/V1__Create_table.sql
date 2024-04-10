DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ユーザID',
  mail varchar(255) NOT NULL UNIQUE COMMENT 'メールアドレス',
  password varchar(255) NOT NULL COMMENT 'パスワード',
  name varchar(255) NOT NULL COMMENT '社員名',
  department_id int(11) NOT NULL COMMENT '部門ID',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最終更新時刻',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '登録日'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ユーザ';

DROP TABLE IF EXISTS departments;
CREATE TABLE departments (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '部署ID',
  depart_name varchar(255) NOT NULL COMMENT '部署名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部署';

DROP TABLE IF EXISTS events;
CREATE TABLE events (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'イベントID',
  user_id int(11) NOT NULL COMMENT 'ユーザID',
  event_date date NOT NULL COMMENT 'イベント年月日',
  event_allday int(1) NOT NULL COMMENT '終日イベントのフラグ',
  event_start_time time NOT NULL DEFAULT '00:00:00' COMMENT '開始時刻',
  event_end_time time NOT NULL DEFAULT '23:59:59' COMMENT '終了時刻',
  event_name varchar(255) NOT NULL COMMENT 'イベント名',
  memo text NOT NULL COMMENT 'メモ',
  registered_user_id int(11) NOT NULL COMMENT '登録ユーザID',
  is_delete int(1) NOT NULL DEFAULT 0 COMMENT '削除フラグ',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最終更新時刻',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '登録日'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='イベント';
