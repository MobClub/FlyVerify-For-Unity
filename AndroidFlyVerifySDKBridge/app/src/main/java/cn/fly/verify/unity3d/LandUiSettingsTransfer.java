package cn.fly.verify.unity3d;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.fly.verify.FlyVerify;
import cn.fly.verify.CustomUIRegister;
import cn.fly.verify.CustomViewClickListener;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.util.ResHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static cn.fly.verify.util.ResHelper.pxToDip;
import static cn.fly.verify.unity3d.CommonUtil.createBitmap;
import static cn.fly.verify.unity3d.CommonUtil.dipToPx;

public class LandUiSettingsTransfer {
	public static final String TAG = "Transfer";


	public static LandUiSettings transferLandUiSettings(HashMap map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		LandUiSettings.Builder builder = (LandUiSettings.Builder) parse(map);
		return builder.build();

	}

	private static LandUiSettings.Builder parse(HashMap options) {
		LandUiSettings.Builder builder = new LandUiSettings.Builder();
		/**导航栏*/
		if (options.containsKey("customNav")) {
			HashMap nav = (HashMap) options.get("customNav");
			setNavBuilder(builder, nav);
			Log.e(TAG, "setNavBuilderDone");
		}
		if (options.containsKey("customAuthPage")) {
			HashMap page = (HashMap) options.get("customAuthPage");
			setPageBuilder(builder, page);
			Log.e(TAG, "setPageBuilderDone");
		}
		if (options.containsKey("customAuthPageLogo")) {
			HashMap logo = (HashMap) options.get("customAuthPageLogo");
			setLogoBuilder(builder, logo);
			Log.e(TAG, "setLogoBuilderDone");
		}
		if (options.containsKey("customPhoneNumber")) {
			HashMap number = (HashMap) options.get("customPhoneNumber");
			setNumberBuilder(builder, number);
			Log.e(TAG, "setNumberBuilderDone");
		}
		if (options.containsKey("customSwitchNumber")) {
			HashMap switchacc = (HashMap) options.get("customSwitchNumber");
			setSwitchAccBuilder(builder, switchacc);
			Log.e(TAG, "setSwitchAccBuilderDone");
		}
		if (options.containsKey("customCheckBox")) {
			HashMap checkbox = (HashMap) options.get("customCheckBox");
			setCheckBoxBuilder(builder, checkbox);
			Log.e(TAG, "setCheckBoxBuilderDone");
		}
		if (options.containsKey("customPrivacy")) {
			HashMap privacy = (HashMap) options.get("customPrivacy");
			setPrivacyBuilder(builder, privacy);
			Log.e(TAG, "setPrivacyBuilderDone");
		}
		if (options.containsKey("customPrivacyContentPage")) {
			HashMap privacyPage = (HashMap) options.get("customPrivacyContentPage");
			setPrivacyPageBuilder(builder, privacyPage);
			Log.e(TAG, "customPrivacyContentPage");
		}
		if (options.containsKey("customLoginBtn")) {
			HashMap loginBtn = (HashMap) options.get("customLoginBtn");
			setLoginBtnBuilder(builder, loginBtn);
			Log.e(TAG, "setLoginBtnBuilderDone");
		}
		if (options.containsKey("customOperatorSlogan")) {
			HashMap slogan = (HashMap) options.get("customOperatorSlogan");
			setSloganBuilder(builder, slogan);
			Log.e(TAG, "setSloganBuilderDone");
		}
		if (options.containsKey("customDialog")) {
			HashMap dialog = (HashMap) options.get("customDialog");
			setDialogBuilder(builder, dialog);
			Log.e(TAG, "setDialogBuilderDone");
		}
		if (options.containsKey("customView")) {
			HashMap customView = (HashMap) options.get("customView");
			setCustomView(customView);
			Log.e(TAG, "setCustomViewDone");
		}

		return builder;
	}

	private static LandUiSettings.Builder setNavBuilder(LandUiSettings.Builder builder, HashMap nav) {
		if (nav == null){
			return builder;
		}
		if (nav.containsKey("navTransparent")) {
			boolean navTransparent = (boolean) nav.get("navTransparent");
			builder.setNavTransparent(navTransparent);
		}
		if (nav.containsKey("navHidden")) {
			boolean navHidden = (boolean) nav.get("navHidden");
			builder.setNavHidden(navHidden);
		}
		if (nav.containsKey("navColorIdName")) {
			String navColor = (String) nav.get("navColorIdName");
			if (!TextUtils.isEmpty(navColor)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),navColor);
				builder.setNavColorId(colorId);
			}
		}
		if (nav.containsKey("navTextSize")) {
			/**导航栏文字*/
			int navTextSize = (int) nav.get("navTextSize");
			if (navTextSize > 0) {
				builder.setNavTextSize(navTextSize);
			}
		}

		if (nav.containsKey("navTextColorIdName")) {
			String navTextColor = (String) nav.get("navTextColorIdName");
			if (!TextUtils.isEmpty(navTextColor)) {
				int colorId = ResHelper.getColorRes(FlyVerify.getContext(),navTextColor);
				builder.setNavTextColorId(colorId);
			}
		}

		/**导航栏图片*/
		int navReturnImgDrawableWidth = 0;
		int navReturnImgDrawableHeight = 0;
		int navReturnImgDrawableOffsetX = -1;
		int navReturnImgDrawableOffsetRightX = -1;
		int navReturnImgDrawableOffsetY = -1;
		if (nav.containsKey("navCloseImgWidth")) {
			navReturnImgDrawableWidth = (int) nav.get("navCloseImgWidth");
		}
		if (nav.containsKey("navCloseImgHeight")) {
			navReturnImgDrawableHeight = (int) nav.get("navCloseImgHeight");
		}
		if (nav.containsKey("navCloseImgOffsetY")) {
			navReturnImgDrawableOffsetY = Math.abs((int) nav.get("navCloseImgOffsetY"));
		}
		if (nav.containsKey("navCloseImgOffsetX")) {
			navReturnImgDrawableOffsetX = Math.abs((int) nav.get("navCloseImgOffsetX"));
		}
		if (nav.containsKey("navCloseImgOffsetRightX")) {
			navReturnImgDrawableOffsetRightX = Math.abs((int) nav.get("navCloseImgOffsetRightX"));
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
		if (nav.containsKey("navCloseImgPath")) {
			String navReturnImgDrawablePath = (String) nav.get("navCloseImgPath"); //assets里，转成drawable
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

		if (nav.containsKey("navCloseImgHidden")) {
			boolean navReturnImgDrawableHidden = (boolean) nav.get("navCloseImgHidden");
			builder.setNavCloseImgHidden(navReturnImgDrawableHidden);
		}

		if (nav.containsKey("navTextIdName")) {
			String navText = (String) nav.get("navTextIdName");
			int navTextId = ResHelper.getStringRes(FlyVerify.getContext(),navText);
			builder.setNavTextId(navTextId);
		}

		if (nav.containsKey("navCloseImgScaleType")){
			String navCloseImgScaleType = (String) nav.get("navCloseImgScaleType");
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
		return builder;
	}

	private static LandUiSettings.Builder setPageBuilder(LandUiSettings.Builder builder, HashMap page) {
		if (page == null) {
			return builder;
		}
		if (page.containsKey("backgroundImgPath")) {
			String backgroundImgDrawablepath = (String) page.get("backgroundImgPath"); //assets里，转成drawable
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
					Log.e(TAG, e.getMessage(), e);
				}
			}
		}

		if (page.containsKey("backgroundClickClose")) {
			boolean backgroundClickClose = (boolean) page.get("backgroundClickClose");
			builder.setBackgroundClickClose(backgroundClickClose);
		}

		if (page.containsKey("fullScreen")) {
			boolean fullScreen = (boolean) page.get("fullScreen");
			builder.setFullScreen(fullScreen);
		}

		if (page.containsKey("immersiveTheme")) {
			boolean immersiveTheme = (boolean) page.get("immersiveTheme");
			builder.setImmersiveTheme(immersiveTheme);
		}

		if (page.containsKey("immersiveStatusTextColorBlack")) {
			boolean immersiveStatusTextColorBlack = (boolean) page.get("immersiveStatusTextColorBlack");
			builder.setImmersiveStatusTextColorBlack(immersiveStatusTextColorBlack);
		}

		return builder;
	}

	private static LandUiSettings.Builder setLogoBuilder(LandUiSettings.Builder builder, HashMap logo) {
		if(logo == null){
			return builder;
		}
		int logoWidth = -1;
		int logoHeight = -1;
		int logoOffsetX = -1;
		int logoOffsetY = -1;
		int logoOffsetBottomY = -1;
		int logoOffsetRightX = -1;

		if (logo.containsKey("logoWidth")) {
			logoWidth = (int) logo.get("logoWidth");
		}
		if (logo.containsKey("logoHeight")) {
			logoHeight = (int) logo.get("logoHeight");
		}
		if (logo.containsKey("logoOffsetX")) {
			logoOffsetX = Math.abs((int) logo.get("logoOffsetX"));
		}
		if (logo.containsKey("logoOffsetY")) {
			logoOffsetY = Math.abs((int) logo.get("logoOffsetY"));
		}
		if (logo.containsKey("logoOffsetBottomY")) {
			logoOffsetBottomY = Math.abs((int) logo.get("logoOffsetBottomY"));
		}
		if (logo.containsKey("logoOffsetRightX")) {
			logoOffsetRightX = Math.abs((int) logo.get("logoOffsetRightX"));
		}
		if (logoWidth <= 0) {
			logoWidth = 80;
		}
		if (logoHeight <= 0) {
			logoHeight = 80;
		}
		builder.setLogoWidth(logoWidth)
				.setLogoHeight(logoHeight);
		if (logo.containsKey("logoImgPath")) {
			String logoImgDrawablePath = (String) logo.get("logoImgPath"); //assets里，转成drawable
			if (!TextUtils.isEmpty(logoImgDrawablePath)) {
				try {
					logoImgDrawablePath = "flutter_assets" + File.separator + "assets" + File.separator+ logoImgDrawablePath;
					InputStream is = FlyVerify.getContext().getAssets().open(logoImgDrawablePath);
					Drawable logoImgDrawable = createBitmap(is, dipToPx(FlyVerify.getContext(), logoWidth), dipToPx(FlyVerify.getContext(), logoHeight));
					builder.setLogoImgId(logoImgDrawable);
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
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

		if (logo.containsKey("logoHidden")) {
			boolean logoHidden = (boolean) logo.get("logoHidden");
			builder.setLogoHidden(logoHidden);
		}
		if (logo.containsKey("logoAlignParentRight")) {
			boolean logoAlignRight = (boolean) logo.get("logoAlignParentRight");
			builder.setLogoAlignParentRight(logoAlignRight);
		}

		return builder;
	}

	private static LandUiSettings.Builder setNumberBuilder(LandUiSettings.Builder builder, HashMap number) {
		if (number == null) {
			return builder;
		}


		if (number.containsKey("numberColorIdName")) {
			String phoneColor = (String) number.get("numberColorIdName");
			if (!TextUtils.isEmpty(phoneColor)) {
				int phoneColorId = ResHelper.getColorRes(FlyVerify.getContext(),phoneColor);
				builder = builder.setNumberColorId(phoneColorId);
			}
		}
		if (number.containsKey("numberSize")) {
			int phoneSize = (int) number.get("numberSize");
			if (phoneSize > 0) {
				builder = builder.setNumberSizeId(phoneSize);
			}
		}
		if (number.containsKey("numberHidden")) {
			boolean phoneHidden = (boolean) number.get("numberHidden");
			builder = builder.setNumberHidden(phoneHidden);
		}
		int phoneOffsetX = -1;
		int phoneOffsetY = -1;
		int phoneOffsetBottomY = -1;
		int phoneOffsetRightX = -1;
		if (number.containsKey("numberOffsetX")) {
			phoneOffsetX = Math.abs((int) number.get("numberOffsetX"));
		}
		if (number.containsKey("numberOffsetY")) {
			phoneOffsetY = Math.abs((int) number.get("numberOffsetY"));
		}
		if (number.containsKey("numberOffsetBottomY")) {
			phoneOffsetBottomY = Math.abs((int) number.get("numberOffsetBottomY"));
		}
		if (number.containsKey("numberOffsetRightX")) {
			phoneOffsetRightX = Math.abs((int) number.get("numberOffsetRightX"));
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

		if (number.containsKey("numberAlignParentRight")) {
			boolean phoneAlignParentRightNumber = (boolean) number.get("numberAlignParentRight");
			builder = builder.setNumberAlignParentRight(phoneAlignParentRightNumber);
		}
		if (number.containsKey("numberBold")) {
			boolean numberBold = (boolean) number.get("numberBold");
			builder = builder.setNumberBold(numberBold);
		}
		return builder;
	}

	private static LandUiSettings.Builder setSwitchAccBuilder(LandUiSettings.Builder builder, HashMap switchacc) {
		if (switchacc == null) {
			return builder;
		}
		/**切换登录*/
		if (switchacc.containsKey("switchAccColor")) {
			String switchAccColor = (String) switchacc.get("switchAccColor");
			if (!TextUtils.isEmpty(switchAccColor)) {
				int switchColodId = ResHelper.getColorRes(FlyVerify.getContext(),switchAccColor);
				builder.setSwitchAccColorId(switchColodId);
			}
		}
		if (switchacc.containsKey("switchAccHidden")) {
			boolean switchAccHidden = (boolean) switchacc.get("switchAccHidden");
			builder.setSwitchAccHidden(switchAccHidden);
		}
		if (switchacc.containsKey("switchAccTextSize")) {
			int switchAccTextSize = (int) switchacc.get("switchAccTextSize");
			if (switchAccTextSize > 0) {
				builder.setSwitchAccTextSize(switchAccTextSize);
			}
		}
		if (switchacc.containsKey("switchAccText")) {
			String switchAccText = (String) switchacc.get("switchAccText");
			if (!TextUtils.isEmpty(switchAccText)) {
				builder.setSwitchAccText(switchAccText);
			}
		}
		int switchAccOffsetX = -1;
		int switchAccOffsetY = -1;
		int switchAccOffsetBottomY = -1;
		int switchAccOffsetRightX = -1;
		if (switchacc.containsKey("switchAccOffsetX")) {
			switchAccOffsetX = Math.abs((int) switchacc.get("switchAccOffsetX"));
		}
		if (switchacc.containsKey("switchAccOffsetY")) {
			switchAccOffsetY = Math.abs((int) switchacc.get("switchAccOffsetY"));
		}
		if (switchacc.containsKey("switchAccOffsetBottomY")) {
			switchAccOffsetBottomY = Math.abs((int) switchacc.get("switchAccOffsetBottomY"));
		}
		if (switchacc.containsKey("switchAccOffsetRightX")) {
			switchAccOffsetRightX = Math.abs((int) switchacc.get("switchAccOffsetRightX"));
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

		if (switchacc.containsKey("switchAccAlignParentRight")) {
			boolean switchAccAlignParentRight = (boolean) switchacc.get("switchAccAlignParentRight");
			builder.setSwitchAccAlignParentRight(switchAccAlignParentRight);
		}
		if (switchacc.containsKey("switchAccTextBold")) {
			boolean switchAccTextBold = (boolean) switchacc.get("switchAccTextBold");
			builder.setSwitchAccTextBold(switchAccTextBold);
		}
		return builder;
	}

	private static LandUiSettings.Builder setCheckBoxBuilder(LandUiSettings.Builder builder, HashMap checkbox) {
		if (checkbox == null) {
			return builder;
		}
		int checkboxWidth = -1;
		int checkboxHeight = -1;
		int checkboxOffsetX = -1;
		int checkboxOffsetRightX = -1;
		int checkboxOffsetY = -1;
		int checkboxOffsetBottomY = -1;

		if (checkbox.containsKey("checkboxWidth")) {
			checkboxWidth = (int) checkbox.get("checkboxWidth");
		}
		if (checkbox.containsKey("checkboxHeight")) {
			checkboxHeight = (int) checkbox.get("checkboxHeight");
		}
		if (checkbox.containsKey("checkboxOffsetY")) {
			checkboxOffsetY = Math.abs((int) checkbox.get("checkboxOffsetY"));
		}
		if (checkbox.containsKey("checkboxOffsetX")) {
			checkboxOffsetX = Math.abs((int) checkbox.get("checkboxOffsetX"));
		}
		if (checkbox.containsKey("checkboxOffsetBottomY")) {
			checkboxOffsetBottomY = Math.abs((int) checkbox.get("checkboxOffsetBottomY"));
		}
		if (checkbox.containsKey("checkboxOffsetRightX")) {
			checkboxOffsetRightX = Math.abs((int) checkbox.get("checkboxOffsetRightX"));
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
		if (checkbox.containsKey("checkedImgName") && checkbox.containsKey("uncheckedImgName")) {
			String checkboxCheckedImgPath = (String) checkbox.get("checkedImgName"); //assets里，转成drawable
			String checkboxUncheckedImgPath = (String) checkbox.get("uncheckedImgName"); //assets里，转成drawable
			StateListDrawable drawable = new StateListDrawable();

			if (checkboxCheckedImgPath.startsWith("#")) {
				drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.parseColor(checkboxCheckedImgPath)));
			} else {
				try {
					checkboxCheckedImgPath = "flutter_assets" + File.separator + "assets" + File.separator+ checkboxCheckedImgPath;
					InputStream is = FlyVerify.getContext().getAssets().open(checkboxCheckedImgPath);
					Drawable drawableStatePressed = createBitmap(is, dipToPx(FlyVerify.getContext(), checkboxWidth), dipToPx(FlyVerify.getContext(), checkboxHeight));
					drawable.addState(new int[]{android.R.attr.state_pressed}, drawableStatePressed);
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

//		if (checkbox.containsKey("checkedImgName") && checkbox.containsKey("uncheckedImgName")) {
//			String checkboxCheckedImgPath = (String) checkbox.get("checkedImgName"); //assets里，转成drawable
//			String checkboxUncheckedImgPath = (String) checkbox.get("uncheckedImgName"); //assets里，转成drawable
//			if (!TextUtils.isEmpty(checkboxCheckedImgPath) && !TextUtils.isEmpty(checkboxUncheckedImgPath)) {
//				try {
//					StateListDrawable drawable = new StateListDrawable();
//
//					checkboxUncheckedImgPath = "flutter_assets" + File.separator + "assets" + File.separator+ checkboxUncheckedImgPath;
//					InputStream is = FlyVerify.getContext().getAssets().open(checkboxUncheckedImgPath);
//					Drawable checkboxUncheckedImg = createBitmap(is, dipToPx(FlyVerify.getContext(), checkboxWidth), dipToPx(FlyVerify.getContext(), checkboxHeight));
//					drawable.addState(new int[]{-android.R.attr.state_checked}, checkboxUncheckedImg);
//
//					checkboxCheckedImgPath = "flutter_assets" + File.separator + "assets" + File.separator+ checkboxCheckedImgPath;
//					InputStream is1 = FlyVerify.getContext().getAssets().open(checkboxCheckedImgPath);
//					Drawable checkboxCheckedImg = createBitmap(is1, dipToPx(FlyVerify.getContext(), checkboxWidth), dipToPx(FlyVerify.getContext(), checkboxHeight));
//					drawable.addState(new int[]{android.R.attr.state_checked}, checkboxCheckedImg);
//					builder.setCheckboxImgId(drawable);
//				} catch (IOException e) {
//					Log.e(TAG, e.getMessage(), e);
//				}
//			}
//		}

		if (checkbox.containsKey("checkboxImgIdName")) {
			String checkboxImg = (String) checkbox.get("checkboxImgIdName");
			if (!TextUtils.isEmpty(checkboxImg)){
				checkboxImg = "flutter_assets" + File.separator + "assets" + File.separator + checkboxImg;
				try {
					InputStream is = FlyVerify.getContext().getAssets().open(checkboxImg);
					Drawable checkBoxImg = createBitmap(is, dipToPx(FlyVerify.getContext(), checkboxWidth), dipToPx(FlyVerify.getContext(), checkboxHeight));
					builder.setCheckboxImgId(checkBoxImg);
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
		}

		if (checkbox.containsKey("checkboxDefaultState")) {
			boolean checkboxDefaultState = (boolean) checkbox.get("checkboxDefaultState");
			builder.setCheckboxDefaultState(checkboxDefaultState);
		}
		if (checkbox.containsKey("checkboxHidden")) {
			boolean checkboxHidden = (boolean) checkbox.get("checkboxHidden");
			builder.setCheckboxHidden(checkboxHidden);
		}
		if (checkbox.containsKey("checkboxScaleX")) {
			double scaleDoubleX = (double) checkbox.get("checkboxScaleX");
			float scaleFloatX = (float) scaleDoubleX;
			builder.setCheckboxScaleX(scaleFloatX);
		}
		if (checkbox.containsKey("checkboxScaleY")) {
			double scaleDoubleY = (double) checkbox.get("checkboxScaleY");
			float scaleFloatY = (float) scaleDoubleY;
			builder.setCheckboxScaleX(scaleFloatY);
		}
		return builder;
	}

	private static LandUiSettings.Builder setPrivacyBuilder(LandUiSettings.Builder builder, HashMap privacy) {
		if (privacy == null) {
			return builder;
		}
		/**隐私协议*/
		if (privacy.containsKey("agreementBaseTextColor")) {
			String privacyBaseTextColor = (String) privacy.get("agreementBaseTextColor");
			if (!TextUtils.isEmpty(privacyBaseTextColor)) {
				builder.setAgreementBaseTextColorId(Color.parseColor(privacyBaseTextColor));
			}
		}
		if (privacy.containsKey("agreementColor")) {
			String privacyColor = (String) privacy.get("agreementColor");
			if (!TextUtils.isEmpty(privacyColor)) {
				builder.setAgreementColorId(Color.parseColor(privacyColor));
			} else {
				builder.setAgreementColorId(Color.parseColor("#00B6B5"));
			}
		}
		if (privacy.containsKey("cusAgreementColor1")) {
			String privacyColor1 = (String) privacy.get("cusAgreementColor1");
			if (!TextUtils.isEmpty(privacyColor1)) {
				builder.setCusAgreementColor1(Color.parseColor(privacyColor1));
			} else {
				builder.setCusAgreementColor1(Color.parseColor("#00B6B5"));
			}
		}
		if (privacy.containsKey("cusAgreementColor2")) {
			String privacyColor2 = (String) privacy.get("cusAgreementColor2");
			if (!TextUtils.isEmpty(privacyColor2)) {
				builder.setCusAgreementColor2(Color.parseColor(privacyColor2));
			} else {
				builder.setCusAgreementColor2(Color.parseColor("#00B6B5"));
			}
		}
		if (privacy.containsKey("cusAgreementColor3")) {
			String privacyColor3 = (String) privacy.get("cusAgreementColor2");
			if (!TextUtils.isEmpty(privacyColor3)) {
				builder.setCusAgreementColor3(Color.parseColor(privacyColor3));
			} else {
				builder.setCusAgreementColor3(Color.parseColor("#00B6B5"));
			}
		}
		if (privacy.containsKey("agreementTextSize")) {
			int privacyTextSize = (int) privacy.get("agreementTextSize");
			if (privacyTextSize > 0) {
				builder.setAgreementTextSize(privacyTextSize);
			}
		}
		if (privacy.containsKey("agreementHidden")) {
			boolean privacyHidden = (boolean) privacy.get("agreementHidden");
			builder.setAgreementHidden(privacyHidden);
		}

		if (privacy.containsKey("agreementCmccTextString")) {
			String agreementCmccTextString = (String) privacy.get("agreementCmccTextString");
			builder.setAgreementCmccText(ResHelper.getStringRes(FlyVerify.getContext(),agreementCmccTextString));
		}
		if (privacy.containsKey("agreementCuccTextString")) {
			String agreementCuccTextString = (String) privacy.get("agreementCuccTextString");
			builder.setAgreementCmccText(ResHelper.getStringRes(FlyVerify.getContext(),agreementCuccTextString));
		}

		if (privacy.containsKey("agreementCtccTextString")) {
			String agreementCtccTextString = (String) privacy.get("agreementCtccTextString");
			builder.setAgreementCmccText(ResHelper.getStringRes(FlyVerify.getContext(),agreementCtccTextString));
		}

		if (privacy.containsKey("cusAgreementNameId1")) {
			String cusAgreementNameId1 = (String) privacy.get("cusAgreementNameId1");
			builder.setCusAgreementNameId1(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameId1));
		}

		if (privacy.containsKey("cusAgreementNameText1")) {
			String cusAgreementNameText1 = (String) privacy.get("cusAgreementNameText1");
			builder.setCusAgreementNameId1(cusAgreementNameText1);
		}

		if (privacy.containsKey("cusAgreementUrl1")){
			String cusAgreementUrl1 = (String) privacy.get("cusAgreementUrl1");
			builder.setCusAgreementUrl1(cusAgreementUrl1);
		}

		if (privacy.containsKey("cusAgreementUrl2")){
			String cusAgreementUrl2 = (String) privacy.get("cusAgreementUrl2");
			builder.setCusAgreementUrl2(cusAgreementUrl2);
		}

		if (privacy.containsKey("cusAgreementUrl3")){
			String cusAgreementUrl3 = (String) privacy.get("cusAgreementUrl3");
			builder.setCusAgreementUrl3(cusAgreementUrl3);
		}

		if (privacy.containsKey("agreementTextAndString1")){
			String agreementTextAndString1 = (String) privacy.get("agreementTextAndString1");
			builder.setAgreementTextAnd1(agreementTextAndString1);
		}

		if (privacy.containsKey("cusAgreementNameId2")) {
			String cusAgreementNameId2 = (String) privacy.get("cusAgreementNameId2");
			builder.setCusAgreementNameId2(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameId2));
		}

		if (privacy.containsKey("cusAgreementNameText2")) {
			String cusAgreementNameText2 = (String) privacy.get("cusAgreementNameText2");
			builder.setCusAgreementNameId2(cusAgreementNameText2);
		}

		if (privacy.containsKey("agreementTextAndString2")){
			String agreementTextAndString2 = (String) privacy.get("agreementTextAndString2");
			builder.setAgreementTextAnd2(agreementTextAndString2);
		}

		if (privacy.containsKey("cusAgreementNameId3")) {
			String cusAgreementNameId3 = (String) privacy.get("cusAgreementNameId3");
			builder.setCusAgreementNameId2(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementNameId3));
		}

		if (privacy.containsKey("cusAgreementNameText3")) {
			String cusAgreementNameText3 = (String) privacy.get("cusAgreementNameText3");
			builder.setCusAgreementNameId3(cusAgreementNameText3);
		}

		if (privacy.containsKey("agreementTextAndString3")){
			String agreementTextAndString3 = (String) privacy.get("agreementTextAndString3");
			builder.setAgreementTextAnd3(agreementTextAndString3);
		}

		if (privacy.containsKey("agreementTextStartIdName")) {
			String agreementTextStartIdName = (String) privacy.get("agreementTextStartIdName");
			if (!TextUtils.isEmpty(agreementTextStartIdName)) {
				builder.setAgreementTextStart(ResHelper.getStringRes(FlyVerify.getContext(),agreementTextStartIdName));
			}
		}

		if (privacy.containsKey("agreementTextStartString")) {
			String agreementTextStartString = (String) privacy.get("agreementTextStartString");
			if (!TextUtils.isEmpty(agreementTextStartString)) {
				builder.setAgreementTextStart(agreementTextStartString);
			}
		}

		if (privacy.containsKey("agreementTextEndIdName")) {
			String agreementTextEndIdName = (String) privacy.get("agreementTextEndIdName");
			if (TextUtils.isEmpty(agreementTextEndIdName)) {
				agreementTextEndIdName = "";
			}
			builder.setAgreementTextEnd(ResHelper.getStringRes(FlyVerify.getContext(),agreementTextEndIdName));
		}

		if (privacy.containsKey("agreementTextEndString")) {
			String agreementTextEndString = (String) privacy.get("agreementTextEndString");
			if (TextUtils.isEmpty(agreementTextEndString)) {
				agreementTextEndString = "";
			}
			builder.setAgreementTextEnd(agreementTextEndString);
		}

//		if (privacy.containsKey("FlyVerifyPrivacyProtocolMarkArr")) {
//			JSONArray protocolMark = privacy.getJSONArray("FlyVerifyPrivacyProtocolMarkArr");
//			if (protocolMark != null && protocolMark.size() == 2) {
//				try {
//					String protocolMarkStart = protocolMark.get(0);
//					String protocolMarkEnd = protocolMark.get(1);
//					String privacyCmccText = protocolMarkStart + "中国移动认证服务条款" + protocolMarkEnd;
//					String privacyCuccText = protocolMarkStart + "中国联通认证服务条款" + protocolMarkEnd;
//					String privacyCtccText = protocolMarkStart + "中国电信认证服务条款" + protocolMarkEnd;
//					builder.setPrivacyCmccText(privacyCmccText)
//							.setPrivacyCuccText(privacyCuccText)
//							.setPrivacyCtccText(privacyCtccText);
//				} catch (JSONException jsonException) {
//					Log.e(TAG, jsonException.getMessage(), jsonException);
//				}
//
//			}
//		}
		int privacyOffsetX = -1;
		int privacyOffsetRightX = -1;
		int privacyOffsetY = -1;
		int privacyOffsetBottomY = -1;
		if (privacy.containsKey("agreementOffsetY")) {
			privacyOffsetY = Math.abs((int) privacy.get("agreementOffsetY"));
		}
		if (privacy.containsKey("agreementOffsetX")) {
			privacyOffsetX = Math.abs((int) privacy.get("agreementOffsetX"));
		}
		if (privacy.containsKey("agreementOffsetBottomY")) {
			privacyOffsetBottomY = Math.abs((int) privacy.get("agreementOffsetBottomY"));
		}
		if (privacy.containsKey("agreementOffsetRightX")) {
			privacyOffsetRightX = Math.abs((int) privacy.get("agreementOffsetRightX"));
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

		if (privacy.containsKey("agreementUncheckHintText")) {
			String privacyPromptText = (String) privacy.get("agreementUncheckHintText");
			if (!TextUtils.isEmpty(privacyPromptText)) {
				builder.setAgreementUncheckHintText(ResHelper.getStringRes(FlyVerify.getContext(),privacyPromptText));
			}
		}
		if (privacy.containsKey("agreementTextWithUnderLine")) {
			boolean agreementTextWithUnderLine = (boolean) privacy.get("agreementTextWithUnderLine");
			builder.setAgreementTextWithUnderLine(agreementTextWithUnderLine);
		}
		if (privacy.containsKey("agreementTextBold")) {
			boolean agreementTextBold = (boolean) privacy.get("agreementTextBold");
			builder.setAgreementTextBold(agreementTextBold);
		}
		if (privacy.containsKey("agreementAlignParentRight")) {
			boolean agreementAlignParentRight = (boolean) privacy.get("agreementAlignParentRight");
			builder.setAgreementAlignParentRight(agreementAlignParentRight);
		}
		if (privacy.containsKey("agreementUncheckHintType")) {
			int agreementUncheckHintType = (int) privacy.get("agreementUncheckHintType");
			builder.setAgreementUncheckHintType(agreementUncheckHintType);
		}
		//暂不添加
		boolean privacyGravityLeft = true;
		return builder;
	}

	private static LandUiSettings.Builder setPrivacyPageBuilder(LandUiSettings.Builder builder, HashMap nav) {
		if (nav == null){
			return builder;
		}
		if (nav.containsKey("agreementPageTitleString")) {
			String navText = (String) nav.get("agreementPageTitleString");
			if (!TextUtils.isEmpty(navText)) {
				builder.setAgreementPageTitle(navText);
			}
		}
		if (nav.containsKey("agreementPageTitleStringId")) {
			String navText = (String) nav.get("agreementPageTitleStringId");
			if (!TextUtils.isEmpty(navText)) {
				builder.setAgreementPageTitle(ResHelper.getStringRes(FlyVerify.getContext(),navText));
			}
		}
		if (nav.containsKey("cusAgreementPageOneTitleString")) {
			String cusAgreementPageOneTitle = (String) nav.get("cusAgreementPageOneTitleString");
			if (!TextUtils.isEmpty(cusAgreementPageOneTitle)) {
				builder.setCusAgreementPageOneTitle(cusAgreementPageOneTitle);
			}
		}
		if (nav.containsKey("cusAgreementPageOneTitleNameId")) {
			String cusAgreementPageOneTitleNameId = (String) nav.get("cusAgreementPageOneTitleNameId");
			if (!TextUtils.isEmpty(cusAgreementPageOneTitleNameId)) {
				builder.setCusAgreementPageOneTitle(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementPageOneTitleNameId));
			}
		}
		if (nav.containsKey("cusAgreementPageTwoTitleString")) {
			String cusAgreementPageTwoTitle = (String) nav.get("cusAgreementPageTwoTitleString");
			if (!TextUtils.isEmpty(cusAgreementPageTwoTitle)) {
				builder.setCusAgreementPageTwoTitle(cusAgreementPageTwoTitle);
			}
		}

		if (nav.containsKey("cusAgreementPageTwoTitleNameId")) {
			String cusAgreementPageOneTitleNameId = (String) nav.get("cusAgreementPageOneTitleNameId");
			if (!TextUtils.isEmpty(cusAgreementPageOneTitleNameId)) {
				builder.setCusAgreementPageTwoTitle(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementPageOneTitleNameId));
			}
		}

		if (nav.containsKey("cusAgreementPageThreeTitleString")) {
			String cusAgreementPageThreeTitle = (String) nav.get("cusAgreementPageThreeTitleString");
			if (!TextUtils.isEmpty(cusAgreementPageThreeTitle)) {
				builder.setCusAgreementPageThreeTitle(cusAgreementPageThreeTitle);
			}
		}

		if (nav.containsKey("cusAgreementPageThreeTitleNameId")) {
			String cusAgreementPageThreeTitle = (String) nav.get("cusAgreementPageThreeTitleNameId");
			if (!TextUtils.isEmpty(cusAgreementPageThreeTitle)) {
				builder.setCusAgreementPageThreeTitle(ResHelper.getStringRes(FlyVerify.getContext(),cusAgreementPageThreeTitle));
			}
		}

		if (nav.containsKey("agreementPageCloseImgHidden")) {
			boolean agreementPageCloseImgHidden = (boolean) nav.get("agreementPageCloseImgHidden");
			builder.setAgreementPageCloseImgHidden(agreementPageCloseImgHidden);
		}
		if (nav.containsKey("agreementPageTitleHidden")) {
			boolean agreementPageTitleHidden = (boolean) nav.get("agreementPageTitleHidden");
			builder.setAgreementPageTitleHidden(agreementPageTitleHidden);
		}
		if (nav.containsKey("agreementPageTitleTextBold")) {
			boolean agreementPageTitleTextBold = (boolean) nav.get("agreementPageTitleTextBold");
			builder.setAgreementPageTitleTextBold(agreementPageTitleTextBold);
		}
		if (nav.containsKey("agreementPageTitleTextColor")) {
			String agreementPageTitleTextColor = (String) nav.get("agreementPageTitleTextColor");
			builder.setAgreementPageTitleTextColor(Color.parseColor(agreementPageTitleTextColor));
		}
		if (nav.containsKey("agreementPageTitleTextSize")) {
			int agreementPageTitleTextSize = (int) nav.get("agreementPageTitleTextSize");
			builder.setAgreementPageTitleTextSize(agreementPageTitleTextSize);
		}

//				"agreementPageCloseImg": "close.png",//隐私协议页面 返回图标名称
//				"agreementPageCloseImgWidth": 50,//隐私协议页面 返回图标宽度
//				"agreementPageCloseImgHeight": 50,//隐私协议页面 返回图标高度
		/**导航栏图片*/
		int navReturnImgDrawableWidth = 0;
		int navReturnImgDrawableHeight = 0;

		if (nav.containsKey("agreementPageCloseImgWidth")) {
			navReturnImgDrawableWidth = (int) nav.get("agreementPageCloseImgWidth");
		}
		if (nav.containsKey("agreementPageCloseImgHeight")) {
			navReturnImgDrawableHeight = (int) nav.get("agreementPageCloseImgHeight");
		}

		if (navReturnImgDrawableWidth <= 0) {
			navReturnImgDrawableWidth = 30;
		}

		if (navReturnImgDrawableHeight <= 0) {
			navReturnImgDrawableHeight = 30;
		}

		builder.setAgreementPageCloseImgWidth(navReturnImgDrawableWidth);
		builder.setAgreementPageCloseImgHeight(navReturnImgDrawableHeight);


		if (nav.containsKey("agreementPageCloseImg")) {
			String navReturnImgDrawablePath = (String) nav.get("agreementPageCloseImg"); //assets里，转成drawable
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

		return builder;
	}

	private static LandUiSettings.Builder setLoginBtnBuilder(LandUiSettings.Builder builder, HashMap loginBtn) {
		if (loginBtn == null) {
			return builder;
		}

		int loginBtnWidth = -1;
		int loginBtnHeight = -1;
		int loginBtnOffsetX = -1;
		int loginBtnOffsetY = -1;
		int loginBtnOffsetBottomY = -1;
		int loginBtnOffsetRightX = -1;
		if (loginBtn.containsKey("loginBtnWidth")) {
			loginBtnWidth = (int) loginBtn.get("loginBtnWidth");
		}
		if (loginBtn.containsKey("loginBtnHeight")) {
			loginBtnHeight = (int) loginBtn.get("loginBtnHeight");
		}
		if (loginBtn.containsKey("loginBtnOffsetY")) {
			loginBtnOffsetY = Math.abs((int) loginBtn.get("loginBtnOffsetY"));
		}
		if (loginBtn.containsKey("loginBtnOffsetX")) {
			loginBtnOffsetX = Math.abs((int) loginBtn.get("loginBtnOffsetX"));
		}
		if (loginBtn.containsKey("loginBtnOffsetBottomY")) {
			loginBtnOffsetBottomY = Math.abs((int) loginBtn.get("loginBtnOffsetBottomY"));
		}
		if (loginBtn.containsKey("loginBtnOffsetRightX")) {
			loginBtnOffsetRightX = Math.abs((int) loginBtn.get("loginBtnOffsetRightX"));
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


		if (loginBtn.containsKey("loginImgNormalName") && loginBtn.containsKey("loginImgPressedName") ){
			String loginImgNormal = (String) loginBtn.get("loginImgNormalName"); //assets里，转成drawable
			String loginImgPressed = (String) loginBtn.get("loginImgPressedName"); //assets里，转成drawable

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

		if (loginBtn.containsKey("loginBtnImgIdName")){
			String loginBtnImgName = (String) loginBtn.get("loginBtnImgIdName");
			if (!TextUtils.isEmpty(loginBtnImgName)){
				int bgId = ResHelper.getResId(FlyVerify.getContext(),"drawable",loginBtnImgName);
				builder.setLoginBtnImgId(bgId);
			}
		}

//		if (loginBtn.containsKey("loginImgPressedName")){
//			String loginImgPressed = (String) loginBtn.get("loginImgPressedName"); //assets里，转成drawable
//
//			StateListDrawable drawable = new StateListDrawable();
//			if (loginImgPressed.startsWith("#")) {
//				drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.parseColor(loginImgPressed)));
//			} else {
//				try {
//					loginImgPressed = "flutter_assets" + File.separator + "assets" + File.separator+ loginImgPressed;
//					InputStream is = FlyVerify.getContext().getAssets().open(loginImgPressed);
//					Drawable drawableStateUnPressed = createBitmap(is, dipToPx(FlyVerify.getContext(), loginBtnWidth), dipToPx(FlyVerify.getContext(), loginBtnHeight));
//					drawable.addState(new int[]{-android.R.attr.state_pressed}, drawableStateUnPressed);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			builder.setLoginBtnImgId(drawable);
//		}

//		if (loginBtn.containsKey("loginBtnImgArr")) {
//			ArrayList loginBtnImgDrawablePathArray = (ArrayList) loginBtn.get("loginBtnImgArr");
//			if (loginBtnImgDrawablePathArray != null && loginBtnImgDrawablePathArray.size() == 3) {
//				String loginBtnColorOrImageOne = "";
//				String loginBtnColorOrImageTwo = "";
//				String loginBtnColorOrImageThree = "";
//				loginBtnColorOrImageOne = (String) loginBtnImgDrawablePathArray.get(0);
////				loginBtnColorOrImageTwo = (String) loginBtnImgDrawablePathArray.get(1);
//				loginBtnColorOrImageThree = (String) loginBtnImgDrawablePathArray.get(1);
//				StateListDrawable drawable = new StateListDrawable();
//				if (loginBtnColorOrImageOne.startsWith("#")) {
//					drawable.addState(new int[]{-android.R.attr.state_pressed}, new ColorDrawable(Color.parseColor(loginBtnColorOrImageOne)));
//				} else {
//					try {
//						loginBtnColorOrImageOne = "flutter_assets" + File.separator + "assets" + File.separator+ loginBtnColorOrImageOne;
//						InputStream is = FlyVerify.getContext().getAssets().open(loginBtnColorOrImageOne);
//						Drawable drawableStateUnPressed = createBitmap(is, dipToPx(FlyVerify.getContext(), loginBtnWidth), dipToPx(FlyVerify.getContext(), loginBtnHeight));
//						drawable.addState(new int[]{-android.R.attr.state_pressed}, drawableStateUnPressed);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
////			if (loginBtnColorOrImageTwo.startsWith("#")) {
////				drawable.addState(new int[]{-android.R.attr.state_active}, new ColorDrawable(Color.parseColor(loginBtnColorOrImageTwo)));
////			} else {
////				try {
////			loginBtnColorOrImageTwo =  "flutter_assets" + File.separator + "assets" + File.separator + loginBtnColorOrImageTwo;
////					InputStream is = FlyVerify.getContext().getAssets().open(loginBtnColorOrImageTwo);
////					Drawable drawableStateDisable = createBitmap(is, dipToPx(FlyVerify.getContext(), loginBtnWidth), dipToPx(FlyVerify.getContext(), loginBtnHeight));
////					drawable.addState(new int[]{-android.R.attr.state_active}, drawableStateDisable);
////				} catch (IOException e) {
////					e.printStackTrace();
////				}
////			}
//				if (loginBtnColorOrImageThree.startsWith("#")) {
//					drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.parseColor(loginBtnColorOrImageThree)));
//				} else {
//					try {
//						loginBtnColorOrImageThree = "flutter_assets" + File.separator + "assets" + File.separator  + loginBtnColorOrImageThree;
//						InputStream is = FlyVerify.getContext().getAssets().open(loginBtnColorOrImageThree);
//						Drawable drawableStatePressed = createBitmap(is, dipToPx(FlyVerify.getContext(), loginBtnWidth), dipToPx(FlyVerify.getContext(), loginBtnHeight));
//						drawable.addState(new int[]{android.R.attr.state_pressed}, drawableStatePressed);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				builder.setLoginBtnImgId(drawable);
//			}
//		}

		if (loginBtn.containsKey("loginBtnTextIdName")) {
			String loginBtnText = (String) loginBtn.get("loginBtnTextIdName");
			if (!TextUtils.isEmpty(loginBtnText)) {
				int textId = ResHelper.getResId(FlyVerify.getContext(),"string",loginBtnText);
				builder.setLoginBtnTextId(textId);
			}
		}

		if (loginBtn.containsKey("loginBtnText")) {
			String loginBtnText = (String) loginBtn.get("loginBtnText");
			if (!TextUtils.isEmpty(loginBtnText)) {
				builder.setLoginBtnTextId(loginBtnText);
			}
		}


		if (loginBtn.containsKey("loginBtnTextColorIdName")) {
			String loginBtnTextColor = (String) loginBtn.get("loginBtnTextColorIdName");
			if (!TextUtils.isEmpty(loginBtnTextColor)) {
				int loginColorId = ResHelper.getResId(FlyVerify.getContext(),"color",loginBtnTextColor);
				builder.setLoginBtnTextColorId(loginColorId);
			}
		}
		if (loginBtn.containsKey("loginBtnTextSize")) {
			int loginBtnTextSize = (int) loginBtn.get("loginBtnTextSize");
			if (loginBtnTextSize > 0) {
				builder.setLoginBtnTextSize(loginBtnTextSize);
			}
		}
		if (loginBtn.containsKey("loginBtnHidden")) {
			boolean loginBtnHidden = (boolean) loginBtn.get("loginBtnHidden");
			builder.setLoginBtnHidden(loginBtnHidden);
		}
		if (loginBtn.containsKey("loginBtnTextBold")) {
			boolean loginBtnTextBold = (boolean) loginBtn.get("loginBtnTextBold");
			builder.setLoginBtnTextBold(loginBtnTextBold);
		}
		if (loginBtn.containsKey("loginBtnAlignParentRight")) {
			boolean loginBtnAlignParentRight = (boolean) loginBtn.get("loginBtnAlignParentRight");
			builder.setLoginBtnAlignParentRight(loginBtnAlignParentRight);
		}
		return builder;
	}

	private static LandUiSettings.Builder setSloganBuilder(LandUiSettings.Builder builder, HashMap slogan) {
		if (slogan == null) {
			return builder;
		}
		if (slogan.containsKey("sloganTextColor")) {
			String sloganTextColor = (String) slogan.get("sloganTextColor");
			if (!TextUtils.isEmpty(sloganTextColor)) {
				int sloganColor = ResHelper.getColorRes(FlyVerify.getContext(),sloganTextColor);
				builder.setSloganTextColor(sloganColor);
			}
		}
		if (slogan.containsKey("sloganTextSize")) {
			int sloganTextSize = (int) slogan.get("sloganTextSize");
			if (sloganTextSize > 0) {
				builder.setSloganTextSize(sloganTextSize);
			}
		}
		if (slogan.containsKey("sloganHidden")) {
			boolean sloganHidden = (boolean) slogan.get("sloganHidden");
			builder.setSloganHidden(sloganHidden);
		}
		if (slogan.containsKey("sloganTextBold")) {
			boolean sloganTextBold = (boolean) slogan.get("sloganTextBold");
			builder.setSloganTextBold(sloganTextBold);
		}
		if (slogan.containsKey("sloganAlignParentRight")) {
			boolean sloganAlignParentRightNumber = (boolean) slogan.get("sloganAlignParentRight");
			builder.setSloganAlignParentRight(sloganAlignParentRightNumber);
		}
		int sloganOffsetX = -1;
		int sloganOffsetY = -1;
		int sloganOffsetBottomY = -1;
		int sloganOffsetRightX = -1;
		if (slogan.containsKey("sloganOffsetY")) {
			sloganOffsetY = Math.abs((int) slogan.get("sloganOffsetY"));
		}
		if (slogan.containsKey("sloganOffsetX")) {
			sloganOffsetX = Math.abs((int) slogan.get("sloganOffsetX"));
		}
		if (slogan.containsKey("sloganOffsetBottomY")) {
			sloganOffsetBottomY = Math.abs((int) slogan.get("sloganOffsetBottomY"));
		}
		if (slogan.containsKey("sloganOffsetRightX")) {
			sloganOffsetRightX = Math.abs((int) slogan.get("sloganOffsetRightX"));
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

		return builder;
	}

	private static LandUiSettings.Builder setDialogBuilder(LandUiSettings.Builder builder, HashMap dialog) {
		if (dialog == null) {
			return builder;
		}
		if (dialog.containsKey("dialogTheme")) {
			boolean dialogTheme = (boolean) dialog.get("dialogTheme");
			builder.setDialogTheme(dialogTheme);
		}
		if (dialog.containsKey("dialogAlignBottom")) {
			boolean dialogAlignBottom = (boolean) dialog.get("dialogAlignBottom");
			builder.setDialogAlignBottom(dialogAlignBottom);
		}
		if (dialog.containsKey("dialogBackgroundClickClose")) {
			boolean dialogBackgroundClickReturn = (boolean) dialog.get("dialogBackgroundClickClose");
			builder.setDialogMaskBackgroundClickClose(dialogBackgroundClickReturn);
		}
		int dialogOffsetX = -1;
		int dialogOffsetY = -1;
		int dialogWidth = -1;
		int dialogHeight = -1;

		if (dialog.containsKey("dialogOffsetX")) {
			dialogOffsetX = (int) dialog.get("dialogOffsetX");
		}
		if (dialog.containsKey("dialogOffsetY")) {
			dialogOffsetY = (int) dialog.get("dialogOffsetY");
		}
		if (dialog.containsKey("dialogWidth")) {
			dialogWidth = (int) dialog.get("dialogWidth");
		}
		if (dialog.containsKey("dialogHeight")) {
			dialogHeight = (int) dialog.get("dialogHeight");
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
		if (dialog.containsKey("dialogBackground")) {
			String dialogBackgroundPath = (String) dialog.get("dialogBackground");
			if (!TextUtils.isEmpty(dialogBackgroundPath)) {
				dialogBackgroundPath =  "flutter_assets" + File.separator + "assets" + File.separator + dialogBackgroundPath;
				try {
					InputStream is = FlyVerify.getContext().getAssets().open(dialogBackgroundPath);
					Drawable logoImgDrawable = createBitmap(is, dipToPx(FlyVerify.getContext(), dialogWidth), dipToPx(FlyVerify.getContext(), dialogHeight));
					builder.setDialogMaskBackground(logoImgDrawable);
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
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
					Log.e(TAG, "CustomViewClick are not avliable");
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
					Log.e(TAG, "Current is TextView");
					textView = new TextView(FlyVerify.getContext());
					if (customView.containsKey("viewText")) {
						String viewText = (String) customView.get("viewText");
						Log.e(TAG, "Current is viewText: "+viewText);
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
					Log.e(TAG, "Current is ImageView");
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
								Log.e(TAG, e.getMessage(), e);
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
