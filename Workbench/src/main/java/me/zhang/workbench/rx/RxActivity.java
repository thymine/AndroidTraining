package me.zhang.workbench.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhangxiangdong on 2016/9/22.
 */

public class RxActivity extends AppCompatActivity {

    private static final String TAG = RxActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable<String> myStringObservable = Observable.just("Hello, world!"); // Emits "Hello, world!"

        Observer<String> myStringObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
            }

            @Override
            public void onNext(String s) {
                // Called each time the observable emits data
                Log.i(TAG, "onNext: " + s);
            }
        };

        Subscription mySubscription = myStringObservable.subscribe(myStringObserver);

        Action1<String> myStringAction1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "call: " + s);
            }
        };

        myStringObservable.subscribe(myStringAction1);

        // Emits each item of the array, one at a time
        Observable<Integer> myArrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});

        myArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Log.d(TAG, "call: " + String.valueOf(i)); // Prints the number received
            }
        });

        // the call to the map operator returns a new Observable
        Observable<Integer> myNewArrayObservable = myArrayObservable.map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * integer; // Square the number
            }
        });

        myNewArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: " + integer);
            }
        });
        // it doesn’t change the original Observable
        myArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: " + integer);
            }
        });

        Observable<Integer> myNewNewArrayObservable = myNewArrayObservable
                .skip(2) // Skip the first two items
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        // Ignores any item that returns false
                        return integer % 2 == 0;
                    }
                });
        // Emits 16 and 36
        myNewNewArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "skip+filter call: " + integer);
            }
        });
    }
}
