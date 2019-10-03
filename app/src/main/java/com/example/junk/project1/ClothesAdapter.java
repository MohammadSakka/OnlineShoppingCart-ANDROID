package com.example.junk.project1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.junk.project1.model.Clothes;

import java.util.ArrayList;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.MyViewHolder> {

    // hold data for adapter
    public static ArrayList<Clothes> mClothes;

    private LayoutInflater inflater;
    private Context context;

    // constructor that accepts array of clothes and inflates from context
    public ClothesAdapter(Context context, ArrayList<Clothes> clothes) {
        inflater = LayoutInflater.from(context);
        this.mClothes = clothes;
        this.context = context;
    }

    // returns newly created MyViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // get layout of each items of clothes created in linear_layout_clothes
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.linear_layout_clothes, parent, false);

        MyViewHolder holder = new MyViewHolder(layout);

        return holder;
    }

    // called by LayoutManager to display data and specified position
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        // sets these elements to specified positions in mClothes
        holder.chkItem.setChecked(mClothes.get(position).getChecked());
        holder.txtClothes.setText(mClothes.get(position).getClothes());

        holder.spnQuantity.setSelection(mClothes.get(position).getQuantitySelection());
        holder.spnSize.setSelection(mClothes.get(position).getSizeSelection());

        holder.imgView.setImageResource(mClothes.get(position).getImg());

        // setting tags to remember which items, size, quantity is selected
        holder.spnQuantity.setTag(position);
        holder.spnSize.setTag(position);
        holder.chkItem.setTag(position);

        holder.chkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // gets checked item position from the tag
                Integer chkItemPosition = (Integer) holder.chkItem.getTag();

                // if already checked, uncheck box, other wise check the box
                if (mClothes.get(chkItemPosition).getChecked()) {

                    mClothes.get(chkItemPosition).setChecked(false);

                } else {
                    mClothes.get(chkItemPosition).setChecked(true);
                }

            }
        });

        holder.spnSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id) {

                // gets size spinner position from the tag
                Integer spnSizePosition = (Integer) holder.spnSize.getTag();

                // setting size spinner to selected position
                mClothes.get(spnSizePosition).setSizeSelection(pos);

                // setting selected size variable to selected size from the spinner
                String selectedSize = holder.spnSize.getSelectedItem().toString();

                // setting size variable from clothes item to selected size
                mClothes.get(spnSizePosition).size = selectedSize;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        holder.spnQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id) {

                // gets checked item position from the tag
                Integer spnQuantityPosition = (Integer) holder.spnQuantity.getTag();

                // setting quantity spinner to selected position
                mClothes.get(spnQuantityPosition).setQuantitySelection(pos);

                // setting selected quantity variable to selected quantity from the spinner
                String selectedQuantity = holder.spnQuantity.getSelectedItem().toString();

                // setting quantity variable from clothes item to selected quantity
                mClothes.get(spnQuantityPosition).quantity = selectedQuantity;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    //returns total number of elements to be displayed
    @Override
    public int getItemCount() {
        return mClothes.size();
    }

    // sets field variables and references to these field variables from the layout
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox chkItem;
        private TextView txtClothes;
        private Spinner spnSize;
        private Spinner spnQuantity;
        private ImageView imgView;

        public MyViewHolder(LinearLayout layout) {
            super(layout);

            chkItem = layout.findViewById(R.id.chk_item);
            txtClothes = layout.findViewById(R.id.txt_clothes);
            spnSize = layout.findViewById(R.id.spn_size);
            spnQuantity = layout.findViewById(R.id.spn_quantity);
            imgView = layout.findViewById(R.id.img_item_picture);
        }
    }

}

