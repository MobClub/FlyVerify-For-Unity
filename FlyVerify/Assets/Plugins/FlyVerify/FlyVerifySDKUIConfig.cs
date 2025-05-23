using System;
using System.Collections;
using System.Reflection;

namespace cn.FlyVerifySDK.Unity {
    public enum FlyVerifySDKUIConfigType {
        iOSType,
        androidType,
        otherType
    }

    public interface FlyVerifySDKUIConfig {
        FlyVerifySDKUIConfigType uiConfigType();
        Hashtable uiConfig();
    }

    public class FlyVerifySDKIOSUIConfig: FlyVerifySDKUIConfig {
        private Hashtable config = new Hashtable();

        //导航栏隐藏(例:@(YES))
        public bool navBarHidden {
            set {
                config["navBarHidden"] = value;
            }
        }
        /* 外部手动管理关闭授权页
         * 默认为NO，点击一键登录按钮后成功或失败，授权页均自动关闭。(例:@(NO))
         * 可通过[SVSDKHyVerify finishLoginVcAnimated:Completion:]手动关闭
         */
        public bool manualDismiss {
            set {
                config["manualDismiss"] = value;
            }
        }
        /*状态栏隐藏 eg.@(NO)*/
        public bool prefersStatusBarHidden {
            set {
                config["prefersStatusBarHidden"] = value;
            }
        }
//        /* 需先设置Info.plist: View controller-based status bar appearance = YES 方可生效
//         * UIStatusBarStyleDefault：状态栏显示 黑
//         * UIStatusBarStyleLightContent：状态栏显示 白
//         * UIStatusBarStyleDarkContent：状态栏显示 黑 API_AVAILABLE(ios(13.0)) = 3
//         * eg. @(UIStatusBarStyleLightContent)
//         */
//        public iOSStatusBarStyle preferredStatusBarStyle {
//            set {
//                config["preferredStatusBarStyle"] = value;
//            }
//        }
        //横竖屏 是否支持自动转屏 (例:@(NO))
        public bool shouldAutorotate {
            set {
                config["shouldAutorotate"] = value;
            }
        }
//        //横竖屏 设备支持方向 (例:@(UIInterfaceOrientationMaskAll))
//        public iOSInterfaceOrientationMask supportedInterfaceOrientations {
//            set {
//                config["supportedInterfaceOrientations"] = value;
//            }
//        }
//        //横竖屏 偏好方向方向（需和支持方向匹配） (例:@(UIInterfaceOrientationPortrait))
//        public iOSInterfaceOrientation preferredInterfaceOrientationForPresentation {
//            set {
//                config["preferredInterfaceOrientationForPresentation"] = value;
//            }
//        }
        //Present方法的animate参数
        public bool presentingWithAnimate {
            set {
                config["presentingWithAnimate"] = value;
            }
        }
//        /* modalTransitionStyle系统自带的弹出方式 仅支持以下三种
//         * UIModalTransitionStyleCoverVertical 底部弹出
//         * UIModalTransitionStyleCrossDissolve 淡入
//         * UIModalTransitionStyleFlipHorizontal 翻转显示
//         */
//        public iOSModalTransitionStyle modalTransitionStyle {
//            set {
//                config["modalTransitionStyle"] = value;
//            }
//        }
//        /* UIModalPresentationStyle
//         * 设置为UIModalPresentationOverFullScreen，可使模态背景透明，可实现弹窗授权页
//         * 默认UIModalPresentationFullScreen
//         * eg. @(UIModalPresentationOverFullScreen)
//         */
//        public iOSModalPresentationStyle modalPresentationStyle {
//            set {
//                config["modalPresentationStyle"] = value;
//            }
//        }
        /*协议页使用模态弹出（默认使用Push)*/
        public bool showPrivacyWebVCByPresent {
            set {
                config["modalPresentationStyle"] = value;
            }
        }
//        /*协议页状态栏样式 默认：UIStatusBarStyleDefault*/
//        public iOSStatusBarStyle privacyWebVCPreferredStatusBarStyle {
//            set {
//                config["privacyWebVCPreferredStatusBarStyle"] = value;
//            }
//        }
//        /*协议页 ModalPresentationStyle （协议页使用模态弹出时生效）*/
//        public iOSModalPresentationStyle privacyWebVCModalPresentationStyle {
//            set {
//                config["privacyWebVCModalPresentationStyle"] = value;
//            }
//        }
//        /* UIUserInterfaceStyle
//         * UIUserInterfaceStyleUnspecified - 不指定样式，跟随系统设置进行展示
//         * UIUserInterfaceStyleLight       - 明亮
//         * UIUserInterfaceStyleDark,       - 暗黑 仅对iOS13+系统有效
//         */
//        public iOSUserInterfaceStyle overrideUserInterfaceStyle {
//            set {
//                config["overrideUserInterfaceStyle"] = value;
//            }
//        }
        public string backBtnImageName {
            set {
                config["backBtnImageName"] = value;
            }
        }
        // LoginBtn
        // Notice: Login Btn Couldn't Be Hidden
        public string loginBtnText {
            set {
                config["loginBtnText"] = value;
            }
        }
        public string loginBtnBgColor {
            set {
                config["loginBtnBgColor"] = value;
            }
        }
        public string loginBtnTextColor {
            set {
                config["loginBtnTextColor"] = value;
            }
        }
        public float loginBtnBorderWidth {
            set {
                config["loginBtnBorderWidth"] = value;
            }
        }
        public float loginBtnCornerRadius {
            set {
                config["loginBtnCornerRadius"] = value;
            }
        }
        public string loginBtnBorderColor {
            set {
                config["loginBtnBorderColor"] = value;
            }
        }
        public string[] loginBtnBgImgNames {
            set {
                config["loginBtnBgImgNames"] = value;
            }
        }
        // Logo
        public bool logoHidden {
            set {
                config["logoHidden"] = value;
            }
        }
        public string logoImageName {
            set {
                config["logoImageName"] = value;
            }
        }
        public float logoCornerRadius {
            set {
                config["logoCornerRadius"] = value;
            }
        }
        // PhoneLabel
        public bool phoneHidden {
            set {
                config["phoneHidden"] = value;
            }
        }
        public string numberColor {
            set {
                config["numberColor"] = value;
            }
        }
        public string numberBgColor {
            set {
                config["numberBgColor"] = value;
            }
        }
//        public iOSTextAlignment numberTextAlignment {
//            set {
//                config["numberTextAlignment"] = value;
//            }
//        }
        public float phoneCorner {
            set {
                config["phoneCorner"] = value;
            }
        }
        public float phoneBorderWidth {
            set {
                config["phoneBorderWidth"] = value;
            }
        }
        public string phoneBorderColor {
            set {
                config["phoneBorderColor"] = value;
            }
        }
        // CheckBox
        public bool checkHidden {
            set {
                config["checkHidden"] = value;
            }
        }
        public bool checkDefaultState {
            set {
                config["checkDefaultState"] = value;
            }
        }

        public string checkedImgName {
            set {
                config["checkedImgName"] = value;
            }
        }

        public string uncheckedImgName {
            set {
                config["uncheckedImgName"] = value;
            }
        }
        // Privacy
        // Notice Privacy Couldn't Be Hidden
        public float privacyLineSpacing {
            set {
                config["privacyLineSpacing"] = value;
            }
        }
//        public iOSTextAlignment privacyTextAlignment {
//            set {
//                config["privacyTextAlignment"] = value;
//            }
//        }
        public Hashtable[] privacySettings {
            set {
                config["privacySettings"] = value;
            }
        }
        // Slogan
        public bool sloganHidden {
            set {
                config["sloganHidden"] = value;
            }
        }
        public string sloganText {
            set {
                config["sloganText"] = value;
            }
        }
        public string sloganBgColor {
            set {
                config["sloganBgColor"] = value;
            }
        }
        public string sloganTextColor {
            set {
                config["sloganTextColor"] = value;
            }
        }
//        public iOSTextAlignment sloganTextAlignment {
//            set {
//                config["sloganTextAlignment"] = value;
//            }
//        }

        public float sloganCorner {
            set {
                config["sloganCorner"] = value;
            }
        }
        public float sloganBorderWidth {
            set {
                config["sloganBorderWidth"] = value;
            }
        }
        public string sloganBorderColor {
            set {
                config["sloganBorderColor"] = value;
            }
        }

        public Hashtable[] widgets {
            set {
                config["widgets"] = value;
            }
        }

        public Hashtable portraitLayouts {
            set {
                config["portraitLayouts"] = value;
            }
        }
        public Hashtable landscapeLayouts {
            set {
                config["landscapeLayouts"] = value;
            }
        }

        // Methods
        public FlyVerifySDKUIConfigType uiConfigType() {
            return FlyVerifySDKUIConfigType.iOSType;
        }

        public Hashtable uiConfig() {
            return config;
        }
    }
    //安卓
    public class FlyVerifySDKAndroidUIConfig: FlyVerifySDKUIConfig {
        private Hashtable config = new Hashtable();

        public FlyVerifySDKUIConfigType uiConfigType() {
            return FlyVerifySDKUIConfigType.androidType;
        }

        public Hashtable uiConfig() {
          
            return config;
        }

       //manu dismiss
        public bool manualDismiss
        {
            set
            {
                config["manualDismiss"] = value;
            }
        }

        //nav is hiden
        public bool navHidden
        {
            set
            {
                config["navHidden"] = value;
            }
        }
        
        public bool navCloseImgHidden
        {
            set
            {
                config["navCloseImgHidden"] = value;
            }
        }
        //logo 号码上方

            //check acount
        public bool switchAccHidden
        {
            set
            {
                config["switchAccHidden"] = value;
            }
        }

        public string switchAccText
        {
            set
            {
                config["switchAccText"] = value;
            }
        }

        //btn
        public string loginBtnTextStringName
        {
            set
            {
                config["loginBtnTextStringName"] = value;
            }
        }
        //slogan
        public bool sloganHidden
        {
            set
            {
                config["sloganHidden"] = value;
            }
        }
        //show style
        public bool dialogTheme
        {
            set
            {
                config["dialogTheme"] = value;
            }
        }
        public bool dialogAlignBottom
        {
            set
            {
                config["dialogdialogAlignBottomTheme"] = value;
            }
        }
        public bool dialogBackgroundClickClose
        {
            set
            {
                config["dialogBackgroundClickClose"] = value;
            }
        }
        public bool checkboxDefaultState
        {
            set
            {
                config["checkboxDefaultState"] = value;
            }
        }

    }

    public class FlyVerifySDKOtherUIConfig: FlyVerifySDKUIConfig {
        private Hashtable config = new Hashtable();

        /* 外部手动管理关闭授权页
         * 默认为NO，点击一键登录按钮后成功或失败，授权页均自动关闭。(例:@(NO))
         * 可通过[SVSDKHyVerify finishLoginVcAnimated:Completion:]手动关闭
         */
        public bool manualDismiss
        {
            set
            {
                config["manualDismiss"] = value;
            }
        }





        public FlyVerifySDKUIConfigType uiConfigType() {
            return FlyVerifySDKUIConfigType.otherType;
        }

        public Hashtable uiConfig() {
            return null;
        }
    }
}