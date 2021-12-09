package com.plotprojects.retail.android;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.plotprojects.retail.android.react.util.EventBatchUtils;
import com.plotprojects.retail.android.react.util.GeotriggerMarshaller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactNativeGeotriggerHandlerReceiver extends GeotriggerHandlerBroadcastReceiverAsync {
    private static Boolean callbackDefined = Boolean.FALSE;
    private static Map<Integer, GeotriggerHandlerCallback> geotriggerHandlerCallbacks = Collections.synchronizedMap(new HashMap<>());
    private static ReactApplicationContext reactApplicationContext;

    static final String EVENT_NAME = "onGeotriggersToHandle";

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

    public static void passHandledGeotriggers(Integer batchId, List<Geotrigger> handledGeotriggers) {
        Log.i("ReactGeotriggerHandler", "received handled geotriggers array");
        geotriggerHandlerCallbacks.remove(batchId).passGeotriggers(handledGeotriggers);
    }

    @Override
    public void handleGeotriggers(List<Geotrigger> geotriggers, GeotriggerHandlerCallback geotriggerHandlerCallback) {
        if (callbackDefined == null || !callbackDefined) {
            geotriggerHandlerCallback.passGeotriggers(geotriggers);
        } else {
            try {
                final WritableArray geotriggersToHandle = GeotriggerMarshaller.toJsArray(geotriggers);
                final int batchId = geotriggerHandlerCallbacks.size() + 1;
                geotriggerHandlerCallbacks.put(batchId, geotriggerHandlerCallback);
                final WritableMap data = EventBatchUtils.marshall(batchId, geotriggersToHandle);
                reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(EVENT_NAME, data);
            } catch (Exception e) {
                Log.e("ReactGeotriggerHandler", "Skipping geotrigger handler due to exceptions and passing all geotriggers", e);
            }
        }
    }
}
