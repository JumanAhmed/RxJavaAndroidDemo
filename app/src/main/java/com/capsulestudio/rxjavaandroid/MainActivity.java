package com.capsulestudio.rxjavaandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.capsulestudio.rxjavaandroid.R.id.subscribe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mshow,mSecond;
    private Button btnStart,btnSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.addLogAdapter(new AndroidLogAdapter());  // Logger Test
        Logger.e("error");

        mshow = (TextView) findViewById(R.id.tvshow);
        mSecond = (TextView) findViewById(R.id.tvsecond);
        btnSecond = (Button) findViewById(R.id.btnSecond);
        btnStart = (Button) findViewById(subscribe);

        btnStart.setOnClickListener(this);
        btnSecond.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case subscribe:
                howRxJavaWillHelpMe();
                break;
            case R.id.btnSecond:
                subcribeSameObservable();
                break;

        }

    }

    public void howRxJavaWillHelpMe(){
        Logger.w("howRxJavaWillHelpMe");
        counterObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        mshow.setText(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplicationContext(), " Task Completed", Toast.LENGTH_LONG).show();
                        Logger.w("onComplete");
                    }
                });

    }

    public void subcribeSameObservable(){
         counterObservable()
                 .subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<String>() {
                     @Override
                     public void onSubscribe(@NonNull Disposable d) {

                     }

                     @Override
                     public void onNext(@NonNull String s) {
                               mSecond.setText(s);
                     }

                     @Override
                     public void onError(@NonNull Throwable e) {

                     }

                     @Override
                     public void onComplete() {
                         Logger.w("onComplete Second");

                     }
                 });

    }

    Observable<String> counterObservable(){
        Logger.w("counterObservable");
        return  Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> subscriber) throws Exception {
                Logger.w("subscribe");
                for (int i =1; i<= 100 ; i++){
                    Thread.sleep(500);
                    subscriber.onNext(" "+i);
                }
                subscriber.onComplete();
            }

        });
    }


}
