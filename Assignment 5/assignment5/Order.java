package assignment5;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderID;
    private List<OrderItem> orderList;
    private boolean sent;
    private boolean done;
    private OrderStatus status = OrderStatus.NotSent;

    public Order() {
        this.orderID = UUID.randomUUID().toString();
        orderList = new ArrayList<>();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getOrderID() {
        return orderID;
    }

    public void addOrderItem(OrderItem item) {
        orderList.add(item);
    }

    public void removeOrderItem(OrderItem item) {
        orderList.remove(item);
    }

    public List<OrderItem> getOrderList() {
        return orderList;
    }

    public String[] getNamesFromOrderList() {
        return orderList.stream().map(item -> item.getName()).toArray(String[]::new);
    }
}