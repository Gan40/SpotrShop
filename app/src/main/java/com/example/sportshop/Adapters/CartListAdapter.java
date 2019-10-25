package com.example.sportshop.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportshop.MyDBHelper;
import com.example.sportshop.Data.Product;
import com.example.sportshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private final int VIEW_TYPE_1 = 1;
    private final int VIEW_TYPE_2 = 2;
    private Boolean flagSelectAll = false;
    private float total_cost = 0;

    private LayoutInflater inflater;
    private List<Product> products;
    private Context context;
    private float sum = 0;
    private int count = 0;

    private AdapterCallback callback;


    public CartListAdapter(Context context, List<Product> products) {
        //this.flagSelectAll = flagSelectAll;
        this.products = products;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        callback = ((AdapterCallback)context);

        /*if(!products.isEmpty()){
            for (Product product : products){
                sum += product.getPrice();
            }
        }*/
    }
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cart_list_item, parent, false);

        return new CartListAdapter.ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final CartListAdapter.ViewHolder holder, final int position) {
        final Product product = products.get(position);

        Picasso.get().load(product.getImage()).fit().into(holder.imageView);
        //holder.imageView.setImageResource(product.getImage());
        holder.nameView.setText(product.getName());
        holder.priceView.setText("$ " + product.getPrice());

        /*if(getFlagSelectAll()){
            holder.checkBox.setOnCheckedChangeListener(null);
            holder.checkBox.setChecked(true);
            //callback.totalCost(sum,true);
        }else if(!getFlagSelectAll()){
            holder.checkBox.setOnCheckedChangeListener(null);
            holder.checkBox.setChecked(false);
            //callback.totalCost(0.00f,true);
        }*/
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    sum = sum + product.getPrice();
                    count++;
                    callback.totalCost(sum, position, count);
                }else {
                    sum = sum - product.getPrice();
                    count--;
                    callback.totalCost(sum, position, count);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView priceView, nameView;
        final CheckBox checkBox;
        ViewHolder(final View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.cart_image);
            nameView = (TextView) view.findViewById(R.id.cart_name);
            priceView = (TextView) view.findViewById(R.id.cart_price);
            checkBox = (CheckBox) view.findViewById(R.id.cart_check_box);
        }
    }

    public static interface AdapterCallback{
        void totalCost(float cost, int position, int count);
    }

    Product recentlyDeletedItem;
    int recentlyDeletedItemPosition;

    public void deleteItem(int position, View view){
        recentlyDeletedItem = products.get(position);
        recentlyDeletedItemPosition = position;
        products.remove(position);
        notifyItemRemoved(position);
        sum = sum - recentlyDeletedItem.getPrice();
        count--;
        callback.totalCost(sum, position, count);
        showUndoSnackBar(view);
    }
    private void showUndoSnackBar(View view){
        Snackbar snackbar = Snackbar.make(view,"Item Deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO",v -> undoDeleted());
        snackbar.show();
        //here delete from database
    }
    private void undoDeleted(){
        products.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
        sum = sum + recentlyDeletedItem.getPrice();
        count++;
        callback.totalCost(sum, recentlyDeletedItemPosition, count);
        //here backup database
    }
    public Boolean getFlagSelectAll() {
        return flagSelectAll;
    }

    public void setFlagSelectAll(Boolean flagSelectAll) {
        this.flagSelectAll = flagSelectAll;
    }

}
