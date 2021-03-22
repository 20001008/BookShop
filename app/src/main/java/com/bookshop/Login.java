package com.bookshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bookshop.tool.*;

public class Login extends AppCompatActivity {
    private EditText editText_user;
    private EditText editText_pwd;
    private Button button_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_user=findViewById(R.id.login_user);
        editText_pwd=findViewById(R.id.login_pwd);
        button_login=findViewById(R.id.login_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (webapi.login(editText_user.getText().toString(),editText_pwd.getText().toString()))
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this,"登录成功！",Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }
                }).start();

            }
        });
    }
}