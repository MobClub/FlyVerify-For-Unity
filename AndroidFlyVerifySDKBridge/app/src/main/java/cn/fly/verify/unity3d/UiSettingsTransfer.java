package cn.fly.verify.unity3d;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.fly.verify.FlyVerify;
import cn.fly.verify.CustomUIRegister;
import cn.fly.verify.CustomViewClickListener;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;
import cn.fly.verify.util.ResHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static cn.fly.verify.util.ResHelper.dipToPx;
import static cn.fly.verify.util.ResHelper.pxToDip;
import static cn.fly.verify.unity3d.CommonUtil.createBitmap;

public class UiSettingsTransfer {
	public static final String TAG = "Transfer";


	public static UiSettings transferUiSettings(HashMap map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		UiSettings.Builder builder = (UiSettings.Builder) parse(map);
		return builder.build();

	}

	private static UiSettings.Builder parse(HashMap options) {
		UiSettings.Builder builder = new UiSettings.Builder();
		if(options==null){
			return builder;
		}

		/**导航栏*/
//		if (options.containsKey("customNav")) {
//			HashMap nav = (HashMap) options.get("customNav");
//			setNavBuilder(builder, nav);
//		}
		if (options.containsKey("navTransparent")) {
			boolean navTransparent = (boolean) options.get("navTransparent");
			builder.setNavTransparent(navTransparent);
		}
		if (options.containsKey("navHidden")) {
			boolean navHidden = (boolean) options.get("navHidden");
			builder.setNavHidden(navHidden);
		}
		if (options.containsKey("navColorIdName")) {
			String navColor = (String) options.get("navColorIdName");
			if (!TextUtils.isEmpty(navColor)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),navColor);
				builder.setNavColorId(colorId);
			}
		}
		if (options.containsKey("navTextSize")) {
			/**导航栏文字*/
			int navTextSize = (int) options.get("navTextSize");
			if (navTextSize > 0) {
				builder.setNavTextSize(navTextSize);
			}
		}

		if (options.containsKey("navTextColorIdName")) {
			String navTextColor = (String) options.get("navTextColorIdName");
			if (!TextUtils.isEmpty(navTextColor)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),navTextColor);
				builder.setNavTextColorId(colorId);
			}
		}
		if (options.containsKey("navTextBold")) {
			boolean navTexBold = (boolean) options.get("navTextBold");
			builder.setNavTextBold(navTexBold);
		}

		/**导航栏图片*/
		int navReturnImgDrawableWidth = 0;
		int navReturnImgDrawableHeight = 0;
		int navReturnImgDrawableOffsetX = -1;
		int navReturnImgDrawableOffsetRightX = -1;
		int navReturnImgDrawableOffsetY = -1;
		if (options.containsKey("navCloseImgWidth")) {
			navReturnImgDrawableWidth = (int) options.get("navCloseImgWidth");
		}
		if (options.containsKey("navCloseImgHeight")) {
			navReturnImgDrawableHeight = (int) options.get("navCloseImgHeight");
		}
		if (options.containsKey("navCloseImgOffsetY")) {
			navReturnImgDrawableOffsetY = Math.abs((int) options.get("navCloseImgOffsetY"));
		}
		if (options.containsKey("navCloseImgOffsetX")) {
			navReturnImgDrawableOffsetX = Math.abs((int) options.get("navCloseImgOffsetX"));
		}
		if (options.containsKey("navCloseImgOffsetRightX")) {
			navReturnImgDrawableOffsetRightX = Math.abs((int) options.get("navCloseImgOffsetRightX"));
		}

		if (navReturnImgDrawableWidth > 0) {
			builder.setNavCloseImgWidth(navReturnImgDrawableWidth);
		}
		if (navReturnImgDrawableHeight > 0) {
			builder.setNavCloseImgHeight(navReturnImgDrawableHeight);
		}
		if (navReturnImgDrawableOffsetX > 0) {
			builder.setNavCloseImgOffsetX(navReturnImgDrawableOffsetX);
		}
		if (navReturnImgDrawableOffsetY > 0) {
			builder.setNavCloseImgOffsetY(navReturnImgDrawableOffsetY);
		}
		if (navReturnImgDrawableOffsetRightX > 0){
			builder.setNavCloseImgOffsetRightX(navReturnImgDrawableOffsetRightX);
		}
		if (navReturnImgDrawableWidth <= 0) {
			navReturnImgDrawableWidth = 30;
		}
		if (navReturnImgDrawableHeight <= 0) {
			navReturnImgDrawableHeight = 30;
		}
		if (options.containsKey("navCloseImgPath")) {
			String navReturnImgDrawablePath = (String) options.get("navCloseImgPath"); //assets里，转成drawable
			if (!TextUtils.isEmpty(navReturnImgDrawablePath)) {
				try {
					navReturnImgDrawablePath =  "flutter_assets" + File.separator + "assets" + File.separator +  navReturnImgDrawablePath;
					InputStream is = FlyVerify.getContext().getAssets().open(navReturnImgDrawablePath);
					Drawable navReturnImageDrawable = createBitmap(is, dipToPx(FlyVerify.getContext(), navReturnImgDrawableWidth), dipToPx(FlyVerify.getContext(), navReturnImgDrawableHeight));
					builder.setNavCloseImgId(navReturnImageDrawable);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (options.containsKey("navCloseImgHidden")) {
			boolean navReturnImgDrawableHidden = (boolean) options.get("navCloseImgHidden");
			builder.setNavCloseImgHidden(navReturnImgDrawableHidden);
		}

		if (options.containsKey("navTextIdName")) {
			String navText = (String) options.get("navTextIdName");
			int navTextId = ResHelper.getStringRes(FlyVerify.getContext(),navText);
			builder.setNavTextId(navTextId);
		}

		if (options.containsKey("navCloseImgScaleType")){
			String navCloseImgScaleType = (String) options.get("navCloseImgScaleType");
			switch (navCloseImgScaleType){
				case "CENTER":
					builder.setNavCloseImgScaleType(ImageView.ScaleType.CENTER);
					break;
				case "MATRIX":
					builder.setNavCloseImgScaleType(ImageView.ScaleType.MATRIX);
					break;
				case "FIT_XY":
					builder.setNavCloseImgScaleType(ImageView.ScaleType.FIT_XY);
					break;
				case "FIT_START":
					builder.setNavCloseImgScaleType(ImageView.ScaleType.FIT_START);
					break;
				case "FIT_CENTER":
					builder.setNavCloseImgScaleType(ImageView.ScaleType.FIT_CENTER);
					break;
				case "FIT_END":
					builder.setNavCloseImgScaleType(ImageView.ScaleType.FIT_END);
					break;
				case "CENTER_CROP":
					builder.setNavCloseImgScaleType(ImageView.ScaleType.CENTER_CROP);
					break;
				case "CENTER_INSIDE":
					builder.setNavCloseImgScaleType(ImageView.ScaleType.CENTER_INSIDE);
					break;
			}

		}


//		if (options.containsKey("customAuthPage")) {
//			HashMap page = (HashMap) options.get("customAuthPage");
//			setPageBuilder(builder, page);
//		}

		if (options.containsKey("backgroundImgPath")) {
			String backgroundImgDrawablepath = (String) options.get("backgroundImgPath"); //assets里，转成drawable
			if (!TextUtils.isEmpty(backgroundImgDrawablepath)) {
				try {
					backgroundImgDrawablepath =  "flutter_assets" + File.separator + "assets" + File.separator + backgroundImgDrawablepath;
					InputStream is = FlyVerify.getContext().getAssets().open(backgroundImgDrawablepath);
					//屏幕宽高
					DisplayMetrics displayMetrics = FlyVerify.getContext().getResources().getDisplayMetrics();
					int width = displayMetrics.widthPixels;
					int height = displayMetrics.heightPixels;
					Drawable backgroundImgDrawable = createBitmap(is, width, height);
					builder.setBackgroundImgId(backgroundImgDrawable);
				} catch (IOException e) {
				}
			}
		}

		if (options.containsKey("backgroundClickClose")) {
			boolean backgroundClickClose = (boolean) options.get("backgroundClickClose");
			builder.setBackgroundClickClose(backgroundClickClose);
		}

		if (options.containsKey("fullScreen")) {
			boolean fullScreen = (boolean) options.get("fullScreen");
			builder.setFullScreen(fullScreen);
		}

		if (options.containsKey("immersiveTheme")) {
			boolean immersiveTheme = (boolean) options.get("immersiveTheme");
			builder.setImmersiveTheme(immersiveTheme);
		}

		if (options.containsKey("immersiveStatusTextColorBlack")) {
			boolean immersiveStatusTextColorBlack = (boolean) options.get("immersiveStatusTextColorBlack");
			builder.setImmersiveStatusTextColorBlack(immersiveStatusTextColorBlack);
		}


//		if (options.containsKey("customAuthPageLogo")) {
//			HashMap logo = (HashMap) options.get("customAuthPageLogo");
//			setLogoBuilder(builder, logo);
//		}
		int logoWidth = -1;
		int logoHeight = -1;
		int logoOffsetX = -1;
		int logoOffsetY = -1;
		int logoOffsetBottomY = -1;
		int logoOffsetRightX = -1;

		if (options.containsKey("logoWidth")) {
			logoWidth = (int) options.get("logoWidth");
		}
		if (options.containsKey("logoHeight")) {
			logoHeight = (int) options.get("logoHeight");
		}
		if (options.containsKey("logoOffsetX")) {
			logoOffsetX = Math.abs((int) options.get("logoOffsetX"));
		}
		if (options.containsKey("logoOffsetY")) {
			logoOffsetY = Math.abs((int) options.get("logoOffsetY"));
		}
		if (options.containsKey("logoOffsetBottomY")) {
			logoOffsetBottomY = Math.abs((int) options.get("logoOffsetBottomY"));
		}
		if (options.containsKey("logoOffsetRightX")) {
			logoOffsetRightX = Math.abs((int) options.get("logoOffsetRightX"));
		}
		if (logoWidth <= 0) {
			logoWidth = 80;
		}
		if (logoHeight <= 0) {
			logoHeight = 80;
		}
		builder.setLogoWidth(logoWidth)
				.setLogoHeight(logoHeight);
		if (options.containsKey("logoImgPath")) {
			String logoImgDrawablePath = (String) options.get("logoImgPath"); //assets里，转成drawable
			if (!TextUtils.isEmpty(logoImgDrawablePath)) {
				try {
					logoImgDrawablePath = "flutter_assets" + File.separator + "assets" + File.separator+ logoImgDrawablePath;
					InputStream is = FlyVerify.getContext().getAssets().open(logoImgDrawablePath);
					Drawable logoImgDrawable = createBitmap(is, dipToPx(FlyVerify.getContext(), logoWidth), dipToPx(FlyVerify.getContext(), logoHeight));
					builder.setLogoImgId(logoImgDrawable);
				} catch (IOException e) {
				}
			}
		}
		if (logoOffsetX > 0) {
			builder.setLogoOffsetX(logoOffsetX);
		}
		if (logoOffsetY > 0) {
			builder.setLogoOffsetY(logoOffsetY);
		}
		if (logoOffsetRightX > 0) {
			builder.setLogoOffsetRightX(logoOffsetRightX);
		}
		if (logoOffsetBottomY > 0) {
			builder.setLogoOffsetBottomY(logoOffsetBottomY);
		}

		if (options.containsKey("logoHidden")) {
			boolean logoHidden = (boolean) options.get("logoHidden");
			builder.setLogoHidden(logoHidden);
		}
		if (options.containsKey("logoAlignParentRight")) {
			boolean logoAlignRight = (boolean) options.get("logoAlignParentRight");
			builder.setLogoAlignParentRight(logoAlignRight);
		}
		//customPhoneNumber
//		if (options.containsKey("customPhoneNumber")) {
//			HashMap number = (HashMap) options.get("customPhoneNumber");
//			setNumberBuilder(builder, number);
//		}

		if (options.containsKey("numberColorIdName")) {
			String phoneColor = (String) options.get("numberColorIdName");
			if (!TextUtils.isEmpty(phoneColor)) {
				int phoneColorId = ResHelper.getColorRes(FlyVerify.getContext(),phoneColor);
				builder = builder.setNumberColorId(phoneColorId);
			}
		}
		if (options.containsKey("numberSize")) {
			int phoneSize = (int) options.get("numberSize");
			if (phoneSize > 0) {
				builder = builder.setNumberSizeId(phoneSize);
			}
		}
		if (options.containsKey("numberHidden")) {
			boolean phoneHidden = (boolean) options.get("numberHidden");
			builder = builder.setNumberHidden(phoneHidden);
		}
		int phoneOffsetX = -1;
		int phoneOffsetY = -1;
		int phoneOffsetBottomY = -1;
		int phoneOffsetRightX = -1;
		if (options.containsKey("numberOffsetX")) {
			phoneOffsetX = Math.abs((int) options.get("numberOffsetX"));
		}
		if (options.containsKey("numberOffsetY")) {
			phoneOffsetY = Math.abs((int) options.get("numberOffsetY"));
		}
		if (options.containsKey("numberOffsetBottomY")) {
			phoneOffsetBottomY = Math.abs((int) options.get("numberOffsetBottomY"));
		}
		if (options.containsKey("numberOffsetRightX")) {
			phoneOffsetRightX = Math.abs((int) options.get("numberOffsetRightX"));
		}

		if (phoneOffsetX > 0) {
			builder = builder.setNumberOffsetX(phoneOffsetX);
		}
		if (phoneOffsetY > 0) {
			builder = builder.setNumberOffsetY(phoneOffsetY);
		}
		if (phoneOffsetRightX > 0) {
			builder = builder.setNumberOffsetRightX(phoneOffsetRightX);
		}
		if (phoneOffsetBottomY > 0) {
			builder = builder.setNumberOffsetBottomY(phoneOffsetBottomY);
		}

		if (options.containsKey("numberAlignParentRight")) {
			boolean phoneAlignParentRightNumber = (boolean) options.get("numberAlignParentRight");
			builder = builder.setNumberAlignParentRight(phoneAlignParentRightNumber);
		}
		if (options.containsKey("numberBold")) {
			boolean numberBold = (boolean) options.get("numberBold");
			builder = builder.setNumberBold(numberBold);
		}

//		if (options.containsKey("customSwitchNumber")) {
//			HashMap switchacc = (HashMap) options.get("customSwitchNumber");
//			setSwitchAccBuilder(builder, switchacc);
//		}
		/**切换登录*/
		if (options.containsKey("switchAccColor")) {
			String switchAccColor = (String) options.get("switchAccColor");
			if (!TextUtils.isEmpty(switchAccColor)) {
				int switchColodId = ResHelper.getColorRes(FlyVerify.getContext(),switchAccColor);
				builder.setSwitchAccColorId(switchColodId);
			}
		}
		if (options.containsKey("switchAccHidden")) {
			boolean switchAccHidden = (boolean) options.get("switchAccHidden");
			builder.setSwitchAccHidden(switchAccHidden);
		}
		if (options.containsKey("switchAccTextSize")) {
			int switchAccTextSize = (int) options.get("switchAccTextSize");
			if (switchAccTextSize > 0) {
				builder.setSwitchAccTextSize(switchAccTextSize);
			}
		}
		if (options.containsKey("switchAccText")) {
			String switchAccText = (String) options.get("switchAccText");
			if (!TextUtils.isEmpty(switchAccText)) {
				builder.setSwitchAccText(switchAccText);
			}
		}
		int switchAccOffsetX = -1;
		int switchAccOffsetY = -1;
		int switchAccOffsetBottomY = -1;
		int switchAccOffsetRightX = -1;
		if (options.containsKey("switchAccOffsetX")) {
			switchAccOffsetX = Math.abs((int) options.get("switchAccOffsetX"));
		}
		if (options.containsKey("switchAccOffsetY")) {
			switchAccOffsetY = Math.abs((int) options.get("switchAccOffsetY"));
		}
		if (options.containsKey("switchAccOffsetBottomY")) {
			switchAccOffsetBottomY = Math.abs((int) options.get("switchAccOffsetBottomY"));
		}
		if (options.containsKey("switchAccOffsetRightX")) {
			switchAccOffsetRightX = Math.abs((int) options.get("switchAccOffsetRightX"));
		}

		if (switchAccOffsetX > 0) {
			builder.setSwitchAccOffsetX(switchAccOffsetX);
		}
		if (switchAccOffsetY > 0) {
			builder.setSwitchAccOffsetY(switchAccOffsetY);
		}
		if (switchAccOffsetRightX > 0) {
			builder.setSwitchAccOffsetRightX(switchAccOffsetRightX);
		}
		if (switchAccOffsetBottomY > 0) {
			builder.setSwitchAccOffsetBottomY(switchAccOffsetBottomY);
		}

		if (options.containsKey("switchAccAlignParentRight")) {
			boolean switchAccAlignParentRight = (boolean) options.get("switchAccAlignParentRight");
			builder.setSwitchAccAlignParentRight(switchAccAlignParentRight);
		}
		if (options.containsKey("switchAccTextBold")) {
			boolean switchAccTextBold = (boolean) options.get("switchAccTextBold");
			builder.setSwitchAccTextBold(switchAccTextBold);
		}

//		if (options.containsKey("customCheckBox")) {
//			HashMap checkbox = (HashMap) options.get("customCheckBox");
//			setCheckBoxBuilder(builder, checkbox);
//		}
		int checkboxWidth = -1;
		int checkboxHeight = -1;
		int checkboxOffsetX = -1;
		int checkboxOffsetRightX = -1;
		int checkboxOffsetY = -1;
		int checkboxOffsetBottomY = -1;

		if (options.containsKey("checkboxWidth")) {
			checkboxWidth = (int) options.get("checkboxWidth");
		}
		if (options.containsKey("checkboxHeight")) {
			checkboxHeight = (int) options.get("checkboxHeight");
		}
		if (options.containsKey("checkboxOffsetY")) {
			checkboxOffsetY = Math.abs((int) options.get("checkboxOffsetY"));
		}
		if (options.containsKey("checkboxOffsetX")) {
			checkboxOffsetX = Math.abs((int) options.get("checkboxOffsetX"));
		}
		if (options.containsKey("checkboxOffsetBottomY")) {
			checkboxOffsetBottomY = Math.abs((int) options.get("checkboxOffsetBottomY"));
		}
		if (options.containsKey("checkboxOffsetRightX")) {
			checkboxOffsetRightX = Math.abs((int) options.get("checkboxOffsetRightX"));
		}
		if (checkboxWidth <= 0) {
			checkboxWidth = 30;
		}
		if (checkboxHeight <= 0) {
			checkboxHeight = 30;
		}
		if (checkboxOffsetX > 0) {
			builder.setCheckboxOffsetX(checkboxOffsetX);
		}
		if (checkboxOffsetY > 0) {
			builder.setCheckboxOffsetY(checkboxOffsetY);
		}
		if (checkboxOffsetBottomY > 0) {
			builder.setCheckboxOffsetBottomY(checkboxOffsetBottomY);
		}
		if (checkboxOffsetRightX > 0){
			builder.setCheckboxOffsetRightX(checkboxOffsetRightX);
		}
		
		if (options.containsKey("checkedImgName") && options.containsKey("uncheckedImgName")) {
			String checkboxCheckedImgPath = (String) options.get("checkedImgName"); //assets里，转成drawable
			String checkboxUncheckedImgPath = (String) options.get("uncheckedImgName"); //assets里，转成drawable
			StateListDrawable drawable = new StateListDrawable();

			if (checkboxCheckedImgPath.startsWith("#")) {
				drawable.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(Color.parseColor(checkboxCheckedImgPath)));
			} else {
				try {
					checkboxCheckedImgPath = "flutter_assets" + File.separator + "assets" + File.separator+ checkboxCheckedImgPath;
					InputStream is = FlyVerify.getContext().getAssets().open(checkboxCheckedImgPath);
					Drawable drawableStatePressed = createBitmap(is, dipToPx(FlyVerify.getContext(), checkboxWidth), dipToPx(FlyVerify.getContext(), checkboxHeight));
					drawable.addState(new int[]{android.R.attr.state_checked}, drawableStatePressed);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (checkboxUncheckedImgPath.startsWith("#")) {
				drawable.addState(new int[]{}, new ColorDrawable(Color.parseColor(checkboxUncheckedImgPath)));
			} else {
				try {
					checkboxUncheckedImgPath = "flutter_assets" + File.separator + "assets" + File.separator+ checkboxUncheckedImgPath;
					InputStream is = FlyVerify.getContext().getAssets().open(checkboxUncheckedImgPath);
					Drawable drawableStateUnPressed = createBitmap(is, dipToPx(FlyVerify.getContext(), checkboxWidth), dipToPx(FlyVerify.getContext(), checkboxHeight));
					drawable.addState(new int[]{}, drawableStateUnPressed);
					builder.setCheckboxImgId(drawable);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (options.containsKey("checkboxDefaultState")) {
			boolean checkboxDefaultState = (boolean) options.get("checkboxDefaultState");
			builder.setCheckboxDefaultState(checkboxDefaultState);
		}
		if (options.containsKey("checkboxHidden")) {
			boolean checkboxHidden = (boolean) options.get("checkboxHidden");
			builder.setCheckboxHidden(checkboxHidden);
		}
		if (options.containsKey("checkboxScaleX")) {
			double scaleDoubleX = (double) options.get("checkboxScaleX");
			float scaleFloatX = (float) scaleDoubleX;
			builder.setCheckboxScaleX(scaleFloatX);
		}
		if (options.containsKey("checkboxScaleY")) {
			double scaleDoubleY = (double) options.get("checkboxScaleY");
			float scaleFloatY = (float) scaleDoubleY;
			builder.setCheckboxScaleX(scaleFloatY);
		}

//		if (options.containsKey("customPrivacy")) {
//			HashMap privacy = (HashMap) options.get("customPrivacy");
//			setPrivacyBuilder(builder, privacy);
//		}
		/**隐私协议*/
		if (options.containsKey("agreementBaseTextColor")) {
			String privacyBaseTextColor = (String) options.get("agreementBaseTextColor");
			if (!TextUtils.isEmpty(privacyBaseTextColor)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),privacyBaseTextColor);
				builder.setAgreementBaseTextColorId(colorId);
			}
		}
		if (options.containsKey("agreementColor")) {
			String privacyColor = (String) options.get("agreementColor");
			if (!TextUtils.isEmpty(privacyColor)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),privacyColor);
				builder.setAgreementColorId(colorId);
			} else {
				builder.setAgreementColorId(Color.parseColor("#00B6B5"));
			}
		}
		if (options.containsKey("cusAgreementColor1")) {
			String privacyColor1 = (String) options.get("cusAgreementColor1");
			if (!TextUtils.isEmpty(privacyColor1)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),privacyColor1);
				builder.setCusAgreementColor1(colorId);
			} else {
				builder.setCusAgreementColor1(Color.parseColor("#00B6B5"));
			}
		}
		if (options.containsKey("cusAgreementColor2")) {
			String privacyColor2 = (String) options.get("cusAgreementColor2");
			if (!TextUtils.isEmpty(privacyColor2)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),privacyColor2);
				builder.setCusAgreementColor2(colorId);
			} else {
				builder.setCusAgreementColor2(Color.parseColor("#00B6B5"));
			}
		}
		if (options.containsKey("cusAgreementColor3")) {
			String privacyColor3 = (String) options.get("cusAgreementColor2");
			if (!TextUtils.isEmpty(privacyColor3)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),privacyColor3);
				builder.setCusAgreementColor3(colorId);
			} else {
				builder.setCusAgreementColor3(Color.parseColor("#00B6B5"));
			}
		}
		if (options.containsKey("agreementTextSize")) {
			int privacyTextSize = (int) options.get("agreementTextSize");
			if (privacyTextSize > 0) {
				builder.setAgreementTextSize(privacyTextSize);
			}
		}
		if (options.containsKey("agreementHidden")) {
			boolean privacyHidden = (boolean) options.get("agreementHidden");
			builder.setAgreementHidden(privacyHidden);
		}

		if (options.containsKey("agreementCmccTextString")) {
			String agreementCmccTextString = (String) options.get("agreementCmccTextString");
			builder.setAgreementCmccText(ResHelper.getStringRes(FlyVerify.getContext(),agreementCmccTextString));
		}
		if (options.containsKey("agreementCuccTextString")) {
			String agreementCuccTextString = (String) options.get("agreementCuccTextString");
			if(!TextUtils.isEmpty(agreementCuccTextString)){
				builder.setAgreementCuccText(agreementCuccTextString);
			}
		}

		if (options.containsKey("agreementCtccTextId")) {
			String agreementCtccTextId = (String) options.get("agreementCtccTextId");
			builder.setAgreementCtccText(ResHelper.getStringRes(FlyVerify.getContext(),agreementCtccTextId));
		}

		if (options.containsKey("agreementCtccTextString")) {
			String agreementCtccTextString = (String) options.get("agreementCtccTextString");
			System.out.println("来自flutter: "+agreementCtccTextString);
			builder.setAgreementCtccText(agreementCtccTextString);
		}
		if (options.containsKey("agreementCtccTextId")) {
			String agreementCtccTextId = (String) options.get("agreementCtccTextId");
			builder.setAgreementCtccText(ResHelper.getStringRes(FlyVerify.getContext(),agreementCtccTextId));
		}

		if (options.containsKey("cusAgreementUrl2")){
			String cusAgreementUrl2 = (String) options.get("cusAgreementUrl2");
			builder.setCusAgreementUrl2(cusAgreementUrl2);
		}

		if (options.containsKey("cusAgreementUrl3")){
			String cusAgreementUrl3 = (String) options.get("cusAgreementUrl3");
			builder.setCusAgreementUrl3(cusAgreementUrl3);
		}

		if (options.containsKey("cusAgreementNameId1")) {
			String cusAgreementNameId1 = (String) options.get("cusAgreementNameId1");
			builder.setCusAgreementNameId1(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameId1));
		}

		if (options.containsKey("cusAgreementNameText1")) {
			String cusAgreementNameText1 = (String) options.get("cusAgreementNameText1");
			builder.setCusAgreementNameId1(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameText1));
		}

		if (options.containsKey("cusAgreementUrl1")){
			String cusAgreementUrl1 = (String) options.get("cusAgreementUrl1");
			builder.setCusAgreementUrl1(cusAgreementUrl1);
		}

		if (options.containsKey("agreementTextAndString1")){
			String agreementTextAndString1 = (String) options.get("agreementTextAndString1");
			builder.setAgreementTextAnd1(ResHelper.getStringRes(FlyVerify.getContext(),agreementTextAndString1));
		}

		if (options.containsKey("cusAgreementNameId2")) {
			String cusAgreementNameId2 = (String) options.get("cusAgreementNameId2");
			builder.setCusAgreementNameId2(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameId2));
		}

		if (options.containsKey("cusAgreementNameText2")) {
			String cusAgreementNameText2 = (String) options.get("cusAgreementNameText2");
			builder.setCusAgreementNameId2(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameText2));
		}

		if (options.containsKey("agreementTextAndString2")){
			String agreementTextAndString2 = (String) options.get("agreementTextAndString2");
			System.out.println("来自flutter: "+agreementTextAndString2);
			builder.setAgreementTextAnd2(ResHelper.getStringRes(FlyVerify.getContext(),agreementTextAndString2));
		}

		if (options.containsKey("cusAgreementNameId3")) {
			String cusAgreementNameId3 = (String) options.get("cusAgreementNameId3");
			builder.setCusAgreementNameId3(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameId3));
		}

		if (options.containsKey("cusAgreementNameText3")) {
			String cusAgreementNameText3 = (String) options.get("cusAgreementNameText3");
			builder.setCusAgreementNameId3(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameText3));
		}

		if (options.containsKey("agreementTextAndString3")){
			String agreementTextAndString3 = (String) options.get("agreementTextAndString3");
			builder.setAgreementTextAnd3(ResHelper.getStringRes(FlyVerify.getContext(),agreementTextAndString3));
		}

		if (options.containsKey("agreementTextStartIdName")) {
			String agreementTextStartIdName = (String) options.get("agreementTextStartIdName");
			if (!TextUtils.isEmpty(agreementTextStartIdName)) {
				builder.setAgreementTextStart(ResHelper.getStringRes(FlyVerify.getContext(),agreementTextStartIdName));
			}
		}

		if (options.containsKey("agreementTextStartString")) {
			String agreementTextStartString = (String) options.get("agreementTextStartString");
			if (!TextUtils.isEmpty(agreementTextStartString)) {
				builder.setAgreementTextStart(agreementTextStartString);
			}
		}

		if (options.containsKey("agreementTextEndIdName")) {
			String agreementTextEndIdName = (String) options.get("agreementTextEndIdName");
			if (TextUtils.isEmpty(agreementTextEndIdName)) {
				agreementTextEndIdName = "";
			}
			builder.setAgreementTextEnd(ResHelper.getStringRes(FlyVerify.getContext(),agreementTextEndIdName));
		}

		if (options.containsKey("agreementTextEndString")) {
			String agreementTextEndString = (String) options.get("agreementTextEndString");
			if (TextUtils.isEmpty(agreementTextEndString)) {
				agreementTextEndString = "";
			}
			builder.setAgreementTextEnd(agreementTextEndString);
		}
		int privacyOffsetX = -1;
		int privacyOffsetRightX = -1;
		int privacyOffsetY = -1;
		int privacyOffsetBottomY = -1;
		if (options.containsKey("agreementOffsetY")) {
			privacyOffsetY = Math.abs((int) options.get("agreementOffsetY"));
		}
		if (options.containsKey("agreementOffsetX")) {
			privacyOffsetX = Math.abs((int) options.get("agreementOffsetX"));
		}
		if (options.containsKey("agreementOffsetBottomY")) {
			privacyOffsetBottomY = Math.abs((int) options.get("agreementOffsetBottomY"));
		}
		if (options.containsKey("agreementOffsetRightX")) {
			privacyOffsetRightX = Math.abs((int) options.get("agreementOffsetRightX"));
		}

		if (privacyOffsetX > 0) {
			builder.setAgreementOffsetX(privacyOffsetX);
		}
		if (privacyOffsetY > 0) {
			builder.setAgreementOffsetY(privacyOffsetY);
		}
		if (privacyOffsetBottomY > 0) {
			builder.setAgreementOffsetBottomY(privacyOffsetBottomY);
		}
		if (privacyOffsetRightX > 0){
			builder.setAgreementOffsetRightX(privacyOffsetRightX);
		}

		if (options.containsKey("agreementUncheckHintText")) {
			String privacyPromptText = (String) options.get("agreementUncheckHintText");
			if (!TextUtils.isEmpty(privacyPromptText)) {
				builder.setAgreementUncheckHintText(ResHelper.getStringRes(FlyVerify.getContext(),privacyPromptText));
			}
		}
		if (options.containsKey("agreementTextWithUnderLine")) {
			boolean agreementTextWithUnderLine = (boolean) options.get("agreementTextWithUnderLine");
			builder.setAgreementTextWithUnderLine(agreementTextWithUnderLine);
		}
		if (options.containsKey("agreementTextBold")) {
			boolean agreementTextBold = (boolean) options.get("agreementTextBold");
			builder.setAgreementTextBold(agreementTextBold);
		}
		if (options.containsKey("agreementAlignParentRight")) {
			boolean agreementAlignParentRight = (boolean) options.get("agreementAlignParentRight");
			builder.setAgreementAlignParentRight(agreementAlignParentRight);
		}

		if (options.containsKey("agreementUncheckHintType")) {
			int agreementUncheckHintType = (int) options.get("agreementUncheckHintType");
			builder.setAgreementUncheckHintType(agreementUncheckHintType);
		}
		//暂不添加
		boolean privacyGravityLeft = true;

//		if (options.containsKey("customPrivacyContentPage")) {
//			HashMap privacyPage = (HashMap) options.get("customPrivacyContentPage");
//			setPrivacyPageBuilder(builder, privacyPage);
//		}
		if (options.containsKey("agreementPageTitleString")) {
			String navText = (String) options.get("agreementPageTitleString");
			if (!TextUtils.isEmpty(navText)) {
				builder.setAgreementPageTitle(navText);
			}
		}
		if (options.containsKey("agreementPageTitleStringId")) {
			String navText = (String) options.get("agreementPageTitleStringId");
			if (!TextUtils.isEmpty(navText)) {
				builder.setAgreementPageTitle(ResHelper.getStringRes(FlyVerify.getContext(),navText));
			}
		}
		if (options.containsKey("cusAgreementPageOneTitleString")) {
			String cusAgreementPageOneTitle = (String) options.get("cusAgreementPageOneTitleString");
			if (!TextUtils.isEmpty(cusAgreementPageOneTitle)) {
				builder.setCusAgreementPageOneTitle(cusAgreementPageOneTitle);
			}
		}
		if (options.containsKey("cusAgreementPageOneTitleNameId")) {
			String cusAgreementPageOneTitleNameId = (String) options.get("cusAgreementPageOneTitleNameId");
			if (!TextUtils.isEmpty(cusAgreementPageOneTitleNameId)) {
				System.out.println("来自flutter: "+cusAgreementPageOneTitleNameId);
				builder.setCusAgreementPageOneTitle(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementPageOneTitleNameId));
			}
		}
		if (options.containsKey("cusAgreementPageTwoTitleString")) {
			String cusAgreementPageTwoTitle = (String) options.get("cusAgreementPageTwoTitleString");
			if (!TextUtils.isEmpty(cusAgreementPageTwoTitle)) {
				builder.setCusAgreementPageTwoTitle(cusAgreementPageTwoTitle);
			}
		}

		if (options.containsKey("cusAgreementPageTwoTitleNameId")) {
			String cusAgreementPageOneTitleNameId = (String) options.get("cusAgreementPageOneTitleNameId");
			if (!TextUtils.isEmpty(cusAgreementPageOneTitleNameId)) {
				builder.setCusAgreementPageTwoTitle(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementPageOneTitleNameId));
			}
		}

		if (options.containsKey("cusAgreementPageThreeTitleString")) {
			String cusAgreementPageThreeTitle = (String) options.get("cusAgreementPageThreeTitleString");
			if (!TextUtils.isEmpty(cusAgreementPageThreeTitle)) {
				builder.setCusAgreementPageThreeTitle(cusAgreementPageThreeTitle);
			}
		}

		if (options.containsKey("cusAgreementPageThreeTitleNameId")) {
			String cusAgreementPageThreeTitle = (String) options.get("cusAgreementPageThreeTitleNameId");
			if (!TextUtils.isEmpty(cusAgreementPageThreeTitle)) {
				builder.setCusAgreementPageThreeTitle(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementPageThreeTitle));
			}
		}

		if (options.containsKey("agreementPageCloseImgHidden")) {
			boolean agreementPageCloseImgHidden = (boolean) options.get("agreementPageCloseImgHidden");
			builder.setAgreementPageCloseImgHidden(agreementPageCloseImgHidden);
		}
		if (options.containsKey("agreementPageTitleHidden")) {
			boolean agreementPageTitleHidden = (boolean) options.get("agreementPageTitleHidden");
			builder.setAgreementPageTitleHidden(agreementPageTitleHidden);
		}
		if (options.containsKey("agreementPageTitleTextBold")) {
			boolean agreementPageTitleTextBold = (boolean) options.get("agreementPageTitleTextBold");
			builder.setAgreementPageTitleTextBold(agreementPageTitleTextBold);
		}
		if (options.containsKey("agreementPageTitleTextColor")) {
			String agreementPageTitleTextColor = (String) options.get("agreementPageTitleTextColor");
			builder.setAgreementPageTitleTextColor(Color.parseColor(agreementPageTitleTextColor));
		}
		if (options.containsKey("agreementPageTitleTextSize")) {
			int agreementPageTitleTextSize = (int) options.get("agreementPageTitleTextSize");
			builder.setAgreementPageTitleTextSize(agreementPageTitleTextSize);
		}

//				"agreementPageCloseImg": "close.png",//隐私协议页面 返回图标名称
//				"agreementPageCloseImgWidth": 50,//隐私协议页面 返回图标宽度
//				"agreementPageCloseImgHeight": 50,//隐私协议页面 返回图标高度
		/**导航栏图片*/
//		int navReturnImgDrawableWidth = 0;
//		int navReturnImgDrawableHeight = 0;

		if (options.containsKey("agreementPageCloseImgWidth")) {
			navReturnImgDrawableWidth = (int) options.get("agreementPageCloseImgWidth");
		}
		if (options.containsKey("agreementPageCloseImgHeight")) {
			navReturnImgDrawableHeight = (int) options.get("agreementPageCloseImgHeight");
		}

		if (navReturnImgDrawableWidth <= 0) {
			navReturnImgDrawableWidth = 30;
		}

		if (navReturnImgDrawableHeight <= 0) {
			navReturnImgDrawableHeight = 30;
		}

		builder.setAgreementPageCloseImgWidth(navReturnImgDrawableWidth);
		builder.setAgreementPageCloseImgHeight(navReturnImgDrawableHeight);


		if (options.containsKey("agreementPageCloseImg")) {
			String navReturnImgDrawablePath = (String) options.get("agreementPageCloseImg"); //assets里，转成drawable
			if (!TextUtils.isEmpty(navReturnImgDrawablePath)) {
				try {
					navReturnImgDrawablePath =  "flutter_assets" + File.separator + "assets" + File.separator +  navReturnImgDrawablePath;
					InputStream is = FlyVerify.getContext().getAssets().open(navReturnImgDrawablePath);
					Drawable navReturnImageDrawable = createBitmap(is, dipToPx(FlyVerify.getContext(), navReturnImgDrawableWidth), dipToPx(FlyVerify.getContext(), navReturnImgDrawableHeight));
					builder.setAgreementPageCloseImg(navReturnImageDrawable);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
//		if (options.containsKey("customLoginBtn")) {
//			HashMap loginBtn = (HashMap) options.get("customLoginBtn");
//			setLoginBtnBuilder(builder, loginBtn);
//		}
		int loginBtnWidth = -1;
		int loginBtnHeight = -1;
		int loginBtnOffsetX = -1;
		int loginBtnOffsetY = -1;
		int loginBtnOffsetBottomY = -1;
		int loginBtnOffsetRightX = -1;
		if (options.containsKey("loginBtnWidth")) {
			loginBtnWidth = (int) options.get("loginBtnWidth");
		}
		if (options.containsKey("loginBtnHeight")) {
			loginBtnHeight = (int) options.get("loginBtnHeight");
		}
		if (options.containsKey("loginBtnOffsetY")) {
			loginBtnOffsetY = Math.abs((int) options.get("loginBtnOffsetY"));
		}
		if (options.containsKey("loginBtnOffsetX")) {
			loginBtnOffsetX = Math.abs((int) options.get("loginBtnOffsetX"));
		}
		if (options.containsKey("loginBtnOffsetBottomY")) {
			loginBtnOffsetBottomY = Math.abs((int) options.get("loginBtnOffsetBottomY"));
		}
		if (options.containsKey("loginBtnOffsetRightX")) {
			loginBtnOffsetRightX = Math.abs((int) options.get("loginBtnOffsetRightX"));
		}
		//如果没有设置，那么设置一个默认值用于设置图片生成bitmap的宽度
		if (loginBtnWidth > 0) {
			builder.setLoginBtnWidth(loginBtnWidth);
		} else {
//			全屏宽度-60dp
			DisplayMetrics metrics = FlyVerify.getContext().getResources().getDisplayMetrics();
			int screenWidth = metrics.widthPixels;
			loginBtnWidth = pxToDip(FlyVerify.getContext(), screenWidth - dipToPx(FlyVerify.getContext(), 60));
		}
		////如果没有设置，那么设置一个默认值用于设置图片的高度
		if (loginBtnHeight > 0) {
			builder.setLoginBtnHeight(loginBtnHeight);
		} else {
			loginBtnHeight = 45;
		}

		if (loginBtnOffsetX > 0) {
			builder.setLoginBtnOffsetX(loginBtnOffsetX);
		}
		if (loginBtnOffsetY > 0) {
			builder.setLoginBtnOffsetY(loginBtnOffsetY);
		}
		if (loginBtnOffsetBottomY > 0) {
			builder.setLoginBtnOffsetBottomY(loginBtnOffsetBottomY);
		}
		if (loginBtnOffsetRightX > 0){
			builder.setLoginBtnOffsetRightX(loginBtnOffsetRightX);
		}


		if (options.containsKey("loginImgNormalName") && options.containsKey("loginImgPressedName") ){
			String loginImgNormal = (String) options.get("loginImgNormalName"); //assets里，转成drawable
			String loginImgPressed = (String) options.get("loginImgPressedName"); //assets里，转成drawable

			StateListDrawable drawable = new StateListDrawable();
			if (loginImgPressed.startsWith("#")) {
				drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.parseColor(loginImgPressed)));
			} else {
				try {
					loginImgPressed = "flutter_assets" + File.separator + "assets" + File.separator+ loginImgPressed;
					InputStream is = FlyVerify.getContext().getAssets().open(loginImgPressed);
					Drawable drawableStatePressed = createBitmap(is, dipToPx(FlyVerify.getContext(), loginBtnWidth), dipToPx(FlyVerify.getContext(), loginBtnHeight));
					drawable.addState(new int[]{android.R.attr.state_pressed}, drawableStatePressed);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (loginImgNormal.startsWith("#")) {
				drawable.addState(new int[]{}, new ColorDrawable(Color.parseColor(loginImgNormal)));
			} else {
				try {
					loginImgNormal = "flutter_assets" + File.separator + "assets" + File.separator+ loginImgNormal;
					InputStream is = FlyVerify.getContext().getAssets().open(loginImgNormal);
					Drawable drawableStateUnPressed = createBitmap(is, dipToPx(FlyVerify.getContext(), loginBtnWidth), dipToPx(FlyVerify.getContext(), loginBtnHeight));
					drawable.addState(new int[]{}, drawableStateUnPressed);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			builder.setLoginBtnImgId(drawable);

		}

		if (options.containsKey("loginBtnImgIdName")){
			String loginBtnImgName = (String) options.get("loginBtnImgIdName");
			if (!TextUtils.isEmpty(loginBtnImgName)){
				int bgId = ResHelper.getResId(FlyVerify.getContext(),"drawable",loginBtnImgName);
				builder.setLoginBtnImgId(bgId);
			}
		}


		if (options.containsKey("loginBtnTextIdName")) {
			String loginBtnText = (String) options.get("loginBtnTextIdName");
			if (!TextUtils.isEmpty(loginBtnText)) {
				int textId = ResHelper.getResId(FlyVerify.getContext(),"string",loginBtnText);
				builder.setLoginBtnTextId(textId);
			}
		}

		if (options.containsKey("loginBtnTextStringName")) {
			String loginBtnText = (String) options.get("loginBtnTextStringName");
			if (!TextUtils.isEmpty(loginBtnText)) {
				builder.setLoginBtnTextId(loginBtnText);
			}
		}


		if (options.containsKey("loginBtnTextColorIdName")) {
			String loginBtnTextColor = (String) options.get("loginBtnTextColorIdName");
			if (!TextUtils.isEmpty(loginBtnTextColor)) {
				int loginColorId = ResHelper.getResId(FlyVerify.getContext(),"color",loginBtnTextColor);
				builder.setLoginBtnTextColorId(loginColorId);
			}
		}
		if (options.containsKey("loginBtnTextSize")) {
			int loginBtnTextSize = (int) options.get("loginBtnTextSize");
			if (loginBtnTextSize > 0) {
				builder.setLoginBtnTextSize(loginBtnTextSize);
			}
		}
		if (options.containsKey("loginBtnHidden")) {
			boolean loginBtnHidden = (boolean) options.get("loginBtnHidden");
			builder.setLoginBtnHidden(loginBtnHidden);
		}
		if (options.containsKey("loginBtnTextBold")) {
			boolean loginBtnTextBold = (boolean) options.get("loginBtnTextBold");
			builder.setLoginBtnTextBold(loginBtnTextBold);
		}
		if (options.containsKey("loginBtnAlignParentRight")) {
			boolean loginBtnAlignParentRight = (boolean) options.get("loginBtnAlignParentRight");
			builder.setLoginBtnAlignParentRight(loginBtnAlignParentRight);
		}
//		if (options.containsKey("customOperatorSlogan")) {
//			HashMap slogan = (HashMap) options.get("customOperatorSlogan");
//			setSloganBuilder(builder, slogan);
//		}
		if (options.containsKey("sloganTextColor")) {
			String sloganTextColor = (String) options.get("sloganTextColor");
			if (!TextUtils.isEmpty(sloganTextColor)) {
				int sloganColor = ResHelper.getColorRes(FlyVerify.getContext(),sloganTextColor);
				builder.setSloganTextColor(sloganColor);
			}
		}
		if (options.containsKey("sloganTextSize")) {
			int sloganTextSize = (int) options.get("sloganTextSize");
			if (sloganTextSize > 0) {
				builder.setSloganTextSize(sloganTextSize);
			}
		}
		if (options.containsKey("sloganHidden")) {
			boolean sloganHidden = (boolean) options.get("sloganHidden");
			builder.setSloganHidden(sloganHidden);
		}
		if (options.containsKey("sloganTextBold")) {
			boolean sloganTextBold = (boolean) options.get("sloganTextBold");
			builder.setSloganTextBold(sloganTextBold);
		}
		if (options.containsKey("sloganAlignParentRight")) {
			boolean sloganAlignParentRightNumber = (boolean) options.get("sloganAlignParentRight");
			builder.setSloganAlignParentRight(sloganAlignParentRightNumber);
		}
		int sloganOffsetX = -1;
		int sloganOffsetY = -1;
		int sloganOffsetBottomY = -1;
		int sloganOffsetRightX = -1;
		if (options.containsKey("sloganOffsetY")) {
			sloganOffsetY = Math.abs((int) options.get("sloganOffsetY"));
		}
		if (options.containsKey("sloganOffsetX")) {
			sloganOffsetX = Math.abs((int) options.get("sloganOffsetX"));
		}
		if (options.containsKey("sloganOffsetBottomY")) {
			sloganOffsetBottomY = Math.abs((int) options.get("sloganOffsetBottomY"));
		}
		if (options.containsKey("sloganOffsetRightX")) {
			sloganOffsetRightX = Math.abs((int) options.get("sloganOffsetRightX"));
		}
		if (sloganOffsetX > 0) {
			builder.setSloganOffsetX(sloganOffsetX);
		}
		if (sloganOffsetY > 0) {
			builder.setSloganOffsetY(sloganOffsetY);
		}
		if (sloganOffsetBottomY > 0) {
			builder.setSloganOffsetBottomY(sloganOffsetBottomY);
		}
		if (sloganOffsetRightX > 0){
			builder.setSloganOffsetRightX(sloganOffsetRightX);
		}
//		if (options.containsKey("customDialog")) {
//			HashMap dialog = (HashMap) options.get("customDialog");
//			setDialogBuilder(builder, dialog);
//		}
		if (options.containsKey("dialogTheme")) {
			boolean dialogTheme = (boolean) options.get("dialogTheme");
			builder.setDialogTheme(dialogTheme);
		}
		if (options.containsKey("dialogAlignBottom")) {
			boolean dialogAlignBottom = (boolean) options.get("dialogAlignBottom");
			builder.setDialogAlignBottom(dialogAlignBottom);
		}
		if (options.containsKey("dialogBackgroundClickClose")) {
			boolean dialogBackgroundClickReturn = (boolean) options.get("dialogBackgroundClickClose");
			builder.setDialogMaskBackgroundClickClose(dialogBackgroundClickReturn);
		}
		int dialogOffsetX = -1;
		int dialogOffsetY = -1;
		int dialogWidth = -1;
		int dialogHeight = -1;

		if (options.containsKey("dialogOffsetX")) {
			dialogOffsetX = (int) options.get("dialogOffsetX");
		}
		if (options.containsKey("dialogOffsetY")) {
			dialogOffsetY = (int) options.get("dialogOffsetY");
		}
		if (options.containsKey("dialogWidth")) {
			dialogWidth = (int) options.get("dialogWidth");
		}
		if (options.containsKey("dialogHeight")) {
			dialogHeight = (int) options.get("dialogHeight");
		}

		if (dialogWidth > 0) {
			builder.setDialogWidth(dialogWidth);
		} else {
			dialogWidth = 250;
		}
		if (dialogHeight > 0) {
			builder.setDialogHeight(dialogHeight);
		} else {
			dialogHeight = 400;
		}
		if (dialogOffsetX > 0) {
			builder.setDialogOffsetX(dialogOffsetX);
		}
		if (dialogOffsetY > 0) {
			builder.setDialogOffsetY(dialogOffsetY);
		}
		if (options.containsKey("dialogBackground")) {
			String dialogBackgroundPath = (String) options.get("dialogBackground");
			if (!TextUtils.isEmpty(dialogBackgroundPath)) {
				dialogBackgroundPath =  "flutter_assets" + File.separator + "assets" + File.separator + dialogBackgroundPath;
				try {
					InputStream is = FlyVerify.getContext().getAssets().open(dialogBackgroundPath);
					Drawable logoImgDrawable = createBitmap(is, dipToPx(FlyVerify.getContext(), dialogWidth), dipToPx(FlyVerify.getContext(), dialogHeight));
					builder.setDialogMaskBackground(logoImgDrawable);
				} catch (IOException e) {
				}
			}
		}
		if (options.containsKey("customView")) {
			HashMap customView = (HashMap) options.get("customView");
			setCustomView(customView);
		}

		return builder;
	}

	
	/**
	 * 注意当同时设置了横屏和竖屏的属性时，会导致view被横屏的UI属性覆盖
	 * @param customView
	 */
	private static void setCustomView(HashMap customView) {

		if (customView == null) {
			return;
		}
		if (!customView.containsKey("customView")) {
			return;
		}
		List<View> views = new ArrayList<>();
		ArrayList FlyVerifyCustomViews = (ArrayList) customView.get("customView");
		if (FlyVerifyCustomViews.size() <= 0) {
			return;
		}

		for (int i = 0; i< FlyVerifyCustomViews.size(); i++) {
			HashMap customedView = (HashMap) FlyVerifyCustomViews.get(i);
			if (customedView != null) {
				View view = setViewBuilder(customedView);
				if (view != null) {
					views.add(view);
				}
			}
		}

		if (!views.isEmpty()) {
			CustomUIRegister.addCustomizedUi(views, new CustomViewClickListener() {
				@Override
				public void onClick(View view) {
				}
			});
		}
	}

	private static View setViewBuilder(HashMap customView) {
		TextView textView = null;
		ImageView imageView = null;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				WRAP_CONTENT, WRAP_CONTENT);
		if (customView.containsKey("viewClass")) {
			String ViewClass = (String) customView.get("viewClass");
			if (!TextUtils.isEmpty(ViewClass)) {
				if ("TextView".equalsIgnoreCase(ViewClass)) {
					textView = new TextView(FlyVerify.getContext());
					if (customView.containsKey("viewText")) {
						String viewText = (String) customView.get("viewText");
						if (!TextUtils.isEmpty(viewText)) {
							textView.setText(viewText);
						}
					}
					if (customView.containsKey("viewTextColor")) {
						String viewTextColor = (String) customView.get("viewTextColor");
						if (!TextUtils.isEmpty(viewTextColor)) {
							int viewTextColorId = ResHelper.getColorRes(FlyVerify.getContext(),viewTextColor);
							textView.setTextColor(viewTextColorId);
						}
					}
					if (customView.containsKey("viewTextFont")) {
						int viewTextFont = (int) customView.get("viewTextFont");
						if (viewTextFont > 0) {
							textView.setTextSize(viewTextFont);
						}
					}
					if (customView.containsKey("viewTextBold")) {
						boolean viewTextBold = (boolean) customView.get("viewTextBold");
						if (viewTextBold) {
							textView.setTypeface(Typeface.DEFAULT_BOLD);
						}
					}
					if (customView.containsKey("viewAlignParentRight")) {
						boolean viewAlignParentRight = (boolean) customView.get("viewAlignParentRight");
						if (viewAlignParentRight) {
							params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						}
					}
					if (customView.containsKey("viewHorizontalCenter")) {
						boolean viewHorizontalCenter = (boolean) customView.get("viewHorizontalCenter");
						if (viewHorizontalCenter) {
							params.addRule(RelativeLayout.CENTER_HORIZONTAL);
						}
					}

					if (customView.containsKey("viewHorizontalCenterVertical")) {
						boolean viewHorizontalCenter = (boolean) customView.get("viewHorizontalCenterVertical");
						if (viewHorizontalCenter) {
							params.addRule(RelativeLayout.CENTER_VERTICAL);
						}
					}
					int viewOffsetX = -1;
					int viewOffsetY = -1;
					int viewOffsetRightX = -1;
					int viewOffsetBottomY = -1;
					int viewWidth = -1;
					int viewHeight = -1;
					if (customView.containsKey("layoutLeft")) {
						viewOffsetX = Math.abs((int) customView.get("viewOffsetX"));
					}
					if (customView.containsKey("layoutTop")) {
						viewOffsetY = Math.abs((int) customView.get("viewOffsetY"));
					}
					if (customView.containsKey("viewOffsetRightX")) {
						viewOffsetRightX = Math.abs((int) customView.get("viewOffsetRightX"));
					}
					if (customView.containsKey("viewOffsetBottomY")) {
						viewOffsetBottomY = Math.abs((int) customView.get("viewOffsetBottomY"));
					}
					if (customView.containsKey("viewWidth")) {
						viewWidth = (int) customView.get("viewWidth");
					}
					if (customView.containsKey("viewHeight")) {
						viewHeight = (int) customView.get("viewHeight");
					}
					if (viewOffsetX > 0) {
						params.leftMargin = dipToPx(FlyVerify.getContext(),viewOffsetX);
					}
					if (viewOffsetY > 0) {
						params.topMargin = dipToPx(FlyVerify.getContext(),viewOffsetY);
					}
					if (viewOffsetRightX > 0) {
						params.rightMargin = dipToPx(FlyVerify.getContext(),viewOffsetRightX);
					}
					if (viewOffsetBottomY > 0) {
						params.bottomMargin = dipToPx(FlyVerify.getContext(),viewOffsetBottomY);
						params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					}
					if (viewWidth > 0) {
						params.width = dipToPx(FlyVerify.getContext(), viewWidth);
					} else {
						params.width = dipToPx(FlyVerify.getContext(), 30);
					}
					if (viewHeight > 0) {
						params.height = dipToPx(FlyVerify.getContext(), viewHeight);
					} else {
						params.height = dipToPx(FlyVerify.getContext(), 30);
					}
					textView.setLayoutParams(params);
				} else if ("ImageView".equalsIgnoreCase(ViewClass)) {
					imageView = new ImageView(FlyVerify.getContext());
					int viewOffsetX = -1;
					int viewOffsetY = -1;
					int viewOffsetRightX = -1;
					int viewOffsetBottomY = -1;
					int viewWidth = -1;
					int viewHeight = -1;
					if (customView.containsKey("viewOffsetX")) {
						viewOffsetX = (int) customView.get("viewOffsetX");
					}
					if (customView.containsKey("viewOffsetY")) {
						viewOffsetY = (int) customView.get("viewOffsetY");
					}
					if (customView.containsKey("viewOffsetRightX")) {
						viewOffsetRightX = (int) customView.get("viewOffsetRightX");
					}
					if (customView.containsKey("viewOffsetBottomY")) {
						viewOffsetBottomY = (int) customView.get("viewOffsetBottomY");
					}
					if (customView.containsKey("viewWidth")) {
						viewWidth = (int) customView.get("viewWidth");
					}
					if (customView.containsKey("viewHeight")) {
						viewHeight = (int) customView.get("viewHeight");
					}

					if (viewOffsetX > 0) {

						params.leftMargin = dipToPx(FlyVerify.getContext(), viewOffsetX);
					}
					if (viewOffsetY > 0) {
						params.topMargin = dipToPx(FlyVerify.getContext(), viewOffsetY);
					}
					if (viewOffsetRightX > 0) {
						params.rightMargin = dipToPx(FlyVerify.getContext(), viewOffsetRightX);
					}
					if (viewOffsetBottomY > 0) {
						params.bottomMargin = dipToPx(FlyVerify.getContext(), viewOffsetBottomY);
						params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					}

					if (viewWidth > 0) {
						params.width = dipToPx(FlyVerify.getContext(), viewWidth);
					} else {
						params.width = dipToPx(FlyVerify.getContext(), 30);
					}
					if (viewHeight > 0) {
						params.height = dipToPx(FlyVerify.getContext(), viewHeight);
					} else {
						params.height = dipToPx(FlyVerify.getContext(), 30);
					}
					if (customView.containsKey("viewImg")) {
						String viewImg = (String) customView.get("viewImg");
						if (!TextUtils.isEmpty(viewImg)) {
							viewImg = "flutter_assets" + File.separator + "assets" + File.separator  + viewImg;
							try {
								InputStream is = FlyVerify.getContext().getAssets().open(viewImg);
								Drawable viewImgDrawable = createBitmap(is, viewWidth, viewHeight);
								imageView.setImageDrawable(viewImgDrawable);
							} catch (IOException e) {
							}
						}
					}
					if (customView.containsKey("viewAlignParentRight")) {
						boolean viewAlignParentRight = (boolean) customView.get("viewAlignParentRight");
						if (viewAlignParentRight) {
							params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						}
					}
					if (customView.containsKey("viewHorizontalCenter")) {
						boolean viewHorizontalCenter = (boolean) customView.get("viewHorizontalCenter");
						if (viewHorizontalCenter) {
							params.addRule(RelativeLayout.CENTER_HORIZONTAL);
						}
					}
					imageView.setLayoutParams(params);
				}
			}
		}
		if (textView != null) {
			return textView;
		} else return imageView;

	}
}
