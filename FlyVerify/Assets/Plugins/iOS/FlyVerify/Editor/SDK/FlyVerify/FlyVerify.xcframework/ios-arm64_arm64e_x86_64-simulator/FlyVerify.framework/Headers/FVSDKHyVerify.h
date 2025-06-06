//
//  FVSDKHYVerify.h
//  FVSDKHYVerify
//
//  Created by fly on 2020/11/12.
//  Copyright © 2020 fly. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#import "FVSDKDefine.h"
#import "FVSDKHyProtocolUserInfo.h"
#import "FVSDKHyUIConfigure.h"

@protocol FVSDKVerifyDelegate <NSObject>

@optional

//授权页生命周期相关事件:

/**授权页vc对象创建*/
-(void)fvVerifyAuthPageDidInit:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
/**将要present前，可在此设置转场动画代理*/
-(void)fvVerifyAuthPageWillPresent:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
/**present的completion回调*/
-(void)fvVerifyAuthPageDidPresent:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
/**授权页viewDidload,一般在此设置基本控件样式布局和添加自定义控件*/
-(void)fvVerifyAuthPageViewDidLoad:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;

/**授权页显示消失,随着页面跳转，可能多次触发*/
-(void)fvVerifyAuthPageViewWillAppear:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
-(void)fvVerifyAuthPageViewDidAppear:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
-(void)fvVerifyAuthPageViewWillDisAppear:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
-(void)fvVerifyAuthPageViewDidDisappear:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;

/**横竖屏旋转实时切换时，可用于更新UI*/
-(void)fvVerifyAuthPageViewWillLayoutSubviews:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
/**横竖屏旋转实时切换*/
-(void)fvVerifyAuthPageViewWillTransition:(UIViewController *)authVC toSize:(CGSize)size withTransitionCoordinator:(id <UIViewControllerTransitionCoordinator>)coordinator userInfo:(FVSDKHyProtocolUserInfo*)userInfo;

/**授权页释放*/
-(void)fvVerifyAuthPageDealloc;

/**将要跳转协议web页,返回NO则不自动跳转,可在此自行跳转自定义web页*/
-(BOOL)fvVerifyShouldLinkPrivacy:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
/**将要显示内置的未勾选协议提示,返回NO则不显示内置提示,可在此添加自定义提示*/
-(BOOL)fvVerifyShouldAlertUnChecked:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
/**将要显示内置的一键登录等待loading,返回NO则不显示内置loading,可在此添加自定义loading*/
-(BOOL)fvVerifyShouldShowLoadingLoginClick:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;

/**授权页点击相关事件*/
-(void)fvVerifyCheckBoxClick:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo checkBoxValue:(BOOL)isSeleted;
-(void)fvVerifyLoginButtonClick:(UIViewController *)authVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo checkBoxValue:(BOOL)isSeleted;

/**授权页navigationVC生命周期相关事件*/
-(void)fvVerifyAuthPageNavViewWillLayoutSubviews:(UINavigationController *)authNav userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
-(void)fvVerifyAuthPageNavViewWillTransition:(UINavigationController *)authNav toSize:(CGSize)size withTransitionCoordinator:(id <UIViewControllerTransitionCoordinator>)coordinator userInfo:(FVSDKHyProtocolUserInfo*)userInfo;

/**隐私协议页生命周期相关事件*/
-(void)fvVerifyAuthPrivacyPageViewDidLoad:(UIViewController *)webVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
-(void)fvVerifyAuthPrivacyPageViewWillAppear:(UIViewController *)webVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
-(void)fvVerifyAuthPrivacyPageViewDidAppear:(UIViewController *)webVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
-(void)fvVerifyAuthPrivacyPageViewWillDisAppear:(UIViewController *)webVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;
-(void)fvVerifyAuthPrivacyPageViewDidDisappear:(UIViewController *)webVC userInfo:(FVSDKHyProtocolUserInfo*)userInfo;

@end






@interface FVSDKHyVerify : NSObject

/**
 * 预登录
 *
 * @param handler
 * 回调队列global_queue
 * error为nil即为成功
 */
+ (void)preLogin:(nullable FlyVerifyResultHander)handler;


/**
 * 拉起授权页+一键登录
 * 若已经提前预取号成功，sdk将直接拉起授权页。否则将先在sdk内部进行预取号，成功后拉起授权页
 *
 * @param uiConfigure 授权页配FVSDKHyUIConfigure
 * uiConfigure.currentViewController比传
 * 要修改授权页自带控件的样式和添加自定义控件等，请在代理方法中自行设置
 * 授权页将采用系统模态弹出方式。由于系统present机制问题，当currentViewController非最上层vc时，present将无效且completion无回调,请尝试使用currentViewController测试present任一vc看是否可以正常弹出
 *
 * @param openAuthPageListener 拉起授权页回调
 * 回调队列main_queue
 * 拉起成功失败均有回调，error为nil即为成功
 *
 * @param cancelAuthPageListener 拉起授权页后，sdk自带的返回/关闭按钮回调
 * 回调队列main_queue
 * 点击内置的取消、返回按钮，sdk将自动关闭授权页
 * 取消、返回等操作，sdk均视为失败，故此回调resultDic=nil,error!=nil
 *
 * @param oneKeyLoginListener 一键登录点击取token回调
 * 回调队列global_queue
 * 不包含checkBox未勾选事件
 */
+ (void)openAuthPageWithModel:(nonnull FVSDKHyUIConfigure *)uiConfigure
         openAuthPageListener:(FlyVerifyResultHander _Nullable )openAuthPageListener
       cancelAuthPageListener:(FlyVerifyResultHander _Nullable )cancelAuthPageListener
          oneKeyLoginListener:(FlyVerifyResultHander _Nullable )oneKeyLoginListener;


/**
 * 设置代理
 * 接收相关事件回调，可用于自定义界面和埋点等
 */
+ (void)setDelegate:(id<FVSDKVerifyDelegate>_Nullable)delegate;

/**
 * 关闭登录页面
 * 适用于需要手动关闭登录界面的场景 (如：model manualDismiss=YES,自定义视图按钮事件等)
 */
+ (void)finishLoginVcAnimated:(BOOL)flag Completion:(void(^_Nullable)(void))completion;

/**
 * 手动关闭授权页自带的一键登录loading
 */
+ (void)hideLoading;

/**
 * 手动设置当前授权页checkBox勾选状态
 */
+ (void)setCheckBoxValue:(BOOL)isSelect;

/**
 * 判断当前网络环境是否可以发起认证（结果仅供参考）
 * YES 可以认证, NO 不能认证
 */
+ (BOOL)isVerifyEnable;

#pragma maek - 本机认证
/**
 * 本机认证获取Token
 * @param handler 结果回调。当error为空，resultDic为本机认证token
 */
+ (void)mobileAuth:(FlyVerifyResultHander _Nullable )handler;

/**
 * 本机号码认证
 * @param phoneNum 手机号
 * @param tokenInfos 预请求得到的token信息
 * @param completion 结果回调
 */
+ (void)mobileAuthWith:(NSString * _Nonnull)phoneNum
                 token:(NSDictionary * _Nonnull)tokenInfos
               timeOut:(NSTimeInterval)timeOut
            completion:(void(^_Nullable)(NSDictionary * _Nullable result, NSError * _Nullable error))completion;

#pragma mark - 设置超时
/**
 * 设置预取号超时 单位:s
 * 大于0有效
 * 建议4s左右，默认4s
 */
+ (void)setPreGetPhonenumberTimeOut:(NSTimeInterval)preGetPhoneTimeOut;
/**
 * 设置获取token超时 单位:s
 * 大于0有效
 * 建议4s左右，默认4s
 */
+ (void)setLoginAuthTimeOut:(NSTimeInterval)loginAuthTimeOut;

/**
 * 获取当前流量卡运营商（结果仅供参考）
 * CMCC:移动 CUCC:联通 CTCC:电信 UNKNOW:未知
 */
+ (NSString *_Nullable)getCurrentOperatorType;

/**
 * 清空sdk内部预取号缓存
 */
+ (void)clearPhoneScripCache;

/**
 * 开启debug模式
 * YES时会打印较多调试日志
 */
+ (void)setDebug:(BOOL)enable;

/**
 * 当前sdk版本号
 */
+ (nonnull NSString *)sdkVersion;

/// 设置当前运营商
/// - Parameter type: 运营商类型
+ (void)setupCurrentOperator:(FlyVerifyOperatorType)type;


//+ (void)setForbidde:(BOOL)forbidde;

@end
