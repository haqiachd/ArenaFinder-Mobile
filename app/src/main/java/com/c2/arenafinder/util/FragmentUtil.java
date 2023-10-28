package com.c2.arenafinder.util;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.c2.arenafinder.R;

public class FragmentUtil {

    private static void switchFragment(
            @NonNull FragmentManager fragmentManager, Fragment fragment, @IdRes int frameLayout, boolean backStack
    ){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (backStack){
            transaction.replace(frameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
        }else {
            transaction.replace(frameLayout, fragment).commit();
        }
    }

    public static void switchFragmentAccount(
            @NonNull FragmentManager fragmentManager, Fragment fragment, boolean backStack
    ){
        switchFragment(fragmentManager, fragment, R.id.acc_frame_layout, backStack);
    }

    public static void switchFragmentMain(
            @NonNull FragmentManager fragmentManager, Fragment fragment, boolean backStack
    ){
        switchFragment(fragmentManager, fragment, R.id.main_frame, backStack);
    }

    public static void switchFragmentSubMain(
            @NonNull FragmentManager fragmentManager, Fragment fragment, boolean backStack
    ){
        switchFragment(fragmentManager, fragment, R.id.sub_frame_layout, backStack);
    }

    public static void switchFragmentEmpty(
            @NonNull FragmentManager fragmentManager, Fragment fragment, boolean backStack
    ){
        switchFragment(fragmentManager, fragment, R.id.empty_frame_layout, backStack);
    }

    public static void switchFragmentDetailed(
            @NonNull FragmentManager fragmentManager, Fragment fragment, boolean backStack
    ){
        switchFragment(fragmentManager, fragment, R.id.detailed_frame_layout, backStack);
    }

}
