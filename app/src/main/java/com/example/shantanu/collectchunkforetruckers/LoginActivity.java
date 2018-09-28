package com.example.shantanu.collectchunkforetruckers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    String adminname;
    String adminpass;
    EditText t1,t2;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void openthenext(View view)
    {
       t1=(EditText)findViewById(R.id.adminname);
      t2=(EditText) findViewById(R.id.adminpass);

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds1:dataSnapshot.getChildren())
                {
                    String key=ds1.getKey();
                   DatabaseReference dr2=FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information/"+key);
                   dr2.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot123) {

                           for (DataSnapshot dsf :dataSnapshot123.getChildren())
                           {
                               String h=dsf.getKey();
                               Log.v("LoginActivity",""+h);
                               adminname=t1.getText().toString().trim();
                               adminpass=t2.getText().toString().trim();
                               if(h.equals(adminname) && h.equals(adminpass))
                               {
                                   Stringpasser.name=h;
                                   flag=1;
                                   Intent intent = new Intent(LoginActivity.this, Complaintstoberesolved.class);
                                   startActivity(intent);
                                   break;
                               }


                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });

                }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
