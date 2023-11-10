package com.c2.arenafinder.ui.fragment.submain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c2.arenafinder.R;
import com.c2.arenafinder.api.retrofit.RetrofitClient;
import com.c2.arenafinder.data.model.VenueExtendedModel;
import com.c2.arenafinder.data.response.VenueExtendedResponse;
import com.c2.arenafinder.ui.activity.DetailedActivity;
import com.c2.arenafinder.ui.adapter.VenueExtendedAdapter;
import com.c2.arenafinder.util.AdapterActionListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchWorldFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchWorldFragment() {
        // Required empty public constructor
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.fsw_recycler);
    }

    public static SearchWorldFragment newInstance(String param1, String param2) {
        SearchWorldFragment fragment = new SearchWorldFragment();
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
        return inflater.inflate(R.layout.fragment_search_world, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        getAppbar();
    }

    private void getAppbar() {

        if (getActivity() != null) {

            TextView txtTitle = getActivity().findViewById(R.id.sub_title);
            ImageView imageView = getActivity().findViewById(R.id.sub_detailed);
            TextInputLayout inputLayout = getActivity().findViewById(R.id.sub_cari_layout);
            TextInputEditText inputEditText = getActivity().findViewById(R.id.sub_cari_text);

            txtTitle.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            inputLayout.setVisibility(View.VISIBLE);

            inputLayout.setEndIconOnClickListener(v -> {
                Objects.requireNonNull(inputEditText.getText()).clear();
            });

            inputEditText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_FLAG_NO_ENTER_ACTION) {
                    // Hide the keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    if (!Objects.requireNonNull(inputEditText.getText()).toString().isEmpty() && !inputEditText.getText().toString().isBlank()) {

                        RetrofitClient.getInstance().searchVenue(inputEditText.getText().toString())
                                .enqueue(new Callback<VenueExtendedResponse>() {

                                    public void onResponse(Call<VenueExtendedResponse> call, Response<VenueExtendedResponse> response) {

                                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase(RetrofitClient.SUCCESSFUL_RESPONSE)) {

                                            ArrayList<VenueExtendedModel> models = response.body().getData();

                                            recyclerView.setAdapter(new VenueExtendedAdapter(
                                                    requireContext(), models, new AdapterActionListener() {
                                                @Override
                                                public void onClickListener(int position) {
                                                    startActivity(new Intent(requireActivity(), DetailedActivity.class)
                                                            .putExtra(DetailedActivity.ID, models.get(position).getidVenue())
                                                    );
                                                }
                                            }
                                            ));

                                        } else {
                                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<VenueExtendedResponse> call, Throwable t) {
                                        Toast.makeText(requireContext(), "Error -> " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }

                    return true;
                }
                return false;
            });

        }

    }

}