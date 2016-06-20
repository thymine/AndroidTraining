package me.zhang.art.ipc.parcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static me.zhang.art.ipc.parcel.BinderPool.BINDER_COMPUTE;
import static me.zhang.art.ipc.parcel.BinderPool.BINDER_SECURITY_CENTER;

/**
 * Created by Li on 6/20/2016 10:24 PM.
 */
public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInsance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BINDER_SECURITY_CENTER);

        ISecurityCenter mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG, "visit ISecurityCenter");
        String msg = "helloworld-安卓";
        Log.i(TAG, "doWork: content, " + msg);

        String password;
        try {
            password = mSecurityCenter.encrypt(msg);
            Log.i(TAG, "doWork: encrypt, " + password);
            Log.i(TAG, "doWork: decrypt, " + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BINDER_COMPUTE);
        ICompute mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Log.i(TAG, "doWork: 3 + 5 = " + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
