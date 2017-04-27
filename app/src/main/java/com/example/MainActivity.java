package com.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.uberv.rxeventbus.R;

import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView mLocationTv;
    private Disposable mSubscribtion;
    private UserLocationModel mUserLocationModel = new UserLocationModel();
    private Consumer<LatLng> mLocationConsumer = new Consumer<LatLng>() {
        @Override
        public void accept(@NonNull LatLng latLng) throws Exception {
            Log.d(LOG_TAG, "new location: " + latLng.toString());
            mLocationTv.setText("lat: " + latLng.getLatitude() + ", lng: " + latLng.getLongitude());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationTv = (TextView) findViewById(R.id.location_tv);
    }

    public void onSubscribe(View view) {
        if (mSubscribtion == null) {
            mSubscribtion = mUserLocationModel.getUserLocation().subscribe(mLocationConsumer);
        }
    }

    public void onUnsubscribe(View view) {
        if (mSubscribtion != null) {
            if (!mSubscribtion.isDisposed()) {
                mSubscribtion.dispose();
            }
            mSubscribtion = null;
        }
    }

    public void onUpdateLocation(View view) {
        Random rnd = new Random();
        mUserLocationModel.setLocation(new LatLng(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000));
    }
}
