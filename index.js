import { NativeEventEmitter, NativeModules } from 'react-native';

const { PlotProjectsReactModule } = NativeModules;

const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample);

PlotProjectsReactModule.unregisterNotificationFilter = function() {
	if (PlotProjectsReactModule.notificationFilterListener) {
		PlotProjectsReactModule.notificationFilterListener.remove();
		PlotProjectsReactModule.unsetNotificationFilterRegistered();
		console.debug("Notification filter was removed successfully");
	}
}

PlotProjectsReactModule.registerNotificationFilter = function(callback) {
	PlotProjectsReactModule.unregisterNotificationFilter();
    PlotProjectsReactModule.notificationFilterListener = eventEmitter.addListener('onNotificationsToFilter', (notificationsWithBatchId) => callback(notificationsWithBatchId.batchId, notificationsWithBatchId.data));
	PlotProjectsReactModule.setNotificationFilterRegistered();
	console.debug("Notification filter was registered successfully");
};

PlotProjectsReactModule.unregisterGeotriggerHandler = function() {
	if (PlotProjectsReactModule.geotriggerHandler) {
		PlotProjectsReactModule.geotriggerHandler.remove();
		PlotProjectsReactModule.unsetGeotriggerHandlerRegistered();
		console.debug("Geotrigger handler was removed successfully");
	}
}

PlotProjectsReactModule.registerGeotriggerHandler = function(callback) {
	PlotProjectsReactModule.unregisterGeotriggerHandler();
    PlotProjectsReactModule.geotriggerHandler = eventEmitter.addListener('onGeotriggersToHandle', (geotriggersWithBatchId) => callback(geotriggersWithBatchId.batchId, geotriggersWithBatchId.data));
	PlotProjectsReactModule.setGeotriggerHandlerRegistered();
	console.debug("Geotrigger handler was registered successfully");
};

PlotProjectsReactModule.unregisterNotificationOpenHandler = function() {
	if (PlotProjectsReactModule.notificationOpenHandler) {
		PlotProjectsReactModule.notificationOpenHandler.remove();
		PlotProjectsReactModule.unsetNotificationOpenHandlerRegistered();
		console.debug("Notification open handler was removed successfully");
	}
}

PlotProjectsReactModule.registerNotificationOpenHandler = function(callback) {
	PlotProjectsReactModule.unregisterNotificationOpenHandler();
    PlotProjectsReactModule.notificationOpenHandler = eventEmitter.addListener('onNotificationOpened', (notification) => callback(notification));
	PlotProjectsReactModule.setNotificationOpenHandlerRegistered();
	console.debug("Notification open handler was registered successfully");
};

export default PlotProjectsReactModule;
