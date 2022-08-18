package com.plotprojects.retail.android.react.util;

import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.plotprojects.retail.android.FilterableNotification;
import com.plotprojects.retail.android.internal.util.None;
import com.plotprojects.retail.android.internal.util.Option;
import com.plotprojects.retail.android.internal.util.Some;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class FilterableNotificationMarshaller {

    private FilterableNotificationMarshaller() {
    }

    public static WritableArray toJsArray(Collection<FilterableNotification> notifications) {
        final List<Bundle> notificationBundles = new ArrayList<>(notifications.size());
        for (FilterableNotification n : notifications) {
            notificationBundles.add(toBundle(n));
        }
        return Arguments.fromList(notificationBundles);
    }

    public static WritableMap toJs(FilterableNotification n) {
        return Arguments.fromBundle(toBundle(n));
    }

    public static List<FilterableNotification> fromJsArray(String jsArray) throws JSONException {
        final JSONArray notificationsJsonArray = new JSONArray(jsArray);
        if (notificationsJsonArray.length() > 0) {
            final List<FilterableNotification> result = new ArrayList<FilterableNotification>(notificationsJsonArray.length());
            for (int i = 0; i < notificationsJsonArray.length(); i++) {
                result.add(fromJs(notificationsJsonArray.getJSONObject(i)));
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    public static FilterableNotification fromJs(JSONObject n) throws JSONException {
        final Map<String, String> triggerProperties = new HashMap<>();
        if (n.has("triggerProperties")) {
            final JSONObject triggerPropertiesJson = n.getJSONObject("triggerProperties");
            for (Iterator<String> it = triggerPropertiesJson.keys(); it.hasNext(); ) {
                String k = it.next();
                triggerProperties.put(k, triggerPropertiesJson.get(k).toString());
            }
        }

        final Map<String, String> matchPayload = new HashMap<>();
        if (n.has("matchPayload")) {
            final JSONObject matchPayloadJson = n.getJSONObject("matchPayload");
            for (Iterator<String> it = matchPayloadJson.keys(); it.hasNext(); ) {
                String k = it.next();
                matchPayload.put(k, matchPayloadJson.get(k).toString());
            }
        }

        final Map<String, String> customRegionFields = new HashMap<>();
        if (n.has("customRegionFields")) {
            final JSONObject customRegionFieldsJson = n.getJSONObject("customRegionFields");
            for (Iterator<String> it = customRegionFieldsJson.keys(); it.hasNext(); ) {
                String k = it.next();
                customRegionFields.put(k, customRegionFieldsJson.get(k).toString());
            }
        }

        final String campaignId = n.getString("campaignId");
        final Option<String> regionId = n.has("regionId") ? new Some(n.getString("regionId")) : None.getInstance();
        final String experimentId = n.getString("experimentId");
        final int experimentMessageNumber = n.getInt("experimentMessageNumber");
        final String matchId = n.getString("matchId");
        final String message = n.getString("message");
        final String data = n.getString("data");
        final double geofenceLatitude = n.has("geofenceLatitude") ? n.getDouble("geofenceLatitude") : Double.NaN;
        final double geofenceLongitude = n.has("geofenceLongitude") ? n.getDouble("geofenceLongitude") : Double.NaN;
        final Option<Integer> matchRange = n.has("matchRange") ? new Some<>(n.getInt("matchRange")) : None.getInstance();
        final String handlerType = n.getString("handlerType");
        final String trigger = n.getString("trigger");
        final int dwellingMinutes = n.getInt("dwellingMinutes");
        final int notificationSmallIcon = n.getInt("notificationSmallIcon");
        final int notificationAccentColor = n.getInt("notificationAccentColor");
        final int internalId = n.getInt("internalId");
        final String regionType = n.getString("regionType");
        final String activityClass = n.getString("activityClass");

        return new FilterableNotification(campaignId,
                regionId,
                experimentId,
                experimentMessageNumber,
                matchId,
                message,
                data,
                geofenceLatitude,
                geofenceLongitude,
                matchRange,
                handlerType,
                trigger,
                dwellingMinutes,
                notificationSmallIcon,
                notificationAccentColor,
                false,
                triggerProperties,
                internalId,
                regionType,
                matchPayload,
                customRegionFields,
                activityClass);
    }

    private static Bundle toBundle(FilterableNotification n) {
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

        if (n.getCustomRegionFields() != null) {
            final Bundle customRegionFields = new Bundle();
            for (Map.Entry<String, String> entry : n.getCustomRegionFields().entrySet()) {
                customRegionFields.putString(entry.getKey(), entry.getValue());
            }
            bundle.putBundle("customRegionFields", customRegionFields);
        }

        bundle.putString("campaignId", n.getCampaignId());
        bundle.putString("regionId", n.getRegionId());
        bundle.putString("experimentId", n.getExperimentId());
        bundle.putInt("experimentMessageNumber", n.getExperimentMessageNumber());
        bundle.putString("matchId", n.getMatchId());
        bundle.putString("message", n.getMessage());
        bundle.putString("data", n.getData());
        if (!Double.isNaN(n.getGeofenceLatitude())) {
            bundle.putDouble("geofenceLatitude", n.getGeofenceLatitude());
        }
        if (!Double.isNaN(n.getGeofenceLongitude())) {
            bundle.putDouble("geofenceLongitude", n.getGeofenceLongitude());
        }
        bundle.putInt("matchRange", n.getMatchRange());
        bundle.putString("handlerType", n.getHandlerType());
        bundle.putString("trigger", n.getTrigger());
        bundle.putInt("dwellingMinutes", n.getDwellingMinutes());
        bundle.putInt("notificationSmallIcon", n.getNotificationSmallIcon());
        bundle.putInt("notificationAccentColor", n.getNotificationAccentColor());
        bundle.putInt("internalId", n.getInternalId());
        bundle.putString("regionType", n.getRegionType());
        bundle.putString("activityClass", n.getActivity());

        return bundle;
    }
}
