package com.plotprojects.retail.android.react;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.plotprojects.retail.android.Plot;
import com.plotprojects.retail.android.ReactNativeGeotriggerHandlerReceiver;
import com.plotprojects.retail.android.ReactNativeNotificationFilterReceiver;
import com.plotprojects.retail.android.ReactNativeNotificationOpenReceiver;
import com.plotprojects.retail.android.react.util.FilterableNotificationMarshaller;
import com.plotprojects.retail.android.react.util.GeotriggerMarshaller;
import com.plotprojects.retail.android.react.util.NotificationTriggerMarshaller;

import org.json.JSONException;

import static com.plotprojects.retail.android.ReactNativeGeotriggerHandlerReceiver.passHandledGeotriggers;
import static com.plotprojects.retail.android.ReactNativeNotificationFilterReceiver.passFilteredNotifications;

public class PlotProjectsReactModuleModule extends ReactContextBaseJavaModule {

    private static final String LOG_TAG = "PlotProjectsReactModule";
    private final ReactApplicationContext reactContext;

    public PlotProjectsReactModuleModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "PlotProjectsReactModule";
    }

//    @ReactMethod
//    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
//        // TODO: Implement some actually useful functionality
//        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
//    }

    @ReactMethod
    public void initialize() {
        Plot.init(reactContext);
    }

    @ReactMethod
    public void enable() {
        Plot.enable();
    }

    @ReactMethod
    public void disable() {
        Plot.disable();
    }

    @ReactMethod
    public void isEnabled(Callback callback) {
        callback.invoke(Plot.isEnabled());
    }

    @ReactMethod
    public void setAdvertisingId(String advertisingId, Boolean limitAdTracking) {
        Plot.setAdvertisingId(advertisingId, limitAdTracking);
    }

    @ReactMethod
    public void sendAttributionEvent(String actionName, String itemId) {
        Log.d(LOG_TAG, "Setting attribution event");
        if (itemId == null) {
            Plot.sendAttributionEvent(actionName);
        } else {
            Plot.sendAttributionEvent(actionName, itemId);
        }
    }

    @ReactMethod
    public void setStringSegmentationProperty(String name, String property) {
        Plot.setStringSegmentationProperty(name, property);
    }

    @ReactMethod
    public void setBooleanSegmentationProperty(String name, boolean property) {
        Plot.setBooleanSegmentationProperty(name, property);
    }

    @ReactMethod
    public void setDoubleSegmentationProperty(String name, double property) {
        Plot.setDoubleSegmentationProperty(name, property);
    }

    @ReactMethod
    public void setLongSegmentationProperty(String name, long property) {
        Plot.setLongSegmentationProperty(name, property);
    }

    @ReactMethod
    public void setDateSegmentationProperty(String name, long property) {
        Plot.setDateSegmentationProperty(name, property);
    }

    @ReactMethod
    public void registerNotificationFilter(Callback callback) {
        Log.d(LOG_TAG, "Registering notification filter");
        ReactNativeNotificationFilterReceiver.setCallback(callback);
    }

    @ReactMethod
    public void filterNotifications(int batchId, String filteredNotificationsString) {
        try {
            Log.d(LOG_TAG, "Filtered notifications: " + filteredNotificationsString);
            passFilteredNotifications(batchId, FilterableNotificationMarshaller.fromJsArray(filteredNotificationsString));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error while filtering notifications", e);
        }
    }

    @ReactMethod
    public void registerGeotriggerHandler(Callback callback) {
        Log.d(LOG_TAG, "Registering geotrigger handler");
        ReactNativeGeotriggerHandlerReceiver.setCallback(callback);
    }

    @ReactMethod
    public void handleGeotriggers(int batchId, String handledGeotriggersString) {
        try {
            Log.d(LOG_TAG, "Handled geotriggers: " + handledGeotriggersString);
            passHandledGeotriggers(batchId, GeotriggerMarshaller.fromJsArray(handledGeotriggersString));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error while handling geotriggers", e);
        }
    }

    @ReactMethod
    public void registerNotificationOpenHandler(Callback callback) {
        Log.d(LOG_TAG, "Registering notification open handler");
        ReactNativeNotificationOpenReceiver.setCallback(callback);
    }

    @ReactMethod
    public void getLoadedNotifications(Callback callback) {
        callback.invoke(NotificationTriggerMarshaller.toJsArray(Plot.getLoadedNotifications()));
    }

    @ReactMethod
    public void getLoadedGeotriggers(Callback callback) {
        callback.invoke(GeotriggerMarshaller.toJsArray(Plot.getLoadedGeotriggers()));
    }

    @ReactMethod
    public void mailDebugLog() {
        Plot.mailDebugLog();
    }

}


