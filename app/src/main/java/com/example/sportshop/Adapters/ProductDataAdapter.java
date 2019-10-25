package com.example.sportshop.Adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportshop.Data.Product;
import com.example.sportshop.MainActivity;
import com.example.sportshop.Activities.ProductDetailActivity;
import com.example.sportshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDataAdapter extends RecyclerView.Adapter<ProductDataAdapter.ViewHolder> implements View.OnClickListener {

    private final int VIEW_TYPE_1 = 1;
    private final int VIEW_TYPE_2 = 2;

    private LayoutInflater inflater;
    private List<Product> products;

    private FragmentTransaction transaction;


    public ProductDataAdapter(Context context, List<Product> products) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ProductDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType){
            case VIEW_TYPE_1: view = inflater.inflate(R.layout.product_list_item_1, parent, false); break;
            case VIEW_TYPE_2: view = inflater.inflate(R.layout.product_list_item_2, parent, false); break;
            default: view = inflater.inflate(R.layout.product_list_item_2, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        //Log.d("myLog",position+"");
        if((position+1)%2 != 0) return VIEW_TYPE_1;
        else return VIEW_TYPE_2;
        //return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final ProductDataAdapter.ViewHolder holder, final int position) {
        final Product product = products.get(position);
        //Picasso.with(context).load(R.drawable.landing_screen).into(imageView1);
        Picasso.get().load(product.getImage()).fit().into(holder.imageView);
        //Picasso.get().load(R.drawable.box_1).
        //holder.imageView.setImageResource(product.getImage());
        holder.nameView.setText(product.getName());
        holder.priceView.setText("$ "+product.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("myLog",position+"");
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    View view =  holder.imageView;
                    if (view != null) {
                        //animate
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((MainActivity)v.getContext(), view, ((MainActivity)v.getContext()).getString(R.string.animImage));
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
                    ((MainActivity)v.getContext()).startActivity(intent);
                } else {
                    ((MainActivity)v.getContext()).startActivity(intent, bundle);
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

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(),"Click " ,Toast.LENGTH_LONG);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView priceView, nameView;
        ViewHolder(final View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image);
            nameView = (TextView) view.findViewById(R.id.name);
            priceView = (TextView) view.findViewById(R.id.price);
        }
    }
}
