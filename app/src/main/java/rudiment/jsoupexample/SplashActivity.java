package rudiment.jsoupexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import rudiment.jsoupexample.util.PreferenceConnector;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    public static int SPLASHTIME = 4000;
    Message msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AdView adView = (AdView) findViewById(R.id.adView);
        AdView adView2 = (AdView) findViewById(R.id.adView2);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView2.loadAd(adRequest);


        addHandler();
        msg = new Message();
        msg.what = 1;
        handler.sendMessageDelayed(msg, SPLASHTIME);
    }

    private void switchActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            handler.removeMessages(msg.what);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (message.what == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (PreferenceConnector.readBoolean(SplashActivity.this, PreferenceConnector.IS_FIRST_TIME, false)) {
                                switchActivity(Main2Activity.class);
                                PreferenceConnector.writeBoolean(SplashActivity.this, PreferenceConnector.IS_FIRST_TIME, true);
                            } else {
                                switchActivity(IntroActivity.class);
                                PreferenceConnector.writeBoolean(SplashActivity.this, PreferenceConnector.IS_FIRST_TIME, true);
                            }
                        }
                    });
                }
                return false;
            }
        });
    }
}
