# 这是一个最简单的Spring的项目

该项目使用xml配置bean信息。
需要理解
IOC 控制反转，是一种设计思想，是将以前由上层的server层控制创建的dao,交给由spring
    这样控制权交给spring容器。

+ IoC 的理念就是让别人为你服务   
   + 谁控制谁：在传统的开发模式下，我们都是采用直接 new 一个对象的方式来创建对象，也就是说你依赖的对象直接由你自己控制，但是有了 IOC 容器后，则直接由 IoC 容器来控制。所以“谁控制谁”，当然是 IoC 容器控制对象。
   + 控制什么：控制对象。
   + 为何是反转：没有 IoC 的时候我们都是在自己对象中主动去创建被依赖的对象，这是正转。但是有了 IoC 后，所依赖的对象直接由 IoC 容器创建后注入到被注入的对象中，依赖的对象由原来的主动获取变成被动接受，所以是反转。
   + 哪些方面反转了：所依赖对象的获取被反转了。

+ DI依赖注入是实现方式，    