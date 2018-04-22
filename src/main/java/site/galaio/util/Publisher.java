package site.galaio.util;

import java.util.NoSuchElementException;

/**
 * Created by tianyi on 2018/4/20.
 */
public class Publisher {

    private volatile Node subscribers = null;

    /**
     * publish an event using deliveryAgent.
     * @param deliveryAgent
     */
    public void publish(Distributor deliveryAgent) {
        for(Node cursor = subscribers; cursor!=null;cursor=cursor.next) {
            cursor.accept(deliveryAgent);
        }
    }

    /**
     * subscribe a new observer.
     * @param subscriber
     */
    public void subscribe(Object subscriber) {
        subscribers = new Node(subscriber, subscribers);
    }

    public void cancelSubscription(Object subscriber) {

    }


    public interface Distributor {
        /**
         * the visitor pattern's visit method.
         * @param subscriber
         */
        void deliverTo(Object subscriber);
    }

    private class Node {
        public final Object subscriber;
        public final Node next;

        private Node(Object subscriber, Node next) {
            this.subscriber = subscriber;
            this.next = next;
        }

        public Node remove(Object target) {
            if (target == subscriber) {
                return next;
            }
            // target is not in list.
            if (next == null) {
                throw new NoSuchElementException(target.toString());
            }
            return new Node(subscriber, next.remove(target));
        }

        /**
         * a public method to access subscriber.
         * @param deliveryAgent the param is a visitor.
         */
        public void accept(Distributor deliveryAgent) {
            deliveryAgent.deliverTo(subscriber);
        }

    }


}
