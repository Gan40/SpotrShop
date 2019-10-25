package com.example.sportshop.Adapters;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportshop.Activities.FavoritesActivity;
import com.example.sportshop.Activities.ProductDetailActivity;
import com.example.sportshop.MainActivity;
import com.example.sportshop.MyDBHelper;
import com.example.sportshop.Data.Product;
import com.example.sportshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder> {

    private final int VIEW_TYPE_1 = 1;
    private final int VIEW_TYPE_2 = 2;


    private LayoutInflater inflater;
    private List<Product> products;
    private Context context;
    private float sum = 0;
    private int count = 0;



    public FavoritesListAdapter(Context context, List<Product> products) {
        //this.flagSelectAll = flagSelectAll;
        this.products = products;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public FavoritesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.cart_list_item, parent, false);

        return new FavoritesListAdapter.ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final FavoritesListAdapter.ViewHolder holder, final int position) {
        final Product product = products.get(position);

        Picasso.get().load(product.getImage()).fit().into(holder.imageView);
        //holder.imageView.setImageResource(product.getImage());
        holder.nameView.setText(product.getName());
        holder.priceView.setText("$ " + product.getPrice());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("myLog",position+"");
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    View view =  holder.imageView;
                    if (view != null) {
                        //animate
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((FavoritesActivity)v.getContext(), view, ((FavoritesActivity)v.getContext()).getString(R.string.animImage));
                        bundle = options.toBundle();
                    }
                }
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra("id",product.getId());
                intent.putExtra("category",product.getCategory());
                intent.putExtra("name",product.getName());
                intent.putExtra("price",product.getPrice());
                intent.putExtra("description",product.getDescription());
                intent.putExtra("image",product.getImage());
                if (bundle == null) {
                    ((FavoritesActivity)v.getContext()).startActivity(intent);
                } else {
                    ((FavoritesActivity)v.getContext()).startActivity(intent, bundle);
                }


                //v.getContext().startActivity(intent);
                //startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView priceView, nameView;;
        ViewHolder(final View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.cart_image);
            nameView = (TextView) view.findViewById(R.id.cart_name);
            priceView = (TextView) view.findViewById(R.id.cart_price);
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
        //callback.totalCost(sum, position, count);
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
        //callback.totalCost(sum, recentlyDeletedItemPosition, count);
        //here backup database
    }

}
