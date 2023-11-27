package quanpnph29471.example.quanpnph29471_asm.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import quanpnph29471.example.quanpnph29471_asm.ClickDelItem;

import quanpnph29471.example.quanpnph29471_asm.ClickUpdateItem;
import quanpnph29471.example.quanpnph29471_asm.DAO.TaskDAO;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.MyDatePicker;
import quanpnph29471.example.quanpnph29471_asm.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    ArrayList<Task> list;
    Context context;
    ClickDelItem clickDelItem;



    public TaskAdapter(ArrayList<Task> list,  Context context) {
        this.list = list;
        this.clickDelItem = clickDelItem;
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
        TextView tv_start = v.findViewById(R.id.tv_update_start);
        TextView tv_end = v.findViewById(R.id.tv_update_end);
        ImageView img_start = v.findViewById(R.id.img_update_start);
        ImageView img_end = v.findViewById(R.id.img_update_end);

        ed_content.getEditText().setText(obj.getContent());
        ed_name.getEditText().setText(obj.getName());
        tv_end.setText(obj.getEnd());
        tv_start.setText(obj.getStart());

        Button btnUpdate = v.findViewById(R.id.btn_update);
        Button btn_cancel = v.findViewById(R.id.btn_update_cancel);

        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatePicker.showDatePicker(context, new MyDatePicker.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_start.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year) ;
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
                        tv_end.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year) ;
                    }
                });

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj.setName(ed_name.getEditText().getText().toString());
                obj.setContent(ed_content.getEditText().getText().toString());
                obj.setStart(tv_start.getText().toString());
                obj.setEnd(tv_end.getText().toString());

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
}
