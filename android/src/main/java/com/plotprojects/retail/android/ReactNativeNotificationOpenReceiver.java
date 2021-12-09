package com.plotprojects.retail.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.plotprojects.retail.android.react.util.FilterableNotificationMarshaller;

public class ReactNativeNotificationOpenReceiver extends BroadcastReceiver {
    private static Boolean callbackDefined = Boolean.FALSE;
    private static ReactApplicationContext reactApplicationContext;

    static final String EVENT_NAME = "onNotificationOpened";

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

    @Override
    public void onReceive(Context context, Intent intent) {
        if (callbackDefined == null || !callbackDefined) {
            return;
        } else {
            try {
                final FilterableNotification notification = intent.getParcelableExtra("notification");
                if (notification.getData() == null) {
                    return;
                } else {
                    final WritableMap data = FilterableNotificationMarshaller.toJs(notification);
                    reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(EVENT_NAME, data);
                }
            } catch (Exception e) {
                Log.e("ReactNotificationOpen", "Error during handling notification open", e);
            }
        }
    }
}
