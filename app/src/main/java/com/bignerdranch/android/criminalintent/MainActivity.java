package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import com.bignerdranch.android.criminalintent.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        //final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final EditText input = (EditText) findViewById(R.id.editTextMessage);

        Button button = (Button) findViewById(R.id.buttonFirebaseStore);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String text = input.getText().toString();

                String message = "message";
                SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
                message += s.format(new Date());

                // Write a message to the database
                DatabaseReference firebaseRef = database.getReference(message);
                firebaseRef.setValue(text);

                // Read from the database
                firebaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);

                        // Do something in response to button click
                        final Context context = getApplicationContext();
                        //CharSequence text = "You Pressed the Stupid Button";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context,
                                value + " was written to Firebase",
                                duration);
                        toast.show();

                        Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });


    }
}
