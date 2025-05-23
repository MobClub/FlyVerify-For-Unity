//
//  FlyVerifySDK_Unity3dBridge.h
//  FlyVerifySDK_Unity3d
//
//  Created by Jacky on 21/2/20.
//  Copyright © 2016年 liys. All rights reserved.
//

#import "FlyVerifySDK_Unity3dBridge.h"

#import <FlyVerifyCSDK/FlyVerifyCSDK.h>
#import <FlyVerify/FlyVerify-umbrella.h>

#if defined(__cplusplus)
extern "C" {
#endif
    
    // Before Operation
    extern void __iosSubmitGrantResult(int actionType, BOOL status, void *observer);
    
    extern void __iosRegisterAppWithAppKeyAndAppSerect(int actionType, void *appKey,
                                                       void *appSecret,
                                                       void *observer);
    
    // Operation Methods
    extern void __iosPrelogin(int actionType, double timeout, void *observer);
    extern void __iosLoginAuth(int actionType, void *uiConfig, void *observer);
    extern void __iosPreMobileAuth(int actionType, double timeout, void *observer);
    extern void __iosMobileAuth(int actionType, void *phoneNum, void *tokenInfor,
                                double timeout, void *observer);
    
    // Login Auth VC
    extern void __iosDismissLoginAuthVCLoading(int actionType);
    extern void __iosDismissLoginAuthVC(int actionType, BOOL animated,
                                        void *observer);
    
    // Tools
    extern void __iosSetCheckBoxValue(int actionType, BOOL isSelected);
    
    extern void __iosFlyVerifySupport(int actionType, void *observer);
    
    extern void __iosGetCurrentOperatorType(int actionType, void *observer);
    
    extern void __iosClearSDKPhoneScripCache(int actionType, void *observer);
    
    extern void __iosEnableSDKDebug(int actionType, BOOL enable);
    
    extern void __iosGetSDKVersion(int actionType, void *observer);
    
    // Private
    static FlyVerifySDK_Unity3dBridge *bridge = nil;
    
    BOOL __iosStrIsEmpty(NSString *str);
    NSString *__iosCreateCommonResponseWith(int actionType, int listenerType,
                                            NSString *tag, NSDictionary *dict,
                                            NSError *err);
#if defined(__cplusplus)
}
#endif

static NSString *SecVerify_Module_Name = @"SecVerifyPlus";
static NSString *SecVerify_CustomUI_Event_Name = @"SecVerifyCustomEvent";

@interface FlyVerifySDK_Unity3dBridge () <FVSDKVerifyDelegate>

// Configure Dict
@property(nonatomic, strong) NSDictionary *uiConfigure;

// 当前授权页面
@property(nonatomic, weak) UIViewController *authPage;
@property(nonatomic, strong) NSMutableArray<NSDictionary *> *customWidgets;

// Callback Name
@property(nonatomic, copy) NSString *callbackObjectName;

- (FVSDKHyUIConfigure *)_convertToUIConfigure:(NSDictionary *)dict
                             withCallBackName:(NSString *)name;

@end

#if defined(__cplusplus)
extern "C" {
#endif
    
    void __iosSubmitGrantResult(int actionType, BOOL status, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr)) {
            NSLog(@"SubmitGrantResult_Observer不能为空！");
            return;
        }
        NSLog(@"ObserverName: %@", observerStr);
        
        [FlyVerifyC agreePrivacy:status onResult:^(BOOL success) {
            NSString *resStr = __iosCreateCommonResponseWith(
                                                             actionType, 0, nil,
                                                             @{@"isSuccess" : @(success)}, nil);
            UnitySendMessage([observerStr UTF8String],
                             "_callback", [resStr UTF8String]);
        }];
    }
    
    void __iosRegisterAppWithAppKeyAndAppSerect(int actionType, void *appKey,
                                                void *appSecret, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (appKey && appSecret) {
            NSString *appKeyStr = [NSString stringWithCString:appKey
                                                     encoding:NSUTF8StringEncoding];
            NSString *appSecretStr = [NSString stringWithCString:appSecret
                                                        encoding:NSUTF8StringEncoding];
            
            NSString *content = @"";
            if (!__iosStrIsEmpty(appKeyStr) && !__iosStrIsEmpty(appSecretStr)) {
                [FlyVerifyC initKey:appKeyStr secret:appSecretStr];
                content = @"Register App Success！";
            } else {
                NSLog(@"请检查appKey 或 appSecret！");
                content = @"Please Check the AppKey or AppSecret!";
            }
            
            NSString *resStr = __iosCreateCommonResponseWith(
                                                             actionType, 0, nil, @{@"result" : content}, nil);
            UnitySendMessage([observerStr UTF8String], "_callback",
                             [resStr UTF8String]);
        } else {
            NSLog(@"appKey 或 appSecret不能为空！");
            NSString *resStr = __iosCreateCommonResponseWith(
                                                             actionType, 0, nil,
                                                             @{@"result" : @"The appKey and  appSecret can't be empty!"}, nil);
            UnitySendMessage([observerStr UTF8String], "_callback",
                             [resStr UTF8String]);
        }
    }
    
    // Operation Methods
    void __iosPrelogin(int actionType, double timeout, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr)) {
            NSLog(@"SecVerifyPrelogin_Observer不能为空！");
            return;
        }
        
        if (timeout > 0.0) {
            [FVSDKHyVerify setPreGetPhonenumberTimeOut:timeout];
        }
        
        [FVSDKHyVerify
         preLogin:^(NSDictionary *_Nullable resultDic, NSError *_Nullable error) {
            NSString *resStr =
            __iosCreateCommonResponseWith(actionType, 0, nil, resultDic, error);
            UnitySendMessage([observerStr UTF8String], "_callback",
                             [resStr UTF8String]);
        }];
    }
    
    void __iosLoginAuth(int actionType, void *uiConfig, void *observer) {
        if (uiConfig == NULL || observer == NULL) {
            NSLog(@"SecVerify PreLogin Param Is Invalid!");
            return;
        }
        
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        NSString *uiConfigJsonStr = [NSString stringWithCString:uiConfig
                                                       encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr) || __iosStrIsEmpty(uiConfigJsonStr)) {
            NSLog(@"SecVerify PreLogin Param Is Invalid!");
            return;
        }
        
        NSLog(@"Login Auth Config File: %@", uiConfigJsonStr);
        NSDictionary *dict = [FlyVerifyCJson objectFromJSONString:uiConfigJsonStr];
        // Config Bredge Object
        bridge = [[FlyVerifySDK_Unity3dBridge alloc] init];
        [FVSDKHyVerify setDelegate:bridge];
        FVSDKHyUIConfigure *uiConfigModel =
        [bridge _convertToUIConfigure:dict withCallBackName:observerStr];
        
        void (^resultCallBack)(int listenerType, NSDictionary *, NSError *) =
        ^(int listenerType, NSDictionary *result, NSError *err) {
            NSString *resStr = __iosCreateCommonResponseWith(
                                                             actionType, listenerType, nil, result, err);
            UnitySendMessage([observerStr UTF8String], "_callback",
                             [resStr UTF8String]);
        };
        
        [FVSDKHyVerify openAuthPageWithModel:uiConfigModel
                        openAuthPageListener:^(NSDictionary *_Nullable resultDic,
                                               NSError *_Nullable error) {
            resultCallBack(1, resultDic, error);
        }
                      cancelAuthPageListener:^(NSDictionary *_Nullable resultDic,
                                               NSError *_Nullable error) {
            resultCallBack(2, resultDic, error);
        }
                         oneKeyLoginListener:^(NSDictionary *_Nullable resultDic,
                                               NSError *_Nullable error) {
            resultCallBack(3, resultDic, error);
        }];
    }
    
    void __iosPreMobileAuth(int actionType, double timeout, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr)) {
            NSLog(@"SecVerifyPreMobileAuth_Observer不能为空！");
            return;
        }
        
        if (timeout > 0.0) {
            [FVSDKHyVerify setLoginAuthTimeOut:timeout];
        }
        
        [FVSDKHyVerify mobileAuth:^(NSDictionary *_Nullable resultDic,
                                    NSError *_Nullable error) {
            NSString *extractedExpr =
            __iosCreateCommonResponseWith(actionType, 0, nil, resultDic, error);
            
            UnitySendMessage([observerStr UTF8String], "_callback",
                             [extractedExpr UTF8String]);
        }];
    }
    
    void __iosMobileAuth(int actionType, void *phoneNum, void *tokenInfor,
                         double timeout, void *observer) {
        if (phoneNum == NULL || tokenInfor == NULL) {
            NSLog(@"MobileAuth parameters is invalid!");
            return;
        }
        
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        NSString *phoneNumStr = [NSString stringWithCString:phoneNum
                                                   encoding:NSUTF8StringEncoding];
        NSString *tokenInfoJsonStr =
        [NSString stringWithCString:tokenInfor encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr) || __iosStrIsEmpty(phoneNumStr) ||
            __iosStrIsEmpty(tokenInfoJsonStr)) {
            NSLog(@"MobileAuth parameters is invalid!");
            return;
        }
        
        NSLog(@"Token Info Json Str: %@", tokenInfoJsonStr);
        NSDictionary *dict = [FlyVerifyCJson objectFromJSONString:tokenInfoJsonStr];
        if (dict == nil) {
            NSError *err =
            [NSError errorWithDomain:@"SecVerifyErrorDomain"
                                code:-999
                            userInfo:@{
                @"err_code" : @"-999",
                @"err_desc" : @"MobileAuth Params Parsing Failed!"
            }];
            NSString *resStr =
            __iosCreateCommonResponseWith(actionType, 0, nil, nil, err);
            UnitySendMessage([observerStr UTF8String], "_callback",
                             [resStr UTF8String]);
            return;
        }
        
        [FVSDKHyVerify mobileAuthWith:phoneNumStr
                                token:dict
                              timeOut:timeout
                           completion:^(NSDictionary *_Nullable result,
                                        NSError *_Nullable error) {
            NSString *resStr = __iosCreateCommonResponseWith(
                                                             actionType, 0, nil, result, error);
            UnitySendMessage([observerStr UTF8String], "_callback",
                             [resStr UTF8String]);
        }];
    }
    
    // Login Auth VC
    void __iosDismissLoginAuthVCLoading(int actionType) {
        [FVSDKHyVerify hideLoading];
    }
    
    void __iosDismissLoginAuthVC(int actionType, BOOL animated, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr)) {
            NSLog(@"SecVerifyDismissLoginAuthVC_Observer不能为空！");
            return;
        }
        
        [FVSDKHyVerify
         finishLoginVcAnimated:animated
         Completion:^{
            UnitySendMessage([observerStr UTF8String], "_callback", "");
        }];
    }
    
    // Tool Methods
    void __iosSetCheckBoxValue(int actionType, BOOL isSelected) {
        [FVSDKHyVerify setCheckBoxValue:isSelected];
    }
    
    void __iosFlyVerifySupport(int actionType, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr)) {
            NSLog(@"SecVerifySupport_Observer不能为空！");
            return;
        }
        
        BOOL isSupport = [FVSDKHyVerify isVerifyEnable];
        NSString *resultStr = __iosCreateCommonResponseWith(
                                                            actionType, 0, nil,
                                                            @{@"isSupport" : @(isSupport)}, nil);
        
        UnitySendMessage([observerStr UTF8String], "_callback",
                         [resultStr UTF8String]);
    }
    
    void __iosGetCurrentOperatorType(int actionType, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr)) {
            NSLog(@"SecVerifyGetCurrentOperatorType_Observer不能为空！");
            return;
        }
        
        NSString *operType = [FVSDKHyVerify getCurrentOperatorType];
        operType = __iosStrIsEmpty(operType) ? @"" : operType;
        
        NSString *resStr = __iosCreateCommonResponseWith(
                                                         actionType, 0, nil, @{@"operator" : operType}, nil);
        
        UnitySendMessage([observerStr UTF8String], "_callback", [resStr UTF8String]);
    }
    
    void __iosClearSDKPhoneScripCache(int actionType, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr)) {
            NSLog(@"SecVerifyClearPhoneScripCache_Observer不能为空！");
            return;
        }
        
        NSString *resStr = __iosCreateCommonResponseWith(
                                                         actionType, 0, nil, @{@"result" : @"Clear SDK Cache Success"}, nil);
        [FVSDKHyVerify clearPhoneScripCache];
        UnitySendMessage([observerStr UTF8String], "_callback", [resStr UTF8String]);
    }
    
    void __iosEnableSDKDebug(int actionType, BOOL enable) {
        [FVSDKHyVerify setDebug:enable];
    }
    
    void __iosGetSDKVersion(int actionType, void *observer) {
        NSString *observerStr = [NSString stringWithCString:observer
                                                   encoding:NSUTF8StringEncoding];
        if (__iosStrIsEmpty(observerStr)) {
            NSLog(@"SecVerifySDKVersion_Observer不能为空！");
            return;
        }
        
        NSString *sdkVersion = [FVSDKHyVerify sdkVersion];
        sdkVersion = __iosStrIsEmpty(sdkVersion) ? @"" : sdkVersion;
        NSString *resStr = __iosCreateCommonResponseWith(
                                                         actionType, 0, nil, @{@"version" : sdkVersion}, nil);
        
        UnitySendMessage([observerStr UTF8String], "_callback", [resStr UTF8String]);
    }
    
    // Private Method
    BOOL __iosStrIsEmpty(NSString *str) {
        if (![str length] || [str isKindOfClass:[NSNull class]] ||
            ![str isKindOfClass:[NSString class]]) {
            return YES;
        }
        NSString *dealedStr = [str
                               stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
        
        return [dealedStr length] ? NO : YES;
    }
    
    NSString *__iosCreateCommonResponseWith(int actionType, int listenerType,
                                            NSString *tag, NSDictionary *dict,
                                            NSError *err) {
        NSMutableDictionary *mDict = [NSMutableDictionary dictionary];
        
        [mDict setObject:@(actionType) forKey:@"actionType"];
        [mDict setObject:@(listenerType) forKey:@"listenerType"];
        [mDict setObject:(tag ? tag : @"") forKey:@"tag"];
        
        int status = err ? 1 : 0;
        [mDict setObject:@(status) forKey:@"status"];
        
        NSMutableDictionary *response = [NSMutableDictionary dictionary];
        if (dict && [dict isKindOfClass:[NSDictionary class]]) {
            [response setObject:dict forKey:@"ret"];
        }
        
        if (err) {
            NSDictionary *errInfo = [NSDictionary
                                     dictionaryWithObjects:@[ @([err code]), [err localizedDescription] ]
                                     forKeys:@[ @"err_code", @"err_desc" ]];
            [response setObject:errInfo forKey:@"err"];
        }
        [mDict setObject:response forKey:@"response"];
        
        return [FlyVerifyCJson jsonStringFromObject:mDict];
    }
    
#if defined(__cplusplus)
}
#endif

@implementation FlyVerifySDK_Unity3dBridge

#pragma mark - SVSDKVerifyDelegate Methods
#pragma mark -

// 授权页生命周期相关事件
- (void)svVerifyAuthPageViewDidLoad:(UIViewController *)authVC
                           userInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    // 持有当前授权页面
    self.authPage = authVC;
    
    if (self.uiConfigure && ![self.uiConfigure isKindOfClass:[NSNull class]] &&
        userInfo) {
        // 当前页面的所有子视图
        [self _configureAuthPageSubViewsWith:userInfo];
        
        // 新增视图
        [self _configureAddedSubviews];
    }
}

- (void)svVerifyAuthPageViewWillAppear:(UIViewController *)authVC
                              userInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    // 实现所有子视图布局
    if (self.uiConfigure && ![self.uiConfigure isKindOfClass:[NSNull class]] &&
        userInfo) {
        [self _configureAuthPageSubViewsLayoutWithUserInfo:userInfo];
    }
}

- (void)svVerifyAuthPageViewWillTransition:(UIViewController *)authVC
                                    toSize:(CGSize)size
                 withTransitionCoordinator:
(id<UIViewControllerTransitionCoordinator>)coordinator
                                  userInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    /// 横竖屏切换
    [coordinator
     animateAlongsideTransition:^(
                                  id<UIViewControllerTransitionCoordinatorContext> _Nonnull context) {
                                      UIInterfaceOrientation orientation =
                                      [UIApplication sharedApplication].statusBarOrientation;
                                      NSDictionary *iOSConfig = [self uiConfigure];
                                      if (!iOSConfig || [iOSConfig isKindOfClass:[NSNull class]]) {
                                          return;
                                      }
                                      
                                      if (orientation == UIInterfaceOrientationPortrait ||
                                          orientation == UIInterfaceOrientationPortraitUpsideDown) {
                                          NSDictionary *layouts = [iOSConfig objectForKey:@"portraitLayouts"];
                                          if (!layouts || [layouts isKindOfClass:[NSDictionary class]]) {
                                              return;
                                          }
                                          // 布局_竖屏
                                          [self _reload_layoutSubViews:layouts withUserInfo:userInfo];
                                          [self _reload_layoutCustomSubViews:@"portraitLayout"];
                                      } else {
                                          // 布局_横屏
                                          NSDictionary *layouts = [iOSConfig objectForKey:@"landscapeLayouts"];
                                          if (!layouts || ![layouts isKindOfClass:[NSDictionary class]]) {
                                              return;
                                          }
                                          
                                          [self _reload_layoutSubViews:layouts withUserInfo:userInfo];
                                          [self _reload_layoutCustomSubViews:@"landscapeLayout"];
                                      }
                                  }
     completion:nil];
}

- (void)svVerifyAuthPageWillPresent:(UIViewController *)authVC
                           userInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    /// 自定义转场动画
}

// 授权页释放
- (void)svVerifyAuthPageDealloc {
    if ([[self customWidgets] count]) {
        [[self customWidgets] removeAllObjects];
    }
}

#pragma mark - Custom View Click Method
- (void)_customBtnClicked:(UIButton *)btn {
    if (self.callbackObjectName == nil ||
        ![self.callbackObjectName isKindOfClass:[NSString class]] ||
        !self.callbackObjectName.length) {
        return;
    }
    
    NSString *tagStr = [NSString stringWithFormat:@"%ld", btn.tag];
    NSString *resStr = __iosCreateCommonResponseWith(14, 0, tagStr, nil, nil);
    UnitySendMessage([self.callbackObjectName UTF8String], "_callback",
                     [resStr UTF8String]);
}

#pragma mark - 内部方法
#pragma mark -

- (FVSDKHyUIConfigure *)_convertToUIConfigure:(NSDictionary *)dict
                             withCallBackName:(NSString *)name {
    self.callbackObjectName = name;
    
    FVSDKHyUIConfigure *configure = [[FVSDKHyUIConfigure alloc] init];
    configure.currentViewController = [self dc_findCurrentShowingViewController];
    
    if (!dict || ![dict isKindOfClass:[NSDictionary class]] || ![dict count]) {
        return configure;
    }
    
    // 隐藏NavBar
    if ([[dict allKeys] containsObject:@"navBarHidden"] &&
        [[dict objectForKey:@"navBarHidden"]
         respondsToSelector:@selector(boolValue)]) {
        [configure
         setNavBarHidden:@([[dict objectForKey:@"navBarHidden"] boolValue])];
    }
    
    // 是否手动关闭授权页面
    if ([[dict allKeys] containsObject:@"manualDismiss"] &&
        [[dict objectForKey:@"manualDismiss"]
         respondsToSelector:@selector(boolValue)]) {
        [configure
         setManualDismiss:@([[dict objectForKey:@"manualDismiss"] boolValue])];
    }
    
    // Status Bar
    if ([[dict allKeys] containsObject:@"prefersStatusBarHidden"] &&
        [[dict objectForKey:@"prefersStatusBarHidden"]
         respondsToSelector:@selector(boolValue)]) {
        [configure
         setPrefersStatusBarHidden:@([[dict
                                       objectForKey:@"prefersStatusBarHidden"]
                                      boolValue])];
    }
    
    if ([[dict allKeys] containsObject:@"preferredStatusBarStyle"] &&
        [[dict objectForKey:@"preferredStatusBarStyle"]
         respondsToSelector:@selector(intValue)]) {
        [configure
         setPreferredStatusBarStyle:@([[dict
                                        objectForKey:@"preferredStatusBarStyle"]
                                       intValue])];
    }
    
    // 横竖屏支持
    if ([[dict allKeys] containsObject:@"shouldAutorotate"] &&
        [[dict objectForKey:@"shouldAutorotate"]
         respondsToSelector:@selector(boolValue)]) {
        [configure setShouldAutorotate:@([[dict objectForKey:@"shouldAutorotate"]
                                          boolValue])];
    }
    
    if ([[dict allKeys] containsObject:@"supportedInterfaceOrientations"] &&
        [[dict objectForKey:@"supportedInterfaceOrientations"]
         respondsToSelector:@selector(intValue)]) {
        [configure setSupportedInterfaceOrientations:
         @([[dict objectForKey:@"supportedInterfaceOrientations"]
            intValue])];
    }
    
    if ([[dict allKeys]
         containsObject:@"preferredInterfaceOrientationForPresentation"] &&
        [[dict objectForKey:@"preferredInterfaceOrientationForPresentation"]
         respondsToSelector:@selector(intValue)]) {
        [configure
         setPreferredInterfaceOrientationForPresentation:
             @([[dict
                 objectForKey:@"preferredInterfaceOrientationForPresentation"]
                intValue])];
    }
    
    // presnet方法的animate参数
    if ([[dict allKeys] containsObject:@"presentingWithAnimate"] &&
        [[dict objectForKey:@"presentingWithAnimate"]
         respondsToSelector:@selector(boolValue)]) {
        [configure
         setPresentingWithAnimate:@([[dict objectForKey:@"presentingWithAnimate"]
                                     boolValue])];
    }
    
    /**modalTransitionStyle系统自带的弹出方式 仅支持以下三种
     UIModalTransitionStyleCoverVertical 底部弹出
     UIModalTransitionStyleCrossDissolve 淡入
     UIModalTransitionStyleFlipHorizontal 翻转显示
     */
    if ([[dict allKeys] containsObject:@"modalTransitionStyle"] &&
        [[dict objectForKey:@"modalTransitionStyle"]
         respondsToSelector:@selector(intValue)]) {
        [configure
         setModalTransitionStyle:@([[dict objectForKey:@"modalTransitionStyle"]
                                    intValue])];
    }
    
    /* UIModalPresentationStyle
     * 设置为UIModalPresentationOverFullScreen，可使模态背景透明，可实现弹窗授权页
     * 默认UIModalPresentationFullScreen
     * eg. @(UIModalPresentationOverFullScreen)
     */
    /*授权页 ModalPresentationStyle*/
    if ([[dict allKeys] containsObject:@"modalPresentationStyle"] &&
        [[dict objectForKey:@"modalPresentationStyle"]
         respondsToSelector:@selector(intValue)]) {
        [configure
         setModalPresentationStyle:@([[dict
                                       objectForKey:@"modalPresentationStyle"]
                                      intValue])];
    }
    
    /* UIUserInterfaceStyle
     * UIUserInterfaceStyleUnspecified - 不指定样式，跟随系统设置进行展示
     * UIUserInterfaceStyleLight       - 明亮
     * UIUserInterfaceStyleDark,       - 暗黑 仅对iOS13+系统有效
     */
    /*授权页 UIUserInterfaceStyle,默认:UIUserInterfaceStyleLight,eg.
     * @(UIUserInterfaceStyleLight)*/
    if ([[dict allKeys] containsObject:@"overrideUserInterfaceStyle"] &&
        [[dict objectForKey:@"overrideUserInterfaceStyle"]
         respondsToSelector:@selector(intValue)]) {
        [configure
         setOverrideUserInterfaceStyle:
             @([[dict objectForKey:@"overrideUserInterfaceStyle"] intValue])];
    }
    
    // Check Box Value
    if ([[dict allKeys] containsObject:@"checkDefaultState"] &&
        [[dict objectForKey:@"checkDefaultState"]
         respondsToSelector:@selector(boolValue)]) {
        [configure setCheckDefaultState:@([[dict objectForKey:@"checkDefaultState"]
                                           boolValue])];
    }
    
    // 协议相关
    if ([[dict allKeys] containsObject:@"privacySettings"] &&
        [[dict objectForKey:@"privacySettings"] isKindOfClass:[NSArray class]] &&
        [[dict objectForKey:@"privacySettings"] count]) {
        NSArray<NSDictionary *> *list = [dict objectForKey:@"privacySettings"];
        NSMutableArray *mArray = [NSMutableArray arrayWithCapacity:list.count];
        for (NSDictionary *privacyDict in list) {
            FVSDKHyPrivacyText *privacyText = [[FVSDKHyPrivacyText alloc] init];
            
            privacyText.text = [privacyDict objectForKey:@"text"] ?: @"";
            if ([[privacyDict allKeys] containsObject:@"textFontName"] &&
                [[privacyDict objectForKey:@"textFontName"]
                 isKindOfClass:[NSString class]] &&
                [self _isFontValueJudeName:[privacyDict
                                            objectForKey:@"textFontName"]] &&
                [[privacyDict allKeys] containsObject:@"textFont"]) {
                privacyText.textFont = [UIFont
                                        fontWithName:[privacyDict objectForKey:@"textFontName"]
                                        size:[[privacyDict objectForKey:@"textFont"] floatValue]];
            } else if ([[privacyDict allKeys] containsObject:@"textFont"]) {
                privacyText.textFont =
                [UIFont systemFontOfSize:[[privacyDict objectForKey:@"textFont"]
                                          floatValue]];
            }
            
            if ([[privacyDict allKeys] containsObject:@"textColor"]) {
                privacyText.textColor = [UIColor
                                         colorWithHexString:[privacyDict objectForKey:@"textColor"] ?: @""];
            }
            
            privacyText.webTitleText =
            [privacyDict objectForKey:@"webTitleText"] ?: @"";
            privacyText.textLinkString =
            [privacyDict objectForKey:@"textLinkString"] ?: @"";
            if ([[privacyDict objectForKey:@"isOperatorPlaceHolder"]
                 respondsToSelector:@selector(boolValue)]) {
                privacyText.isOperatorPlaceHolder =
                [[privacyDict objectForKey:@"isOperatorPlaceHolder"] boolValue];
            }
            
            // 文字富文本样式需和iOS原生代码一致
            if ([[privacyDict allKeys] containsObject:@"textAttribute"] &&
                [[privacyDict objectForKey:@"textAttribute"]
                 isKindOfClass:[NSDictionary class]] &&
                [[privacyDict objectForKey:@"textAttribute"] count]) {
                privacyText.textAttribute = [privacyDict objectForKey:@"textAttribute"];
            }
            
            [mArray addObject:privacyText];
        }
        
        if (mArray && [mArray count]) {
            [configure setPrivacySettings:[mArray copy]];
        }
    }
    // 隐私条款对其方式(例:@(NSTextAlignmentCenter)
    if ([[dict allKeys] containsObject:@"privacyTextAlignment"] &&
        [[dict objectForKey:@"privacyTextAlignment"]
         respondsToSelector:@selector(intValue)]) {
        [configure
         setPrivacyTextAlignment:@([[dict objectForKey:@"privacyTextAlignment"]
                                    intValue])];
    }
    // 隐私条款多行时行距 CGFloat (例:@(4.0))
    if ([[dict allKeys] containsObject:@"privacyLineSpacing"] &&
        [[dict objectForKey:@"privacyLineSpacing"]
         respondsToSelector:@selector(floatValue)]) {
        [configure
         setPrivacyLineSpacing:@([[dict objectForKey:@"privacyLineSpacing"]
                                  floatValue])];
    }
    
    /*协议页使用模态弹出（默认使用Push)*/
    if ([[dict allKeys] containsObject:@"showPrivacyWebVCByPresent"] &&
        [[dict objectForKey:@"showPrivacyWebVCByPresent"]
         respondsToSelector:@selector(boolValue)]) {
        [configure
         setShowPrivacyWebVCByPresent:
             @([[dict objectForKey:@"showPrivacyWebVCByPresent"] boolValue])];
    }
    /*协议页状态栏样式 默认：UIStatusBarStyleDefault*/
    if ([[dict allKeys] containsObject:@"privacyWebVCPreferredStatusBarStyle"] &&
        [[dict objectForKey:@"privacyWebVCPreferredStatusBarStyle"]
         respondsToSelector:@selector(intValue)]) {
        [configure setPrivacyWebVCPreferredStatusBarStyle:
         @([[dict objectForKey:@"privacyWebVCPreferredStatusBarStyle"]
            intValue])];
    }
    /*协议页 ModalPresentationStyle （协议页使用模态弹出时生效）*/
    if ([[dict allKeys] containsObject:@"privacyWebVCModalPresentationStyle"] &&
        [[dict objectForKey:@"privacyWebVCModalPresentationStyle"]
         respondsToSelector:@selector(intValue)]) {
        [configure setPrivacyWebVCModalPresentationStyle:
         @([[dict objectForKey:@"privacyWebVCModalPresentationStyle"]
            intValue])];
    }
    
    return configure;
}

#pragma mark - Configure Layouts
- (void)_configureAuthPageSubViewsLayoutWithUserInfo:
(FVSDKHyProtocolUserInfo *)userInfo {
    if (!userInfo) {
        // 如果UserInfo为空则直接返回
        return;
    }
    
    NSDictionary *iOSConfig = [self uiConfigure];
    if (!iOSConfig || [iOSConfig isKindOfClass:[NSNull class]]) {
        return;
    }
    
    // 默认设置Portrait Layouts
    if ([[iOSConfig allKeys] containsObject:@"portraitLayouts"] &&
        [[iOSConfig objectForKey:@"portraitLayouts"]
         isKindOfClass:[NSDictionary class]]) {
        NSDictionary *portaitLayouts = [iOSConfig objectForKey:@"portraitLayouts"];
        [self _reload_layoutSubViews:portaitLayouts withUserInfo:userInfo];
    }
    
    [self _reload_layoutCustomSubViews:@"portraitLayout"];
}

- (void)_reload_layoutCustomSubViews:(NSString *)layoutOrientation {
    NSLog(@"Custom Widgets Array: %@", self.customWidgets);
    
    if ([self customWidgets] && [[self customWidgets] count]) {
        for (NSDictionary *widgetConfig in [self customWidgets]) {
            UIView *subView = [[[self authPage] view]
                               viewWithTag:[[widgetConfig objectForKey:@"widgetID"] intValue]];
            if (!subView) {
                continue;
            }
            
            NSDictionary *layout = [widgetConfig objectForKey:layoutOrientation];
            if (!layout || ![layout isKindOfClass:[NSDictionary class]] ||
                ![layout count]) {
                continue;
            }
            
            [self _updateConstraintsAt:[[self authPage] view]
                               subView:subView
                                layout:layout];
        }
    }
}

- (void)_reload_layoutSubViews:(NSDictionary *)layouts
                  withUserInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    if (!self.authPage) {
        // 如果授权页面为空，则直接返回
        return;
    }
    
    // 授权页面自带的子视图
    if ([[layouts allKeys] containsObject:@"loginBtnLayout"] &&
        [[layouts objectForKey:@"loginBtnLayout"]
         isKindOfClass:[NSDictionary class]] &&
        [userInfo loginButton] &&
        ![[userInfo loginButton] isHidden]) { // 如果已经隐藏，则不再配置
        NSDictionary *loginBtnLayout = [layouts objectForKey:@"loginBtnLayout"];
        [self _updateConstraintsAt:[[self authPage] view]
                           subView:[userInfo loginButton]
                            layout:loginBtnLayout];
    }
    
    if ([[layouts allKeys] containsObject:@"phoneLabelLayout"] &&
        [[layouts objectForKey:@"phoneLabelLayout"]
         isKindOfClass:[NSDictionary class]] &&
        [userInfo phoneLabel] &&
        ![[userInfo phoneLabel] isHidden]) { // 如果已经隐藏，则不再配置
        NSDictionary *phoneLabelLayout = [layouts objectForKey:@"phoneLabelLayout"];
        [self _updateConstraintsAt:[[self authPage] view]
                           subView:[userInfo phoneLabel]
                            layout:phoneLabelLayout];
    }
    
    if ([[layouts allKeys] containsObject:@"sloganLabelLayout"] &&
        [[layouts objectForKey:@"sloganLabelLayout"]
         isKindOfClass:[NSDictionary class]] &&
        [userInfo sloganLabel] &&
        ![[userInfo sloganLabel] isHidden]) { // 如果已经隐藏，则不再配置
        NSDictionary *solganLabelLayout =
        [layouts objectForKey:@"sloganLabelLayout"];
        [self _updateConstraintsAt:[[self authPage] view]
                           subView:[userInfo sloganLabel]
                            layout:solganLabelLayout];
    }
    
    if ([[layouts allKeys] containsObject:@"logoImageViewLayout"] &&
        [[layouts objectForKey:@"logoImageViewLayout"]
         isKindOfClass:[NSDictionary class]] &&
        [userInfo logoImageView] &&
        ![[userInfo logoImageView] isHidden]) { // 如果已经隐藏，则不再配置
        NSDictionary *logoIVLayout = [layouts objectForKey:@"logoImageViewLayout"];
        [self _updateConstraintsAt:[[self authPage] view]
                           subView:[userInfo logoImageView]
                            layout:logoIVLayout];
    }
    
    if ([[layouts allKeys] containsObject:@"privacyTextViewLayout"] &&
        [[layouts objectForKey:@"privacyTextViewLayout"]
         isKindOfClass:[NSDictionary class]] &&
        [userInfo privacyTextView] &&
        ![[userInfo privacyTextView] isHidden]) { // 如果已经隐藏，则不再配置
        NSDictionary *privacyTVLayout =
        [layouts objectForKey:@"privacyTextViewLayout"];
        [self _updateConstraintsAt:[[self authPage] view]
                           subView:[userInfo privacyTextView]
                            layout:privacyTVLayout];
    }
    
    if ([[layouts allKeys] containsObject:@"checkBoxLayout"] &&
        [[layouts objectForKey:@"checkBoxLayout"]
         isKindOfClass:[NSDictionary class]] &&
        [userInfo checkBox] &&
        ![[userInfo checkBox] isHidden]) { // 如果已经隐藏，则不再配置
        NSDictionary *checkBoxLayout = [layouts objectForKey:@"checkBoxLayout"];
        [self _updatePrivacyCheckBoxConstraintsAt:[[self authPage] view]
                                          subView:[userInfo checkBox]
                                           layout:checkBoxLayout];
    }
}

- (void)_updateConstraintsAt:(UIView *)superView
                     subView:(UIView *)subView
                      layout:(NSDictionary *)layoutInfo {
    NSDictionary *layouts = [NSDictionary dictionaryWithDictionary:layoutInfo];
    
    [self _removeConstraintsWith:subView];
    subView.translatesAutoresizingMaskIntoConstraints = NO;
    
    BOOL (^layoutIsValid)(NSString *) = ^(NSString *key) {
        BOOL isContains = [[layouts allKeys] containsObject:key];
        BOOL isValid =
        [[layouts objectForKey:key] respondsToSelector:@selector(floatValue)];
        BOOL result = isContains && isValid;
        return result;
    };
    
    if (layoutIsValid(@"layoutTop")) {
        CGFloat layoutTop = [[layouts objectForKey:@"layoutTop"] floatValue];
        NSLayoutConstraint *topConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeTop
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeTop
                                    multiplier:1.0
                                      constant:layoutTop];
        [superView addConstraint:topConstraint];
    }
    
    if (layoutIsValid(@"layoutLeading")) {
        CGFloat layoutLeading =
        [[layouts objectForKey:@"layoutLeading"] floatValue];
        NSLayoutConstraint *leadingConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeLeading
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeLeading
                                    multiplier:1.0
                                      constant:layoutLeading];
        [superView addConstraint:leadingConstraint];
    }
    
    if (layoutIsValid(@"layoutBottom")) {
        CGFloat layoutBottom = [[layouts objectForKey:@"layoutBottom"] floatValue];
        NSLayoutConstraint *bottomConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeBottom
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeBottom
                                    multiplier:1.0
                                      constant:layoutBottom];
        [superView addConstraint:bottomConstraint];
    }
    
    if (layoutIsValid(@"layoutTrailing")) {
        CGFloat layoutTrailing =
        [[layouts objectForKey:@"layoutTrailing"] floatValue];
        NSLayoutConstraint *trailingConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeTrailing
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeTrailing
                                    multiplier:1.0
                                      constant:layoutTrailing];
        [superView addConstraint:trailingConstraint];
    }
    
    if (layoutIsValid(@"layoutCenterX")) {
        CGFloat layoutCenterX =
        [[layouts objectForKey:@"layoutCenterX"] floatValue];
        NSLayoutConstraint *centerXConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeCenterX
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeCenterX
                                    multiplier:1.0
                                      constant:layoutCenterX];
        [superView addConstraint:centerXConstraint];
    }
    
    if (layoutIsValid(@"layoutCenterY")) {
        CGFloat layoutCenterY =
        [[layouts objectForKey:@"layoutCenterY"] floatValue];
        NSLayoutConstraint *centerYConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeCenterY
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeCenterY
                                    multiplier:1.0
                                      constant:layoutCenterY];
        [superView addConstraint:centerYConstraint];
    }
    
    if (layoutIsValid(@"layoutWidth")) {
        CGFloat layoutWidth = [[layouts objectForKey:@"layoutWidth"] floatValue];
        NSLayoutConstraint *widthConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeWidth
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:nil
                                     attribute:NSLayoutAttributeWidth
                                    multiplier:1.0
                                      constant:layoutWidth];
        [superView addConstraint:widthConstraint];
    }
    
    if (layoutIsValid(@"layoutHeight")) {
        CGFloat layoutHeight = [[layouts objectForKey:@"layoutHeight"] floatValue];
        NSLayoutConstraint *heightConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeHeight
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:nil
                                     attribute:NSLayoutAttributeHeight
                                    multiplier:1.0
                                      constant:layoutHeight];
        [superView addConstraint:heightConstraint];
    }
    
    [superView layoutIfNeeded];
}

- (void)_updatePrivacyCheckBoxConstraintsAt:(UIView *)superView
                                    subView:(UIView *)subView
                                     layout:(NSDictionary *)layoutInfo {
    NSDictionary *layouts = [NSDictionary dictionaryWithDictionary:layoutInfo];
    
    [self _removeConstraintsWith:subView];
    subView.translatesAutoresizingMaskIntoConstraints = NO;
    
    BOOL (^layoutIsValid)(NSString *) = ^(NSString *key) {
        BOOL isContains = [[layouts allKeys] containsObject:key];
        BOOL isValid =
        [[layouts objectForKey:key] respondsToSelector:@selector(floatValue)];
        BOOL result = isContains && isValid;
        return result;
    };
    
    if (layoutIsValid(@"layoutTop")) {
        CGFloat layoutTop = [[layouts objectForKey:@"layoutTop"] floatValue];
        NSLayoutConstraint *topConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeTop
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeTop
                                    multiplier:1.0
                                      constant:layoutTop];
        [superView addConstraint:topConstraint];
    }
    
    if (layoutIsValid(@"layoutRight")) {
        CGFloat layoutRight = [[layouts objectForKey:@"layoutRight"] floatValue];
        NSLayoutConstraint *rightConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeRight
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeRight
                                    multiplier:1.0
                                      constant:layoutRight];
        [superView addConstraint:rightConstraint];
    }
    
    if (layoutIsValid(@"layoutCenterY")) {
        CGFloat layoutCenterY =
        [[layouts objectForKey:@"layoutCenterY"] floatValue];
        NSLayoutConstraint *centerYConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeCenterY
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeCenterY
                                    multiplier:1.0
                                      constant:layoutCenterY];
        [superView addConstraint:centerYConstraint];
    }
    
    if (layoutIsValid(@"layoutWidth")) {
        CGFloat layoutWidth = [[layouts objectForKey:@"layoutWidth"] floatValue];
        NSLayoutConstraint *widthConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeWidth
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeWidth
                                    multiplier:1.0
                                      constant:layoutWidth];
        [superView addConstraint:widthConstraint];
    }
    
    if (layoutIsValid(@"layoutHeight")) {
        CGFloat layoutHeight = [[layouts objectForKey:@"layoutHeight"] floatValue];
        NSLayoutConstraint *heightConstraint =
        [NSLayoutConstraint constraintWithItem:subView
                                     attribute:NSLayoutAttributeHeight
                                     relatedBy:NSLayoutRelationEqual
                                        toItem:superView
                                     attribute:NSLayoutAttributeHeight
                                    multiplier:1.0
                                      constant:layoutHeight];
        [superView addConstraint:heightConstraint];
    }
    
    [superView layoutIfNeeded];
}

#pragma mark - Configure SubViews
- (void)_configureAddedSubviews {
    if (!self.authPage) {
        return;
    }
    
    NSDictionary *iOSConfig = [self uiConfigure];
    if (![[iOSConfig allKeys] containsObject:@"widgets"] ||
        ![[iOSConfig objectForKey:@"widgets"] isKindOfClass:[NSArray class]] ||
        ![[iOSConfig objectForKey:@"widgets"] count]) {
        return;
    }
    
    NSArray<NSDictionary *> *widgets = [iOSConfig objectForKey:@"widgets"];
    NSLog(@"Widgets Array: %@", self.customWidgets);
    
    for (NSDictionary *widgetConfig in widgets) {
        NSLog(@"Custom Widget: %@", widgetConfig);
        if (![[widgetConfig allKeys] containsObject:@"widgetType"] ||
            [[widgetConfig objectForKey:@"widgetType"]
             isKindOfClass:[NSNull class]] ||
            ![[widgetConfig allKeys] containsObject:@"widgetID"] ||
            ![[widgetConfig objectForKey:@"widgetID"]
              respondsToSelector:@selector(intValue)]) {
            // 如果不包含widgetID 或widgetType，则直接下一轮
            continue;
        }
        
        if ([[widgetConfig allKeys] containsObject:@"navPosition"] &&
            [[widgetConfig objectForKey:@"navPosition"]
             isKindOfClass:[NSString class]] &&
            [[widgetConfig objectForKey:@"navPosition"] length]) {
            // 配置自定义导航栏
            [self _configureNavBarCustomView:widgetConfig];
            return;
        }
        
        UIView *customView = [self _createCustomSubView:widgetConfig];
        [[[self authPage] view] addSubview:customView];
        
        if ([self customWidgets] && [[self customWidgets] count]) {
            [[self customWidgets] removeAllObjects];
        }
        
        [[self customWidgets] addObject:widgetConfig];
    }
}

- (void)_configureNavBarCustomView:(NSDictionary *)widgetConfig {
    UIBarButtonItem *barItem = [[UIBarButtonItem alloc] init];
    
    CGFloat width = 0.0;
    CGFloat height = 0.0;
    NSDictionary *portraitLayout = [widgetConfig objectForKey:@"portraitLayout"];
    if ([[portraitLayout allKeys] containsObject:@"layoutWidth"] &&
        [[portraitLayout objectForKey:@"layoutWidth"]
         respondsToSelector:@selector(floatValue)]) {
        width = [[portraitLayout objectForKey:@"layoutWidth"] floatValue];
    }
    
    if ([[portraitLayout allKeys] containsObject:@"layoutHeight"] &&
        [[portraitLayout objectForKey:@"layoutHeight"]
         respondsToSelector:@selector(floatValue)]) {
        height = [[portraitLayout objectForKey:@"layoutHeight"] floatValue];
    }
    
    UIView *customSubView = [self _createCustomSubView:widgetConfig];
    if (!customSubView) {
        NSLog(@"创建自定义视图失败!");
        return;
    }
    
    customSubView.frame = CGRectMake(0, 0, width, height);
    [barItem setCustomView:customSubView];
    
    int navPosition = [widgetConfig objectForKey:@"navPosition"];
    if (navPosition == 0) {
        [[[self authPage] navigationItem] setLeftBarButtonItem:barItem];
    } else if (navPosition == 1) {
        [[[self authPage] navigationItem] setRightBarButtonItem:barItem];
    }
}

- (UIView *)_createCustomSubView:(NSDictionary *)widgetConfig {
    int widgetType = [widgetConfig objectForKey:@"widgetType"];
    int tag = [[widgetConfig objectForKey:@"widgetID"] intValue];
    
    // Tools method
    NSString * (^toGetStringContent)(NSString *) = ^(NSString *key) {
        NSString *content = nil;
        
        if (!key || ![key length]) {
            return content;
        }
        
        if ([[widgetConfig allKeys] containsObject:key] &&
            [[widgetConfig objectForKey:key] isKindOfClass:[NSString class]] &&
            [[widgetConfig objectForKey:key] length]) {
            content = [widgetConfig objectForKey:key];
        }
        return content;
    };
    
    if (widgetType == 0) { // 自定义视图类型为Label
        UILabel *label = [[UILabel alloc] init];
        
        label.text = toGetStringContent(@"labelText") ?: @"";
        
        NSString *labelTextColor = toGetStringContent(@"labelTextColor");
        if (labelTextColor) {
            label.textColor = [UIColor colorWithHexString:labelTextColor];
        }
        if ([[widgetConfig objectForKey:@"labelFont"]
             respondsToSelector:@selector(floatValue)]) {
            label.font =
            [UIFont systemFontOfSize:[[widgetConfig objectForKey:@"labelFont"]
                                      floatValue]];
        }
        NSString *labelBgColor = toGetStringContent(@"labelBgColor");
        if (labelBgColor) {
            label.backgroundColor = [UIColor colorWithHexString:labelBgColor];
        }
        if ([[widgetConfig objectForKey:@"labelTextAlignment"]
             respondsToSelector:@selector(intValue)]) {
            label.textAlignment =
            [[widgetConfig objectForKey:@"labelTextAlignment"] intValue];
        }
        
        label.tag = tag;
        
        return label;
    } else if (widgetType == 2) { // 自定义视图类型为ImageView
        UIImageView *imageView = [[UIImageView alloc] init];
        
        NSString *imaName = toGetStringContent(@"imaName");
        if (imaName) {
            [imageView setImage:[self fetchImageWithName:imaName]];
        }
        
        if ([[widgetConfig objectForKey:@"ivCornerRadius"]
             respondsToSelector:@selector(floatValue)]) {
            [[imageView layer]
             setCornerRadius:[[widgetConfig objectForKey:@"ivCornerRadius"]
                              floatValue]];
            [[imageView layer] setMasksToBounds:YES];
        }
        
        imageView.tag = tag;
        
        return imageView;
    } else if (widgetType == 1) { // 自定义视图类型为button
        __block UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
        
        NSString *btnTitle = toGetStringContent(@"btnTitle");
        NSString *btnBgColor = toGetStringContent(@"btnBgColor");
        NSString *btnTitleColor = toGetStringContent(@"btnTitleColor");
        NSString *btnBorderColor = toGetStringContent(@"btnBorderColor");
        
        [btn setTitle:(btnTitle ?: @"") forState:UIControlStateNormal];
        if (btnTitleColor) {
            [btn setTitleColor:[UIColor colorWithHexString:btnTitleColor]
                      forState:UIControlStateNormal];
        }
        if (btnBgColor) {
            [btn setBackgroundColor:[UIColor colorWithHexString:btnBgColor]];
        }
        if (btnBorderColor) {
            [btn.layer
             setBorderColor:[UIColor colorWithHexString:btnBorderColor].CGColor];
        }
        
        if ([[widgetConfig objectForKey:@"btnTitleFont"]
             respondsToSelector:@selector(floatValue)]) {
            [[btn titleLabel]
             setFont:[UIFont systemFontOfSize:[[widgetConfig
                                                objectForKey:@"btnTitleFont"]
                                               floatValue]]];
        }
        if ([[widgetConfig objectForKey:@"btnTitleFont"]
             respondsToSelector:@selector(floatValue)]) {
            [[btn titleLabel]
             setFont:[UIFont systemFontOfSize:[[widgetConfig
                                                objectForKey:@"btnTitleFont"]
                                               floatValue]]];
        }
        if ([[widgetConfig objectForKey:@"btnBorderWidth"]
             respondsToSelector:@selector(floatValue)]) {
            [[btn layer] setBorderWidth:[[widgetConfig objectForKey:@"btnBorderWidth"]
                                         floatValue]];
        }
        if ([[widgetConfig objectForKey:@"btnBorderCornerRadius"]
             respondsToSelector:@selector(floatValue)]) {
            [[btn layer]
             setCornerRadius:[[widgetConfig objectForKey:@"btnBorderCornerRadius"]
                              floatValue]];
            [[btn layer] setMasksToBounds:YES];
        }
        if ([[widgetConfig allKeys] containsObject:@"btnImages"] &&
            [[widgetConfig objectForKey:@"btnImages"]
             isKindOfClass:[NSArray class]] &&
            [[widgetConfig objectForKey:@"btnImages"] count]) {
            NSArray<NSString *> *btnImages = [widgetConfig objectForKey:@"btnImages"];
            [btnImages
             enumerateObjectsUsingBlock:^(NSString *_Nonnull obj, NSUInteger idx,
                                          BOOL *_Nonnull stop) {
                if (obj && ![obj isKindOfClass:[NSNull class]] && [obj length]) {
                    switch (idx) {
                        case 0:
                            [btn setImage:[self fetchImageWithName:obj]
                                 forState:UIControlStateNormal];
                            break;
                        case 1:
                            [btn setImage:[self fetchImageWithName:obj]
                                 forState:UIControlStateHighlighted];
                            break;
                        case 2:
                            [btn setImage:[self fetchImageWithName:obj]
                                 forState:UIControlStateDisabled];
                            break;
                            
                        default:
                            break;
                    }
                }
            }];
        }
        
        [btn addTarget:self
                action:@selector(_customBtnClicked:)
      forControlEvents:UIControlEventTouchUpInside];
        
        btn.tag = tag;
        
        return btn;
    }
    
    return nil;
}

- (void)_configureAuthPageSubViewsWith:(FVSDKHyProtocolUserInfo *)userInfo {
    if (!userInfo) {
        // 如果UserInfo为空，则直接返回
        return;
    }
    
    NSDictionary *iOSConfig = [self uiConfigure];
    for (NSString *configureKey in [iOSConfig allKeys]) {
        if ([configureKey hasPrefix:@"loginBtn"]) {
            // Config Login Btn
            [self _configureLoginBtn:configureKey
                          withConfig:iOSConfig
                          atUserInfo:userInfo];
        } else if ([configureKey hasPrefix:@"logo"]) {
            // Config Logo
            [self _configureLogoIV:configureKey
                        withConfig:iOSConfig
                        atUserInfo:userInfo];
        } else if ([configureKey hasPrefix:@"phone"] ||
                   [configureKey hasPrefix:@"number"]) {
            // Config Phone Label
            [self _configurePhoneLabel:configureKey
                            withConfig:iOSConfig
                            atUserInfo:userInfo];
        } else if ([configureKey hasPrefix:@"check"] ||
                   [configureKey hasPrefix:@"unchecked"]) {
            // Config Check Box
            [self _configureCheckBox:configureKey
                          withConfig:iOSConfig
                          atUserInfo:userInfo];
        } else if ([configureKey hasPrefix:@"slogan"]) {
            // Config Slogan
            [self _configureSlogan:configureKey
                        withConfig:iOSConfig
                        atUserInfo:userInfo];
        } else if ([configureKey hasPrefix:@"privacy"]) {
            // Config Privacy
            if ([configureKey isEqualToString:@"privacyHidden"] &&
                [[iOSConfig objectForKey:configureKey]
                 respondsToSelector:@selector(boolValue)]) {
                BOOL isHidden = [[iOSConfig objectForKey:configureKey] boolValue];
                [[userInfo privacyTextView] setHidden:isHidden];
            }
        } else if ([configureKey hasPrefix:@"backBtn"]) {
            // Config Back Button
            if ([configureKey isEqualToString:@"backBtnHidden"] &&
                [[iOSConfig objectForKey:configureKey]
                 respondsToSelector:@selector(boolValue)]) {
                BOOL isHidden = [[iOSConfig objectForKey:configureKey] boolValue];
                [[userInfo backButton] setHidden:isHidden];
            } else if ([configureKey isEqualToString:@"backBtnImageName"] &&
                       [[iOSConfig objectForKey:configureKey]
                        isKindOfClass:[NSString class]] &&
                       [[iOSConfig objectForKey:configureKey] length]) {
                [[userInfo backButton]
                 setImage:[self fetchImageWithName:[iOSConfig
                                                    objectForKey:configureKey]]
                 forState:UIControlStateNormal];
            }
        }
    }
}

- (void)_configureLoginBtn:(NSString *)key
                withConfig:(NSDictionary *)config
                atUserInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    if ([key isEqualToString:@"loginBtnText"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo loginButton] setTitle:[config objectForKey:key]
                                forState:UIControlStateNormal];
    }
    
    if ([key isEqualToString:@"loginBtnTextColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo loginButton]
         setTitleColor:[UIColor colorWithHexString:[config objectForKey:key]]
         forState:UIControlStateNormal];
    }
    
    if ([key isEqualToString:@"loginBtnBgColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo loginButton]
         setBackgroundColor:[UIColor
                             colorWithHexString:[config objectForKey:key]]];
    }
    
    if ([key isEqualToString:@"loginBtnBorderWidth"] &&
        [config objectForKey:key]) {
        [[userInfo loginButton].layer
         setBorderWidth:[[config objectForKey:key] floatValue]];
    }
    
    if ([key isEqualToString:@"loginBtnCornerRadius"] &&
        [config objectForKey:key]) {
        [[userInfo loginButton].layer
         setCornerRadius:[[config objectForKey:key] floatValue]];
        [[[userInfo loginButton] layer] setMasksToBounds:YES];
    }
    
    if ([key isEqualToString:@"loginBtnBorderColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[[userInfo loginButton] layer]
         setBorderColor:[UIColor colorWithHexString:[config objectForKey:key]]
            .CGColor];
    }
    
    if ([key isEqualToString:@"loginBtnBgImgNames"] &&
        [[config objectForKey:key] isKindOfClass:[NSArray class]] &&
        [[config objectForKey:key] count]) {
        NSArray<NSString *> *imageNames = [config objectForKey:key];
        [imageNames
         enumerateObjectsUsingBlock:^(NSString *_Nonnull obj, NSUInteger idx,
                                      BOOL *_Nonnull stop) {
            if (obj && [obj isKindOfClass:[NSString class]] && [obj length]) {
                switch (idx) {
                    case 0:
                        [[userInfo loginButton] setImage:[self fetchImageWithName:obj]
                                                forState:UIControlStateNormal];
                        break;
                    case 1:
                        [[userInfo loginButton] setImage:[self fetchImageWithName:obj]
                                                forState:UIControlStateDisabled];
                        break;
                    case 2:
                        [[userInfo loginButton] setImage:[self fetchImageWithName:obj]
                                                forState:UIControlStateHighlighted];
                        break;
                    default:
                        *stop = YES;
                        break;
                }
            }
        }];
    }
}

- (void)_configureLogoIV:(NSString *)key
              withConfig:(NSDictionary *)config
              atUserInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    if ([key isEqualToString:@"logoHidden"] &&
        [[config objectForKey:@"logoHidden"]
         respondsToSelector:@selector(boolValue)]) {
        BOOL isHidden = [[config objectForKey:@"logoHidden"] boolValue];
        
        [[userInfo logoImageView] setHidden:isHidden];
        if (isHidden) { // 如果LogoImageView Hidden，则不再额外配置
            return;
        }
    }
    
    if ([key isEqualToString:@"logoImageName"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo logoImageView]
         setImage:[self fetchImageWithName:[config objectForKey:key]]];
    }
    
    if ([key isEqualToString:@"logoCornerRadius"] &&
        [[config objectForKey:key] respondsToSelector:@selector(floatValue)]) {
        [[[userInfo logoImageView] layer]
         setCornerRadius:[[config objectForKey:key] floatValue]];
        [[[userInfo logoImageView] layer] setMasksToBounds:YES];
    }
}

- (void)_configurePhoneLabel:(NSString *)key
                  withConfig:(NSDictionary *)config
                  atUserInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    if ([key isEqualToString:@"phoneHidden"] &&
        [[config objectForKey:key] respondsToSelector:@selector(boolValue)]) {
        BOOL isHidden = [[config objectForKey:key] boolValue];
        
        [[userInfo phoneLabel] setHidden:isHidden];
        if (isHidden) { // 如果隐藏PhoneLabel，则不需要其他配置
            return;
        }
    }
    
    if ([key isEqualToString:@"numberColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo phoneLabel]
         setTextColor:[UIColor colorWithHexString:[config objectForKey:key]]];
    }
    
    if ([key isEqualToString:@"numberBgColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo phoneLabel]
         setBackgroundColor:[UIColor
                             colorWithHexString:[config objectForKey:key]]];
    }
    
    if ([key isEqualToString:@"phoneBorderWidth"] &&
        [[config objectForKey:key] respondsToSelector:@selector(floatValue)]) {
        [[[userInfo phoneLabel] layer]
         setBorderWidth:[[config objectForKey:key] floatValue]];
    }
    
    if ([key isEqualToString:@"phoneCorner"] &&
        [[config objectForKey:key] respondsToSelector:@selector(floatValue)]) {
        [[[userInfo phoneLabel] layer]
         setCornerRadius:[[config objectForKey:key] floatValue]];
        [[[userInfo phoneLabel] layer] setMasksToBounds:YES];
    }
    
    if ([key isEqualToString:@"phoneBorderColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[[userInfo phoneLabel] layer]
         setBorderColor:[UIColor colorWithHexString:[config objectForKey:key]]
            .CGColor];
    }
    
    if ([key isEqualToString:@"numberTextAlignment"] &&
        [[config objectForKey:key] respondsToSelector:@selector(intValue)]) {
        [[userInfo phoneLabel]
         setTextAlignment:[[config objectForKey:key] intValue]];
    }
}

- (void)_configureCheckBox:(NSString *)key
                withConfig:(NSDictionary *)config
                atUserInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    if ([key isEqualToString:@"checkHidden"] &&
        [[config objectForKey:key] respondsToSelector:@selector(boolValue)]) {
        BOOL isHidden = [[config objectForKey:key] boolValue];
        
        [[userInfo checkBox] setHidden:isHidden];
        
        if (isHidden) {
            return;
        }
    }
    
    if ([key isEqualToString:@"checkDefaultState"] &&
        [[config objectForKey:key] respondsToSelector:@selector(boolValue)]) {
        BOOL isSelected = [[config objectForKey:key] boolValue];
        
        [[userInfo checkBox] setSelected:isSelected];
    }
    
    if ([key isEqualToString:@"checkedImgName"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo checkBox]
         setImage:[self fetchImageWithName:[config objectForKey:key]]
         forState:UIControlStateSelected];
    }
    
    if ([key isEqualToString:@"uncheckedImgName"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo checkBox]
         setImage:[self fetchImageWithName:[config objectForKey:key]]
         forState:UIControlStateNormal];
    }
}

- (void)_configureSlogan:(NSString *)key
              withConfig:(NSDictionary *)config
              atUserInfo:(FVSDKHyProtocolUserInfo *)userInfo {
    if ([key isEqualToString:@"sloganHidden"] &&
        [[config objectForKey:key] respondsToSelector:@selector(boolValue)]) {
        BOOL isHidden = [[config objectForKey:key] boolValue];
        
        [[userInfo sloganLabel] setHidden:isHidden];
        if (isHidden) {
            return;
        }
    }
    
    if ([key isEqualToString:@"sloganText"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo sloganLabel] setText:[config objectForKey:key]];
    }
    
    if ([key isEqualToString:@"sloganTextColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo sloganLabel]
         setTextColor:[UIColor colorWithHexString:[config objectForKey:key]]];
    }
    
    if ([key isEqualToString:@"sloganBgColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[userInfo sloganLabel]
         setBackgroundColor:[UIColor
                             colorWithHexString:[config objectForKey:key]]];
    }
    
    if ([key isEqualToString:@"sloganTextAlignment"] &&
        [[config objectForKey:key] respondsToSelector:@selector(intValue)]) {
        [[userInfo sloganLabel]
         setTextAlignment:[[config objectForKey:key] intValue]];
    }
    
    if ([key isEqualToString:@"sloganBorderWidth"] &&
        [[config objectForKey:key] respondsToSelector:@selector(floatValue)]) {
        [[[userInfo sloganLabel] layer]
         setBorderWidth:[[config objectForKey:key] floatValue]];
    }
    
    if ([key isEqualToString:@"sloganCorner"] &&
        [[config objectForKey:key] respondsToSelector:@selector(floatValue)]) {
        [[[userInfo sloganLabel] layer]
         setCornerRadius:[[config objectForKey:key] floatValue]];
        [[[userInfo sloganLabel] layer] setMasksToBounds:YES];
    }
    
    if ([key isEqualToString:@"sloganBorderColor"] &&
        [[config objectForKey:key] isKindOfClass:[NSString class]] &&
        [[config objectForKey:key] length]) {
        [[[userInfo sloganLabel] layer]
         setBorderColor:[UIColor colorWithHexString:[config objectForKey:key]]
            .CGColor];
    }
}

#pragma mark - Private Methods
#pragma mark -

- (void)_removeConstraintsWith:(UIView *)view {
    if (![[view constraints] count]) {
        return;
    }
    
    NSArray *constraints = [view constraints];
    for (NSLayoutConstraint *constraint in constraints) {
        [view removeConstraint:constraint];
    }
}

- (BOOL)_isFontValueJudeName:(NSString *)fontName {
    BOOL isConst = NO;
    if (!fontName || ![fontName isKindOfClass:[NSString class]] ||
        ![fontName length]) {
        return isConst;
    }
    
    NSArray *familys = [UIFont familyNames];
    for (NSString *fontFamily in familys) {
        NSArray *fonts = [UIFont fontNamesForFamilyName:fontFamily];
        if ([fonts containsObject:fontName]) {
            isConst = YES;
            break;
        }
    }
    
    return isConst;
}

- (BOOL)isEmptyStr:(NSString *)str {
    if (![str length] || [str isKindOfClass:[NSNull class]] ||
        ![str isKindOfClass:[NSString class]]) {
        return YES;
    }
    NSString *dealedStr = [str
                           stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
    
    return [dealedStr length] ? NO : YES;
}

// 获取当前显示的 UIViewController
- (UIViewController *)dc_findCurrentShowingViewController {
    // 获得当前活动窗口的根视图
    UIViewController *vc =
    [UIApplication sharedApplication].keyWindow.rootViewController;
    UIViewController *currentShowingVC =
    [self findCurrentShowingViewControllerFrom:vc];
    return currentShowingVC;
}

- (UIViewController *)findCurrentShowingViewControllerFrom:
(UIViewController *)vc {
    // 递归方法 Recursive method
    UIViewController *currentShowingVC;
    if ([vc presentedViewController]) {
        // 当前视图是被presented出来的
        UIViewController *nextRootVC = [vc presentedViewController];
        currentShowingVC = [self findCurrentShowingViewControllerFrom:nextRootVC];
        
    } else if ([vc isKindOfClass:[UITabBarController class]]) {
        // 根视图为UITabBarController
        UIViewController *nextRootVC =
        [(UITabBarController *)vc selectedViewController];
        currentShowingVC = [self findCurrentShowingViewControllerFrom:nextRootVC];
        
    } else if ([vc isKindOfClass:[UINavigationController class]]) {
        // 根视图为UINavigationController
        UIViewController *nextRootVC =
        [(UINavigationController *)vc visibleViewController];
        currentShowingVC = [self findCurrentShowingViewControllerFrom:nextRootVC];
        
    } else {
        // 根视图为非导航类
        currentShowingVC = vc;
    }
    
    return currentShowingVC;
}

- (UIImage *)fetchImageWithName:(NSString *)imgName {
    if (!imgName || ![imgName isKindOfClass:[NSString class]] ||
        ![imgName length]) {
        return nil;
    }
    
    UIImage *image = [UIImage imageNamed:imgName];
    return [image isKindOfClass:[NSNull class]] ? nil : image;
}

@end

// UIColor Category
@implementation UIColor (Hex)

// 透明度固定为1，以0x开头的十六进制转换成的颜色
+ (UIColor *)colorWithHex:(long)hexColor;
{ return [UIColor colorWithHex:hexColor alpha:1.]; }

// 0x开头的十六进制转换成的颜色,透明度可调整
+ (UIColor *)colorWithHex:(long)hexColor alpha:(float)opacity {
    float red = ((float)((hexColor & 0xFF0000) >> 16)) / 255.0;
    float green = ((float)((hexColor & 0xFF00) >> 8)) / 255.0;
    float blue = ((float)(hexColor & 0xFF)) / 255.0;
    return [UIColor colorWithRed:red green:green blue:blue alpha:opacity];
}

// 颜色转换三：iOS中十六进制的颜色（支持以#或0x开头）转换为UIColor
+ (UIColor *)colorWithHexString:(NSString *)colorString {
    NSString *cString = [[colorString
                          stringByTrimmingCharactersInSet:[NSCharacterSet
                                                           whitespaceAndNewlineCharacterSet]]
                         uppercaseString];
    
    // String should be 6 or 8 characters
    if (cString.length < 6 || cString.length > 8) {
        return [UIColor clearColor];
    }
    
    // 判断前缀并剪切掉
    if ([cString hasPrefix:@"0X"]) {
        cString = [cString substringFromIndex:2];
    }
    if ([cString hasPrefix:@"#"]) {
        cString = [cString substringFromIndex:1];
    }
    
    if (cString.length != 6 && cString.length != 8) {
        return [UIColor clearColor];
    }
    
    // 从六位数值中找到RGB对应的位数并转换
    NSRange range;
    range.location = 0;
    range.length = 2;
    
    // R、G、B
    NSString *rString = [cString substringWithRange:range];
    
    range.location = 2;
    NSString *gString = [cString substringWithRange:range];
    
    range.location = 4;
    NSString *bString = [cString substringWithRange:range];
    
    // Scan values
    unsigned int r, g, b;
    [[NSScanner scannerWithString:rString] scanHexInt:&r];
    [[NSScanner scannerWithString:gString] scanHexInt:&g];
    [[NSScanner scannerWithString:bString] scanHexInt:&b];
    
    // 如果是8位则加上alpha通道
    if (cString.length == 8) {
        range.location = 6;
        NSString *aString = [cString substringWithRange:range];
        unsigned int a;
        [[NSScanner scannerWithString:aString] scanHexInt:&a];
        return [UIColor colorWithRed:((float)r / 255.0f)
                               green:((float)g / 255.0f)
                                blue:((float)b / 255.0f)
                               alpha:((float)a / 255.0f)];
    }
    
    return [UIColor colorWithRed:((float)r / 255.0f)
                           green:((float)g / 255.0f)
                            blue:((float)b / 255.0f)
                           alpha:1.0f];
}

@end
