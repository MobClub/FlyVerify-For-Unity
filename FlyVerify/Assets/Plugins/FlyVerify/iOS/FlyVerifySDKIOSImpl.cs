using System;
using UnityEngine; 
using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;

namespace cn.FlyVerifySDK.Unity {
    #if UNITY_IPHONE || UNITY_IOS
    public class FlyVerifyIOSImpl: FlyVerifySDKInterface
    {
        [DllImport("__Internal")]
        static extern void __iosSubmitGrantResult(FlyVerifyActionType actionType, 
                                                  bool status, 
                                                  string observer);
        [DllImport("__Internal")]
        static extern void __iosRegisterAppWithAppKeyAndAppSerect(FlyVerifyActionType actionType, 
                                                                  string appKey, 
                                                                  string appSecret, 
                                                                  string observer);
        
        [DllImport("__Internal")]
        static extern void __iosPrelogin(FlyVerifyActionType actionType, double timeout, string observer);
        [DllImport("__Internal")]
        static extern void __iosLoginAuth(FlyVerifyActionType actionType, string uiconfig, string observer);
        [DllImport("__Internal")]
        static extern void __iosPreMobileAuth(FlyVerifyActionType actionType, double time, string observer);
        [DllImport("__Internal")]
        static extern void __iosMobileAuth(FlyVerifyActionType actionType, 
                                           string phoneNum, 
                                           string tokenInfo, 
                                           double timeout, 
                                           string observer);
        
        [DllImport("__Internal")]
        static extern void __iosDismissLoginAuthVCLoading(FlyVerifyActionType actionType);
        [DllImport("__Internal")]
        static extern void __iosDismissLoginAuthVC(FlyVerifyActionType actionType, bool animated, string observer);

        [DllImport("__Internal")]
        static extern void __iosGetSDKVersion(FlyVerifyActionType actionType, string observer);
        [DllImport("__Internal")]
        static extern void __iosEnableSDKDebug(FlyVerifyActionType actionType, bool enable);
        [DllImport("__Internal")]
        static extern void __iosClearSDKPhoneScripCache(FlyVerifyActionType actionType, string observer);
        [DllImport("__Internal")]
        static extern void __iosGetCurrentOperatorType(FlyVerifyActionType actionType, string observer);
        [DllImport("__Internal")]
        static extern void __iosFlyVerifySupport(FlyVerifyActionType actionType, string observer);
        [DllImport("__Internal")]
        static extern void __iosSetCheckBoxValue(FlyVerifyActionType actionType, bool isSelected);
        
        // To get the callback id
        private string _callbackObjectName = "Main Camera";
        public FlyVerifyIOSImpl(GameObject go)
        {
            try {
                _callbackObjectName = go.name;
            } catch (Exception e) {
                Console.WriteLine("{0} Exception caught: ", e);
            }
        }

        /// <summary>
        /// Init the specified appKey, appSecret
        /// <summary>
        /// <param name="appKey">Mob App Key.</param>
        /// <param name="appSecret">Mob App Secret.</param>
        public override void init(string appKey, string appSecret) {

            if (Application.platform == RuntimePlatform.IPhonePlayer)
            {
                __iosRegisterAppWithAppKeyAndAppSerect(FlyVerifyActionType.registerApp, appKey, appSecret, _callbackObjectName);
            }
        }

        /// <summary>
		/// Upload the privacy grant status.
		/// </summary>
		/// <param name="status">Status.</param>
        public override void submitPrivacyGrantStatus(bool status) {
            __iosSubmitGrantResult(FlyVerifyActionType.uploadPrivacyGrantResult, status, _callbackObjectName);
        }

        /// <summary>
        /// To get the FlyVerifySDK's version
        /// <summary>
        public override void sdkVersion() {
            Console.WriteLine("Call FlyVerify iOSImpl SDKVersion Method!");
            __iosGetSDKVersion(FlyVerifyActionType.sdkVersion, _callbackObjectName);
        }

        /// <summary>
        /// Set whether is debug mode
        /// <summary>
        /// <param name="enable">bool value.</param>
        public override void enableDebug(bool enable) {
            __iosEnableSDKDebug(FlyVerifyActionType.enableDebug, enable);
        }

        /// <summary>
        /// Is the SDK available
        /// <summary>
        public override void isVerifySupport() {
            __iosFlyVerifySupport(FlyVerifyActionType.sdkSupport, _callbackObjectName);
        }

        /// <summary>
        /// Clear the SDK cache
        /// <summary>
        public override void clearPhoneScripCache() {
            __iosClearSDKPhoneScripCache(FlyVerifyActionType.clearCache, _callbackObjectName);
        }

        /// <summary>
        /// Get the current operator type (for reference only)
        /// <summary>
        public override void currentOperatorType() {
            __iosGetCurrentOperatorType(FlyVerifyActionType.operatorType, _callbackObjectName);
        }

        /// <summary>
        /// PreLogin with timeout
        /// <summary>
        /// <param name="timeout">double value.</param>
        public override void preLogin(double timeout) {
            __iosPrelogin(FlyVerifyActionType.preLogin, timeout, _callbackObjectName);
        }

        /// <summary>
        /// Set the value of CheckBox
        /// <summary>
        /// <param name="isSelected">bool value.</param>
        public override void setCheckBoxValue(bool isSelected) {
            __iosSetCheckBoxValue(FlyVerifyActionType.setCheckBox, isSelected);
        }

        /// <summary>
        /// Open the login auth vc
        /// <summary>
        /// <param name="uiconfig">The uiconfig.</param>
        public override void openAuthPage(FlyVerifySDKUIConfig uiconfig) {
            string configStr = MiniJSON.jsonEncode(uiconfig.uiConfig());
            __iosLoginAuth(FlyVerifyActionType.loginAuth, configStr, _callbackObjectName);
        }

        /// <summary>
        /// Hide the loding view when manual dismiss the login auth vc.
        /// <summary>
        public override void hideAuthPageLoading() {
            __iosDismissLoginAuthVCLoading(FlyVerifyActionType.dismissLoading);
        }

        /// <summary>
        /// Dismiss the login auth vc when manual dismiss
        /// <summary>
        /// <param name="animated">bool value.</param>
        public override void finishAuthPage(bool animated) {
            __iosDismissLoginAuthVC(FlyVerifyActionType.dismissAuthPage, animated, _callbackObjectName);
        }

        /// <summary>
        /// Request the mobile auth token with timeout
        /// <summary>
        /// <param name="timeout">double value.</param>
        public override void mobileAuthToken(double timeout) {
            __iosPreMobileAuth(FlyVerifyActionType.preMobileAuth, timeout, _callbackObjectName);
        }

        /// <summary>
        /// Verify that the phone number is correct
        /// <summary>
        /// <param name="phoneNum">phone number</param>
        /// <param name="tokenInfo">detail token info.</param>
        /// <param name="timeout">double value.</param>
        public override void mobileVerify(string phoneNum, 
                                          object tokenInfo, 
                                          double timeout) {
            string tokenInfoStr = (string)MiniJSON.jsonEncode(tokenInfo);                                  
            __iosMobileAuth(FlyVerifyActionType.mobileAuth, phoneNum, tokenInfoStr, timeout, _callbackObjectName);
        }
    }
    #endif
}