package assignment5;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class KitchenServer extends AbstractKitchenServer{
    ExecutorService threadPool;
    Map<String, Order> orderMap;

    public KitchenServer()
    {
        this.threadPool = Executors.newFixedThreadPool(10);
        this.orderMap = new HashMap<String,Order>();
    }

    /**
     * This method should save the order to the map
     * and return a confirmation that the order is received {@link KitchenStatus#Received}
     * or a rejection {@link KitchenStatus#Rejected}
     *
     * When an order is received, a {@link #cook(Order)} task should be launced in th {@link #threadPool}
     *
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException, ExecutionException {
        CompletableFuture<KitchenStatus> future = new CompletableFuture<>();
        try{
            orderMap.put(String.valueOf(order.getOrderID()), order);
            future.complete(KitchenStatus.Received);
            threadPool.submit(() -> {
                try {
                    cook(order);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch(Exception e) {
            future.complete(KitchenStatus.Rejected);
        }
        return future;
    }

    /**
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        CompletableFuture<OrderStatus> future = new CompletableFuture<>();
        Order order = orderMap.get(orderID);
        OrderStatus status = order.getStatus();
        future.complete(status);
        return future;
    }

    /**
     * Allows a client to picks up the order if it is ready {@link OrderStatus#Ready}.
     * Should remove the order from the {@link #orderMap}
     *
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */

    @Override
    public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {
        CompletableFuture<KitchenStatus> future = new CompletableFuture();
            try {
                Order order = orderMap.get(orderID);
                Thread.sleep(1000);
                order.setStatus(OrderStatus.Served);
                Thread.sleep(1000);
                future.complete(KitchenStatus.Served);
            } catch (Exception e)
            {
                System.out.println(e);
            }
            return future;
    }

    /**
     * Simulate cooking in this method.
     * Execute random delay and update the order status
     * {@link OrderStatus#Received} -> {@link OrderStatus#BeingPrepared} -> {@link OrderStatus#Ready}
     * @return
     */

    @Override
    public void cook(Order order) throws InterruptedException {
        try
        {
            order.setStatus(OrderStatus.BeingPrepared);
            orderMap.replace(OrderStatus.BeingPrepared.text, order);
            Thread.sleep(1500);

            order.setStatus(OrderStatus.Ready);
            orderMap.replace(OrderStatus.Ready.text, order);
            Thread.sleep(1000);

            Thread.sleep(2000);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
