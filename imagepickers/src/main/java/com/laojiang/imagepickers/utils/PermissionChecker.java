package com.laojiang.imagepickers.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.laojiang.imagepickers.R;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 权限检查工具类
 */
public class PermissionChecker
{

    /**
     * 检查权限的方法
     *
     * @param activity              发起检查的Activity
     * @param permissions           权限组
     * @param requestCode           请求Code
     * @param dialogMsgForRationale 为权限用途作解释的Dialog内容
     * @return 是否有权限，没有权限时会发起请求权限
     */
    public static boolean checkPermissions(final Activity activity, String[] permissions
            , final int requestCode, final int dialogMsgForRationale)
    {
        //Android6.0以下默认有权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        final List<String> needList = new ArrayList<>();
        boolean needShowRationale = false;
        int length = permissions.length;

        for (int i = 0; i < length; i++)
        {
            String permisson = permissions[i];
            if (ActivityCompat.checkSelfPermission(activity, permisson)
                    != PackageManager.PERMISSION_GRANTED)
            {
                needList.add(permisson);
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permisson))
                    needShowRationale = true;
            }
        }

        if (needList.size() != 0)
        {
            if (needShowRationale)
            {
                new AlertDialog.Builder(activity).setCancelable(false)
                        .setTitle(R.string.dialog_imagepicker_permission_title)
                        .setMessage(dialogMsgForRationale)
                        .setPositiveButton(R.string.dialog_imagepicker_permission_confirm, new DialogInterface.OnClickListener()
                        {
                            @TargetApi(Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(activity, needList.toArray(new String[needList.size()]), requestCode);
                            }
                        }).create().show();
                return false;
            }

            ActivityCompat.requestPermissions(activity, needList.toArray(new String[needList.size()]), requestCode);
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * 判断请求权限的结果
     *
     * @param activity                发起请求的Activity
     * @param permissions             权限组
     * @param grantResults            请求结果
     * @param finishAfterCancelDialog Dialog被取消后是否关闭Activity
     * @param dialogMsgForNerverAsk   “不再提醒”的Dialog提示内容
     * @return 检查结果、是否显示NerverAsk
     */
    public static boolean[] onRequestPermissionsResult(final Activity activity, String[] permissions, int[] grantResults,
                                                       final boolean finishAfterCancelDialog, int dialogMsgForNerverAsk)
    {
        boolean result = true;
        boolean isNerverAsk = false;

        int length = grantResults.length;
        for (int i = 0; i < length; i++)
        {
            String permission = permissions[i];
            int grandResult = grantResults[i];
            if (grandResult == PackageManager.PERMISSION_DENIED)
            {
                result = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                    isNerverAsk = true;
            }
        }

        if (!result)
        {
            Toast.makeText(activity, R.string.toast_imagepicker_permission_denied, Toast.LENGTH_SHORT).show();
            if (isNerverAsk)
            {
                //处理NerverAsk
                new AlertDialog.Builder(activity).setCancelable(false)
                        .setTitle(R.string.dialog_imagepicker_permission_title)
                        .setMessage(dialogMsgForNerverAsk)
                        .setNegativeButton(R.string.dialog_imagepicker_permission_nerver_ask_cancel, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                                if (finishAfterCancelDialog)
                                    activity.finish();
                            }
                        })
                        .setPositiveButton(R.string.dialog_imagepicker_permission_nerver_ask_confirm, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        }).create().show();
            }
        }

        return new boolean[]{result, isNerverAsk};
    }
}
