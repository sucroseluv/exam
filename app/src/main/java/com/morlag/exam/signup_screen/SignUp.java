package com.morlag.exam.signup_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.morlag.exam.R;
import com.morlag.exam.common.Api;
import com.morlag.exam.common.Callback;
import com.morlag.exam.common.MainMenu;
import com.morlag.exam.common.MessageDialog;
import com.morlag.exam.signin_screen.SignIn;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextView name;
    TextView lastname;
    TextView email;
    TextView password;
    TextView repeatPassword;
    Button register;
    Button signIn;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.repeatPassword);
        register = findViewById(R.id.register);
        signIn = findViewById(R.id.signin);
        api = new Api();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    Map<String,String> data = new HashMap<>();
                    data.put("email",email.getText().toString());
                    data.put("firstName",name.getText().toString());
                    data.put("password",password.getText().toString());
                    data.put("lastName",lastname.getText().toString());

                    api.register(data, SignUp.this, getSupportFragmentManager(),
                            new Callback<String>() {
                                @Override
                                public void run(String data) {
                                        Map<String,String> d = new HashMap<>();
                                    d.put("email",email.getText().toString());
                                    d.put("password",password.getText().toString());

                                    api.login(d, SignUp.this, getSupportFragmentManager(), new Callback<String>() {
                                        @Override
                                        public void run(String token) {
                                            getSharedPreferences("prefs",MODE_PRIVATE).edit().putString("token",token).apply();
                                            startActivity(new Intent(SignUp.this, MainMenu.class));
                                            finish();
                                        }
                                    });
                                }
                            }
                    );
                }
            }
        });
    }

    private boolean validation(){
        if(name.getText().toString().equals("")){
            new MessageDialog("Введите имя",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }
        if(lastname.getText().toString().equals("")){
            new MessageDialog("Введите фамилию",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }
        if(email.getText().toString().equals("")){
            new MessageDialog("Введите почту",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }
        Pattern p = Pattern.compile("[0-9a-z]+@[0-9a-z]+\\.[a-z]{1,3}");
        if(!p.matcher(email.getText()).matches()){
            new MessageDialog("Email должен соответствовать паттерну \"name@domenname.ru\", где имя и домен второго уровня могут состоять только из маленьких букв и цифр, домен верхнего уровня - только из маленьких букв. Длина домена верхнего уровня - не более 3х символов.",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }
        if(password.getText().toString().equals("")){
            new MessageDialog("Введите пароль",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }
        if(repeatPassword.getText().toString().equals("")){
            new MessageDialog("Повторите пароль",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }
        if(!password.getText().toString().equals(repeatPassword.getText().toString())){
            new MessageDialog("Пароли должны совпадать",this,null).show(getSupportFragmentManager(),"tag");
            return false;
        }

        return true;
    }
}