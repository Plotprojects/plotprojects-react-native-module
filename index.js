import { NativeEventEmitter, NativeModules } from 'react-native';

const { PlotProjectsReactModule } = NativeModules;

const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample);

PlotProjectsReactModule.registerNotificationFilter = function(callback) {
    eventEmitter.addListener('onNotificationsToFilter', (notificationsWithBatchId) => callback(notificationsWithBatchId.batchId, notificationsWithBatchId.data));
};

PlotProjectsReactModule.registerGeotriggerHandler = function(callback) {
    eventEmitter.addListener('onGeotriggersToHandle', (geotriggersWithBatchId) => callback(geotriggersWithBatchId.batchId, geotriggersWithBatchId.data));
};

PlotProjectsReactModule.registerNotificationOpenHandler = function(callback) {
    eventEmitter.addListener('onNotificationOpened', (notification) => callback(notification));
};

export default PlotProjectsReactModule;
