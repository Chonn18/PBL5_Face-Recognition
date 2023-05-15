package com.project.pbl5_mobile.View;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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


public class CameraFragment extends Fragment {

    private FragmentCameraBinding binding;
    int REQUEST_CODE = 1234;
    StorageReference storageReference;
    private FirebaseDatabase mDatabase;
    Uri imgUri;

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



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://pbl5-c253c.appspot.com/check/");

        binding.btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE);
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

//                Socket socket = null;
//                DataOutputStream dos = null;
//                try {
//                    socket = new Socket("192.168.43.209", 9999);
//                    dos = new DataOutputStream(socket.getOutputStream());
//                    dos.write(data);
//                    dos.close();
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                new NetworkTask().execute();

                // đọc ảnh từ tệp và gửi tới Server Python
//                File file = new File("C:\\Users\\Bao Quoc\\eclipse-workspace\\tmp2\\src\\tmp2\\tmp2.jpg");
//                FileInputStream fis = new FileInputStream(file);
//                byte[] buffer = new byte[4096];
//                int bytesRead = 0;
//                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                while ((bytesRead = fis.read(buffer)) != -1) {
//                    dos.write(buffer, 0, bytesRead);
//                }

                // đóng các tài nguyên
//                fis.close();





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
                                mDatabase.getReference("check").setValue(true);
                                CheckNow userCheck = new CheckNow(url,time);
                                mDatabase.getReference("CheckNow").setValue(userCheck);
//                                Navigation.findNavController(view).navigate(R.id.historyCheckFragment);
                            }
                        });
//                        Task<Uri> uri = mountainsRef.getDownloadUrl();

                    }
                });

            }


        });

    }

    private class NetworkTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String s = "check";
                byte[] data = s.getBytes(StandardCharsets.UTF_8);
                Socket socket = new Socket("10.10.30.11", 9999);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.write(data);
                dos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            binding.imgCamera.setImageBitmap(photo);
//            Picasso.get().load(String.valueOf(photo)).into(binding.imgCamera);
            imgUri = data.getData();
        }
        else{
            Toast.makeText(getContext(),"Failed !!!",
                    Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    public String getTime()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentTime = sdf.format(calendar.getTime());
        return currentTime;
    }
}



//    // Trong onClick()
//    public void onClick(View view) {
//        // Thực hiện các hoạt động chuẩn bị trước
//
//        // Gọi AsyncTask để thực hiện hoạt động mạng
//        MyAsyncTask myAsyncTask = new MyAsyncTask();
//        myAsyncTask.execute();
//    }
