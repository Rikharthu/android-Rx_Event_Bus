package com.example;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class UserLocationModel {

    private PublishSubject<LatLng> mSubject = PublishSubject.create();

    public void setLocation(LatLng latLng) {
        mSubject.onNext(latLng);
    }

    public Observable<LatLng> getUserLocation() {
        return mSubject;
    }
}
