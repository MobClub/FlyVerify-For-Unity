package cn.fly.verify.unity3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLES10;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import cn.fly.verify.FlyVerify;

public class CommonUtil {
	private static final String TAG = "CommonUtil";
	private static float density;

	public static int dipToPx(Context context, int dip) {
		if (density <= 0.0F) {
			density = context.getResources().getDisplayMetrics().density;
		}
		return (int)((float)dip * density + 0.5F);
	}

	public static int pxToDip(Context context, int px) {
		if (density <= 0) {
			density = context.getResources().getDisplayMetrics().density;
		}
		return (int) (px / density + 0.5f);
	}

	/**
	 * 压缩图片
	 * @param outputStream 图片地址
	 * @param desiredWidth 指定图片宽度
	 * @param desiredHeight 指定图片高度
	 * @return
	 */
	public static Bitmap getBitmapByCompressSize(ByteArrayOutputStream outputStream, int desiredWidth, int desiredHeight) {
		InputStream stream1 = new ByteArrayInputStream(outputStream.toByteArray());
		InputStream stream2 = new ByteArrayInputStream(outputStream.toByteArray());
		int[] maxTextureSize = new int[1];
		GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
		int maxBitmapDimension = Math.max(maxTextureSize[0], 2048);
		int maxBitmapWidth = maxBitmapDimension;
		int maxBitmapHeight = maxBitmapDimension;

		BitmapFactory.Options measureOptions = new BitmapFactory.Options();
		measureOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(stream1, null, measureOptions);
		int realWidth = measureOptions.outWidth;
		int realHeight = measureOptions.outHeight;
		int inSampleSize = 1;
		if (desiredWidth > 1 && desiredHeight > 1) {
			float minSideScale = 1.0f * Math.min(realWidth, realHeight) / Math.min(desiredWidth, desiredHeight);
			float maxSideScale = 1.0f * Math.max(realWidth, realHeight) / Math.max(desiredWidth, desiredHeight);
			float ratio = realWidth / realHeight;
			if (ratio > 2 || ratio < 0.5) {
				//宽高比大于2的图，以最小边的压缩比为主
				float n = 1.0f;
				while ((n * 2) <= minSideScale) {
					n *= 2;
				}
				inSampleSize = (int) n;
			} else {
				//小于2的图，则按最小的压缩比为主
				float minScale = Math.min(minSideScale, maxSideScale);
				float n = 1.0f;
				while ((n * 2) <= minScale) {
					n *= 2;
				}
				inSampleSize = (int) n;
			}
		}
		if (inSampleSize < 1) {
			inSampleSize = 1;
		}
		//consider max texture size
		while ((realWidth / inSampleSize) > maxBitmapWidth || (realHeight / inSampleSize) > maxBitmapHeight) {
			inSampleSize++;
		}
		BitmapFactory.Options decodingOptions = new BitmapFactory.Options();
		decodingOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		decodingOptions.inSampleSize = inSampleSize;
		return BitmapFactory.decodeStream(stream2, null, decodingOptions);
	}

	public static Drawable createBitmap(InputStream is, int targetWidth, int targetHeight) {
		ByteArrayOutputStream stream = transferInputStreamToByteArray(is);
		Bitmap img = getBitmapByCompressSize(stream, targetWidth, targetHeight);
		return new BitmapDrawable(FlyVerify.getContext().getResources(),img);
	}

	/**
	 * 由于inputStream数据流只能读一次，所以先转为ByteArrayOutputStream，需要时再转回InputStream
	 * @param inputStream 数据流
	 * @return
	 */
	private static ByteArrayOutputStream transferInputStreamToByteArray(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buffer)) > -1 ) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
		} catch (IOException ex) {
			Log.e(TAG, ex.getMessage(), ex);
		}
		return baos;
	}
}
