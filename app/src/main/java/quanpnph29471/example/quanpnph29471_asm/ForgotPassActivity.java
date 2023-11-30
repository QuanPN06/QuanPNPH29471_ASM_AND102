package quanpnph29471.example.quanpnph29471_asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import quanpnph29471.example.quanpnph29471_asm.DAO.TaskDAO;
import quanpnph29471.example.quanpnph29471_asm.DAO.UserDAO;
import quanpnph29471.example.quanpnph29471_asm.Model.User;

public class ForgotPassActivity extends AppCompatActivity {
    TextInputLayout edname, edEmail, edPass, edRePass;
    Button btn_cancel, btnSubmit;

    UserDAO userDAO;

    User obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        edname = findViewById(R.id.ed_username_change_pass);
        edEmail = findViewById(R.id.ed_email_change_pass);
        edPass = findViewById(R.id.ed_pass_change_pass);
        edRePass = findViewById(R.id.ed_re_pass_change_repass);
        btn_cancel = findViewById(R.id.btn_change_huy);
        btnSubmit = findViewById(R.id.btn_change_pass);

        userDAO = new UserDAO(ForgotPassActivity.this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edname.getEditText().getText().toString();
                String email = edEmail.getEditText().getText().toString();

                if (name.isEmpty()) {
                    edname.requestFocus();
                    edname.setError("Vui lòng nhập tên đăng nhập");
                } else if (email.length() == 0) {
                    edEmail.requestFocus();
                    edEmail.setError("Vui lòng nhập email");
                }
                if (userDAO.checkChangePass(name, email) > 0) {
                    String pass = edPass.getEditText().getText().toString();
                    String re_pass = edRePass.getEditText().getText().toString();
                    if (pass.length() == 0) {
                        edPass.requestFocus();
                        edPass.setError("Vui lòng nhập mật khẩu mới");
                        return;
                    } else if (re_pass.length() == 0) {
                        edRePass.requestFocus();
                        edRePass.setError("Vui lòng nhập lại mật khẩu mới");
                        return;
                    }
                    if (re_pass.equals(pass)) {
                        obj = userDAO.getUserToChangePass(name, email);
//                        Toast.makeText(ForgotPassActivity.this, "" + obj.getUsername() + "/" + obj.getEmail(), Toast.LENGTH_SHORT).show();
                        Log.d("zzzzzz", "onClick: pass moi nhap la"+pass);
                        obj.setPassword(pass);
                        int check = userDAO.update(obj);
                        if (check > 0) {
                            Toast.makeText(ForgotPassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        } else {
                            Toast.makeText(ForgotPassActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ForgotPassActivity.this, "Re-Pass chưa khớp !!!", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(ForgotPassActivity.this, "Vui lòng nhập đúng Username và Email", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
            }
        });
    }


}