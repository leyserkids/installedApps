#import "InstalledApps.h"
#import <Cordova/CDV.h>

@implementation InstalledApps

// Internal reference to Diagnostic singleton instance
static Diagnostic* diagnostic;

// Internal constants
static NSString*const LOG_TAG = @"Diagnostic_Notifications[native]";

static NSString*const REMOTE_NOTIFICATIONS_ALERT = @"alert";
static NSString*const REMOTE_NOTIFICATIONS_SOUND = @"sound";
static NSString*const REMOTE_NOTIFICATIONS_BADGE = @"badge";

- (void)pluginInitialize {
    
    [super pluginInitialize];
    
    diagnostic = [Diagnostic getInstance];
}


- (void)checkNotificationEnabled:(CDVInvokedUrlCommand*)command {
    
    // 参考：https://github.com/dpa99c/cordova-diagnostic-plugin/blob/master/src/ios/Diagnostic_Notifications.m
    [self.commandDelegate runInBackground:^{
        @try {
            
            // iOS 10+
            if(NSClassFromString(@"UNUserNotificationCenter")) {
#if defined(__IPHONE_10_0) && __IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_10_0
                // iOS 10+
                UNUserNotificationCenter* center = [UNUserNotificationCenter currentNotificationCenter];
                [center getNotificationSettingsWithCompletionHandler:^(UNNotificationSettings * _Nonnull settings) {
                    BOOL userSettingEnabled = settings.authorizationStatus == UNAuthorizationStatusAuthorized;
                    [self isRemoteNotificationsEnabledResult:userSettingEnabled:command];
                }];
#endif
            } else{
                // iOS 9
                UIUserNotificationSettings *userNotificationSettings = [UIApplication sharedApplication].currentUserNotificationSettings;
                BOOL userSettingEnabled = userNotificationSettings.types != UIUserNotificationTypeNone;
                [self isRemoteNotificationsEnabledResult:userSettingEnabled:command];
            }
            
        }
        @catch (NSException *exception) {
            [diagnostic handlePluginException:exception:command];
        }
    }];
}

- (void) isRemoteNotificationsEnabledResult: (BOOL) userSettingEnabled : (CDVInvokedUrlCommand*)command
{
    // iOS 8+
    [self _isRegisteredForRemoteNotifications:^(BOOL remoteNotificationsEnabled) {
        BOOL isEnabled = remoteNotificationsEnabled && userSettingEnabled;
        [diagnostic sendPluginResultBool:isEnabled:command];
    }];
}

- (void) _isRegisteredForRemoteNotifications:(void (^)(BOOL result))completeBlock {
    dispatch_async(dispatch_get_main_queue(), ^{
        BOOL registered = [UIApplication sharedApplication].isRegisteredForRemoteNotifications;
        if( completeBlock ){
            completeBlock(registered);
        }
    });
};

@end
