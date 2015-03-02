package com.greenapp.todolist.to_dolist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by kabardinov133238 on 27.02.2015.
 */
public class ToDoContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI = Uri.parse("content://com.paad.todoprovider/todoitems");
    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_CREATION_DATE = "creation_date";

    private MySQLiteOpenHelper myOpenHelper;

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;

    private static final UriMatcher urimatcher;

    static {
        urimatcher = new UriMatcher(UriMatcher.NO_MATCH);
        urimatcher.addURI("com.paad.todoprovider", "todoitems", ALLROWS);
        urimatcher.addURI("com.paad.todoprovider", "todoitems/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        Log.d("myLogs", "create");

        myOpenHelper = new MySQLiteOpenHelper(getContext(),
                MySQLiteOpenHelper.DATABASE_NAME, null,
                MySQLiteOpenHelper.DATABASE_VERSION);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs,
                        String sortOrder) {
        Log.d("myLogs", "query");
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);

        switch (urimatcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
            default:
                Log.d("myLogs", "break");
                break;
        }
        Log.d("myLogs", "prequery");
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
        Log.d("myLogs", "postquery");
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        Log.d("myLogs", "gettype");
        switch (urimatcher.match(uri)) {
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.paad.todos";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.paad.todos";
            default:
                Log.d("myLogs", "123");
                throw new IllegalArgumentException("Unsupported URI:" + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("myLogs", "insert");
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String nullColumnHack = null;

        long id = db.insert(MySQLiteOpenHelper.DATABASE_TABLE, nullColumnHack, values);

        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertedId, null);

            return insertedId;
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("myLogs", "delete");
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        switch (urimatcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowID + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
            default:
                break;
        }

        if (selection == null) selection = "1";

        int deleteCount = db.delete(MySQLiteOpenHelper.DATABASE_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);


        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d("myLogs", "update");
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        switch (urimatcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowID + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : ""
                );
            default:
                break;
        }

        int updateCount = db.update(MySQLiteOpenHelper.DATABASE_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;

    }

    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "todoDatabase.db";
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_TABLE = "todoItemTable";

        private static final String DATABASE_CREATE = "create table " +
                DATABASE_TABLE + " (" + KEY_ID +
                " integer primary key autoincrement, " +
                KEY_TASK + " text not null, " +
                KEY_CREATION_DATE + " long);";


        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d("myLogs", "DATABASE_CREATE");
            db.execSQL(DATABASE_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("TaskDBAdapter", "Upgrading from version" +
                            oldVersion + " to " +
                            newVersion + ", which will destroy all old data"
            );
            db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

    }
}
