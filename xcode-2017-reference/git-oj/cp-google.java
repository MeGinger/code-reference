// stock price update
Design a data structure to show stock information of a company in a day: current price, high price and low price.
functions that need implementations:
1. update(timestamp t2, price): timestamp can be an old one or a new one. 
2. delete(timestamp t1): delete the data at timestamp t1
3. getCurrentPrice(), getHighPrice(), getLowPrice().

public static class Stock{

  public TreeMap<Integer, Integer> priceMap;
  public TreeMap<Integer, Integer> countMap;

  public Stock(){
    priceMap = new TreeMap<Integer, Integer>();  //key is the timestamp and value is the price
    countMap = new TreeMap<Integer, Integer>();  // key is the price and value is the count of the price
  }

  // update(timestamp t2, price): timestamp can be an old one or a new one.
  public void update(int timestamp, int price){
    if(priceMap.containsKey(timestamp)){
      int origin = priceMap.get(timestamp);
      if(countMap.get(origin) == 1) countMap.remove(origin);
      else countMap.put(origin, countMap.get(origin) - 1);
    }
    priceMap.put(timestamp, price);
    countMap.put(price, countMap.getOrDefault(price, 0) + 1);
  }

  //delete(timestamp t1): delete the data at timestamp t1
  public void delete(int timestamp){
    if(! priceMap.containsKey(timestamp)) return ;
    int price = priceMap.get(timestamp);
    if(countMap.get(price) == 1) countMap.remove(price);
    else countMap.put(price, countMap.get(price) - 1);
    priceMap.remove(timestamp);
  }

  public int getCurrentPrice(){
    if(priceMap.size() == 0) return -1;
    else return priceMap.lastEntry().getValue();
  }

  public int getHighPrice(){
    if(countMap.size() == 0) return -1;
    else return countMap.lastKey();
  }

  public int getLowPrice(){
    if(countMap.size() == 0) return -1;
    else return countMap.firstKey();
  }
}