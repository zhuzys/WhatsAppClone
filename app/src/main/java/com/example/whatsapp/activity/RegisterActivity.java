package com.example.whatsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText UserEmail, UserPassword;
    private TextView AlreadyHaveAccount, ForgetPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference RootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        RootReference = FirebaseDatabase.getInstance().getReference();
        init();
    }
    public void init () {
        registerButton = findViewById(R.id.register_button);
        UserEmail = findViewById(R.id.register_email);
        UserPassword = findViewById(R.id.register_password);
        AlreadyHaveAccount = findViewById(R.id.already_have_account);
        ForgetPassword = findViewById(R.id.forget_password);
        loadingBar = new ProgressDialog(this);
        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();


                /*String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please enter email or password...", Toast.LENGTH_SHORT).show();
                }
                else  {
                    loadingBar.setTitle("Creating new account");
                    loadingBar.setMessage("Please wait, while we are creating new account for you");
                    loadingBar.setCanceledOnTouchOutside(true);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Account created successfully...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else {
                                        String message = task.getException().toString();
                                        Toast.makeText(RegisterActivity.this, "Error :"+ message, Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }*/
            }
            // });


            private void CreateAccount() {
                String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please enter email or password!", Toast.LENGTH_LONG).show();

                } else {
                    loadingBar.setTitle("Создание акканута");
                    loadingBar.setMessage("Пожалуйста подождите");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    String currentUserID = mAuth.getCurrentUser().getUid();
                                    RootReference.child("Users").child(currentUserID).setValue("");

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    loadingBar.dismiss();

                                }
                                else {
                                    String message =task.getException().toString();

                                    Toast.makeText(RegisterActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                                    });


                   // connectDB(email, password);

                }
            }

            private void connectDB(String email, String password) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.child("Users").child(email).exists()) {
                            HashMap<String, Object> userData = new HashMap<>();
                            userData.put("email", email);
                            userData.put("password", password);
                            myRef.child("Users").child(email).updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);

                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this, "Почта" + email + "уже зарегистрирована", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}


