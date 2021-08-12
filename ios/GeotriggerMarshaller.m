//
//  GeotriggerMarshaller.m
//  PlotProjectsReactModule
//
//  Copyright © 2020 PlotProjects. All rights reserved.
//

#import "GeotriggerMarshaller.h"

@implementation GeotriggerMarshaller

+(NSDictionary*)toDictionary:(PlotGeotrigger*)geotrigger {
    return geotrigger.userInfo;
}

+(NSArray<NSDictionary*>*)toDictionaryArray:(NSArray<PlotGeotrigger*>*)geotriggers {
    NSMutableArray<NSDictionary*>* result = [NSMutableArray new];
    for (PlotGeotrigger* g in geotriggers) {
        [result addObject:[GeotriggerMarshaller toDictionary:g]];
    }
    return result;
}

@end
