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
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    EditText name,emailid,password,contact;
    Button register;
    TextView alreadyregisted;
    ProgressDialog rDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.editText);
        emailid = (EditText)findViewById(R.id.editText2);
        password = (EditText)findViewById(R.id.editText3);
        contact = (EditText)findViewById(R.id.editText4);

        register = (Button)findViewById(R.id.button2);

        alreadyregisted = (TextView)findViewById(R.id.textView);

        rDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String rName = name.getText().toString().trim();
                final String rEmail = emailid.getText().toString().trim();
                final String rPhn = contact.getText().toString().trim();
                final String rPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(rName)) {
                    name.setError("Enter your Name");
                    return;
                }
                if (TextUtils.isEmpty(rEmail)) {
                    emailid.setError("Enter email id");
                    return;
                }
                if (TextUtils.isEmpty(rPassword)) {
                    password.setError("Enter password");
                    return;
                }
                if (TextUtils.isEmpty(rPhn)) {
                    contact.setError("Enter your number");
                    return;
                }

                rDialog.setMessage("Registering.....");
                rDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(rEmail,rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final UserData userData = new UserData(rName,
                                    rEmail,
                                    rPhn);
                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Successfuly Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    rDialog.dismiss();
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            rDialog.dismiss();
                        }
                    }
                });
            }
        });

        alreadyregisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
