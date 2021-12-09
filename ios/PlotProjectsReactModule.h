//  Copyright © 2020 PlotProjects. All rights reserved.

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import "ReactPlotDelegate.h"

@class ReactPlotDelegate;

@interface PlotProjectsReactModule : RCTEventEmitter <RCTBridgeModule> {
    ReactPlotDelegate* defaultDelegate;
}

@end
