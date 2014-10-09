package ru.sandi.calculator.model.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import ru.sandi.calculator.model.table.*;

/**
 * Created by gadfil on 24.09.2014.
 */
public class Provider extends ContentProvider {

    public static final String DISTINCT = "distinct";
    public static final String GROUP_BY = "group_by";

   public static final String AUTHORITY = "ru.sandi.calculator.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri IMAGE_BOX_URI = Uri.withAppendedPath(AUTHORITY_URI, ImageBoxTable.TABLE_NAME);
    public static final Uri BOX_URI = Uri.withAppendedPath(AUTHORITY_URI, BoxTable.TABLE_NAME);
    public static final Uri BOX_COLOR_URI = Uri.withAppendedPath(AUTHORITY_URI, BoxColorTable.TABLE_NAME);
    public static final Uri IMAGE_BOX_COLOR_URI = Uri.withAppendedPath(AUTHORITY_URI, ImageBoxColorTable.TABLE_NAME);
    public static final Uri DOOR_URI = Uri.withAppendedPath(AUTHORITY_URI, DoorTable.TABLE_NAME);
    public static final Uri DOOR_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, DoorContentTable.TABLE_NAME);
    public static final Uri DOOR_FRAME_URI = Uri.withAppendedPath(AUTHORITY_URI, DoorFrameTable.TABLE_NAME);
    public static final Uri UNION_BOX_IMAGE_BOX_URI = Uri.withAppendedPath(BOX_URI, ImageBoxTable.TABLE_NAME);
    public static final Uri UNION_BOX_COLOR_IMAGE_BOX_COLOR_URI = Uri.withAppendedPath(BOX_COLOR_URI, ImageBoxColorTable.TABLE_NAME);
    public static final Uri UNION_DOOR_CONTENT_URI = Uri.withAppendedPath( Uri.withAppendedPath(AUTHORITY_URI, DoorContentTable.TABLE_NAME), DoorContentTable.TABLE_NAME);

    private static final int ALL_BOX_COLOR = 1;
    private static final int ALL_BOX = 2;
    private static final int ALL_CITY = 3;
    private static final int ALL_DOOR_CONTENT = 4;
    private static final int ALL_DOOR_FRAME = 5;
    private static final int ALL_DOOR_PRICE = 6;
    private static final int ALL_DOOR = 7;
    private static final int ALL_IMAGE_BOX_COLOR = 8;
    private static final int ALL_IMAGE_BOX = 9;
    private static final int ALL_IMAGE_DOOR_FRAME = 10;
    private static final int SINGLE_BOX_COLOR = 11;
    private static final int SINGLE_BOX = 12;
    private static final int SINGLE_CITY = 13;
    private static final int SINGLE_DOOR_CONTENT = 14;
    private static final int SINGLE_DOOR_FRAME = 15;
    private static final int SINGLE_DOOR_PRICE = 16;
    private static final int SINGLE_DOOR = 17;
    private static final int SINGLE_IMAGE_BOX_COLOR = 18;
    private static final int SINGLE_IMAGE_BOX = 19;
    private static final int SINGLE_IMAGE_DOOR_FRAME = 20;

    private static final int UNION_BOX_AND_IMAGE_BOX = 21;
    private static final int UNION_BOX_COLOR_AND_IMAGE_BOX_COLOR = 22;
    private static final int UNION_DOOR_CONTENT =  23;

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        sUriMatcher.addURI(AUTHORITY, BoxColorTable.TABLE_NAME, ALL_BOX_COLOR);
        sUriMatcher.addURI(AUTHORITY, BoxTable.TABLE_NAME, ALL_BOX);
        sUriMatcher.addURI(AUTHORITY, CityTable.TABLE_NAME, ALL_CITY);
        sUriMatcher.addURI(AUTHORITY, DoorContentTable.TABLE_NAME, ALL_DOOR_CONTENT);
        sUriMatcher.addURI(AUTHORITY, DoorFrameTable.TABLE_NAME, ALL_DOOR_FRAME);
        sUriMatcher.addURI(AUTHORITY, DoorPriceTable.TABLE_NAME, ALL_DOOR_PRICE);
        sUriMatcher.addURI(AUTHORITY, DoorTable.TABLE_NAME, ALL_DOOR);
        sUriMatcher.addURI(AUTHORITY, ImageBoxColorTable.TABLE_NAME, ALL_IMAGE_BOX_COLOR);
        sUriMatcher.addURI(AUTHORITY, ImageBoxTable.TABLE_NAME, ALL_IMAGE_BOX);
        sUriMatcher.addURI(AUTHORITY, ImageDoorFrameTable.TABLE_NAME, ALL_IMAGE_DOOR_FRAME);
        sUriMatcher.addURI(AUTHORITY, BoxTable.TABLE_NAME+"/"+ImageBoxTable.TABLE_NAME, UNION_BOX_AND_IMAGE_BOX );
        sUriMatcher.addURI(AUTHORITY, BoxColorTable.TABLE_NAME+"/"+ImageBoxColorTable.TABLE_NAME, UNION_BOX_COLOR_AND_IMAGE_BOX_COLOR );
        sUriMatcher.addURI(AUTHORITY, DoorContentTable.TABLE_NAME+"/"+DoorContentTable.TABLE_NAME, UNION_DOOR_CONTENT );


        sUriMatcher.addURI(AUTHORITY, BoxColorTable.TABLE_NAME + "/#", SINGLE_BOX_COLOR);
        sUriMatcher.addURI(AUTHORITY, BoxTable.TABLE_NAME + "/#", SINGLE_BOX);
        sUriMatcher.addURI(AUTHORITY, CityTable.TABLE_NAME + "/#", SINGLE_CITY);
        sUriMatcher.addURI(AUTHORITY, DoorContentTable.TABLE_NAME + "/#", SINGLE_DOOR_CONTENT);
        sUriMatcher.addURI(AUTHORITY, DoorFrameTable.TABLE_NAME + "/#", SINGLE_DOOR_FRAME);
        sUriMatcher.addURI(AUTHORITY, DoorPriceTable.TABLE_NAME + "/#", SINGLE_DOOR_PRICE);
        sUriMatcher.addURI(AUTHORITY, DoorTable.TABLE_NAME + "/#", SINGLE_DOOR);
        sUriMatcher.addURI(AUTHORITY, ImageBoxColorTable.TABLE_NAME + "/#", SINGLE_IMAGE_BOX_COLOR);
        sUriMatcher.addURI(AUTHORITY, ImageBoxTable.TABLE_NAME + "/#", SINGLE_IMAGE_BOX);
        sUriMatcher.addURI(AUTHORITY, ImageDoorFrameTable.TABLE_NAME + "/#", SINGLE_IMAGE_DOOR_FRAME);


    }

    private DatabaseHelper dbHelper;



    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String id;
        boolean distinct = uri.getBooleanQueryParameter(DISTINCT, false);
        String groupBy = uri.getQueryParameter(GROUP_BY);

        Log.d("vvv", " " + uri);
        Log.d("vvv", "queryBuilder " + queryBuilder.buildQuery(projection, selection, selectionArgs, groupBy, null, sortOrder, null));
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case ALL_BOX_COLOR:
                queryBuilder.setTables(BoxColorTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;
            case ALL_BOX:
                queryBuilder.setTables(BoxTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;
            case ALL_CITY:
                queryBuilder.setTables(CityTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;
            case ALL_DOOR_CONTENT:
                Log.d("bbb", "queryBuilder " + queryBuilder.buildQuery(projection, selection, selectionArgs, groupBy, null, sortOrder, null));
                queryBuilder.setTables(DoorContentTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;
            case ALL_DOOR_FRAME:

                queryBuilder.setTables(DoorFrameTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                Log.d("vvv", "queryBuilder@ " + queryBuilder.buildQuery(projection, selection, selectionArgs, groupBy, null, sortOrder, null));
                break;
            case ALL_DOOR_PRICE:
                queryBuilder.setTables(DoorPriceTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;
            case ALL_DOOR:
                queryBuilder.setTables(DoorTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;
            case ALL_IMAGE_BOX_COLOR:
                queryBuilder.setTables(ImageBoxColorTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;
            case ALL_IMAGE_BOX:
                queryBuilder.setTables(ImageBoxTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;
            case ALL_IMAGE_DOOR_FRAME:
                queryBuilder.setTables(ImageDoorFrameTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;

            case UNION_BOX_AND_IMAGE_BOX:
                queryBuilder.setTables(BoxTable.TABLE_NAME + ", " + ImageBoxTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
//                Log.d("aaa", "queryBuilder " + queryBuilder.buildQuery(projection, selection, selectionArgs, groupBy, null, sortOrder, null));
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;

            case UNION_BOX_COLOR_AND_IMAGE_BOX_COLOR:
                queryBuilder.setTables(BoxColorTable.TABLE_NAME + ", " + ImageBoxColorTable.TABLE_NAME);
                queryBuilder.setDistinct(distinct);
//                Log.d("aaa", "queryBuilder " + queryBuilder.buildQuery(projection, selection, selectionArgs, groupBy, null, sortOrder, null));
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;

            case UNION_DOOR_CONTENT:
                queryBuilder.setTables(DoorContentTable.TABLE_NAME + " first, " + DoorContentTable.TABLE_NAME + " second ");
                queryBuilder.setDistinct(distinct);
                Log.d("aaa", "queryBuilder " + queryBuilder.buildQuery(projection, selection, selectionArgs, groupBy, null, sortOrder, null));
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
                break;

            case SINGLE_BOX_COLOR:
                queryBuilder.setTables(BoxColorTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(BoxColorTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_BOX:
                queryBuilder.setTables(BoxTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(BoxTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_CITY:
                queryBuilder.setTables(CityTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(CityTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_DOOR_CONTENT:
                queryBuilder.setTables(DoorContentTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DoorContentTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_DOOR_FRAME:
                queryBuilder.setTables(DoorFrameTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DoorFrameTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_DOOR_PRICE:
                queryBuilder.setTables(DoorPriceTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DoorPriceTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_DOOR:
                queryBuilder.setTables(DoorTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DoorTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_IMAGE_BOX_COLOR:
                queryBuilder.setTables(ImageBoxColorTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(ImageBoxColorTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_IMAGE_BOX:
                queryBuilder.setTables(ImageBoxTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(ImageBoxTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_IMAGE_DOOR_FRAME:
                queryBuilder.setTables(ImageDoorFrameTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(ImageDoorFrameTable.ID + " = " + id);
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;


        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long result;
        switch (sUriMatcher.match(uri)) {
            case ALL_BOX_COLOR:
                result = dbHelper.getWritableDatabase().insert(BoxColorTable.TABLE_NAME, null, values);
                break;
            case ALL_BOX:
                result = dbHelper.getWritableDatabase().insert(BoxTable.TABLE_NAME, null, values);
                break;
            case ALL_CITY:
                result = dbHelper.getWritableDatabase().insert(CityTable.TABLE_NAME, null, values);
                break;
            case ALL_DOOR_CONTENT:
                result = dbHelper.getWritableDatabase().insert(DoorContentTable.TABLE_NAME, null, values);
                break;
            case ALL_DOOR_FRAME:
                result = dbHelper.getWritableDatabase().insert(DoorFrameTable.TABLE_NAME, null, values);
                break;
            case ALL_DOOR_PRICE:
                result = dbHelper.getWritableDatabase().insert(DoorPriceTable.TABLE_NAME, null, values);
                break;
            case ALL_DOOR:
                result = dbHelper.getWritableDatabase().insert(DoorTable.TABLE_NAME, null, values);
                break;
            case ALL_IMAGE_BOX_COLOR:
                result = dbHelper.getWritableDatabase().insert(ImageBoxColorTable.TABLE_NAME, null, values);
                break;
            case ALL_IMAGE_BOX:
                result = dbHelper.getWritableDatabase().insert(ImageBoxTable.TABLE_NAME, null, values);
                break;
            case ALL_IMAGE_DOOR_FRAME:
                result = dbHelper.getWritableDatabase().insert(ImageDoorFrameTable.TABLE_NAME, null, values);
                break;
            default:
                return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.withAppendedPath(uri, String.valueOf(result));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String id;
        int result = 0;

        switch (sUriMatcher.match(uri)) {
            case ALL_BOX_COLOR:
                result = db.delete(BoxColorTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_BOX:
                result = db.delete(BoxTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_CITY:
                result = db.delete(CityTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_DOOR_CONTENT:
                result = db.delete(DoorContentTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_DOOR_FRAME:
                result = db.delete(DoorFrameTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_DOOR_PRICE:
                result = db.delete(DoorPriceTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_DOOR:
                result = db.delete(DoorTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_IMAGE_BOX_COLOR:
                result = db.delete(ImageBoxColorTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_IMAGE_BOX:
                result = db.delete(ImageBoxTable.TABLE_NAME, selection, selectionArgs);
                break;
            case ALL_IMAGE_DOOR_FRAME:
                result = db.delete(ImageDoorFrameTable.TABLE_NAME, selection, selectionArgs);
                break;


            case SINGLE_BOX_COLOR:
                id = uri.getPathSegments().get(1);
                result = db.delete(BoxColorTable.TABLE_NAME, BoxColorTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_BOX:
                id = uri.getPathSegments().get(1);
                result = db.delete(BoxTable.TABLE_NAME, BoxTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_CITY:
                id = uri.getPathSegments().get(1);
                result = db.delete(CityTable.TABLE_NAME, CityTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_DOOR_CONTENT:
                id = uri.getPathSegments().get(1);
                result = db.delete(DoorContentTable.TABLE_NAME, DoorContentTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_DOOR_FRAME:
                id = uri.getPathSegments().get(1);
                result = db.delete(DoorFrameTable.TABLE_NAME, DoorFrameTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_DOOR_PRICE:
                id = uri.getPathSegments().get(1);
                result = db.delete(DoorPriceTable.TABLE_NAME, DoorPriceTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_DOOR:
                id = uri.getPathSegments().get(1);
                result = db.delete(DoorTable.TABLE_NAME, DoorTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_IMAGE_BOX_COLOR:
                id = uri.getPathSegments().get(1);
                result = db.delete(ImageBoxColorTable.TABLE_NAME, ImageBoxColorTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_IMAGE_BOX:
                id = uri.getPathSegments().get(1);
                result = db.delete(ImageBoxTable.TABLE_NAME, ImageBoxTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_IMAGE_DOOR_FRAME:
                id = uri.getPathSegments().get(1);
                result = db.delete(ImageDoorFrameTable.TABLE_NAME, ImageDoorFrameTable.ID + " = " + id, selectionArgs);
                break;


        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String id;
        int result = 0;

        switch (sUriMatcher.match(uri)) {
            case ALL_BOX_COLOR:
                result = db.update(BoxColorTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_BOX:
                result = db.update(BoxTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_CITY:
                result = db.update(CityTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_DOOR_CONTENT:
                result = db.update(DoorContentTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_DOOR_FRAME:
                result = db.update(DoorFrameTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_DOOR_PRICE:
                result = db.update(DoorPriceTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_DOOR:
                result = db.update(DoorTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_IMAGE_BOX_COLOR:
                result = db.update(ImageBoxColorTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_IMAGE_BOX:
                result = db.update(ImageBoxTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALL_IMAGE_DOOR_FRAME:
                result = db.update(ImageDoorFrameTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SINGLE_BOX_COLOR:
                id = uri.getPathSegments().get(1);
                result = db.update(BoxColorTable.TABLE_NAME, values, BoxColorTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_BOX:
                id = uri.getPathSegments().get(1);
                result = db.update(BoxTable.TABLE_NAME, values, BoxTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_CITY:
                id = uri.getPathSegments().get(1);
                result = db.update(CityTable.TABLE_NAME, values, CityTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_DOOR_CONTENT:
                id = uri.getPathSegments().get(1);
                result = db.update(DoorContentTable.TABLE_NAME, values, DoorContentTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_DOOR_FRAME:
                id = uri.getPathSegments().get(1);
                result = db.update(DoorFrameTable.TABLE_NAME, values, DoorFrameTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_DOOR_PRICE:
                id = uri.getPathSegments().get(1);
                result = db.update(DoorPriceTable.TABLE_NAME, values, DoorPriceTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_DOOR:
                id = uri.getPathSegments().get(1);
                result = db.update(DoorTable.TABLE_NAME, values, DoorTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_IMAGE_BOX_COLOR:
                id = uri.getPathSegments().get(1);
                result = db.update(ImageBoxColorTable.TABLE_NAME, values, ImageBoxColorTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_IMAGE_BOX:
                id = uri.getPathSegments().get(1);
                result = db.update(ImageBoxTable.TABLE_NAME, values, ImageBoxTable.ID + " = " + id, selectionArgs);
                break;
            case SINGLE_IMAGE_DOOR_FRAME:
                id = uri.getPathSegments().get(1);
                result = db.update(ImageDoorFrameTable.TABLE_NAME, values, ImageDoorFrameTable.ID + " = " + id, selectionArgs);
                break;

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }
}
