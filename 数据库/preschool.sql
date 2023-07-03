/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 8.0.25 : Database - preschool
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`preschool` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `preschool`;

/*Table structure for table `column_self` */

DROP TABLE IF EXISTS `column_self`;

CREATE TABLE `column_self` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '专栏id',
  `name` varchar(100) DEFAULT NULL COMMENT '专栏名字',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  KEY `user_id_column_fk` (`user_id`),
  CONSTRAINT `user_id_column_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `column_self` */

insert  into `column_self`(`id`,`name`,`user_id`) values 
(1,'测试',7);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `userIdFrom` int NOT NULL COMMENT '谁评论',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发表时间',
  `discussPostId` int DEFAULT NULL COMMENT '评论的博客的id',
  PRIMARY KEY (`id`),
  KEY `discuss_post_id_fk` (`discussPostId`),
  CONSTRAINT `discuss_post_id_fk` FOREIGN KEY (`discussPostId`) REFERENCES `discuss_post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `comment` */

insert  into `comment`(`id`,`userIdFrom`,`content`,`create_time`,`discussPostId`) values 
(1,2,'测试评论','2021-11-27 10:05:53',2),
(2,3,'测试评论二','2021-11-27 10:06:10',3),
(3,7,'还可以噢','2021-12-18 23:19:17',29);

/*Table structure for table `comment_reply` */

DROP TABLE IF EXISTS `comment_reply`;

CREATE TABLE `comment_reply` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论的回复的id',
  `user_id_from` int DEFAULT NULL COMMENT '谁回复',
  `user_id_to` int DEFAULT NULL COMMENT '回复谁',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '回复的内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '回复时间',
  `comment_id` int DEFAULT NULL COMMENT '所属评论的id',
  PRIMARY KEY (`id`),
  KEY `user_id_fk1` (`user_id_from`),
  KEY `user_id_fk2` (`user_id_to`),
  KEY `comment_id_fk` (`comment_id`),
  CONSTRAINT `comment_id_fk` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `user_id_fk1` FOREIGN KEY (`user_id_from`) REFERENCES `user` (`id`),
  CONSTRAINT `user_id_fk2` FOREIGN KEY (`user_id_to`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `comment_reply` */

insert  into `comment_reply`(`id`,`user_id_from`,`user_id_to`,`content`,`create_time`,`comment_id`) values 
(1,3,1,'哈哈哈','2021-12-07 22:08:57',1),
(2,1,2,'啊哈哈哈哈\r\n','2021-12-07 22:09:16',2),
(3,7,7,'评论的评论','2021-12-17 19:06:58',3);

/*Table structure for table `discuss_post` */

DROP TABLE IF EXISTS `discuss_post`;

CREATE TABLE `discuss_post` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '发表该博客的用户id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `type` int DEFAULT '0' COMMENT '0-普通，1-置顶',
  `status` int DEFAULT '0' COMMENT '0-正常，1-精华，2-拉黑',
  `comment_count` int DEFAULT NULL COMMENT '评论数量',
  `up_count` int DEFAULT NULL COMMENT '点赞数量',
  `btype` int DEFAULT NULL COMMENT '0-笔记，1-踩坑记录，2-提问',
  `subject` int DEFAULT NULL COMMENT '学科类型id',
  `column_id` int DEFAULT NULL COMMENT '所对应的专栏id，非笔记类默认值为0，没有专栏为-1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `identify` int DEFAULT '1' COMMENT '0-审核通过，1-审核未通过',
  `url` varchar(200) DEFAULT NULL COMMENT '该博文的链接，方便后台核查举报信息',
  `isFree` int DEFAULT NULL COMMENT '1-免费，0-付费',
  `price` int DEFAULT NULL COMMENT '需要的cc币数量',
  PRIMARY KEY (`id`),
  KEY `user_id_fk` (`user_id`),
  KEY `subject_fk` (`subject`),
  CONSTRAINT `subject_fk` FOREIGN KEY (`subject`) REFERENCES `subject` (`id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3;

/*Data for the table `discuss_post` */

insert  into `discuss_post`(`id`,`user_id`,`title`,`content`,`type`,`status`,`comment_count`,`up_count`,`btype`,`subject`,`column_id`,`create_time`,`update_time`,`identify`,`url`,`isFree`,`price`) values 
(2,2,'2222','2222',1,0,0,1,0,1,0,'2021-11-23 22:28:08','2021-11-23 22:28:08',0,NULL,NULL,NULL),
(3,3,'测试','我写的文章好啊',0,0,0,0,0,1,0,'2021-11-23 22:28:23','2021-11-23 22:28:23',0,NULL,NULL,NULL),
(4,3,'efs','fsrfrf',1,0,0,0,0,1,0,'2021-12-26 15:36:28','2021-12-29 15:36:30',1,NULL,NULL,NULL),
(5,3,'gg','gfthtyj',1,0,0,0,0,2,0,'2021-12-18 15:40:56','2021-12-25 15:40:58',1,NULL,NULL,NULL),
(6,3,'tr','ttttttttttttt',1,0,0,0,0,2,0,'2021-12-26 15:46:51','2021-12-27 15:46:53',1,NULL,NULL,NULL),
(7,3,'trt','1111111',1,0,0,0,0,3,0,'2021-12-30 15:52:38','2021-12-31 15:52:41',1,NULL,NULL,NULL),
(8,3,'55','11111111111',1,0,0,0,0,4,0,'2021-12-31 15:56:51','2021-12-31 15:56:54',1,NULL,NULL,NULL),
(9,4,'5','1111111111111111111111111',1,0,0,0,0,4,0,'2021-12-01 16:00:51','2021-12-03 16:00:55',1,NULL,NULL,NULL),
(10,4,'yyy','3333333333333',1,0,0,0,0,4,0,'2021-12-02 16:07:32','2021-12-02 16:07:34',1,NULL,NULL,NULL),
(11,4,'5','66666666666666666',1,0,0,0,0,5,0,'2021-12-02 16:08:43','2021-12-02 16:08:45',1,NULL,NULL,NULL),
(12,4,'6565','786888888888888888888888',1,0,0,0,0,6,0,'2021-12-02 16:10:40','2021-12-02 16:10:42',1,NULL,NULL,NULL),
(14,2,'11111','666666',1,0,0,0,0,8,0,'2021-12-02 16:15:32','2021-12-02 16:15:35',1,NULL,NULL,NULL),
(15,2,'1','00000',1,0,0,0,0,9,0,'2021-12-02 16:17:07','2021-12-02 16:17:09',1,NULL,NULL,NULL),
(16,2,'1111','111111111111111111111111111111111111',1,0,0,0,0,10,0,'2021-12-02 16:26:36','2021-12-02 16:26:37',1,NULL,NULL,NULL),
(17,2,'1111','11111111111111111111111111',1,0,0,0,0,10,0,'2021-12-02 16:27:49','2021-12-02 16:27:51',1,NULL,NULL,NULL),
(18,2,'111','11111111111111111',1,0,0,0,0,10,0,'2021-12-02 16:30:34','2021-12-02 16:30:36',1,NULL,NULL,NULL),
(22,2,'11','0',1,0,0,0,0,12,0,'2021-12-02 16:54:17','2021-12-02 16:54:19',1,NULL,NULL,NULL),
(24,4,'0000','000000000000000000',1,0,0,0,0,5,0,'2021-12-04 22:32:13','2021-12-04 22:32:13',0,NULL,0,0),
(25,4,'function和function()区别','1.function()代表的是被执行后函数返回的值。\r\n2.function代表的是函数自身，一般被作为参数传递给另一个函数。',1,0,0,0,0,5,0,'2021-12-04 23:45:59','2021-12-04 23:46:01',1,NULL,NULL,NULL),
(26,4,'111111','888888888',1,0,0,0,0,5,0,'2021-12-05 20:11:13','2021-12-05 20:11:15',1,NULL,NULL,NULL),
(27,4,'11111','333333',0,0,NULL,NULL,NULL,NULL,0,'2021-12-05 20:09:25','2021-12-05 20:09:25',1,NULL,NULL,NULL),
(28,8,'99999','99999999',1,0,0,0,0,6,0,'2021-10-05 21:16:52','2021-09-23 21:16:59',0,NULL,0,0),
(29,1,'感受一下','# 感觉有点奇怪诶\n## 哈哈哈\n### 啊啊啊啊啊啊啊啊啊啊啊',0,0,2,0,0,1,NULL,'2021-12-15 21:58:43','2021-12-15 21:58:43',0,NULL,1,0),
(31,7,'彭欢彭欢彭欢','# 彭欢好棒好棒\n## 哈哈哈哈哈\n### 啊啊啊啊',0,0,0,1,0,1,NULL,'2021-12-17 19:17:32','2021-12-17 19:17:32',0,NULL,0,30);

/*Table structure for table `email_verification` */

DROP TABLE IF EXISTS `email_verification`;

CREATE TABLE `email_verification` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `email` varchar(100) DEFAULT NULL COMMENT '用户email',
  `code` varchar(20) DEFAULT NULL COMMENT '验证码',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '验证码获得的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

/*Data for the table `email_verification` */

insert  into `email_verification`(`id`,`email`,`code`,`createTime`) values 
(3,'2638826623@qq.com','52982021','2021-12-14 13:55:52');

/*Table structure for table `favorite_blog` */

DROP TABLE IF EXISTS `favorite_blog`;

CREATE TABLE `favorite_blog` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '收藏的博文表的主键',
  `discussPostId` int DEFAULT NULL COMMENT '收藏的博文ID',
  `userId` int DEFAULT NULL COMMENT '用户Id',
  PRIMARY KEY (`id`),
  KEY `favorite_blog_discuss_post_fk` (`discussPostId`),
  KEY `favorite_blog_user_fk` (`userId`),
  CONSTRAINT `favorite_blog_discuss_post_fk` FOREIGN KEY (`discussPostId`) REFERENCES `discuss_post` (`id`),
  CONSTRAINT `favorite_blog_user_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

/*Data for the table `favorite_blog` */

insert  into `favorite_blog`(`id`,`discussPostId`,`userId`) values 
(1,2,2),
(2,3,3),
(3,2,1),
(4,2,3),
(5,2,4),
(6,3,5),
(7,3,6),
(8,2,8);

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '反馈信息ID',
  `userId` int DEFAULT NULL COMMENT '反馈者ID',
  `feedbackFromContent` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '反馈内容(用户->后台)',
  `feedbackToContent` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '回馈内容(后台->用户)',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '反馈时间',
  `state` int DEFAULT '0' COMMENT '状态（未读，已处理，删除三种状态）',
  PRIMARY KEY (`id`),
  KEY `feedback_user_fk` (`userId`),
  CONSTRAINT `feedback_user_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

/*Data for the table `feedback` */

insert  into `feedback`(`id`,`userId`,`feedbackFromContent`,`feedbackToContent`,`createTime`,`state`) values 
(1,2,'请求快点开工，早点合并',NULL,'2021-11-29 01:44:06',0),
(2,3,'什么时候藏家',NULL,'2021-11-29 01:44:56',0),
(3,4,'可以不要这么冷漠嘛呜呜呜',NULL,'2021-11-29 01:46:11',1),
(9,3,'内容侵权','测试测试一下','2021-11-29 21:20:25',1);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单Id',
  `userId` int DEFAULT NULL COMMENT '订单所有者的Id',
  `discussPostId` int DEFAULT NULL COMMENT '订单中所购买的博文的Id',
  `price` int DEFAULT NULL COMMENT '所花费的CC币数量',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单新增的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

/*Data for the table `orders` */

insert  into `orders`(`id`,`userId`,`discussPostId`,`price`,`createTime`) values 
(1,7,2,0,'2021-12-17 19:04:47');

/*Table structure for table `reading_num` */

DROP TABLE IF EXISTS `reading_num`;

CREATE TABLE `reading_num` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '阅读量表主键Id',
  `userId` int DEFAULT NULL COMMENT '用户Id',
  `num` int DEFAULT NULL COMMENT '阅读量',
  PRIMARY KEY (`id`),
  KEY `ReadingNumUser` (`userId`),
  CONSTRAINT `ReadingNumUser` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

/*Data for the table `reading_num` */

/*Table structure for table `reporting_information` */

DROP TABLE IF EXISTS `reporting_information`;

CREATE TABLE `reporting_information` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '举报信息ID',
  `reportIdFrom` int DEFAULT NULL COMMENT '举报人的ID',
  `reportIdTo` int DEFAULT NULL COMMENT '被举报的博文ID',
  `reportReason` varchar(200) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '举报原因',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '举报时间',
  `reportInformation` varchar(2000) CHARACTER SET utf8 COLLATE utf8_german2_ci DEFAULT NULL COMMENT '举报的补充信息',
  `processStatus` int DEFAULT '0' COMMENT '是否处理，1-已处理，0-未处理',
  PRIMARY KEY (`id`),
  KEY `user_id_reportIdFrom_fk` (`reportIdFrom`),
  KEY `discuss_post_reportIdTofk` (`reportIdTo`),
  CONSTRAINT `discuss_post_reportIdTofk` FOREIGN KEY (`reportIdTo`) REFERENCES `discuss_post` (`id`),
  CONSTRAINT `user_id_reportIdFrom_fk` FOREIGN KEY (`reportIdFrom`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_german2_ci;

/*Data for the table `reporting_information` */

insert  into `reporting_information`(`id`,`reportIdFrom`,`reportIdTo`,`reportReason`,`createTime`,`reportInformation`,`processStatus`) values 
(1,2,2,'内容抄袭','2021-11-28 20:42:02','该博文内容和a碟的博客严重相似，请求处理',0),
(2,3,2,'内容侵权','2021-11-28 20:42:45','该博文严重侵权，快快处理',1);

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
  `id` int NOT NULL COMMENT '学科类型id',
  `name` varchar(100) NOT NULL COMMENT '学科名称',
  `category` int DEFAULT NULL COMMENT '所属大类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `subject` */

insert  into `subject`(`id`,`name`,`category`) values 
(1,'高等数学',1),
(2,'线性代数',1),
(3,'离散数学',1),
(4,'C语言',1),
(5,'大学物理',5),
(6,'电路分析',5),
(8,'概率论',1),
(9,'Java语言',1),
(10,'微型计算机原理',1),
(12,'计算机组成原理',1),
(13,'操作系统',1),
(14,'模拟电子技术',5),
(15,'数字电子技术',5),
(16,'计算机网络',1),
(17,'微型计算机原理',1),
(18,'Mysql',2),
(19,'JavaWeb',2),
(20,'算法',1),
(21,'Linux',1),
(23,'ES6',3),
(24,'axios',3),
(25,'vue',3),
(26,'react',3),
(27,'element-ui',3),
(28,'ssm框架',4),
(29,'springboot',4),
(30,'springcloud',4),
(31,'mybatis-plus',4),
(32,'redis',4),
(33,'Kafka',4),
(34,'RabbitMQ',4),
(35,'单片机',5),
(36,'嵌入式',5),
(37,'通信原理',5),
(38,'网络安全',7),
(40,'人工智能',6),
(41,'大数据',6);

/*Table structure for table `support_blog` */

DROP TABLE IF EXISTS `support_blog`;

CREATE TABLE `support_blog` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '点赞的博文表的主键',
  `discussPostId` int DEFAULT NULL COMMENT '点赞的博文ID',
  `userId` int DEFAULT NULL COMMENT '用户Id',
  PRIMARY KEY (`id`),
  KEY `support_blog_discuss_post_fk` (`discussPostId`),
  KEY `support_blog_user_fk` (`userId`),
  CONSTRAINT `support_blog_discuss_post_fk` FOREIGN KEY (`discussPostId`) REFERENCES `discuss_post` (`id`),
  CONSTRAINT `support_blog_user_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;

/*Data for the table `support_blog` */

insert  into `support_blog`(`id`,`discussPostId`,`userId`) values 
(1,2,1),
(2,3,2),
(3,2,3),
(4,3,4),
(5,2,5),
(6,3,6),
(7,2,8),
(8,3,1),
(9,2,1),
(10,3,2),
(11,2,2),
(13,31,7);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `username` varchar(50) NOT NULL COMMENT '用户昵称',
  `type` int NOT NULL COMMENT '用户类型，0-普通用户; 1-管理员;',
  `birthday` varchar(100) DEFAULT NULL COMMENT '用户生日',
  `phonenumber` varchar(50) DEFAULT NULL COMMENT '用户手机号',
  `sex` int DEFAULT NULL COMMENT '用户性别，0-男，1-女',
  `signature` varchar(100) DEFAULT NULL COMMENT '个性签名',
  `portrait_id` int DEFAULT NULL COMMENT '用户头像id',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户注册时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户更新时间',
  `status` int NOT NULL COMMENT '用户账号状态，0-正常，1-冻结',
  `place` varchar(50) DEFAULT NULL COMMENT '用户所在地',
  `ccNum` int DEFAULT '0' COMMENT 'cc币数量',
  `url` varchar(500) DEFAULT 'http://r3zvcgduw.hn-bkt.clouddn.com/%298F9D~LDO7VTV%5D_%40U%60D%5BGK5.png' COMMENT '用户头像',
  PRIMARY KEY (`id`),
  KEY `portrait_id_fk` (`portrait_id`),
  CONSTRAINT `portrait_id_fk` FOREIGN KEY (`portrait_id`) REFERENCES `user_portrait` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=985669638 DEFAULT CHARSET=utf8mb3;

/*Data for the table `user` */

insert  into `user`(`id`,`password`,`email`,`username`,`type`,`birthday`,`phonenumber`,`sex`,`signature`,`portrait_id`,`createTime`,`updateTime`,`status`,`place`,`ccNum`,`url`) values 
(1,'EA983C0D57E04E8D214F63E1228CBF15',NULL,'e碟',0,NULL,'201901020241',0,NULL,0,'2021-12-15 22:01:36','2021-12-15 22:01:36',1,'湖南',0,'http://r3zvcgduw.hn-bkt.clouddn.com/%298F9D~LDO7VTV%5D_%40U%60D%5BGK5.png'),
(2,'8574C4889B90706FE1CAAEAC260B188A','993204079@qq.com','a碟',0,NULL,'33333',0,NULL,0,'2021-12-09 22:42:41','2021-12-09 22:42:41',0,'湖南',0,'http://r3zvcgduw.hn-bkt.clouddn.com/%298F9D~LDO7VTV%5D_%40U%60D%5BGK5.png'),
(3,'8574C4889B90706FE1CAAEAC260B188A','2098656227@qq.com','b碟',0,NULL,'22222',0,NULL,0,'2021-12-09 22:42:48','2021-12-09 22:42:48',1,'浙江',1,'http://r3zvcgduw.hn-bkt.clouddn.com/%298F9D~LDO7VTV%5D_%40U%60D%5BGK5.png'),
(4,'8574C4889B90706FE1CAAEAC260B188A','2638826623@qq.com','hh',0,NULL,'11111',0,NULL,0,'2021-12-15 22:01:39','2021-12-15 22:01:39',1,'浙江',0,'http://r3zvcgduw.hn-bkt.clouddn.com/%298F9D~LDO7VTV%5D_%40U%60D%5BGK5.png'),
(5,'F59BD65F7EDAFB087A81D4DCA06C4910',NULL,'123456',0,NULL,'123456',0,NULL,0,'2021-12-15 21:58:08','2021-12-15 21:58:08',0,'广东',0,'http://r3zvcgduw.hn-bkt.clouddn.com/%298F9D~LDO7VTV%5D_%40U%60D%5BGK5.png'),
(6,'6456C7FAC445F3C1C3E7F4405159D80E',NULL,'00',0,NULL,'000000',0,NULL,0,'2021-12-15 22:01:41','2021-12-15 22:01:41',1,'北京',0,'http://r3zvcgduw.hn-bkt.clouddn.com/%298F9D~LDO7VTV%5D_%40U%60D%5BGK5.png'),
(7,'8574C4889B90706FE1CAAEAC260B188A','993204079@qq.com','a碟',0,'','13975580513',0,'123456',0,'2021-12-18 16:55:57','2021-12-18 16:55:57',1,'上海',2,'http://r3zvcgduw.hn-bkt.clouddn.com/8ee2a6f5-4895-4ccf-81ef-add7b9503d44'),
(8,'950A4152C2B4AA3AD78BDD6B366CC179','1532743414@qq.com','cc',0,NULL,'312312312',0,NULL,0,'2021-12-14 02:09:47','2021-12-14 02:09:47',0,'浙江',0,'http://r3zvcgduw.hn-bkt.clouddn.com/%298F9D~LDO7VTV%5D_%40U%60D%5BGK5.png');

/*Table structure for table `user_portrait` */

DROP TABLE IF EXISTS `user_portrait`;

CREATE TABLE `user_portrait` (
  `id` int NOT NULL COMMENT '头像id，使用java UUid获得',
  `url` varchar(200) NOT NULL COMMENT '头像图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `user_portrait` */

insert  into `user_portrait`(`id`,`url`) values 
(0,'test');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
