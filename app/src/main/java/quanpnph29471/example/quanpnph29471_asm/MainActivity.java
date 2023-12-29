package quanpnph29471.example.quanpnph29471_asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import quanpnph29471.example.quanpnph29471_asm.Fragment.FragCollectionThongKe;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragmentGioiThieu;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragmentHuy;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragmentLogout;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragmentQLCongViec;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragmentSearch;
import quanpnph29471.example.quanpnph29471_asm.Fragment.FragmentThem;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView =findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.mtoolbar001);

        //gan toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //viet code gan su kien tuong tac dong mo menu
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.open,R.string.close
        );
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_01,new FragmentQLCongViec())
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() ==R.id.home){
                    toolbar.setTitle("Quản lý công việc");
                    fragment = new FragmentQLCongViec();
                }else if(item.getItemId() ==R.id.introduce){
                    toolbar.setTitle("Giới thiệu");
                    fragment = new FragmentGioiThieu();
                }else if(item.getItemId() ==R.id.add_task){
                    toolbar.setTitle("Thêm công việc mới");
                    fragment = new FragmentThem();
                }else if(item.getItemId() ==R.id.task_canceled){
                    toolbar.setTitle("Công việc bị hủy");
                    fragment = new FragmentHuy();
                }else if(item.getItemId() ==R.id.logout){
                    toolbar.setTitle("Đăng xuất");
                    fragment = new FragmentLogout();
                }else if(item.getItemId() ==R.id.thong_ke){
                    toolbar.setTitle("Thống kê");
                    fragment = new FragCollectionThongKe();
                }else if(item.getItemId() ==R.id.search){
                    toolbar.setTitle("Thống kê");
                    fragment = new FragmentSearch();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_01,fragment)
                        .commit();
                drawerLayout.close();
                return true;
            }
        });
    }

    public void switchFrag(Fragment fm) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_01, fm)
                .addToBackStack(null)  // Nếu bạn muốn thêm vào Back Stack để có thể quay lại FragmentA
                .commit();
    }


}