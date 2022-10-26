package com.migros.mkolay.assistant;


import android.content.Context;


import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Map data; //includes notification data
    Context context;
    /**
     * user token changes
     * @param s new token
     */
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        context = this;
        /**
         * checks data payload as json
         */
        if (remoteMessage.getData().size() > 0) {

            /**
             * retrieve data
             */
            data = remoteMessage.getData();
        }
    }


}
