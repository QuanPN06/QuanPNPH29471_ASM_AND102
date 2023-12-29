package quanpnph29471.example.quanpnph29471_asm;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class NotifyConfig extends Application {
    public static final String CHANNEL_ID = "CHANNEL_DEMO"; //Thay the cho phu hop voi app

    @Override
    public void onCreate() {
        super.onCreate();
        config();
    }
    void config() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Tên chanel
            CharSequence name = "ten_chanel"; // tên hiển thị trong cài đặt notify của điện thoại
            // mo ta:
            String mota = "Mo ta";
            int do_uu_tien = NotificationManager.IMPORTANCE_DEFAULT;

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);//khai bao am thanh khi do thong bao
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            // đăng ký notify
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, do_uu_tien);

            channel.setDescription(mota);

            channel.setSound(uri, audioAttributes);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
