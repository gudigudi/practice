
## 数据库开发规范
* 基本命名&&约束规范
* 字段设计规范
* 索引规范
* 使用规范

### 基本命名&&约束规范
* 表字符集选择UTF8,如果需要存储emoji表情,需要使用UTF8mb4(mysql5.5.3+)
* 存储引擎使用InnoDB
* 不定长字符串使用varchar/varbinary
* 不在数据库中存储图片/文件
* 单表数据量控制在1亿以下
* 库名/表名/字段名不使用保留字,采用小写下划线格式
* 库名/表名不要过长

### 字段规范
* 所有字段均定义为`NOT NULL`,除非一定要定义为`NULL`
* 在满足条件下,字段类型越小越好,除非存储负数,否则都用`UNSIGNED`
* 使用`UNSIGNED INT`存储IPv4,为了兼容IPV6
* 使用`DECIMAL`存储浮点数

### 索引规范
* 单个索引字段数不超过5,单表索引数量不超过5
* 选择区分度高德列作为索引
* DML和`order by`和`group by`字段要建立合适的索引

### 使用规范
* 尽量不使用存储过程/触发器/函数等
* 避免使用大表的JOIN
* 避免在数据库中进行数学运算和其他大量计算任务
* SQL合并,合并多个value,减少数据库交互
* 合理分页

## 数据库运维规范
* SQL审核,DDL审核和操作时间,尤其是OnlineDDL
* 高危操作检查,Drop前做好数据备份
* 权限控制和审计
* 日志分析,Mysql慢日志/错误日志
* 高可用方案
* 数据备份方案

### InnoDB优化
* innodbfileper_table=1
* innodbbufferpool_size: 根据数据量和内存合理设置
* innodbflushlog_attrxcommit=0 1 2
* innodblogfile_size: 设置大点
* innodbpagesize
* innodbflushmetod=o_direct
* innodbundodirectory: 放在高速设备上
* innodbbufferpool_dump
* atshutdown,bufferpool dump