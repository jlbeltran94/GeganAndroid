package unicauca.movil.gegan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.gegan.models.Animal;
import unicauca.movil.gegan.models.Finca;

/**
 * Created by jlbel on 15/12/2016.
 */

public class AnimalDao {

    SQLiteDatabase db;
    static final String TABLE = "animal";
    static final String C_ID = "id";
    static final String C_IDFINCA = "id_finca";
    static final String C_NAME = "nombre";
    static final String C_RAZA = "direccion";
    static final String C_IMAGE = "imagen";

    public AnimalDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(Animal animal){
        ContentValues values = new ContentValues();
        values.put(C_ID, animal.getId());
        values.put(C_IDFINCA, animal.getId_finca());
        values.put(C_NAME, animal.getNombre());
        values.put(C_RAZA, animal.getRaza());
        values.put(C_IMAGE, animal.getImagen());

        db.insert(TABLE, null, values);
    }

    public void update(Animal animal){
        ContentValues values = new ContentValues();
        values.put(C_ID, animal.getId());
        values.put(C_IDFINCA, animal.getId_finca());
        values.put(C_NAME, animal.getNombre());
        values.put(C_RAZA, animal.getRaza());
        values.put(C_IMAGE, animal.getImagen());

        db.update(TABLE, values, "id = ?", new String[]{""+animal.getId()});
    }

    public void delete (long id){
        db.delete(TABLE, "id = ?", new String[]{""+id});
    }


    public List<Finca> listByUsr(long idusr){
        String sql = "SELECT * FROM "+ TABLE+" WHERE "+C_IDFINCA+" = "+idusr;

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
