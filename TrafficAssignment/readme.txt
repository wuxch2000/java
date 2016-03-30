* 2007-9-17:

修改link的定义，添加一个link-id，而不是依靠2个node来确定一个Link,
主要是考虑到以后的network中会出现2个node之间回出现多个link的情况。

network和algorithmlableSetting对max_node_number的依赖取消了。直接在network定义一个Hashset<Node>,这样不必在开始指定最大的node个数

尚未经过全面的测试。

* 2007-9-18:
Network类中增加m_to_node_pointers

* 09/19/2007 18:12:40
把costflow编码完成了，不过没有调试和验证。
network里面全部使用node,不再使用nodeid

* 09/24/2007 09:08:47
修改了node和link的toString的输出。
Network里面直接使用node作为hash的下标。
修改了最短路径算法中的最短路径的生成函数。
解决了一些costflow里面的bug，目前还有问题。

* 08/03/2008 13:21:37
这两天改了以下几点：
** 计算最短路径和OD的数组全部使用Hashmap或者是Hashset，不在使用简单的数组；
** 计算最短路径的初始化函数，以便多次重复调用
** 支持XML的配置文件
