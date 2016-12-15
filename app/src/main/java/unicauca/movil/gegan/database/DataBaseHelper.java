package unicauca.movil.gegan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jlbel on 12/12/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "gegan.db";
    static int VERSION =6;
    public DataBaseHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlFinca = "CREATE TABLE finca (id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", idusr INTEGER" +
                ", nombre VARCHAR" +
                ", direccion VARCHAR" +
                ", imagen LONGTEXT" +
                ")";
       /* String sqlAnimal = "CREATE TABLE animal (id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", nombre VARCHAR" +
                ", raza VARCHAR" +
                ", sexo VARCHAR" +
                ", nacimiento DATE" +
                ", tipo VARCHAR" +
                ", peso FLOAT" +
                ", litros_diarios FLOAT" +
                ", id_finca INTEGER" +
                ", imagen LONGTEXT" +
                ", peso_al_nacer FLOAT" +
                ", ganancia FLOAT" +
                ")";
        String sqlReporte = "CREATE TABLE reporte (id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", tipo VARCHAR" +
                ", valor DOUBLE" +
                ", comentario VARCHAR" +
                ", fecha DATE" +
                ", id_finca INTEGER" +
                ")";*/


        sqLiteDatabase.execSQL(sqlFinca);
        /*sqLiteDatabase.execSQL(sqlAnimal);
        sqLiteDatabase.execSQL(sqlReporte);*/

        ContentValues contentValues = new ContentValues();
        contentValues.put("idusr", 1);
        contentValues.put("nombre", "Finca De Pruebas");
        contentValues.put("direccion", "Calle falsa 123");
        contentValues.put("imagen", "http://static.panoramio.com/photos/original/7549472.jpg");

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("idusr", 1);
        contentValues2.put("nombre", "Finca De Pruebas2");
        contentValues2.put("direccion", "Calle falsa 12333");
        contentValues2.put("imagen", "http://static.panoramio.com/photos/original/7549472.jpg");


        ContentValues contentValues3 = new ContentValues();
        contentValues3.put("idusr", 1);
        contentValues3.put("nombre", "Finca De Pruebas2");
        contentValues3.put("direccion", "Calle falsa 12333");
        contentValues3.put("imagen", "http://fenixreddenegocios.com/wp-content/uploads/2015/03/IMG-20150303-WA0004.jpg");



        sqLiteDatabase.insert("finca", null, contentValues);
        sqLiteDatabase.insert("finca", null, contentValues2);
        sqLiteDatabase.insert("finca", null, contentValues3);


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE finca");
        //sqLiteDatabase.execSQL("DROP TABLE animal");
        //sqLiteDatabase.execSQL("DROP TABLE reporte");
        onCreate(sqLiteDatabase);

    }
}
