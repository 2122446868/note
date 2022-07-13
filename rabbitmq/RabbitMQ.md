## RabbitMQ

#### 什么是消息队列？

MQ全称为Message Queue，即消息队列。“消息队列”是在消息的传输过程中保存消息的容器。它是典型的：生产者、消费者模型。生产者不断向消息队列中生产消息，消费者不断的从队列中获取消息。因为消息的生产和消费都是异步的，而且只关心消息的发送和接收，没有业务逻辑的侵入，这样就实现了生产者和消费者的解耦。

#### AMQP和JMS

- MQ是消息通信的模型，并发具体实现。现在实现MQ的有两种主流方式：AMQP、JMS。
- 两者间的区别和联系：

- JMS是定义了统一的接口，来对消息操作进行统一；AMQP是通过规定协议来统一数据交互的格式

- JMS限定了必须使用Java语言；AMQP只是协议，不规定实现方式，因此是跨语言的。

- JMS规定了两种消息模型；而AMQP的消息模型更加丰富

#### 常见的消息MQ

- ActiveMQ：基于JMS
- RabbitMQ：基于AMQP协议，erlang语言开发，稳定性好
- RocketMQ：基于JMS，阿里巴巴产品，目前交由Apache基金会
- Kafka：分布式消息系统，高吞吐量



#### RabbitMQ安装

[官网下载地址](https://rabbitmq.com/download.html)

```
#安装Erlange依赖
rpm -ivh erlang-22.0.7-1.el7.x86_64.rpm

#查看是否安装成功
erl

#查看版本
erl -v

#安装RabbitMQ安装包
yum install -y rabbitmq-server-3.7.18-1.el7.noarch.rpm

#启动服务
systemctl start rabbitmq-server
#查看状态
systemctl status rabbitmq-server
#设置开机自启
systemctl enable rabbitmq-server
```

#### 访问web控制台

```
#安装rabbitmq插件管理 开启WEB可视化管理插件
rabbitmq-plugins enable rabbitmq_management

#关闭防火墙 如果是阿里云服务器安全组添加端口放行 端口：15672
systemctl stop firewalld

#默认用户guest只允许本机访
username:guest
password:guest

#添加用户用于访问
rabbitmqctl add_user admin admin
#授予用户管理员权限
rabbitmqctl set_user_tags admin administrator

# 访问web管理界面
http://ip/15672

```

#### RabbitMQ支持的消息模型

##### 第一种模型：直连

![1](https://img-blog.csdnimg.cn/20200722115549863.png)



在上图的模型中，有以下概念：

- P：生产者，也就是要发送消息的程序
- C：消费者：消息的接受者，会一直等待消息到来。
- queue：消息队列，图中红色部分。类似一个邮箱，可以缓存消息；生产者向其中投递消息，消费者从其中取出消息。



1、开发生产者

```java
//        创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
//      设置连接rabbitmq主机
        connectionFactory.setHost("8.136.219.183");
//        设置端口号
        connectionFactory.setPort(5672);
//        设置连接那个虚拟机
        connectionFactory.setVirtualHost("/emp");
//       设置访问须虚拟机的用户名和密码
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
//       获取连接对象
        Connection connection = connectionFactory.newConnection();
//        获取连接中的通道
        Channel channel = connection.createChannel();
//        通道绑定对应的消息队列
//        参数1：队列名称，如果队列不存在自动创建
//        参数2：用来定义队列特性是否要持久化 true  持久化 false 不持久化
//        参数3:是否独占队列
//        参数4:是否自动删除
//        参数5:其他属性
        channel.queueDeclare("helloRabbitMQ", false, false, false, null);

//        发布消息
//        参数1：交换机名称  参数2：队列名称 参数3：传递消息额外设置， 参数4：消息的具体内容
        channel.basicPublish("", "helloRabbitMQ", null, "hello rabbiitmq".getBytes());
        channel.close();
        connection.close();
```

2、开发消费者

```java
   public static void main(String[] args) throws IOException, TimeoutException {

        //        创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
//      设置连接rabbitmq主机
        connectionFactory.setHost("8.136.219.183");
//        设置端口号
        connectionFactory.setPort(5672);
//        设置连接那个虚拟机
        connectionFactory.setVirtualHost("/emp");
//       设置访问须虚拟机的用户名和密码
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
//       获取连接对象
        Connection connection = connectionFactory.newConnection();
//        获取连接中的通道
        Channel channel = connection.createChannel();
        //        通道绑定对应的消息队列
//        参数1：队列名称，如果队列不存在自动创建
//        参数2：用来定义队列特性是否要持久化 true  持久化 false 不持久化
//        参数3:是否独占队列
//        参数4:是否自动删除
//        参数5:其他属性
        channel.queueDeclare("helloRabbitMQ", false, false, false, null);
//        消费消息
//        参数1：消费那个队列的消息 队列名称
//        参数2：开始小的自动确认机制
//        参数3：消费时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("====" + new String(body));
            }
        });
//        channel.close();
//        connection.close();


    }

```



##### 第二种模型：work queue

`Work queues`，也被称为（`Task queues`），任务模型。当消息处理比较耗时的时候，可能生产消息的速度会远远大于消息的消费速度。长此以往，消息就会堆积越来越多，无法及时处理。此时就可以使用work 模型：**让多个消费者绑定到一个队列，共同消费队列中的消息**。队列中的消息一旦消费，就会消失，因此任务是不会被重复执行的。

![å¨è¿éæå¥å¾çæè¿°](https://img-blog.csdnimg.cn/20200722115619587.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70) 

角色：

- P：生产者：任务的发布者
- C1：消费者-1，领取任务并且完成任务，假设完成速度较慢
- C2：消费者-2：领取任务并完成任务，假设完成速度快

1.开发生产者

```java
//        获取连接(工具类)
        Connection connection = RabbitMQUtils.getConnection();
//        获取通达
        Channel channel = connection.createChannel();
//        道绑定对应的消息队列
        channel.queueDeclare("workqueque", true, false, true, null);
        for (int i = 1; i <= 20; i++) {

//        发布消息
            channel.basicPublish("", "workqueque", null, (i+"hello work queque") .getBytes());
        }

        RabbitMQUtils.closeConnectionAndChannel(channel, connection);

    }

```

2.开发消费者-1

```java
  Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("workqueque", true, false, true, null);
        channel.basicConsume("workqueque", true, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Customer1:" + new String(body));
            }
        });
    }
```

3.开发消费者-2

```java
 Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("workqueque", true, false,  true, null);
        channel.basicConsume("workqueque", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Customer2:" + new String(body));
            }
        });
    }
```

**总结：默认情况下，RabbitMQ将按顺序将每个消息发送给下一个使用者。平均而言，每个消费者都会收到相同数量的消息。这种分发消息的方式称为循环。**

4.消息自动确认机制  
能做多劳，速度快的多处理一些
一次性只接受一条未确认的消息

```java
  Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
//        同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(1);
        channel.queueDeclare("workqueque", true, false, true, null);
        channel.basicConsume("workqueque", false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Consumer1：" + new String(body));
//                手动确认：参数1：手动确认消息标志  餐数2：false 每次确认一个
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

```

##### 第三种模型：fanout

fanout 扇出 也成为广播

![img](https://img-blog.csdnimg.cn/20200722115846461.png)

在广播模式下，消息发送流程是这样的：

- 可以有多个消费者
- 每个**消费者有自己的queue**（队列）
- 每个**队列都要绑定到Exchange**（交换机）
- **生产者发送的消息，只能发送到交换机**，交换机来决定要发给哪个队列，生产者无法决定。
- 交换机把消息发送给绑定过的所有队列
- 队列的消费者都能拿到消息。实现一条消息被多个消费者消费

1.开发生产者

```java
	//        获取连接
        Connection connection = RabbitMQUtils.getConnection();
//        创建通道
        Channel channel = connection.createChannel();
//        声明交换机
        channel.exchangeDeclare("logs", "fanout");
//        发布消息
        channel.basicPublish("logs", "", null, "hello".getBytes());
//        释放资源
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
```

2.开发消费者-1

```java
//        获取连接
        Connection connection = RabbitMQUtils.getConnection();
//        创建通道
        Channel channel = connection.createChannel();
//        创建临时队列
        String queue = channel.queueDeclare().getQueue();
        //将临时队列绑定exchange
        channel.queueBind(queue, "logs", "");
//        消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body));
            }
        });

```

3.开发消费者-2

````java
//        获取连接
        Connection connection = RabbitMQUtils.getConnection();
//        创建通道
        Channel channel = connection.createChannel();
//        创建临时队列
        String queue = channel.queueDeclare().getQueue();
        //将临时队列绑定exchange
        channel.queueBind(queue, "logs", "");
//        消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Consumer2: " + new String(body));
            }
        });

````

##### 第四种模型：Routing

1.Routing 之订阅模式-Direct(直连)

在Fanout模式中，一条消息，会被所有订阅的队列都消费。但是，在某些场景下，我们希望不同的消息被不同的队列消费。这时就要用到Direct类型的Exchange。

在Direct模型下：

- 队列与交换机的绑定，不能是任意绑定了，而是要指定一个`RoutingKey`（路由key）

- 消息的发送方在 向 Exchange发送消息时，也必须指定消息的 `RoutingKey`。

- Exchange不再把消息交给每一个绑定的队列，而是根据消息的`Routing Key`进行判断，只有队列的`Routingkey`与消息的 `Routing key`完全一致，才会接收到消息

  

  流程:

![å¨è¿éæå¥å¾çæè¿°](https://img-blog.csdnimg.cn/20200722120027979.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)

图解：

- P：生产者，向Exchange发送消息，发送消息时，会指定一个routing key。
- X：Exchange（交换机），接收生产者的消息，然后把消息递交给 与routing key完全匹配的队列
- C1：消费者，其所在队列指定了需要routing key 为 error 的消息
- C2：消费者，其所在队列指定了需要routing key 为 info、error、warning 的消息

代码：

1.1开发生产者

```java
//        获取连接
        Connection connection = RabbitMQUtils.getConnection();
//        创建通道
        Channel channel = connection.createChannel();
//       声明交换机
        channel.exchangeDeclare("logs_direct", "direct");
//      路由
        String routingKey = "error";
//        发布消息
        channel.basicPublish("logs_direct", routingKey, null, ("制定的 routingKey:" + routingKey + "的消息").getBytes());
//       释放资源
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }
```

1.2 开发消费者-1

```java
  Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
		//	声明交换机
        channel.exchangeDeclare("logs_direct", "direct");
		// 创建临时队列
        String queue = channel.queueDeclare().getQueue();
		// 绑定队列和交换机和路由
        channel.queueBind(queue, "logs_direct", "info");
		// 消费
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body));
            }
        });

```

1.3 开发消费者-2

```java
 Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs_direct", "direct");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "logs_direct", "error");
        channel.queueBind(queue, "logs_direct", "wrong");
        channel.queueBind(queue, "logs_direct", "info");
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2: " + new String(body));
            }
        });
```

##### 第五种模式：Topics

2.Routing 之订阅模型-Topic

Topic类型的Exchange与Direct相比，都是可以根据RoutingKey把消息路由到不同的队列。只不过Topic类型Exchange可以让队列在绑定Routing key 的时候使用通配符！这种模型Routingkey 一般都是由一个或多个单词组成，多个单词之间以”.”分割，例如： item.insert

![å¨è¿éæå¥å¾çæè¿°](https://img-blog.csdnimg.cn/20200722120218489.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)

```java
# 统配符
        * (star) can substitute for exactly one word.    匹配不多不少恰好1个词
        # (hash) can substitute for zero or more words.  匹配一个或多个词
# 如:
        audit.#    匹配audit.irs.corporate或者 audit.irs 等
    audit.*   只能匹配 audit.irs
```

2.1开发生产者

```
   Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
//        声明交换机  和交换机类型(通配符方式)
        channel.exchangeDeclare("topics", "topic");
//        动态路由
        String routingKey = "user.save";
        channel.basicPublish("topics", routingKey, null, ("这是路由中的动态订阅模型,route key: [" + routingKey + "]").getBytes());
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
```

2.2 开发消费者-1

Routing Key中使用*通配符方式

```java
     Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //        声明交换机  和交换机类型(通配符方式)
        channel.exchangeDeclare("topics", "topic");
//        创建临时队列
        String queue = channel.queueDeclare().getQueue();
//       绑定队列与交换机并设置获取交换机中动态路由
        channel.queueBind(queue, "topics", "user.*");
//        消费
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body));
            }
        });
```

2.3 开发消费者-2

Routing Key中使用#通配符方式

```java
    Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //        声明交换机  和交换机类型(通配符方式)
        channel.exchangeDeclare("topics", "topic");
//        创建临时队列
        String queue = channel.queueDeclare().getQueue();
//       绑定队列与交换机并设置获取交换机中动态路由
        channel.queueBind(queue, "topics", "user.#");
//        消费
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2: " + new String(body));
            }
        });
```



#### SpringBoot整合RabbitMQ

##### 1. 引入依赖

```java
<dependency> 
    <groupId>org.springframework.boot</groupId> 
    <artifactId>spring-boot-starter-amqp</artifactId>
 </dependency>
```

##### 2. 配置配置文件

```java
spring:
  rabbitmq:
    host: 8.136.219.183
    port: 5672
    virtual-host: /emp
    username: admin
    password: admin
```

`RabbitTemplate` 用来简化操作 使用时候直接在项目中注入即可使用

###### 第一种模型 hello word

1. ##### 开发生产者

   ```java
   @Autowired
   private RabbitTemplate rabbitTemplate;
   @Test
   public void testHello(){ 
       rabbitTemplate.convertAndSend("hello","hello world");
   }
   ```

2. ##### 开发消费者

   ```java
   @Component
   @RabbitListener(queuesToDeclare = @Queue("hello"))
   public class HelloCustomer {
       @RabbitHandler
       public void receive1(String message){
           System.out.println("message = " + message);
       }
   }
   ```

###### 第二种work模型

开发生产者

```java
@Autowired
private RabbitTemplate rabbitTemplate;
@Test
public void testWork(){
  for (int i = 0; i < 10; i++) {
    rabbitTemplate.convertAndSend("work","hello work!");
  }
}
```

开发消费者

```java
@Component
public class WorkCustomer {
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receive1(String message){
        System.out.println("work message1 = " + message);
    }
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receive2(String message){
        System.out.println("work message2 = " + message);
    }
}
```

说明:默认在Spring AMQP实现中Work这种方式就是公平调度,如果需要实现能者多劳需要额外配置



###### 第三种fanout广播模型

开发生产者

````java
@Autowired
private RabbitTemplate rabbitTemplate;
@Test
public void testFanout() throws InterruptedException {
  rabbitTemplate.convertAndSend("logs","","这是日志广播");
}
````

开发消费者

```java
@Component
public class FanoutCustomer {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name="logs",type = "fanout")
    ))
    public void receive1(String message){
        System.out.println("message1 = " + message);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, //创建临时队列
            exchange = @Exchange(name="logs",type = "fanout")  //绑定交换机类型
    ))
    public void receive2(String message){
        System.out.println("message2 = " + message);
    }
}
```

###### 第四种route 路由模式

开放生产者

```java
@Autowired
private RabbitTemplate rabbitTemplate;
@Test
public void testDirect(){
  rabbitTemplate.convertAndSend("directs","error","error 的日志信息");
}
```

开发消费者

```java
@Component
public class DirectCustomer {
    @RabbitListener(bindings ={
            @QueueBinding(
                    value = @Queue(),
                    key={"info","error"},
                    exchange = @Exchange(type = "direct",name="directs")
            )})
    public void receive1(String message){
        System.out.println("message1 = " + message);
    }
    @RabbitListener(bindings ={
            @QueueBinding(
                    value = @Queue(),
                    key={"error"},
                    exchange = @Exchange(type = "direct",name="directs")
            )})
    public void receive2(String message){
        System.out.println("message2 = " + message);
    }
}
```

###### 第五种Topic订阅模式（动态路由模型）

开发生产者

```java
@Autowired
private RabbitTemplate rabbitTemplate;
//topic
@Test
public void testTopic(){
  rabbitTemplate.convertAndSend("topics","user.save.findAll","user.save.findAll 的消息");
}
```

开发消费者

```java
@Component
public class TopCustomer {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    key = {"user.*"},
                    exchange = @Exchange(type = "topic",name = "topics")
            )
    })
    public void receive1(String message){
        System.out.println("message1 = " + message);
    }
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    key = {"user.#"},
                    exchange = @Exchange(type = "topic",name = "topics")
            )
    })
    public void receive2(String message){
        System.out.println("message2 = " + message);
    }
}
```

#### 应用场景

#### 1 异步处理

场景说明：用户注册后，需要发注册邮件和注册短信,传统的做法有两种 1.串行的方式 2.并行的方式

- `串行方式:` 将注册信息写入数据库后,发送注册邮件,再发送注册短信,以上三个任务全部完成后才返回给客户端。 这有一个问题是,邮件,短信并不是必须的,它只是一个通知,而这种做法让客户端等待没有必要等待的东西.
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200722120351880.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)

- `并行方式:`将注册信息写入数据库后,发送邮件的同时,发送短信,以上三个任务完成后,返回给客户端,并行的方式能提高处理的时间。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200722120420855.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)

- `消息队列:`假设三个业务节点分别使用50ms,串行方式使用时间150ms,并行使用时间100ms。虽然并行已经提高的处理时间,但是,前面说过,邮件和短信对我正常的使用网站没有任何影响，客户端没有必要等着其发送完成才显示注册成功,应该是写入数据库后就返回. `消息队列`: 引入消息队列后，把发送邮件,短信不是必须的业务逻辑异步处理

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200722120447269.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)

由此可以看出,引入消息队列后，用户的响应时间就等于写入数据库的时间+写入消息队列的时间(可以忽略不计),引入消息队列后处理后,响应时间是串行的3倍,是并行的2倍。

#### 2 应用解耦

```
场景：双11是购物狂节,用户下单后,订单系统需要通知库存系统,传统的做法就是订单系统调用库存系统的接口.
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200722120508196.png)

这种做法有一个缺点:

当库存系统出现故障时,订单就会失败。 订单系统和库存系统高耦合. 引入消息队列

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200722120524716.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)

- `订单系统:`用户下单后,订单系统完成持久化处理,将消息写入消息队列,返回用户订单下单成功。
- `库存系统:`订阅下单的消息,获取下单消息,进行库操作。 就算库存系统出现故障,消息队列也能保证消息的可靠投递,不会导致消息丢失.

#### 3 流量削峰

`场景:` 秒杀活动，一般会因为流量过大，导致应用挂掉,为了解决这个问题，一般在应用前端加入消息队列。

```
作用:
```

 1.可以控制活动人数，超过此一定阀值的订单直接丢弃(我为什么秒杀一次都没有成功过呢^^)

 2.可以缓解短时间的高流量压垮应用(应用程序按自己的最大处理能力获取订单)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200722120542324.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)

1.用户的请求,服务器收到之后,首先写入消息队列,加入消息队列长度超过最大值,则直接抛弃用户请求或跳转到错误页面.

2.秒杀业务根据消息队列中的请求信息，再做后续处理.





#### RabbitMQ集群

##### 集群架构

###### 1.普通集群（副本架构）

默认情况下:RabbitMQ代理操作所需的所有数据/状态都将跨所有节点复制。这方面的一个例外是消息队列，默认情况下，消息队列位于一个节点上，尽管它们可以从所有节点看到和访问

架构图

![å¨è¿éæå¥å¾çæè¿°](https://img-blog.csdnimg.cn/20200722120559889.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)



核心解决问题：当集群中某一时刻master节点宕机,可以对Quene中信息,进行备份



###### 2.镜像集群

镜像队列机制就是将队列在三个节点之间设置主从关系，消息会在三个节点之间进行自动同步，且如果其中一个节点不可用，并不会导致消息丢失或服务不可用的情况，提升MQ集群的整体高可用性。

架构图

![å¨è¿éæå¥å¾çæè¿°](https://img-blog.csdnimg.cn/20200722120818256.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTI1Njky,size_16,color_FFFFFF,t_70)



##### 集群搭建



###### 1.单机多节点集群

1.1环境准备

装备一台安装好RabbitMQ的机器

1.2启动前，先修改RabbitMQ的默认节点名（非必要）

vim /etc/rabbitmq/rabbitmq-env.conf  增加内容

```SH
# RabbitMQ 默认节点名，默认是rabbit 
NODENAME=rabbit1
```

1.3 RabbitMQ 默认是使用服务的启动的，单机多节点时需要改为手动启动，先停止运行中的RabbitMQ 服务

```shell
systemctl stop rabbitmq-server
```

1.4 启动第一个节点

```shell
rabbitmq-server -detached
```

1.5 启动第二个节点

```shell
RABBITMQ_NODE_PORT=5673 RABBITMQ_SERVER_START_ARGS="-rabbitmq_management listener [{port,15673}]" RABBITMQ_NODENAME=rabbit2 rabbitmq-server -detached
```

1.6 启动第三个节点

```shell
RABBITMQ_NODE_PORT=5674 RABBITMQ_SERVER_START_ARGS="-rabbitmq_management listener [{port,15674}]" RABBITMQ_NODENAME=rabbit3 rabbitmq-server -detached
```

1.7 将rabbit2加入到集群

```shell
# 停止 rabbit2 的应用
rabbitmqctl -n rabbit2 stop_app
# 重置 rabbit2 的设置
rabbitmqctl -n rabbit2 reset
# rabbit2 节点加入到 rabbit1的集群中
rabbitmqctl -n rabbit2 join_cluster rabbit1 --ram
# 启动 rabbit2 节点
rabbitmqctl -n rabbit2 start_app
```

1.8 将rabbit3加入到集群

```shell
# 停止 rabbit3 的应用
rabbitmqctl -n rabbit3 stop_app
# 重置 rabbit3 的设置
rabbitmqctl -n rabbit3 reset
# rabbit3 节点加入到 rabbit1的集群中
rabbitmqctl -n rabbit3 join_cluster rabbit1 --ram
# 启动 rabbit3 节点
rabbitmqctl -n rabbit3 start_app
```

1.9查看集群状态

**`提示：在管理界面可以更直观的看到集群信息`**

```shell
rabbitmqctl cluster_status
```





###### 2.多机多节点集群

2.1环境准备

准备好三台机器

    node1: 10.15.0.3  mq1  master 主节点
    node2: 10.15.0.4  mq2  repl1  副本节点
    node3: 10.15.0.5  mq3  repl2  副本节点

**`提示：如果使用虚拟机，可以在一台VM上安装好RabbitMQ后，创建快照，从快照创建链接克隆，会节省很多磁盘空间`**

2.2 添加三台机器主机名和ip映射

修改 `10.15.0.3 /etc/hosts`文件

```shell
vim /etc/hosts 加入
10.15.0.3 mq1
10.15.0.4 mq2
10.15.0.5 mq3
```

2.3将10.15.0.3 /etc/hosts文件同步到另外两台机器

```shell
scp /etc/hosts root@mq2:/etc/
scp /etc/hosts root@mq3:/etc/
```

**`说明：命令中的root是目标机器的用户名，命令执行后，可能会提示需要输入密码，输入对应用户的密码就行了`**



2.4 将`10.15.0.3`上的`/var/lib/rabbitmq/.erlang.cookie`文件复制到另外两台机器

```shell
scp /var/lib/rabbitmq/.erlang.cookie root@mq2:/var/lib/rabbitmq/
scp /var/lib/rabbitmq/.erlang.cookie root@mq3:/var/lib/rabbitmq/
```

查看三台机器cookie是否一致

```shell
 node1: cat /var/lib/rabbitmq/.erlang.cookie 
 node2: cat /var/lib/rabbitmq/.erlang.cookie 
 node3: cat /var/lib/rabbitmq/.erlang.cookie
```

2.5启动每台机器的RabbitMQ

```shell
systemctl start rabbitmq-server
或者
rabbitmq-server -detached
```

2.6 加入集群

2.6.1将nod2节点加入集群

```shell
# 停止RabbitMQ 应用
rabbitmqctl stop_app
# 重置RabbitMQ 设置
rabbitmqctl reset
# 加入到集群
rabbitmqctl join_cluster rabbit@mq1 --ram
# 启动RabbitMQ 应用
rabbitmqctl start_app
```

2.6.2将node3节点加入集群

```shell
# 停止 RabbitMQ 应用
rabbitmqctl stop_app
# 重置 RabbitMQ 设置
rabbitmqctl reset
# 节点加入到集群
rabbitmqctl join_cluster rabbit@mq1 --ram
# 启动 RabbitMQ 应用
rabbitmqctl start_app
```



2.7查看集群状态 任意节点执行

```shell
rabbitmqctl cluster_status
```

**`提示：在管理界面可以更直观的看到集群信息`**

###### 3.镜像集群

3.1策略说明

镜像队列属于RabbitMQ 的高可用方案

```shell
rabbitmqctl set_policy [-p <vhost>] [--priority <priority>] [--apply-to <apply-to>] <name> <pattern>  <definition>
-p Vhost： 可选参数，针对指定vhost下的queue进行设置
Name:     policy的名称
Pattern: queue的匹配模式(正则表达式)
Definition：镜像定义，包括三个部分ha-mode, ha-params, ha-sync-mode
               ha-mode:指明镜像队列的模式，有效值为 all/exactly/nodes
                    all：表示在集群中所有的节点上进行镜像
                    exactly：表示在指定个数的节点上进行镜像，节点的个数由ha-params指定
                    nodes：表示在指定的节点上进行镜像，节点名称通过ha-params指定
             ha-params：ha-mode模式需要用到的参数
            ha-sync-mode：进行队列中消息的同步方式，有效值为automatic和manual
            priority：可选参数，policy的优先级
```

3.2查看当前策略

```shell
rabbitmqctl list_policies
```

3.3 添加策略

```shell
 rabbitmqctl set_policy ha-all '^hello' '{"ha-mode":"all","ha-sync-mode":"automatic"}' 
   说明:策略正则表达式为 “^” 表示所有匹配所有队列名称  ^hello:匹配hello开头队列
```

3.4删除策略

```shell
rabbitmqctl clear_policy ha-all
```

参考

[https://blog.csdn.net/weixin_45612794/article/details/106023090](https://blog.csdn.net/weixin_45612794/article/details/106023090)

[https://www.kuangstudy.com/bbs/1366514020613472258#header31](https://www.kuangstudy.com/bbs/1366514020613472258#header31)

[https://blog.csdn.net/qq_28533563/article/details/107932737](https://blog.csdn.net/qq_28533563/article/details/107932737)
