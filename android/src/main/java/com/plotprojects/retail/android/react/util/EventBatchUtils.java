package com.plotprojects.retail.android.react.util;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public class EventBatchUtils {
    public static WritableMap marshall(final int batchId, final WritableArray data) {
        final WritableMap result = Arguments.createMap();
        result.putArray("data", data);
        result.putInt("batchId", batchId);
        return result;
    }
}
