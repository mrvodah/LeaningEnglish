package com.example.vietvan.leaningenglish.databases;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by VietVan on 01/03/2018.
 */

public class AssetHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "toeic_600.db";
    private static final int DATABASE_VERSION = 1;

    // Create Database with DATABASE_NAME and VERSION
    public AssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
