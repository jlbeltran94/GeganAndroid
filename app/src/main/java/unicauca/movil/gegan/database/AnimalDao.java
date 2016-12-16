package unicauca.movil.gegan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import unicauca.movil.gegan.models.Animal;
import unicauca.movil.gegan.models.Finca;

/**
 * Created by jlbel on 15/12/2016.
 */

public class AnimalDao {

    SQLiteDatabase db;
    static final String TABLE = "animal";
    static final String C_IDFINCA = "id_finca";
    static final String C_NAME = "nombre";
    static final String C_RAZA = "raza";
    static final String C_SEXO = "sexo";
    static final String C_TIPO = "tipo";
    static final String C_IMAGE = "imagen";
    static final String C_PESO = "peso";
    static final String C_PESO_AL_NACER = "peso_al_nacer";
    static final String C_LITROS = "litros_diarios";
    static final String C_GANANCIA = "ganancia";
    static final String C_NACIMIENTO = "nacimiento";

    public AnimalDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(Animal animal){
        ContentValues values = new ContentValues();
        values.put(C_NAME, animal.getNombre());
        values.put(C_RAZA, animal.getRaza());
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        Date date = animal.getNacimiento();
        values.put(C_NACIMIENTO, dateFormat.format(date.getTime()));
        values.put(C_TIPO, animal.getTipo());
        values.put(C_PESO, animal.getPeso());
        values.put(C_LITROS, animal.getLitros_diarios());
        values.put(C_IDFINCA, animal.getId_finca());
        values.put(C_IMAGE, animal.getImagen());
        values.put(C_PESO_AL_NACER, animal.getPeso_al_nacer());
        values.put(C_GANANCIA, animal.getGanancia());
        values.put(C_SEXO, animal.getSexo());



        db.insert(TABLE, null, values);
    }

    public void update(Animal animal){
        ContentValues values = new ContentValues();
        values.put(C_NAME, animal.getNombre());
        values.put(C_RAZA, animal.getRaza());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        Date date = animal.getNacimiento();
        values.put(C_NACIMIENTO, dateFormat.format(date.getTime()));
        values.put(C_TIPO, animal.getTipo());
        values.put(C_PESO, animal.getPeso());
        values.put(C_LITROS, animal.getLitros_diarios());
        values.put(C_IDFINCA, animal.getId_finca());
        values.put(C_IMAGE, animal.getImagen());
        values.put(C_PESO_AL_NACER, animal.getPeso_al_nacer());
        values.put(C_GANANCIA, animal.getGanancia());
        values.put(C_SEXO, animal.getSexo());

        db.update(TABLE, values, "id = ?", new String[]{""+animal.getId()});
    }

    public void delete (long id){
        db.delete(TABLE, "id = ?", new String[]{""+id});
    }


    public List<Animal> listByFinca(long idfinca) throws ParseException {
        String sql = "SELECT * FROM "+ TABLE+" WHERE "+C_IDFINCA+" = "+idfinca;

        Log.i("ANIMAL", sql);
        return cursorToList(sql);
    }

    public Animal getById(long id) throws ParseException {
        String sql = "SELECT * FROM "+ TABLE+" WHERE id = "+id;
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToNext();
            return cursorToAnimal(cursor);
        }else {
            return null;
        }
    }


    public List<Animal> lista() throws ParseException {
        String sql = "SELECT * FROM "+ TABLE;
        Log.i("SQL", sql);
        return cursorToList(sql);
    }

    private Animal cursorToAnimal(Cursor cursor) throws ParseException {
        Animal a = new Animal();
        a.setId(cursor.getLong(0));
        a.setNombre(cursor.getString(1));
        a.setRaza(cursor.getString(2));
        a.setSexo(cursor.getString(3));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        a.setNacimiento(dateFormat.parse(cursor.getString(4)));
        a.setTipo(cursor.getString(5));
        a.setPeso(cursor.getFloat(6));
        a.setLitros_diarios(cursor.getFloat(7));
        a.setId_finca(cursor.getLong(8));
        a.setImagen(cursor.getString(9));
        a.setPeso_al_nacer(cursor.getFloat(10));
        a.setGanancia(cursor.getFloat(11));
        return a;
    }


    private List<Animal> cursorToList(String sql) throws ParseException {
        Cursor cursor = db.rawQuery(sql, null);
        List<Animal> data = new ArrayList<>();

        while (cursor.moveToNext()){
            Animal a = cursorToAnimal(cursor);
            data.add(a);
        }
        return data;
    }
}
