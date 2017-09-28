package com.me.core.permission;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by xunice on 08/03/2017.
 */

@TargetApi(Build.VERSION_CODES.M)
public class PermissionManagement {


    /**
     * 判断权限集合,检查是否有缺少的权限
     */
    public static boolean lacksPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                //如果有缺少的权限返回
                return true;
            }
        }
        return false;
    }

    /**
     * 判断权限结果，是否全部获取成功
     */
    public static boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

}
