// IRemoteProductService.aidl
package com.androidaidl.androidaidllibrary;

import com.androidaidl.androidaidllibrary.Product;
import com.androidaidl.androidaidllibrary.IRemoteServiceCallback;

interface IRemoteProductService {

    oneway void addProduct(String name , int quantity, float cost);
    oneway void getProduct(String name, IRemoteServiceCallback callback);
}

