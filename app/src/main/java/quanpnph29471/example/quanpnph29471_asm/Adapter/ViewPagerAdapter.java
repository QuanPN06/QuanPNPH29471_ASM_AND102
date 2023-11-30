package quanpnph29471.example.quanpnph29471_asm.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import quanpnph29471.example.quanpnph29471_asm.Fragment.FragThongKe_DangLam;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragThongKe_HoanThanh;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragThongKe_MoiTao;

public class ViewPagerAdapter extends FragmentStateAdapter {
    FragThongKe_MoiTao frMoiTao;
    FragThongKe_DangLam frDangLam;
    FragThongKe_HoanThanh frHoanThanh;

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        frMoiTao = new FragThongKe_MoiTao();
        frDangLam = new FragThongKe_DangLam();
        frHoanThanh = new FragThongKe_HoanThanh();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return frMoiTao;
            case 1:
                return frDangLam;
            case 2:
                return frHoanThanh;
            default:
                return frMoiTao;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
