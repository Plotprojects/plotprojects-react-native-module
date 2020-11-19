//
//  ReactPlotDelegate.m
//  RNPlotprojectsReactLib
//
//  Copyright © 2020 PlotProjects. All rights reserved.
//

#import "ReactPlotDelegate.h"
#import <React/RCTBridgeModule.h>
#import "NotificationRequestMarshaller.h"
#import "GeotriggerMarshaller.h"

@implementation ReactPlotDelegate

-(id)init {
    if ((self = [super init]) != nil) {
        notificationFilterCallbacks = [NSMutableDictionary new];
        geotriggerHandlerCallbacks = [NSMutableDictionary new];
    }
    return self;
}


-(void)plotFilterNotifications:(PlotFilterNotifications*)filterNotifications {
    NSArray<UNNotificationRequest*>* notifications = filterNotifications.uiNotifications;
    if (notificationFilterCallback != nil) {
        NSNumber* batchId = [NSNumber numberWithLong:([notificationFilterCallbacks count] + 1)];
        [notificationFilterCallbacks setObject:filterNotifications forKey:batchId];
        NSArray<NSDictionary*>* notificationUserInfos = [NotificationRequestMarshaller toDictionaryArray:notifications];
        notificationFilterCallback(@[batchId, notificationUserInfos]);
    } else {
        [filterNotifications showNotifications:notifications];
    }
}

-(void)passFilteredNotifications:(NSNumber*)batchId filteredNotifications:(NSArray<NSDictionary*>*)filteredNotifications {
    PlotFilterNotifications* callback = [notificationFilterCallbacks objectForKey:batchId];
    NSMutableArray* updatedNotifications = [NSMutableArray new];
    for (NSDictionary* n in filteredNotifications) {
        NSString* identifier = [n objectForKey:@"identifier"];
        UNNotificationRequest* originalNotification = [self findOriginalNotification:identifier inBatch:callback];
        UNMutableNotificationContent* customContent =  [[UNMutableNotificationContent alloc] init];
        customContent.body = [n objectForKey:@"message"];
        customContent.userInfo = n;
        UNNotificationRequest* updatedNotification =  [UNNotificationRequest requestWithIdentifier:identifier content:customContent trigger:originalNotification.trigger];
        [updatedNotifications addObject:updatedNotification];
    }
    [callback showNotifications:updatedNotifications];
}

-(void)plotHandleGeotriggers:(PlotHandleGeotriggers*)geotriggerHandler {
    if (geotriggerHandlerCallback != nil) {
        NSNumber* batchId = [NSNumber numberWithLong:([geotriggerHandlerCallbacks count] + 1)];
        [geotriggerHandlerCallbacks setObject:geotriggerHandler forKey:batchId];
        NSArray<NSDictionary*>* geotriggerUserInfos = [GeotriggerMarshaller toDictionaryArray:geotriggerHandler.geotriggers];
        geotriggerHandlerCallback(@[batchId, geotriggerUserInfos]);
    } else {
        [geotriggerHandler markGeotriggersHandled:geotriggerHandler.geotriggers];
    }
}

-(void)passHandledGeotriggers:(NSNumber*)batchId handledGeotriggers:(NSArray<NSDictionary*>*)handledGeotriggers {
    PlotHandleGeotriggers* callback = [geotriggerHandlerCallbacks objectForKey:batchId];
    NSMutableArray<PlotGeotrigger*>* handledGeotriggersResult = [NSMutableArray new];
    for (NSDictionary* g in handledGeotriggers) {
        PlotGeotrigger* geotrigger = [PlotGeotrigger initializeWithUserInfo:g];
        [handledGeotriggersResult addObject:geotrigger];
    }
    [callback markGeotriggersHandled:handledGeotriggersResult];
}

-(UNNotificationRequest*)findOriginalNotification:(NSString*)identifier inBatch:(PlotFilterNotifications*)batch {
    NSArray<UNNotificationRequest*>* notifications = batch.uiNotifications;
    for (UNNotificationRequest* n in notifications) {
        if (n.identifier == identifier) {
            return n;
        }
    }
    return nil;
}

-(void)plotHandleNotification:(NSString*)data response:(UNNotificationResponse*)response {
    if (notificationHandlerCallback != nil) {
        NSMutableDictionary* responseDictionary = [response.notification.request.content.userInfo mutableCopy];
        [responseDictionary setValue:data forKey:@"data"];
        [responseDictionary setValue:response.actionIdentifier forKey:@"actionIdentifier"];
        notificationHandlerCallback(@[responseDictionary]);
    }
}

-(void)setNotificationFilterCallback:(RCTResponseSenderBlock)aNotificationFilterCallback {
    notificationFilterCallback = aNotificationFilterCallback;
}

-(RCTResponseSenderBlock)getNotificationFilterCallback {
    return notificationFilterCallback;
}

-(void)setNotificationHandlerCallback:(RCTResponseSenderBlock)aNotificationHandlerCallback {
    notificationHandlerCallback = aNotificationHandlerCallback;
}

-(RCTResponseSenderBlock)getNotificationHandlerCallback {
    return notificationHandlerCallback;
}

-(void)setGeotriggerHandlerCallback:(RCTResponseSenderBlock)aGeotriggerHandlerCallback {
    geotriggerHandlerCallback = aGeotriggerHandlerCallback;
}

-(RCTResponseSenderBlock)getGeotriggerHandlerCallback {
    return notificationFilterCallback;
}


@end
