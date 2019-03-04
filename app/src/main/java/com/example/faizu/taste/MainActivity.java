package com.example.faizu.taste;


import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        textViewSignin = (TextView)findViewById(R.id.textViewSignin);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.buttonRegister)
        {
            registerUser();
        }
        if(view==textViewSignin)
        {
            Toast.makeText(MainActivity.this,"Under Construction",Toast.LENGTH_SHORT).show();
        }
    }
    private void registerUser()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //checking the validity of the email
        if (email.isEmpty()) {
            editTextEmail.setError("Required");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email address");
            editTextEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if (password.isEmpty()) {
            editTextPassword.setError("Required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of a password should be 6");
            editTextPassword.requestFocus();
            return;
        }
        progressDialog.setMessage("Registering....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Registered Succeeded" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"User is already Registered",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
                    }
                }
                progressDialog.cancel();

            }
        });

    }

}
