package com.c2.arenafinder.ui.fragment.submain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.DataShared;
import com.c2.arenafinder.data.local.LogApp;
import com.c2.arenafinder.data.local.LogTag;
import com.c2.arenafinder.ui.activity.SplashScreenActivity;
import com.c2.arenafinder.util.ArenaFinder;
import com.c2.arenafinder.util.LanguagesUtil;
import com.google.android.material.button.MaterialButton;

import static com.c2.arenafinder.util.LanguagesUtil.ENGLISH;
import static com.c2.arenafinder.util.LanguagesUtil.INDONESIAN;
import static com.c2.arenafinder.util.LanguagesUtil.JAVANESE;

public class LanguageSettingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String langCode;

    private LanguagesUtil language;

    private MaterialButton btnSave;
    private RadioButton opsiIndo, opsiJawa, opsiInggris;

    public LanguageSettingFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        opsiIndo = view.findViewById(R.id.lsf_opsi_indo);
        opsiJawa = view.findViewById(R.id.lsf_opsi_jawa);
        opsiInggris = view.findViewById(R.id.lsf_opsi_inggris);
        btnSave = view.findViewById(R.id.lsf_btn_simpan);
    }

    public static LanguageSettingFragment newInstance(String param1, String param2) {
        LanguageSettingFragment fragment = new LanguageSettingFragment();
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
        return inflater.inflate(R.layout.fragment_language_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getAppbar();

        language = new LanguagesUtil(requireActivity(), new DataShared(requireContext()));
        langCode = language.getActivatedLanguage();

        switch (langCode) {
            case INDONESIAN: {
                opsiIndo.setChecked(true);
                LogApp.info(requireContext(), LogTag.ON_WHEN, "LANG INDO");
                break;
            }
            case JAVANESE: {
                opsiJawa.setChecked(true);
                LogApp.info(requireContext(), LogTag.ON_WHEN, "LANG JAVANESE");
                break;
            }
            case ENGLISH: {
                opsiInggris.setChecked(true);
                LogApp.info(requireContext(), LogTag.ON_WHEN, "LANG ENGLISH");
                break;
            }
            default: {
                opsiIndo.setChecked(true);
            }
        }

        /*
         * Aksi saat button opsi indo di klik
         */
        opsiIndo.setOnClickListener(indo -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Change Language to Indonesian");
            langCode = INDONESIAN;
        });

        /*
         * Aksi saat button opsi jawa di klik
         */
        opsiJawa.setOnClickListener(jawa -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Change Language to Javanese");
            langCode = JAVANESE;
        });

        /*
         * Aksi saat button opsi inggris di klik
         */
        opsiInggris.setOnClickListener(inggris -> {
            LogApp.info(requireContext(), LogTag.ON_CLICK, "Change Language to English");
            langCode = ENGLISH;
        });

        /*
         * Aksi saat button opsi save di klik
         */
        btnSave.setOnClickListener(save -> {
            // confirm change language
            ArenaFinder.playVibrator(requireContext(), ArenaFinder.VIBRATOR_SHORT);
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.dia_title_inform)
                    .setMessage(R.string.dia_change_langueage)
                    .setPositiveButton(R.string.dia_positive_ok, (dialog, which) -> {
                        // save setting and restart app
                        language.setActivatedLanguage(langCode);
                        language.changeLanguage();
                        ArenaFinder.restartApplication(requireContext(), SplashScreenActivity.class);
                    })
                    .setNegativeButton(R.string.dia_negative_cancel, (dialog, which) -> {

                    }).create().show();
        });

    }

    private void getAppbar() {

        if (getActivity() != null) {

            LinearLayout linear = getActivity().findViewById(R.id.sub_linear);
            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            ImageView imgBack = getActivity().findViewById(R.id.sub_back);

            linear.setVisibility(View.VISIBLE);

            txtTitle.setText(getString(R.string.sub_change_lang));

            imgBack.setOnClickListener(v -> {
                requireActivity().onBackPressed();
            });
        }
    }

}