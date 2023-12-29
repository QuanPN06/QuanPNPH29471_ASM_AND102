package quanpnph29471.example.quanpnph29471_asm.Adapter;





import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;

import quanpnph29471.example.quanpnph29471_asm.DAO.TaskDAO;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragmentQLCongViec;
import quanpnph29471.example.quanpnph29471_asm.LoginActivity;
import quanpnph29471.example.quanpnph29471_asm.MainActivity;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.MyDatePicker;
import quanpnph29471.example.quanpnph29471_asm.NotifyConfig;
import quanpnph29471.example.quanpnph29471_asm.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    ArrayList<Task> list;
    Context context;

    public TaskAdapter(ArrayList<Task> list,  Context context) {
        this.list = list;

        this.context=context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_task,parent,false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task obj = list.get(position);
        holder.tv_name.setText(obj.getName());
        holder.tv_content.setText(obj.getContent());
        holder.tv_status.setText("Trạng thái: "+obj.getStatus1(obj.getStatus()));
        holder.tv_start.setText("Ngày bắt đầu: "+obj.getStart());
        holder.tv_end.setText("Ngày kết thúc: "+obj.getEnd());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(obj);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialogDelTask(obj);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_name;
        TextView tv_content;
        TextView tv_status;
        TextView tv_start;
        TextView tv_end;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_row);
            tv_name = itemView.findViewById(R.id.row_tv_name);
            tv_content = itemView.findViewById(R.id.row_tv_content);
            tv_status = itemView.findViewById(R.id.row_tv_status);
            tv_start = itemView.findViewById(R.id.row_tv_start);
            tv_end = itemView.findViewById(R.id.row_tv_end);
        }
    }

    private void showDialogUpdate(Task obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_dialog_update_task, null);
        builder.setView(v);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();

        TextInputLayout ed_name = v.findViewById(R.id.ed_update_name);
        TextInputLayout ed_content = v.findViewById(R.id.ed_update_content);
        TextView tvStart = v.findViewById(R.id.tv_update_start);
        TextView tvEnd = v.findViewById(R.id.tv_update_end);
        ImageView img_start = v.findViewById(R.id.img_update_start);
        ImageView img_end = v.findViewById(R.id.img_update_end);

        ed_content.getEditText().setText(obj.getContent());
        ed_name.getEditText().setText(obj.getName());
        tvEnd.setText(obj.getEnd());
        tvStart.setText(obj.getStart());

        Button btnUpdate = v.findViewById(R.id.btn_update);
        Button btn_cancel = v.findViewById(R.id.btn_update_cancel);


        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatePicker.showDatePicker(context, new MyDatePicker.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = monthOfYear < 9 ? "0"+(monthOfYear+1):(monthOfYear+1)+"";
                        String day = dayOfMonth < 10 ? ("0"+dayOfMonth):(dayOfMonth+"");

                        tvStart.setText(year + "/" + month + "/" +day);
                    }
                });
            }
        });

        img_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatePicker.showDatePicker(context, new MyDatePicker.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = monthOfYear < 9 ? "0"+(monthOfYear+1):(monthOfYear+1)+"";
                        String day = dayOfMonth < 10 ? ("0"+dayOfMonth):(dayOfMonth+"");

                        tvEnd.setText(year + "/" + month + "/" +day);
                    }
                });

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startDate = tvStart.getText().toString();
                String endDate = tvEnd.getText().toString();
                String current = new MyDatePicker().getCurrentDate();

                if(MyDatePicker.isDate1BeforeDate2(startDate,endDate)){
                    int status = MyDatePicker.getStatusByDate(current,startDate,endDate);

                    obj.setName(ed_name.getEditText().getText().toString());
                    obj.setContent(ed_content.getEditText().getText().toString());
                    obj.setStart(startDate);
                    obj.setEnd(endDate);
                    obj.setStatus(status);

                    TaskDAO dao1 = new TaskDAO(context);
                    int check = dao1.update(obj);
                    if (check > 0) {
                        list.clear();
                        list.addAll(dao1.getList());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Ngày bắt đầu phải trước Ngày kết thúc", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogDelTask(Task obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Xác nhận !!!");

        builder.setMessage("Chuyển \"" + obj.getName()+ "\" vào thùng rác");

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TaskDAO dao1 = new TaskDAO(context);
                obj.setStatus(-1);
                long check = dao1.update(obj);
                if (check > 0) {
                    list.clear();
                    list.addAll(dao1.getList());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã chuyển vào thùng rác ", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                    sendNoti("Đã chuyển \""+ obj.getName() +"\"vào thùng rác");
                } else {
                    Toast.makeText(context, "Lỗi xoa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialogdialog = builder.create();
        alertDialogdialog.show();
    }

    void sendNoti(String title){
        //Khai bao intent de nhan tuong tac khi bam vao notify
        Intent intentDemo = new Intent(context, MainActivity.class);

        //gan co de intent hoat dong pham vi cao nhat
        intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intentDemo);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Bitmap anh = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash);

        Notification customNotification = new NotificationCompat.Builder(
                context, NotifyConfig.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(anh).bigLargeIcon(null))
                .setLargeIcon(anh)
                .setColor(Color.RED)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat
                =NotificationManagerCompat.from(context);

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED)
        {
            ContextCompat.checkSelfPermission(context,
                    Manifest.permission.POST_NOTIFICATIONS);
            return;
        }

        int id_notify = (int) new Date().getTime();

        notificationManagerCompat.notify(id_notify,customNotification);
    }

}
