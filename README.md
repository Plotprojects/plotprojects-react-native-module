# Quickstart

You can find PlotProjects official documentation [here](http://files.plotprojects.com/documentation/android/3.13.0/how-to-guides/React-Native-integration-guide/)

You can also find an example ReactNative application with PlotProjects reactNative module integrated [here](https://github.com/Plotprojects/plot-react-native-module-example)

## Step-1 Install PlotProjects ReactNative module

```bash
npm install plotprojects-react-native-module --save
```

## Step-2 Add `plotconfig.json` file to both Android and iOS projects

Here you can find instruction for [iOS](http://files.plotprojects.com/documentation/ios/3.4.3/how-to-guides/iOS-integration-guide/#step-4-add-configuration-file) and [Android](http://files.plotprojects.com/documentation/android/3.16.0/how-to-guides/Android-integration-guide/#step-4-define-configuration-file).

## Step-3 Initialize PlotProjects SDK

In order to initialize PlotProjects plugin, you need add the following code to your `App.js`:

```javascript
import PermissionsAndroid from 'react-native';

import Plot from 'plotprojects-react-native-module';

const requestLocationPermission = async () => {
    try {
		if(Platform.OS === "ios") {
			Plot.initialize();
		} else {
	        const granted = await PermissionsAndroid.requestMultiple(
	          [PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION, 
	              PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
	              PermissionsAndroid.PERMISSIONS.ACCESS_BACKGROUND_LOCATION]);
	        if (granted['android.permission.ACCESS_BACKGROUND_LOCATION'] === PermissionsAndroid.RESULTS.GRANTED
	              || granted['android.permission.ACCESS_COARSE_LOCATION'] === PermissionsAndroid.RESULTS.GRANTED
	              || granted['android.permission.ACCESS_BACKGROUND_LOCATION'] === PermissionsAndroid.RESULTS.GRANTED
	        ) {
	          console.log("Location permission granted!");
	          Plot.initialize();
	        } else {
	          console.log("Location permission denied");
	        }
		}
      
    } catch (err) {
      console.warn(err);
    }
  };

requestLocationPermission();
```

The previous code requests location permission before enabling PlotProjects plugin. The actual code for initializing PlotProjects plugin is the `Plot.initialize();`. The rest if for requesting location permissions.

# Advanced integration

This module supports the following methods:

* `Plot.initialize();` Used to initialize PlotProjects plugin.
* `Plot.setAdvertisingId('my_IDFA', limitTracking);` Used to set the advertising identifier. First parameter is a string represents the value of the Advertising Identifier you want to set and the second is a boolean specifies if tracking is limited or not.
* `Plot.enable()` Enables PlotProjects plugin after disabling it. When you initialize the plugin using `Plot.initialize()` it is enabled by default.
* `Plot.disable()` Disables PlotProjects plugin.
* `Plot.isEnabled(callback);` Checks if PlotProjects plugin is enabled. Callback will receive one boolean argument represents the result of this check.
* `Plot.sendAttributionEvent("read_flyer", "discounts_week_12");` Sets an attribution event with key and value. It can be useful to tie some actions users take to events.
* `Plot.setStringSegmentationProperty("gender", "man");` Sets a string segmentation property.
* `Plot.setBooleanSegmentationProperty("is_true", true);` Sets a boolean segmentation property.
* `Plot.setIntegerSegmentationProperty("count", 10);` Sets an integer segmentation property.
* `Plot.setLongSegmentationProperty("count", 9999999999);` Sets a long segmentation property.
* `Plot.setDoubleSegmentationProperty("avg", 5.5);` Sets a double segmentation property.
* `Plot.setDateSegmentationProperty("today", date.getTime());` Sets a date/time segmentation property.
* `Plot.registerNotificationFilter(callback);` Registers a notification filter so that you can filter notifications before they are sent to the user. An example of a call to that method is:

```javascript
Plot.registerNotificationFilter((batchId, notifications) => {
        notifications[0]['message'] = 'New message';
        Plot.filterNotifications(batchId, JSON.stringify(notifications));
    });
```

You need to call `Plot.filterNotifications(batchId, JSON.stringify(notifications));` when you finish filtering with the filtered result.
    The `batchId` is important to identify which batch this result belongs to.

* `Plot.registerGeotriggerHandler(callback)` register a geotrigger handler. An example of a call to this method is:

```javascript
Plot.registerGeotriggerHandler((batchId, geotriggers) => {
		Plot.handleGeotriggers(batchId, JSON.stringify(geotriggers));
	});
```

You need to call `Plot.handleGeotriggers(batchId, JSON.stringify(geotriggers));` after you finish handling the geotriggers with the handled result.
    The `batchId` is important to identify which batch this result belongs to.

* `Plot.registerNotificationOpenHandler(callback);` register a callback when user opens (tabs) a notification. Callback will have one parameter represents the opened notification.
* `Plot.getLoadedNotifications(callback);` retrieve the nearest 200 loaded notifications. Callback will have one parameter contains an array of the loaded notifications.
* `Plot.getLoadedGeotriggers(callback);` retrieve the nearest 200 loaded geotriggers. Callback will have one parameter contains an array of the loaded geotriggers.
