/**
 Ning Wei 20181102
 增加iOS版的第一个接口：检查推送消息的启用情况
 **/

#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>

// 支持新的授权模型
#if defined(__IPHONE_10_0) && __IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_10_0
#import <UserNotifications/UserNotifications.h>
#endif

@interface InstalledApps : CDVPlugin {
    CDVPluginResult *pluginResult;
    CDVInvokedUrlCommand *_command;
}

- (void)checkNotificationEnabled:(CDVInvokedUrlCommand*)command;


@end
