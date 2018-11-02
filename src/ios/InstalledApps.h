#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>
#import "Diagnostic.h"

#if defined(__IPHONE_10_0) && __IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_10_0
#import <UserNotifications/UserNotifications.h>
#endif
@interface InstalledApps : CDVPlugin {
    CDVPluginResult *pluginResult;
    CDVInvokedUrlCommand *_command;
}

- (void)checkNotificationEnabled:(CDVInvokedUrlCommand*)command;


@end
