package com.androidaidl.androidaidllibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by afrin on 1/8/16.
 */
public class ProductService extends Service {

    public static final String TAG = "AIDL_SERVICE";
    public static final int SUCCESS = 0;
    public static final int FAIL = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service Destroyed");
    }

    private final IRemoteProductService.Stub mBinder = new IRemoteProductService.Stub() {

        List <Product> products = Collections.synchronizedList(new ArrayList<Product>());

        @Override
        public void addProduct(String name, int quantity, float cost) throws RemoteException {
            //Add product called on the service.
            //Idealy you should store the product in a local data base
            //or in some remote service.
            //You can add that code here . We are just storing in In memory list.
            Product product = new Product(name, quantity, cost);
            products.add(product);
        }

        @Override
        public void getProduct(String name, IRemoteServiceCallback callback) throws RemoteException {
            //getProduct product called on the service.
            //Idealy you should store the product in a local data base
            //or in some remote service. Hence the product should be fetched from there.
            //You can add that code here .
            //We are just storing in In memory list.So fetching from in memory list.
            for(Product product : products) {
                if(product.getName().equalsIgnoreCase(name)) {
                    callback.handleGetResponse(product);
                }
            }
            callback.handleGetResponse(null);
        }
    };

}
