package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    //creating variables of the profile activity
    ImageView uimage;
    EditText uname,dob;
    Button btnupdate;
    //DOB variable
    Calendar myCalendar;
    //Firebase
    DatabaseReference dbreference;
    StorageReference storageReference;
    //Views
    TextView email;
    FirebaseUser user;
    Map<String,Object> map;
    //Variables
    Uri filepath;
    Bitmap bitmap;
    String UserID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Setting toolbar and adding views
        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, dashActivity.class));

            }
        });
        //binding views
        uimage=(ImageView)findViewById(R.id.uimage);
        uname=(EditText) findViewById(R.id.uname);
        dob=(EditText) findViewById(R.id.dob);
        email=(TextView)findViewById(R.id.email);
        btnupdate=(Button)findViewById(R.id.btnupdate);
        myCalendar = Calendar.getInstance();

        //DOB date Picker
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        //Setting fetching date data on click
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Firebase instance
        user= FirebaseAuth.getInstance().getCurrentUser();
        //Checking to get the ID
        if(user.getUid() == null){
            Toast.makeText(ProfileActivity.this, "Could not add data", Toast.LENGTH_SHORT).show();
        }
        UserID=user.getUid();
        email.setText(user.getEmail().toString());

        //daabase reference passed by URI and URL endpoint
        dbreference= FirebaseDatabase.getInstance("https://quizapp-6b183-default-rtdb.firebaseio.com/").getReference().child("users/");
        //storage instace to store all the images
        storageReference= FirebaseStorage.getInstance().getReference();
        uimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dexter library used to get the image from the file manager or storage of the mobile device
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                //getting image
                                Intent intent=new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Please Select File"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                //pass

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }

                        }).check();

            }
        });

        //onclick listener on button
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checking for null values

                                                if(uimage.getDrawable() == null){
                                    Toast.makeText(ProfileActivity.this, "Please Select an image.",Toast.LENGTH_SHORT).show();
                                }
                                else if(uname.getText().toString().equals("")){
                                    Toast.makeText(ProfileActivity.this, "Enter a name please.",Toast.LENGTH_SHORT).show();

                                }
                                else if(dob.getText().toString().isEmpty()){
                                                    Toast.makeText(ProfileActivity.this, "Enter Your DOB please.",Toast.LENGTH_SHORT).show();
                                                }
                                else{
                                    //if everything is fine then update the data
                                                    updateData();
                                }

            }
        });


    }

    //On activity opening, reading data from the firebase and adding to the views
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                uimage.setImageBitmap(bitmap);
            }catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    //Method to update the data

    public void updateData()
    {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("File Uploader");
        pd.show();

        final StorageReference uploader=storageReference.child("profileimages/"+"img"+System.currentTimeMillis());
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Map<String,Object> map=new HashMap<>();
                                map.put("uimage",uri.toString());
                                map.put("uname",uname.getText().toString());
                                map.put("dob", dob.getText().toString());

                                dbreference.child(UserID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists())
                                            dbreference.child(UserID).updateChildren(map);
                                        else
                                            dbreference.child(UserID).setValue(map);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded :"+(int)percent+"%");
                    }
                });

    }























//    public void updateData()
//    {
//        final ProgressDialog pd=new ProgressDialog(this);
//        pd.setTitle("File Uploader");
//        pd.show();
//
//        final StorageReference uploader=storageReference.child("profileimages/"+"img"+System.currentTimeMillis());
//        uploader.putFile(filepath)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//
//
//                                    try{
//                                        Map<String,Object> map=new HashMap<>();
//                                        map.put("uimage",uri.toString());
//                                        map.put("uname",uname.getText().toString());
//                                        map.put("dob", dob.getText().toString());
//
//                                        Log.d("Data URI", uri.toString());
//                                        Log.d("Data uname", uname.getText().toString());
//                                        Log.d("Data dob", dob.getText().toString());
//
//
//
//
//                                    }
//                                    catch (Exception e){
//                                        e.printStackTrace();
//                                        displayExceptionMessage(e.getMessage());
//                                    }
//
//
//
//
//                                dbreference.child(UserID).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        if(snapshot.exists()){
//                                            //dbreference.child(UserID).updateChildren(map);
//                                            dbreference.child(UserID).child("uname").setValue(uname.getText().toString());
//                                            dbreference.child(UserID).child("dob").setValue(dob.getText().toString());
//                                            dbreference.child(UserID).child("uimage").setValue(uri.toString());
//                                            dbreference.child("hello").setValue("Nothing");
//
//
//                                            Toast.makeText(ProfileActivity.this, "Uploaded Successfully.", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                        else{
//                                            dbreference.child(UserID).setValue(map);
//                                        }
//
//                                    }
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//                                    }
//                                });
//
//                                pd.dismiss();
//                                //Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
//                        pd.setMessage("Uploaded :"+(int)percent+"%");
//                    }
//                });
//
//    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        UserID=user.getUid();
        dbreference.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    uname.setText(snapshot.child("uname").getValue().toString());
                    dob.setText(snapshot.child("dob").getValue().toString());
                    Glide.with(getApplicationContext()).load(snapshot.child("uimage").getValue().toString()).into(uimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }







    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    public void displayExceptionMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void ResetPassword(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Email set to"+auth.getCurrentUser().getEmail().toString(),Toast.LENGTH_SHORT).show();
                        }
                        else{

                            Toast.makeText(ProfileActivity.this, "Failed to send a reset email.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}