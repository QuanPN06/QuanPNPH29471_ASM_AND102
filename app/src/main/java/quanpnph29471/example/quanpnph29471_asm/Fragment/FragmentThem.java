package quanpnph29471.example.quanpnph29471_asm.Fragment;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import quanpnph29471.example.quanpnph29471_asm.DAO.TaskDAO;
import quanpnph29471.example.quanpnph29471_asm.DAO.UserDAO;
import quanpnph29471.example.quanpnph29471_asm.LoginActivity;
import quanpnph29471.example.quanpnph29471_asm.MainActivity;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.MyDatePicker;
import quanpnph29471.example.quanpnph29471_asm.NotifyConfig;
import quanpnph29471.example.quanpnph29471_asm.R;

public class FragmentThem extends Fragment {
    TextInputLayout ed_name, ed_content;
    ImageView imgStart, imgEnd;
    TextView tvStart, tvEnd;
    Button btnCancel, btnAdd;

    TaskDAO taskDAO;
    Task task;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_them_cv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ed_name = view.findViewById(R.id.ed_add_name);
        ed_content = view.findViewById(R.id.ed_add_content);
        imgStart = view.findViewById(R.id.img_add_start);
        imgEnd = view.findViewById(R.id.img_add_end);
        tvStart = view.findViewById(R.id.tv_add_start);
        tvEnd = view.findViewById(R.id.tv_add_end);
        btnCancel = view.findViewById(R.id.btn_add_cancel);
        btnAdd = view.findViewById(R.id.btn_add_add);

        imgStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatePicker.showDatePicker(getContext(), new MyDatePicker.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = monthOfYear < 9 ? "0"+(monthOfYear+1):(monthOfYear+1)+"";
                        String day = dayOfMonth < 10 ? ("0"+dayOfMonth):(dayOfMonth+"");

                        tvStart.setText(year + "/" + month + "/" +day);
                    }
                });
            }
        });

        imgEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatePicker.showDatePicker(getContext(), new MyDatePicker.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = monthOfYear < 9 ? "0"+(monthOfYear+1):(monthOfYear+1)+"";
                        String day = dayOfMonth < 10 ? ("0"+dayOfMonth):(dayOfMonth+"");

                        tvEnd.setText(year + "/" + month + "/" +day);
                    }
                });
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDAO = new TaskDAO(getContext());
                UserDAO userDAO = new UserDAO(getContext());
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("USERNAME", "");
                if (MyDatePicker.isDate1BeforeDate2(tvStart.getText().toString(), tvEnd.getText().toString())) {
                    task = new Task(
                            userDAO.getUserId(username).getId(),
                            0,
                            ed_name.getEditText().getText().toString(),
                            ed_content.getEditText().getText().toString(),
                            tvStart.getText().toString(),
                            tvEnd.getText().toString()
                    );
                    long check = taskDAO.insert(task);
                    if (check > 0) {
                        Toast.makeText(getContext(), "Thêm hoạt động thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        sendNoti("Đã thêm thành công \""+task.getName()+"\"");
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Ngày bắt đầu phải trước Ngày kết thúc", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).switchFrag(new FragmentQLCongViec());
            }
        });
    }

    void sendNoti(String title){

        //Khai bao intent de nhan tuong tac khi bam vao notify
        Intent intentDemo = new Intent(getContext(), MainActivity.class);

        //gan co de intent hoat dong pham vi cao nhat
        intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addNextIntentWithParentStack(intentDemo);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Bitmap anh = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.task_noti);

        Notification customNotification = new NotificationCompat.Builder(
                getContext(), NotifyConfig.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(anh).bigLargeIcon(null))
                .setLargeIcon(anh)
                .setColor(Color.RED)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat
                =NotificationManagerCompat.from(getContext());

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED)
        {
            ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.POST_NOTIFICATIONS);
            return;
        }

        int id_notify = (int) new Date().getTime();

        notificationManagerCompat.notify(id_notify,customNotification);

    }

}


