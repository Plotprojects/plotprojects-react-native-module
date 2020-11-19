package com.plotprojects.retail.android.react.util;

import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.plotprojects.retail.android.Geotrigger;
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

public final class GeotriggerMarshaller {

    private GeotriggerMarshaller() {
    }

    public static WritableArray toJsArray(Collection<Geotrigger> notifications) {
        final List<Bundle> notificationBundles = new ArrayList<>(notifications.size());
        for (Geotrigger n : notifications) {
            notificationBundles.add(toBundle(n));
        }
        return Arguments.fromList(notificationBundles);
    }

    public static List<Geotrigger> fromJsArray(String jsArray) throws JSONException {
        final JSONArray geotriggersJsonArray = new JSONArray(jsArray);
        if (geotriggersJsonArray.length() > 0) {
            final List<Geotrigger> result = new ArrayList<Geotrigger>(geotriggersJsonArray.length());
            for (int i = 0; i < geotriggersJsonArray.length(); i++) {
                result.add(fromJs(geotriggersJsonArray.getJSONObject(i)));
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    public static Geotrigger fromJs(JSONObject g) throws JSONException {
        final Map<String, String> triggerProperties;
        if (g.has("triggerProperties")) {
            triggerProperties = new HashMap<>();
            final JSONObject triggerPropertiesJson = g.getJSONObject("triggerProperties");
            for (Iterator<String> it = triggerPropertiesJson.keys(); it.hasNext(); ) {
                String k = it.next();
                triggerProperties.put(k, triggerPropertiesJson.get(k).toString());
            }
        } else {
            triggerProperties = null;
        }

        final Map<String, String> matchPayload;
        if (g.has("matchPayload")) {
            matchPayload = new HashMap<>();
            final JSONObject matchPayloadJson = g.getJSONObject("matchPayload");
            for (Iterator<String> it = matchPayloadJson.keys(); it.hasNext(); ) {
                String k = it.next();
                matchPayload.put(k, matchPayloadJson.get(k).toString());
            }
        } else {
            matchPayload = null;
        }

        final Map<String, String> customRegionFields;
        if (g.has("customRegionFields")) {
            customRegionFields = new HashMap<>();
            final JSONObject customRegionJson = g.getJSONObject("customRegionFields");
            for (Iterator<String> it = customRegionJson.keys(); it.hasNext(); ) {
                String k = it.next();
                customRegionFields.put(k, customRegionJson.get(k).toString());
            }
        } else {
            customRegionFields = null;
        }

        final String campaignId = g.getString("campaignId");
        final Option<String> regionId = g.has("regionId") ? new Some(g.getString("regionId")) : None.getInstance();
        final String matchId = g.getString("matchId");
        final String name = g.getString("name");
        final String data = g.getString("data");
        final Double geofenceLatitude = g.getDouble("geofenceLatitude");
        final Double geofenceLongitude = g.getDouble("geofenceLongitude");
        final Option<Integer> matchRange = g.has("matchRange") ? new Some<>(g.getInt("matchRange")) : None.getInstance();
        final String trigger = g.getString("trigger");
        final int dwellingMinutes = g.getInt("dwellingMinutes");
        final int internalId = g.getInt("internalId");

        return new Geotrigger(campaignId,
                regionId,
                matchId,
                name,
                data,
                geofenceLatitude,
                geofenceLongitude,
                matchRange,
                trigger,
                dwellingMinutes,
                triggerProperties,
                false,
                internalId,
                matchPayload,
                customRegionFields);
    }

    private static Bundle toBundle(Geotrigger g) {
        final Bundle bundle = new Bundle();

        if (g.getTriggerProperties() != null) {
            final Bundle triggerProperties = new Bundle();
            for (Map.Entry<String, String> entry : g.getTriggerProperties().entrySet()) {
                triggerProperties.putString(entry.getKey(), entry.getValue());
            }
            bundle.putBundle("triggerProperties", triggerProperties);
        }

        if (g.getMatchPayload() != null) {
            final Bundle matchPayload = new Bundle();
            for (Map.Entry<String, String> entry : g.getMatchPayload().entrySet()) {
                matchPayload.putString(entry.getKey(), entry.getValue());
            }
            bundle.putBundle("matchPayload", matchPayload);
        }

        if (g.getCustomRegionFields() != null) {
            final Bundle customRegionFields = new Bundle();
            for (Map.Entry<String, String> entry : g.getCustomRegionFields().entrySet()) {
                customRegionFields.putString(entry.getKey(), entry.getValue());
            }
            bundle.putBundle("customRegionFields", customRegionFields);
        }

        bundle.putString("campaignId", g.getCampaignId());
        bundle.putString("regionId", g.getRegionId());
        bundle.putString("matchId", g.getMatchId());
        bundle.putString("name", g.getName());
        bundle.putString("data", g.getData());
        bundle.putDouble("geofenceLatitude", g.getGeofenceLatitude());
        bundle.putDouble("geofenceLongitude", g.getGeofenceLongitude());
        bundle.putInt("matchRange", g.getMatchRange());
        bundle.putString("trigger", g.getTrigger());
        bundle.putInt("dwellingMinutes", g.getDwellingMinutes());
        bundle.putInt("internalId", g.getInternalId());

        return bundle;
    }
}
