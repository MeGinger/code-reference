int -> char

3 -> '3'

char ch = (char) (num + '0')

'3' -> 3
int num = ch - '0'

* 然后一个 coding，按行统计词频，不难，hashtable解决。 写code的中间，问了很多次复杂度，
基本上我写一行code，他会问一下这一行的复杂度


1. Unique Path 变形（可以上下左右，可否回溯，不可return false且原地不动）
2. lc239  
3. Max Subtree Sum
4. Longest Substring without duplicate char. more info on 1point3acres
5. Find leftmost node of the lowest level-google 1point3acres
6. Two sum
以下内容需要积分高于 188 才可浏览
.本文原创自1point3acres论坛
7. Merge qua-tree
color: black, white, misc. From 1point 3acres bbs
class Qua-tree
{
    Que-Tree[] children; //0 or 4 children. 围观我们@1point 3 acres
        int color;
        ....
}

Merge two tree, black is dominate color: black vs anything -> black;. Waral 博客有更多文章,

8. 围棋判断一个子被capture与否，被capture的定义就是这子或这子属于的整块同色子区域被另外颜色子包围。 
   (解法也很简单， dfs。 每次recusive call返回是否被capture, 遇到异色子或者出界都是true, 遇到同色子就recursively去call它)
(那假设只有同色的子（没有对手的子），也算是被capture么？（这个点很好！！  可以当作edge case处理单独处理。）

9. Latgest rectangle area
10. Search word in Trie
11.Lowest LCA
12.Top k nearest point
13.给出的是一个目标文件名，自己提出假设定出file class 和 directory class，找出所有符合目标文件名的文件的path
14.job failure root search
(相当于一颗倒的树，root fail了，找导致这个failure的最高的ancestor。DFS, 顺着fail的path往上找). Waral 博客有更多文章,
15.多米诺骨牌，找出最少数量的能推倒所有牌的牌. 1point 3acres 论坛
(大概思路就是循环把入度为0的点当作起点dfs，把能traverse的点mark as visited，把起点加入result，然后如果还剩下没有visited的点，就说明有环，所以就再loop剩下点，每一个dfs，mark能traverse到的点as visited，把起点加入result，如果碰到了之前visited的起点，就把这个起点从result里面删掉，加入当前的起点。).本文原创自1point3acres论坛
16. Shorest Path. Waral 博客有更多文章,
17.Find heavier ball in ball array
18. Work Break. more info on 1point3acres
19.  Word Ladder II(原题是变一个字母，这里是去掉一个字母)
20. string to int
21. int to roman
22. There is list of price at each day，and only one selling window is allowed. The selling price  is the minimum price of start day and end day of that selling window, and price is flat during the selling window. The selling amount is constant per day. Find out the selling window gives maximum profit, output max profit.
23. Graph. graph. 实现画板的brush功能： 一个画板上面已经有一些图案，用户在画板上点任意一点，画板会自动paint包含该点的封闭的多边形。实现这个功能
24.Merge Interval
25. Matrix原题
26. 有一个Task clas包括两个int, start and end; 有个list有很多个Task, 计算需要多少Thread 来run这些task.. 围观我们@1point 3 acres
27. Serialize and Deserialized Binary Tree  
（中间穿插了多线程的问题，以及 abstract class 等知识。.留学论坛-一亩-三分地
Follow up :
    1. What if the TreeNode class value is string (instead of int) which may     including newline, space, comma
. 一亩-三分-地，独家发布
    2. 如果不是二叉树，是多叉树怎么办？
正确的方法是在序列化的时候 存孩子的数目，存数据的长度，再存数据。需要一个结构化的序列化操作。
new line, space 这些只是给你增加难度，你可以把数据当成binary来存; 我的想法是可以存字符串的ASCII码或者UNICODE编码，然后用space或者newline隔开存到文件里
）
28. 一个matrix，里面有一些锁（1表示）和空白（0表示），问每个空白到最近的锁的距离。简单bfs
29. 算法+search suggestion设计，算法：给个word，给个dict判断给定的word是不是anagram
30. numbers of islands 
（ follow up：有多少个形状不同的岛？. 留学申请论坛-一亩三分地
这个Follow up是经典 number of distinct islands
比之前的明显要难些。 需要用到hashing得思想。 
每一个岛将遍历完的点id(每个cell 可以分配一个id, id = i*m+j) 组合起来， 返回字符串，比如 “1/2/3/5”  这个岛有四个点。如果另一个岛是 "11/12/13/15"  只要把它offset下， 第一位归1， 它也变成"1/2/3/5"， 所以这2个岛的shape是一样的。 将这些第一位归1的字符串往set里丢。自然就除重了.本文原创自1point3acres论坛
中心思想： 将CELL ID组合来表示一个岛(hash to string)，然后变形string, 最后往set里丢。 done

）
31. kth largest number in array，quick selection
32. LRU cache
33. Number to English words
34. Valid Parenthesis
35. given n pairs of numbers (1122...nn) and arrange them so that the each number x is x spaces apart from another number x. 
(数字必须紧挨着，思路就是DFS)
36. Given a dictionary of words and a word, return the word if it exists in dict, else return the top 5 words in the dict that are closest to the given word;
37. 2D array is immutable. Give 2D array of 1s and 0s, 1 is island and 0 is sea. Return the maximum island size.1point3acres网
38. 给了一棵树，让给每个node 加一个pointer 指向sibling.
(用了queue level order traverse 加了。然后follow up 让优化，no extra space)
39. Reconstuct itinerary
40. 一个non-negative integer array里找subarray sum。
(followup: 数字可以为负)
41. 有一堆时间连续的purchase records (id, timestamp, userId, productId)，找出最常被同一个用户连续购买的三个商品
42. 找出数组中唯一出现奇数次的数字，保证只有一个，(hashset解决)
43. reverse linkedlist
44. kindle界面是黑白的，黑色区域是一个shape，然后输入一个界面且可修改，返回shape的个数。
(这里我用了二维数组作为输入，0是空白，1是用pixel。然后dfs即可。 其实就是Number of island.)
45. Intersection of two linked list
46. Group anagrams
47. Convert binary tree to double linked list.  In-order order. from: 1point3acres 
48. 写一个class要求push/pop和get minimum都是O(1). 1point 3acres 论坛
49. 给一个数组的object，用里面的key来sort，keys 只有有限的几个 
(这个题我先用的priority queue，让我继续优化，我用了bucket sort，到O(n), 让我继续优化用in-place，我用了两个pointer;
我最后用2个pointer排序的，但是一个while loop 下面嵌套了四个sub while loop。比如，这些objects的key 只有 X, Y, Z. 排序的要求是把这三组按照XYZ的顺序排好。

前两个sub while loop 对调X 和 Y或Z; 这轮结束后，X的位置应该都是在正确的位置上了
后两个 sub while loop 对调 Y 和 Z。).1point3acres网

50. wiggle sort 2 . visit 1point3acres for more.
(给一个数组，里面有负数和正数，让输出负数／正数间隔的数组，我用了两个pointer从头到尾／从尾到头同时扫一遍。估计这题没法做成in-place，最后没时间写完code，只是大概说了一下，最后面试官觉得可以work)

. 1point 3acres 论坛
51. find # of distinct islands in 0-1 matrix.



BTS to a doublelinklist 

find circular dependency: DFS解 

岛题 变种 0 and 1 都被看成是岛
找到0 和 1岛的最大size 还是标准DFS秒， 毕竟原题是可以闭着眼睛写的
追问当矩阵大到内存放不下怎么解 （没答出 大牛求助！）

从一个数字stream不停读取数字，获取当前最大的N个数字 - minHeap - NlogK
有N个数字stream，读取并merge它们

二叉搜索树的第K大元素

MergeKSortedList

找一个lis里边第二大的数。这个用quickselect做。followupshi问如何处理tie的情况。

关于多线程的。 如何调用一个function，如果在规定时间没有完成计算就直接返回。比如 function(200)， 200代表这个function最多跑的时间如果没有在200毫秒完成久停止计算直接返回

一个arr存bit，一个数组的list，每个数组两个数字，求arr里对应的两个index的这段range的或值。要求算法复杂度O(n)。

一个巴士有好几个stop [1，2，5]， [5， 8，9]， [8，7，6]，起点2，终点7，最少换乘的巴士数量

josephus problem，生写code

大哥感觉对bfs不是很熟，问我为啥不用dfs。我觉得这个题应该是bfs比较直觉吧，再说dfs， recursive写法用call stack空间，相比较heap空间容易爆栈。 我极力避免在production code写recursion

从日志中找出频率最高的路径(长度为3的路径)，例如：
jack, /home
lucy, /home
jack, /product
jack, /search
lucy, /product
lucy, /profile
jack, /catatory
mary, /home
jack, /product
jack, /search

/home/product/search -> jack, lucy; /home/product/profile->lucy; /product/search/catatory-> jack;
返回/home/product/searc



涂色, 画图工具那个填色 - BFS
fill 这个功能，在画板里。就是把一块区域颜色涂成想要的颜色。 不解释啦，直接DFS

serialize and deserialize 一个binary tree， 就是蠡口hard那个。。 5分钟白板画了个图，解释了一下，然后开始写， 估计写了10分钟。follow up是如果不是一个integer node， 是个string node， 可能包含你的spliter， 怎么办。。我一开始没反应过来，说可以再弄个array，记录一下是node还是spliter，后来他提示说能不能把spliter escape掉，才想到可以escape



把睿蛇（如果能辨别这个人是把睿蛇，就看他的team和其他人的team不一样）：  围棋判断一个子被capture与否，被capture的定义就是这子或这子属于的整块同色子区域被另外颜色子包围。 . 围观我们@1point 3 acres
   解法也很简单， dfs。 每次recusive call返回是否被capture, 遇到异色子或者出界都是true, 遇到同色子就recursively去call它。


阿三哥 加白人女shadow
一上来BQ， 然后coding 来源一亩.三分地论坛. 
阿三哥一开始问我玩不玩ball game, 我赶紧说不玩，然后又问，知道怎么玩吗，我说不知道。真是听都没听说过。然后shadow 看向阿三，意思很明显呀，不应该问我不知道的game呀，可阿三哥眼睛不眨一下开始给我讲规则，我去，我听都没法听全规则，怎么写code? 阿三连屁股都不想挪，不把规则写在白板上。反正我就这么稀里糊涂地边问边写。他坐一边纠正我。基本是到了最后一分钟我才明白这个ball game到底是咋回事。
就是一 board 有n 个cells, 有一个die, 1-6, roll 出多少就跳几步。 cell 有 3 种情况，如果是字母就前行几步，如果是蛇就后退几步。如果是空的就重新扔die.  最后就最少的步数到终点
不知在蠡口上有没有这样的题



 Amazon locker, 怎么按照货物的大小取空闲的柜子。一开始没有弄清楚问题本质，假设得太简单，我定义柜子种类只有single，double，four。而其实我应该定义Type { int width, int height, int deep }. 最后终于讨论出问题的本质了，然后就没有时间写代码了。定义了Type, 然后给每一种Type一个list的available柜子编号，TreeMap, 其中对Type排序，然后对有序keySet中找到一个大于目标值的最小值，binary search。


 输入2维字符串数组 String[][]，每个数组String[]第一个String表示父母的名字，剩余的该人的子女的名字。every name is unique。 问题： Who has the most children? (Hash table); Follow up: who has the most grandchildren? (Hash table) 
 Follow up, if the data is so huge, how to process. (MapReduce)


一个是编码解决thewordsearch.com这种问题；
记得还有一面是LRU缓存设计；
之后问了我 lru , 我给出了算法设计 (最佳解)
问高频查询情况, 给了lfu.1point3acres
面试官total agree, 很开心

写LRU Cache，在扩展到集群，用Hash映射解决数据分布。

tree node 里包含 left node 的数目, 查找inorder(忘了是preorder 还是什么order) 第x个node,  dfs举例, 几行秒掉. 一亩-三分-地，独家发布




给你一个字典一个，map<Character, Integer>存每个字母的 权，输入是一个List<Character>(可能有重复)让你求这些字母求可以组成最大权数的 word list(List<String>). 



LZ前一阵子，面试亚马逊。自己把地里面的Amazon所有的题目总结了一下，一共大概121页。楼主面试前看了一遍，结果面试时几乎全是原题，已获Amazon offer。下面是我的onsite 题目。以及自己的总结。
楼主快没有米了，走过、路过的赏点米

一共五轮，每轮45分钟，没两轮之间没有break，但是你可以花上3minute左右喝点水和去卫生间.

第一轮：（烙印）
hiring manager面试的，也是我以后mobile team的manager。没有coding。

对着我的简历，一个项目一个项目的去问。 

每个技术细节问的很深，比如细到这个请求网络的服务是用什么Framework类来实现了，这个Framework类比着别的Framework类有什么优点？  

另外，他对我的一个多媒体播放的一个项目特别感兴趣。问了好到技术细节，以及其中的一个我们设计的很关键的算法。
我给他这whiteboard上面画图详细的描述我们当初设计的这个算法。结果这个面试官一眼找出了我们设计的一个漏洞。
我告诉他这个问题我至今还没有解决，他也没有继续追问下去。

第二轮：一个年轻的印度三哥。
这货上来也没有寒暄，也没有微笑，直接说题目。我当时也挺紧张的，感觉这货要挂我。
他的英语我也没有听的很懂。我反复给说他pardon 和Do you mean by ....? 无数次之后才弄明白他的题目意思。 


他的题目的意思是：输入a list of sections, list<section>, 在每一个section中有很多product，每一个product有两个属性：user，relevancy ，你可以通过调用系统API得到这两个值。 每一个section有score所有的product的score之和，每一个product的score是user * relevancy 。 最后给这个list of section排序，使得score大的section在前面。 这个题目其实很简单，但是他前面描述这个问题花了很多时间。

第二题：设计一个 in memory cache system
1. capacity（这个system有一定的容量）
2. TTL（time to live）.
3. LRU (least recently unused)
参考leetcode https://leetcode.com/problems/lru-cache/

follow ups:让你扩展到一个distributed in memory cache system怎么设计


第三轮：老美
继续问简历上面的一个项目，然后给了一个leetcode的原题https://leetcode.com/problems/surrounded-regions/。
由于问我简历，问的时间太久了，结果算法题目的时间就很短了，算法最后没有在whiteboard写完，但是思路给他们解释清楚了。

第四轮：老美
第一题：给我展示了他们组做的一个APP: Myhabit,（可以在AppStore上面下载看看），面试官给我指着Myhabit上面的一些功能，让我去自己设计。在白板上写出自己的思路。

第二题目： linkedlist 每个节点多一个random pointer。leetcode原题。我用hashMap做出来的.

第五轮：（老美）
第一题： 设计一个大楼的电梯（Amazon经典面试题目，面试前面我专门总结了一下^_^）
第二题：继续问简历上面的项目。

总体感觉，对自己以前做过的简历上面的项目一定要熟悉。两道算法都是leetcode中等题目。回答问题时最好要以customer为中心。







