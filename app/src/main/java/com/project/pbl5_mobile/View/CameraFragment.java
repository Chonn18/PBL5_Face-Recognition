package com.project.pbl5_mobile.View;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.Manifest;

import com.google.android.gms.tasks.OnCompleteListener;
import com.project.pbl5_mobile.R; // Thay thế "appname" bằng tên gói ứng dụng của bạn

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.normal.TedPermission;
import com.project.pbl5_mobile.MainActivity;
import com.project.pbl5_mobile.Model.Entity.CheckNow;
import com.project.pbl5_mobile.Model.Entity.UserCheck;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.FragmentCameraBinding;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;



public class CameraFragment extends Fragment {

    private FragmentCameraBinding binding;
    int REQUEST_CODE = 1234;
    StorageReference storageReference;
    private FirebaseDatabase mDatabase;
    Uri imgUri;
    private static final int IMAGE_CAPTURE_CODE =1001 ;
    private static final int PERMISSION_CODE=1000;
    private int idContact=-1;
    Uri image_uri=null;

    public CameraFragment() {
    }


    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_camera, container , false);
        return binding.getRoot();
    }

    public String getdata(){
        return "1";
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference("check").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean isChecked = snapshot.getValue(Boolean.class);
                if(isChecked){
                    Navigation.findNavController(view).navigate(R.id.historyCheckFragment);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pbl5-c253c.appspot.com/check/");

        binding.btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(CameraFragment.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();

            }



        });


        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                String time = getTime();

                StorageReference mountainsRef = storageReference.child("image" +time+".png");


                binding.imgCamera.setDrawingCacheEnabled(true);
                binding.imgCamera.buildDrawingCache();
                Bitmap bitmap = binding.imgCamera.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();


                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Failed!!!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    String url;
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference()
                                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUrl) {
                                        url = downloadUrl.toString();
                                        // Handle URL here
                                        Toast.makeText(getContext(),"upload successful !!",
                                                Toast.LENGTH_SHORT).show();

                                        binding.imgCamera.setImageBitmap(null);

                                        mDatabase = FirebaseDatabase.getInstance();
//                                mDatabase.getReference("check").setValue(true);
                                        CheckNow userCheck = new CheckNow(url,time);
                                        mDatabase.getReference("CheckNow").setValue(userCheck).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Thread thread_tmp = new Thread(){
                                                    @Override
                                                    public void run(){
                                                        Socket socket = null;
                                                        DataOutputStream dos = null;
                                                        byte[] data = getdata().getBytes();
                                                        try {
                                                            socket = new Socket("172.20.10.3", 9999);
                                                            dos = new DataOutputStream(socket.getOutputStream());
                                                            dos.write(data);
                                                            dos.close();
                                                            socket.close();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                thread_tmp.start();
//                                                new NetworkTask().execute();
                                            }
                                        });
                                    }
                                });

//                        Thread thread_tmp = new Thread(){
//                            @Override
//                            public void run(){
//                                Socket socket = null;
//                                DataOutputStream dos = null;
//                                byte[] data = getdata().getBytes();
//                                try {
//                                    socket = new Socket("192.168.1.13", 9999);
//                                    dos = new DataOutputStream(socket.getOutputStream());
//                                    dos.write(data);
//                                    dos.close();
//                                    socket.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        };
//                        thread_tmp.start();
//                        new NetworkTask().execute();

                    }
                });


            }
        });

    }

//
//    private class NetworkTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                String s = "check";
//                byte[] data = s.getBytes(StandardCharsets.UTF_8);
//                Socket socket = new Socket("172.20.10.3", 9999);
//                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                dos.write(data);
//                dos.close();
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }



    private void openCamera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From camera");
        image_uri= requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(getContext(), "Permission denied..", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        image_uri = data.getData();

            binding.imgCamera.setImageURI(image_uri);

    }


    public String getTime()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentTime = sdf.format(calendar.getTime());
        return currentTime;
    }
}



