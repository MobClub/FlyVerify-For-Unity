using System;
using System.Collections;

namespace cn.FlyVerifySDK.Unity {
    public enum iOSCustomWidgetNavPosition {
        navLeft,
        navRight,
        unknow,
    }

    public enum iOSCustomWidgetType {
        label,
        button,
        imageView,
    }

    public enum iOSUserInterfaceStyle {
        unspecified = 0, 
        light = 1,
        dark = 2,
    }

    public enum iOSModalPresentationStyle {
        fullScreen = 0,
        pageSheet = 1,
        forSheet = 2,
        currentContext = 3,
        custom = 4,
        overFullScreen = 5,
        overCurrentContext = 6,
        popOver = 7,
        blurOverFullScreen = 8,
        none = -1,
        automatic = -2,
    }

    public enum iOSModalTransitionStyle {
        coverVertical = 0, // 底部弹出
        flipHorizontal = 1, // 淡入
        crossDissolve = 2, // 翻转显示
        partialCurl = 3,
    }

    public enum iOSInterfaceOrientation {
        portrait = 1, //竖屏
        portraitUpsideDown = 2, //上下倒置
        landscapeLeft = 4, //横屏：左
        landscapeRight = 3, //横屏：右
        unknown = 0,
    }

    public enum iOSInterfaceOrientationMask {
        portrait = 2,
        landscapeLeft = 16,
        landscapeRight = 8,
        portraitUpsideDown = 4,
        landscape = 24,
        all = 30,
        allButUpsideDown = 26,
    }

    public enum iOSTextAlignment {
        center = 1, 
        left = 0,
        right = 2,
        justified = 3,
        natural = 4,
    }

    public enum iOSStatusBarStyle {
        styleDefault,
        styleLightContent,
        styleDarkContent,
    }

    public class FlyVerifySDKIOSPrivacyText {
        public Hashtable privacyTextConfig = new Hashtable();

        public string text {
            set {
                privacyTextConfig["text"] = value;
            }
        }
        public double textFont {
            set {
                privacyTextConfig["textFont"] = value;
            }
        }
        public string textFontName {
            set {
                privacyTextConfig["textFontName"] = value;
            }
        }
        public string textColor {
            set {
                privacyTextConfig["textColor"] = value;
            }
        }
        public string webTitleText {
            set {
                privacyTextConfig["webTitleText"] = value;
            }
        }
        public string textLinkString {
            set {
                privacyTextConfig["textLinkString"] = value;
            }
        }
        public bool isOperatorPlaceHolder {
            set {
                privacyTextConfig["isOperatorPlaceHolder"] = value;
            }
        }
        public Hashtable textAttribute {
            set {
                privacyTextConfig["textAttribute"] = value;
            }
        }
    }

    public class FlyVerifySDKIOSCustomView {
        public Hashtable customViewConfig = new Hashtable();

        // Common Config
        public int widgetID {
            set {
                customViewConfig["widgetID"] = value;
            }
        }
        public iOSCustomWidgetType widgetType {
            set {
                customViewConfig["widgetType"] = value;
            }
        }
        public iOSCustomWidgetNavPosition navPosition {
            set {
                customViewConfig["navPosition"] = value;
            }
        }
        // ImageView
        public string imaName {
            set {
                customViewConfig["imaName"] = value;
            }
        }
        public double ivCornerRadius {
            set {
                customViewConfig["ivCornerRadius"] = value;
            }
        }
        // Button
        public string btnTitle {
            set {
                customViewConfig["btnTitle"] = value;
            }
        }
        public string btnBgColor {
            set {
                customViewConfig["btnBgColor"] = value;
            }
        }
        public string btnTitleColor {
            set {
                customViewConfig["btnTitleColor"] = value;
            }
        }
        public double btnTitleFont {
            set {
                customViewConfig["btnTitleFont"] = value;
            }
        }
        public string[] btnImages {
            set {
                customViewConfig["btnImages"] = value;
            }
        }
        public double btnBorderWidth {
            set {
                customViewConfig["btnBorderWidth"] = value;
            }
        }
        public string btnBorderColor {
            set {
                customViewConfig["btnBorderColor"] = value;
            }
        }
        public double btnBorderCornerRadius {
            set {
                customViewConfig["btnBorderCornerRadius"] = value;
            }
        }
        // Table
        public string labelText {
            set {
                customViewConfig["labelText"] = value;
            }
        }
        public string labelTextColor {
            set {
                customViewConfig["labelTextColor"] = value;
            }
        }
        public double labelFont {
            set {
                customViewConfig["labelFont"] = value;
            }
        }
        public string labelBgColor {
            set {
                customViewConfig["labelBgColor"] = value;
            }
        }
        public iOSTextAlignment labelTextAlignment {
            set {
                customViewConfig["labelTextAlignment"] = value;
            }
        }
        public FlyVerifySDKIOSAllLayouts portraitLayout {
            set {
                customViewConfig["portraitLayout"] = value;
            }
        }
        public FlyVerifySDKIOSAllLayouts landscapeLayout {
            set {
                customViewConfig["landscapeLayout"] = value;
            }
        }
    }

    public class FlyVerifySDKIOSAllLayouts {
        public Hashtable layoutsConfig = new Hashtable();
        // Layout
        public FlyVerifySDKIOSUILayout loginBtnLayout {
            set {
                layoutsConfig["loginBtnLayout"] = value;
            }
        }
        public FlyVerifySDKIOSUILayout phoneLabelLayout {
            set {
                layoutsConfig["phoneLabelLayout"] = value;
            }
        }
        public FlyVerifySDKIOSUILayout sloganLabelLayout {
            set {
                layoutsConfig["sloganLabelLayout"] = value;
            }
        }
        public FlyVerifySDKIOSUILayout logoImageViewLayout {
            set {
                layoutsConfig["logoImageViewLayout"] = value;
            }
        }
        public FlyVerifySDKIOSUILayout privacyTextViewLayout {
            set {
                layoutsConfig["privacyTextViewLayout"] = value;
            }
        }
        public FlyVerifySDKIOSCheckBoxLayout checkBoxLayout {
            set {
                layoutsConfig["checkBoxLayout"] = value;
            }
        }
    }

    public class FlyVerifySDKIOSUILayout {
        public Hashtable layoutConfig = new Hashtable();
        public double layoutTop {
            set {
                layoutConfig["layoutTop"] = value;
            }
        }
        public double layoutLeading {
            set {
                layoutConfig["layoutLeading"] = value;
            }
        }
        public double layoutBottom {
            set {
                layoutConfig["layoutBottom"] = value;
            }
        }
        public double layoutTrailing {
            set {
                layoutConfig["layoutTrailing"] = value;
            }
        }

        public double layoutCenterX {
            set {
                layoutConfig["layoutCenterX"] = value;
            }
        }
        public double layoutCenterY {
            set {
                layoutConfig["layoutCenterY"] = value;
            }
        }

        public double layoutWidth {
            set {
                layoutConfig["layoutWidth"] = value;
            }
        }
        public double layoutHeight {
            set {
                layoutConfig["layoutHeight"] = value;
            }
        }
    }

    public class FlyVerifySDKIOSCheckBoxLayout {
        public Hashtable layoutConfig = new Hashtable();

        public double layoutTop {
            set {
                layoutConfig["layoutTop"] = value;
            }
        }
        public double layoutRight {
            set {
                layoutConfig["layoutRight"] = value;
            }
        }
        public double layoutCenterY {
            set {
                layoutConfig["layoutCenterY"] = value;
            }
        }
        public double layoutWidth {
            set {
                layoutConfig["layoutWidth"] = value;
            }
        }
        public double layoutHeight {
            set {
                layoutConfig["layoutHeight"] = value;
            }
        }
    }
}