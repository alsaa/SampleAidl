// IRemoteServiceCallback.aidl
package com.androidaidl.androidaidllibrary;

import com.androidaidl.androidaidllibrary.Product;

interface IRemoteServiceCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void handleGetResponse(out Product prd);
}
