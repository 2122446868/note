# MySQL高级

mysql版本：5.5.48

查看字符集

```mysql
show variables like 'character%';
or
show variables like '%char%';
```

## 1、MySQL架构介绍

### 1.1MySQL安装

#### 1.1.1 准备环境包

下载地址：[尚硅谷网盘/MySQL高级/资料](https://pan.baidu.com/s/1Kg7UUpO3wwALX6x28cWA7A#list/path=%2F%E5%B0%9A%E7%A1%85%E8%B0%B7Java%E5%AD%A6%E7%A7%91%E5%85%A8%E5%A5%97%E6%95%99%E7%A8%8B%EF%BC%88%E6%80%BB207.77GB%EF%BC%89%2F3.%E5%B0%9A%E7%A1%85%E8%B0%B7%E5%85%A8%E5%A5%97JAVA%E6%95%99%E7%A8%8B--%E5%BE%AE%E6%9C%8D%E5%8A%A1%E7%94%9F%E6%80%81%EF%BC%8866.68GB%EF%BC%89%2F%E5%B0%9A%E7%A1%85%E8%B0%B7MySQL%E9%AB%98%E7%BA%A7)

MySQL-client-5.5.48-1.linux2.6.i386.rpm

MySQL-server-5.5.48-1.linux2.6.i386.rpm

#### 1.1.2 查看Linux服务器上是否安装过MySQL

```shell
#查询出所有mysql依赖包
rpm -qa | grep -i mysql 
#卸载mysql
yum remove mysql-community-server-5.6.36-2.el7.x86_64
#卸载依赖
yum remove mysql-libs
yum remove mysql-server
yum remove perl-DBD-MySQL
yum remove mysql

```
#### 1.1.3安装MySQL

##### 1.1.3.1正确安装

```shell
#1.安装MySQL-server-5.5.48-1.linux2.6.i386.rpm
sudo rpm -ivh MySQL-server-5.5.48-1.linux2.6.i386.rpm --force --nodeps

#成功信息
[zcc@iZbp1f5ieky9rn5jhzon9dZ ~]$ sudo rpm -ivh MySQL-server-5.5.48-1.linux2.6.i386.rpm --force --nodeps
[sudo] password for zcc:
warning: MySQL-server-5.5.48-1.linux2.6.i386.rpm: Header V3 DSA/SHA1 Signature, key ID 5072e1f5: NOKEY
Verifying...                          ################################# [100%]
Preparing...                          ################################# [100%]
Updating / installing...
   1:MySQL-server-5.5.48-1.linux2.6   ################################# [100%]
/var/tmp/rpm-tmp.18p3jB: line 10: /usr/bin/my_print_defaults: No such file or directory
/usr/bin/mysql_install_db: line 249: /usr/bin/my_print_defaults: No such file or directory
/usr/bin/mysql_install_db: line 403: /usr/sbin/mysqld: No such file or directory
cat: write error: Broken pipe
cat: write error: Broken pipe

Installation of system tables failed!  Examine the logs in
/var/lib/mysql for more information.

You can try to start the mysqld daemon with:

    shell> /usr/sbin/mysqld --skip-grant &

and use the command line tool /usr/bin/mysql
to connect to the mysql database and look at the grant tables:

    shell> /usr/bin/mysql -u root mysql
    mysql> show tables

Try 'mysqld --help' if you have problems with paths.  Using --log
gives you a log in /var/lib/mysql that may be helpful.

Please consult the MySQL manual section
'Problems running mysql_install_db', and the manual section that
describes problems on your OS.  Another information source are the
MySQL email archives available at http://lists.mysql.com/.

Please check all of the above before submitting a bug report
at http://bugs.mysql.com/


#2.安装MySQL-server-5.5.48-1.linux2.6.i386.rpm
sudo rpm -ivh MySQL-client-5.5.48-1.linux2.6.i386.rpm --force --nodeps

#安装信息
[zcc@iZbp1f5ieky9rn5jhzon9dZ ~]$ sudo rpm -ivh MySQL-client-5.5.48-1.linux2.6.i386.rpm --force --nodeps
warning: MySQL-client-5.5.48-1.linux2.6.i386.rpm: Header V3 DSA/SHA1 Signature, key ID 5072e1f5: NOKEY
Verifying...                          ################################# [100%]
Preparing...                          ################################# [100%]
Updating / installing...
   1:MySQL-client-5.5.48-1.linux2.6   ################################# [100%]

```

##### 1.1.3.2遇到的问题

###### 1.1.3.2.1 安装命令后面缺少--force --nodeps

```shell
rpm -ivh MySQL-server-5.5.48-1.linux2.6.i386.rpm 
#报错： 需要在命令后面加上  --force --nodeps
[zcc@iZbp1f5ieky9rn5jhzon9dZ ~]$ rpm -ivh MySQL-server-5.5.48-1.linux2.6.i386.rpm
warning: MySQL-server-5.5.48-1.linux2.6.i386.rpm: Header V3 DSA/SHA1 Signature, key ID 5072e1f5: NOKEY
error: Failed dependencies:
        libaio.so.1 is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libaio.so.1(LIBAIO_0.1) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libaio.so.1(LIBAIO_0.4) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6 is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6(GLIBC_2.0) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6(GLIBC_2.1) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6(GLIBC_2.1.2) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6(GLIBC_2.1.3) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6(GLIBC_2.2) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6(GLIBC_2.2.3) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6(GLIBC_2.3) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libc.so.6(GLIBC_2.3.3) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libcrypt.so.1 is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libcrypt.so.1(GLIBC_2.0) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libdl.so.2 is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libdl.so.2(GLIBC_2.0) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libdl.so.2(GLIBC_2.1) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libm.so.6 is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libm.so.6(GLIBC_2.0) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libm.so.6(GLIBC_2.1) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libpthread.so.0 is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libpthread.so.0(GLIBC_2.0) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libpthread.so.0(GLIBC_2.1) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libpthread.so.0(GLIBC_2.2) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        libpthread.so.0(GLIBC_2.3.2) is needed by MySQL-server-5.5.48-1.linux2.6.i386
        librt.so.1 is needed by MySQL-server-5.5.48-1.linux2.6.i386
        librt.so.1(GLIBC_2.2) is needed by MySQL-server-5.5.48-1.linux2.6.i386
[zcc@iZbp1f5ieky9rn5jhzon9dZ ~]$ rpm -ivh MySQL-server-5.5.48-1.linux2.6.i386.rpm --force --nodeps
warning: MySQL-server-5.5.48-1.linux2.6.i386.rpm: Header V3 DSA/SHA1 Signature, key ID 5072e1f5: NOKEY
error: can't create transaction lock on /var/lib/rpm/.rpm.lock (Permission denied)

```

###### 1.1.3.2.1 加上sudo报错（阿里云服务器  或者使用非管理员用户）

```shell
sudo rpm -ivh MySQL-server-5.5.48-1.linux2.6.i386.rpm --force --nodeps
#错误信息
[zcc@iZbp1f5ieky9rn5jhzon9dZ ~]$ sudo rpm -ivh MySQL-server-5.5.48-1.linux2.6.i386.rpm --force --nodeps

We trust you have received the usual lecture from the local System
Administrator. It usually boils down to these three things:

    #1) Respect the privacy of others.
    #2) Think before you type.
    #3) With great power comes great responsibility.

[sudo] password for zcc:
zcc is not in the sudoers file.  This incident will be reported.

#解决办法： 切换到管理员用户
su - root

#编辑sudoers文件
vi /etc/sudoers

#找到 
#root   ALL=(ALL)    ALL    
#在下面一行添加  我的用户是zcc
youUsername    ALL=(ALL) ALL

#保存退出
wq!
```





#### 1.2配置文件

#### 1.2.1my.cnf

```shell
# Example MySQL config file for very large systems.
#
# This is for a large system with memory of 1G-2G where the system runs mainly
# MySQL.
#
# MySQL programs look for option files in a set of
# locations which depend on the deployment platform.
# You can copy this option file to one of those
# locations. For information about these locations, see:
# http://dev.mysql.com/doc/mysql/en/option-files.html
#
# In this file, you can use all long options that a program supports.
# If you want to know which options a program supports, run the program
# with the "--help" option.

# The following options will be passed to all MySQL clients
[client]
#password	= your_password
port		= 3306
socket		= /var/lib/mysql/mysql.sock
default-character-set = utf8
# Here follows entries for some specific programs

# The MySQL server
[mysqld]
port		= 3306
character-set-server = utf8
character-set-client = utf8
collation-server=utf8_general_ci
#区分大小写 1不区分  0区分
lower_case_table_name=1
socket		= /var/lib/mysql/mysql.sock
skip-external-locking
key_buffer_size = 384M
max_allowed_packet = 1M
table_open_cache = 512
sort_buffer_size = 2M
read_buffer_size = 2M
read_rnd_buffer_size = 8M
myisam_sort_buffer_size = 64M
thread_cache_size = 8
query_cache_size = 32M
# Try number of CPU's*2 for thread_concurrency
thread_concurrency = 8

# Don't listen on a TCP/IP port at all. This can be a security enhancement,
# if all processes that need to connect to mysqld run on the same host.
# All interaction with mysqld must be made via Unix sockets or named pipes.
# Note that using this option without enabling named pipes on Windows
# (via the "enable-named-pipe" option) will render mysqld useless!
# 
#skip-networking

# Replication Master Server (default)
# binary logging is required for replication
log-bin=mysql-bin

# required unique id between 1 and 2^32 - 1
# defaults to 1 if master-host is not set
# but will not function as a master if omitted
server-id	= 1

# Replication Slave (comment out master section to use this)
#
# To configure this host as a replication slave, you can choose between
# two methods :
#
# 1) Use the CHANGE MASTER TO command (fully described in our manual) -
#    the syntax is:
#
#    CHANGE MASTER TO MASTER_HOST=<host>, MASTER_PORT=<port>,
#    MASTER_USER=<user>, MASTER_PASSWORD=<password> ;
#
#    where you replace <host>, <user>, <password> by quoted strings and
#    <port> by the master's port number (3306 by default).
#
#    Example:
#
#    CHANGE MASTER TO MASTER_HOST='125.564.12.1', MASTER_PORT=3306,
#    MASTER_USER='joe', MASTER_PASSWORD='secret';
#
# OR
#
# 2) Set the variables below. However, in case you choose this method, then
#    start replication for the first time (even unsuccessfully, for example
#    if you mistyped the password in master-password and the slave fails to
#    connect), the slave will create a master.info file, and any later
#    change in this file to the variables' values below will be ignored and
#    overridden by the content of the master.info file, unless you shutdown
#    the slave server, delete master.info and restart the slaver server.
#    For that reason, you may want to leave the lines below untouched
#    (commented) and instead use CHANGE MASTER TO (see above)
#
# required unique id between 2 and 2^32 - 1
# (and different from the master)
# defaults to 2 if master-host is set
# but will not function as a slave if omitted
#server-id       = 2
#
# The replication master for this slave - required
#master-host     =   <hostname>
#
# The username the slave will use for authentication when connecting
# to the master - required
#master-user     =   <username>
#
# The password the slave will authenticate with when connecting to
# the master - required
#master-password =   <password>
#
# The port the master is listening on.
# optional - defaults to 3306
#master-port     =  <port>
#
# binary logging - not required for slaves, but recommended
#log-bin=mysql-bin
#
# binary logging format - mixed recommended 
#binlog_format=mixed

# Uncomment the following if you are using InnoDB tables
#innodb_data_home_dir = /var/lib/mysql
#innodb_data_file_path = ibdata1:2000M;ibdata2:10M:autoextend
#innodb_log_group_home_dir = /var/lib/mysql
# You can set .._buffer_pool_size up to 50 - 80 %
# of RAM but beware of setting memory usage too high
#innodb_buffer_pool_size = 384M
#innodb_additional_mem_pool_size = 20M
# Set .._log_file_size to 25 % of buffer pool size
#innodb_log_file_size = 100M
#innodb_log_buffer_size = 8M
#innodb_flush_log_at_trx_commit = 1
#innodb_lock_wait_timeout = 50

[mysqldump]
quick
max_allowed_packet = 16M

[mysql]
no-auto-rehash
default-character-set = utf8
# Remove the next comment character if you are not familiar with SQL
#safe-updates

[myisamchk]
key_buffer_size = 256M
sort_buffer_size = 256M
read_buffer = 2M
write_buffer = 2M

[mysqlhotcopy]
interactive-timeout
```

#### 1.2.2数据文件

二进制login-bin：  主从复制

错误日志login-error：  默认关闭，记录严重的警告和错误信息，每次启动和关闭的详细信息等；

查询日志 log：默认关闭，记录查询的sql语句，如果开启会降低mysql的整体性能，因为记录日志也是需要消耗系统资源

数据文件：

位于按照目录下例如：/usr/local/mysql/data/studetn(这个是自己新建的表)

frm 文件  存放表结构

myd 文件 存放表数据

myi 文件 存放表索引



### 2、MySQL逻辑架构

和其它数据库相比, MySQL有点与众不同,它的架构可以在多种不同场景中应用并发挥良好作用。<font color="red">主要体现在存储引擎的架构上备件式的存储引擎架构将查询处理和其它的系统任务以及数据的存储提取相分离。</font>这种架构可以根据业务的需求和实际需要选择合适的存储引擎。



![img](https://img-blog.csdnimg.cn/20200914103530682.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70#pic_center)



- `Connectors`：指的是不同语言中与SQL的交互。
- `Connection Pool`：管理缓冲用户连接，线程处理等需要缓存的需求。**MySQL数据库的连接层。**
- `Management Serveices & Utilities`：系统管理和控制工具。备份、安全、复制、集群等等。。
- `SQL Interface`：接受用户的SQL命令，并且返回用户需要查询的结果。
- `Parser`：SQL语句解析器。
- `Optimizer`：查询优化器，SQL语句在查询之前会使用查询优化器对查询进行优化。**就是优化客户端请求query**，根据客户端请求的 query 语句，和数据库中的一些统计信息，在一系列算法的基础上进行分析，得出一个最优的策略，告诉后面的程序如何取得这个 query 语句的结果。**For Example**： `select uid,name from user where gender = 1;`这个`select `查询先根据`where `语句进行选取，而不是先将表全部查询出来以后再进行`gender`过滤；然后根据`uid`和`name`进行属性投影，而不是将属性全部取出以后再进行过滤。最后将这两个查询条件联接起来生成最终查询结果。
- `Caches & Buffers`：查询缓存。
- `Pluggable Storage Engines`：**存储引擎接口。MySQL区别于其他数据库的最重要的特点就是其插件式的表存储引擎(注意：存储引擎是基于表的，而不是数据库)。**
- `File System`：数据落地到磁盘上，就是文件的存储。





1、连接层

最上层是一些客户端和连接服务,包含本地sock通信和大多数基于客户端/服务端工具实现的类似于tcpl/ip的通信。主要完成一些类似于连接处理、授权认证、及相关的安全方案。在该层上引入了线程池的概念,为通过认证安全接入的客户端提供线程。同样在该层上可以实现基于SSL的安全链接。服务器也会为安全接入的每个客户端验证它所具有的操作权限。 

2、服务层

第二层架构主要完成大多少的核心服务功能,如SQL接口,并完成缓存的查询, SQL的分析和优化及部分内置函数的执行。所有跨存储引擎的功能也在这一层实现,如过程、函数等。在该层,服务器会解析查询并创建相应的内部解析树,并对其完成相应的优化如确定查询表的顺序,是否利用索引等,最后生成相应的执行操作。如果是select语句,服务器还会查询内部的缓存。如果缓存空间足够大,这样在解决大量读操作的环境中能够很好的提升系统的性能。

3、引擎层

存储引擎层,存储引擎真正的负责了MySQL中数据的存储和提取,服务器通过API与存储引擎进行通信。不同的存储引擎具有的功能不同,这样我们可以根据自己的实际需要进行选取。后面介绍MyISAM和InnoDB 

4、存储层

数据存储层,主要是将数据存储在运行于裸设备的文件系统之上,并完成与存储引擎的交互。 



### 3、MySQL存储引擎

#### 3.1查看命令

``` mysql
#查看MySQL提供的存储引擎
show engines;
# 查看mysql当前默认的存储引擎
show variables like '%storage_engine%';
```

#### 3.2MyISAM 和 InnoDB对比

| 对比项   |                            MyISAM                            |                                                       InnoDB |
| -------- | :----------------------------------------------------------: | -----------------------------------------------------------: |
| 主外键   |                            不支持                            |                                                         支持 |
| 事务     |                            不支持                            |                                                         支持 |
| 行表锁   | 表锁，即使操作一条记录也会锁住整个表，<font color="red">不适合高并发操作</font> | 行锁，操作时只锁住某一行，不对其它行有影响，<font color="red">适合高并发操作</font> |
| 缓存     |                  只缓存索引，不缓存真实数据                  | 不仅缓存索引还要缓存真实数据,对内存要求较高,而且内存大小对性能有决定性的影响 |
| 表空间   |                              小                              |                                                           大 |
| 关注点   |                             性能                             |                                                         事务 |
| 默认安装 |                              Y                               |                                                            Y |

## 4、索引优化分析

### 4.1性能下降sql慢、执行时间长、等待时间长的原因？

1. 查询语句写的烂
2. 索引失效： 单值 、复合
3. 关联查询太多join(设计缺陷或不得已的需求)
4. 服务器调优及各个参数设置（缓冲，线程数等）



### 4.2常见通用的join查询

#### 4.2.1sql执行顺序

##### 4.2.1.1手写

```mysql
select DISTINCT <查询列表> 
from
<left_table> <join_type>
join <right_table> on <join_condition>
where
	<where_condition>
group by
	<group_by_condition>
having
	<having_condition>
order by
	<order_by_condition>
limit <limit number>
```



##### 4.2.1.2机读

``` mysql
from
<left_table>
on <join_condition>
<join_type> join <right_table>
where
	<where_condition>
group by
	<group_by_condition>
having
	<having_condition>
select
DISTINCT <查询列表> 
order by
	<order_by_condition>
limit <limit number>
```

##### 4.2.1.3总结

![image-20210509114656820](C:\Users\pc\AppData\Roaming\Typora\typora-user-images\image-20210509114656820.png)



### 4.3 七中 join理论 图

![img](https://camo.githubusercontent.com/86ddd9cb519bf327ca485e638dd17d8ec15e10778a6b1d85b7c6dfa54d553df1/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303230303830313231323031313535392e6a70673f782d6f73732d70726f636573733d696d6167652f77617465726d61726b2c747970655f5a6d46755a33706f5a57356e6147567064476b2c736861646f775f31302c746578745f6148523063484d364c7939696247396e4c6d4e7a5a473475626d56304c314a796157356e6231383d2c73697a655f31362c636f6c6f725f4646464646462c745f3730)

建表sql

```mysql
 CREATE TABLE `tbl_emp`  (
 `id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(20)  DEFAULT NULL,
`deptId` int(11) DEFAULT NULL,
 PRIMARY KEY (`id`) ,
KEY `fk_dept_id`(`deptId`)
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

 CREATE TABLE `tbl_dept`  (
 `id` int(11) NOT NULL AUTO_INCREMENT,
`deptName` varchar(30) DEFAULT NULL,
 `locAdd` varchar(40) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

INSERT INTO tbl_dept ( deptName, locAdd )
VALUE( 'RD', 1 ),('HR',12 ),( 'MK', 13 ),( 'MIS', 14 ),( 'FD', 15 );
INSERT INTO tbl_emp ( `name`, deptId )
VALUES( 'z3', 1 ),( 'z4', 1 ),( 'z5', 1 ),( 'w5', 2 ),('w6',2),( 's7', 3 ),( 's8', 4 ),( 's9', 51 );
```





```mysql
select * from tbl_dept
select * from tbl_emp

-- 1查询共有部分
select * from  tbl_emp
inner join tbl_dept
on tbl_dept.id = tbl_emp.deptId

-- 2查询左表全部和共有部分   = 左表全部+左右表共有
select * from tbl_emp
left join tbl_dept
on tbl_emp.deptId = tbl_dept.id

-- 3查询右表全部和共有部分   = 右边全部+左右表共有
select * from tbl_emp
RIGHT JOIN tbl_dept
on tbl_emp.deptId = tbl_dept.id

-- 4.查询左表的独有    = 2 + 筛选去掉共有部分
select * from tbl_emp
LEFT JOIN tbl_dept
on tbl_emp.deptId = tbl_dept.id
where tbl_dept.id is null;

-- 5.查询右表独有  =  3 + 筛选去掉共有部分
select * from tbl_emp
RIGHT JOIN tbl_dept
on tbl_emp.deptId = tbl_dept.id
where tbl_emp.id  is null;

-- 6.查询俩表全部
-- ①MySQL不支持
select * from tbl_emp
FULL OUTER JOIN tbl_dept
on tbl_emp.deptId =tbl_dept.id

-- ②使用联合查询
select * from tbl_emp
UNION
select * from tbl_dept



-- 7 查询左右表全部
SELECT <select_list> FROM TableA A FULL OUTER JOIN TableB B ON A.Key = B.Key WHERE A.Key IS NULL OR B.Key IS NULL;
/* MySQL不支持FULL OUTER JOIN这种语法 可以改成 4+5 */
SELECT <select_list> FROM TableA A LEFT JOIN TableB B ON A.Key = B.Key WHERE B.Key IS NULL;
UNION
SELECT <select_list> FROM TableA A RIGHT JOIN TableB B ON A.Key = B.Key WHERE A.Key IS NULL;


```



### 4.4索引

#### 4.4.1索引简介

索引是什么？

MySQL官方对索引的定义为：**索引（INDEX）是帮助MySQL高效获取数据的数据结果。**

从而可以获得索引的本质：**索引是排好序的快速查找数据结构。**

索引的目的在于提高查询效率，可以类比字典的目录。如果要查`mysql`这个这个单词，我们肯定要先定位到`m`字母，然后从上往下找`y`字母，再找剩下的`sql`。如果没有索引，那么可能需要`a---z`，这样全字典扫描，如果我想找`Java`开头的单词呢？如果我想找`Oracle`开头的单词呢？？？

**重点：索引会影响到MySQL查找(WHERE的查询条件)和排序(ORDER BY)两大功能！**

**除了数据本身之外，数据库还维护着一个满足特定查找算法的数据结构，这些数据结构以某种方式指向数据，这样就可以在这些数据结构的基础上实现高级查找算法，这种数据结构就是索引。**

一般来说，索引本身也很大，不可能全部存储在内存中，因此索引往往以索引文件的形式存储在磁盘上。



我们平时所说的索引，如果没有特别指明，都是指B树（多路搜索树，并不一定是二叉的）结构组织的索引。其中聚集索引，次要索引，覆盖索引，复合索引，前缀索引，唯一索引默认都是使用B+树索引，统称索引。当然，除了B+树这种数据结构的索引之外，还有哈希索引（Hash Index）等。





#### 4.4.2索引的优势劣势

优势

- 查找：类似大学图书馆的书目索引，提高数据检索的效率，降低数据库的IO成本。
- 排序：通过索引対数据进行排序，降低数据排序的成本，降低了CPU的消耗。

劣势

- 实际上索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录，所以索引列也是要占用空间的。
- 虽然索引大大提高了查询速度，但是同时会降低表的更新速度，例如对表频繁的进行`INSERT`、`UPDATE`和`DELETE`。因为更新表的时候，MySQL不仅要保存数据，还要保存一下索引文件每次更新添加的索引列的字段，都会调整因为更新所带来的键值变化后的索引信息。
- 索引只是提高效率的一个因素，如果MySQL有大数据量的表，就需要花时间研究建立最优秀的索引。



#### 4.4.3 MYSQL索引的分类

##### 4.4.3.1索引的分类

- 单值索引：一个索引只包含单个列，一个表可以有多个单列索引。
- 唯一索引：索引列的值必须唯一，但是允许空值。
- 复合索引：一个索引包含多个字段。

<font color='red'>建议：一张表建的索引最好不要超过5个</font>

##### 4.4.3.2 基本语法:

```mysql


/* 1、创建索引 [UNIQUE]可以省略*/
/* 如果只写一个字段就是单值索引，写多个字段就是复合索引 */
CREATE [UNIQUE] INDEX indexName ON tabName(columnName(length));

/* 2、删除索引 */
DROP INDEX [indexName] ON tabName;

/* 3、查看索引 */
/* 加上\G就可以以列的形式查看了 不加\G就是以表的形式查看 */
SHOW INDEX FROM tabName \G;
```

使用`ALTER`命令来为数据表添加索引

```mysql
/* 1、该语句添加一个主键，这意味着索引值必须是唯一的，并且不能为NULL */
ALTER TABLE tabName ADD PRIMARY KEY(column_list);

/* 2、该语句创建索引的键值必须是唯一的(除了NULL之外，NULL可能会出现多次) */
ALTER TABLE tabName ADD UNIQUE indexName(column_list);

/* 3、该语句创建普通索引，索引值可以出现多次 */
ALTER TABLE tabName ADD INDEX indexName(column_list);

/* 4、该语句指定了索引为FULLTEXT，用于全文检索 */
ALTER TABLE tabName ADD FULLTEXT indexName(column_list);
```

 

#### 4.4.4MYSQL索引数据结构

索引数据结构：

- `BTree`索引。

- `Hash`索引。

- `Full-text`全文索引。

- `R-Tree`索引。

  BTree`索引检索原理：

 

![img](https://camo.githubusercontent.com/585e16bba5e6911f7d81728ab9731894fd6631963a028f79d6f327d50109b42d/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303230303830313233333133343933312e706e673f782d6f73732d70726f636573733d696d6167652f77617465726d61726b2c747970655f5a6d46755a33706f5a57356e6147567064476b2c736861646f775f31302c746578745f6148523063484d364c7939696247396e4c6d4e7a5a473475626d56304c314a796157356e6231383d2c73697a655f31362c636f6c6f725f4646464646462c745f3730)

#### 4.4.5哪些情况需要建立索引

- 主键自动建立主键索引（唯一 + 非空）。
- 频繁作为查询条件的字段应该创建索引。
- 查询中与其他表关联的字段，外键关系建立索引。
- 查询中排序的字段，排序字段若通过索引去访问将大大提高排序速度。
- 查询中统计或者分组字段（group by也和索引有关）。



#### 4.4.6哪些情况不需要建立索引

- 记录太少的表。
- 经常增删改的表。
- 频繁更新的字段不适合创建索引。
- Where条件里用不到的字段不创建索引。
- 假如一个表有10万行记录，有一个字段A只有true和false两种值，并且每个值的分布概率大约为50%，那么对A字段建索引一般不会提高数据库的查询速度。索引的选择性是指索引列中不同值的数目与表中记录数的比。如果一个表中有2000条记录，表索引列有1980个不同的值，那么这个索引的选择性就是1980/2000=0.99。一个索引的选择性越接近于1，这个索引的效率就越高。



### 4.5性能分析

> 是什么？

#### 4.5.1 explain 简介

EXPLAIN：SQL的执行计划，使用EXPLAIN关键字可以模拟优化器执行SQL查询语句，从而知道MySQL是如何处理SQL语句的。分析你的查询语句或者表结构的性能瓶颈。

> 能干嘛？

可以查看以下信息：

- `id`：表的读取顺序。
- `select_type`：数据读取操作的操作类型。
- `possible_keys`：哪些索引可以使用。
- `key`：哪些索引被实际使用。
- `ref`：表之间的引用。
- `rows`：每张表有多少行被优化器查询。



> 怎么玩？

explain + sql语句

```mysql
EXPLAIN SELECT * FROM tbl_dept

       id: 1
  select_type: SIMPLE
        table: pms_category
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 1425
     filtered: 100.00
        Extra: NULL
```

#### 4.5.2explain 各字段解释

- <font color="blue">**id** </font>：查询中执行select字句或操作表的顺序

  三种情况：

  id相同：执行循序由上至下

  id不同：如果是子查询，id的序号会递增，id**值越大优先级越高，越先被执行**

  id相同不同 同时存在： **永远是id大的优先级最高，id相同的时候顺序执行**

- **select_type**：数据查询的类型，主要是用于区别，普通查询、联合查询、子查询等的复杂查询。

  ​	SIMPLE：简单的SELECT查询，查询中不包含子查询或者UNION 。

  ​    PRIMARY：查询中如果包含任何复杂的子部分，最外层查询则被标记为PRIMARY。

     SUBQUERY：在SELECT或者WHERE子句中包含了子查询。
     DERIVED：在FROM子句中包含的子查询被标记为DERIVED(衍生)，MySQL会递归执行这些子查询，把结果放在临时表中。
     UNION：如果第二个SELECT出现在UNION之后，则被标记为UNION；若UNION包含在FROM子句的子查询中，外层SELECT将被标记为DERIVED。
    UNION RESULT：从UNION表获取结果的SELECT。

- **table**：表名

- <font color="blue">**type**</font>：访问类型排列

  **从最好到最差依次是：**`system`>`const`>`eq_ref`>`ref`>`range`>`index`>`ALL`。除了`ALL`没有用到索引，其他级别都用到索引了。

  <font color='red'>一般来说，得保证查询至少达到`range`级别，最好达到`ref`。</font>

  **system**：表只有一行记录（等于系统表），这是const类型的特例，平时不会出现，这个也可以忽略不计。
  **const**：表示通过索引一次就找到了，const用于比较primary key或者unique索引。因为只匹配一行数据，所以很快。如将主键置于where列表中，MySQL就能将该查询转化为一个常量。
  **eq_ref**：唯一性索引扫描，读取本表中和关联表表中的每行组合成的一行，查出来只有一条记录。除 了 system 和 const 类型之外, 这是最好的联接类型。
  **ref**：非唯一性索引扫描，返回本表和关联表某个值匹配的所有行，查出来有多条记录。
  **range**：只检索给定范围的行，一般就是在WHERE语句中出现了BETWEEN、< >、in等的查询。这种范围扫描索引比全表扫描要好，因为它只需要开始于索引树的某一点，而结束于另一点，不用扫描全部索引。
  **index：Full** Index Scan，全索引扫描，index和ALL的区别为index类型只遍历索引树。也就是说虽然ALL和index都是读全表，但是index是从索引中读的，ALL是从磁盘中读取的。
  **ALL：Full** Table Scan，没有用到索引，全表扫描。

- **possible_keys**：显示可能应用在这张表中的索引，一个或者多个。查询涉及到的字段上若存在索引，则该索引将被列出，**但不一定被查询实际使用。**

- <font color="blue">**key**</font>：实际使用的索引。如果为`NULL`，则没有使用索引。查询中如果使用了覆盖索引，则该索引仅仅出现在`key`列表中。

- **key_len**：表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。`key_len`显示的值为索引字段的最大可能长度，并非实际使用长度，即`key_len`是根据表定义计算而得，不是通过表内检索出的。在不损失精度的情况下，长度越短越好。

- **ref**：显示索引的哪一列被使用了，如果可能的话，是一个常数。哪些列或常量被用于查找索引列上的值。

- <font color="blue">**rows**</font>：根据表统计信息及索引选用情况，大致估算出找到所需的记录需要读取的行数。

- <font color="blue">**extra**</font>：包含不适合在其他列中显示但十分重要的额外信息。

  <font color="red">`Using filesort`：说明MySQL会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。**MySQL中无法利用索引完成的排序操作成为"文件内排序"。**</font>

  <font color="red">`Using temporary`：使用了临时表保存中间结果，MySQL在対查询结果排序时使用了临时表。常见于排序`order by`和分组查询`group by`。**临时表対系统性能损耗很大。**</font>

  <font color="red">`Using index`：表示相应的`SELECT`操作中使用了覆盖索引，避免访问了表的数据行，效率不错！如果同时出现`Using where`，表示索引被用来执行索引键值的查找；如果没有同时出现`Using where`，表明索引用来读取数据而非执行查找动作。</font>

  ```mysql
  # 覆盖索引
  # 就是select的数据列只用从索引中就能够取得，不必从数据表中读取，换句话说查询列要被所使用的索引覆盖。
  # 注意：如果要使用覆盖索引，一定不能写SELECT *，要写出具体的字段。
  ```

  `Using where`：表明使用了`WHERE`过滤。

  `Using join buffer`：使用了连接缓存。

  `impossible where`：`WHERE`子句的值总是false，不能用来获取任何元组。



### 4.6索引分析

#### 4.6.1单表索引分析

> 数据准备

```mysql
DROP TABLE IF EXISTS `article`;

CREATE TABLE IF NOT EXISTS `article`(
`id` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
`author_id` INT(10) UNSIGNED NOT NULL COMMENT '作者id',
`category_id` INT(10) UNSIGNED NOT NULL COMMENT '分类id',
`views` INT(10) UNSIGNED NOT NULL COMMENT '被查看的次数',
`comments` INT(10) UNSIGNED NOT NULL COMMENT '回帖的备注',
`title` VARCHAR(255) NOT NULL COMMENT '标题',
`content` VARCHAR(255) NOT NULL COMMENT '正文内容'
) COMMENT '文章';

INSERT INTO `article`(`author_id`, `category_id`, `views`, `comments`, `title`, `content`) VALUES(1,1,1,1,'1','1');
INSERT INTO `article`(`author_id`, `category_id`, `views`, `comments`, `title`, `content`) VALUES(2,2,2,2,'2','2');
INSERT INTO `article`(`author_id`, `category_id`, `views`, `comments`, `title`, `content`) VALUES(3,3,3,3,'3','3');
INSERT INTO `article`(`author_id`, `category_id`, `views`, `comments`, `title`, `content`) VALUES(1,1,3,3,'3','3');
INSERT INTO `article`(`author_id`, `category_id`, `views`, `comments`, `title`, `content`) VALUES(1,1,4,4,'4','4');
```

> 案列 ：查询`category_id`为1且`comments`大于1的情况下，`views`最多的`article_id`。

```mysql
#1.sql语句
mysql> select id,author_id from article where category_id =1 and comments >1 order by views desc limit 1;
+----+-----------+
| id | author_id |
+----+-----------+
|  5 |         1 |
+----+-----------+
1 row in set (0.00 sec)


#2.sql执行计划
mysql> explain select id,author_id from article where category_id =1 and comments >1 order by views desc limit 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: article
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 5
        Extra: Using where; Using filesort   # 产生了文件内排序，需要优化SQL
1 row in set (0.00 sec)

#第一次优化 建立索引 categoryId_comments_views
create index categoryId_comments_views on article(category_id,comments,views);

#查看当前索引
mysql> show index from article;
+---------+------------+---------------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| Table   | Non_unique | Key_name                  | Seq_in_index | Column_name | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment |
+---------+------------+---------------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| article |          0 | PRIMARY                   |            1 | id          | A         |           2 |     NULL | NULL   |      | BTREE      |         |               |
| article |          1 | categoryId_comments_views |            1 | category_id | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
| article |          1 | categoryId_comments_views |            2 | comments    | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
| article |          1 | categoryId_comments_views |            3 | views       | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
+---------+------------+---------------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
4 rows in set (0.00 sec)

#查看当前sql执行计划
#我们发现，创建符合索引categoryId_comments_views之后，虽然解决了全表扫描的问题，但是在order by排序的时候没有用到索引，MySQL居然还是用的Using filesort，为什么？

mysql> explain select id,author_id from article where category_id =1 and comments >1 order by views desc limit 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: article
         type: range
possible_keys: categoryId_comments_views
          key: categoryId_comments_views
      key_len: 8
          ref: NULL
         rows: 2
        Extra: Using where; Using filesort
1 row in set (0.00 sec)


# 我们试试把SQL修改为SELECT id,author_id FROM article WHERE category_id = 1 AND comments = 1 ORDER BY views DESC LIMIT 1;看看SQL的执行计划。
#推论：当comments > 1的时候order by排序views字段索引就用不上，但是当comments = 1的时候order by排序views字段索引就可以用上！！！所以，范围之后的索引会失效。
mysql> explain select id,author_id from article where category_id =1 and comments =1 order by views desc limit 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: article
         type: ref
possible_keys: categoryId_comments_views
          key: categoryId_comments_views
      key_len: 8
          ref: const,const
         rows: 1
        Extra: Using where
1 row in set (0.00 sec)


# 现在知道范围之后的索引会失效，原来的索引categoryId_comments_views最后一个字段views会失效，那么我们如果删除这个索引，创建categoryId_comments索引呢？？？？

#删除索引
DROP INDEX ategoryId_comments_views ON article;

#创建索引
CREATE INDEX idx_article_cv ON article(category_id,views);

#查看当前索引
show index from article;

#查看当前sql执行计划
mysql> explain select id,author_id from article where category_id =1 and comments >1 order by views desc limit 1 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: article
         type: range
possible_keys: idx_article_cv
          key: idx_article_cv
      key_len: 4
          ref: NULL
         rows: 3
        Extra: Using where
1 row in set (0.00 sec)


```



#### 4.6.2两表索引分析

数据准备

```mysql
DROP TABLE IF EXISTS `class`;
DROP TABLE IF EXISTS `book`;

CREATE TABLE IF NOT EXISTS `class`(
`id` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
`card` INT(10) UNSIGNED NOT NULL COMMENT '分类' 
) COMMENT '商品类别';

CREATE TABLE IF NOT EXISTS `book`(
`bookid` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
`card` INT(10) UNSIGNED NOT NULL COMMENT '分类'
) COMMENT '书籍';



insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));

insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));

```

不创建索引的情况下，SQL的执行计划。

```mysql
mysql> explain select * from book left join class on book.card=class.card;
+----+-------------+-------+------+---------------+------+---------+------+------+-------+
| id | select_type | table | type | possible_keys | key  | key_len | ref  | rows | Extra |
+----+-------------+-------+------+---------------+------+---------+------+------+-------+
|  1 | SIMPLE      | book  | ALL  | NULL          | NULL | NULL    | NULL |   20 |       |
|  1 | SIMPLE      | class | ALL  | NULL          | NULL | NULL    | NULL |   20 |       |
+----+-------------+-------+------+---------------+------+---------+------+------+-------+
2 rows in set (0.00 sec)

```

`book`和`class`两张表都是没有使用索引，全表扫描，那么如果进行优化，索引是创建在`book`表还是创建在`class`表呢？下面进行大胆的尝试！

左表(`book`表)创建索引。

```mysql
CREATE INDEX idx_book_card ON book(card);
```

在`book`表中有`idx_book_card`索引的情况下，查看SQL执行计划

```mysql

mysql> explain select * from book left join class on book.card=class.card;
+----+-------------+-------+-------+---------------+---------------+---------+------+------+-------------+
| id | select_type | table | type  | possible_keys | key           | key_len | ref  | rows | Extra       |
+----+-------------+-------+-------+---------------+---------------+---------+------+------+-------------+
|  1 | SIMPLE      | book  | index | NULL          | idx_book_card | 4       | NULL |   20 | Using index |
|  1 | SIMPLE      | class | ALL   | NULL          | NULL          | NULL    | NULL |   20 |             |
+----+-------------+-------+-------+---------------+---------------+---------+------+------+-------------+
2 rows in set (0.00 sec)

```

删除`book`表的索引，右表(`class`表)创建索引。

```mysql
drop index idx_book_card on book;
```

右表创建索引

```mysql
CREATE INDEX idx_class_card ON class(card);
```



在`class`表中有`idx_class_card`索引的情况下，查看SQL执行计划

```mysql
mysql> explain select * from book left join class on book.card=class.card;
+----+-------------+-------+------+----------------+----------------+---------+-----------------+------+-------------+
| id | select_type | table | type | possible_keys  | key            | key_len | ref             | rows | Extra       |
+----+-------------+-------+------+----------------+----------------+---------+-----------------+------+-------------+
|  1 | SIMPLE      | book  | ALL  | NULL           | NULL           | NULL    | NULL            |   20 |             |
|  1 | SIMPLE      | class | ref  | idx_class_card | idx_class_card | 4       | study.book.card |    1 | Using index |
+----+-------------+-------+------+----------------+----------------+---------+-----------------+------+-------------+
2 rows in set (0.00 sec)

```

<font color="red">**由此可见，左连接将索引创建在右表上更合适，右连接将索引创建在左表上更合适。**</font>

#### 4.6.3三表索引分析

数据准备

```mysql
CREATE TABLE IF NOT EXISTS `phone`(
`phoneid` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`card` INT (10) UNSIGNED NOT NULL
)ENGINE = INNODB;

insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
```



> 三表连接查询SQL优化

1、不加任何索引，查看SQL执行计划。

[![explain](https://camo.githubusercontent.com/9dcd7591ba18a63cc467bb82a603bbcb1a89793f61de5de0710a6126b54bf380/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303230303830333136303633313738362e706e67)](https://camo.githubusercontent.com/9dcd7591ba18a63cc467bb82a603bbcb1a89793f61de5de0710a6126b54bf380/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303230303830333136303633313738362e706e67)

2、根据两表查询优化的经验，左连接需要在右表上添加索引，所以尝试在`book`表和`phone`表上添加索引。

```
/* 在book表创建索引 */
CREATE INDEX idx_book_card ON book(card);

/* 在phone表上创建索引 */
CREATE INDEX idx_phone_card ON phone(card);
```

再次执行SQL的执行计划

[![explain](https://camo.githubusercontent.com/f9108f20d0c1b7412e1b6158bd50761dbbd264453aa7ac3e0af28189f6ffe18f/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303230303830333136313031333838302e706e67)](https://camo.githubusercontent.com/f9108f20d0c1b7412e1b6158bd50761dbbd264453aa7ac3e0af28189f6ffe18f/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303230303830333136313031333838302e706e67)

4.结论

`JOIN`语句的优化：

- 尽可能减少`JOIN`语句中的`NestedLoop`（嵌套循环）的总次数：**永远都是小的结果集驱动大的结果集**。
- 优先优化`NestedLoop`的内层循环。
- 保证`JOIN`语句中被驱动表上`JOIN`条件字段已经被索引。
- 当无法保证被驱动表的`JOIN`条件字段被索引且内存资源充足的前提下，不要太吝惜`Join Buffer` 的设置。



#### 4.6.3总结

> 索引失效情况

- 全值匹配我最爱。
- 最佳左前缀法则。
- 不在索引列上做任何操作（计算、函数、(自动or手动)类型转换），会导致索引失效而转向全表扫描。
- 索引中范围条件右边的字段会全部失效。
- 尽量使用覆盖索引（只访问索引的查询，索引列和查询列一致），减少`SELECT *`。
- MySQL在使用`!=`或者`<>`的时候无法使用索引会导致全表扫描。
- `is null`、`is not null`也无法使用索引。
- `like`以通配符开头`%abc`索引失效会变成全表扫描。
- 字符串不加单引号索引失效。
- 少用`or`，用它来连接时会索引失效。



索引优化的一般性建议：

- 对于单值索引，尽量选择针对当前`query`过滤性更好的索引。
- 在选择复合索引的时候，当前`query`中过滤性最好的字段在索引字段顺序中，位置越靠前越好。
- 在选择复合索引的时候，尽量选择可以能够包含当前`query`中的`where`子句中更多字段的索引。
- 尽可能通过分析统计信息和调整`query`的写法来达到选择合适索引的目的。

口诀：

- 带头大哥不能死。
- 中间兄弟不能断。
- 索引列上不计算。
- 范围之后全失效。
- 覆盖索引尽量用。
- 不等有时会失效。
- like百分加右边。
- 字符要加单引号。
- 一般SQL少用or。

```mysql
10.2.最佳左前缀法则
案例

/* 用到了idx_staffs_name_age_pos索引中的name字段 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` = 'Ringo';

/* 用到了idx_staffs_name_age_pos索引中的name, age字段 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` = 'Ringo' AND `age` = 18;

/* 用到了idx_staffs_name_age_pos索引中的name，age，pos字段 这是属于全值匹配的情况！！！*/
EXPLAIN SELECT * FROM `staffs` WHERE `name` = 'Ringo' AND `age` = 18 AND `pos` = 'manager';

/* 索引没用上，ALL全表扫描 */
EXPLAIN SELECT * FROM `staffs` WHERE `age` = 18 AND `pos` = 'manager';

/* 索引没用上，ALL全表扫描 */
EXPLAIN SELECT * FROM `staffs` WHERE `pos` = 'manager';

/* 用到了idx_staffs_name_age_pos索引中的name字段，pos字段索引失效 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` = 'Ringo' AND `pos` = 'manager';
概念

最佳左前缀法则：如果索引是多字段的复合索引，要遵守最佳左前缀法则。指的是查询从索引的最左前列开始并且不跳过索引中的字段。

口诀：带头大哥不能死，中间兄弟不能断。

10.3.索引列上不计算
案例

# 现在要查询`name` = 'Ringo'的记录下面有两种方式来查询！

# 1、直接使用 字段 = 值的方式来计算
mysql> SELECT * FROM `staffs` WHERE `name` = 'Ringo';
+----+-------+-----+---------+---------------------+
| id | name  | age | pos     | add_time            |
+----+-------+-----+---------+---------------------+
|  1 | Ringo |  18 | manager | 2020-08-03 08:30:39 |
+----+-------+-----+---------+---------------------+
1 row in set (0.00 sec)

# 2、使用MySQL内置的函数
mysql> SELECT * FROM `staffs` WHERE LEFT(`name`, 5) = 'Ringo';
+----+-------+-----+---------+---------------------+
| id | name  | age | pos     | add_time            |
+----+-------+-----+---------+---------------------+
|  1 | Ringo |  18 | manager | 2020-08-03 08:30:39 |
+----+-------+-----+---------+---------------------+
1 row in set (0.00 sec)
我们发现以上两条SQL的执行结果都是一样的，但是执行效率有没有差距呢？？？

通过分析两条SQL的执行计划来分析性能。

explain

由此可见，在索引列上进行计算，会使索引失效。

口诀：索引列上不计算。

10.4.范围之后全失效
案例

/* 用到了idx_staffs_name_age_pos索引中的name，age，pos字段 这是属于全值匹配的情况！！！*/
EXPLAIN SELECT * FROM `staffs` WHERE `name` = 'Ringo' AND `age` = 18 AND `pos` = 'manager';


/* 用到了idx_staffs_name_age_pos索引中的name，age字段，pos字段索引失效 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` = '张三' AND `age` > 18 AND `pos` = 'dev';
查看上述SQL的执行计划

explain

由此可知，查询范围的字段使用到了索引，但是范围之后的索引字段会失效。

口诀：范围之后全失效。

10.5.覆盖索引尽量用
在写SQL的不要使用SELECT *，用什么字段就查询什么字段。

/* 没有用到覆盖索引 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` = 'Ringo' AND `age` = 18 AND `pos` = 'manager';

/* 用到了覆盖索引 */
EXPLAIN SELECT `name`, `age`, `pos` FROM `staffs` WHERE `name` = 'Ringo' AND `age` = 18 AND `pos` = 'manager';
使用覆盖索引

口诀：查询一定不用*。

10.6.不等有时会失效
/* 会使用到覆盖索引 */
EXPLAIN SELECT `name`, `age`, `pos` FROM `staffs` WHERE `name` != 'Ringo';

/* 索引失效 全表扫描 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` != 'Ringo';
10.7.like百分加右边
/* 索引失效 全表扫描 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` LIKE '%ing%';

/* 索引失效 全表扫描 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` LIKE '%ing';

/* 使用索引范围查询 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` LIKE 'Rin%';
口诀：like百分加右边。

如果一定要使用%like，而且还要保证索引不失效，那么使用覆盖索引来编写SQL。

/* 使用到了覆盖索引 */
EXPLAIN SELECT `id` FROM `staffs` WHERE `name` LIKE '%in%';

/* 使用到了覆盖索引 */
EXPLAIN SELECT `name` FROM `staffs` WHERE `name` LIKE '%in%';

/* 使用到了覆盖索引 */
EXPLAIN SELECT `age` FROM `staffs` WHERE `name` LIKE '%in%';

/* 使用到了覆盖索引 */
EXPLAIN SELECT `pos` FROM `staffs` WHERE `name` LIKE '%in%';

/* 使用到了覆盖索引 */
EXPLAIN SELECT `id`, `name` FROM `staffs` WHERE `name` LIKE '%in%';

/* 使用到了覆盖索引 */
EXPLAIN SELECT `id`, `age` FROM `staffs` WHERE `name` LIKE '%in%';

/* 使用到了覆盖索引 */
EXPLAIN SELECT `id`,`name`, `age`, `pos` FROM `staffs` WHERE `name` LIKE '%in';

/* 使用到了覆盖索引 */
EXPLAIN SELECT `id`, `name` FROM `staffs` WHERE `pos` LIKE '%na';

/* 索引失效 全表扫描 */
EXPLAIN SELECT `name`, `age`, `pos`, `add_time` FROM `staffs` WHERE `name` LIKE '%in';
模糊查询百分号一定加前边

口诀：覆盖索引保两边。

10.8.字符要加单引号
/* 使用到了覆盖索引 */
EXPLAIN SELECT `id`, `name` FROM `staffs` WHERE `name` = 'Ringo';

/* 使用到了覆盖索引 */
EXPLAIN SELECT `id`, `name` FROM `staffs` WHERE `name` = 2000;

/* 索引失效 全表扫描 */
EXPLAIN SELECT * FROM `staffs` WHERE `name` = 2000;
这里name = 2000在MySQL中会发生强制类型转换，将数字转成字符串。

口诀：字符要加单引号。
```





## 5、查询截取分析

### 5.1查询优化

#### 5.1.1 永远小表驱动大表

优化原则：对于MySQL数据库而言，永远都是小表驱动大表。

```mysql
/**
* 举个例子：可以使用嵌套的for循环来理解小表驱动大表。
* 以下两个循环结果都是一样的，但是对于MySQL来说不一样，
* 第一种可以理解为，和MySQL建立5次连接每次查询1000次。
* 第二种可以理解为，和MySQL建立1000次连接每次查询5次。
*/
for(int i = 1; i <= 5; i ++){
    for(int j = 1; j <= 1000; j++){
        
    }
}
// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
for(int i = 1; i <= 1000; i ++){
    for(int j = 1; j <= 5; j++){
        
    }
}
```

> IN和EXISTS

```mysql
/* 优化原则：小表驱动大表，即小的数据集驱动大的数据集 */

/* IN适合B表比A表数据小的情况*/
SELECT * FROM `A` WHERE `id` IN (SELECT `id` FROM `B`)

/* EXISTS适合B表比A表数据大的情况 */
SELECT * FROM `A` WHERE EXISTS (SELECT 1 FROM `B` WHERE `B`.id = `A`.id);
```

**EXISTS：**

- 语法：`SELECT....FROM tab WHERE EXISTS(subquery);`该语法可以理解为：
- 该语法可以理解为：将主查询的数据，放到子查询中做条件验证，根据验证结果（`true`或是`false`）来决定主查询的数据结果是否得以保留。

**提示：**

- `EXISTS(subquery)`子查询只返回`true`或者`false`，因此子查询中的`SELECT *`可以是`SELECT 1 OR SELECT X`，它们并没有区别。
- `EXISTS(subquery)`子查询的实际执行过程可能经过了优化而不是我们理解上的逐条对比，如果担心效率问题，可进行实际检验以确定是否有效率问题。
- `EXISTS(subquery)`子查询往往也可以用条件表达式，其他子查询或者`JOIN`替代，何种最优需要具体问题具体分析。

#### 5.1.2 ORDER BY关键字优化

##### 5.1.2.1数据准备

```mysql
CREATE TABLE `talA`(
`age` INT,
`birth` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO `talA`(`age`) VALUES(18);
INSERT INTO `talA`(`age`) VALUES(19);
INSERT INTO `talA`(`age`) VALUES(20);
INSERT INTO `talA`(`age`) VALUES(21);
INSERT INTO `talA`(`age`) VALUES(22);
INSERT INTO `talA`(`age`) VALUES(23);
INSERT INTO `talA`(`age`) VALUES(24);
INSERT INTO `talA`(`age`) VALUES(25);

/* 创建索引 */
CREATE INDEX idx_talA_age_birth ON `talA`(`age`, `birth`);
```

##### 5.1.2.2案列

```mysql
/* 1.使用索引进行排序了 不会产生Using filesort */
EXPLAIN SELECT * FROM `talA` WHERE `age` > 20 ORDER BY `age`;

/* 2.使用索引进行排序了 不会产生Using filesort */
EXPLAIN SELECT * FROM `talA` WHERE `age` > 20 ORDER BY `age`,`birth`;

/* 3.没有使用索引进行排序 产生了Using filesort */
EXPLAIN SELECT * FROM `talA` WHERE `age` > 20 ORDER BY `birth`;

/* 4.没有使用索引进行排序 产生了Using filesort */
EXPLAIN SELECT * FROM `talA` WHERE `age` > 20 ORDER BY `birth`,`age`;

/* 5.没有使用索引进行排序 产生了Using filesort */
EXPLAIN SELECT * FROM `talA` ORDER BY `birth`;

/* 6.没有使用索引进行排序 产生了Using filesort */
EXPLAIN SELECT * FROM `talA` WHERE `birth` > '2020-08-04 07:42:21' ORDER BY `birth`;

/* 7.使用索引进行排序了 不会产生Using filesort */
EXPLAIN SELECT * FROM `talA` WHERE `birth` > '2020-08-04 07:42:21' ORDER BY `age`;

/* 8.没有使用索引进行排序 产生了Using filesort */
EXPLAIN SELECT * FROM `talA` ORDER BY `age` ASC, `birth` DESC;
```

##### 5.1.2.3  ORDERY BY  index 和 filesort排序

- `ORDER BY`子句，尽量使用索引排序，避免使用`Using filesort`排序。

  MySQL支持两种方式的排序，`FileSort`和`Index`，`Index`的效率高，它指MySQL扫描索引本身完成排序。`FileSort`方式效率较低。

  `ORDER BY`满足两情况，会使用`Index`方式排序：

  - `ORDER BY`语句使用索引最左前列。
  - 使用`WHERE`子句与`ORDER BY`子句条件列组合满足索引最左前列。

  **结论：尽可能在索引列上完成排序操作，遵照索引建的最佳左前缀原则。**



##### 5.1.2.4 filesort排序的两种算法

> 如果不在索引列上，File Sort有两种算法：MySQL就要启动双路排序算法和单路排序算法

###### 5.1.2.4.1双路排序算法

双路排序算法：MySQL4.1之前使用双路排序，字面意思就是两次扫描磁盘，最终得到数据，读取行指针和`ORDER BY`列，対他们进行排序，然后扫描已经排序好的列表，按照列表中的值重新从列表中读取对应的数据输出。**一句话，从磁盘取排序字段，在`buffer`中进行排序，再从磁盘取其他字段。**

取一批数据，要对磁盘进行两次扫描，众所周知，IO是很耗时的，所以在MySQL4.1之后，出现了改进的算法，就是单路排序算法。

###### 5.1.2.4.2单路排序算法

单路排序算法：从磁盘读取查询需要的所有列，按照`ORDER BY`列在`buffer`対它们进行排序，然后扫描排序后的列表进行输出，它的效率更快一些，避免了第二次读取数据。并且把随机IO变成了顺序IO，但是它会使用更多的空间，因为它把每一行都保存在内存中了。

由于单路排序算法是后出的，总体而言效率好过双路排序算法。

但是单路排序算法有问题：如果`SortBuffer`缓冲区太小，导致从磁盘中读取所有的列不能完全保存在`SortBuffer`缓冲区中，这时候单路复用算法就会出现问题，反而性能不如双路复用算法。

**单路复用算法的优化策略：**

- 增大`sort_buffer_size`参数的设置。
- 增大`max_length_for_sort_data`参数的设置。

**提高ORDER BY排序的速度：**

- `ORDER BY`时使用`SELECT *`是大忌，查什么字段就写什么字段，这点非常重要。在这里的影响是：
  - 当查询的字段大小总和小于`max_length_for_sort_data`而且排序字段不是`TEXT|BLOB`类型时，会使用单路排序算法，否则使用多路排序算法。
  - 两种排序算法的数据都有可能超出`sort_buffer`缓冲区的容量，超出之后，会创建`tmp`临时文件进行合并排序，导致多次IO，但是单路排序算法的风险会更大一些，所以要增大`sort_buffer_size`参数的设置。
- 尝试提高`sort_buffer_size`：不管使用哪种算法，提高这个参数都会提高效率，当然，要根据系统的能力去提高，因为这个参数是针对每个进程的。
- 尝试提高`max_length_for_sort_data`：提高这个参数，会增加用单路排序算法的概率。但是如果设置的太高，数据总容量`sort_buffer_size`的概率就增大，明显症状是高的磁盘IO活动和低的处理器使用率。

#### 5.1.3 GROUP BY 关键字优化

其它情况和order by一样

- `GROUP BY`实质是先排序后进行分组，遵照索引建的最佳左前缀。
- 当无法使用索引列时，会使用`Using filesort`进行排序，增大`max_length_for_sort_data`参数的设置和增大`sort_buffer_size`参数的设置，会提高性能。
- `WHERE`执行顺序高于`HAVING`，能写在`WHERE`限定条件里的就不要写在`HAVING`中了。

### 5.2 慢查询日志

#### 5.2.1是什么？

**基本介绍：**

- MySQL的慢查询日志是MySQL提供的一种日志记录，它用来记录在MySQL中响应时间超过阈值的语句，具体指运行时间超过`long_query_time`值的SQL，则会被记录到慢查询日志中。
- `long_query_time`的默认值为10，意思是运行10秒以上的语句。
- 由慢查询日志来查看哪些SQL超出了我们的最大忍耐时间值，比如一条SQL执行超过5秒钟，我们就算慢SQL，希望能收集超过5秒钟的SQL，结合之前`explain`进行全面分析。

<font color='red'>特别说明：</font>**默认情况下，MySQL数据库没有开启慢查询日志，**需要我们手动来设置这个参数。

**如果不是调优需要的话，一般不建议启动该参数**，因为开启慢查询日志会或多或少带来一定的性能影响。慢查询日志支持将日志记录写入文件。



#### 5.2.2怎么玩？

##### 5.2.2.1 查看是否开启 

1、使用命令查询：show variables like '%slow_query_log%';

```mysql

mysql> show variables like '%slow_query_log%';
+---------------------+---------------------------------+
| Variable_name       | Value                           |
+---------------------+---------------------------------+
| slow_query_log      | ON                              |
| slow_query_log_file | /www/server/data/mysql-slow.log |
+---------------------+---------------------------------+
2 rows in set (0.00 sec)

```

2、查看配置文件my.cnf



##### 5.2.2.2如何开启

1、使用命令

开启慢查询日志：`SET GLOBAL slow_query_log = 1;`。**使用该方法开启MySQL的慢查询日志只对当前数据库生效，如果MySQL重启后会失效。**

```mysql
mysql> SET GLOBAL slow_query_log = 1;
Query OK, 0 rows affected (0.00 sec)

```

2、使用配置文件my.cnf

使用配置文件则永久开启

在`[mysqld]`下增加修改参数。

```mysql
# my.cnf
[mysqld]
# 1.这个是开启慢查询。注意ON需要大写
slow_query_log=ON  

# 2.这个是存储慢查询的日志文件。这个文件不存在的话，需要自己创建
slow_query_log_file=/var/lib/mysql/slow.log
```

##### 5.2.2.3设置慢的阙值时间

**疑问 开启了慢查询日志后，什么样的SQL才会被记录到慢查询日志里面呢？**

这个是由参数`long_query_time`控制的，默认情况下`long_query_time`的值为10秒。

MySQL中查看`long_query_time`的时间：`SHOW VARIABLES LIKE 'long_query_time%';`。

<font color='red'>只有sql执行时间大于long_query_time的才会被记录</font>

> 设置慢的阙值时间

1. 使用命令	

   set global long_query_time=3;

   为什么设置之后看不出变化？

   1、需要重新连接或者重新打开一个会话 SHOW VARIABLES LIKE 'long_query_time%';

   2、或者使用 SHOW global  VARIABLES LIKE 'long_query_time%';



2. 配置文件修改

```mysql
[mysqld]
# 这个是设置慢查询的时间，我设置的为1秒
long_query_time=1
```



#### 5.2.3 日志分析工具

日志分析工具`mysqldumpslow`：在生产环境中，如果要手工分析日志，查找、分析SQL，显然是个体力活，MySQL提供了日志分析工具`mysqldumpslow`。

```mysql
# 1、mysqldumpslow --help 来查看mysqldumpslow的帮助信息
root@1dcb5644392c:/usr/bin# mysqldumpslow --help
Usage: mysqldumpslow [ OPTS... ] [ LOGS... ]

Parse and summarize the MySQL slow query log. Options are

  --verbose    verbose
  --debug      debug
  --help       write this text to standard output

  -v           verbose
  -d           debug
  -s ORDER     what to sort by (al, at, ar, c, l, r, t), 'at' is default  # 按照何种方式排序
                al: average lock time # 平均锁定时间
                ar: average rows sent # 平均返回记录数
                at: average query time # 平均查询时间
                 c: count  # 访问次数
                 l: lock time  # 锁定时间
                 r: rows sent  # 返回记录
                 t: query time  # 查询时间 
  -r           reverse the sort order (largest last instead of first)
  -t NUM       just show the top n queries  # 返回前面多少条记录
  -a           don't abstract all numbers to N and strings to 'S'
  -n NUM       abstract numbers with at least n digits within names
  -g PATTERN   grep: only consider stmts that include this string  
  -h HOSTNAME  hostname of db server for *-slow.log filename (can be wildcard),
               default is '*', i.e. match all
  -i NAME      name of server instance (if using mysql.server startup script)
  -l           don't subtract lock time from total time
  
# 2、 案例
# 2.1、得到返回记录集最多的10个SQL
mysqldumpslow -s r -t 10 /var/lib/mysql/slow.log
 
# 2.2、得到访问次数最多的10个SQL
mysqldumpslow -s c -t 10 /var/lib/mysql/slow.log
 
# 2.3、得到按照时间排序的前10条里面含有左连接的查询语句
mysqldumpslow -s t -t 10 -g "left join" /var/lib/mysql/slow.log

# 2.4、另外建议使用这些命令时结合|和more使用，否则出现爆屏的情况
mysqldumpslow -s r -t 10 /var/lib/mysql/slow.log | more
```



### 5.3 批量插入数据脚本

建表sql

```mysql
/* 1.dept表 */
CREATE TABLE `dept` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `deptno` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '部门id',
  `dname` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名字',
  `loc` varchar(13) NOT NULL DEFAULT '' COMMENT '部门地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表'

/* 2.emp表 */
CREATE TABLE `emp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `empno` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '员工编号',
  `ename` varchar(20) NOT NULL DEFAULT '' COMMENT '员工名字',
  `job` varchar(9) NOT NULL DEFAULT '' COMMENT '职位',
  `mgr` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上级编号',
  `hiredata` date NOT NULL COMMENT '入职时间',
  `sal` decimal(7,2) NOT NULL COMMENT '薪水',
  `comm` decimal(7,2) NOT NULL COMMENT '分红',
  `deptno` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表'
```

由于开启过慢查询日志，开启了`bin-log`，我们就必须为`function`指定一个参数，否则使用函数会报错。

```mysql
# 在mysql中设置 
# log_bin_trust_function_creators 默认是关闭的 需要手动开启
mysql> SHOW VARIABLES LIKE 'log_bin_trust_function_creators';
+---------------------------------+-------+
| Variable_name                   | Value |
+---------------------------------+-------+
| log_bin_trust_function_creators | OFF   |
+---------------------------------+-------+
1 row in set (0.00 sec)

mysql> SET GLOBAL log_bin_trust_function_creators=1;
Query OK, 0 rows affected (0.00 sec)
```

上述修改方式MySQL重启后会失败，在`my.cnf`配置文件下修改永久有效。

```mysql
[mysqld]
log_bin_trust_function_creators=ON
```

创建函数

```mysql
# 1、函数：随机产生字符串
DELIMITER $$
CREATE FUNCTION rand_string(n INT) RETURNS VARCHAR(255)
BEGIN
    DECLARE chars_str VARCHAR(100) DEFAULT 'abcdefghijklmnopqrstuvwsyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    DECLARE return_str VARCHAR(255) DEFAULT '';
    DECLARE i INT DEFAULT 0;
    WHILE i < n DO
    SET return_str = CONCAT(return_str,SUBSTRING(chars_str,FLOOR(1+RAND()*52),1));
    SET i = i + 1;
    END WHILE;
    RETURN return_str;
END $$

# 2、函数：随机产生部门编号
DELIMITER $$
CREATE FUNCTION rand_num() RETURNS INT(5)
BEGIN
    DECLARE i INT DEFAULT 0;
    SET i = FLOOR(100 + RAND() * 10);
    RETURN i;
END $$
```

创建存储过程

```mysql
# 1、函数：向dept表批量插入
DELIMITER $$
CREATE PROCEDURE insert_dept(IN START INT(10),IN max_num INT(10))
BEGIN
DECLARE i INT DEFAULT 0;
    SET autocommit = 0;
    REPEAT
    SET i = i + 1;
    INSERT INTO dept(deptno,dname,loc) VALUES((START + i),rand_string(10),rand_string(8));
    UNTIL i = max_num
    END REPEAT;
    COMMIT;
END $$

# 2、函数：向emp表批量插入
DELIMITER $$
CREATE PROCEDURE insert_emp(IN START INT(10),IN max_num INT(10))
BEGIN
DECLARE i INT DEFAULT 0;
    SET autocommit = 0;
    REPEAT
    SET i = i + 1;
    INSERT INTO emp(empno,ename,job,mgr,hiredata,sal,comm,deptno) VALUES((START + i),rand_string(6),'SALESMAN',0001,CURDATE(),2000,400,rand_num());
    UNTIL i = max_num
    END REPEAT;
    COMMIT;
END $$
```

调用存储过程

```mysql
# 1、调用存储过程向dept表插入10个部门。
DELIMITER ;
CALL insert_dept(100,10);

# 2、调用存储过程向emp表插入50万条数据。
DELIMITER ;
CALL insert_emp(100001,500000);
```



### 5.4 Show profile

> Show Profile是什么？

`Show Profile`：MySQL提供可以用来分析当前会话中语句执行的资源消耗情况。可以用于SQL的调优的测量。**默认情况下，参数处于关闭状态，并保存最近15次的运行结果。**

> 分析步骤

1、是否支持，看看当前的MySQL版本是否支持。

```mysql
# 查看Show Profile功能是否开启
mysql> show variables like 'profiling';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| profiling     | OFF   |
+---------------+-------+
1 row in set (0.00 sec)

```

2、开启`Show Profile`功能，默认是关闭的，使用前需要开启。

```mysql
# 开启Show Profile功能
mysql> SET PROFILING=on;
Query OK, 0 rows affected (0.00 sec)
```

3、运行SQL

```mysql
SELECT * FROM `emp` GROUP BY `id`%10 LIMIT 150000;

SELECT * FROM `emp` GROUP BY `id`%20 ORDER BY 5;
```

4、查看结果，执行`SHOW PROFILES;`

`Duration`：持续时间。

```mysql
mysql> show profiles;
+----------+------------+---------------------------------------------------+
| Query_ID | Duration   | Query                                             |
+----------+------------+---------------------------------------------------+
|        1 | 0.00034275 | show variables like 'profiling'                   |
|        2 | 1.57023825 | SELECT * FROM `emp` GROUP BY `id`%10 LIMIT 150000 |
|        3 | 1.81227125 | SELECT * FROM `emp` GROUP BY `id`%20 ORDER BY 5   |
+----------+------------+---------------------------------------------------+
3 rows in set (0.00 sec)
```

5、诊断SQL，`SHOW PROFILE cpu,block io FOR QUERY Query_ID;`

```mysql
# 这里的3是第四步中的Query_ID。
# 可以在SHOW PROFILE中看到一条SQL中完整的生命周期。
mysql> show profile cpu ,block io for query 3;
+--------------------------------+----------+----------+------------+--------------+---------------+
| Status                         | Duration | CPU_user | CPU_system | Block_ops_in | Block_ops_out |
+--------------------------------+----------+----------+------------+--------------+---------------+
| starting                       | 0.000020 | 0.000015 |   0.000000 |            0 |             0 |
| Waiting for query cache lock   | 0.000003 | 0.000003 |   0.000000 |            0 |             0 |
| checking query cache for query | 0.000047 | 0.000048 |   0.000000 |            0 |             0 |
| checking permissions           | 0.000006 | 0.000005 |   0.000000 |            0 |             0 |
| Opening tables                 | 0.000018 | 0.000018 |   0.000000 |            0 |             0 |
| System lock                    | 0.000019 | 0.000019 |   0.000000 |            0 |             0 |
| Waiting for query cache lock   | 0.000021 | 0.000020 |   0.000000 |            0 |             0 |
| init                           | 0.000028 | 0.000028 |   0.000000 |            0 |             0 |
| optimizing                     | 0.000005 | 0.000005 |   0.000000 |            0 |             0 |
| statistics                     | 0.000013 | 0.000013 |   0.000000 |            0 |             0 |
| preparing                      | 0.000011 | 0.000011 |   0.000000 |            0 |             0 |
| Creating tmp table             | 0.000039 | 0.000038 |   0.000000 |            0 |             0 |
| executing                      | 0.000003 | 0.000003 |   0.000000 |            0 |             0 |
| Copying to tmp table           | 1.811892 | 1.703421 |   0.000000 |            0 |             0 |
| Sorting result                 | 0.000031 | 0.000026 |   0.000000 |            0 |             0 |
| Sending data                   | 0.000046 | 0.000046 |   0.000000 |            0 |             0 |
| end                            | 0.000004 | 0.000004 |   0.000000 |            0 |             0 |
| removing tmp table             | 0.000008 | 0.000008 |   0.000000 |            0 |             0 |
| end                            | 0.000004 | 0.000003 |   0.000000 |            0 |             0 |
| query end                      | 0.000005 | 0.000005 |   0.000000 |            0 |             0 |
| closing tables                 | 0.000010 | 0.000010 |   0.000000 |            0 |             0 |
| freeing items                  | 0.000008 | 0.000008 |   0.000000 |            0 |             0 |
| Waiting for query cache lock   | 0.000003 | 0.000002 |   0.000000 |            0 |             0 |
| freeing items                  | 0.000019 | 0.000019 |   0.000000 |            0 |             0 |
| Waiting for query cache lock   | 0.000003 | 0.000003 |   0.000000 |            0 |             0 |
| freeing items                  | 0.000002 | 0.000002 |   0.000000 |            0 |             0 |
| storing result in query cache  | 0.000003 | 0.000002 |   0.000000 |            0 |             0 |
| logging slow query             | 0.000003 | 0.000003 |   0.000000 |            0 |             0 |
| cleaning up                    | 0.000003 | 0.000003 |   0.000000 |            0 |             0 |
+--------------------------------+----------+----------+------------+--------------+---------------+
29 rows in set (0.00 sec)

```

`Show Profile`查询参数备注：

- `ALL`：显示所有的开销信息。
- `BLOCK IO`：显示块IO相关开销（通用）。
- `CONTEXT SWITCHES`：上下文切换相关开销。
- `CPU`：显示CPU相关开销信息（通用）。
- `IPC`：显示发送和接收相关开销信息。
- `MEMORY`：显示内存相关开销信息。
- `PAGE FAULTS`：显示页面错误相关开销信息。
- `SOURCE`：显示和Source_function。
- `SWAPS`：显示交换次数相关开销的信息。

<font color='red'>6、`Show Profile`查询列表，日常开发需要注意的结论：</font>

- `converting HEAP to MyISAM`：查询结果太大，内存都不够用了，往磁盘上搬了。
- `Creating tmp table`：创建临时表（拷贝数据到临时表，用完再删除），非常耗费数据库性能。
- `Copying to tmp table on disk`：把内存中的临时表复制到磁盘，危险！！！
- `locked`：死锁。



### 5.5全局查询日志

> 1、配置启用

修改my.cnf

```shell
#开启
general_log=1
#记录日志文件的路径
general_log_file=/path/logfile
#输出格式
log_output=FILE
```

> 2、编码启用

```mysql
set global general_log=1;

#以后你所编写的sql都会被记录到mysql库里的general_log表
set global log_output='TABLE';

#用命令查看
SELECT * FROM `mysql`.`general_log`;
```



<font color='red' size='50'>永远不要在生产环境开启这个功能</font>



## 6、MYSQL锁机制

### 6.1锁的分类

#### 6.1.1从对数据操作的类型（读\写）分

1. 读锁（共享锁）

   ​	针对同一份数据，多个对操作可以同时进行而不会互相影响。

2. 写锁（排它锁）

   ​	当前写操作没有完成前，它会阻断其它写锁和写锁。

#### 6.1.2从对数据操作的粒度分

1. 表所
2. 行锁



### 6.2表锁（偏读）

#### 6.2.1特点

偏向**MyISAM存储引擎**,开销小,加锁快;无死锁;锁定粒度大,发生锁冲突的概率最高,并发度最低。

#### 6.2.2建表sql

```mysql
# 1、创建表
CREATE TABLE `mylock`(
`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(20)
)ENGINE=MYISAM DEFAULT CHARSET=utf8 COMMENT='测试表锁';

# 2、插入数据
INSERT INTO `mylock`(`name`) VALUES('ZhangSan');
INSERT INTO `mylock`(`name`) VALUES('LiSi');
INSERT INTO `mylock`(`name`) VALUES('WangWu');
INSERT INTO `mylock`(`name`) VALUES('ZhaoLiu');
```

#### 6.2.3命令

> 1、查看数据库表锁的命令。

```mysql
# 查看数据库表锁的命令
show open tables;
```

> 2、给`mylock`表上读锁，给`book`表上写锁。

```mysql
# 给mylock表上读锁，给book表上写锁
LOCK TABLE `mylock` READ, `book` WRITE;

# 查看当前表的状态
SHOW OPEN TABLES;
```

> 3、释放表锁。

```mysql
# 释放给表添加的锁
UNLOCK TABLES;

# 查看当前表的状态
mysql> SHOW OPEN TABLES;
```

#### 6.2.4 读锁案例

> 1、打开两个会话，`SESSION1`为`mylock`表添加读锁。

```
# 为mylock表添加读锁
LOCK TABLE `mylock` READ;
```

> 2、打开两个会话，`SESSION1`是否可以读自己锁的表？是否可以修改自己锁的表？是否可以读其他的表？那么`SESSION2`呢？

```
# SESSION1

# 问题1：SESSION1为mylock表加了读锁，可以读mylock表！
mysql> SELECT * FROM `mylock`;
+----+----------+
| id | name     |
+----+----------+
|  1 | ZhangSan |
|  2 | LiSi     |
|  3 | WangWu   |
|  4 | ZhaoLiu  |
+----+----------+
4 rows in set (0.00 sec)

# 问题2：SESSION1为mylock表加了读锁，不可以修改mylock表！
mysql> UPDATE `mylock` SET `name` = 'abc' WHERE `id` = 1;
ERROR 1099 (HY000): Table 'mylock' was locked with a READ lock and can't be updated

# 问题3：SESSION1为mylock表加了读锁，不可以读其他的表！
mysql> SELECT * FROM `book`;
ERROR 1100 (HY000): Table 'book' was not locked with LOCK TABLES


# SESSION2

# 问题1：SESSION1为mylock表加了读锁，SESSION2可以读mylock表！
mysql> SELECT * FROM `mylock`;
+----+----------+
| id | name     |
+----+----------+
|  1 | ZhangSan |
|  2 | LiSi     |
|  3 | WangWu   |
|  4 | ZhaoLiu  |
+----+----------+
4 rows in set (0.00 sec)

# 问题2：SESSION1为mylock表加了读锁，SESSION2修改mylock表会被阻塞，需要等待SESSION1释放mylock表！
mysql> UPDATE `mylock` SET `name` = 'abc' WHERE `id` = 1;
^C^C -- query aborted
ERROR 1317 (70100): Query execution was interrupted

# 问题3：SESSION1为mylock表加了读锁，SESSION2可以读其他表！
mysql> SELECT * FROM `book`;
+--------+------+
| bookid | card |
+--------+------+
|      1 |    1 |
|      7 |    4 |
|      8 |    4 |
|      9 |    5 |
|      5 |    6 |
|     17 |    6 |
|     15 |    8 |
+--------+------+
24 rows in set (0.00 sec)
```

#### 6.2.5 写锁案例

> 1、打开两个会话，`SESSION1`为`mylock`表添加写锁。

```
# 为mylock表添加写锁
LOCK TABLE `mylock` WRITE;
```

> 2、打开两个会话，`SESSION1`是否可以读自己锁的表？是否可以修改自己锁的表？是否可以读其他的表？那么`SESSION2`呢？

```
# SESSION1

# 问题1：SESSION1为mylock表加了写锁，可以读mylock的表！
mysql> SELECT * FROM `mylock`;
+----+----------+
| id | name     |
+----+----------+
|  1 | ZhangSan |
|  2 | LiSi     |
|  3 | WangWu   |
|  4 | ZhaoLiu  |
+----+----------+
4 rows in set (0.00 sec)

# 问题2：SESSION1为mylock表加了写锁，可以修改mylock表!
mysql> UPDATE `mylock` SET `name` = 'abc' WHERE `id` = 1;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

# 问题3：SESSION1为mylock表加了写锁，不能读其他表!
mysql> SELECT * FROM `book`;
ERROR 1100 (HY000): Table 'book' was not locked with LOCK TABLES

# SESSION2

# 问题1：SESSION1为mylock表加了写锁，SESSION2读mylock表会阻塞，等待SESSION1释放！
mysql> SELECT * FROM `mylock`;
^C^C -- query aborted
ERROR 1317 (70100): Query execution was interrupted

# 问题2：SESSION1为mylock表加了写锁，SESSION2读mylock表会阻塞，等待SESSION1释放！
mysql> UPDATE `mylock` SET `name` = 'abc' WHERE `id` = 1;
^C^C -- query aborted
ERROR 1317 (70100): Query execution was interrupted

# 问题3：SESSION1为mylock表加了写锁，SESSION2可以读其他表！
mysql> SELECT * FROM `book`;
+--------+------+
| bookid | card |
+--------+------+
|      1 |    1 |
|      7 |    4 |
|      8 |    4 |
|      9 |    5 |
|      5 |    6 |
|     17 |    6 |
|     15 |    8 |
+--------+------+
24 rows in set (0.00 sec)
```

#### 6.2.6结论

**`MyISAM`引擎在执行查询语句`SELECT`之前，会自动给涉及到的所有表加读锁，在执行增删改之前，会自动给涉及的表加写锁。**

MySQL的表级锁有两种模式：

- 表共享读锁（Table Read Lock）。
- 表独占写锁（Table Write Lock）。

対`MyISAM`表进行操作，会有以下情况：

- 対`MyISAM`表的读操作（加读锁），不会阻塞其他线程対同一表的读操作，但是会阻塞其他线程対同一表的写操作。只有当读锁释放之后，才会执行其他线程的写操作。
- 対`MyISAM`表的写操作（加写锁），会阻塞其他线程対同一表的读和写操作，只有当写锁释放之后，才会执行其他线程的读写操作。



表锁分析：

```
mysql> SHOW STATUS LIKE 'table%';
+----------------------------+-------+
| Variable_name              | Value |
+----------------------------+-------+
| Table_locks_immediate      | 173   |
| Table_locks_waited         | 0     |
| Table_open_cache_hits      | 5     |
| Table_open_cache_misses    | 8     |
| Table_open_cache_overflows | 0     |
+----------------------------+-------+
5 rows in set (0.00 sec)
```

可以通过`Table_locks_immediate`和`Table_locks_waited`状态变量来分析系统上的表锁定。具体说明如下：

`Table_locks_immediate`：产生表级锁定的次数，表示可以立即获取锁的查询次数，每立即获取锁值加1。

`Table_locks_waited`：出现表级锁定争用而发生等待的次数（不能立即获取锁的次数，每等待一次锁值加1），此值高则说明存在较严重的表级锁争用情况。

**此外，`MyISAM`的读写锁调度是写优先，这也是`MyISAM`不适合作为主表的引擎。因为写锁后，其他线程不能进行任何操作，大量的写操作会使查询很难得到锁，从而造成永远阻塞。**





### 6.3行锁（偏写）

####  6.3.1特点

偏向`InnoDB`存储引擎，开销大，加锁慢；会出现死锁；锁定粒度最小，发生锁冲突的概率最低，并发度最高。

**`InnoDB`存储引擎和`MyISAM`存储引擎最大不同有两点：一是支持事务，二是采用行锁。**

事务的ACID：

- `Atomicity [ˌætəˈmɪsəti] `。
- `Consistency [kənˈsɪstənsi] `。
- `Isolation [ˌaɪsəˈleɪʃn]`。
- `Durability [ˌdjʊərəˈbɪlɪti] `。



#### 6.3.2建表sql

```mysql
# 建表语句
CREATE TABLE `test_innodb_lock`(
`a` INT,
`b` VARCHAR(16)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='测试行锁'; 

# 插入数据
INSERT INTO `test_innodb_lock`(`a`, `b`) VALUES(1, 'b2');
INSERT INTO `test_innodb_lock`(`a`, `b`) VALUES(2, '3');
INSERT INTO `test_innodb_lock`(`a`, `b`) VALUES(3, '4000');
INSERT INTO `test_innodb_lock`(`a`, `b`) VALUES(4, '5000');
INSERT INTO `test_innodb_lock`(`a`, `b`) VALUES(5, '6000');
INSERT INTO `test_innodb_lock`(`a`, `b`) VALUES(6, '7000');
INSERT INTO `test_innodb_lock`(`a`, `b`) VALUES(7, '8000');
INSERT INTO `test_innodb_lock`(`a`, `b`) VALUES(8, '9000');

# 创建索引
CREATE INDEX idx_test_a ON `test_innodb_lock`(a);
CREATE INDEX idx_test_b ON `test_innodb_lock`(b);
```

#### 6.3.3案列

> 1、开启手动提交

打开`SESSION1`和`SESSION2`两个会话，都开启手动提交。

```mysql
# 开启MySQL数据库的手动提交
mysql> SET autocommit=0;
Query OK, 0 rows affected (0.00 sec)
```

> 2、读几知所写

```mysql
# SESSION1 

# SESSION1対test_innodb_lock表做写操作，但是没有commit。
# 执行修改SQL之后，查询一下test_innodb_lock表，发现数据被修改了。
mysql> UPDATE `test_innodb_lock` SET `b` = '88' WHERE `a` = 1;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> SELECT * FROM `test_innodb_lock`;
+------+------+
| a    | b    |
+------+------+
|    1 | 88   |
|    2 | 3    |
|    3 | 4000 |
|    4 | 5000 |
|    5 | 6000 |
|    6 | 7000 |
|    7 | 8000 |
|    8 | 9000 |
+------+------+
8 rows in set (0.00 sec)

# SESSION2 

# SESSION2这时候来查询test_innodb_lock表。
# 发现SESSION2是读不到SESSION1未提交的数据的。
mysql> SELECT * FROM `test_innodb_lock`;
+------+------+
| a    | b    |
+------+------+
|    1 | b2   |
|    2 | 3    |
|    3 | 4000 |
|    4 | 5000 |
|    5 | 6000 |
|    6 | 7000 |
|    7 | 8000 |
|    8 | 9000 |
+------+------+
8 rows in set (0.00 se
```

> 3、行锁两个SESSION同时対一条记录进行写操作

```mysql
# SESSION1 対test_innodb_lock表的`a`=1这一行进行写操作，但是没有commit
mysql> UPDATE `test_innodb_lock` SET `b` = '99' WHERE `a` = 1;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

# SESSION2 也对test_innodb_lock表的`a`=1这一行进行写操作，但是发现阻塞了！！！
# 等SESSION1执行commit语句之后，SESSION2的SQL就会执行了
mysql> UPDATE `test_innodb_lock` SET `b` = 'asdasd' WHERE `a` = 1;
ERROR 1205 (HY000): Lock wait timeout exceeded; try restarting transaction
```

> 4、行锁两个SESSION同时对不同记录进行写操作

```mysql
# SESSION1 対test_innodb_lock表的`a`=6这一行进行写操作，但是没有commit
mysql> UPDATE `test_innodb_lock` SET `b` = '8976' WHERE `a` = 6;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

# SESSION2 対test_innodb_lock表的`a`=4这一行进行写操作，没有阻塞！！！
# SESSION1和SESSION2同时对不同的行进行写操作互不影响
mysql> UPDATE `test_innodb_lock` SET `b` = 'Ringo' WHERE `a` = 4;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0
```

#### 6.3.4索引失效行锁变表锁

```mysql
# SESSION1 执行SQL语句，没有执行commit。
# 由于`b`字段是字符串，但是没有加单引号导致索引失效
mysql> UPDATE `test_innodb_lock` SET `a` = 888 WHERE `b` = 8000;
Query OK, 1 row affected, 1 warning (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 1

# SESSION2 和SESSION1操作的并不是同一行，但是也被阻塞了？？？
# 由于SESSION1执行的SQL索引失效，导致行锁升级为表锁。
mysql> UPDATE `test_innodb_lock` SET `b` = '1314' WHERE `a` = 1;
ERROR 1205 (HY000): Lock wait timeout exceeded; try restarting transaction
```

6.3.5.间隙锁的危害

> 什么事间隙锁？

当我们用范围条件而不是相等条件检索数据，并请求共享或者排他锁时，`InnoDB`会给符合条件的已有数据记录的索引项加锁，对于键值在条件范文内但并不存在的记录，叫做"间隙(GAP)"。

`InnoDB`也会对这个"间隙"加锁，这种锁的机制就是所谓的"间隙锁"。



> 间隙锁的危害

因为`Query`执行过程中通过范围查找的话，他会锁定整个范围内所有的索引键值，即使这个键值不存在。

间隙锁有一个比较致命的缺点，就是**当锁定一个范围的键值后，即使某些不存在的键值也会被无辜的锁定，而造成在锁定的时候无法插入锁定键值范围内的任何数据。**在某些场景下这可能会対性能造成很大的危害。



#### 6.3.5 如何锁定一行

![锁定一行](https://camo.githubusercontent.com/e5a381130dc0fdc8155bd9459dede1fc8598913dcd6aeabc7fa7b6c36d6dce24/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f323032303038303631363035303335352e706e673f782d6f73732d70726f636573733d696d6167652f77617465726d61726b2c747970655f5a6d46755a33706f5a57356e6147567064476b2c736861646f775f31302c746578745f6148523063484d364c7939696247396e4c6d4e7a5a473475626d56304c314a796157356e6231383d2c73697a655f31362c636f6c6f725f4646464646462c745f3730)



`SELECT .....FOR UPDATE`在锁定某一行后，其他写操作会被阻塞，直到锁定的行被`COMMIT`。

mysql InnoDB引擎默认的修改数据语句，update,delete,insert都会自动给涉及到的数据加上排他锁，select语句默认不会加任何锁类型，如果加排他锁可以使用select ...for update语句，加共享锁可以使用select ... lock in share mode语句。所以加过排他锁的数据行在其他事务种是不能修改数据的，也不能通过for update和lock in share mode锁的方式查询数据，但可以直接通过select ...from...查询数据，因为普通查询没有任何锁机制。



#### 6.3.6**案列结论：**

`InnoDB`存储引擎由于实现了行级锁定，虽然在锁定机制的实现方面所带来的性能损耗可能比表级锁定会要更高一些，但是在整体并发处理能力方面要远远优于`MyISAM`的表级锁定的。当系统并发量较高的时候，`InnoDB`的整体性能和`MyISAM`相比就会有比较明显的优势了。

但是，`InnoDB`的行级锁定同样也有其脆弱的一面，当我们使用不当的时候，可能会让`InnoDB`的整体性能表现不仅不能比`MyISAM`高，甚至可能会更差。



#### 6.3.7行锁分析

```mysql
mysql> SHOW STATUS LIKE 'innodb_row_lock%';
+-------------------------------+--------+
| Variable_name                 | Value  |
+-------------------------------+--------+
| Innodb_row_lock_current_waits | 0      |
| Innodb_row_lock_time          | 124150 |
| Innodb_row_lock_time_avg      | 31037  |
| Innodb_row_lock_time_max      | 51004  |
| Innodb_row_lock_waits         | 4      |
+-------------------------------+--------+
5 rows in set (0.00 sec)
```

対各个状态量的说明如下：

- `Innodb_row_lock_current_waits`：当前正在等待锁定的数量。
- `Innodb_row_lock_time`：从系统启动到现在锁定总时间长度（重要）。
- `Innodb_row_lock_time_avg`：每次等待所花的平均时间（重要）。
- `Innodb_row_lock_time_max`：从系统启动到现在等待最长的一次所花的时间。
- `Innodb_row_lock_waits`：系统启动后到现在总共等待的次数（重要）。

尤其是当等待次数很高，而且每次等待时长也不小的时候，我们就需要分析系统中为什么会有如此多的等待，然后根据分析结果着手制定优化策略。

#### 6.3.8优化建议

尽可能让所有数据检索都通过索引来完成,避免无索引行锁升级为表锁。

合理设计索引,尽量缩小锁的范围

尽可能较少检索条件,避免间隙锁

尽量控制事务大小,减少锁定资源量和时间长度

尽可能低级别事务隔离



### 6.4页锁

了解即可。

开销和加锁时间界于表锁和行锁之间;会出现死锁;锁定粒度界于表锁和行锁之间,并发度一般。





## 7、主从复制

### 7.1原理

![主从复制](https://camo.githubusercontent.com/640beeb0ebd17d217b5d3b6902d353fab0d718cc0d674979c11cbe8e5b2275b1/68747470733a2f2f696d672d626c6f672e6373646e696d672e636e2f32303230303830363137303431353430312e706e673f782d6f73732d70726f636573733d696d6167652f77617465726d61726b2c747970655f5a6d46755a33706f5a57356e6147567064476b2c736861646f775f31302c746578745f6148523063484d364c7939696247396e4c6d4e7a5a473475626d56304c314a796157356e6231383d2c73697a655f31362c636f6c6f725f4646464646462c745f3730)

MySQL复制过程分为三步：

- Master将改变记录到二进制日志(Binary Log)。这些记录过程叫做二进制日志事件，`Binary Log Events`；
- Slave将Master的`Binary Log Events`拷贝到它的中继日志(Replay Log);
- Slave重做中继日志中的事件，将改变应用到自己的数据库中。MySQL复制是异步且串行化的。



### 7.2复制的基本原则

- 每个Slave只有一个Master。
- 每个Slave只能有一个唯一的服务器ID。
- 每个Master可以有多个Salve。



注意事项：

- 主从服务器操作系统版本和位数一致；
- Master 和 Slave 数据库的版本要一致；
- Master 和 Slave 数据库中的数据要一致；
- Master 开启二进制日志， Master 和 Slave 的 server_id 在局域网内必须唯一；



### 7.3一主二从配置

特别提醒：

先在第一个主机上安装mysql环境 然后再克隆另外两个。

克隆的时候要注意修改server-id

还有mysql的uuid  在data目录下 需要修改唯一





> 1、基本要求：Master和Slave的MySQL服务器版本一致且后台以服务运行。



> 2、主从配置都是配在[mysqld]节点下，都是小写

```mysql
# Master配置
[mysqld]
#主服务器唯一ID
server-id=1 # 必须
#二进制日志
log-bin=/usr/local/mysql/data/mysql-bin # 必须
#主机 0 读写都可以
read-only=0
#设置不要复制的数据库
binlog-ignore-db=mysql  #可选
#设置要复制的数据库
#binlog-do-ddb=数据库名称
```



```mysql
# Slave配置
[mysqld]
server-id=2 # 必须
log-bin=/var/lib/mysql/mysql-bin
```

> 3、Master配置

```mysql
# 1、GRANT REPLICATION SLAVE ON *.* TO 'username'@'从机IP地址' IDENTIFIED BY 'password';
mysql> GRANT REPLICATION SLAVE ON *.* TO 'zhangsan'@'172.18.0.3' IDENTIFIED BY '123456';
Query OK, 0 rows affected, 1 warning (0.01 sec)

# 2、刷新命令
mysql> FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.00 sec)

# 3、记录下File和Position
# 每次配从机的时候都要SHOW MASTER STATUS;查看最新的File和Position
mysql> SHOW MASTER STATUS;
+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000001 |      602 |              | mysql            |                   |
+------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```

> 4、Slave从机配置

```mysql
CHANGE MASTER TO MASTER_HOST='172.18.0.4',
MASTER_USER='zhangsan',
MASTER_PASSWORD='123456',
MASTER_LOG_FILE='mysql-bin.File的编号',
MASTER_LOG_POS=Position的最新值;
```

```mysql
# 1、使用用户名密码登录进Master
mysql> CHANGE MASTER TO MASTER_HOST='172.18.0.4',
    -> MASTER_USER='zhangsan',
    -> MASTER_PASSWORD='123456',
    -> MASTER_LOG_FILE='mysql-bin.000001',
    -> MASTER_LOG_POS=602;
Query OK, 0 rows affected, 2 warnings (0.02 sec)

# 2、开启Slave从机的复制
mysql> START SLAVE;
Query OK, 0 rows affected (0.00 sec)

# 3、查看Slave状态
# Slave_IO_Running 和 Slave_SQL_Running 必须同时为Yes 说明主从复制配置成功！
mysql> SHOW SLAVE STATUS\G
*************************** 1. row ***************************
               Slave_IO_State: Waiting for master to send event # Slave待命状态
                  Master_Host: 172.18.0.4
                  Master_User: zhangsan
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql-bin.000001
          Read_Master_Log_Pos: 602
               Relay_Log_File: b030ad25d5fe-relay-bin.000002
                Relay_Log_Pos: 320
        Relay_Master_Log_File: mysql-bin.000001
             Slave_IO_Running: Yes  
            Slave_SQL_Running: Yes
              Replicate_Do_DB: 
          Replicate_Ignore_DB: 
           Replicate_Do_Table: 
       Replicate_Ignore_Table: 
      Replicate_Wild_Do_Table: 
  Replicate_Wild_Ignore_Table: 
                   Last_Errno: 0
                   Last_Error: 
                 Skip_Counter: 0
          Exec_Master_Log_Pos: 602
              Relay_Log_Space: 534
              Until_Condition: None
               Until_Log_File: 
                Until_Log_Pos: 0
           Master_SSL_Allowed: No
           Master_SSL_CA_File: 
           Master_SSL_CA_Path: 
              Master_SSL_Cert: 
            Master_SSL_Cipher: 
               Master_SSL_Key: 
        Seconds_Behind_Master: 0
Master_SSL_Verify_Server_Cert: No
                Last_IO_Errno: 0
                Last_IO_Error: 
               Last_SQL_Errno: 0
               Last_SQL_Error: 
  Replicate_Ignore_Server_Ids: 
             Master_Server_Id: 1
                  Master_UUID: bd047557-b20c-11ea-9961-0242ac120002
             Master_Info_File: /var/lib/mysql/master.info
                    SQL_Delay: 0
          SQL_Remaining_Delay: NULL
      Slave_SQL_Running_State: Slave has read all relay log; waiting for more updates
           Master_Retry_Count: 86400
                  Master_Bind: 
      Last_IO_Error_Timestamp: 
     Last_SQL_Error_Timestamp: 
               Master_SSL_Crl: 
           Master_SSL_Crlpath: 
           Retrieved_Gtid_Set: 
            Executed_Gtid_Set: 
                Auto_Position: 0
         Replicate_Rewrite_DB: 
                 Channel_Name: 
           Master_TLS_Version: 
1 row in set (0.00 sec)
```

> 5、测试主从复制

```
# Master创建数据库
mysql> create database test_replication;
Query OK, 1 row affected (0.01 sec)

# Slave查询数据库
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
| test_replication   |
+--------------------+
5 rows in set (0.00 sec)
```

> 6、停止主从复制功能

```mysql
# 1、停止Slave
mysql> STOP SLAVE;
Query OK, 0 rows affected (0.00 sec)

# 2、重新配置主从
# MASTER_LOG_FILE 和 MASTER_LOG_POS一定要根据最新的数据来配
mysql> CHANGE MASTER TO MASTER_HOST='172.18.0.4',
    -> MASTER_USER='zhangsan',
    -> MASTER_PASSWORD='123456',
    -> MASTER_LOG_FILE='mysql-bin.000001',
    -> MASTER_LOG_POS=797;
Query OK, 0 rows affected, 2 warnings (0.01 sec)

mysql> START SLAVE;
Query OK, 0 rows affected (0.00 sec)

mysql> SHOW SLAVE STATUS\G
*************************** 1. row ***************************
               Slave_IO_State: Waiting for master to send event
                  Master_Host: 172.18.0.4
                  Master_User: zhangsan
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql-bin.000001
          Read_Master_Log_Pos: 797
               Relay_Log_File: b030ad25d5fe-relay-bin.000002
                Relay_Log_Pos: 320
        Relay_Master_Log_File: mysql-bin.000001
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
              Replicate_Do_DB: 
          Replicate_Ignore_DB: 
           Replicate_Do_Table: 
       Replicate_Ignore_Table: 
      Replicate_Wild_Do_Table: 
  Replicate_Wild_Ignore_Table: 
                   Last_Errno: 0
                   Last_Error: 
                 Skip_Counter: 0
          Exec_Master_Log_Pos: 797
              Relay_Log_Space: 534
              Until_Condition: None
               Until_Log_File: 
                Until_Log_Pos: 0
           Master_SSL_Allowed: No
           Master_SSL_CA_File: 
           Master_SSL_CA_Path: 
              Master_SSL_Cert: 
            Master_SSL_Cipher: 
               Master_SSL_Key: 
        Seconds_Behind_Master: 0
Master_SSL_Verify_Server_Cert: No
                Last_IO_Errno: 0
                Last_IO_Error: 
               Last_SQL_Errno: 0
               Last_SQL_Error: 
  Replicate_Ignore_Server_Ids: 
             Master_Server_Id: 1
                  Master_UUID: bd047557-b20c-11ea-9961-0242ac120002
             Master_Info_File: /var/lib/mysql/master.info
                    SQL_Delay: 0
          SQL_Remaining_Delay: NULL
      Slave_SQL_Running_State: Slave has read all relay log; waiting for more updates
           Master_Retry_Count: 86400
                  Master_Bind: 
      Last_IO_Error_Timestamp: 
     Last_SQL_Error_Timestamp: 
               Master_SSL_Crl: 
           Master_SSL_Crlpath: 
           Retrieved_Gtid_Set: 
            Executed_Gtid_Set: 
                Auto_Position: 0
         Replicate_Rewrite_DB: 
                 Channel_Name: 
           Master_TLS_Version: 
1 row in set (0.00 sec)
```

> 参考笔记

[https://github.com/RingoTangs/LearningNote/blob/master/MySQL/MySQL.md](https://github.com/RingoTangs/LearningNote/blob/master/MySQL/MySQL.md)

[https://www.cnblogs.com/dwdw/p/9928181.html](https://www.cnblogs.com/dwdw/p/9928181.html)



```mysql
GRANT REPLICATION SLAVE ON *.* TO 'slaveOne'@'192.168.31.126' IDENTIFIED BY 'admin';
grant replication slave,replication client on *.* to 'slaveOne'@'192.168.31.126' identified by 'admin';

CREATE USER 'slaveOne'@'192.168.31.126' IDENTIFIED WITH mysql_native_password BY 'admin';
GRANT REPLICATION SLAVE ON *.* TO  'slaveOne'@'192.168.31.126';


CREATE USER 'slaveTwo'@'192.168.31.125' IDENTIFIED WITH mysql_native_password BY 'admin';
GRANT REPLICATION SLAVE ON *.* TO  'slaveTwo'@'192.168.31.125' ;




CHANGE MASTER TO MASTER_HOST='192.168.31.127',
MASTER_USER='slaveTwo',
MASTER_PASSWORD='admin',
MASTER_LOG_FILE='mysql-bin.000005',
MASTER_LOG_POS=9739;
```



