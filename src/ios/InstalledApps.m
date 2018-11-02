/**
 Ning Wei 20181102
 增加iOS版的第一个接口：检查推送消息的启用情况
 **/

#import "InstalledApps.h"
#import <Cordova/CDV.h>

@implementation InstalledApps

- (void)checkNotificationEnabled:(CDVInvokedUrlCommand*)command {
    
    // 参考：https://github.com/dpa99c/cordova-diagnostic-plugin/blob/master/src/ios/Diagnostic_Notifications.m
    // 因为不需要支持iOS7，简化了对应的实现。
    [self.commandDelegate runInBackground:^{
        @try {
            
            // iOS 10+
            if(NSClassFromString(@"UNUserNotificationCenter")) {
#if defined(__IPHONE_10_0) && __IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_10_0
                // iOS 10+
                UNUserNotificationCenter* center = [UNUserNotificationCenter currentNotificationCenter];
                [center getNotificationSettingsWithCompletionHandler:^(UNNotificationSettings * _Nonnull settings) {
                    BOOL userSettingEnabled = settings.authorizationStatus == UNAuthorizationStatusAuthorized;
                    [self _isRemoteNotificationsEnabledResult:userSettingEnabled:command];
                }];
#endif
            } else{
                // iOS 9
                UIUserNotificationSettings *userNotificationSettings = [UIApplication sharedApplication].currentUserNotificationSettings;
                BOOL userSettingEnabled = userNotificationSettings.types != UIUserNotificationTypeNone;
                [self _isRemoteNotificationsEnabledResult:userSettingEnabled:command];
            }
            
        }
        @catch (NSException *exception) {
            
            pluginResult = [CDVPluginResult resultWithStatus: CDVCommandStatus_ERROR messageAsString: exception.reason];
            
            [self.commandDelegate sendPluginResult:pluginResult callbackId: command.callbackId];
        }
    }];
}

- (void) _isRemoteNotificationsEnabledResult: (BOOL) userSettingEnabled : (CDVInvokedUrlCommand*)command
{
    // 参考：https://github.com/dpa99c/cordova-diagnostic-plugin/blob/master/src/ios/Diagnostic_Notifications.m
    // iOS 8+
    [self _isRegisteredForRemoteNotifications:^(BOOL remoteNotificationsEnabled) {
        BOOL isEnabled = remoteNotificationsEnabled && userSettingEnabled;
        
    
        pluginResult = [CDVPluginResult resultWithStatus: CDVCommandStatus_OK messageAsInt:isEnabled?1:0];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId: command.callbackId];
        
    }];
}

- (void) _isRegisteredForRemoteNotifications:(void (^)(BOOL result))completeBlock {
    
    // 参考：https://github.com/dpa99c/cordova-diagnostic-plugin/blob/master/src/ios/Diagnostic_Notifications.m
    dispatch_async(dispatch_get_main_queue(), ^{
        BOOL registered = [UIApplication sharedApplication].isRegisteredForRemoteNotifications;
        if( completeBlock ){
            completeBlock(registered);
        }
    });
};

@end
