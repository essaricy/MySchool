package com.myschool.middleware.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class QueueLifeCycleStages.
 */
public class QueueLifeCycleMonitor {

    /** The total listeners running. */
    private int totalListenersRunning;

    /** The busy listeners. */
    private int busyListeners;

    /** The listener status map. */
    private Map<String, Integer> listenerStatusMap;

    /**
     * The Class QueueLifeCycleStages.
     */
    public static class QueueLifeCycleStages {

        /** The Constant STARTED. */
        public static final int STARTED = 1;

        /** The Constant SUCCESS_STARTED. */
        public static final int SUCCESS_STARTED = 11;

        /** The Constant FAIL_STARTED. */
        public static final int FAIL_STARTED = 12;

        /** The Constant ERROR_STARTED. */
        public static final int ERROR_STARTED = 13;

        /** The Constant DIED. */
        public static final int DIED = 2;

        /** The Constant SUCCESS_DIED. */
        public static final int SUCCESS_DIED = 21;

        /** The Constant FAIL_DIED. */
        public static final int FAIL_DIED = 22;

        /** The Constant ERROR_DIED. */
        public static final int ERROR_DIED = 23;

        /** The Constant BUSY. */
        public static final int BUSY = 3;

        /** The Constant IDLE. */
        public static final int IDLE = 4;
    }

    /**
     * Instantiates a new queue life cycle monitor.
     */
    public QueueLifeCycleMonitor() {
        listenerStatusMap = new HashMap<String, Integer>();
    }

    /**
     * Update queue status.
     *
     * @param queueName the queue name
     * @param status the status
     */
    public synchronized void updateQueueStatus(String queueName, int status) {
        if (listenerStatusMap.containsKey(queueName)) {
            if (status == QueueLifeCycleStages.STARTED) {
                totalListenersRunning++;
            } else if (status == QueueLifeCycleStages.DIED) {
                totalListenersRunning--;
            } else if (status == QueueLifeCycleStages.BUSY) {
                if (busyListeners < totalListenersRunning) {
                    busyListeners++;
                }
            } else if (status == QueueLifeCycleStages.IDLE) {
                if (busyListeners > 0) {
                    busyListeners--;
                }
            }
        } else {
            if (status == QueueLifeCycleStages.STARTED) {
                totalListenersRunning++;
            }
        }
        listenerStatusMap.put(queueName, status);
    }

    /**
     * Gets the total listeners running.
     *
     * @return the total listeners running
     */
    public synchronized int getTotalListenersRunning() {
        return totalListenersRunning;
    }

    /**
     * Gets the busy listeners.
     *
     * @return the busy listeners
     */
    public synchronized int getBusyListeners() {
        return busyListeners;
    }

    /**
     * Removes the queue stats.
     */
    public synchronized void removeQueueStats() {
        if (listenerStatusMap != null) {
            listenerStatusMap.clear();
        }
        totalListenersRunning = 0;
        busyListeners = 0;
    }

}
