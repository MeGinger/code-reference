OOD

* 聊一些OOD的概念和一些细节
* 什么是OOD，OOD哪里好
* 什么是多态，什么时候favor interface，是么时候favor abstract class等等
* 问了一些pure virtual function, 多态，什么时候最好用继承扩展，什么时候 用纯虚函数， 写例子。
* 开始问了很多object oriented的问题。abstract class，
* interface，什么情况下如何选择，inheritance VS composition等等。


* JSON parser
* parking lot 
	* followup 不同的是这是个收费的停车场，还有电脑可以查询空余车位和价格。
		* price/fee, receipt
		* spare space - count and position
	* 设计parkingspace，里面有很多种space(比如smallspace middlespace largespace), 对应 不同型号的车。尽量让space都利用好
		* parking space - small, middle, large - inheritance 
		* vehicle - small, middle, large, 
		* findSpace
* traffic light - observer design pattern, thread.sleep()
* class scheduler - runSchedule() meeting room 2 - variation 

* restaurants
	* 设计在线订披萨里的披萨。有各种大小的披萨，披萨可以有各种 topping，同一种topping也可以同时上好几个，比如double cheese。写 披萨的数据结构，然后写个函数计算披萨价格。
	* 设计一个餐厅。然后他说如果他和他朋友第二天6pm要来吃饭，但是餐厅已经订满了，怎 么schedule。还问了怎么拼桌子来满足客户的要求。 和第一个面试官聊得很开心，还没聊完第二个就来敲门了叫我过去面试
	* 订餐系统
* elevator - 
	* 设计电梯箱，按键面板等对象和它们之间的关系，没有深入调度算法细节。
	* scode出来，于是抓瞎了。最后我想了一个办法是用两个array，一个工作array一个 后备array，如果电梯向上而新请求来自上方楼层，或者电梯向下而新请求来自下方楼层， 那就把新请求加入工作array，否则加入后备array。在适当时机，swap两个array，比如需 要变化方向的时候。感觉挺粗糙的，但是能达到需求



* Design Amazon Features
	* Lastest 3 view Items by customer
	* 设计整个亚马逊的物品、 仓库、卡车互动系统。仓库遍布美国，卡车有油量，仓库之间有些有路径有些没 有。我搞了几个类，包括物品、仓库、卡车。要求实现一些功能诸如，从仓库A把一 定的物品分给仓库B，分派卡车，装货、卸货函数。
	* 设计购物车，考虑discount code什么的
* Design furnitures 家具
* Games
	* Tic-tac Game, Followups。答:CC150
	* A poker game and different methods in each class
	* 纸牌游戏，还让画类图
	* 设计一个card，实现shuffle 和 deal两个功能。 CC150原题
	* 洗牌 洗牌要从建class可以写。然后两个function，shuffle
	* 一个三角形棋盘，只有一个空，像跳棋那么走，走过空就可以把中间那抽掉，然后OOD设计。然后follow就是如果不同棋盘怎么设计
	* 设计国际象棋
	* Black Jack. 因为之前有准备过棋牌类设计，所以不怎么慌，在白板上边交流边画类图，在他要求的地方写一些代码无非是继承之类的，45分钟很快就过去了，基本上类图也画完了，聊得挺好，感觉还可以。
	* 设计一个游戏，任务可以上下左右动
* 设计一个机场调度系统。这个OOD还有点讨厌，就是和面试官多 交流有哪些实体需要抽象吧，要想清楚业务流程里的参与者和用例，还有之后的耦合内聚怎么优化等等。
* 设计机场跑道tracking system，保证每个飞机能顺利使用跑道升降
* Expression
	* 解析表达式，例如“1*2 + 3”，关键要用面向对象的思想设计。需要可以拓展，支持括号，支持开 方之类的 这题感觉自己没思路，瞎答了一通。
	* 一个OOD，一个tree的结构，计算表达式。
* 给一个数组和一个范围，要返回范围里的最小值，然后扩 展到如何缓存这个查询。一开始搞我以为是算法题，DP?排序?扯了半天，后来发现原来 是OOD，囧。。。关键是如何解耦缓存查询和实时查询这俩类，(implement interface)然后扯了扯工厂模式，依 赖注入等等。。. strategy design pattern

* 设计vending machine的题，而且要求找的零钱数量越少越好.我中间定义硬币的时候还因为不知道5分硬币叫啥问了一下
* jukebox 系统设计
* money 找change， change有四类，0.01,0.05,0.25, 0.1, 刚开始我还让面试官一个一个给我把coin的名字写出来，我说我没用过coin， 然后这题是从高往下走，比如说0.52, 先把最大的找出来，然后是次大的，0.52先是两个0.25,然后是两个0.01. follow up是如果再加上其它币种怎么办，楼主说的是设计一个interface，有一个方法是public Map<> Change(), 每一个币种，比如说欧元是一个class，美元是一个class，然后implement interface，实现自己的币种，或者把自己的币种换算成美元.


* Problem 1: 设计一个停车场计费系统 一开始从OOD的角度来设计。后来面试官说我应该think bigger，才意识到应该是从一个 通用系统架构的角度来回答这个问题。于是就开始扯后台系统架构，想到经典的三层系统机 构从来不会答偏题于是就开始往上搬了。第一层接入层，第二层逻辑层，第三层存储层。接 入层收到用户请求根据协议和请求类型转发给合适的逻辑层。逻辑层包括多个不同服务，各 干各的事，完成库存啊计费啊汇率转换啊等等操作。存储层当然就是数据库，mysql和 nosql大家都懂的，上来把基本表建好，就直接用吧，有需要就扯缓存。比如我就扯了用 redis当缓存，缓存当然有各种不同粒度，各自命中率肯定不一样等等。问我如何扩展方 面，我的回答就是各层内部放入是随便加服务的，只要服务起来了之后到Zookeeper去注 册让其他层的服务调用的时候能找到就好了，存储层扩展有很多Proxy可以用，比如我厂就 有一个叫Atlas的开源项目，是一个很出色的Mysql Proxy，大家可以去Git看看。然后大家 可以根据需要扯一些优化点，找不到也无所谓。 开放性的设计题如果是我这种没太多工作经验的人或者是应届生有时候还是觉得挺难的，见 过了一些之后就死了心决定干脆不去费脑子了。只要不是特别奇怪的系统，那干脆施展一番 演技之后就把经典的三层结构网上套，这个结构大家都比较熟悉，而且也简单清晰，面试有 限的时间里也能够解释的比较清楚，里面的Nginx、Zookeeper、mysql、几张nosql因为 挺基本我估计也不会细问。
邓侃老师在09年发了一系列博文讲了Twitter早期架构， http://blog.sina.com.cn/s/blog_46d0a3930100f0vr.html，当时Twitter使用就是一种基 于Push典型的三层结构，大家有需要可以去看看。虽然文章历史久了点儿，里面的内容我
觉得应付一般的面试还是够了。实在不行，就把文章里面的twitter换成面试题里面的系统 往上背吧。。。



System Design


* Design Amazon Features
	* 设计 Amazon Locker，主要需要写code 设计的部分就是怎么找到最合适放某个package的那个locker。

	* Amazon 主页你搜索一个东西的时候，会出来 most viewed related items，设计这个功能怎么实现
	* 设计产品推荐系统 - could be related and high rated system
	* 怎么collect ads的clicks的frequency，答的很差。。感觉他提示了很多实际场景，然后问我该用什么record click。最后问db怎么设计，说要存的时候可以按click happen的timestamp/frame 存，但是read的时候可能要按照advertiser来collect frequency。
	* 设计搜索引擎，设计customer also viewed 产品的功能

<user action couting system>
https://medium.com/@Pinterest_Engineering/building-a-real-time-user-action-counting-system-for-ads-88a60d9c9a
<recommendation system> - related 
http://blog.echen.me/2011/02/15/item-to-item-collaborative-filtering-with-amazons-recommendation-system/
In making its product recommendations, Amazon makes heavy use of an item-to-item collaborative filtering approach. This essentially means that for each item X, Amazon builds a neighborhood of related items S(X); whenever you buy/look at an item, Amazon then recommends you items from that item’s neighborhood. That’s why when you sign in to Amazon and look at the front page, your recommendations are mostly of the form “You viewed… Customers who viewed this also viewed…”.
https://www.baeldung.com/java-collaborative-filtering-recommendations
java code


	* 设计淘宝网站鼻祖的竞拍模式，lz表示近大半年没见人面过这题，所以就只能基本套路，往high level套，什么hosting server啊，lb啊，身份验证，竞拍行动的服务器，加上用户数据库，竞拍货物数据库，每次bid的数据库，monitor，sharding啥的，毕竟面sde2。据说扯api，interface什么的都是刚毕业孩子玩的。。。人不待见。当然反响感觉也一般，两个manager可能见惯了我这种吹牛逼的，所以也有点虚
	* 设计一个在线可以买电脑的系统，要求客户可以定制显卡，cpu之类的，然后可以生成订单，还有一些model是预设的，用户可以选择这些model，也可以再基础上定制
	* 上板 Design AWS marketplace，小哥出题先画出大致流程， 感觉他也不知道要问啥。。我说这块traffic会比较多 需要用cache缓冲 他点点头那块storage需要replica防止single point of failure 他又点点头然后让我写出API出来 啊， 顺间变OOD Design了思维有点跳跃 感觉到最后10分钟意识到题的意思 结束时小哥握手无力 估计没戏 还拍了照
	
When a Pinner loads a page of a view type on Pinterest, an ad request with user info is sent from the web server to the ads system in order to fill the ads spots (i.e. an opportunity to display an ad). The system will retrieve ads candidates from our inventory and subsequently determine which ads among the candidates to put in the designated spots. This behavior is called ad insertion.

As a Pinner interacts with promoted Pins, these actions are tracked by calling a tracking endpoint from the front-end. The server then logs the event to Kafka. A Kafka consumer will consume the messages and write the action events to a data store (Aperture). Ads backend servers request user action counts from Aperture after candidate retrieval.
	
independent counting service
The event data store has a counting layer to serve counts.
The service exposes a set of generic counting APIs.
	

	* 设计一个亚马wishlist的数据结构
	 就是吧product， host，guest和wishlist怎么混在一起，我选的用non sql来实现，讨论完了又聊了下用sql怎么设计schema，分享wishlist就发wishlist ID 到url里面，然后很简单的聊了一下如何提高performance和如何cache数据的问题，我就提了下用redis就行因为我也只用过redis。 然后大概估计了一下latency和throughput，然后讨论了一下db读写和如何隔离优化。讨论了一下cache的优先级选择，我就提了下LRU，用欢迎度和时间来决定cache优先级。就提了一下LRU是一个hashmap + linkedlist就不再问了，本来还想给他现场写一个LRU搞个妥帖的，结果不问了。主要就半小时，好像也没啥太多能问的

host, product, guest, wishlist
SQL: 
Guest: {id, name}
Host: {id, name}
Share: {wishlist_id, url} - primary key (wishlist_id, host_id)
WishList: {wishlist_id, host_id, productId} - primary key (all)
it might be better to use NoSQL for wishlist - a list of products

Limitation:
Need to maintain consistency between 
caches and the source of truth such as the database 
through cache invalidation.

Cache Read/Write Strategy - Cache-aside

Cache Replacement Pocilies
https://en.wikipedia.org/wiki/Cache_replacement_policies
* First-in-first-out
* Last-in-first-out
* Least recently used
* Least frequently used
Counts how often an item is needed. Those that are used least often are discarded first. This works very similar to LRU except that instead of storing the value of how recently a block was accessed, we store the value of how many times it was accessed. 
* Random replacement
* The Time aware Least Recently Used (TLRU) is a variant of LRU designed for the situation where the stored contents in cache have a valid life time.
The algorithm is suitable in network cache applications, 
such as Information-centric networking (ICN), 
Content Delivery Networks (CDNs) and distributed networks in general. 
TLRU introduces a new term: TTU (Time to Use). 


LRU Cache - 用欢迎度和时间来决定cache优先级
* 欢迎度 - 使用, put it to head of list
* 时间 - least recently used

LFU Cache - 用欢迎度, 频率和时间来决定cache优先级
*** 

* Design Cache
* Twitter
	* 设计Twitter的系统，问到了如何解决latency问题，我卡住了。。。显然推拉组合还不能让他满意。。。大神可以解决一下吗
	* 设计推特热搜功能，其实就是TopK + 如何scale up的问题没啥其他好说的，网上一搜都是标准答案。记得处理不同区域不同热搜榜单的问题。比如你在中国肯定不想看到美国的前十热搜之类的。

https://www.quora.com/What-algorithm-is-used-to-find-trending-topics-on-Twitter
https://www.careercup.com/question?id=15310676

blogs.ischool.berkeley.edu/i290-abdt-s12/

“The new algorithm identifies topics that are immediately popular, rather than topics that have been popular for a while or on a daily basis, to help people discover the ‘most breaking’ breaking news from across the world. (We had previously built in this ‘emergent’ algorithm for all local trends, described below.) We think that trending topics which capture the hottest emerging trends and topics of discussion on Twitter are the most interesting.”

!!!!
http://blogs.ischool.berkeley.edu/i290-abdt-s12/files/2012/08/Kostas_Trends_Sept_13_2012.pdf
1. simple couting
limitation:
solution: remove common words or stopwords

https://www.youtube.com/watch?v=RMuLavkPLwc&feature=youtu.be
Trending topics? hashtag in tweet
Scale? twitter users
Interval? last hour, last day, last ten second, etc.

Simplistic approach:
1. client requests for trending topics
2. Server fetches the latest tweets in the last ten seconds from the database and return the top k hashtags as trending topics
Issue:
1. not scalale
2. Querying database each time is expensive -> cache

Improve design
1. load balancer and multiple servers handling the request
2. master-slave architeture and we read from the slave databases
3. Caching layer
4. Sliding window - avoid duplicate calculation
- maintain a hash with tweet counts over the last 10 seconds
- EVERY SECOND, update the dictionary
	- add new tweets over the last second from the database
	- remove tweets that are no longer in 10 second window
	- return the topmost hashtags in the hash

richer definition of topics - not just hashtag
geographical clustering of trends - offline processing




	* 系统设计 twitter 我照本宣科地说了qps等计算, 算出了 40M/s 感觉这块完全是多余自己提, 面试官不感兴趣好像 [留个疑问在这里, 系统设计这种estimate 需要做么??] 之后, 讲了最简单的 timeline 设计, 所有followers 获取tweet, 之后排序 aggregate, 返回. 面试官: make sense high level :  API server => DB  followup, case 如果 川普发了句, 我明天9点发tweet, 1m 用户准时不断刷, 怎么处理. sharding 方面, 如果川普的tweet都在一个db里, 我分析的是, 所有request 会 hit 某一个 db, heavy load, 所以 db要 shard by tweetID to make sure data is shared evenly.. 面试官:  how can the user get the tweetIds, 我: user 会首先 拿到所有 follower的userid (包含Trump), 然后查询 所有db, 返回对应的 tweets,  (我当时脑子短路, 以为设计的是 userid : [tweets] 的结构, 其实应该是 tweetID, Uid tweetId 做主键和shard id,  ) 这样就不会heavy load 某一个db, 所以当时答的含糊其辞)我: 当时shadow 问的是 shard by uid or tid, 我就说了 tid, 之后我补充了 要加 cache, 之后也没下文了, 不知道最后结果是什么


* 设计一个图片上传和浏览系统 - instagram, flxicr
* 设计一个照片分享系统，最主要的是如何优化 photosharing website
设计完了还跟他扯了一点怎么做highly available， partition-tolerant的系统




* 设计订票系统
* 设计交通系统， 在线查公交车或者地铁的schedule
* 设计Youtube
* 设计Uber
* TinyURL - 短网址服务
* Netflix的视频在线播放
* Design restaurant reservation system

* Design a distributed JukeBox System
* 设计chat system 从1对1聊天开始，最后没时间了简单说了一下group chat和推拉模型
* Calendar web app，类似google calendar. 

* 使用prefix tree存储电话号码
* 系统设计监测系统

* Grokking the System Design Interview - https://www.educative.io/collection/5668639101419520/5649050225344512


* 设计题，是给了很多很多不间断的log，里面有各个模块，其中有个exception模块，好几条log拼成一个完整的exception，找出某个特定的app的Top 5的exception模块。我第一直觉用PriorityQueue，后来还是改HashMap了，全程小哥哥看我方向偏了就来提醒我，最后顺利设计了几个类和function就过了
* 设计一个类似于Expedia的旅游app。。。我本人从没用过那个，之前都是studentuniverse买票，或者官网直接买票有积分的，能给我讲讲都啥功能么。。。她就把题简化了（貌似）， 说要设计flight查询，可以查non-stop 或是不non-stop的所有从A到B 的航班。 我大概问了一下QPS什么的，其实不重要，她说你就想象成需要你scale的那么多就好。 我一开始没太理解这个stop的玄机，以为一个航班从A到B stop直接就有了。。她说要像
101  A-》B
102  A -》C 
103  C-》B
104  A-》C -》B
这种的。。。感觉是个graph的题，我说我想把non-stop限制到小于3， 然后如果是non-stop 就直接查表，1 stop 就两个query   A-》 *（！B） 然后从这个list里 再 query  -》B 的把结果放进另一个表里。 其实不知道可不可行，没什么这个的经验。。然后她就问了怎么improve performance啊，其实就是cache，问了cache key啥的
* 快递包裹进柜子的问题，版上哪里见过但没做过也没见过答案所以start from scratch，于是快速写出几个核心class，被小韩打断，进入coding阶段，实现怎么选择柜子。

	迅速写出线性解法 - O(n)
	然后说如果有成千上万个柜子，怎么优化 - O(logN)

* 大概意思就是说要在网页上显示库存信息，数据从好几个数据库里面抓，当然速度慢，但是，数据库不能动它因为还有别的系统依赖，怎么优化。装模作样问了些问题，

其实聪明如我早就识破你了，不就是要加缓存吗。各种讨论优化，非常会引导我，跟这样的面试官交流简直是一种享受。都是些开放性问题，答的有理有据就行，最后分析下trade off。问罢终于又轮到我面他，接着他的工作又提了些问题，相言甚欢，握手，目送离开。


System Design:
1. Design a Uber 设计一个简单的Uber，包括
检测周围空闲的车，
用户打车付账流程，
到目的地时间估计
(将城市化成许多个矩形block（区），可以借鉴二维k-d tree那个思想 
	https://zhuanlan.zhihu.com/p/22727174
每个车实时更新当前位置坐标和是否available，找用户最近八个区的空闲的车，
然后时间就是车速和距离的关系，这个没错。地图api这种你需要什么和interviewr说就好了，
如果不是考察项目的话一般都会说可以默认给出的。)

2.TinyURL
(Write heavy? improve Security? 怎么scale? 一个region上的服务出问题了怎么处理?)
3. Repository system, design commit fuction and branch function.

4. Video/Movie System (given a list of videos, return top 5 rated videos)
- small set of data: PiorityQueue - minHeap, Time: O(NlogK)
- large set of data: 
MapReduce
{user_id, movie_id, rate} -> Map {movie_id, rate} -> Reduce -> {movie_id, avg_rate} -> data store - SQL {movie_id, avg_rate} index on avg_rate -> query top k 
postgresql: 
select *
from table
order by avg_rate desc
limit 10


5. Store the livestreaming video and watch it later function

6. cc150 JukeBox.

7. Amazon gift card printing machine. (实就是general的说一下你对一个application architecture的理解，面试官会引导你，比如用啥样的db，用rest/soap，某一步失败了怎么办，data consistency一类的随便扯一扯). visit 1point3acres for more.

8. One hour delivery system

9. Explain Agile, Waterfall, Pro & Cons.

10. Predict User purchase. From 1point 3acres bbs
(先分析什么因素判断用户买不买这个商品，浏览记录，购买记录，在页面停留时间，浏览这类商品的次数，现在火的top 100商品等等。然后分析架构，
给的答案是首先master slave避免single point failure，用户点击商品后先通过dymanic dns look up找到距离最近的CDN，然后http request传过来给那个cluster的master server, mater本身有cache看看这个请求的结果是不是已经cache过了有的话直接返回（这里cache的是这个请求对应的购物车html页面），没有的话master做负载均衡下传给空闲slave server（rmi call）, slave有自己的local cache因为对这个预测结果每个slave cache可以不consistent， 可以不用时刻recon每个不同的server cache。所有的数据存储都用in memory database并设置time to live， 因为这个是一个读取大于写的系统数据也不需要持久化不用支持transaction, scale也更容易。master如果挂了重启就可以，因为都是预测数据丢失了也无所谓。如果要更优化可以在浏览器端也做一层cache，如果用户反复点击同样的商品，就不用每次都make http call了). 

11. Card game , and write shuffle method

12. Amazon Locker 就是Amazon买东西可以运到一个Locker然后pick up的那个.
(仔细想一下你就会发现就是一个Parking Lot.Package有Small,Medium,Large.一个Location的 Lockers也有Small,Medium, Large。面试官主要想知道一个送货小哥去的时候怎么分配给他个大小合适的Locker。要写那个method。我就按照Parking Lot做的。我觉得一模一样。恩恩，我那时侯一开始考虑也想是不是Order生成的时候就匹配了一个Locker，还有挑选哪个Location,但跟面试观官交流以后，他就说假设只有一个Location然后主要想知道送货小哥去的时候怎么分配，别的先不考虑。)

Amazon locker, 怎么按照货物的大小取空闲的柜子。一开始没有弄清楚问题本质，假设得太简单，我定义柜子种类只有single，double，four。而其实我应该定义Type { int width, int height, int deep }. 最后终于讨论出问题的本质了，然后就没有时间写代码了。定义了Type, 然后给每一种Type一个list的available柜子编号，TreeMap, 其中对Type排序，然后对有序keySet中找到一个大于目标值的最小值，binary search。


13. Reader System

14. Parking Lot, Airport etc...(http://stackoverflow.com/questio ... n-an-oo-parking-lot )

15. Amazon address book
   (1. What's in web server
    2. What's the API for address service
    3. What's in the storage
    4. How to improve the performance)
(user 发送与address 相关的请求到web server，　然后web server 获取／更新相关的记录。大概说了下DNS根据user ip访问临近的web server, 问到了web server 里头有哪些与address 相关的function 以及参数，具体说了下getAddressLists. 期间提了下cache 面试官表情不对了。后边问了有多少个表，怎么设计，怎么提高profermance. 楼主提到说作no sql 做sharding。面试官说时间到了没有给反馈，多半是设计太狗血)

16. Design system to store user info and address info. Address info changes frequently. Need to notify address change.

17. Design a Log application. (就是开发从上面获取 bug log 信息，用户可以往上提交。开始我并不知道log application怎么工作的，是程序员往上提bug 还是test人员往上提。跟面试官一步步讨论的，然后写了些前后端的 process。中间很多问题，他们会顺着你的思路往下问)

18. 给了一堆 商品，每个有不同的分类tag 比如 book, music, sports... 然后按顺序输出，就是输出这里他描述的特别不清楚，于是开始先按tag sort了，然后顺着输出。他说不行，想要控制每行的个数，然后就控制个数输出。然后他比较满意，follow up 了随意更改每行的个数，不要求写代码，也写上去了
. from: 1point3acres 
19. OOD。设计一个SQL parser（不是compiler，这里强调如何解析SQL语句中的变量，比如table name，和关键字， 比如select 语句），关键在于怎么用好interface

20. 设计一个big integer类。实现其中的add和multiply。先是讨论了要使用的数据结构（数组和链表）并对比优缺点，然后用数组实现（非高效压缩那种的，一个数组元素是大数里的一位数）。multiply只要写伪代码，出了错，不过他不介意
. more info on 1point3acres
21. 设计个API，满足两个function，一个是往list里面丢string，还有一个是统计top k frequent element. 对于API实现scalability.

22.  Design pattern: strategy, observer   设计模式那个就是duck/toyduck的变形。Observer那个也是比较教科书的东西
23. 如果有一个service, 要求设计方法支持query，比如最后一秒的访问数，最后一分钟的访问数，。。。。最后一小时的访问数。。。. 牛人云集,一亩三分地
(1.把timestamp 写到磁盘上，然后用hadoop 来算。面试官问pros and cons
2. 为了改善读的速度，我说把这些timestamp存到in-memory buckets里面，最后还是 hadoop
根据我的buckets排序思路提示我，可以不用存timestamp。。。。我有点惊呆了，但是转念一想，你这些query的时间段是last second/minute/hour。我觉得就可以只有这三个buckets，每次call 这个service的同时，检查当前的timestamp，如果超过1秒，我说就去aggregate/update last minute和last hour的总数就好了。。。 到最后我脑子里有点捣浆糊了，但是面试官看到我最后的思路解释说可以work))


24. 手机公司的bill系统，手机计划有免费时段，比如晚7点到第二天早7点和周末免费。短信和数据有各自的价格，用超过计划允许的数量怎么办。最后完成的感觉还算不错。这一轮也问了oo的一些基础知识，比如inheritance 和 composition的区别，什么时候用哪种？

* OOD：设计一个音乐播放器的歌单接口
* 图像处理系统，基本功能有生成thumbnail，raw 转jpeg/png
* 第一轮设计一个load balancer。给你2个方法：forwardRequest(httpRequest), 和requestComplete(httpRequet)。 让你implement 这个两个方法

设计一个chat log. 因为面试官跟我一样玩dota2,所以是参照dota2里面的频道。用户创建频道，加入频道，并且可以在频道里面聊天

最后一轮还是system design: 你获取一个log stream, log的内是若干的key value pairs 对应userId->visited url。让你求most frequent user visited chain.
  比如user1 访问过的url 依次是B->C->D
         user2  A->B->C->D
         user3 B->C->D->E
那么答案是B->C->D。


---------- LINUX FIND * START ----------

* 设计linux的find命令

白人大胡子大哥（貌似看到其他一篇里面也提到同样的人，估计就是同一个），问的是怎么设计一个linux里的find function，一模一样。我开始晕了半天，过一会他给我写了个File的class，里面有string name, bool isDir, vector<File> children，children里面是下一层的所有file/dir 名字，然后根据这个我写了一个 find class。基本就是loop当前层的所有file/dir name，判断这个东西是file还是dir，是dir的话继续recursive find。



linux find的核心我感觉就是recursion search 每一个子文件，遇到是文件的就加到返回list，如果是directory 就dfs
我就找到一个用java写的.
https://www.mkyong.com/java/search-directories-recursively-for-file-in-java/
希望大家一起分享一下，感觉这道题可以出的很复杂很难

设计linux的find命令，应该是一道OOD题目

leetcode里有这题啊，就是设计文件系统 lc588



我觉得这道题本身是所谓的面向函数编程的体现

做法是在外部构建一个函数，这个函数返回boolean，然后input是文件，函数的具体实现是find命令所带的各种参数，然后将这个函数放入一个recursive的find方法，这个方法会得出当前目录下的所有文件，并遍历所有当前的文件，如果是文件则使用这个函数来判断一下是否返回true，返回true，则加入到返回文件列表中，如果文件是个目录，则recursive的调用find方法来查询目录下的所有文件。

比较麻烦的是如果文件是个soft link，这个处理有点烦人。

Java下面实现这个主要应该是靠1.8的lambda来做，最java的方法是基于find定义成一个interface，然后基于这个interface继承各种具体的查询类，比如查询名字，查询大小，然后将这些查询类实例化以后发给这个recursive的find，再复杂一点就是得考虑多个查询条件并用的情况，这个是可以通过将实例化的各个类放到一个list里面一起传给find来搞定。. 牛人云集,一亩三分地

个人感觉这个是能充分说明函数式编程的优势的，应该有些语言用起来会更顺畅一些，个人不熟悉

---------- LINUX FIND * END ----------










