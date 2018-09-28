package com.example.shantanu.collectchunkforetruckers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



class DriverInfo
{
    public String Wardname;
    public String Firstname;
    public String Lastname;
    public String age;
    public String driverid;
    public String address;
    public String contactno;
    public String busy;
    DriverInfo()
    {

    }
}
public class CompleteComplaint extends AppCompatActivity {

    EditText e1;
    int flag=0;
    String wardname;
    DriverInfo driverInfo=new DriverInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_complaint);
    e1=(EditText)findViewById(R.id.newsubject);
    }

    public void completecomplaint(View view)
    {
    Log.v("CompleteComplaint",""+Stringpasser.date);


      FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser.date+"/extra").setValue("Yes");
      FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser.date+"/subject").setValue(e1.getText().toString().trim());

        FirebaseDatabase.getInstance().getReference().child("Current_Etrucker_Visits/"+Stringpasser.name).removeValue();



                DatabaseReference drf=FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information");
                drf.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot21) {

                        for(DataSnapshot dsf:dataSnapshot21.getChildren())
                        {

                                     wardname=dsf.getKey();
                                    for(DataSnapshot drf:dsf.getChildren()) {
                                        String key = drf.getKey();
                                       if(key.equals(Stringpasser.name)) {
                                           Log.v("CompleteComplaint", "KEY ::" + key);


                                            FirebaseDatabase.getInstance().getReference().child("Wardwise_drivers_Information/"+wardname+"/"+key+"/busy").setValue("No");
                                            flag=1;

                                            Intent intent = new Intent(CompleteComplaint.this, Complaintstoberesolved.class);
                                            startActivity(intent);
                                            return;

                                        }
                                    }
                                    if(flag==1)
                                    {
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
