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
        notificationRequestsWithIds = [NSMutableDictionary new];
    }
    return self;
}

-(void)plotFilterNotifications:(PlotFilterNotifications*)filterNotifications {
    NSArray<UNNotificationRequest*>* notificationsToFilter = filterNotifications.uiNotifications;
    if (notificationFilterCallback != nil) {
        NSNumber* batchId = [NSNumber numberWithLong:([notificationFilterCallbacks count] + 1)];
        [notificationFilterCallbacks setObject:filterNotifications forKey:batchId];
        for (UNNotificationRequest* n in notificationsToFilter) {
            [notificationRequestsWithIds setObject:n forKey:n.identifier];
        }
        NSArray<NSDictionary*>* notificationUserInfos = [NotificationRequestMarshaller toDictionaryArray:notificationsToFilter];
        notificationFilterCallback(@[batchId, notificationUserInfos]);
    } else {
        [filterNotifications showNotifications:notificationsToFilter];
    }
}

-(void)passFilteredNotifications:(NSNumber*)batchId filteredNotifications:(NSArray<NSDictionary*>*)filteredNotifications {
    PlotFilterNotifications* callback = [notificationFilterCallbacks objectForKey:batchId];
    NSMutableArray* updatedNotifications = [NSMutableArray new];
    
    for (NSDictionary* filteredNotification in filteredNotifications) {
        UNNotificationRequest* updatedNotification =  [self convertToNotificationRequest:filteredNotification withCallback:callback];
        [updatedNotifications addObject:updatedNotification];
    }
    [notificationFilterCallbacks removeObjectForKey:batchId];
    [callback showNotifications:updatedNotifications];
}

-(UNNotificationRequest*)convertToNotificationRequest:(NSDictionary*)filteredNotification withCallback:(PlotFilterNotifications*)callback {
    NSMutableDictionary* newFilteredNotification = [filteredNotification mutableCopy];
    NSString* requestIdentifier = [filteredNotification objectForKey:@"identifier"];
    NSString* newMessage = [filteredNotification objectForKey:@"message"];
    
    UNNotificationRequest* originalNotification = [notificationRequestsWithIds objectForKey:requestIdentifier];
    [notificationRequestsWithIds removeObjectForKey:requestIdentifier];
    
    id originalIdentifier = [originalNotification.content.userInfo objectForKey:@"identifier"];
    [newFilteredNotification setValue:originalIdentifier forKey:@"identifier"];
    id originalIdentifierWithExperiment = [originalNotification.content.userInfo objectForKey:@"identifierWithExperiment"];
    [newFilteredNotification setValue:originalIdentifierWithExperiment forKey:@"identifierWithExperiment"];
    UNMutableNotificationContent* customContent =  [[UNMutableNotificationContent alloc] init];
    customContent.body = newMessage;
    [newFilteredNotification removeObjectForKey:@"message"];
    customContent.userInfo = newFilteredNotification;
    
    return [UNNotificationRequest requestWithIdentifier:requestIdentifier content:customContent trigger:originalNotification.trigger];
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
