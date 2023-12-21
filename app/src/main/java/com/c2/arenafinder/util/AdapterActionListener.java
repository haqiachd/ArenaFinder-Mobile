package com.c2.arenafinder.util;

/**
 * Digunakan untuk menangani action saat list di klik
 */
public interface AdapterActionListener {

    default void onClickListener(int position){
        // TODO : aksi saat item adapter di klik
    }

}
