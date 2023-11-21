package com.c2.arenafinder.ui.fragment.submain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.DataShared.KEY;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.UserModel;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.ImageUtil;
import com.c2.arenafinder.util.UsersUtil;

import java.io.IOException;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PICK_IMAGE = 1;

    private String mParam1;
    private String mParam2;
    private UsersUtil usersUtil;
    private DataShared dataShared;
    private Uri uri;

    private MaterialCardView cardPhoto;
    private Button btnBatal, btnSimpan;
    private TextView btnChoose;
    private EditText inpUsername, inpNama;
    private CircleImageView imgPhoto;

    public EditAccountFragment() {
        // Required empty public constructor
    }

    private void initViews(View view){
        cardPhoto = view.findViewById(R.id.edacc_card_photo);
        btnBatal = view.findViewById(R.id.edacc_batal);
        btnSimpan = view.findViewById(R.id.edacc_simpan);
        inpUsername = view.findViewById(R.id.edacc_inp_username);
        inpNama = view.findViewById(R.id.edacc_inp_name);
        imgPhoto = view.findViewById(R.id.edacc_photo);
        btnChoose = view.findViewById(R.id.edacc_choose_pp);
    }

    public static EditAccountFragment newInstance(String param1, String param2) {
        EditAccountFragment fragment = new EditAccountFragment();
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
        return inflater.inflate(R.layout.fragment_edit_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usersUtil = new UsersUtil(requireContext());
        dataShared = new DataShared(requireContext());
        initViews(view);

        Glide.with(requireActivity())
                .load(RetrofitClient.USER_PHOTO_URL + usersUtil.getUserPhoto())
                .placeholder(R.drawable.ic_profile)
                .into(imgPhoto);

        inpUsername.setText(usersUtil.getUsername());
        inpNama.setText(usersUtil.getFullName());

        onClickGroups();
        getAppbar();
    }

    private void getAppbar(){

        if (getActivity() != null){

            LinearLayout linear = getActivity().findViewById(R.id.sub_linear);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            ImageView imgBack = getActivity().findViewById(R.id.sub_back);

            linear.setVisibility(View.VISIBLE);

            txtTitle.setText(getString(R.string.item_mpr_edit_account));

            imgBack.setOnClickListener(v -> {
                requireActivity().onBackPressed();
            });

        }

    }

    private void choosePhoto() {
        // tambahkan user permission
        openGallery();
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
                            .placeholder(R.drawable.ic_profile)
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

    private void updateAccount(){

        RetrofitClient.getInstance().updateAccount(
                usersUtil.getEmail(), inpUsername.getText().toString(), inpNama.getText().toString()
        ).enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {

                if (response.body() != null && RetrofitClient.apakahSukses(response)){
                    // update data di preference
                    UserModel model = response.body().getData();
                    dataShared.setData(KEY.ACC_USERNAME, model.getUsername());
                    dataShared.setData(KEY.ACC_FULL_NAME, model.getNama());
                    dataShared.setData(KEY.ACC_PHOTO, model.getUserPhoto());

                    Toast.makeText(requireContext(), R.string.suc_account_edited, Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_MEDIUM);
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                uri = data.getData();

                Glide.with(requireContext())
                        .load(uri)
                        .placeholder(R.drawable.ic_profile)
                        .into(imgPhoto);
            }
        }
    }

    private void onClickGroups(){

        btnSimpan.setOnClickListener(v -> {
            if(uri != null) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String encoded = ImageUtil.bitmapToBase64String(bitmap, 100);
                uploadPhoto(encoded);
                updateAccount();
            }else{
                Toast.makeText(requireActivity(), "You must choose the image", Toast.LENGTH_SHORT).show();
            }
        });

        btnBatal.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        cardPhoto.setOnClickListener(v -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Button Choose Diclik");
            choosePhoto();
        });

        btnChoose.setOnClickListener(v -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Button Choose Diclik");
            choosePhoto();
        });

    }
}