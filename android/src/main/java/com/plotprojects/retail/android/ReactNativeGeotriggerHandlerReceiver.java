package com.plotprojects.retail.android;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableArray;
import com.plotprojects.retail.android.react.util.GeotriggerMarshaller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactNativeGeotriggerHandlerReceiver extends GeotriggerHandlerBroadcastReceiverAsync {
    private static Callback callback;
    private static Map<Integer, GeotriggerHandlerCallback> geotriggerHandlerCallbacks = Collections.synchronizedMap(new HashMap<>());

    public static Callback getCallback() {
        return callback;
    }

    public static void setCallback(Callback callback) {
        ReactNativeGeotriggerHandlerReceiver.callback = callback;
    }

    public synchronized static void passHandledGeotriggers(Integer batchId, List<Geotrigger> handledGeotriggers) {
        Log.i("ReactGeotriggerHandler", "received handled geotriggers array");
        geotriggerHandlerCallbacks.remove(batchId).passGeotriggers(handledGeotriggers);
    }

    @Override
    public synchronized void handleGeotriggers(List<Geotrigger> geotriggers, GeotriggerHandlerCallback geotriggerHandlerCallback) {
        if (callback == null) {
            geotriggerHandlerCallback.passGeotriggers(geotriggers);
        } else {
            try {
                final WritableArray geotriggersToHandle = GeotriggerMarshaller.toJsArray(geotriggers);
                final Integer batchId = geotriggerHandlerCallbacks.size() + 1;
                geotriggerHandlerCallbacks.put(batchId, geotriggerHandlerCallback);
                callback.invoke(batchId, geotriggersToHandle);
            } catch (Exception e) {
                Log.e("ReactGeotriggerHandler", "Skipping geotrigger handler due to exceptions and passing all geotriggers", e);
            }
        }
    }
}
