package com.example.RXkotlin;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginfirebasedemo.R;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RXActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    private String getstring = "Hello from RXJava";
    private Observable<String> myobservable;
    private Observer<String> myobserver;
    private TextView tvgetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxactivity);
        tvgetting = findViewById(R.id.tvgetting);


        myobservable = Observable.just(getstring);
        myobserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe invoked");

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext invoked");
                tvgetting.setText(s);

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError invoked");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete invoked");

            }
        };
        myobservable.subscribe(myobserver);
    }
}