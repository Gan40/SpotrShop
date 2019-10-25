package com.example.sportshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sportshop.R;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    /*USER*/
    public static final String TABLE_USER = "user";
    public static final String USER_ID = "_id";
    public static final String USER_LOGIN = "login";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    /*SPORT*/
    public static final String TABLE_SPORT = "sport";
    public static final String SPORT_ID = "_id";
    public static final String SPORT_NAME = "name";
    /*PRODUCT CATEGORY*/
    public static final String TABLE_PRODUCT_CATEGORY = "product_category";
    public static final String PRODUCT_CATEGORY_ID = "_id";
    public static final String PRODUCT_CATEGORY_NAME = "name";
    /*PRODUCT*/
    public static final String TABLE_PRODUCT = "product";
    public static final String PRODUCT_ID = "_id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_SPORT_ID = "sport_id";
    public static final String PRODUCT_PRODUCT_CATEGORY_ID = "product_category_id";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_IMAGE = "image";
    /*CART*/
    public static final String TABLE_CART = "cart";
    public static final String CART_ID = "_id";
    public static final String CART_PRODUCT_ID = "product_id";
    public static final String CART_USER_ID = "user_id";
    /*ORDER*/
    public static final String TABLE_ORDERS = "orders";
    public static final String ORDER_ID = "_id";
    public static final String ORDER_PRODUCT_ID = "product_id";
    public static final String ORDER_USER_ID = "user_id";
    /*FAVORITE*/
    public static final String TABLE_FAVORITES = "favorites";
    public static final String FAVORITE_ID = "_id";
    public static final String FAVORITE_PRODUCT_ID = "product_id";
    public static final String FAVORITE_USER_ID = "user_id";

    public MyDBHelper( Context context) {
        super(context, "myDB", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER
                + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_LOGIN + " TEXT, "
                + USER_EMAIL + " TEXT, "
                + USER_PASSWORD + " TEXT);");

      /*  db.execSQL("CREATE TABLE " + TABLE_SPORT
                + " (" + SPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SPORT_NAME + " TEXT);");*/

        db.execSQL("CREATE TABLE " + TABLE_PRODUCT_CATEGORY
                + " (" + PRODUCT_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_CATEGORY_NAME + " TEXT);");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCT
                + " (" + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_NAME + " TEXT, "
                + PRODUCT_PRODUCT_CATEGORY_ID + " INTEGER, "
                + PRODUCT_PRICE + " REAL, "
                + PRODUCT_DESCRIPTION + " TEXT, "
                + PRODUCT_IMAGE + " INTEGER);");

        db.execSQL("CREATE TABLE " + TABLE_CART
                + " (" + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CART_PRODUCT_ID + " INTEGER, "
                + CART_USER_ID + " INTEGER,"
                + "UNIQUE (" + CART_PRODUCT_ID + ") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE " + TABLE_ORDERS
                + " (" + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_PRODUCT_ID + " INTEGER, "
                + ORDER_USER_ID + " INTEGER,"
                + "UNIQUE (" + ORDER_PRODUCT_ID + ") ON CONFLICT REPLACE);");

        db.execSQL("CREATE TABLE " + TABLE_FAVORITES
                + " (" + FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FAVORITE_PRODUCT_ID + " INTEGER, "
                + FAVORITE_USER_ID + " INTEGER,"
                + "UNIQUE (" + FAVORITE_PRODUCT_ID + ") ON CONFLICT REPLACE);");

        setStartDatas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void setStartDatas(SQLiteDatabase db){

        insertProduct(db, "Green\nBay Packers", 1,  11.3f, "Some description about cap 1...", R.drawable.img_cap_1);
        insertProduct(db, "Kansas\nCity Chiefs", 1,  22.4f, "Some description about cap 2...",R.drawable.img_cap_2);
        insertProduct(db, "New England\nPatriot", 1,  33.6f, "Some description about cap 3...",R.drawable.img_cap_3);
        insertProduct(db, "Philadelphia\nEagles", 1,  44.7f, "Some description about cap 4...",R.drawable.img_cap_4);
        insertProduct(db, "Seattle\nSeahawks", 1,  55.8f, "Some description about cap 5...",R.drawable.img_cap_5);
        insertProduct(db, "Denver\nBronkos", 1,  66.9f, "Some description about cap 6...",R.drawable.img_cap_6);

        insertProduct(db, "Jordan\n33\"All Stars\" ",  2, 19.4f, "Some description about shoes 1...",R.drawable.img_shoes_1);
        insertProduct(db, "Nike\nHyperdunk X", 2,  28.4f, "Some description about shoes 2...",R.drawable.img_shoes_2);
        insertProduct(db, "Nike\nKyrie 3", 2,  37.4f, "Some description about shoes 3...",R.drawable.img_shoes_3);
        insertProduct(db, "Adidas\nCrazy Explosive", 2, 46.4f, "Some description about shoes 4...",R.drawable.img_shoes_4);
        insertProduct(db, "Nike\nLeBron 11", 2,  55.4f, "Some description about shoes 5...",R.drawable.img_shoes_5);
        insertProduct(db, "Jordan\nWhy Not 2k17", 2,  55.4f, "Some description about shoes 6...",R.drawable.img_shoes_6);

        insertProduct(db, "Green Hill\nBlack", 3,  13.4f, "Some description about gloves 1...",R.drawable.img_box_1);
        insertProduct(db, "Green Hill\nGym", 3,  24.4f, "Some description about gloves 2...",R.drawable.img_box_2);
        insertProduct(db, "Green Hill\nLegend", 3,  35.4f, "Some description about gloves 3...",R.drawable.img_box_3);
        insertProduct(db, "Green Hill\nRed Tiger", 3,  46.4f, "Some description about gloves 4...",R.drawable.img_box_4);
        insertProduct(db, "Green Hill\nDove", 3,  57.4f, "Some description about gloves 5...",R.drawable.img_box_5);
        insertProduct(db, "Green Hill\nPunch", 3,  68.4f, "Some description about gloves 6...",R.drawable.img_box_6);
        insertProduct(db, "Green Hill\nKnock", 3,  79.4f, "Some description about gloves 7...",R.drawable.img_box_7);

        insertProduct(db, "Tarmak BT-500\nControl",  4, 19.4f, "Some description about shoes 1...",R.drawable.img_ball_1);
        insertProduct(db, "Spalding NBA\nSilver Outdoor",  4, 28.4f, "Some description about shoes 2...",R.drawable.img_ball_2);
        insertProduct(db, "Lenvave\n 2k18",  4, 37.4f, "Some description about shoes 3...",R.drawable.img_ball_3);
        insertProduct(db, "Kuangmi\nKMbb30",  4, 46.4f, "Some description about shoes 4...",R.drawable.img_ball_4);
        insertProduct(db, "Nike True\nGrip 17",  4, 55.4f, "Some description about shoes 5...",R.drawable.img_ball_5);
        insertProduct(db, "Sankexing\nOfficial",  4, 55.4f, "Some description about shoes 6...",R.drawable.img_ball_6);

        insertProductCategory(db, "football caps");
        insertProductCategory(db, "basketball shoes");
        insertProductCategory(db, "boxing gloves");
        insertProductCategory(db, "basketball balls");

    }

    public void insertUser(SQLiteDatabase db, String login, String email, String password){
        ContentValues values = new ContentValues();
        values.put(USER_LOGIN, login);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USER,null, values);
    }

    public void insertSport(SQLiteDatabase db, String name){
        ContentValues values = new ContentValues();
        values.put(SPORT_NAME, name);
        db.insert(TABLE_SPORT,null, values);
    }

    public void insertProductCategory(SQLiteDatabase db, String name){
        ContentValues values = new ContentValues();
        values.put(PRODUCT_CATEGORY_NAME, name);
        db.insert(TABLE_PRODUCT_CATEGORY,null, values);
    }

    public void insertProduct(SQLiteDatabase db, String name, int product_category_id,
                              float price, String description, int image){
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, name);
        //values.put(PRODUCT_SPORT_ID, sport_id);
        values.put(PRODUCT_PRODUCT_CATEGORY_ID, product_category_id);
        values.put(PRODUCT_PRICE, price);
        values.put(PRODUCT_DESCRIPTION, description);
        values.put(PRODUCT_IMAGE, image);
        db.insert(TABLE_PRODUCT,null, values);
    }

    public void insertCart(SQLiteDatabase db, int product_id, int user_id){
        ContentValues values = new ContentValues();
        values.put(CART_PRODUCT_ID, product_id);
        values.put(CART_USER_ID, user_id);
        db.insert(TABLE_CART,null, values);
    }

    public void insertOrder(SQLiteDatabase db, int product_id, int user_id){
        ContentValues values = new ContentValues();
        values.put(ORDER_PRODUCT_ID, product_id);
        values.put(ORDER_USER_ID, user_id);
        db.insert(TABLE_ORDERS,null, values);
    }

    public void insertFavorite(SQLiteDatabase db, int product_id, int user_id){
        ContentValues values = new ContentValues();
        values.put(FAVORITE_PRODUCT_ID, product_id);
        values.put(FAVORITE_USER_ID, user_id);
        db.insert(TABLE_FAVORITES,null, values);

    }

    public boolean checkIsDataAlreadyInDBorNot(SQLiteDatabase db,String table, String selection, String selectionArg) {

        String Query = "Select * from " + table + " where " + selection + " = " + selectionArg;
        Cursor cursor = db.rawQuery(Query, null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
