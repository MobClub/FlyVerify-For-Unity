package cn.fly.verify.unity3d;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import cn.fly.verify.FlyVerify;
import cn.fly.verify.OperationCallback;
import cn.fly.verify.VerifyCallback;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.ui.component.CommonProgressDialog;
import cn.fly.tools.utils.HashonHelper;
import com.unity3d.player.UnityPlayer;

import java.util.HashMap;

public class FlyVerifyUtils {
    private static Context context;
    private static String u3dGameObject;
    private static String u3dCallback;

    public FlyVerifyUtils(final String gameObject,final String callback) {
        if (context == null) {
            context = UnityPlayer.currentActivity.getApplicationContext();
        }
        u3dGameObject = gameObject;
        u3dCallback = callback;
    }
    //初始化
    private void init(String appKey, String appSecret) {
        if (TextUtils.isEmpty(appKey) || TextUtils.isEmpty(appSecret))
            return;
        FlyVerify.init(context, appKey, appSecret);
    }
    //隐私协议
    private void submitPrivacyGrantResult(boolean isGrant) {
        FlyVerify.submitPolicyGrantResult(isGrant);
    }
    //环境是否可用
    private void isVerifySupport() {
        Boolean isSupport = FlyVerify.isVerifySupport();
        //将结果转换成json回调回去
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("isSupport",isSupport);
        String formatStr = FormartResponseUtils.getFormartCompleteResponse("2",map);
        UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
    }
    //获取秒验版本号
    private void getVersion() {
        String version = FlyVerify.getVersion();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("version", version);
        String formatStr = FormartResponseUtils.getFormartCompleteResponse("3", map);
        UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
    }

    private void currentOperatorType(){
        try {
            int type = OperatorUtils.getCellularOperatorType();
            String tempString = "NOTSUPPORT";
            if (type == 1) {
                //CMCC
                tempString = "CMCC";
            } else if (type == 2) {
                // CUCC
                tempString = "CUCC";
            } else if (type == 3) {
                // CTCC
                tempString = "CTCC";
            }
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("operator",tempString);
            String formatStr = FormartResponseUtils.getFormartCompleteResponse("6",map);
            UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
        } catch (Throwable t) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("operatorErr",t.toString());
            String formatStr = FormartResponseUtils.getFormartCompleteResponse("6",map);
            UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
        }
    }

    /**
     * 预取号
     */
    private void preVerify(double timeout) {
        FlyVerify.preVerify(new cn.fly.verify.OperationCallback() {
            @Override
            public void onComplete(Object o) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("success",true);
                String formatStr = FormartResponseUtils.getFormartCompleteResponse("8", map);
                UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
            }

            @Override
            public void onFailure(VerifyException e) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("err_desc",e.getMessage().toString());
                map.put("err_code",e.getCode());

                String formatStr = FormartResponseUtils.getFormartErrorResponse("8",e);
                UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
            }
        });
    }

    /**
     * 登录
     */
    private void verify() {
        Log.i("FlyVerify","verify");
        FlyVerify.verify(new VerifyCallback() {
            @Override
            public void onOtherLogin() {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("result", "onOtherLogin");
                String formatStr = FormartResponseUtils.getFormartCompleteResponse("9",map);
                UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
            }

            @Override
            public void onUserCanceled() {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("result", "onUserCanceled");
                String formatStr = FormartResponseUtils.getFormartCompleteResponse("9",map);
                UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
            }

            @Override
            public void onComplete(VerifyResult verifyResult) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("result", "onComplete");
                map.put("token",verifyResult.getToken());
                map.put("Operator",verifyResult.getOperator());
                map.put("OpToken",verifyResult.getOpToken());
                String formatStr = FormartResponseUtils.getFormartCompleteResponse("9",map);
                UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
                //关闭loading
                CommonProgressDialog.dismissProgressDialog();
            }

            @Override
            public void onFailure(VerifyException e) {
                String formatStr = FormartResponseUtils.getFormartErrorResponse("9", e);
                UnityPlayer.UnitySendMessage(u3dGameObject, u3dCallback, formatStr);
            }
        });
    }

    /**
     * 设置竖屏
     * @param map
     */
    private void setPortraitUiSettings(String json) {
        HashMap map = HashonHelper.fromJson(json);
        UiSettings uiSettings = UiSettingsTransfer.transferUiSettings(map);
        FlyVerify.setUiSettings(uiSettings);
    }

    /**
     * 设置横屏
     * @param map
     */
    private void setLandUiSettings(String json) {
        HashMap map = HashonHelper.fromJson(json);
        LandUiSettings uiSettings = LandUiSettingsTransfer.transferLandUiSettings(map);
        FlyVerify.setLandUiSettings(uiSettings);
    }

    /**
     * 调试状态设置
     *
     * @param debugMode
     */
    private void setDebugMode(boolean debugMode) {
        FlyVerify.setDebugMode(debugMode);
    }

    /**
     * 设置超时时间
     *
     * @param timeout
     */
    private void setTimeOut(int timeout) {
        FlyVerify.setTimeOut(timeout);
    }

    //设置是否自动关闭一键登录页面
    private void autoFinishOauthPage(boolean autoFinish) {
        Log.i("FlyVerify","autoFinishOauthPage:"+autoFinish);
        FlyVerify.autoFinishOAuthPage(autoFinish);
    }

    //登录验证
    public void loginAuth(String uiconfig) {
        try {
            Log.i("FlyVerify","loginAuth:"+uiconfig);
            setLandUiSettings(uiconfig);
            setPortraitUiSettings(uiconfig);
            HashMap map = HashonHelper.fromJson(uiconfig);
            boolean isManualDismiss = false;
            if (map.containsKey("manualDismiss")) {
                isManualDismiss = (boolean) map.get("manualDismiss");
            }
            FlyVerify.autoFinishOAuthPage(isManualDismiss);

            verify();
        } catch (Throwable t) {
            System.out.println(t.toString());
        }
    }
}
