package assignment5;

import java.util.Map;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractOrderClient {
    private Order order;
    private AbstractKitchenServer kitchenServer;
    Timer pollingTimer;
    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#receiveOrder(Order)}
     * Also start {@link #startPollingServer(String)}
     */
    abstract public void submitOrder() throws ExecutionException, InterruptedException;

    /**
     * Start a new task with a periodic timer {@link #pollingTimer}
     * to ask a server periodically about the order status {@link AbstractKitchenServer#checkStatus(String)}.
     *
     * Call {@link #pickUpOrder()} when status is {@link OrderStatus#Ready} and stop the {@link #pollingTimer}.
     */
    abstract protected void startPollingServer(String orderId) throws InterruptedException;

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#serveOrder(String)}
     */
    abstract protected void pickUpOrder() throws InterruptedException, ExecutionException;
}
