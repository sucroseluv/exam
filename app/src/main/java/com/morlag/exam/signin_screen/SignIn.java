package com.morlag.exam.signin_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.morlag.exam.R;
import com.morlag.exam.common.Api;
import com.morlag.exam.common.Callback;
import com.morlag.exam.common.MainMenu;
import com.morlag.exam.common.MessageDialog;
import com.morlag.exam.signup_screen.SignUp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {
    TextView email;
    TextView password;
    Button signIn;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.login);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signin);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    Map<String,String> data = new HashMap<>();
                    data.put("email",email.getText().toString());
                    data.put("password",password.getText().toString());

                    new Api().login(data, SignIn.this, getSupportFragmentManager(), new Callback<String>() {
                        @Override
                        public void run(String token) {
                            getSharedPreferences("prefs",MODE_PRIVATE).edit().putString("token",token).apply();
                            startActivity(new Intent(SignIn.this,MainMenu.class));
                            finish();
                        }
                    });
                }
            }
        });

    }

    private boolean validation(){
        if(email.getText().toString().equals("")){
            new MessageDialog("Введите почту",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }
        if(password.getText().toString().equals("")){
            new MessageDialog("Введите пароль",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }

        return true;
    }
}