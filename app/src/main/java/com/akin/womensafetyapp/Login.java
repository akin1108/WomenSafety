package com.akin.womensafetyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
git
public class Login extends AppCompatActivity {

    EditText email, pwd;
    Button login;
    TextView notRegistered;
    FirebaseAuth lAuth;
    ProgressDialog lDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.editText5);
        pwd = (EditText)findViewById(R.id.editText6);
        login = (Button)findViewById(R.id.button3);
        notRegistered = (TextView)findViewById(R.id.textView2);

        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        lDialog = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String lEmail = email.getText().toString().trim();
                final String lPwd =  pwd.getText().toString().trim();

                if(TextUtils.isEmpty(lEmail)){
                    email.setError("Enter email id");
                    return;
                }
                if(TextUtils.isEmpty(lPwd)){
                    pwd.setError("Enter password");
                    return;
                }

                lDialog.setMessage("Logging you in...");
                lDialog.show();

                lAuth.signInWithEmailAndPassword(lEmail,lPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Logged in Successfuly",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MapActivity.class));
                            lDialog.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                            lDialog.dismiss();
                        }
                    }
                });
            }

        });
    }
}
