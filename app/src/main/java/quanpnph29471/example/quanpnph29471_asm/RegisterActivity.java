package quanpnph29471.example.quanpnph29471_asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import quanpnph29471.example.quanpnph29471_asm.DAO.UserDAO;
import quanpnph29471.example.quanpnph29471_asm.Model.User;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout ed_username,ed_fullname,ed_email,ed_pass,ed_repass;
    Button btnRegister;
    UserDAO userDAO;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_username = findViewById(R.id.ed_username_regis);
        ed_fullname = findViewById(R.id.ed_fullname_regis);
        ed_email = findViewById(R.id.ed_email_regis);
        ed_pass = findViewById(R.id.ed_pass_regis);
        ed_repass = findViewById(R.id.ed_re_pass_regis);
        btnRegister = findViewById(R.id.btnRegis);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO = new UserDAO(RegisterActivity.this);
                
                String username = ed_username.getEditText().getText().toString();
                String fullname = ed_fullname.getEditText().getText().toString();
                String email = ed_email.getEditText().getText().toString();
                String pass = ed_pass.getEditText().getText().toString();
                String re_pass = ed_repass.getEditText().getText().toString();
                
                
                if(re_pass.equals(pass)==false){
                    Toast.makeText(RegisterActivity.this, "Mat khau khong khop", Toast.LENGTH_SHORT).show();
                }else {
                    user = new User(username,pass,email,fullname);
                    long check = userDAO.insert(user);
                    if(check>0){
                        Toast.makeText(RegisterActivity.this, "Chúc mừng bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                        rememberUser(username,pass);
                    }else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void rememberUser(String u, String p) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("USERNAME", u);
        edit.putString("PASSWORD", p);
        edit.commit();
    }
}