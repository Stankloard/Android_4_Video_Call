 package com.stankloardindustries.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

 public class SignUpActivity extends AppCompatActivity {

     FirebaseAuth auth;
     FirebaseFirestore database;
     EditText email_2, password_2, username;
     Button signUp;
     TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        username = findViewById(R.id.username);
        email_2 = findViewById(R.id.emailBox_signup);
        password_2 = findViewById(R.id.passwordBox_signup);
        signUp = findViewById(R.id.signupBtn);
        login = findViewById(R.id.login_signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginAcitivity.class));
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, name;
                email = email_2.getText().toString();
                pass = password_2.getText().toString();
                name = username.getText().toString();

                User user = new User(name, email, pass);

                if(!email.isEmpty() && !pass.isEmpty() && !name.isEmpty()){
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                database.collection("User")
                                        .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(SignUpActivity.this, LoginAcitivity.class));
                                        finish();
                                    }
                                });
//                            Toast.makeText(SignUpActivity.this, "Account is Created", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Please Enter all the Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}