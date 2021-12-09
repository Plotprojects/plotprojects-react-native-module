package com.plotprojects.retail.android;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.plotprojects.retail.android.react.util.EventBatchUtils;
import com.plotprojects.retail.android.react.util.FilterableNotificationMarshaller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactNativeNotificationFilterReceiver extends NotificationFilterBroadcastReceiverAsync {
    private static Boolean callbackDefined = Boolean.FALSE;
    private static Map<Integer, NotificationFilterCallback> notificationFilterCallbacks = Collections.synchronizedMap(new HashMap<>());
    private static ReactApplicationContext reactApplicationContext;

    static final String EVENT_NAME = "onNotificationsToFilter";

    public static void setReactApplicationContext(ReactApplicationContext applicationContext) {
        reactApplicationContext = applicationContext;
    }

    public static Boolean isCallbackDefined() {
        return callbackDefined;
    }

    public static void setCallbackDefined() {
        callbackDefined = Boolean.TRUE;
    }

    public static void unsetCallbackDefined() {
        callbackDefined = Boolean.FALSE;
    }

    public static void passFilteredNotifications(Integer batchId, List<FilterableNotification> filteredNotifications) {
        Log.i("ReactNotificationFilter", "Received filtered notifications array: " + filteredNotifications);
        notificationFilterCallbacks.remove(batchId).passNotifications(filteredNotifications);
    }

    @Override
    public void filterNotifications(List<FilterableNotification> notifications, NotificationFilterCallback notificationFilterCallback) {
        if (callbackDefined == null || !callbackDefined) {
            notificationFilterCallback.passNotifications(notifications);
        } else {
            try {
                final WritableArray notificationsToFilter = FilterableNotificationMarshaller.toJsArray(notifications);
                final int batchId = notificationFilterCallbacks.size() + 1;
                notificationFilterCallbacks.put(batchId, notificationFilterCallback);
                final WritableMap data = EventBatchUtils.marshall(batchId, notificationsToFilter);
                reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(EVENT_NAME, data);
            } catch (Exception e) {
                Log.e("ReactNotificationFilter", "Error during filtering notifications", e);
            }
        }
    }
}
