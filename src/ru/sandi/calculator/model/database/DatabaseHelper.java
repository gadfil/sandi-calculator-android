package ru.sandi.calculator.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ru.sandi.calculator.model.object.DoorFrameObject;
import ru.sandi.calculator.model.table.*;

/**
 * Created by gadfil on 24.09.2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sandi.sqlite3";
    private Context context;

    private static final int DATABASE_VERSION = 11;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists " + CityTable.TABLE_NAME + " ( " +
                        CityTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        CityTable.ID + " INTEGER  NOT NULL, " +
                        CityTable.NAME + " TEXT NOT NULL ); "
        );

        db.execSQL("CREATE TABLE if not exists " + BoxColorTable.TABLE_NAME + " ( " +
                        BoxColorTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        BoxColorTable.ID + " INTEGER  NOT NULL, " +
                        BoxColorTable.ID_COLOR + " INTEGER  NOT NULL, " +
                        BoxColorTable.PRICE + " REAL, " +
                        BoxColorTable.TYPE_OF_LAMINATE + " TEXT, " +
                        BoxColorTable.TITLE + " TEXT  ); "
        );

        db.execSQL("CREATE TABLE if not exists " + BoxTable.TABLE_NAME + " ( " +
                        BoxTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        BoxTable.ID + " INTEGER  NOT NULL, " +
                        BoxTable.COUNT_DOOR + " INTEGER  NOT NULL, " +
                        BoxTable.WIDTH + " INTEGER  NOT NULL, " +
                        BoxTable.HEIGHT + " INTEGER  NOT NULL, " +
                        BoxTable.DEPTH + " INTEGER  NOT NULL, " +
                        BoxTable.ID_IMAGE_2D + " INTEGER  NOT NULL, " +
                        BoxTable.ID_IMAGE_3D + " INTEGER  NOT NULL, " +
                        BoxTable.PRICE_STD + " REAL NOT NULL, " +
                        BoxTable.PRICE_EGGER + " REAL NOT NULL, " +
                        BoxTable.NUMBER + " TEXT, " +
                        BoxTable.COLLECTION + " TEXT  ); "
        );


        db.execSQL("CREATE TABLE if not exists " + DoorContentTable.TABLE_NAME + " ( " +
                        DoorContentTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        DoorContentTable.ID + " INTEGER  NOT NULL, " +
                        DoorContentTable.PARENT + " INTEGER  NOT NULL, " +
                        DoorContentTable.PRICE + " REAL, " +
                        DoorContentTable.TYPE + " TEXT, " +
                        DoorContentTable.TITLE + "  TEXT  ); "
        );


        db.execSQL("CREATE TABLE if not exists " + DoorFrameTable.TABLE_NAME + " ( " +
                        DoorFrameTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        DoorFrameTable.ID + " INTEGER  NOT NULL, " +
                        DoorFrameTable.NUMBER + " INTEGER  NOT NULL, " +
                        DoorFrameTable.ID_IMAGE_DOOR_FRAME + " INTEGER  NOT NULL, " +
                        DoorFrameTable.COLOR + " TEXT  NOT NULL, " +
                        DoorFrameTable.TITLE + "  TEXT  NOT NULL ); "
        );


        db.execSQL("CREATE TABLE if not exists " + DoorPriceTable.TABLE_NAME + " ( " +
                        DoorPriceTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        DoorPriceTable.ID + " INTEGER  NOT NULL, " +
                        DoorPriceTable.ID_DOOR_FRAME + " INTEGER  NOT NULL, " +
                        DoorPriceTable.WIDTH + " INTEGER  NOT NULL, " +
                        DoorPriceTable.PRICE + " REAL  NOT NULL); "
        );


        db.execSQL("CREATE TABLE if not exists " + DoorTable.TABLE_NAME + " ( " +
                        DoorTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        DoorTable.ID + " INTEGER  NOT NULL, " +
                        DoorTable.COEFFICIENT + " REAL  NOT NULL, " +
                        DoorTable.TITLE + " TEXT  NOT NULL); "
        );


        db.execSQL("CREATE TABLE if not exists " + ImageBoxColorTable.TABLE_NAME + " ( " +
                        ImageBoxColorTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        ImageBoxColorTable.ID + " INTEGER  NOT NULL, " +
                        ImageBoxColorTable.IMAGE + " TEXT  NOT NULL); "
        );


        db.execSQL("CREATE TABLE if not exists " + ImageBoxTable.TABLE_NAME + " ( " +
                        ImageBoxTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        ImageBoxTable.ID + " INTEGER  NOT NULL, " +
                        ImageBoxTable.IMAGE + " TEXT  NOT NULL); "
        );


        db.execSQL("CREATE TABLE if not exists " + ImageDoorFrameTable.TABLE_NAME + " ( " +
                        ImageDoorFrameTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        ImageDoorFrameTable.ID + " INTEGER  NOT NULL, " +
                        ImageDoorFrameTable.IMAGE + " TEXT  NOT NULL); "
        );


        ContentValues values1 = new ContentValues();
        values1.put(DoorTable.ID, 1);
        values1.put(DoorTable.COEFFICIENT, 1);
        values1.put(DoorTable.TITLE, "Версаль 1 (матовая) симметрия");
        db.insert(DoorTable.TABLE_NAME, null, values1);


        values1 = new ContentValues();
        values1.put(DoorTable.ID, 2);
        values1.put(DoorTable.COEFFICIENT, 1);
        values1.put(DoorTable.TITLE, "Версаль 1 (матовая) асимметрия");
        db.insert(DoorTable.TABLE_NAME, null, values1);


        values1 = new ContentValues();
        values1.put(DoorTable.ID, 3);
        values1.put(DoorTable.COEFFICIENT, 1.45);
        values1.put(DoorTable.TITLE, "Версаль 2 (матовая) симметрия");
        db.insert(DoorTable.TABLE_NAME, null, values1);


        values1 = new ContentValues();
        values1.put(DoorTable.ID, 4);
        values1.put(DoorTable.COEFFICIENT, 1.45);
        values1.put(DoorTable.TITLE, "Версаль 2 (матовая) асимметрия");
        db.insert(DoorTable.TABLE_NAME, null, values1);


        values1 = new ContentValues();
        values1.put(DoorTable.ID, 5);
        values1.put(DoorTable.COEFFICIENT, 1.45);
        values1.put(DoorTable.TITLE, "Версаль 2 (глянцевая) симметрия");
        db.insert(DoorTable.TABLE_NAME, null, values1);


        values1 = new ContentValues();
        values1.put(DoorTable.ID, 6);
        values1.put(DoorTable.COEFFICIENT, 1.45);
        values1.put(DoorTable.TITLE, "Версаль 2 (глянцевая) асимметрия");
        db.insert(DoorTable.TABLE_NAME, null, values1);


        for (int i = 1; i <= 51; i++) {
            if (i != 3) {
                ContentValues values = new ContentValues();
                values.put(ImageBoxTable.IMAGE, "/SiteNN/images/test_layouts/" + i + "_2d.png");
                values.put(ImageBoxTable.ID, i);
                long result = db.insert(ImageBoxTable.TABLE_NAME, null, values);
                Log.d("log", "/SiteNN/images/test_layouts/" + i + "_2d.png    result" + result);
            }
        }
        for (int i = 1; i <= 51; i++) {
            if (i != 3) {
                ContentValues values = new ContentValues();
                values.put(ImageBoxTable.IMAGE, "/SiteNN/images/test_layouts/" + i + "_3d.png");
                values.put(ImageBoxTable.ID, 51 + i);
                long result = db.insert(ImageBoxTable.TABLE_NAME, null, values);
                Log.d("log", "/SiteNN/images/test_layouts/" + i + "_2d.png    result" + result);
            }

        }

        ContentValues values = new ContentValues();
        values.put(BoxTable.NUMBER, "1.1");
        values.put(BoxTable.ID, 1);
        values.put(BoxTable.WIDTH, 1000);
        values.put(BoxTable.HEIGHT, 2400);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6340);
        values.put(BoxTable.PRICE_EGGER, 7120);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 1);
        values.put(BoxTable.ID_IMAGE_3D, 52);

        db.insert(BoxTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(BoxTable.NUMBER, "1.2");
        values.put(BoxTable.ID, 2);
        values.put(BoxTable.WIDTH, 1000);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6850);
        values.put(BoxTable.PRICE_EGGER, 7760);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 2);
        values.put(BoxTable.ID_IMAGE_3D, 53);

        db.insert(BoxTable.TABLE_NAME, null, values);

//
        values = new ContentValues();
        values.put(BoxTable.NUMBER, "1.3");
        values.put(BoxTable.ID, 4);
        values.put(BoxTable.WIDTH, 1050);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6450);
        values.put(BoxTable.PRICE_EGGER, 7230);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 2);
        values.put(BoxTable.ID_IMAGE_3D, 55);

        db.insert(BoxTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(BoxTable.NUMBER, "3");
        values.put(BoxTable.ID, 7);
        values.put(BoxTable.WIDTH, 1000);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 450);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6450);
        values.put(BoxTable.PRICE_EGGER, 7230);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 7);
        values.put(BoxTable.ID_IMAGE_3D, 58);

        db.insert(BoxTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(BoxTable.NUMBER, "3");
        values.put(BoxTable.ID, 9);
        values.put(BoxTable.WIDTH, 1100);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6450);
        values.put(BoxTable.PRICE_EGGER, 7230);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 9);
        values.put(BoxTable.ID_IMAGE_3D, 60);

        db.insert(BoxTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(BoxTable.NUMBER, "6.1");
        values.put(BoxTable.ID, 16);
        values.put(BoxTable.WIDTH, 1100);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6450);
        values.put(BoxTable.PRICE_EGGER, 7230);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 16);
        values.put(BoxTable.ID_IMAGE_3D, 67);

        db.insert(BoxTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(BoxTable.NUMBER, "9");
        values.put(BoxTable.ID, 17);
        values.put(BoxTable.WIDTH, 1200);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6450);
        values.put(BoxTable.PRICE_EGGER, 7230);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 17);
        values.put(BoxTable.ID_IMAGE_3D, 68);

        db.insert(BoxTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(BoxTable.NUMBER, "9.1");
        values.put(BoxTable.ID, 18);
        values.put(BoxTable.WIDTH, 1250);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6450);
        values.put(BoxTable.PRICE_EGGER, 7230);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 18);
        values.put(BoxTable.ID_IMAGE_3D, 69);

        db.insert(BoxTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(BoxTable.NUMBER, "9.1");
        values.put(BoxTable.ID, 18);
        values.put(BoxTable.WIDTH, 1250);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6450);
        values.put(BoxTable.PRICE_EGGER, 7230);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 18);
        values.put(BoxTable.ID_IMAGE_3D, 69);

        db.insert(BoxTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(BoxTable.NUMBER, "13");
        values.put(BoxTable.ID, 23);
        values.put(BoxTable.WIDTH, 1300);
        values.put(BoxTable.HEIGHT, 2000);
        values.put(BoxTable.DEPTH, 600);
        values.put(BoxTable.COUNT_DOOR, 2);
        values.put(BoxTable.PRICE_STD, 6450);
        values.put(BoxTable.PRICE_EGGER, 7230);
        values.put(BoxTable.COLLECTION, "standard");
        values.put(BoxTable.ID_IMAGE_2D, 23);
        values.put(BoxTable.ID_IMAGE_3D, 74);

        db.insert(BoxTable.TABLE_NAME, null, values);


        for (int i = 1; i <= 19; i++) {
            ContentValues values2 = new ContentValues();
            ContentValues values3 = new ContentValues();
            values3.put(ImageBoxColorTable.ID, i);
            values3.put(ImageBoxColorTable.IMAGE, "/newcalc/colors/" + i + ".jpg");
            values2.put(BoxColorTable.ID, i);
            values2.put(BoxColorTable.ID_COLOR, i);
            values2.put(BoxColorTable.TITLE, "standard " + i);
            values2.put(BoxColorTable.TYPE_OF_LAMINATE, "standard");
            values2.put(BoxColorTable.PRICE, 716);

            db.insert(BoxColorTable.TABLE_NAME, null, values2);
            db.insert(ImageBoxColorTable.TABLE_NAME, null, values3);
        }


        for (int i = 20; i <= 37; i++) {
            ContentValues values2 = new ContentValues();
            ContentValues values3 = new ContentValues();
            values3.put(ImageBoxColorTable.ID, i);
            values3.put(ImageBoxColorTable.IMAGE, "/newcalc/colors/" + i + ".jpg");
            values2.put(BoxColorTable.ID, i);
            values2.put(BoxColorTable.ID_COLOR, i);
            values2.put(BoxColorTable.TITLE, "egger  " + i);
            values2.put(BoxColorTable.TYPE_OF_LAMINATE, "egger");
            values2.put(BoxColorTable.PRICE, 956);

            db.insert(BoxColorTable.TABLE_NAME, null, values2);
            db.insert(ImageBoxColorTable.TABLE_NAME, null, values3);
        }

        values = new ContentValues();
        values.put(DoorContentTable.ID, 1);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Бамбук");
        values.putNull(DoorContentTable.PRICE);
        db.insert(DoorContentTable.TABLE_NAME, null, values);



        //ImageDoorFrameTable
        for(int i = 1; i<= 32; i++){
            values = new ContentValues();
            values.put(ImageDoorFrameTable.ID,i);
            values.put(ImageDoorFrameTable.IMAGE,"/newcalc/ramki/"+i+"_gold.png");
            db.insert(ImageDoorFrameTable.TABLE_NAME, null, values);

            values = new ContentValues();
            values.put(ImageDoorFrameTable.ID,i+32);
            values.put(ImageDoorFrameTable.IMAGE,"/newcalc/ramki/"+i+"_chrome.png");
            db.insert(ImageDoorFrameTable.TABLE_NAME, null, values);

            values = new ContentValues();
            values.put(ImageDoorFrameTable.ID,i+32*2);
            values.put(ImageDoorFrameTable.IMAGE,"/newcalc/ramki/"+i+"_bronze.png");
            db.insert(ImageDoorFrameTable.TABLE_NAME, null, values);

            values = new ContentValues();
            values.put(ImageDoorFrameTable.ID,i+32*3);
            values.put(ImageDoorFrameTable.IMAGE,"/newcalc/ramki/"+i+"_champagne.png");
            db.insert(ImageDoorFrameTable.TABLE_NAME, null, values);
        }

        //DoorFrameTable
        for(int i = 1; i<= 32; i++){
            int n;
            if(i>=14){
                n = i +1;
            }else {
                n = i;

            }

            values = new ContentValues();
            values.put(DoorFrameTable.ID,i);
            values.put(DoorFrameTable.NUMBER,n);
            values.put(DoorFrameTable.TITLE,"Рамка " + n + " золото");
            values.put(DoorFrameTable.COLOR, "ЗОЛОТО");
            values.put(DoorFrameTable.ID_IMAGE_DOOR_FRAME, i);
            db.insert(DoorFrameTable.TABLE_NAME, null, values);


            values = new ContentValues();
            values.put(DoorFrameTable.ID,i);
            values.put(DoorFrameTable.NUMBER,n);
            values.put(DoorFrameTable.TITLE,"Рамка " + n + " хром");
            values.put(DoorFrameTable.COLOR, "ХРОМ");
            values.put(DoorFrameTable.ID_IMAGE_DOOR_FRAME, i + 32);
            db.insert(DoorFrameTable.TABLE_NAME, null, values);



            values = new ContentValues();
            values.put(DoorFrameTable.ID,i);
            values.put(DoorFrameTable.NUMBER,n);
            values.put(DoorFrameTable.TITLE,"Рамка " + n + " бронза");
            values.put(DoorFrameTable.COLOR, "БРОНЗА");
            values.put(DoorFrameTable.ID_IMAGE_DOOR_FRAME, i + 32*2);
            db.insert(DoorFrameTable.TABLE_NAME, null, values);



            values = new ContentValues();
            values.put(DoorFrameTable.ID,i);
            values.put(DoorFrameTable.NUMBER,n);
            values.put(DoorFrameTable.TITLE,"Рамка " + n + " шампань");
            values.put(DoorFrameTable.COLOR, "ШАМПАНЬ");
            values.put(DoorFrameTable.ID_IMAGE_DOOR_FRAME, i + 32*3);
            db.insert(DoorFrameTable.TABLE_NAME, null, values);



        }


        //DoorContentTable

        values = new ContentValues();
        values.put(DoorContentTable.ID, 2);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Ламинат");
        values.put(DoorContentTable.PRICE, 716);
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 1088);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Пескоструйный рисунок на зеркале");
        values.put(DoorContentTable.PRICE, 1000);
        db.insert(DoorContentTable.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(DoorContentTable.ID, 4);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Ротанг");
        values.put(DoorContentTable.PRICE, 3500);
        db.insert(DoorContentTable.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(DoorContentTable.ID, 5);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Стекло цветное");
        values.put(DoorContentTable.PRICE, 1000);
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 6);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Стекло цветное с NRM");
        values.put(DoorContentTable.PRICE, 1444);
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 7);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Стекло цветное с искрой");
        values.put(DoorContentTable.PRICE, 2778);
        db.insert(DoorContentTable.TABLE_NAME, null, values);



        values = new ContentValues();
        values.put(DoorContentTable.ID, 8);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Фотопечать (матовая)");
        values.put(DoorContentTable.PRICE, 3335);
        db.insert(DoorContentTable.TABLE_NAME, null, values);



        values = new ContentValues();
        values.put(DoorContentTable.ID, 9);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Фотопечать (глянцевая)");
        values.put(DoorContentTable.PRICE, 2000);
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 154);
        values.put(DoorContentTable.PARENT, 0);
        values.put(DoorContentTable.TYPE, "cat");
        values.put(DoorContentTable.TITLE, "Зеркало");
        values.putNull(DoorContentTable.PRICE);
        db.insert(DoorContentTable.TABLE_NAME, null, values);



        values = new ContentValues();
        values.put(DoorContentTable.ID, 93);
        values.put(DoorContentTable.PARENT, 1);
        values.put(DoorContentTable.TYPE, "pre-leaf");
        values.put(DoorContentTable.TITLE, "Бамбук Коньяк 4мм");
        values.put(DoorContentTable.PRICE, 2000 );
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 94);
        values.put(DoorContentTable.PARENT, 93);
        values.put(DoorContentTable.TYPE, "leaf");
        values.put(DoorContentTable.TITLE, "/newcalc/napolnenie/93_img1.jpg");
        values.putNull(DoorContentTable.PRICE);
        db.insert(DoorContentTable.TABLE_NAME, null, values);



        values = new ContentValues();
        values.put(DoorContentTable.ID, 95);
        values.put(DoorContentTable.PARENT, 1);
        values.put(DoorContentTable.TYPE, "pre-leaf");
        values.put(DoorContentTable.TITLE, "Бамбук Коньяк 7мм");
        values.put(DoorContentTable.PRICE, 2000 );
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 96);
        values.put(DoorContentTable.PARENT, 95);
        values.put(DoorContentTable.TYPE, "leaf");
        values.put(DoorContentTable.TITLE, "/newcalc/napolnenie/95_img1.jpg");
        values.putNull(DoorContentTable.PRICE);
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 97);
        values.put(DoorContentTable.PARENT, 1);
        values.put(DoorContentTable.TYPE, "pre-leaf");
        values.put(DoorContentTable.TITLE, "Бамбук Коньяк 12мм");
        values.put(DoorContentTable.PRICE, 2000 );
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 98);
        values.put(DoorContentTable.PARENT, 97);
        values.put(DoorContentTable.TYPE, "leaf");
        values.put(DoorContentTable.TITLE, "/newcalc/napolnenie/97_img1.jpg");
        values.putNull(DoorContentTable.PRICE);
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 99);
        values.put(DoorContentTable.PARENT, 1);
        values.put(DoorContentTable.TYPE, "pre-leaf");
        values.put(DoorContentTable.TITLE, "Бамбук венге 7мм");
        values.put(DoorContentTable.PRICE, 2223 );
        db.insert(DoorContentTable.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(DoorContentTable.ID, 100);
        values.put(DoorContentTable.PARENT, 99);
        values.put(DoorContentTable.TYPE, "leaf");
        values.put(DoorContentTable.TITLE, "/newcalc/napolnenie/99_img1.jpg");
        values.putNull(DoorContentTable.PRICE);
        db.insert(DoorContentTable.TABLE_NAME, null, values);







    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BoxColorTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BoxTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CityTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DoorContentTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DoorFrameTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DoorPriceTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DoorTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ImageBoxColorTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ImageBoxTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ImageDoorFrameTable.TABLE_NAME);
        onCreate(db);
    }
}
