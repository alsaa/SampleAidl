package com.androidaidl.androidaidl;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidaidl.androidaidllibrary.BindService;
import com.androidaidl.androidaidllibrary.IRemoteProductService;
import com.androidaidl.androidaidllibrary.IRemoteServiceCallback;
import com.androidaidl.androidaidllibrary.Product;

public class MainActivity extends Activity {

    private IRemoteProductService service;
    private RemoteServiceConnection serviceConnection;
    private static final String TAG = "AIDL_CLIENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectService();

        final IRemoteServiceCallback.Stub mCallback = new IRemoteServiceCallback.Stub() {
            @Override
            public void handleGetResponse(Product product) throws RemoteException {
                Log.d(TAG, "handleGetResponse");
                if(product != null) {
                    ((TextView) findViewById(R.id.txtSearchResult)).setText(product.toString());
                } else {
                    Toast.makeText(MainActivity.this, "No product found with this name", Toast.LENGTH_LONG)
                            .show();
                }
            }
            public void handleResponse(String name) throws RemoteException {
                Log.d(TAG, name);
            }
        };

        Button addProduct = (Button)findViewById(R.id.btnAdd);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (service != null) {
                        String name = ((EditText) findViewById(R.id.edtName)).getText().toString();
                        int quatity = Integer.parseInt(((EditText) findViewById(R.id.edtQuantity)).getText().toString());
                        float cost = Float.parseFloat(((EditText) findViewById(R.id.edtCost)).getText().toString());

                        service.addProduct(name, quatity, cost);
                        Toast.makeText(MainActivity.this, "Product added.", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(MainActivity.this, "Service is not connected", Toast.LENGTH_LONG)
                                .show();
                    }
                } catch (Exception e) {

                }
            }
        });

        Button searchProduct = (Button)findViewById(R.id.btnSearch);
        searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (service != null) {
                        String name = ((EditText) findViewById(R.id.edtSearchProduct)).getText().toString();
                        service.getProduct(name, mCallback);
                    } else {
                        Toast.makeText(MainActivity.this, "Service is not connected", Toast.LENGTH_LONG)
                                .show();
                    }
                } catch(Exception e) {

                }
            }
        });
    }

    private void connectService() {
        Log.i(TAG, "connectService");
        serviceConnection = new RemoteServiceConnection();
        Log.d(TAG, "before binding with service");
        //BindService class from the library contains API to bind with the service
        boolean ret = BindService.aidlBindService(this, serviceConnection, Context.BIND_AUTO_CREATE);

        Log.d(TAG, "bind service result = "+ret);
        Toast.makeText(MainActivity.this, "bind result = "+ret, Toast.LENGTH_LONG)
                .show();
    }
    class RemoteServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IRemoteProductService.Stub.asInterface((IBinder) boundService);
            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_LONG)
                    .show();
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(MainActivity.this, "Service disconnected", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Avtivity destroyed");
        unbindService(serviceConnection);
    }
}
