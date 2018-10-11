package com.company.ood;

import java.util.List;
import java.util.Map;

/*
When a customer selects for a delivery to be made-

1)They should be assigned a locker number closest to his zip code.
2)They should be allowed to cancel a locker delivery until the order has been shipped.

3)You can assume that you have several concurrent requests coming in.
When I asked for the hourly/monthly requests we should expectedly be processing,
I was told to assume it is tight but not extraordinary.
However,the design should allow to scale up.
4)Service should be highly available.

I was also asked to think about how an update would be made to make a locker available when an order is picked up.
*/

enum OrderItemStatus {
    CANCELED, READY, SHIPPED, DELIVERED, PICKEDUP;
}

class OrderItem {
    int length;
    int width;
    int height;
    OrderItemStatus status;
}

class Container {
    int length;
    int width;
    int height;

    boolean isUsed;
    OrderItem orderItem;

    public void drop(OrderItem orderItem) {
        this.orderItem = orderItem;
        this.orderItem.status = OrderItemStatus.DELIVERED;
        this.isUsed = true;
    }

    public OrderItem pick() {
        this.orderItem.status = OrderItemStatus.PICKEDUP;
        this.isUsed = false;
        return this.orderItem;
    }
}

class Locker {
    Point location;
    List<Container> containers;

    public Container findContainerForItem(OrderItem item) {
        return null;
    }
}

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class AMNLockerSystem {
    private List<Locker> lockers;
    private Map<OrderItem, Locker> deliveries;

    public Locker findClosestLocker(Point destination) {
        int closesDistance = Integer.MAX_VALUE;
        Locker closestLocker = null;
        for (Locker l : lockers) {
            int distance = Math.abs(destination.x - l.location.x) + Math.abs(destination.y - l.location.y);
            if (closesDistance > distance) {
                closesDistance = distance;
                closestLocker = l;
            }
        }
        return closestLocker;
    }

    public Locker[][] getLockerDistanceGrid(int cityLength, int cityWidth) {
        Locker[][] grid = new Locker[cityLength][cityWidth];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = findClosestLocker(new Point(row + 1, col + 1));
            }
        }
        return grid;
    }

    public void deliver(Point destiniation, OrderItem orderItem) {
        Locker closestLocker = findClosestLocker(destiniation);
        if (closestLocker == null) {
            // throw new NotEnoughLockerException
        }

        this.deliveries.put(orderItem, closestLocker);
    }

    public void cancel(OrderItem orderItem) {
        if (orderItem.status == OrderItemStatus.READY || orderItem.status == OrderItemStatus.CANCELED) {
            // throw exception
        }

        orderItem.status = OrderItemStatus.CANCELED;
        this.deliveries.remove(orderItem);
    }
}

package com.company.ood;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 设计整个亚马逊的物品、 仓库、卡车互动系统。
// 仓库遍布美国，卡车有油量，仓库之间有些有路径有些没有。
// 我搞了几个类，包括物品、仓库、卡车。
// 要求实现一些功能诸如，从仓库A把一定的物品分给仓库B，分派卡车，装货、卸货函数。
public class AMNFeature {
    // undirected graph -> represented as directed graph
    // int[][], List<Integer>[]
    private Map<Warehouse, List<Warehouse>> warehouseGraph;
    private List<Trunk> trunks;

    public AMNFeature(List<Trunk> trunks) {
        this.trunks = trunks;
    }

    public void fetchFromSource(Warehouse source, Trunk trunk, List<Item> itemsToSend) {
        source.removeItems(itemsToSend);
        for (Item item : itemsToSend) {
            trunk.addItems(item);
        }
    }

    public void goTo(Trunk trunk, Warehouse source, Warehouse target) {
        trunk.goTo(source, target, this.warehouseGraph);
    }

    public void sendToTarget(Warehouse target, Trunk trunk) {
        List<Item> items = trunk.getItems();
        target.addItems(items);
        for (Item item : items) {
            trunk.removeItem(item);
        }
    }

    public Trunk getAvailableTrunk() {
        return trunks.get(0);
    }
}

class Item {
    public int itemId;
    public String name;
}

class Warehouse {
    public int warehouseId;
    public String location;
    public List<Item> items;

    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }

    public void removeItems(List<Item> items) {
        this.items.removeAll(items);
    }
}

class Trunk {
    public int trunkId;
    public int oil;
    public List<Item> itemsToCarry;

    public Trunk(int oil) {
        this.itemsToCarry = new ArrayList<>();
        this.oil = oil;
    }

    public void addItems(Item item) {
        this.itemsToCarry.add(item);
    }

    public void removeItem(Item item) {
        this.itemsToCarry.remove(item);
    }

    public List<Item> getItems() {
        return this.itemsToCarry;
    }

    public void goTo(Warehouse start, Warehouse destination, Map<Warehouse, List<Warehouse>> warehouseGraph) {

    }
}

package com.company.ood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

    public JsonObject parse(String json) {
        return null;
    }

    public String seriazlie(JsonObject jsonObject) {
        return null;
    }
}

abstract class JsonValue {

}

class JsonNumber extends JsonValue {
    private final String string;

    JsonNumber(String string) {
        if (string == null) {
            throw new NullPointerException("string is null");
        }
        this.string = string;
    }

    public boolean isNumber() {
        return true;
    }

}

class JsonLiteral extends JsonValue {
    private final String value;
    private final boolean isNull;
    private final boolean isTrue;
    private final boolean isFalse;

    JsonLiteral(String value) {
        this.value = value;
        isNull = "null".equals(value);
        isTrue = "true".equals(value);
        isFalse = "false".equals(value);
    }

    public boolean isNull() {
        return isNull;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public boolean isFalse() {
        return isFalse;
    }

    public boolean isBoolean() {
        return isTrue || isFalse;
    }
}

class JsonString extends JsonValue {

    private final String string;

    JsonString(String string) {
        if (string == null) {
            throw new NullPointerException("string is null");
        }
        this.string = string;
    }

    public boolean isString() {
        return true;
    }

    public String asString() {
        return string;
    }
}

class JsonObject extends JsonValue {
    private final Map<String, JsonValue> pairs;

    public JsonObject() {
        pairs = new HashMap<>();
    }

    public JsonValue get(String property) {
        return pairs.get(property);
    }
}

class JsonArray extends JsonValue {
    private final List<JsonValue> values;

    public JsonArray() {
        values = new ArrayList<JsonValue>();
    }
}

package com.company.ood;


interface AENode {
    public double evaluate() throws EvaluationException;
}

class AEValueNode implements AENode {
    private double value;

    public AEValueNode(double value) {
        this.value = value;
    }

    public double evaluate() throws EvaluationException {
        return value;
    }

}

public class ExpressionTree implements AENode {
    protected char operator;
    protected AENode left, right; //might be operator node or value node, thus use the general node type here

    public ExpressionTree(char operator, AENode left, AENode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public double evaluate() throws EvaluationException {
        if (left != null && right != null) {
            switch (operator) {
                case '+':
                    return left.evaluate() + right.evaluate();
                case '-':
                    return left.evaluate() - right.evaluate();
                case '*':
                    return left.evaluate() * right.evaluate();
                case '/':
                    return left.evaluate() / right.evaluate();
                default:
                    throw new EvaluationException("The operator is invalid");
            }

        }
        throw new EvaluationException();
    }
}

@SuppressWarnings("serial")
class EvaluationException extends Exception {
    public EvaluationException() {
        System.out.println("This AE node is not a valid node");
    }

    public EvaluationException(String error) {
        System.out.println(error);
    }

}
