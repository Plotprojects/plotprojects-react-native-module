//
//  GeotriggerMarshaller.h
//  PlotProjectsReactModule
//
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <PlotProjects/Plot.h>

NS_ASSUME_NONNULL_BEGIN

@interface GeotriggerMarshaller : NSObject

+(NSDictionary*)toDictionary:(PlotGeotrigger*)geotrigger;

+(NSArray<NSDictionary*>*)toDictionaryArray:(NSArray<PlotGeotrigger*>*)geotriggers;

@end

NS_ASSUME_NONNULL_END
