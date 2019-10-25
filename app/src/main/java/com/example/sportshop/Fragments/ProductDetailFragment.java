package com.example.sportshop.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.sportshop.Data.Product;
import com.example.sportshop.R;

public class ProductDetailFragment extends Fragment {
    public ProductDetailFragment() {
    }
    public static ProductDetailFragment newInstance(Product product){
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name",product.getName());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        view.setBackgroundColor(Color.RED);
        getActivity(). getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getArguments();
        if(bundle != null){
            TextView name = (TextView)view.findViewById(R.id.detail_name);
            name.setText(bundle.getString("name"));
        }

        return view;
    }
}
