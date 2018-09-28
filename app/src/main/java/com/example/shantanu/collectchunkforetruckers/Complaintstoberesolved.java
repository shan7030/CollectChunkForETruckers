package com.example.shantanu.collectchunkforetruckers;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


class detaileddescofcomplaints
{
    public String subject;
    public String Address;
    public String informeruid;
    public Double Latitude;
    public Double Longitude;
    public String datetime;
    public String extra;
    public int Cashback;

    detaileddescofcomplaints()
    {

    }
}
public class Complaintstoberesolved extends AppCompatActivity {
    ImageView img;
    TextView t1,t2,t3,t4,t5;
    Button b1,b2;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    detaileddescofcomplaints d;
    detaileddescofcomplaints ddc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaintstoberesolved);
        Log.v("Complaintstoberesolved", "" + Stringpasser.name);

            firebaseStorage = FirebaseStorage.getInstance();
            t1 = (TextView) findViewById(R.id.Dateandtime);
            t2 = (TextView) findViewById(R.id.Cash_back);
            t3 = (TextView) findViewById(R.id.amountofwaste);
            t4 = (TextView) findViewById(R.id.Address);
            t5 = (TextView) findViewById(R.id.Status_of_collection);
            img = (ImageView) findViewById(R.id.ivProfilePic);
        b1=(Button)findViewById(R.id.getdirections);
        b2=(Button)findViewById(R.id.completerequest);
        t1.setText("NO CURRENT ACTIVITY FOR YOU WAIT FOR THE ADMINISTRATOR  !!");
        t2.setVisibility(GONE);
        t3.setVisibility(GONE);
        t4.setVisibility(GONE);
        t5.setVisibility(GONE);
        b1.setVisibility(GONE);
        b2.setVisibility(GONE);
        firebaseStorage=FirebaseStorage.getInstance();

        Log.v("Complaintstoberesolved","ONE :"+Stringpasser.name);


            DatabaseReference drft=FirebaseDatabase.getInstance().getReference().child("Current_Etrucker_Visits/"+Stringpasser.name);
            drft.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                     Stringpasser.date=dataSnapshot.getValue(String.class);
                     Log.v("Complaintstoberesolved","THREE :"+Stringpasser.date);
                     Log.v("Complaintstoberesolved","FOUR :"+Stringpasser.name);



                     DatabaseReference dref=FirebaseDatabase.getInstance().getReference().child("Detail_description_of_Complaints/"+Stringpasser.date);
                     dref.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot1) {

                             ddc=dataSnapshot1.getValue(detaileddescofcomplaints.class);
                             if(ddc!=null)

                             {
                                if(!ddc.extra.equals("Yes")) {
                                    Log.v("Complaintstoberesolved", "ADDRESS :" + ddc.Address);
                                    t1.setText("Date amd Time of Request :" + ddc.datetime);
                                    t2.setText("Cash Back :" + ddc.Cashback);
                                    t3.setText("Amount of Waste :" + ddc.subject);
                                    t4.setText("" + ddc.Address);
                                    t5.setText("Status of Collection :" + ddc.extra);
                                    t2.setVisibility(VISIBLE);
                                    t3.setVisibility(VISIBLE);
                                    t4.setVisibility(VISIBLE);
                                    t5.setVisibility(VISIBLE);
                                    b1.setVisibility(VISIBLE);
                                    StorageReference storageReference = firebaseStorage.getReference();
                                    storageReference.child(ddc.informeruid).child("Images/" + ddc.datetime).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).fit().centerCrop().into(img);
                                        }
                                    });
                                    b1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            b2.setVisibility(VISIBLE);
                                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                    Uri.parse("http://maps.google.com/maps?daddr=" + ddc.Latitude + "," + ddc.Longitude));
                                            startActivity(intent);
                                        }
                                    });
                                }
                             }
                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                         }
                     });


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





                }











        public void completerequest(View view)
        {
            Intent intent = new Intent(Complaintstoberesolved.this, CompleteComplaint.class);
            startActivity(intent);
        }
    }

