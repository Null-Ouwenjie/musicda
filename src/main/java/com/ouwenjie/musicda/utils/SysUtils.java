package com.ouwenjie.musicda.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * 系统工具类，带一些常用的调用系统功能
 * 使用单例模式
 * Created by 文杰 on 2014年12月30日 14:30:18
 */
public class SysUtils {

	private static SysUtils mSysUtils;

	private Context mContext;
	private PackageManager packageManager;

	// 默认SharedPreferences
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

	private SysUtils(Context cxt) {

		this.mContext = cxt;
		this.packageManager = mContext.getPackageManager();
		preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		editor = preferences.edit();
	}

	public static SysUtils getInstance(Context cxt) {
		if (null == mSysUtils) {
			mSysUtils = new SysUtils(cxt);
		}
		return mSysUtils;
	}

	/**
	 * 查询某个Activity是否可用
	 *
	 * @param action
	 * @return
	 */
	public boolean isIntentAvailable(String action) {

		Intent intent = new Intent(action);
		return isIntentAvailable(intent);
	}

	/**
	 * 查询带参数的intent是否可用
	 *
	 * @param action
	 * @param URIs
	 * @return
	 */
	public boolean isIntentAvailable(String action, String URIs) {

		Intent intent = new Intent(action, Uri.parse(URIs));

		return isIntentAvailable(intent);
	}

	/**
	 * 检测某个intent是否可用
	 * 
	 * @param intent
	 * @return
	 */
	public boolean isIntentAvailable(Intent intent) {

		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);

		return list.size() > 0;
	}

	/**
	 * 检测Google Play是否可用
	 */
	public boolean hasGooglePlayInstalled() {

		return hasInstalledPackage("com.android.vending");
	}

	/**
	 * 是否已经安装了微信应用
	 * 
	 * @return
	 */
	public boolean hasWechatInstalled() {

		return hasInstalledPackage("com.tencent.mm");
	}

	/**
	 * 是否已经安装了QQ应用
	 * 
	 * @return
	 */
	public boolean hasQQInstalled() {

		return hasInstalledPackage("com.tencent.mobileqq");
	}

	/**
	 * 是否已经安装了百度地图应用
	 * 
	 * @return
	 */
	public boolean hasBaiduMapInstalled() {

		return hasInstalledPackage("com.baidu.BaiduMap");
	}

	/**
	 * 查询是否安装了某个包
	 * 
	 * @param packageName
	 * @return
	 */
	public boolean hasInstalledPackage(String packageName) {

		List<PackageInfo> list = packageManager.getInstalledPackages(0);

		if (null == list || list.size() == 0)
			return false;

		for (PackageInfo info : list) {

			if (info.packageName.equals(packageName))
				return true;
		}

		return false;
	}

	/**
	 * 检查所支持的类
	 * 
	 * @param className
	 *            类的名称
	 * @return 支持返回true,否则false
	 */
	public static boolean isClassAvailable(String className) {

		try {

			Class.forName(className);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			return false;
		}

		return true;
	}

	public SharedPreferences getPreferences() {

		return preferences;
	}

	public SharedPreferences.Editor getEditor() {

        return editor;
    }

	/**
	 * 
	 * 判断某个服务是否正在执行
	 * 
	 * @param cxt
	 * @param serviceName
	 * @return
	 */
	public boolean isServiceRunning(Context cxt, String serviceName) {

		if (null == cxt || null == serviceName || "".equals(serviceName))
			return false;

		ActivityManager mActivityManager;
		List<ActivityManager.RunningServiceInfo> mServiceList;

		mActivityManager = (ActivityManager) cxt
				.getSystemService(Context.ACTIVITY_SERVICE);
		mServiceList = mActivityManager.getRunningServices(30);

		if (null != mServiceList && !mServiceList.isEmpty()) {

			for (int i = 0; i < mServiceList.size(); i++) {

				if (serviceName.equals(mServiceList.get(i).service
						.getClassName())) {

					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 本应用是否为当前可视状态
	 * 
	 * @param cxt
	 * @return
	 */
	public boolean isTopActivity(Context cxt) {

		String packageName = null;
		ActivityManager am;
		List<RunningTaskInfo> tasksInfo;

		packageName = cxt.getPackageName();

		if (null != packageName) {

			am = (ActivityManager) cxt
					.getSystemService(Context.ACTIVITY_SERVICE);
			tasksInfo = am.getRunningTasks(2);

			if (tasksInfo.size() > 0) {

				if (packageName.equals(tasksInfo.get(0).topActivity
						.getPackageName())) { // 应用程序位于堆栈的顶层

					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 震动（隔100ms，然后震动400ms）
	 */
	public void vibrate() {

		long[] pattern = { 100, 400 }; // 震动时间（停止 开启）
		final Vibrator vibrator;

		vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE); // 震动服务
		vibrator.vibrate(pattern, -1); // 震动一次

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				vibrator.cancel();
			}
		}, 500);
	}

    /**
     * 检测WIFI连接的状态
     */
    public static boolean isWifiConnect(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }
}
