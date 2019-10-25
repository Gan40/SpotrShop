package com.example.sportshop.Fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportshop.MyDBHelper;
import com.example.sportshop.Adapters.ProductDataAdapter;
import com.example.sportshop.Data.Product;
import com.example.sportshop.R;

public class PageFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "page_number";
    static final String ARGUMENT_CATEGORY_ID = "category_id";
    private List<Product> products = new ArrayList<>();
    private int pageNumber;
    private int categoryId;
    int backColor;

    public static PageFragment newInstance(int page, int category_id) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt(ARGUMENT_CATEGORY_ID, category_id);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        categoryId = getArguments().getInt(ARGUMENT_CATEGORY_ID);
        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        //setInitialData(pageNumber);
        setInitialData(categoryId, getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ProductDataAdapter adapter = new ProductDataAdapter(getContext(), products);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

        return view;
    }

    private  void setInitialData(int category_id, Context context){
        MyDBHelper dbHelper = new MyDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("product",null,"product_category_id = ?",new String[]{category_id+""},null,null,null);

        if (cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int id = cursor.getColumnIndex("_id");
            int name = cursor.getColumnIndex("name");
            int category = cursor.getColumnIndex("product_category_id");
            int price = cursor.getColumnIndex("price");
            int description = cursor.getColumnIndex("description");
            int image = cursor.getColumnIndex("image");
            int i = 0;
            do {
                //String[] mass = new String[]{cursor.getInt(id)+"",cursor.getString(name).toUpperCase()};
                products.add(new Product(cursor.getInt(id), cursor.getString(name), cursor.getInt(category), cursor.getFloat(price), cursor.getString(description), cursor.getInt(image)));
                i++;
            } while (cursor.moveToNext());
        }
    }
    /*private void setInitialData(int page){
        switch (page){
            case 0:
                products.add(new Product(1, "Green\nBay Packers", 1, 11.3f, "Some description about cap 1...",R.drawable.img_cap_1));
                products.add(new Product(2, "Kansas\nCity Chiefs", 1, 22.4f, "Some description about cap 2...",R.drawable.img_cap_2));
                products.add(new Product(3, "New England\nPatriot", 1, 33.6f, "Some description about cap 3...",R.drawable.img_cap_3));
                products.add(new Product(4, "Philadelphia\nEagles", 1, 44.7f, "Some description about cap 4...",R.drawable.img_cap_4));
                products.add(new Product(5, "Seattle\nSeahawks", 1, 55.8f, "Some description about cap 5...",R.drawable.img_cap_5));
                products.add(new Product(6, "Denver\nBronkos", 1, 66.9f, "Some description about cap 6...",R.drawable.img_cap_6));
                break;
            case 1:
                products.add(new Product(1, "Jordan\n33\"All Stars\" ", 2, 19.4f, "Some description about shoes 1...",R.drawable.img_shoes_1));
                products.add(new Product(2, "Nike\nHyperdunk X", 2, 28.4f, "Some description about shoes 2...",R.drawable.img_shoes_2));
                products.add(new Product(3, "Nike\nKyrie 3", 2, 37.4f, "Some description about shoes 3...",R.drawable.img_shoes_3));
                products.add(new Product(4, "Adidas\nCrazy Explosive", 2, 46.4f, "Some description about shoes 4...",R.drawable.img_shoes_4));
                products.add(new Product(5, "Nike\nLeBron 11", 2, 55.4f, "Some description about shoes 5...",R.drawable.img_shoes_5));
                products.add(new Product(6, "Jordan\nWhy Not 2k17", 2, 55.4f, "Some description about shoes 6...",R.drawable.img_shoes_6));
                break;
            case 2:
                products.add(new Product(1, "Green Hill\nBlack", 3, 13.4f, "Some description about gloves 1...",R.drawable.img_box_1));
                products.add(new Product(2, "Green Hill\nGym", 3, 24.4f, "Some description about gloves 2...",R.drawable.img_box_2));
                products.add(new Product(3, "Green Hill\nLegend", 3, 35.4f, "Some description about gloves 3...",R.drawable.img_box_3));
                products.add(new Product(4, "Green Hill\nRed Tiger", 3, 46.4f, "Some description about gloves 4...",R.drawable.img_box_4));
                products.add(new Product(5, "Green Hill\nDove", 3, 57.4f, "Some description about gloves 5...",R.drawable.img_box_5));
                products.add(new Product(6, "Green Hill\nPunch", 3, 68.4f, "Some description about gloves 6...",R.drawable.img_box_6));
                products.add(new Product(7, "Green Hill\nKnock", 3, 79.4f, "Some description about gloves 7...",R.drawable.img_box_7));
                break;
            case 3:
                products.add(new Product(1, "Tarmak BT-500\nControl", 4, 19.4f, "Some description about shoes 1...",R.drawable.img_ball_1));
                products.add(new Product(2, "Spalding NBA\nSilver Outdoor", 4, 28.4f, "Some description about shoes 2...",R.drawable.img_ball_2));
                products.add(new Product(3, "Lenvave\n 2k18", 4, 37.4f, "Some description about shoes 3...",R.drawable.img_ball_3));
                products.add(new Product(4, "Kuangmi\nKMbb30", 4, 46.4f, "Some description about shoes 4...",R.drawable.img_ball_4));
                products.add(new Product(5, "Nike True\nGrip 17", 4, 55.4f, "Some description about shoes 5...",R.drawable.img_ball_5));
                products.add(new Product(6, "Sankexing\nOfficial", 4, 55.4f, "Some description about shoes 6...",R.drawable.img_ball_6));
                break;

            default:
                products.add(new Product(1, "Green Hill\nBlack", 3, 13.4f, "Some description about gloves 1...",R.drawable.img_box_1));
                products.add(new Product(2, "Green Hill\nGym", 3, 24.4f, "Some description about gloves 2...",R.drawable.img_box_2));
                products.add(new Product(3, "Green Hill\nLegend", 3, 35.4f, "Some description about gloves 3...",R.drawable.img_box_3));
                products.add(new Product(4, "Green Hill\nRed Tiger", 3, 46.4f, "Some description about gloves 4...",R.drawable.img_box_4));
                products.add(new Product(5, "Green Hill\nDove", 3, 57.4f, "Some description about gloves 5...",R.drawable.img_box_5));
                products.add(new Product(6, "Green Hill\nPunch", 3, 68.4f, "Some description about gloves 6...",R.drawable.img_box_6));
                products.add(new Product(7, "Green Hill\nKnock", 3, 79.4f, "Some description about gloves 7...",R.drawable.img_box_7));
        }
    }*/
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration
    {
        private int space;
        public SpacesItemDecoration(int space)
        {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //добавить переданное кол-во пикселей отступа снизу
            outRect.bottom = space;
        }
    }
}