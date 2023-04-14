package hugsforbugs.ecoalert;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    private EditText LogInEmailText, LogInPassText;
    private TextView SignupText, OrText;
    private ImageButton SponsorImgBttn, VoluntarImgBttn;
    private ImageView LogoImg;
    private Button LoginBttn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private void loginUser(){
        String email = LogInEmailText.getText().toString().trim();
        String password = LogInPassText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //mail is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        /**login user in firebase **/

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //start profile activity
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserInfo(user.getUid());
                        }
                        else{
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(MainActivity.this, "Could not log in... please try again"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

    public void UserInfo(final String userId){
        databaseReference =
                FirebaseDatabase.getInstance().getReference().child("Volunteers").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    // user exists
                    UserInformation mUser = dataSnapshot.getValue(UserInformation.class);
                    startActivity(new Intent(MainActivity.this, VoluntarActivity.class));
                    Toast.makeText(MainActivity.this, "****NOT FOUND****", Toast.LENGTH_LONG).show();
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
                    startActivity(new Intent(MainActivity.this, SponsorActivity.class));
                    Toast.makeText(MainActivity.this, "****NOT FOUND****", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_main);

        /** connect with firebase **/
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        /**verify if user is active **/
        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            UserInfo(user.getUid());
        }

        LogInEmailText = (EditText) findViewById(R.id.LogInEmailText);
        LogInPassText = (EditText) findViewById(R.id.LogInPassText);

        VoluntarImgBttn = (ImageButton) findViewById(R.id.VoluntarImgBttn);
        SponsorImgBttn = (ImageButton) findViewById(R.id.SponsorImgBttn);

        LogoImg = (ImageView) findViewById(R.id.LogoImg);

        SignupText = (TextView) findViewById(R.id.SignUpText);
        OrText = (TextView) findViewById(R.id.OrText);

        LoginBttn = (Button) findViewById(R.id.LoginBttn);

        /**sign up as volunteer**/
        VoluntarImgBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this,  VoluntarSignUp.class));
            }
        });
        /**sign up as sponsor**/
        SponsorImgBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, SponsorSignUp.class));
            }
        });
        /**login**/
        LoginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Example of a call to a native method

    }

}
