package com.c2.arenafinder.ui.fragment.submain;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.api.retrofit.RetrofitState;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.DataShared.KEY;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.model.UserModel;
import com.c2.arenafinder.data.repository.UsersRepository;
import com.c2.arenafinder.data.response.UsersResponse;
import com.c2.arenafinder.di.UsersViewModelFactory;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.ImageUtil;
import com.c2.arenafinder.util.UsersUtil;

import java.io.IOException;

import com.bumptech.glide.Glide;
import com.c2.arenafinder.util.ValidatorUtil;
import com.c2.arenafinder.viewmodel.UsersViewModel;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PICK_FROM_GALLERY = 1, PICK_FROM_CAMERA = 2;

    private String mParam1;
    private String mParam2;

    private UsersViewModel usersViewModel;

    private UsersUtil usersUtil;
    private DataShared dataShared;
    private Uri uri;
    private Bitmap bitmap;

    private MaterialCardView cardPhoto;
    private MaterialButton btnBatal, btnSimpan;
    private TextView btnChoose, txtHelper;
    private EditText inpUsername, inpNama;
    private CircleImageView imgPhoto;

    public EditAccountFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        cardPhoto = view.findViewById(R.id.edacc_card_photo);
        btnBatal = view.findViewById(R.id.edacc_batal);
        btnSimpan = view.findViewById(R.id.edacc_simpan);
        inpUsername = view.findViewById(R.id.edacc_inp_username);
        inpNama = view.findViewById(R.id.edacc_inp_name);
        imgPhoto = view.findViewById(R.id.edacc_photo);
        btnChoose = view.findViewById(R.id.edacc_choose_pp);
        txtHelper = view.findViewById(R.id.edacc_txt_helper);
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

        usersViewModel = new ViewModelProvider(
                requireActivity(),
                new UsersViewModelFactory(new UsersRepository())
        ).get(UsersViewModel.class);

        Glide.with(requireActivity())
                .load(RetrofitClient.USER_PHOTO_URL + usersUtil.getUserPhoto())
                .placeholder(R.drawable.ic_profile)
                .into(imgPhoto);

        inpUsername.setText(usersUtil.getUsername());
        inpNama.setText(usersUtil.getFullName());

        onClickGroups();
        getAppbar();
        if (getView() != null) {
            observer();
        }

//        Toast.makeText(requireContext(), "Device : " + Build.BRAND, Toast.LENGTH_SHORT).show();
    }

    private void getAppbar() {

        if (getActivity() != null) {

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ArenaFinder.PERMISSION_STORAGE) {
            if (Build.BRAND.contains("samsung")) {
                openGallery();
                return;
            }
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(requireActivity(), R.string.toast_cannot_open_galery, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == ArenaFinder.PERMISSION_CAMERA) {
            if (Build.BRAND.contains("samsung")) {
                openCamera();
                return;
            }
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(requireActivity(), R.string.toast_cannot_open_camera, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.getData();

                Glide.with(requireContext())
                        .load(uri)
                        .placeholder(R.drawable.ic_profile)
                        .into(imgPhoto);
            }
        } else if (requestCode == PICK_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                bitmap = (Bitmap) data.getExtras().get("data");

                Glide.with(requireContext())
                        .load(bitmap)
                        .placeholder(R.drawable.ic_profile)
                        .into(imgPhoto);
            }
        }
    }

    private void observer() {
        usersViewModel.deletePhoto().observe(getViewLifecycleOwner(), dataState -> {
            if (dataState instanceof RetrofitState.Loading) {
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_LOADING, "DELETE ON LOADING");
            } else if (dataState instanceof RetrofitState.Error) {
                LogApp.error(requireContext(), LogTag.RETROFIT_ON_FAILURE, "DELETE ON FAILURE");
                Toast.makeText(requireContext(), ((RetrofitState.Error) dataState).getMessage(), Toast.LENGTH_SHORT).show();
            } else if (dataState instanceof RetrofitState.Success) {
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_RESPONSE, "DELETE ON RESPONSE");

                UserModel model = ((RetrofitState.Success<UsersResponse>) dataState).getData().getData();
                usersUtil.setUserPhoto(model.getUserPhoto());

                Glide.with(requireContext())
                        .load(RetrofitClient.USER_PHOTO_URL + usersUtil.getUserPhoto())
                        .placeholder(R.drawable.ic_profile)
                        .into(imgPhoto);
            }
        });
    }

    private void onClickGroups() {

        btnSimpan.setOnClickListener(v -> {
            ValidatorUtil validatorUtil = new ValidatorUtil(requireContext(), btnSimpan, txtHelper);

            if (!validatorUtil.isValidUsername(inpUsername.getText().toString())) {
                txtHelper.setText(validatorUtil.getMessage());
                txtHelper.setTextColor(ContextCompat.getColor(requireContext(), R.color.orangered));
            } else if (!validatorUtil.isValidName(inpNama.getText().toString())) {
                txtHelper.setText(validatorUtil.getMessage());
                txtHelper.setTextColor(ContextCompat.getColor(requireContext(), R.color.orangered));
            } else {
                txtHelper.setTextColor(ContextCompat.getColor(requireContext(), R.color.jade_green));
                txtHelper.setText("");
                if (uri != null || bitmap != null) {
                    if (bitmap == null) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    assert bitmap != null;
                    String encoded = ImageUtil.bitmapToBase64String(bitmap, 100);
                    uploadPhoto(encoded);
                    updateAccount();
                } else {
                    updateAccount();
                }
            }

        });

        btnBatal.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        cardPhoto.setOnClickListener(v -> {
            cardPhoto.setClickable(false);
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Button Choose Diclik");

            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
            BottomSheetDialog sheet = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);
            View sheetInflater = requireActivity().getLayoutInflater().inflate(R.layout.sheet_change_photo_profile, null);
            sheet.setContentView(sheetInflater);

            sheet.setOnDismissListener(d -> {
                cardPhoto.setClickable(true);
            });

            sheetInflater.findViewById(R.id.scpp_opsi_foto).setOnClickListener(foto -> {
                takePicture();
                sheet.dismiss();
            });

            sheetInflater.findViewById(R.id.scpp_opsi_galery).setOnClickListener(galery -> {
                choosePhoto();
                sheet.dismiss();
            });

            sheetInflater.findViewById(R.id.scpp_opsi_hapus).setOnClickListener(hapus -> {
                new AlertDialog.Builder(requireActivity())
                        .setTitle(R.string.dia_title_warning)
                        .setMessage(R.string.dia_confirm_delete_photo)
                        .setPositiveButton(R.string.dia_positive_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                usersViewModel.doDeletePhoto(usersUtil.getEmail());
                            }
                        })
                        .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {
                        })
                        .create().show();
                sheet.dismiss();
            });

            // show dialog
            sheet.show();
            sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            sheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnim;
            sheet.getWindow().setGravity(Gravity.BOTTOM);
        });

        btnChoose.setOnClickListener(v -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Button Choose Diclik");
            choosePhoto();
        });

    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery();
        } else {
            Toast.makeText(requireActivity(), R.string.toast_cannot_open_galery, Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ArenaFinder.PERMISSION_STORAGE);
        }
    }

    private void takePicture() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera();
        } else {
            Toast.makeText(requireContext(), R.string.toast_cannot_open_camera, Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, ArenaFinder.PERMISSION_CAMERA);
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_FROM_GALLERY);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
        }
    }

    private void uploadPhoto(String imgBase64) {
        RetrofitClient.getInstance().uploadPhotoBase64(usersUtil.getEmail(), imgBase64).enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (RetrofitClient.apakahSukses(response)) {
                    assert response.body() != null;
                    UserModel model = response.body().getData();
                    usersUtil.setUserPhoto(model.getUserPhoto());

                    Glide.with(requireContext())
                            .load(RetrofitClient.USER_PHOTO_URL + usersUtil.getUserPhoto())
                            .placeholder(R.drawable.ic_profile)
                            .into(imgPhoto);

                    // reset uri and bitmap
                    uri = null;
                    bitmap = null;
                } else {
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

    private void updateAccount() {

        RetrofitClient.getInstance().updateAccount(
                usersUtil.getEmail(), inpUsername.getText().toString(), inpNama.getText().toString()
        ).enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {

                if (response.body() != null && RetrofitClient.apakahSukses(response)) {
                    // update data di preference
                    UserModel model = response.body().getData();
                    dataShared.setData(KEY.ACC_USERNAME, model.getUsername());
                    dataShared.setData(KEY.ACC_FULL_NAME, model.getNama());
                    dataShared.setData(KEY.ACC_PHOTO, model.getUserPhoto());

                    Toast.makeText(requireContext(), R.string.suc_account_edited, Toast.LENGTH_SHORT).show();

                } else {
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

}