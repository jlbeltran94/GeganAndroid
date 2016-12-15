package unicauca.movil.gegan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.gegan.models.Finca;

/**
 * Created by jlbel on 12/12/2016.
 */

public class FincaDao {

    SQLiteDatabase db;
    static final String TABLE = "finca";
    static final String C_ID = "id";
    static final String C_IDUSR = "idusr";
    static final String C_NAME = "nombre";
    static final String C_ADDRESS = "direccion";
    static final String C_IMAGE = "imagen";

    public FincaDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(Finca finca){
        ContentValues values = new ContentValues();

        values.put(C_IDUSR, finca.getIdusr());
        values.put(C_NAME, finca.getNombre());
        values.put(C_ADDRESS, finca.getDireccion());
        values.put(C_IMAGE, finca.getImagen());

        db.insert(TABLE, null, values);
    }

    public void update(Finca finca){
        ContentValues values = new ContentValues();
        values.put(C_IDUSR, finca.getIdusr());
        values.put(C_NAME, finca.getNombre());
        values.put(C_ADDRESS, finca.getDireccion());
        values.put(C_IMAGE, finca.getImagen());

        db.update(TABLE, values, "id = ?", new String[]{""+finca.getId()});
    }

    public void delete (long id){
        db.delete(TABLE, "id = ?", new String[]{""+id});
    }


    public List<Finca> listByUsr(long idusr){
        String sql = "SELECT * FROM "+ TABLE+" WHERE "+C_IDUSR+" = "+idusr;

        Log.i("Finca", sql);
        return cursorToList(sql);
    }

    public Finca getById(long id){
        String sql = "SELECT * FROM "+ TABLE+" WHERE id = "+id;
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToNext();
            return cursorToFinca(cursor);
        }else {
            return null;
        }
    }


    public List<Finca> lista(){
        String sql = "SELECT * FROM "+ TABLE;
        Log.i("SQL", sql);
        return cursorToList(sql);
    }

    private Finca cursorToFinca(Cursor cursor){
        Finca f = new Finca();
        f.setId(cursor.getLong(0));
        f.setIdusr(cursor.getLong(1));
        f.setNombre(cursor.getString(2));
        f.setDireccion(cursor.getString(3));
        f.setImagen(cursor.getString(4));
        return f;
    }


    private List<Finca> cursorToList(String sql){
        Cursor cursor = db.rawQuery(sql, null);
        List<Finca> data = new ArrayList<>();

        while (cursor.moveToNext()){
            Finca f = cursorToFinca(cursor);
            data.add(f);
        }
        return data;
    }


}
