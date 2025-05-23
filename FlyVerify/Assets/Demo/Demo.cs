using System;
using UnityEngine;
using UnityEditor;
using System.Collections;
using cn.FlyVerifySDK.Unity;


public class Demo : MonoBehaviour, FlyVerifySDKHandlers
{
    public GUISkin demoSkin;
    public FlyVerify flyVerify;
    private string phone;
    public Hashtable tokenInfo;
    private string completeResult = null;

    void Start()
    {
        flyVerify = gameObject.GetComponent<FlyVerify>();
        if (Application.platform == RuntimePlatform.Android) {
            flyVerify.init("3a2c2a977d3d2", "b89d2427a3bc7ad1aea1e1e8c1d36bf3");
        }
        flyVerify.setHandler(this);
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            Application.Quit();
        }
    }
  
    void OnGUI()
    {
        GUI.skin = demoSkin;
		float scale = 1.0f;
		if (Application.platform == RuntimePlatform.IPhonePlayer) {
			scale = Screen.width / 300;
		} else if (Application.platform == RuntimePlatform.Android) {
			if (Screen.orientation == ScreenOrientation.Portrait) {
				scale = Screen.width / 320;
		    } else {
			    scale = Screen.height / 320;
	        }
		}
				
		float btnWidth = 200 * scale;
		float btnHeight = 30 * scale;
		float btnTop = 50 * scale;
		GUI.skin.button.fontSize = Convert.ToInt32(14 * scale);
		GUI.skin.label.fontSize = Convert.ToInt32 (14 * scale);
		GUI.skin.label.alignment = TextAnchor.MiddleCenter;
		GUI.skin.textField.fontSize = Convert.ToInt32 (14 * scale);
		GUI.skin.textField.alignment = TextAnchor.MiddleCenter;

		float labelWidth = 60 * scale;
        
        btnTop += btnHeight + 10 * scale;
        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "同意隐私协议"))
        {
            Debug.Log("FlyVerify:cs " + "FlyVerify-btn-submitPrivacyGrantStatus:");
            flyVerify.submitPrivacyGrantStatus(true);
        }

        btnTop += btnHeight + 10 * scale;
       
        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "秒验功能是否可用"))
        {
            flyVerify.isFlyVerifySupport();
        }
        btnTop += btnHeight + 10 * scale;

        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "获取秒验版本号"))
        {
            flyVerify.getSDKVersion();
        }
        btnTop += btnHeight + 10 * scale;

        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "运营商类型"))
        {
            flyVerify.operatorType();
        }
        btnTop += btnHeight + 10 * scale;

        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "清理缓存"))
        {
            flyVerify.clearSDKCache();
        }
        btnTop += btnHeight + 10 * scale;

        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "登录预取号"))
        {
            flyVerify.preLogin(5.0);
        }
        btnTop += btnHeight + 10 * scale;

        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "登录验证-自动关闭"))
        {
            FlyVerifySDKUIConfig uiConfig = configLoginAuthVC(true);
            flyVerify.loginAuth(uiConfig);
        }
        btnTop += btnHeight + 10 * scale;

        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "登录验证-手动关闭"))
        {

             FlyVerifySDKUIConfig uiConfig = configLoginAuthVC(false);
             flyVerify.loginAuth(uiConfig);
        }
        btnTop += btnHeight + 10 * scale;

        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "请求本机认证Token"))
        {
            flyVerify.preMobileAuth(5.0);
        }
        btnTop += btnHeight + 10 * scale;
        phone = GUI.TextField(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), phone);
        btnTop += btnHeight + 10 * scale;
        if (GUI.Button(new Rect((Screen.width - btnWidth) / 2, btnTop + 5, btnWidth, btnHeight), "本机认证"))
        {
            Console.WriteLine("Current Token Token TokenInfo: {0}", MiniJSON.jsonEncode(tokenInfo));
            if (phone != null && phone.Length > 0 && tokenInfo != null)
            {
                flyVerify.mobileAuth(phone, tokenInfo, 5.0);
            }
        }

        //展示回调结果
        btnTop += btnHeight + 10 * scale;
        GUIStyle style = new GUIStyle();
        style.normal.textColor = new Color(1, 0, 0);   //字体颜色
        style.wordWrap = true;                              // 换行
        style.fontSize = (int)(15 * scale);       //字体大小
        GUI.Label(new Rect(20, btnTop, Screen.width - 20 - 20, Screen.height - btnTop), completeResult, style);
    }

    // Callback Methods
    public void onComplete(FlyVerifyResponseResult result) {
        
        FlyVerifyActionType actionType = result.actionType;
        FlyVerifyResultStatus status = result.status;
        Hashtable response = (Hashtable)result.response;
        if (response != null)
        {
            Hashtable ret = (Hashtable)response["ret"];
            completeResult = MiniJSON.jsonEncode(ret);
        }

        if (status == FlyVerifyResultStatus.failure) {
            return;
        }
        switch (actionType)
            {
                case FlyVerifyActionType.uploadPrivacyGrantResult:{
                    Hashtable ret = (Hashtable)response["ret"];
                    Debug.Log("FlyVerify:cs "+ "Demo-onComplet: " + MiniJSON.jsonEncode(ret));
                }
                    break;
                case FlyVerifyActionType.sdkSupport: {
                    Debug.Log("FlyVerify:cs " + "Demo-onComplet: sdkSupport");
                    Hashtable ret = (Hashtable)response["ret"];
                    Debug.Log("FlyVerify:cs " + "Demo-onComplet: " +MiniJSON.jsonEncode(ret));
                }
                    break;
                case FlyVerifyActionType.sdkVersion: {
                    Hashtable ret = (Hashtable)response["ret"];
                    Debug.Log("FlyVerify:cs " + MiniJSON.jsonEncode(ret));
                }
                    break;
                case FlyVerifyActionType.operatorType: {
                    Hashtable ret = (Hashtable)response["ret"];
                    Debug.Log("FlyVerify:cs " + MiniJSON.jsonEncode(ret));
                }
                    break;
                case FlyVerifyActionType.preLogin: {
                    Hashtable ret = (Hashtable)response["ret"];
                    Debug.Log("FlyVerify:cs " + MiniJSON.jsonEncode(ret));
                }
                    break;
                case FlyVerifyActionType.loginAuth: {
                    switch (result.listenerType)
                    {
                        case FlyVerifyIOSAuthPageListenerType.openAuthPageEvent:
                        case FlyVerifyIOSAuthPageListenerType.cancelAuthPageEvent:
                        case FlyVerifyIOSAuthPageListenerType.loginAuthEvent:
                        default:
                            Hashtable ret = (Hashtable)response["ret"];
                            Debug.Log("FlyVerify:cs " + MiniJSON.jsonEncode(ret));
                            break;
                    }
                }
                    break;
                case FlyVerifyActionType.dismissAuthPage: {
                    Debug.Log("FlyVerify:cs " + "Dismiss Login Auth VC!");
                }
                    break;
                case FlyVerifyActionType.preMobileAuth: {
                    tokenInfo = (Hashtable)response["ret"];
                    Console.WriteLine("Return return return Token: {0}", tokenInfo);
                    Debug.Log("FlyVerify:cs " + MiniJSON.jsonEncode(tokenInfo));
                }
                    break;
                case FlyVerifyActionType.mobileAuth: {
                    Hashtable ret = (Hashtable)response["ret"];
                    Debug.Log("FlyVerify:cs " + MiniJSON.jsonEncode(ret));
                }
                    break;
                case FlyVerifyActionType.authPageCustomEvent: {
                    Hashtable ret = (Hashtable)response["ret"];
                    Debug.Log("FlyVerify:cs " + MiniJSON.jsonEncode(ret));
                }
                    break;
                // No Handlers
                case FlyVerifyActionType.others:
                case FlyVerifyActionType.registerApp:
                case FlyVerifyActionType.enableDebug:
                case FlyVerifyActionType.clearCache:
                case FlyVerifyActionType.setCheckBox:
                case FlyVerifyActionType.dismissLoading:
                default:
                    break;
            }
    }

    public void onError(FlyVerifyResponseResult result) {
        FlyVerifyActionType actionType = result.actionType;
        FlyVerifyResultStatus status = result.status;
        Hashtable response = (Hashtable)result.response;

        Hashtable err = (Hashtable)response["err"];
        string err_msg = (string)err["err_desc"];
        int err_code = Convert.ToInt32(err["err_code"]);
        completeResult = err_code+ err_msg;
        Console.WriteLine("FlyVerify:cs " + "Error Code: {0}, Error Msg: {1}", err_code, err_msg);
    }

    private FlyVerifySDKUIConfig configLoginAuthVC(bool manualDismiss) {
    //TODO: 待修改
//        if (Application.platform == RuntimePlatform.IPhonePlayer)
//        {
//            FlyVerifySDKIOSUIConfig iOSConfig = new FlyVerifySDKIOSUIConfig();
//            iOSConfig.navBarHidden = true;
//            iOSConfig.manualDismiss = manualDismiss;
//
//            iOSConfig.prefersStatusBarHidden = false;
//            iOSConfig.preferredStatusBarStyle = cn.FlyVerifySDK.Unity.iOSStatusBarStyle.styleDefault;
//
//            iOSConfig.shouldAutorotate = true;
//            iOSConfig.supportedInterfaceOrientations = iOSInterfaceOrientationMask.all;
//            iOSConfig.preferredInterfaceOrientationForPresentation = iOSInterfaceOrientation.portrait;
//
//            iOSConfig.presentingWithAnimate = true;
//            iOSConfig.modalTransitionStyle = iOSModalTransitionStyle.coverVertical;
//            iOSConfig.modalPresentationStyle = iOSModalPresentationStyle.fullScreen;
//
//            iOSConfig.showPrivacyWebVCByPresent = false;
//            iOSConfig.privacyWebVCPreferredStatusBarStyle = cn.FlyVerifySDK.Unity.iOSStatusBarStyle.styleDefault;
//            iOSConfig.privacyWebVCModalPresentationStyle = iOSModalPresentationStyle.fullScreen;
//
//            iOSConfig.overrideUserInterfaceStyle = iOSUserInterfaceStyle.unspecified;
//
//            return iOSConfig;
//        } else
        if (Application.platform == RuntimePlatform.Android) {
                FlyVerifySDKAndroidUIConfig andConfig = new FlyVerifySDKAndroidUIConfig();
                andConfig.manualDismiss = manualDismiss;
                andConfig.navHidden = false;
                andConfig.loginBtnTextStringName = "一键登录";
                andConfig.dialogTheme = false;
                andConfig.navCloseImgHidden = false;
                andConfig.sloganHidden = false;
                andConfig.checkboxDefaultState = false;
                andConfig.switchAccText = "切换账号";

            return andConfig;
        } else {
            FlyVerifySDKOtherUIConfig otherConfig = new FlyVerifySDKOtherUIConfig();

            return otherConfig;
        }
    }
}
