//
//  NotificationRequestMarshaller.h
//  PlotProjectsReactModule
//
//  Copyright © 2020 PlotProjects. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UserNotifications/UserNotifications.h>

NS_ASSUME_NONNULL_BEGIN

@interface NotificationRequestMarshaller : NSObject

+(NSDictionary*)toDictionary:(UNNotificationRequest*)notificationRequest;

+(NSArray<NSDictionary*>*)toDictionaryArray:(NSArray<UNNotificationRequest*>*)notificationRequests;

@end

NS_ASSUME_NONNULL_END
