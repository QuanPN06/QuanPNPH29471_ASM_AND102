package quanpnph29471.example.quanpnph29471_asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView img = findViewById(R.id.img_splash);
        TextView tv = findViewById(R.id.tv_splash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        img.startAnimation(animation);
        tv.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}