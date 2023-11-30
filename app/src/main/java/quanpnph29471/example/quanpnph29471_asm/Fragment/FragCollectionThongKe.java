package quanpnph29471.example.quanpnph29471_asm.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import quanpnph29471.example.quanpnph29471_asm.Adapter.ViewPagerAdapter;
import quanpnph29471.example.quanpnph29471_asm.R;

public class FragCollectionThongKe extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    ViewPagerAdapter viewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_collection_thong_ke, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ánh xạ:
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager2);

        //  tạo Adapter
        viewPagerAdapter = new ViewPagerAdapter(this);

        //gắn adapter cho viewpager
        viewPager2.setAdapter( viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){
                    case 0:
                        tab.setText("MỚI TẠO");
                        break;
                    case 1:
                        tab.setText("ĐANG LÀM");
                        break;
                    case 2:
                        tab.setText("HOÀN THÀNH");
                        break;
                }
            }
        }).attach();

    }

}
