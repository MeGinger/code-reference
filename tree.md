**EXPRESSION TREE**

* construction
  * construction from infix/postfix/prefix expression https://www.geeksforgeeks.org/expression-tree/
     * infix expr -> postfix expr tree -> 
* evaluation
  * evaluate the expression represented by expression tree
  * evaluate prefix/postfix expression (using stack https://blog.csdn.net/linraise/article/details/20459751)

  
#Fastest
楼主的data structure考的是波兰表达式和逆波兰表达式。
楼主用stack做的，但最后面试官告诉我他的本意是想考我expression tree的。我这才慌不择路一样把expression tree说了一遍，来不及在白板上写代码了。
 不过面试官说stack也可以

#Amazon
给一个expression tree，子节点都是数字，其他节点都是符号，比如+， -， *， /。 设计一个这样的树的数据结构，实现返回运算结果的方法

```
e.g.                 +
                 /       \ 
               *          -
             /   \      /    \  
           5     10    9      7
```

表达式就是（5*10） + （9 - 7）返回52

Assumption & clarification
- 可能因为事先没问清楚，本来设计的符号是char，后来又问如果符号是sqrt怎么办，又把所有char改成string，
- 本来的设计是int 又问1/2应该返回什么 我说0，他说0.5更好，又改成double。。。
- 然后又问如果想加一个符号 比如mod怎么办。。。总之我刚开始当成算法题做的，后来通过followup发现是个设计题。。

#Twitter_OA
Parse and operate on simple expression tree

#Twitter_onsite
Build expression tree (lintcode 原题，给定3*2+（5-7）*8 一个expression， 构建expressin tree)。 
但楼主刷这个题已经很久以前了，只记得可以先把infix expression编程postfix expression tree，再用stack可以做，
但忘了最后stack那一步是怎么做的了，就只写了怎么把infix变成postfix(Shunting-yard algorithm)，
但这哥们明显没听懂这个算法怎么work的，也不知道他到底想让写什么样的算法. 

#PocketGem_2015
好在面试10分钟之前看了glassdoor的面经，挺经典的Convert Ternary Expression to a Binary Tree。我用的two stacks
https://www.geeksforgeeks.org/convert-ternary-expression-binary-tree/

**HUFFMAN TREE**

#Google 给一个list，建一个 huffman tree 
https://www.siggraph.org/education/materials/HyperGraph/video/mpeg/mpegfaq/huffman_tutorial.html

