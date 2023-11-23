package quanpnph29471.example.quanpnph29471_asm.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import quanpnph29471.example.quanpnph29471_asm.Adapter.TaskAdapter;
import quanpnph29471.example.quanpnph29471_asm.ClickDelItem;
import quanpnph29471.example.quanpnph29471_asm.ClickUpdateItem;
import quanpnph29471.example.quanpnph29471_asm.DAO.TaskDAO;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.R;

public class FragmentQLCongViec extends Fragment {
    RecyclerView rc ;
    TaskAdapter taskAdapter;
    TaskDAO taskDAO;
    ArrayList<Task> list = new ArrayList<>();
    
    FloatingActionButton float_btn;

    ImageButton btn_search;
    EditText ed_search;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_ly_cv,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rc = view.findViewById(R.id.recycler_view);
        float_btn = view.findViewById(R.id.floating_button);
        ed_search = view.findViewById(R.id.ed_fragment_ql_search);
        btn_search = view.findViewById(R.id.btn_fragment_ql_tapSearch);
        
        taskDAO = new TaskDAO(getContext());
        list =taskDAO.getList();

        taskAdapter = new TaskAdapter(list, new ClickDelItem() {
            @Override
            public void onClickDel(Task obj) {
                showDialogDelTask(obj);
            }
        }, new ClickUpdateItem() {
            @Override
            public void onClickUpdate(Task obj) {
                showDialogUpdateTask(obj);
            }
        });

        rc.setAdapter(taskAdapter);
        
        float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_search.length()>0){
                    String searchName = ed_search.getText().toString();
                    LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
                    rc.setLayoutManager(linearLayoutManager);
                    TaskDAO taskDAO1 = new TaskDAO(getContext());
                    list = new ArrayList<>();
                    list = taskDAO1.Search(searchName);
                    taskAdapter = new TaskAdapter(list, new ClickDelItem() {
                        @Override
                        public void onClickDel(Task obj) {
                            showDialogDelTask(obj);
                        }
                    }, new ClickUpdateItem() {
                        @Override
                        public void onClickUpdate(Task obj) {
                            showDialogUpdateTask(obj);
                        }
                    });
                    rc.setAdapter(taskAdapter);
                }else {

                }
            }
        });

    }

    private void showDialogUpdateTask(Task obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog_update_task,null);
        builder.setView(v);
        builder.setCancelable(false);

        AlertDialog dialog =builder.create();
        Button btn_del_dialog = v.findViewById(R.id.btn_update);
        Button btn_cancel=v.findViewById(R.id.btn_update_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogDelTask(Task obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog_del_task,null);
        builder.setView(v);
        builder.setCancelable(false);

        AlertDialog dialog =builder.create();//khởi tạo dialog
        //tương tác view
        TextView tv_name = v.findViewById(R.id.tv_del_name);
        Button btn_del_dialog = v.findViewById(R.id.btn_del_dialog);
        Button btn_cancel=v.findViewById(R.id.btn_cancel_del_dialog);
        tv_name.setText("Ban chac chan muon xoa: "+obj.getName());
        btn_del_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDAO dao1 = new TaskDAO(getContext());
                long check = dao1.delete(obj);
                if(check>0){
                    list.clear();
                    list.addAll(dao1.getList());
                    taskAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Xoa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();//tắt dialog
                }else {
                    Toast.makeText(getContext(), "Lỗi xoa", Toast.LENGTH_SHORT).show();
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
}


