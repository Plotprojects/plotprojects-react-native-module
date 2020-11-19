package com.plotprojects.retail.android.react.util;

import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.plotprojects.retail.android.NotificationTrigger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class NotificationTriggerMarshaller {
    private NotificationTriggerMarshaller() {
    }

    public static WritableArray toJsArray(Collection<NotificationTrigger> notifications) {
        final List<Bundle> notificationBundles = new ArrayList<>(notifications.size());
        for (NotificationTrigger n : notifications) {
            notificationBundles.add(toBundle(n));
        }
        return Arguments.fromList(notificationBundles);
    }

    public static WritableMap toJs(NotificationTrigger n) {
        return Arguments.fromBundle(toBundle(n));
    }

    private static Bundle toBundle(NotificationTrigger n) {
        final Bundle bundle = new Bundle();

        if (n.getTriggerProperties() != null) {
            final Bundle triggerProperties = new Bundle();
            for (Map.Entry<String, String> entry : n.getTriggerProperties().entrySet()) {
                triggerProperties.putString(entry.getKey(), entry.getValue());
            }
            bundle.putBundle("triggerProperties", triggerProperties);
        }

        if (n.getMatchPayload() != null) {
            final Bundle matchPayload = new Bundle();
            for (Map.Entry<String, String> entry : n.getMatchPayload().entrySet()) {
                matchPayload.putString(entry.getKey(), entry.getValue());
            }
            bundle.putBundle("matchPayload", matchPayload);
        }

        bundle.putString("id", n.getId());
        bundle.putString("campaignId", n.getCampaignId());
        bundle.putString("regionId", n.getRegionId());
        bundle.putString("matchId", n.getMatchId());
        bundle.putString("data", n.getData());
        bundle.putDouble("geofenceLatitude", n.getGeofenceLatitude());
        bundle.putDouble("geofenceLongitude", n.getGeofenceLongitude());
        bundle.putString("trigger", n.getTrigger());
        bundle.putInt("dwellingMinutes", n.getDwellingMinutes());
        bundle.putInt("matchRange", n.getMatchRange());
        bundle.putString("regionType", n.getRegionType());
        bundle.putString("message", n.getMessage());
        bundle.putString("handlerType", n.getHandlerType());

        return bundle;
    }
}
