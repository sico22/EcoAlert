package hugsforbugs.ecoalert;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VoluntarSignUp extends AppCompatActivity {

    private String id;
    int i;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference,databaseReference;

    public void UserInfo(final String userId){
        databaseReference =
                FirebaseDatabase.getInstance().getReference().child("Volunteers").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    // user exists
                    UserInformation mUser = dataSnapshot.getValue(UserInformation.class);
                    startActivity(new Intent(VoluntarSignUp.this, VoluntarActivity.class));
                    Toast.makeText(VoluntarSignUp.this, "****NOT FOUND****", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference =
                FirebaseDatabase.getInstance().getReference().child("Sponsors").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    // user exists
                    SponsorInformation mUser = dataSnapshot.getValue(SponsorInformation.class);
                    startActivity(new Intent(VoluntarSignUp.this, SponsorActivity.class));
                    Toast.makeText(VoluntarSignUp.this, "****NOT FOUND****", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntar_sign_up);

        /**connect with firebase**/
        firebaseAuth = FirebaseAuth.getInstance();

        /**verify if user is active**/
        if(firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            UserInfo(user.getUid());
        }

        /*dbReference = FirebaseDatabase.getInstance().getReference().child("Volunteers");
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserInformation user = snapshot.getValue(UserInformation.class);

                        id = snapshot.getKey();

                        if(i == 1){
                            userInformations.add(user);
                            userKeys.add(id);
                            adapter.notifyDataSetChanged();
                        }
                        showProgressDialog();

                    }
                }
                hideProgressDialog();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
            }
        });*/
    }
}
