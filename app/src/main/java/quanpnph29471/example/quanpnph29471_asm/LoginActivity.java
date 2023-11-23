package quanpnph29471.example.quanpnph29471_asm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import quanpnph29471.example.quanpnph29471_asm.DAO.UserDAO;
import quanpnph29471.example.quanpnph29471_asm.Model.User;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tv_register;

    TextInputLayout ed_username,ed_pass;

    String strUsername,strPass;

    UserDAO userDAO;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        tv_register = findViewById(R.id.tv_register);
        ed_username = findViewById(R.id.ed_username);
        ed_pass = findViewById(R.id.ed_password);

        checkAutoLogin();
        userDAO = new UserDAO(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });


        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void checkAutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "");
        String password = sharedPreferences.getString("PASSWORD", "");

        if (!username.isEmpty() && !password.isEmpty()) {
            ed_username.getEditText().setText(username);
            ed_pass.getEditText().setText(password);
        }
    }

    public void checkLogin() {
        strUsername = ed_username.getEditText().getText().toString();
        strPass = ed_pass.getEditText().getText().toString();
        if (strUsername.length() == 0) {
            ed_username.requestFocus();
            ed_username.setError("Vui lòng nhập tên đăng nhập");
        } else if (strPass.length() == 0) {
            ed_pass.requestFocus();
            ed_pass.setError("Vui lòng nhập mật khẩu");
        }else {
            if (userDAO.checkLogin(strUsername, strPass) > 0) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                rememberUser(strUsername, strPass);
                finish();
            } else {
                showLoginFailedDialog();
            }

        }
    }
    public void rememberUser(String u, String p) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("USERNAME", u);
        edit.putString("PASSWORD", p);
        edit.commit();
    }

    private void showLoginFailedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Đăng nhập không thành công bạn cần đăng ký sau đó đăng nhập lại");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}