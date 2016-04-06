package com.groupify.prabhapattabiraman.groupme;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.groupify.prabhapattabiraman.groupme.retrofit.impl.GroupmeServerService.getServiceInstance;

public class MyInstanceIdListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Log.d("REGISTER", "Refresh token called");
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
