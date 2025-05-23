using System;

namespace cn.FlyVerifySDK.Unity
{
    public abstract class FlyVerifySDKInterface
    {
        /// <summary>
		/// Init the specified appKey, appSerect and isWarn.
		/// </summary>
		/// <param name="appKey">App key.</param>
		/// <param name="appSerect">App serect.</param>
        public abstract void init(string appKey, string appSecret);

        /// <summary>
		/// Upload the privacy grant status.
		/// </summary>
		/// <param name="status">Status.</param>
        public abstract void submitPrivacyGrantStatus(bool status);

        /// <summary>
		/// Method to get the sdk version
		/// </summary>
        public abstract void sdkVersion();

        /// <summary>
		/// FlyVerify whether support.
		/// </summary>
        public abstract void isVerifySupport();

        /// <summary>
		/// Method to clear cache.
		/// </summary>
        public abstract void clearPhoneScripCache();

        /// <summary>
		/// Method to get the current data card operator(results are for reference only)
        /// CMCC: Mobile CUCC: Unicom CTCC: Telecom
		/// </summary>
        public abstract void currentOperatorType();

        /// <summary>
		/// Request Token Pre Login
		/// </summary>
        public abstract void preLogin(double timeout);

        /// <summary>
		/// Set the check box value.
		/// </summary>
		/// <param name="isSelected">bool value</param>
        public abstract void setCheckBoxValue(bool isSelected);

        /// <summary>
		/// Set the check box value.
		/// </summary>
		/// <param name="isSelected">bool value</param>
        public abstract void openAuthPage(FlyVerifySDKUIConfig uiconfig);

        /// <summary>
		/// Method to hide the auth page loading view.
		/// </summary>
        public abstract void hideAuthPageLoading();

        /// <summary>
		/// Dismiss the auth page when the parameter is manual dismiss.
		/// </summary>
		/// <param name="animated">bool value</param>
        public abstract void finishAuthPage(bool animated);

        /// <summary>
		/// Request MobileAuth token
		/// </summary>
        public abstract void mobileAuthToken(double timeout);

        /// <summary>
		/// Verify Phone Number With TokenInfo
		/// </summary>
        public abstract void mobileVerify(string phoneNum, 
                                          object tokenInfo, 
                                          double timeout);

		// Tool Methods

		/// <summary>
		/// Set whether to enable Debug mode.
		/// </summary>
		/// <param name="enable">bool</param>
        public abstract void enableDebug(bool enable);						
    }
}