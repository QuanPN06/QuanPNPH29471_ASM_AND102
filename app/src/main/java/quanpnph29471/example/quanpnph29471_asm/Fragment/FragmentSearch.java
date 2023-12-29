package quanpnph29471.example.quanpnph29471_asm.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import quanpnph29471.example.quanpnph29471_asm.Adapter.TaskAdapter;
import quanpnph29471.example.quanpnph29471_asm.DAO.TaskDAO;
import quanpnph29471.example.quanpnph29471_asm.MainActivity;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.MyDatePicker;
import quanpnph29471.example.quanpnph29471_asm.R;

public class FragmentSearch extends Fragment {
    RecyclerView rc;
    TaskAdapter taskAdapter;
    TaskDAO taskDAO;
    ArrayList<Task> list = new ArrayList<>();
    Button btn_search;
    TextView tvStart,tvEnd;
    ImageView imgStart,imgEnd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rc = view.findViewById(R.id.rc_search);
        btn_search = view.findViewById(R.id.btnSearch);
        tvStart = view.findViewById(R.id.tv_search_start);
        tvEnd = view.findViewById(R.id.tv_search_end);
        imgStart = view.findViewById(R.id.img_search_start);
        imgEnd = view.findViewById(R.id.img_search_end);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "");

        taskDAO = new TaskDAO(getContext());
        list = taskDAO.getList();

        taskAdapter = new TaskAdapter(list,getContext());
        rc.setAdapter(taskAdapter);

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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDAO taskDAO1 = new TaskDAO(getContext());
                if (MyDatePicker.isDate1BeforeDate2(tvStart.getText().toString(), tvEnd.getText().toString())) {
                    list = new ArrayList<>();
                    list = taskDAO1.searchByDate(tvStart.getText().toString(), tvEnd.getText().toString());

                    Toast.makeText(getContext(), "" + list.size(), Toast.LENGTH_SHORT).show();

                    taskAdapter = new TaskAdapter(list, getContext());
                    rc.setAdapter(taskAdapter);

                } else {
                    Toast.makeText(getContext(), "Ngày bắt đầu phải trước Ngày kết thúc", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void reloadData(){
        TaskDAO taskDAO1 = new TaskDAO(getContext());
        list = taskDAO1.getList();
        taskAdapter = new TaskAdapter(list,getContext());
        rc.setAdapter(taskAdapter);
    }

}


