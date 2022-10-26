package com.migros.mkolay.assistant;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.migros.mkolay.MainActivity;
import com.migros.mkolay.R;
import com.migros.mkolay.assistant.AppShared;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    Context context;
    public MyFirebaseInstanceIdService() {
        this.context = MainActivity.instance();
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {

                            String token = task.getResult();
                            storeRegistrationId(getApplicationContext(), token);
                        }
                    }
                });
    }

    private void storeRegistrationId(Context ctx, String regid) {
        final SharedPreferences prefs = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ctx.getResources().getString(R.string.PROPERTY_REG_ID), regid);
        editor.apply();
    }
}