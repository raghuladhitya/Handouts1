package com.example.handouts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    EditText t1;
    EditText t2;
    EditText t3;
    Button b1;
    Spinner s1;

    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        t1=(EditText) findViewById(R.id.editText);
        t2=(EditText) findViewById(R.id.editText2);
        t3=(EditText) findViewById(R.id.editText3);
        b1=(Button) findViewById(R.id.btnlogin);
        s1=(Spinner) findViewById(R.id.spinner);

        database=FirebaseDatabase.getInstance();
        ref=database.getReference();

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String spin=s1.getSelectedItem().toString();
                if(spin=="Student") {
                    addStudent();
                }

                else {
                    addAdmin();
                }
            }
        });
    }

    private void addStudent(){
        final String name=t1.getText().toString().trim();
        final String pass1=t2.getText().toString().trim();
        final String pass2=t3.getText().toString().trim();

        if(name.length()==0){
            t1.setError("Username is required");
        }

        else{
            if(isInternetConnection()){
                final ProgressDialog Dialog=new ProgressDialog(SignupActivity.this);
                Dialog.setMessage("Loading");
                Dialog.show();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(name)){
                            t1.setError("User name already exists");
                        }

                        else{
                            if(pass1.length()==0){
                                t2.setError("This field should not be empty");
                            }

                            else if(pass2.length()==0){
                                t3.setError("This field should not be empty");
                            }

                            else if(pass1.equals(pass2)){
                                Intent intent=new Intent(SignupActivity.this, SignupStudActivity.class);
                                intent.putExtra("child", name);
                                intent.putExtra("pass", pass1);
                                intent.putExtra("type", "Student");
                                startActivity(intent);
                            }

                            else{
                                t3.setError("Password is not matching");
                            }
                        }
                        Dialog.hide();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            else{
                Toast toast= Toast.makeText(SignupActivity.this, "Please check your internet connction", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED)
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    private void addAdmin(){
        final String name=t1.getText().toString().trim();
        final String pass1=t2.getText().toString().trim();
        final String pass2=t3.getText().toString().trim();

        if(name.length()==0){
            t1.setError("Username is required");
        }

        else{
            if(isInternetConnection()){
                final ProgressDialog Dialog=new ProgressDialog(SignupActivity.this);
                Dialog.setMessage("Loading");
                Dialog.show();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(name)){
                            t1.setError("User name already exists");
                        }

                        else{
                            if(pass1.length()==0){
                                t2.setError("This field should not be empty");
                            }

                            else if(pass2.length()==0){
                                t3.setError("This field should not be empty");
                            }

                            else if(pass1.equals(pass2)){
                                Intent intent=new Intent(SignupActivity.this, SignupAdminActivity.class);
                                intent.putExtra("child", name);
                                intent.putExtra("pass", pass1);
                                intent.putExtra("type", "Admin");
                                startActivity(intent);
                            }

                            else{
                                t3.setError("Password is not matching");
                            }
                        }
                        Dialog.hide();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            else{
                Toast toast= Toast.makeText(SignupActivity.this, "Please check your internet connction", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}