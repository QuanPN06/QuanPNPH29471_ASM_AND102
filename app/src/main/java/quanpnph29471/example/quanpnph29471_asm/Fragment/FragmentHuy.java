package quanpnph29471.example.quanpnph29471_asm.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import quanpnph29471.example.quanpnph29471_asm.Adapter.TaskCancelAdapter;
import quanpnph29471.example.quanpnph29471_asm.ClickDelItem;
import quanpnph29471.example.quanpnph29471_asm.ClickUpdateItem;
import quanpnph29471.example.quanpnph29471_asm.DAO.TaskDAO;
import quanpnph29471.example.quanpnph29471_asm.MainActivity;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.R;

public class FragmentHuy extends Fragment {
    RecyclerView rc;
    TaskCancelAdapter taskAdapter;
    TaskDAO taskDAO;
    ArrayList<Task> list = new ArrayList<>();
    ImageButton btn_search;
    EditText ed_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_huy_cv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rc = view.findViewById(R.id.recycler_view);
        ed_search = view.findViewById(R.id.ed_fragment_ql_search);
        btn_search = view.findViewById(R.id.btn_fragment_ql_tapSearch);

        taskDAO = new TaskDAO(getContext());
        list = taskDAO.getListCancel();

        taskAdapter = new TaskCancelAdapter(list, getContext());

        rc.setAdapter(taskAdapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_search.length() > 0) {
                    String searchName = ed_search.getText().toString();
                    LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
                    rc.setLayoutManager(linearLayoutManager);
                    TaskDAO taskDAO1 = new TaskDAO(getContext());
                    list = new ArrayList<>();
                    list = taskDAO1.SearchCancelTask(searchName);
                    taskAdapter = new TaskCancelAdapter(list, getContext());
                    rc.setAdapter(taskAdapter);
                } else {
                    TaskDAO taskDAO1 = new TaskDAO(getContext());
                    list = taskDAO1.getListCancel();
                    taskAdapter = new TaskCancelAdapter(list, getContext());
                    rc.setAdapter(taskAdapter);
                }
            }
        });
    }


}


