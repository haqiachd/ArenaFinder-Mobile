package com.c2.arenafinder.ui.fragment.empty;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitState;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.data.repository.UsersRepository;
import com.c2.arenafinder.di.UsersViewModelFactory;
import com.c2.arenafinder.util.UsersUtil;
import com.c2.arenafinder.viewmodel.UsersViewModel;

public class VerifyStatusFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private UsersViewModel usersViewModel;

    private UsersUtil usersUtil;

    // Animation setting
    private final float transX = -1500F;
    private final float transXB = 1500F;
    private final long duration = 1200L;
    private final long delay = 100L;

    private LottieAnimationView lottie;
    private TextView txtMessage;
    private TextView txtDesk;
    private Button button;

    public VerifyStatusFragment() {
        // Required empty public constructor
    }

    private void initViews(View root) {
        lottie = root.findViewById(R.id.fev_lottie);
        button = root.findViewById(R.id.fev_button);
        txtMessage = root.findViewById(R.id.fev_txt);
        txtDesk = root.findViewById(R.id.fev_txt_deks);
        button = root.findViewById(R.id.fev_button);
    }

    public static VerifyStatusFragment newInstance(String param1, String param2) {
        VerifyStatusFragment fragment = new VerifyStatusFragment();
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
        return inflater.inflate(R.layout.fragment_verify_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        usersViewModel = new ViewModelProvider(
                requireActivity(),
                new UsersViewModelFactory(new UsersRepository())
        ).get(UsersViewModel.class);

        usersUtil = new UsersUtil(requireContext());

        usersViewModel.fetchVerified(usersUtil.getEmail());

        txtDesk.setText(getResources().getString(R.string.fev_desc, new UsersUtil(requireContext()).getEmail()));

        button.setOnClickListener(v ->
                mbalek()
        );

        if (getView() != null){
            observer();
        }

        getAppbar();
    }

    private void getAppbar() {

        if (getActivity() != null) {

            LinearLayout linear = getActivity().findViewById(R.id.sub_linear);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            ImageView imgBack = getActivity().findViewById(R.id.sub_back);

            linear.setVisibility(View.VISIBLE);

            txtTitle.setText(getString(R.string.sub_verify_status));

            imgBack.setOnClickListener(v -> {
                requireActivity().onBackPressed();
            });
        }
    }

    private void observer(){
        usersViewModel.isVerified().observe(getViewLifecycleOwner(), dataState -> {
            if (dataState instanceof RetrofitState.Loading){
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_LOADING, "VERIFIED ON LOADING");
            }
            else if (dataState instanceof RetrofitState.Error){
                LogApp.error(requireContext(), LogTag.RETROFIT_ON_FAILURE, "VERIFIED ON ERROR");
                Toast.makeText(requireContext(), "not verified", Toast.LENGTH_SHORT).show();
            }
            else if (dataState instanceof RetrofitState.Success){
                LogApp.info(requireContext(), LogTag.RETROFIT_ON_RESPONSE, "VERIFIED ON SUCCESS");
                Toast.makeText(requireContext(), "verified", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mbalek() {
        // animasi transisi
        lottie.animate().translationX(transX).setDuration(duration).setStartDelay(delay).start();
        txtMessage.animate().translationX(transXB).setDuration(duration).setStartDelay(delay).start();
        txtDesk.animate().translationX(transXB).setDuration(duration).setStartDelay(delay).start();

        button.animate().translationY(-1800F).setDuration(600).setStartDelay(delay * 3)
                .withEndAction(
                        () -> requireActivity().onBackPressed()
                );
    }


}