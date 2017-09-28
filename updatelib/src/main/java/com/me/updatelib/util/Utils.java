package com.me.updatelib.util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jjr on 2017/4/12.
 */

public class Utils {

    private static final String DOWNLOAD_ID = "downloadId";

    /**
     * 获取保存的downloadId
     *
     * @return 如不存在返回-1
     */
    public static long getDownloadId(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getLong(DOWNLOAD_ID, -1L);
    }

    /**
     * 保存的downloadId到本地
     */
    public static void setDownloadId(Context context, long downloadId) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putLong(DOWNLOAD_ID, downloadId).commit();
    }

    /**
     * 显示toast
     */
    private static Toast toast;

    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 判断是否是网络请求url
     *
     * @param url
     * @return
     */
    public static boolean isHttpUrl(String url) {
        return (null != url) && (url.length() > 6)
                && url.substring(0, 7).equalsIgnoreCase("http://");
    }

    public static boolean isHttpsUrl(String url) {
        return (null != url) && (url.length() > 7)
                && url.substring(0, 8).equalsIgnoreCase("https://");
    }

    public static boolean isNetworkUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        return isHttpUrl(url) || isHttpsUrl(url);
    }

    /**
     * 判断能不能使用DownloadManager
     *
     * @param context used to check the device version and DownloadManager information
     * @return true if the download manager is available
     */
    public static boolean isDownloadManagerAvailable(Context context, Intent intent) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
                return false;
            }
            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断下载服务是否可用
     *
     * @param context
     * @return true为可用, false为不可用
     */
    public static boolean canDownloadState(Context context) {
        try {
            int state = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 打开下载设置界面
     */
    public static void showDownloadSetting(Context context) {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (isDownloadManagerAvailable(context, intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * 获取当前app版本信息
     *
     * @return packageInfo
     * @throws PackageManager.NameNotFoundException
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        if (context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
//                String packageName = packageInfo.packageName;
//                String version = packageInfo.versionName;
//                Log.d("getPackageInfo", "packageName:" + packageName + ";version:" + version);
//                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.MATCH_DISABLED_UNTIL_USED_COMPONENTS);
//                String appName = packageManager.getApplicationLabel(applicationInfo).toString();
//                Drawable icon = packageManager.getApplicationIcon(applicationInfo);//得到图标信息
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return packageInfo;
    }

    /**
     * 获取未安装的apk文件版本的信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    public static PackageInfo getPackageInfo(Context context, String path) {
        PackageInfo packageInfo = null;
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        }
        return packageInfo;
    }

    /**
     * 下载的apk和当前程序版本比较
     *
     * @param context     Context
     * @param apkFileInfo apk file's packageInfo
     * @return 如果当前应用版本小于apk文件的版本则返回true
     */
    public static boolean compare(Context context, PackageInfo apkFileInfo) {
        if (apkFileInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        if (apkFileInfo.packageName.equals(localPackage)) {
            PackageInfo packageInfo = getPackageInfo(context);
            if (apkFileInfo.versionCode > packageInfo.versionCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下载到本地的apk和服务器的版本比较
     *
     * @param versionCode 服务器最新的apk版本号
     * @param apkFileInfo apk file's packageInfo
     * @return 如果版本号相同则返回ture,不同则返回false
     */
    public static boolean compare(String versionCode, PackageInfo apkFileInfo) {
        if (apkFileInfo == null || TextUtils.isEmpty(versionCode)) {
            return false;
        }
        if (Integer.parseInt(versionCode) == apkFileInfo.versionCode) {
            return true;
        }
        return false;
    }

    /**
     * 打开系统安装APK的界面
     *
     * @param uri
     */
    public static void installApk(Context context, Uri uri) {
        if (context != null) {
            if (uri != null) {
                Log.d("DownloadManager", uri.toString());
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(uri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(Build.VERSION.SDK_INT >= 24) install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                context.startActivity(install);
            } else {
                Log.e("NullPointerException", "Utils-installApk-保存的apk文件uri为空");
            }
        } else {
            Log.e("NullPointerException", "Utils-installApk-context为空");
        }
    }

    public static String getPathByUri(final Context context, final Uri uri) {
        String path = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            path = getPathByUriBeforeKitkat(context, uri);
        } else {
            path = getPathByUriAfterKitkat(context, uri);
        }
        return path;
    }

    private static String getPathByUriBeforeKitkat(Context context, Uri uri) {
        String filename = null;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, new String[] { "_data" }, null, null, null);
            if (cursor.moveToFirst()) {
                filename = cursor.getString(0);
            }
        } else if (uri.getScheme().toString().compareTo("file") == 0) {// file:///开头的uri
            filename = uri.toString();
            filename = uri.toString().replace("file://", "");// 替换file://
//            if (!filename.startsWith("/mnt")) {// 加上"/mnt"头
//                filename = "/mnt" + filename;
//            }
        }
        return filename;
    }

    // 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getPathByUriAfterKitkat(final Context context, final Uri uri) {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/my_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if (uri != null && "content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if (uri != null && "file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
