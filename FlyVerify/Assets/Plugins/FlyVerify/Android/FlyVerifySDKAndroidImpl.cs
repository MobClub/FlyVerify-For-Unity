using System;
using UnityEngine;
using System.Collections;
namespace cn.FlyVerifySDK.Unity
{
#if UNITY_ANDROID
    public class FlyVerifySDKAndroidImpl : FlyVerifySDKInterface
    {
        private AndroidJavaObject FlyVerifyObj;

        public FlyVerifySDKAndroidImpl (GameObject go) 
	    {
	        try{
                FlyVerifyObj = new AndroidJavaObject("cn.fly.verify.unity3d.FlyVerifyUtils", go.name, "_callback");
            } catch(Exception e) {
	            Console.WriteLine("{0} Exception caught.", e);
            }
        }

        public override void init(string appKey, string appSecret)
        {
            FlyVerifyObj.Call("init", appKey, appSecret);

        }

        public override void submitPrivacyGrantStatus(bool status)
        {
             FlyVerifyObj.Call("submitPrivacyGrantResult", status);
        }

        public override void isVerifySupport()
        {
            FlyVerifyObj.Call("isVerifySupport");
        }

        public override void clearPhoneScripCache()
        {
            Debug.Log("FlyVerifySDKAndroidImpl ==>>> clearPhoneScripCache");

        }

        public override void preLogin(double timeout)
        {
            FlyVerifyObj.Call("preVerify", timeout);
        }


        public override void openAuthPage(FlyVerifySDKUIConfig uiconfig)
        {
            Hashtable table = uiconfig.uiConfig();
            string configStr = MiniJSON.jsonEncode(table);
            FlyVerifyObj.Call("setLandUiSettings", configStr);
            FlyVerifyObj.Call("setPortraitUiSettings", configStr);

            bool isManualDismiss = (bool)table["manualDismiss"];
            FlyVerifyObj.Call("autoFinishOauthPage", isManualDismiss);
            FlyVerifyObj.Call("verify");
        }

        public override void currentOperatorType()
        {
            FlyVerifyObj.Call("currentOperatorType");
        }

        public override void enableDebug(bool enable)
        {
            FlyVerifyObj.Call("setDebugMode", enable);
        }

        public override void finishAuthPage(bool animated)
        {
            FlyVerifyObj.Call("autoFinishOauthPage", animated);
        }

        public override void hideAuthPageLoading()
        {
            Debug.Log("FlyVerifySDKAndroidImpl ==>>> hideAuthPageLoading: Android is not support yet");
        }

        public override void mobileAuthToken(double timeout)
        {
            Debug.Log("FlyVerifySDKAndroidImpl ==>>> mobileAuthToken: Android is not support yet");

        }

        public override void mobileVerify(string phoneNum, object tokenInfo, double timeout)
        {
            Debug.Log("FlyVerifySDKAndroidImpl ==>>> mobileVerify: Android is not support yet");
        }

        public override void sdkVersion()
        {
            FlyVerifyObj.Call("getVersion");
        }

        public override void setCheckBoxValue(bool isSelected)
        {
            
        }
    }

    
#endif
}