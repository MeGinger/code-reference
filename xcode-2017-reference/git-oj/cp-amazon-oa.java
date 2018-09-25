1. 飞机题目。return 所有的组合， input List<List<Integer>> 这种，注意处理
2. Top k 饭馆，heap， input List<List<Integer>> , int k.
*. LC763 Partition Labels
*. Substring with k distinct values. 给一个string和k, 求长度为k且每个字符各不相同的substring
*. mostCommonWord.  求出现频率最高的string
*. 迷宫，给定起点和终点，求最短路径
*. sort log.
*. 给定数组 求建bst, 并求指定两个node之间的距离
* X nearest
* foward + return routes pair


Work simulation(原则有先后顺序)
目前两大做题中最重要原则：
1.requirement排在第一，deadline第二。
2.有manager出现的选项无脑选manager，manager就是一个组的地头蛇。

Amazon9条主要原则
原则1：客户是上帝，requirement优先，任何影响上帝的事情都不能干，
        如某个requirement影响了上帝的体验， 
        你就是死键盘上也不能砍了，宁愿miss deadline
原则2：为长远考虑，即客户几年之后可能会出现的需求也要考虑到，
        不会为了交付短期的deadline，
        而牺牲长期的价值。（比如 global api  和 local api）
原则3：最高标准，“最高”对应上面的“长远”。
原则4：一般情况，能请示manager就请示manager，manager一般不会出错
原则5：速度很重要，决策和行动都可以改变，因此不需要进行过于广泛的推敲
        ，但提倡在深思熟虑下进行冒险。
原则6：不需要一定要坚持“非我发明”，需求帮助也是可以的，四处寻找创意
        ，并且接受长期被误导的可能
原则7：敢于承担责任，任劳任怨，比如领导说谁会java，你会你就跳出来说我会
原则8：对问题刨根问底，探究细节
原则9：服从大局（团队比个人重要）

打分不是关键，排序才是关键。
大部分情况下其实并没有deadline 和 requirement谁更好，更多还是在
这个组合中你对ddl 和 requirement整体的权衡。

每个选项可以评1~5分，most effective 是5，然后1是least effective
刚开始让你看一些介绍amazon工作环境的视频
1.上来给一段video，场景是项目的晨会，就是把team正在推进的项目描述一下，
期间会有多个项目和你有关系，后面会遇到
2.进入工作界面，可以看到接受到邮件，接收到的instant message
3.进入工作状态。会有同事给你发邮件，发信息。需要你对他们提出的问题做一些判断，也就是给解决问题的选项评分.
4.一个21题，有log分析bug，有给报告出问题结论，有判断项目 走向的
情境1：给图书馆写图书推荐系统，关于book api
两个人，在表达不同的观点
选择：tell me more
一开始其实每个人都在强调自己是对的，即使有一个人更对一些，
也应该选tell me more（原则8），选了之后会得到更多信息

情境2：选图书馆的服务器有没有开放关于实体书的api. 
两个小哥讨论图书推荐的api应该是自己做还是用现成的。
自己做api覆盖面广，但是due赶不上，别人做的能赶上due。 
requirement优先（原则2），tell me more层层递进. 

情境3：经理说咱们最近服务器老挂，什么情况？
先选看internal bug的记录
选 I think service 3 is the problem, 
but I would like to see another report to confirm
烙印，义正言辞说自己做了20年服务器，不可能有错误，
刚刚调试过服务器，不可能是内部错误。
选自己去查，问题的关键在于不要麻烦别人
增加开发过程中测试的时间/测试覆盖更多case，放5
写Manuel test，放3
还有个是unit test，也放3.
增加QA的人手，放1
让客户来当小白鼠发现问题，放1


情境4：Amazon recommendation system item，
给你推荐一些你感兴趣的item，第一个issue总是失败，
第二个issue总是显示germany
第一个问题是因为username 太长所以一直报错。
第二个问题是因为他用proxy的name来决定是不是语言了。

情境5：德国amazon除了什么问题，让你看log回答问题。问你大概哪里除了问题
亚马逊推荐广告，给英国人推了德文广告，给你log文件，
问你可能在哪，找bug in error log

情境6：员工们讨论case media network服务器最近好多compliants
有德国的，有invalid recommendation，有返回404，
找出错原因的相同点
德语因为服务器， 一个因为用户名太长，一个是有些用户的语言变成德语

情境7：具体客户ddl 只有两周，两个方案，延到四周，做完整。
另一个说先实现一部分功能做个demo，再慢慢做。
先做demo放5，按部就班四周放3， 通知其他组说两走做不完接着做美国放1

情境8：估计项目开发时间
Manager放5，找有经验的人请教4，上网查资料或是先做一段时间再估计都放3，. Waral 博客有更多文章,
还有其他裸上的就1。

情境9：一个项目时间表设计
说你是这里最会用什么语言的，比如java

情境10：安排会议
视频会议 5   三个老二开会和老二去找老大开会 3   推迟会议和邮件开会 1

情境11：搞个数据库
两周时间可以搞个数据，**ben可以帮忙，大腿priya可以帮，但是要等一周半
报告manager放5，和**合作等大腿放4，合作/等大腿是3
自己单干，cut feaure都是1. 

情境12：系统是否升级
做两个feature，一个让100%用户爽，一个让20%用户爽，
但要升级系统，升级系统自己组会爽，但是升级会推迟做的feature，
不升级吧，升级之后还得做一遍
这题的中心是不升级，先做feature，先让用户爽。
先做100的feature再升级，再做20的feature，放5
不升级，因为我们承诺要做feature，放4。
不升级，要搞定feature，可以以后推了其他ddl再升级，放3
不升级，因为对其他组没影响，我们应该focus在request上面，放2
升级，推迟这两个feature的ddl，因为升级造福子孙后代，放2
升级，不然要做两次，放1
这题的关键在于升不升级，要坚定的站在一边

情境13：新产品设计
给8周时间，选择题，让你pick up 一个features的组合要求利益最大化，
每个feature都有相应的价值，H >> M >> L 都代表远大于.
首先ddl是前提，中位数不能超过8太多，那样的话就算feature再多也没意义，
同价值，按照ddl排序，同ddl按照价值排序。

情境17：代码分析
三段一长选最长 



1.k distinct string 返回count
Count number of substrings with exactly k distinct characters
count all possible substrings (not necessarily distinct) 
that has exactly k distinct characters.
Input: aa, k = 1 Output: 3 substrings are {"a", "a", "aa"} 
Input: abc, k = 2 Output: 2 substrings are {"ab", "bc"}

def findSubstring(self, str, k):
        if str is None or len(str) == 0:
            return 0
            
        ch_count = {}
        
        n = len(str)
        start, end = 0, 0
        
        count = 0
        while end < n:
            if str[end] not in ch_count:
                ch_count[str[end]] = 0
            ch_count[str[end]] += 1
            while len(ch_count) > k:
                self.removeStart(start, ch_count, str)
                start += 1
            
            if len(ch_count) == k:
                count += self.getCount(end, str, ch_count)
                self.removeStart(start, ch_count, str)
                start += 1
            end += 1
                
        return count

    def removeStart(self, start, ch_count, str):
        ch_count[str[start]] -= 1
        if ch_count[str[start]] == 0:
            del ch_count[str[start]]
    
    def getCount(self, end, str, ch_count):
        n = len(str)
        count = 0
        while end < n:
            if str[end] not in ch_count:
                break
            count += 1
            end += 1
        
        return count

2. Minimum路线，给矩阵，求所有路线里经过的最小值的最大值
def findMaxMin(self, height):
    row, col = len(height), len(height[0])
    dp = [[-sys.maxsize for i in range(col + 1)] for j in range(row + 1)]
    for i in range(1, row + 1):
        for j in range(1, col + 1):
            if i == 1 and j == 1:
                dp[i][j] = height[0][0]
                continue
            dp[i][j] = min(height[i - 1][j - 1], max(dp[i - 1][j], dp[i][j - 1]))

    return dp[-1][-1]

3. two sum closest
def twoSumCloest(self, durations, k):

    if durations is None or len(durations) < 2:
        return [-1, -1]

    duration_list = []
    for duration in durations:
        duration_list.append((duration, index))
    duration_list.sort()

    max_sum = -sys.maxsize
    res = [-1, -1]
    start, end = 0, len(duration_list) - 1
    target = k - 30
    while start < end:
        start_d, start_idx = duration_list[start]
        end_d, end_idx = duration_list[end]
        curr_sum = start_d + end_d

        if curr_sum > target:
            end -= 1
        elif curr_sum > max_sum:
            max_sum = curr_sum
            res[0] = min(start_idx, end_idx)
            res[1] = max(start_idx, end_idx)
            start += 1
        else:
            start += 1

    return res

4. mamximum average subtree
def mas (self, root):
    if root is None:
        return None

    self.max_average = -sys.maxsize
    self.node = None
    self.find_max_average(root)

    return self.node

def find_max_average(self, root)
    if len(root.children) == 0:
        if root.val > self.max_average:
            self.max_average = root.val
            self.node = root
        return root.val, 1

    count, sum = 1, root.val
    for child in root.children:
        c_count, c_sum = self.find_max_average(child)
        count += c_count
        sum += c_sum

    if sum / count > self.max_average:
        self.max_average = sum / count
        self.node = root

    return sum, count



5.Copy List with Random Pointer
def copyRandomList(self, head):
    # write your code here
    if head == None:
        return None
        
    myMap = {}
    nHead = RandomListNode(head.label)
    myMap[head] = nHead
    p = head
    q = nHead
    while p != None:
        q.random = p.random
        if p.next != None:
            q.next = RandomListNode(p.next.label)
            myMap[p.next] = q.next
        else:
            q.next = None
        p = p.next
        q = q.next
    
    p = nHead
    while p!= None:
        if p.random != None:
            p.random = myMap[p.random]
        p = p.next
    return nHead

6.Minimum Spanning Tree
def lowestCost(self, connections):
        # Write your code here
        connections.sort(key = lambda x: (x.cost, x.city1, x.city2))
        self.father = {}
        self.count = 0
        # init
        self.initialize(connections)
        
        result = []
        # union
        for connection in connections:
            father1 = self.find(connection.city1)
            father2 = self.find(connection.city2)
            if father1 != father2:
                self.union(father1, father2)
                self.count -= 1
                result.append(connection)
        
        if self.count == 1:
            return result
        
        return []
    
def union(self, a, b):
    self.father[self.find(a)] = self.find(b)

def find(self, node):
    path = []
    while self.father[node] != node:
        path.append(node)
        node = self.father[node]
    
    for n in path:
        self.father[n] = node
    
    return node

def initialize(self, connections):
    for connection in connections:
        city1 = connection.city1
        city2 = connection.city2
        if city1 not in self.father:
            self.father[city1] = city1
            self.count += 1
        if connection.city2 not in self.father:
            self.father[city2] = city2
            self.count += 1


























