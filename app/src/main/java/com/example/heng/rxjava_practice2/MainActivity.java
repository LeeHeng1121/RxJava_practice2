package com.example.heng.rxjava_practice2;

        import android.graphics.drawable.Drawable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.ImageView;

        import rx.Observable;
        import rx.Observer;
        import rx.Scheduler;
        import rx.Single;
        import rx.Subscriber;
        import rx.android.schedulers.AndroidSchedulers;
        import rx.functions.Action0;
        import rx.functions.Action1;
        import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    String tag = "MainActivity";

    Observer<String> observer = new Observer<String>() {

        @Override
        public void onNext(String s) {

            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {

        @Override
        public void onNext(String s) {

            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };


    Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });

    Observable observable2 = Observable.just("Hello","Hi","Aloha");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        observable1.subscribe(observer);
//        observable2.subscribe(observer);
        Schedulers.immediate();
        Schedulers.newThread();
        Schedulers.io();
        AndroidSchedulers.mainThread();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = (ImageView)findViewById(R.id.imageview);
        final Drawable drawable = getResources().getDrawable(R.drawable.cat3);

        observable1.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call (Subscriber<? super Drawable> subscriber){
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }

        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(tag, e.toString());
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        drawable = getResources().getDrawable(R.drawable.cat3);
                        imageView.setImageDrawable(drawable);
                    }

                 });



//        String[] names = {"henry","eric","lee"};
//        Observable.from(names).subscribe(new Action1<String>(){
//            @Override
//            public void call(String name){
//                Log.d(tag, name);
//            }
//        });
    }



}
