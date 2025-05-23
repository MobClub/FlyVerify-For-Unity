using System;
using System.Collections;

namespace cn.FlyVerifySDK.Unity
{
    public enum FlyVerifyResultStatus
    {
        success,
        failure,
    }

    public enum FlyVerifyActionType
    {
        // tools method
        registerApp,
        uploadPrivacyGrantResult,
        sdkSupport,
        sdkVersion, 
        enableDebug,
        operatorType,
        clearCache,
        setCheckBox,
        // mobile auto login
        preLogin,
        loginAuth,
        dismissLoading,
        dismissAuthPage,
        // mobile num verify
        preMobileAuth,
        mobileAuth,
        // Custom Subviews Event
        authPageCustomEvent,
        // Other
        others
    }

    public enum FlyVerifyIOSAuthPageListenerType
    {
        unknowEvent,
        openAuthPageEvent,
        cancelAuthPageEvent,
        loginAuthEvent,
    }

    public struct FlyVerifyResponseResult
    {
        /// Parameters
        public FlyVerifyActionType actionType;
        // If is android, don't need use this parameter.
        // If the platform is iOS and the action type == logingAuth, 
        // use this parameter to distinguish different callbacks.
        public FlyVerifyIOSAuthPageListenerType listenerType;
        // If have added custom ui, the tag parameter was is to distinguish different UIs.
        public string tag;

        // success = 0, failure = 1
        public FlyVerifyResultStatus status;
        // response
        public object response;

        /// Init method
        public FlyVerifyResponseResult(FlyVerifyActionType type,  
                                    FlyVerifyResultStatus status, 
                                    object response,
                                    string tag = null, 
                                    FlyVerifyIOSAuthPageListenerType pageListenerType = FlyVerifyIOSAuthPageListenerType.unknowEvent) {
            this.actionType = type;
            this.listenerType = pageListenerType;
            this.tag = tag;
            this.status = status;
            this.response = response;
        }
    }

    public interface FlyVerifySDKHandlers
    {
        void onComplete(FlyVerifyResponseResult result);
        void onError(FlyVerifyResponseResult error);
    }
}