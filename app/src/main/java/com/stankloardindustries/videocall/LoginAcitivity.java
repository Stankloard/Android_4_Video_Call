package com.stankloardindustries.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.protobuf.Empty;

public class LoginAcitivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email_1, password_1;
    Button login;
    TextView signup;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");

        email_1 = findViewById(R.id.emailBox_login);
        password_1 = findViewById(R.id.passwordBox_login);
        login = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.signup_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email, pass;
                email = email_1.getText().toString();
                pass = password_1.getText().toString();

                if(!email.isEmpty() && !pass.isEmpty()){
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(LoginAcitivity.this, "LogIn Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginAcitivity.this, DashBoardScreen.class));
                                finish();
                            }
                            else {
                                Toast.makeText(LoginAcitivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginAcitivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAcitivity.this, SignUpActivity.class));
                finish();
            }
        });
    }
}