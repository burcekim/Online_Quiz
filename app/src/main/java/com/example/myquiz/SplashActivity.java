package com.example.myquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {
   private TextView appName;

   public static List<String> catList = new ArrayList<>();
   private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appName = findViewById(R.id.appName);

        firestore = FirebaseFirestore.getInstance();


        new Thread() {
            public void run() {

                   // sleep(3000);

                    loadData();

            }
        }.start();


    }
    private void loadData()
    {
        catList.clear();

        firestore.collection("QUIZ").document("DERSLER")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful())
                {
                    DocumentSnapshot doc = task.getResult();

                    if(doc.exists())
                    {
                        long count = (long)doc.get("COUNT");

                        for(int i=1; i <= count; i++)
                        {
                            String catName = doc.getString("CAT" + String.valueOf(i));

                            catList.add(catName);
                        }

                        Intent intent = new Intent(SplashActivity.this, loginActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }
                    else
                    {
                        Toast.makeText(SplashActivity.this,"Hiçbir Ders Belgesi Yok!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else
                    {
                        Toast.makeText(SplashActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
