Best Time to Buy and Sell Stock

Comlete a transaction

class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int min = prices[0];
        int profit = 0; // start from 0
        
        for (int i = 1; i < prices.length; i++) {
            profit = Math.max(profit, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        
        return profit;
    }
}


Best time to buy and sell stock II

You may complete as many transactions as you like 
(i.e., buy one and sell one share of the stock multiple times).
Note: You may not engage in multiple transactions at the same time 

class Solution {
    public int maxProfit(int[] prices) {
        
        int profit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        
        return profit;
    }
}
