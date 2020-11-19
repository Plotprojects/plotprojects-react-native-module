//
//  ReactPlotDelegate.h
//  RNPlotprojectsReactLib
//
//  Copyright © 2020 PlotProjects. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <PlotProjects/Plot.h>

NS_ASSUME_NONNULL_BEGIN

@interface ReactPlotDelegate : NSObject<PlotDelegate> {
    RCTResponseSenderBlock notificationFilterCallback;
    RCTResponseSenderBlock geotriggerHandlerCallback;
    RCTResponseSenderBlock notificationHandlerCallback;
    NSMutableDictionary<NSNumber*, PlotFilterNotifications*>* notificationFilterCallbacks;
    NSMutableDictionary<NSNumber*, PlotHandleGeotriggers*>* geotriggerHandlerCallbacks;
}

-(instancetype)init;

-(RCTResponseSenderBlock)getNotificationFilterCallback;

-(void)setNotificationFilterCallback:(RCTResponseSenderBlock)aNotificationFilterCallback;

-(void)passFilteredNotifications:(NSNumber*)batchId filteredNotifications:(NSArray<NSDictionary*>*)filteredNotifications;

-(void)setGeotriggerHandlerCallback:(RCTResponseSenderBlock)aGeotriggerHandlerCallback;

-(void)passHandledGeotriggers:(NSNumber*)batchId handledGeotriggers:(NSArray<NSDictionary*>*)handledGeotriggers;

-(RCTResponseSenderBlock)getGeotriggerHandlerCallback;

-(void)setNotificationHandlerCallback:(RCTResponseSenderBlock)aNotificationHandlerCallback;

-(RCTResponseSenderBlock)getNotificationHandlerCallback;

@end

NS_ASSUME_NONNULL_END
