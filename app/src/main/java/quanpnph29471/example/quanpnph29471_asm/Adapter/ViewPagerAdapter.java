package quanpnph29471.example.quanpnph29471_asm.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import quanpnph29471.example.quanpnph29471_asm.Fragment.FragThongKe_DangLam;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragThongKe_HoanThanh;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragThongKe_MoiTao;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragThongKe_TodayTask;

public class ViewPagerAdapter extends FragmentStateAdapter {
    FragThongKe_MoiTao frMoiTao;
    FragThongKe_DangLam frDangLam;
    FragThongKe_HoanThanh frHoanThanh;
    FragThongKe_TodayTask frToday;

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        frMoiTao = new FragThongKe_MoiTao();
        frDangLam = new FragThongKe_DangLam();
        frHoanThanh = new FragThongKe_HoanThanh();
        frToday = new FragThongKe_TodayTask();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return frToday;
            case 1:
                return frMoiTao;
            case 2:
                return frDangLam;
            case 3:
                return frHoanThanh;
            default:
                return frToday;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
