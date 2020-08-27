package com.vempower.eezyclinic.application;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.stripe.android.EphemeralKey;
import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.schedulers.Schedulers;

public class ExampleEphemeralKeyProvider implements EphemeralKeyProvider {
   /* private final BackendApi backendApi =
            RetrofitFactory.instance.create(BackendApi.class);*/
   /* private final CompositeDisposable compositeDisposable =
            new CompositeDisposable();*/



    @Override
    public void createEphemeralKey(
            @NonNull @Size(min = 4) String apiVersion,
            @NonNull final EphemeralKeyUpdateListener keyUpdateListener) {
        final Map<String, String> apiParamMap = new HashMap<>();
        apiParamMap.put("api_version", apiVersion);

        // Using RxJava2 for handling asynchronous responses
  /*      compositeDisposable.add(backendApi.createEphemeralKey(apiParamMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            try {
                                final String rawKey = response.string();
                                keyUpdateListener.onKeyUpdate(rawKey);
                            } catch (IOException ignored) {
                            }
                        }));*/
    }
}
