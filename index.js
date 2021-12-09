import { NativeEventEmitter, NativeModules } from 'react-native';

const { PlotProjectsReactModule } = NativeModules;

PlotProjectsReactModule.registerNotificationFilter = function(callback) {
    NativeEventEmitter.addListener('onNotificationsToFilter', (notificationsWithBatchId) => callback(notificationsWithBatchId.batchId, notificationsWithBatchId.data));
};

PlotProjectsReactModule.registerGeotriggerHandler = function(callback) {
    NativeEventEmitter.addListener('onGeotriggersToHandle', (geotriggersWithBatchId) => callback(geotriggersWithBatchId.batchId, geotriggersWithBatchId.data));
};

PlotProjectsReactModule.registerNotificationOpenHandler = function(callback) {
    NativeEventEmitter.addListener('onNotificationOpened', (notification) => callback(notification));
};

export default PlotProjectsReactModule;
