package com.plotprojects.retail.android;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableArray;
import com.plotprojects.retail.android.react.util.FilterableNotificationMarshaller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactNativeNotificationFilterReceiver extends NotificationFilterBroadcastReceiverAsync {
    private static Callback callback;
    private static Map<Integer, NotificationFilterCallback> notificationFilterCallbacks = Collections.synchronizedMap(new HashMap<>());

    public static Callback getCallback() {
        return callback;
    }

    public static void setCallback(Callback callback) {
        ReactNativeNotificationFilterReceiver.callback = callback;
    }

    public synchronized static void passFilteredNotifications(Integer batchId, List<FilterableNotification> filteredNotifications) {
        Log.i("ReactNotificationFilter", "Received filtered notifications array: " + filteredNotifications);
        notificationFilterCallbacks.remove(batchId).passNotifications(filteredNotifications);
    }

    @Override
    public synchronized void filterNotifications(List<FilterableNotification> notifications, NotificationFilterCallback notificationFilterCallback) {
        if (callback == null) {
            notificationFilterCallback.passNotifications(notifications);
        } else {
            try {
                final WritableArray notificationsToFilter = FilterableNotificationMarshaller.toJsArray(notifications);
                final Integer batchId = notificationFilterCallbacks.size() + 1;
                notificationFilterCallbacks.put(batchId, notificationFilterCallback);
                callback.invoke(batchId, notificationsToFilter);
            } catch (Exception e) {
                Log.e("ReactNotificationFilter", "Error during filtering notifications", e);
            }

        }
    }
}
