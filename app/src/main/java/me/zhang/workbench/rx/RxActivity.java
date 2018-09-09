package me.zhang.workbench.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import me.zhang.workbench.R;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static me.zhang.workbench.R.id.zippedContent;

/**
 * Created by zhangxiangdong on 2016/9/22.
 */

public class RxActivity extends AppCompatActivity {

    private static final String TAG = RxActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rx);

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

        Observable<String> fetchFromBaiduObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    String content = fetchContent("https://www.baidu.com/");
                    subscriber.onNext(content); // Emits content to subscriber
                    subscriber.onCompleted(); // Nothing more to emit
                } catch (Exception e) {
                    // In case there are network errors
                    subscriber.onError(e);
                }
            }
        });

        final TextView netContentTextView = (TextView) findViewById(R.id.netContent);
        // use subscribeOn and observeOn to specify the threads it should use and subscribe to it
        fetchFromBaiduObservable
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        netContentTextView.setText(s); // Change a View
                    }
                });

        Observable<String> fetchFromGoogleObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    String content = fetchContent("https://www.google.com/");
                    subscriber.onNext(content); // Emits content to subscriber
                    subscriber.onCompleted(); // Nothing more to emit
                } catch (Exception e) {
                    // In case there are network errors
                    subscriber.onError(e);
                }
            }
        });

        Observable<String> fromBaidu = fetchFromBaiduObservable.subscribeOn(Schedulers.newThread());
        Observable<String> fromGoogle = fetchFromGoogleObservable.subscribeOn(Schedulers.newThread());
        final TextView zippedContentTextView = (TextView) findViewById(zippedContent);
        Observable
                .zip(fromBaidu, fromGoogle, new Func2<String, String, String>() {
                    @Override
                    public String call(String s1, String s2) {
                        return s1 + "\n*\n" + s2;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        zippedContentTextView.setText(s);
                    }
                });

        final TextView concatContentTextView = (TextView) findViewById(R.id.concatContent);
        Observable
                // use the concat operator to run the threads one after another
                .concat(fromBaidu, fromGoogle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        concatContentTextView.setText(s);
                    }
                });

        Button observeButton = (Button) findViewById(R.id.observeButton);
        RxView
                .clicks(observeButton)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(RxActivity.this, "Button clicked.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String fetchContent(String s) throws InterruptedException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new InterruptedException(e.getMessage());
        }
        return "From: " + s;
    }
}
