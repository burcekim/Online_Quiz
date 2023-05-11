package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotmypassword extends AppCompatActivity {
    TextView resmail;
    Button resetpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotmypassword);
        resetpassword=findViewById(R.id.resetpass);
        resmail=findViewById(R.id.resmail);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(resmail.getText().toString())
                        .addOnCompleteListener(forgotmypassword.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(forgotmypassword.this, "Şifreniz mail adresinize gönderildi!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(forgotmypassword.this, "Mail adresi kayıtlı değil!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
