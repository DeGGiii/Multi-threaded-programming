package assignment5;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class OrderClient extends AbstractOrderClient {

    private Order order;
    private KitchenServer kitchenServer;
    Timer pollingTimer;
    GenericRestaurantForm gui;

    public OrderClient(GenericRestaurantForm gui, KitchenServer kitchenServer)
    {
        this.kitchenServer = kitchenServer;
        this.gui = gui;
        this.order = new Order();
    }

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#receiveOrder(Order)}
     * Also start {@link #startPollingServer(String)}
     */

    @Override
    public void submitOrder() throws ExecutionException, InterruptedException {
        gui.changeStatusText("Submitting order...");
        CompletableFuture kitchenStatus = kitchenServer.receiveOrder(order);
        try
        {
            if (kitchenStatus.get() == KitchenStatus.Received)
            {
                Thread.sleep(1500);
                gui.changeStatusText("Order received...");
                Thread.sleep(1000);
                startPollingServer(order.getOrderID());
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Start a new task with a periodic timer {@link #pollingTimer}
     * to ask a server periodically about the order status {@link AbstractKitchenServer#checkStatus(String)}.
     *
     * Call {@link #pickUpOrder()} when status is {@link OrderStatus#Ready} and stop the {@link #pollingTimer}.
     */

    @Override
    protected void startPollingServer(String orderId) throws InterruptedException {
        pollingTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int i = 1;
            @Override
            public void run() {
                gui.changeStatusText("Waiting for order to be ready...");
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (i == 1) {
                    try {
                        CompletableFuture orderStatus = kitchenServer.checkStatus(order.getOrderID());
                        if (orderStatus.get() == OrderStatus.Ready) {
                            gui.changeStatusText("Order is ready for pickup...");
                            Thread.sleep(1500);
                            pickUpOrder();
                            cancel();
                            i = 2;
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        pollingTimer.scheduleAtFixedRate(timerTask, 500, 20000);
    }
    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#serveOrder(String)}
     */

    @Override
    protected void pickUpOrder() throws InterruptedException, ExecutionException {
        gui.changeStatusText("Order is getting picked up...");
        Thread.sleep(1000);
       CompletableFuture  kitchenStatus = kitchenServer.serveOrder(order.getOrderID());
       if (kitchenStatus.get() == KitchenStatus.Served)
       {
           Thread.sleep(2000);
           gui.changeStatusText("Order is "+ order.getStatus().text);
       }
        else
           System.out.println("something went wrong");
    }

    public Order getOrder() {
        return order;
    }
}
