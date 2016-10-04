package com.androidaidl.androidaidllibrary;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

/**
 * Created by afrin on 1/8/16.
 */
public class BindService {

    public static final String TAG = "AIDL_BIND_SERVICE";

       public static boolean aidlBindService(Context app, ServiceConnection conn, int flags) {
           boolean result = false;
           Intent service = new Intent(app, com.androidaidl.androidaidllibrary.ProductService.class);
           service.setPackage("com.androidaidl.androidaidllibrary");
                result = app.bindService(service, conn, flags);
                return result;
        }
}
