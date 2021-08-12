//
//  NotificationRequestMarshaller.m
//  PlotProjectsReactModule
//
//  Copyright © 2020 PlotProjects. All rights reserved.
//

#import "NotificationRequestMarshaller.h"

@implementation NotificationRequestMarshaller

+(NSDictionary*)toDictionary:(UNNotificationRequest*)notificationRequest {
    NSMutableDictionary* userInfo = [notificationRequest.content.userInfo mutableCopy];
    [userInfo setObject:notificationRequest.identifier forKey:@"identifier"];
    [userInfo setObject:notificationRequest.content.body forKey:@"message"];
    return userInfo;
}

+(NSArray<NSDictionary*>*)toDictionaryArray:(NSArray<UNNotificationRequest*>*)notificationRequests {
    NSMutableArray<NSDictionary*>* result = [NSMutableArray new];
    for (UNNotificationRequest* n in notificationRequests) {
        NSDictionary* notificationRequestDictionary = [NotificationRequestMarshaller toDictionary:n];
        [result addObject:notificationRequestDictionary];
    }
    return result;
}

@end
