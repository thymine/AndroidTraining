package me.zhang.art.ipc.parcel;

import android.os.RemoteException;

/**
 * Created by Li on 6/20/2016 8:49 PM.
 */
public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

}
