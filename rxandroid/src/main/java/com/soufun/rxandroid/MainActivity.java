package com.soufun.rxandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func0;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "RxAndroidSamples";

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BackgroundThread thread = new BackgroundThread();
        thread.start();

        handler = new Handler(thread.getLooper());

        findViewById(R.id.run).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRunSchedulerExampleButtonClicked();
            }
        });
    }

    private void onRunSchedulerExampleButtonClicked() {
        sampleObservable()
                // Run on a background thread
                .subscribeOn(HandlerScheduler.from(handler))
                        // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "onCompleted()");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError()", e);
                            }

                            @Override
                            public void onNext(String s) {
                                Log.d(TAG, "onNext(" + s + ")");
                            }
                        }
                );
    }

    private Observable<String> sampleObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                try {
                    // Do some long running operation
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e) {
                    throw OnErrorThrowable.from(e);
                }
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    class BackgroundThread extends HandlerThread {

        public BackgroundThread() {
            super("BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }

}
