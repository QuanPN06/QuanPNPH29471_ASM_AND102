package quanpnph29471.example.quanpnph29471_asm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import quanpnph29471.example.quanpnph29471_asm.ClickDelItem;
import quanpnph29471.example.quanpnph29471_asm.ClickUpdateItem;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.R;

public class TaskCancelAdapter extends RecyclerView.Adapter<TaskCancelAdapter.TaskViewHolder>{
    ArrayList<Task> list;
    Context context;

    ClickDelItem clickDelItem;
    ClickUpdateItem clickUpdateItem;

    public TaskCancelAdapter(ArrayList<Task> list, ClickDelItem clickDelItem, ClickUpdateItem clickUpdateItem) {
        this.list = list;
        this.clickDelItem = clickDelItem;
        this.clickUpdateItem = clickUpdateItem;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_task_cancel,parent,false);
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickDelItem.onClickDel(obj);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), obj.getName()+"", Toast.LENGTH_SHORT).show();
                clickUpdateItem.onClickUpdate(obj);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{
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
}
