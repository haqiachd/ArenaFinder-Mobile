package com.c2.arenafinder.ui.fragment.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.UserModel;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.ui.activity.AccountActivity;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.ImageUtil;
import com.c2.arenafinder.util.UsersUtil;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    private static final String TYPE_1 = "multipart";
    private static final String TYPE_2 = "base64";

    private Uri uri;

    private String mParam1;
    private String mParam2;

    private UsersUtil usersUtil;
    private Button btnLogout;
    private TextView txtUsername, txtEmail, txtName, txtLevel, btnChoose, btnUpload;
    private CircleImageView imgPhoto;

    private void initViews(View view){
        btnLogout = view.findViewById(R.id.mpr_btn_logout);
        txtUsername = view.findViewById(R.id.mpr_txt_username);
        txtEmail = view.findViewById(R.id.mpr_txt_email);
        txtName = view.findViewById(R.id.mpr_txt_nama);
        txtLevel = view.findViewById(R.id.mpr_txt_level);
        btnChoose = view.findViewById(R.id.mpr_choose_pp);
        btnUpload = view.findViewById(R.id.mpr_upload_pp);
        imgPhoto = view.findViewById(R.id.mpr_img_photo);
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        usersUtil = new UsersUtil(requireContext());

        txtUsername.setText(usersUtil.getUsername());
        txtEmail.setText(usersUtil.getEmail());
        txtName.setText(usersUtil.getFullName());
        txtLevel.setText(usersUtil.getLevel());

        Glide.with(requireActivity())
                .load(RetrofitClient.USER_PHOTO_URL + usersUtil.getUserPhoto())
                .into(imgPhoto);

        onClickGroups();
    }

    private void choosePhoto() {
//        if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(requireActivity(),
//                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    PERMISSION_REQUEST_STORAGE);
//
//            Toast.makeText(requireActivity(), "permission needed", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(requireActivity(), "permission granted", Toast.LENGTH_SHORT).show();
            openGallery();
//        }
//        checkAndRequestStoragePermission();
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    private void uploadPhoto(String imgBase64) {
        RetrofitClient.getInstance().uploadPhotoBase64(usersUtil.getEmail(), imgBase64).enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (RetrofitClient.apakahSukses(response)){
                    assert response.body() != null;
                    UserModel model = response.body().getData();
                    usersUtil.setUserPhoto(model.getUserPhoto());

                    Glide.with(requireContext())
                            .load(RetrofitClient.USER_PHOTO_URL + usersUtil.getUserPhoto())
                            .into(imgPhoto);

                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                ArenaFinder.playVibrator(requireActivity(), ArenaFinder.VIBRATOR_SHORT);
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_STORAGE: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    openGallery();
//                }
//
//                return;
//            }
//        }
//    }

    // Method to check and request permission if needed.
    private void checkAndRequestStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }else {
            Toast.makeText(requireActivity(), "Permission Denied :v", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                uri = data.getData();

                imgPhoto.setImageURI(uri);
            }
        }
    }

    private void onClickGroups(){

        btnLogout.setOnClickListener(v -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Logout Account");
            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
            // konfirmasi logout
            ArenaFinder.showAlertDialog(
                    requireContext(), getString(R.string.dia_title_confirm), getString(R.string.dia_msg_confirm_logout),
                    false,
                    (dialog, which) -> {
                        usersUtil.signOut();
                        if (!usersUtil.isSignIn()) {
                            Toast.makeText(requireContext(), "Logout Sukses", Toast.LENGTH_SHORT).show();
                            ArenaFinder.restartApplication(requireContext(), AccountActivity.class);
                        }
                    },
                    (dialog, which) -> {}
            );
        });

        btnChoose.setOnClickListener(v -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Button Choose Diclik");
            choosePhoto();
        });

        btnUpload.setOnClickListener(v -> {
            if(uri != null) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String encoded = ImageUtil.bitmapToBase64String(bitmap, 100);
                uploadPhoto(encoded);
            }else{
                Toast.makeText(requireActivity(), "You must choose the image", Toast.LENGTH_SHORT).show();
            }
        });

    }
}