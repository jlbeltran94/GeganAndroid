package unicauca.movil.gegan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jlbel on 12/12/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "gegan2.db";
    static int VERSION = 1;
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
        String sqlAnimal = "CREATE TABLE animal (id INTEGER PRIMARY KEY AUTOINCREMENT" +
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
                ")";


        sqLiteDatabase.execSQL(sqlFinca);
        sqLiteDatabase.execSQL(sqlAnimal);
        sqLiteDatabase.execSQL(sqlReporte);





    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE finca");
        sqLiteDatabase.execSQL("DROP TABLE animal");
        sqLiteDatabase.execSQL("DROP TABLE reporte");
        onCreate(sqLiteDatabase);

    }
}
