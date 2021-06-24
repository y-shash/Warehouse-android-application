package com.example.warehousethingy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousethingy.provider.ArrayForListItem;

import java.util.ArrayList;
import java.util.List;

public class ArrayListAdapter extends RecyclerView.Adapter<ArrayListAdapter.ListViewHolder> {
    private List<ArrayForListItem> arrays = new ArrayList<>();

    public ArrayListAdapter(List<ArrayForListItem> arrays) {
        this.arrays = arrays;
    }

    public ArrayListAdapter() {

    }

    public void setArrays(List<ArrayForListItem> arrays) {
        this.arrays = arrays;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        create a viewholder object and inflate the card view layout then return the
//        initialized viewholder
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
//
        ArrayForListItem array = arrays.get(position);

        setText(holder.item, "Item Name", array.getItem());
        setText(holder.quantity, "Quantity", array.getQuantity());
        setText(holder.cost, "Cost", array.getCost());
        setText(holder.des, "Description", array.getDescription());

        boolean isFrozen = array.isFrozen();
        String frozenText = isFrozen ? "Frozen product" : "Not a frozen product";
        holder.frozen.setText(frozenText);
    }

    @Override
    public int getItemCount() {
        return arrays.size();
    }

    // Takes a list of products, and initializes the RecyclerView with products
    public void setData(List<ArrayForListItem> products) {
        this.arrays = products;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView item, quantity, cost, des, frozen;

        private ListViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.card_item);
            quantity = itemView.findViewById(R.id.card_quantity);
            cost = itemView.findViewById(R.id.card_cost);
            des = itemView.findViewById(R.id.card_des);
            frozen = itemView.findViewById(R.id.card_frozen);
        }
    }

    private void setText(TextView tv, String property, String value) {
        String string = String.format("%s: %s", property, value);
        tv.setText(string);
    }
}



