//  Copyright © 2020 PlotProjects. All rights reserved.

#import "PlotProjectsReactModule.h"
#import "ReactPlotDelegate.h"
#import "NotificationRequestMarshaller.h"
#import "GeotriggerMarshaller.h"
#import <PlotProjects/Plot.h>


@implementation PlotProjectsReactModule

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(initialize) {
    if (defaultDelegate == nil) {
        defaultDelegate = [[ReactPlotDelegate alloc] initWithEventEmitter:self];
    }
    [Plot initializeWithDelegate:defaultDelegate];
}

RCT_EXPORT_METHOD(setAdvertisingId:(NSString *)advertisingIdentifier limitAdTracking:(BOOL)limitAdTracking) {
    NSUUID* uuid = [[NSUUID alloc] initWithUUIDString:advertisingIdentifier];
    [Plot setAdvertisingIdentifier:uuid advertisingTrackingEnabled:limitAdTracking];
}

RCT_EXPORT_METHOD(enable) {
    [Plot enable];
}

RCT_EXPORT_METHOD(disable) {
    [Plot disable];
}

RCT_EXPORT_METHOD(isEnabled:(RCTResponseSenderBlock)callback) {
    callback(@[[NSNumber numberWithBool:[Plot isEnabled]]]);
}

RCT_EXPORT_METHOD(sendAttributionEvent:(NSString*)actionName itemId:(NSString*)itemId) {
    [Plot sendAttributionEvent:actionName withItemId:itemId];
}

RCT_EXPORT_METHOD(setStringSegmentationProperty:(NSString*)name property:(NSString*)property) {
    [Plot setStringSegmentationProperty:property forKey:name];
}

RCT_EXPORT_METHOD(setBooleanSegmentationProperty:(NSString*)name property:(BOOL)property) {
    [Plot setBooleanSegmentationProperty:property forKey:name];
}

RCT_EXPORT_METHOD(setIntegerSegmentationProperty:(NSString*)name property:(long long)property) {
    [Plot setIntegerSegmentationProperty:property forKey:name];
}

RCT_EXPORT_METHOD(setDoubleSegmentationProperty:(NSString*)name property:(double)property) {
    [Plot setDoubleSegmentationProperty:property forKey:name];
}

RCT_EXPORT_METHOD(setLongSegmentationProperty:(NSString*)name property:(long long)property) {
    [Plot setIntegerSegmentationProperty:property forKey:name];
}

RCT_EXPORT_METHOD(setDateSegmentationProperty:(NSString*)name property:(NSDate*)property) {
    [Plot setDateSegmentationProperty:property forKey:name];
}

RCT_EXPORT_METHOD(setNotificationFilterRegistered) {
    [defaultDelegate setNotificationFilterRegistered];
}

RCT_EXPORT_METHOD(unsetNotificationFilterRegistered) {
    [defaultDelegate unsetNotificationFilterRegistered];
}

RCT_EXPORT_METHOD(filterNotifications:(nonnull NSNumber*)batchId filteredNotificationsString:(NSString*)filteredNotificationsString) {
    NSData* jsonData = [filteredNotificationsString dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error = nil;
    NSArray<NSDictionary*>* filteredNotifications = [NSJSONSerialization
                             JSONObjectWithData:jsonData
                             options:0
                             error:&error];
    if(! error) {
        [defaultDelegate passFilteredNotifications:batchId filteredNotifications:filteredNotifications];
    } else {
        NSLog(@"Error in parsing filteredNotifications JSON: %@", [error localizedDescription]);
    }
}

RCT_EXPORT_METHOD(setGeotriggerHandlerRegistered) {
    [defaultDelegate setGeotriggerHandlerRegistered];
}

RCT_EXPORT_METHOD(unsetGeotriggerHandlerRegistered) {
    [defaultDelegate unsetGeotriggerHandlerRegistered];
}

RCT_EXPORT_METHOD(handleGeotriggers:(nonnull NSNumber*)batchId handledGeotriggersString:(NSString*)handledGeotriggersString) {
    NSData* jsonData = [handledGeotriggersString dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error = nil;
    NSArray<NSDictionary*>* handledGeotriggers = [NSJSONSerialization
                             JSONObjectWithData:jsonData
                             options:0
                             error:&error];
    if(! error) {
        [defaultDelegate passHandledGeotriggers:batchId handledGeotriggers:handledGeotriggers];
    } else {
        NSLog(@"Error in parsing filteredNotifications JSON: %@", [error localizedDescription]);
    }

}

RCT_EXPORT_METHOD(setNotificationOpenHandlerRegistered) {
    [defaultDelegate setNotificationOpenHandlerRegistered];
}

RCT_EXPORT_METHOD(unsetNotificationOpenHandlerRegistered) {
    [defaultDelegate unsetNotificationOpenHandlerRegistered];
}

RCT_EXPORT_METHOD(getLoadedNotifications:(RCTResponseSenderBlock)callback) {
    NSArray<UNNotificationRequest*>* loadedNotifications = [Plot loadedNotifications];
    NSArray<NSDictionary*>* loadedNotificationsDictionaries = [NotificationRequestMarshaller toDictionaryArray:loadedNotifications];
    callback(@[loadedNotificationsDictionaries]);
}

RCT_EXPORT_METHOD(getLoadedGeotriggers:(RCTResponseSenderBlock)callback) {
    NSArray<PlotGeotrigger*>* loadedGeotriggers = [Plot loadedGeotriggers];
    NSArray<NSDictionary*>* loadedGeotriggersDictionaries = [GeotriggerMarshaller toDictionaryArray:loadedGeotriggers];
    callback(@[loadedGeotriggersDictionaries]);
}

RCT_EXPORT_METHOD(mailDebugLog) {
    [Plot mailDebugLog: [UIApplication sharedApplication].delegate.window.rootViewController];
}

- (NSArray<NSString *> *)supportedEvents {
    return @[@"onNotificationsToFilter", @"onGeotriggersToHandle", @"onNotificationOpened"];
}

@end

