package me.zhang.laboratory.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import me.zhang.laboratory.R
import java.util.concurrent.TimeUnit

class RxActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()
    private lateinit var tvRes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx)

        tvRes = findViewById(R.id.tv_res)

        findViewById<Button>(R.id.btn_emit).setOnClickListener {
            onRunSchedulerExampleButtonClicked()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onRunSchedulerExampleButtonClicked() {
        disposables.add(
            sampleObservable() // Run on a background thread
                .subscribeOn(Schedulers.io()) // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Int>() {
                    override fun onNext(t: Int) {
                        tvRes.text = "onNext($t)"
                    }

                    override fun onError(e: Throwable) {
                        tvRes.text = "onError(${e.message})"
                    }

                    override fun onComplete() {
                        tvRes.text = "onComplete()"
                    }
                })
        )
    }

    private fun sampleObservable(): Observable<Int> {
        return Observable.range(1, 5).concatMap { Observable.just(it).delay(1, TimeUnit.SECONDS) }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

}