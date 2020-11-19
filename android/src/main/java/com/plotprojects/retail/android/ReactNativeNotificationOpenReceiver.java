package com.plotprojects.retail.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.facebook.react.bridge.Callback;
import com.plotprojects.retail.android.react.util.FilterableNotificationMarshaller;

public class ReactNativeNotificationOpenReceiver extends BroadcastReceiver {
    private static Callback callback;

    public static Callback getCallback() {
        return callback;
    }

    public static void setCallback(Callback callback) {
        ReactNativeNotificationOpenReceiver.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (callback == null) {
            return;
        } else {
            final FilterableNotification notification = intent.getParcelableExtra("notification");
            if (notification.getData() == null) {
                return;
            } else {
                callback.invoke(FilterableNotificationMarshaller.toJs(notification));
            }
        }
    }
}
