package com.eca.aishu.trafichack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText iemail, ipassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FirebaseApp.initializeApp(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
        {

            startActivity(new Intent(Login.this,MainActivity.class));
            finish();

        }
        setContentView(R.layout.activity_login);

        iemail = findViewById(R.id.email_login);
        ipassword = findViewById(R.id.password_login);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.signup_button);
        btnReset = findViewById(R.id.resetButton);
        btnLogin = findViewById(R.id.login_button);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Reset_pass.class));
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,SignUp.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btnLogin.setVisibility(View.INVISIBLE);
                String email = iemail.getText().toString();
                final String password = ipassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        ipassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(Login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        //btnSignUp.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
