package uitstart.uit.schedule.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import uitstart.uit.schedule.R;

/*
    Created by Lê Văn Khang
    Detail: when you launch app, this activity show logo first in 1.5 second before launch to main app
 */

public class LogoActivity extends AppCompatActivity {

    private static final int TIME_SHOW_LOGO=1500; // 1,5 second
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentMainActivity=new Intent(LogoActivity.this,MainActivity.class);
                startActivity(intentMainActivity);// launch to MainActivity
                finish();
            }
        },TIME_SHOW_LOGO);
    }
}
