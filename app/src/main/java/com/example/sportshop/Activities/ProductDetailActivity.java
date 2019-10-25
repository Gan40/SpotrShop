package com.example.sportshop.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportshop.MyDBHelper;
import com.example.sportshop.R;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView image;
    private TextView name;
    private TextView price;
    private TextView description;
    private ImageButton favotite;
    volatile boolean isToFavorite = true;
    volatile boolean isToCart = true;
    private FloatingActionButton add_to_cart;

    public ProductDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = (ImageView)findViewById(R.id.detail_image);
        //name = (TextView)findViewById(R.id.detail_name);
        price = (TextView)findViewById(R.id.detail_price);
        description = (TextView)findViewById(R.id.detail_description);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        favotite = (ImageButton)findViewById(R.id.detail_add_to_favorites);
        add_to_cart = (FloatingActionButton)findViewById(R.id.detail_add_to_cart);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_shopping_cart);
        add_to_cart.setFabIcon(drawable);

        add_to_cart.setOnClickListener(this);
        favotite.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            image.setImageResource(Integer.parseInt(bundle.get("image").toString()));
            //name.setText(bundle.get("name").toString());
            price.setText("$ "+bundle.get("price").toString());
            description.setText(bundle.get("description").toString());
            setTitle(bundle.get("name").toString());
            int currentProductId = Integer.parseInt(bundle.get("id").toString());
            /**
             * if product exist in DB
             * TABLE: favorites*/
            MyDBHelper dbHelper = new MyDBHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(dbHelper.checkIsDataAlreadyInDBorNot(db,"favorites","product_id",Integer.toString(currentProductId))){
                isToFavorite = false;
                favotite.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_to_favorite_2));
            }else {
                isToFavorite = true;
                favotite.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_to_favorite_1));
            }

        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//show back press button
    }

    @Override
    public boolean onSupportNavigateUp(){//back button listener
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_add_to_favorites:
                Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.favorite_button_anim);
                v.startAnimation(animAlpha);
                if(isToFavorite){
                    //Log.d("myLog", "FAV");
                    /**
                     * insert to DB
                     * TABLE: favorites*/
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null){
                        int currentProductId = Integer.parseInt(bundle.get("id").toString());
                        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        dbHelper.insertFavorite(db, currentProductId, 1);
                        Log.d("myLog", "Inserted To Favorites: id = " + currentProductId);
                    }
                    favotite.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_to_favorite_2));
                    isToFavorite = false;

                }else {
                    //Log.d("myLog", "NO FAV");
                    /**
                     * delete from DB
                     * TABLE: favorites*/
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null){
                        int currentProductId = Integer.parseInt(bundle.get("id").toString());
                        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("favorites","product_id = ?", new String[]{Integer.toString(currentProductId)});
                        Log.d("myLog", "Deleted from Favorites: id = " + currentProductId);
                    }
                    favotite.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_to_favorite_1));
                    isToFavorite = true;
                }
                break;
            case R.id.detail_add_to_cart:
                /**
                 * insert to DB
                 * TABLE: cart*/
                Bundle bundle = getIntent().getExtras();
                if (bundle != null){
                    int currentProductId = Integer.parseInt(bundle.get("id").toString());
                    MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    dbHelper.insertCart(db, currentProductId, 1);
                    Log.d("myLog", "Inserted To Cart: id = " + currentProductId);
                }
                break;
        }
    }

}
