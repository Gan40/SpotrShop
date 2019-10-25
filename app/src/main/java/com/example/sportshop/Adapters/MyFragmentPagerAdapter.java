package com.example.sportshop.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.sportshop.MyDBHelper;
import com.example.sportshop.Fragments.PageFragment;

import java.util.ArrayList;


public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    /*static final String[] TAB_TITLES = new String[]{
            "FOOTBALL\nCAPS",
            "BASKETBALL\nSHOES",
            "BOXING\nGLOVES",
            "BASKETBALL\nBALLS"};*/
    //private String[] TAB_TITLES = null ;
    private ArrayList<String[]> TAB_TITLES = new ArrayList<>() ;
    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.TAB_TITLES = setTitleArray(context);
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position, Integer.parseInt(TAB_TITLES.get(position)[0]));
        //return PageFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return TAB_TITLES[position];
        return TAB_TITLES.get(position)[1].replaceFirst(" ","\n");
    }
    @Override
    public int getCount() {
        //return TAB_TITLES.length;
        return TAB_TITLES.size();
    }

    private ArrayList<String[]> setTitleArray(Context context){
        //String[] out = null;
        ArrayList<String[]> out = new ArrayList<>();
        MyDBHelper dbHelper = new MyDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("product_category",null,null,null,null,null,null);

        if (cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int id = cursor.getColumnIndex("_id");
            int name = cursor.getColumnIndex("name");
            int i = 0;
            do {
                String[] mass = new String[]{cursor.getInt(id)+"",cursor.getString(name).toUpperCase()};
                out.add(mass);
                //Log.d("myLog",i+" : "+cursor.getString(name).toUpperCase());
                Log.d("myLog",i+" : " + out.get(i));
                i++;
            } while (cursor.moveToNext());
        }
        return out;
    }
}
