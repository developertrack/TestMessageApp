package com.testmessage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TrueSDK;
import com.truecaller.android.sdk.TrueSdkScope;

public class MainActivity extends AppCompatActivity implements ITrueCallback {

    String TAG="Truecaller Test";
    boolean trueapp;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TrueSdkScope trueScope = new TrueSdkScope.Builder(this, this)
                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.SDK_CONSENT_TITLE_LOG_IN )
                .build();

        TrueSDK.init(trueScope);

        verify=(Button)findViewById(R.id.verify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "Verified Successfully : " + String.valueOf(trueapp) );

                trueapp=TrueSDK.getInstance().isUsable();

                if(trueapp){
                    TrueSDK.getInstance().getUserProfile(MainActivity.this);
                }else{
                    Toast.makeText(MainActivity.this,"Install True Caller for verification",Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    @Override
    public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
        Log.d( TAG, "Verified Successfully : " + trueProfile.firstName );
//        TrueSDK.getInstance().getUserProfile();
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Log.d( TAG, "onFailureProfileShared: " + trueError.getErrorType() );
    }

    @Override
    public void onOtpRequired() {

    }
}
