之后 问我 工作经验相关,  如何test 自己设计的系统 可用, 我说了 spring eureka 里可以提供dashboard 检测 内存占用, cpu占用, 之后 可以做集成测试, 压力测试..
面试官 做了笔记, 问还有什么, 
我: 想不出还有什么, 其实还可以说 to make available, we can provide replica instances, to avoid single point failure. 但我觉得他更考察你的经验, 相关的tool.
如果让我再回答一遍 我可能这么说:
first, make every service functionally work, pass the unit test. 
second, make integration work, RPC call,  (microservices集成测试, 大神给点意见, 什么工具之类的)
third, high available (pressure test, single point failure)
其实最重点不知道他考哪里, 感觉就tm想问 unit test? 其实有时候不知道对面问题的level是什么

https certificate的基本原理，@transctional的用法

如何处理starvation，
以下内容需要积分高于 155 才可浏览
. 留学申请论坛-一亩三分地
比如如果一直有小的order进来，大的order永远不会被process（我之前给的是greedy的思路）。
还有如果有大量的order，怎样提高效率，这题我一开始答的distributed processing，明显不是面试官想要的答案，最后也没想出什么好的方法面试官跟我说it's ok 这个是above and beyond question。