package com.c2.arenafinder.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.model.ProfileMenuModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Digunakan untuk menampilkan list dari data ItemProfile pada halaman Profile
 *
 */
public class ItemProfileAdapter extends ArrayAdapter<ProfileMenuModel> {

    private final Context appContext;
    private final int resources;
    private final List<ProfileMenuModel> items;

    public ItemProfileAdapter(Context appContext, int resources, List<ProfileMenuModel> items) {
        super(appContext, resources, items);
        this.appContext = appContext;
        this.resources = resources;
        this.items = items;
    }

    public static final int DEFAULT_END_ICON = R.drawable.ic_listview_profile_arrow_right;

    /**
     * Untuk mendapatkan dan menampilkan data kedalam list
     *
     * @param position .
     */
    @SuppressLint({"ViewHolder", "UseCompatLoadingForDrawables"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(appContext);
        View view = inflater.inflate(resources, null);

        // menghubungkan ke id
        CircleImageView icon = view.findViewById(R.id.ita_icon);
        TextView itemName = view.findViewById(R.id.ita_title);
        ImageView endIcon = view.findViewById(R.id.ita_end_icon);

        // mendapatkan dan menampilkan data
        ProfileMenuModel aItem = items.get(position);
        icon.setImageDrawable(appContext.getResources().getDrawable(aItem.getIcon()));
        itemName.setText(appContext.getResources().getString(aItem.getItemTitle()));
        endIcon.setImageDrawable(appContext.getResources().getDrawable(aItem.getEndIcon()));

        return view;
    }
}

