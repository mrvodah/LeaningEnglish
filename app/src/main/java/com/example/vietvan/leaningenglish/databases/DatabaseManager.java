package com.example.vietvan.leaningenglish.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vietvan.leaningenglish.databases.models.WordModel;
import com.example.vietvan.leaningenglish.databases.models.CategoryModel;
import com.example.vietvan.leaningenglish.databases.models.TopicModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by VietVan on 01/03/2018.
 */

public class DatabaseManager {

    public static final String TABLE_TOPIC = "tbl_topic";
    public static final String TABLE_WORD = "tbl_word";
    private static final String TAG = "TAG";

    private SQLiteDatabase sqLiteDatabase;
    private AssetHelper assetHelper;
    private static DatabaseManager databaseManager;

    public DatabaseManager(Context context){
        // Call constructor of AssetHelper to create DATABASE
        assetHelper = new AssetHelper(context);

        // give READDATABASE Permission for sqLiteDataBase
        sqLiteDatabase = assetHelper.getReadableDatabase();
    }

    // create Instance of DataBaseManager
    public static DatabaseManager getInstance(Context context){
        if(databaseManager == null)
            databaseManager = new DatabaseManager(context);
        return databaseManager;
    }

    // get List<TopicModel> in Database
    public List<TopicModel> getListTopic(){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_TOPIC, null);
        cursor.moveToFirst();

        List<TopicModel> list = new ArrayList<>();
        while(!cursor.isAfterLast()){
            // read data
            list.add(new TopicModel(cursor.getInt(0), cursor.getString(1), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6)));

            // move to next line
            cursor.moveToNext();
        }

        return list;
    }

    public int getNumberWordById(int topicId, int level){
        Cursor cursor = sqLiteDatabase.rawQuery("select level from " + TABLE_WORD +
                " where level = " + level + " and topic_id = " + topicId, null);

        return cursor.getCount();
    }

    // get List<CategoryModel> form Database
    public List<CategoryModel> getListCategory(){
        List<CategoryModel> list = new ArrayList<>();

        for(int i=0;i<getListTopic().size(); i+=5){
            list.add(new CategoryModel(getListTopic().get(i).category, getListTopic().get(i).color));
        }

        return list;
    }

    // get HashMap<String, list<>> from Database
    public HashMap<String, List<TopicModel>> getHashMap(List<TopicModel> topicModelList, List<CategoryModel> categoryModelList){
        HashMap<String, List<TopicModel>> hash = new HashMap<>();

        for(int i=0;i<categoryModelList.size();i++){
            hash.put(
                    categoryModelList.get(i).name,
                    topicModelList.subList(i*5, i*5+5)
            );
        }

        return hash;
    }

    public WordModel getRandomWord(int topicId, int preId) {
        Cursor cursor;

        do{
            int level = 0;
            double random = Math.random() * 100;
            if(random < 5) level = 4;
            else if(random < 15) level = 3;
            else if(random < 30) level = 2;
            else if(random < 60) level = 1;
            else level = 0;

            cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_WORD +
                    " where topic_id = " + topicId +
                    " and level = " + level +
                    " and id <> " + preId +
                    " order by random() limit 1", null);
        } while (cursor.getCount() == 0);
        cursor.moveToFirst();
        Log.d(TAG, "getRandomWord: " + cursor);

        return new WordModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getInt(8), cursor.getInt(9));
    }

    public void updateWordLevel(WordModel wordModel, boolean isKnown){
        sqLiteDatabase = assetHelper.getWritableDatabase();
        int level = wordModel.level;
        if(isKnown && level < 4) level++;
        else if(!isKnown && level > 0) level--;

        ContentValues values = new ContentValues();
        values.put("level", level);
        sqLiteDatabase.update(TABLE_WORD, values,
                "id = ?", new String[] {String.valueOf(wordModel.id)} );

    }

    public void updateLastTime(TopicModel topicModel, String lastTime) {
        sqLiteDatabase = assetHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("last_time", lastTime);

        sqLiteDatabase.update(TABLE_TOPIC, values, "id = ?", new String[] {String.valueOf(topicModel.id)});

    }

}
