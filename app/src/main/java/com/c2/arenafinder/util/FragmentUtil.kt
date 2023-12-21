package com.c2.arenafinder.util

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.c2.arenafinder.R
import com.c2.arenafinder.data.local.LogApp
import com.c2.arenafinder.data.local.LogTag

/**
 * Berisi method-method umum untuk melakukan perpindahan dari satu fragment ke fragment lainya
 */
object FragmentUtil {

    private const val defaultAnim = R.anim.anim_default_anim

    private fun switchFragment(
        manager: FragmentManager, fragment: Fragment?,
        @IdRes frameLayout: Int,
        backStack: Boolean,
        @AnimRes enter: Int,
        @AnimRes exit: Int,
        @AnimRes popEnter: Int,
        @AnimRes popExit: Int,
    ) {
        if (fragment != null) {
            // create fragment transaction
            val fragmentTransaction = manager.beginTransaction()
            fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
            // backstack action
            if (backStack) {
                fragmentTransaction.replace(frameLayout, fragment)
                fragmentTransaction.addToBackStack(null).commit()
            } else {
                fragmentTransaction.replace(frameLayout, fragment).commit()
            }
        } else {
            LogApp.error("OBJECT", LogTag.LIFEFCYLE, "ERROR FRAGMENT NO ATTACHED ON ACTIVITY")
        }
    }

    @JvmOverloads
    @JvmStatic
    fun switchFragmentAccount(
        fragmentManager: FragmentManager, fragment: Fragment?, backStack: Boolean,
        @AnimRes enter: Int = defaultAnim,
        @AnimRes exit: Int = defaultAnim,
        @AnimRes popEnter: Int = defaultAnim,
        @AnimRes popExit: Int = defaultAnim,
    ) {
        switchFragment(fragmentManager, fragment, R.id.acc_frame_layout, backStack, enter, exit, popEnter, popExit)
    }

    @JvmOverloads
    @JvmStatic
    fun switchFragmentMain(
        fragmentManager: FragmentManager, fragment: Fragment?, backStack: Boolean,
        @AnimRes enter: Int = defaultAnim,
        @AnimRes exit: Int = defaultAnim,
        @AnimRes popEnter: Int = defaultAnim,
        @AnimRes popExit: Int = defaultAnim,
    ) {
        switchFragment(fragmentManager, fragment, R.id.main_frame, backStack, enter, exit, popEnter, popExit)
    }

    @JvmOverloads
    @JvmStatic
    fun switchFragmentSubMain(
        fragmentManager: FragmentManager, fragment: Fragment?, backStack: Boolean,
        @AnimRes enter: Int = defaultAnim,
        @AnimRes exit: Int = defaultAnim,
        @AnimRes popEnter: Int = defaultAnim,
        @AnimRes popExit: Int = defaultAnim,
    ) {
        switchFragment(fragmentManager, fragment, R.id.sub_frame_layout, backStack, enter, exit, popEnter, popExit)
    }

    @JvmOverloads
    @JvmStatic
    fun switchFragmentEmpty(
        fragmentManager: FragmentManager, fragment: Fragment?, backStack: Boolean,
        @AnimRes enter: Int = defaultAnim,
        @AnimRes exit: Int = defaultAnim,
        @AnimRes popEnter: Int = defaultAnim,
        @AnimRes popExit: Int = defaultAnim,
    ) {
        switchFragment(fragmentManager, fragment, R.id.empty_frame_layout, backStack, enter, exit, popEnter, popExit)
    }

    @JvmOverloads
    @JvmStatic
    fun switchFragmentDetailed(
        fragmentManager: FragmentManager, fragment: Fragment?, backStack: Boolean,
        @AnimRes enter: Int = defaultAnim,
        @AnimRes exit: Int = defaultAnim,
        @AnimRes popEnter: Int = defaultAnim,
        @AnimRes popExit: Int = defaultAnim,
    ) {
        switchFragment(fragmentManager, fragment, R.id.detailed_frame_layout, backStack, enter, exit, popEnter, popExit)
    }
}