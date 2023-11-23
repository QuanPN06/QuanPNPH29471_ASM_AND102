package quanpnph29471.example.quanpnph29471_asm.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import quanpnph29471.example.quanpnph29471_asm.Adapter.TaskAdapter;
import quanpnph29471.example.quanpnph29471_asm.ClickItem;
import quanpnph29471.example.quanpnph29471_asm.DAO.TaskDAO;
import quanpnph29471.example.quanpnph29471_asm.LoginActivity;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.R;
import quanpnph29471.example.quanpnph29471_asm.RegisterActivity;

public class FragmentThem extends Fragment {
    TextInputLayout ed_name,ed_content;
    ImageView imgStart, imgEnd;
    TextView tvStart, tvEnd;
    Button btnCancel,btnAdd;

    TaskDAO taskDAO;
    Task task;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_them_cv,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ed_name =view.findViewById(R.id.ed_add_name);
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
                openDialogStart();
            }
        });

        imgEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogEnd();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDAO = new TaskDAO(getContext());
                task = new Task(
                        0,
                        ed_name.getEditText().getText().toString(),
                        ed_content.getEditText().getText().toString(),
                        tvStart.getText().toString(),
                        tvEnd.getText().toString()
                        );
                long check = taskDAO.insert(task);
                if(check>0){
                    Toast.makeText(getContext(), "Thêm hoạt động thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openDialogEnd() {
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tvEnd.setText(String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year));
            }
        }, 2023, 11, 23);
        dialog.show();
    }

    private void openDialogStart() {
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                 tvStart.setText(String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year));
            }
        }, 2023, 11, 23);
        dialog.show();
    }

}


