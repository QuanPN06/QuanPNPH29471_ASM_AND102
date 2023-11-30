package quanpnph29471.example.quanpnph29471_asm.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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
import quanpnph29471.example.quanpnph29471_asm.R;

public class FragmentQLCongViec extends Fragment {
    RecyclerView rc;
    TaskAdapter taskAdapter;
    TaskDAO taskDAO;
    ArrayList<Task> list = new ArrayList<>();

    FloatingActionButton float_btn;

    ImageButton btn_search;
    EditText ed_search;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_ly_cv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rc = view.findViewById(R.id.recycler_view);
        float_btn = view.findViewById(R.id.floating_button);
        ed_search = view.findViewById(R.id.ed_fragment_ql_search);
        btn_search = view.findViewById(R.id.btn_fragment_ql_tapSearch);

        taskDAO = new TaskDAO(getContext());
        list = taskDAO.getList();

        taskAdapter = new TaskAdapter(list,getContext());
        rc.setAdapter(taskAdapter);
        float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).switchFrag(new FragmentThem());
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_search.length() > 0) {
                    String searchName = ed_search.getText().toString();
                    TaskDAO taskDAO1 = new TaskDAO(getContext());
                    list = new ArrayList<>();
                    list = taskDAO1.Search(searchName);
                    taskAdapter = new TaskAdapter(list,getContext());
                    rc.setAdapter(taskAdapter);
                } else {
                    reloadData();
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


