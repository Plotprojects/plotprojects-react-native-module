//  Copyright © 2020 PlotProjects. All rights reserved.

#import <React/RCTBridgeModule.h>
#import "ReactPlotDelegate.h"

@class ReactPlotDelegate;

@interface PlotProjectsReactModule : NSObject <RCTBridgeModule> {
    ReactPlotDelegate* defaultDelegate;
}

@end
