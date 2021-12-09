//
//  ReactPlotDelegate.h
//  RNPlotprojectsReactLib
//
//  Copyright © 2020 PlotProjects. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <UserNotifications/UserNotifications.h>
#import <PlotProjects/Plot.h>

NS_ASSUME_NONNULL_BEGIN

@interface ReactPlotDelegate : NSObject<PlotDelegate> {
    NSMutableDictionary<NSNumber*, PlotFilterNotifications*>* notificationFilterCallbacks;
    NSMutableDictionary<NSString*, UNNotificationRequest*>* notificationRequestsWithIds;
    NSMutableDictionary<NSNumber*, PlotHandleGeotriggers*>* geotriggerHandlerCallbacks;
    RCTEventEmitter* eventEmitter;
    BOOL notificationFilterRegistered;
    BOOL geotriggerHandlerRegistered;
    BOOL notificationOpenHandlerRegistered;
}

-(instancetype)initWithEventEmitter:(RCTEventEmitter*)eventEmitter;

-(RCTResponseSenderBlock)getNotificationFilterCallback;

-(void)setNotificationFilterRegistered;

-(void)unsetNotificationFilterRegistered;

-(BOOL)isNotificationFilterRegistered;

-(void)passFilteredNotifications:(NSNumber*)batchId filteredNotifications:(NSArray<NSDictionary*>*)filteredNotifications;

-(void)setGeotriggerHandlerRegistered;

-(void)unsetGeotriggerHandlerRegistered;

-(BOOL)isGeotriggerHandlerRegistered;

-(void)passHandledGeotriggers:(NSNumber*)batchId handledGeotriggers:(NSArray<NSDictionary*>*)handledGeotriggers;

-(void)setNotificationOpenHandlerRegistered;

-(void)unsetNotificationOpenHandlerRegistered;

-(BOOL)isNotificationOpenHandlerRegistered;

@end

NS_ASSUME_NONNULL_END
