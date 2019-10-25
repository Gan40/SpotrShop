package com.example.sportshop.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sportshop.Adapters.CartListAdapter;
import com.example.sportshop.Adapters.FavoritesListAdapter;
import com.example.sportshop.Data.Product;
import com.example.sportshop.MainActivity;
import com.example.sportshop.MyDBHelper;
import com.example.sportshop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static long back_pressed;
    private List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //toggle.getToolbarNavigationClickListener().onClick();
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_favorite);
        setTitle(R.string.item_4);

        /*RECYCLER VIEW LIST*/
        setInitialData(1,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.favorite_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        FavoritesListAdapter adapter = new FavoritesListAdapter(this, products);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        recyclerView.setAdapter(adapter);
        /*View view = findViewById(R.id.coordinator_layout);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete(adapter,view));
        itemTouchHelper.attachToRecyclerView(recyclerView);*/
    }

    private  void setInitialData(int user_id, Context context){

        ArrayList<String> productIdArrayList = new ArrayList<>();
        MyDBHelper dbHelper = new MyDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Cursor cursor = db.query("favorites",null,"user_id = ?",new String[]{Integer.toString(user_id)},null,null,null);
        Cursor cursor = db.query("favorites",null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int id = cursor.getColumnIndex(dbHelper.FAVORITE_ID);
            int productId = cursor.getColumnIndex(dbHelper.FAVORITE_PRODUCT_ID);
            int userId = cursor.getColumnIndex(dbHelper.FAVORITE_USER_ID);

            do {
                productIdArrayList.add(Integer.toString(cursor.getInt(productId)));
            } while (cursor.moveToNext());
        }

        String [] productIdArray = productIdArrayList.toArray(new String[0]);
        for (int i=0; i < productIdArray.length; i++){

            Cursor cursor1 = db.query("product",null,"_id = ?", new String[]{productIdArray[i]},null,null,null);
            if (cursor1.moveToFirst()) {
                int p_id = cursor1.getColumnIndex("_id");
                int name = cursor1.getColumnIndex("name");
                int category = cursor1.getColumnIndex("product_category_id");
                int price = cursor1.getColumnIndex("price");
                int description = cursor1.getColumnIndex("description");
                int image = cursor1.getColumnIndex("image");
                do {
                    //String[] mass = new String[]{cursor.getInt(id)+"",cursor.getString(name).toUpperCase()};
                    products.add(new Product(cursor1.getInt(p_id), cursor1.getString(name), cursor1.getInt(category),
                            cursor1.getFloat(price), cursor1.getString(description), cursor1.getInt(image)));

                } while (cursor1.moveToNext());
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.nav_cart:
                Intent cart = new Intent(this, ShoppingCartActivity.class);
                startActivity(cart);
                finish();
                break;
            case R.id.nav_order:
                Intent order = new Intent(this, MyOrdersActivity.class);
                startActivity(order);
                finish();
                break;
            case R.id.nav_favorite:

                break;
            case R.id.nav_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                finish();
                break;
            case R.id.nav_service:
                Intent service = new Intent(this, CustomerServiceActivity.class);
                startActivity(service);
                finish();
                break;
            case R.id.nav_info:
                Intent info = new Intent(this, AboutUsActivity.class);
                startActivity(info);
                finish();
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
