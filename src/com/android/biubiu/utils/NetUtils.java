package com.android.biubiu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
	 /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager connectivity
                = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
        {
            return false;
        }
        else
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
