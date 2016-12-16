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
import unicauca.movil.gegan.models.Reporte;

/**
 * Created by jlbel on 15/12/2016.
 */

public class ReporteDao {

    SQLiteDatabase db;
    static final String TABLE = "reporte";
    static final String C_IDFINCA = "id_finca";
    static final String C_TIPO = "tipo";
    static final String C_VALOR = "valor";
    static final String C_COMENTARIO = "comentario";
    static final String C_FECHA = "fecha";


    public ReporteDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(Reporte reporte){
        ContentValues values = new ContentValues();
        values.put(C_TIPO, reporte.getTipo());
        values.put(C_VALOR, reporte.getValor());
        values.put(C_COMENTARIO, reporte.getComentario());
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        Date date = reporte.getFecha();
        values.put(C_FECHA, dateFormat.format(date.getTime()));
        values.put(C_IDFINCA, reporte.getId_finca());

        db.insert(TABLE, null, values);

    }

    public void update(Reporte reporte){
        ContentValues values = new ContentValues();
        values.put(C_TIPO, reporte.getTipo());
        values.put(C_VALOR, reporte.getValor());
        values.put(C_COMENTARIO, reporte.getComentario());
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        Date date = reporte.getFecha();
        values.put(C_FECHA, dateFormat.format(date.getTime()));
        values.put(C_IDFINCA, reporte.getId_finca());

        db.update(TABLE, values, "id = ?", new String[]{""+reporte.getId()});
    }

    public void delete (long id){
        db.delete(TABLE, "id = ?", new String[]{""+id});
    }

    public List<Reporte> listByFinca(long idfinca) throws ParseException {
        String sql = "SELECT * FROM "+ TABLE+" WHERE "+C_IDFINCA+" = "+idfinca;

        Log.i("REPORTE", sql);
        return cursorToList(sql);
    }

    public Reporte getById(long id) throws ParseException {
        String sql = "SELECT * FROM "+ TABLE+" WHERE id = "+id;
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToNext();
            return cursorToReporte(cursor);
        }else {
            return null;
        }
    }

    private Reporte cursorToReporte(Cursor cursor) throws ParseException {
        Reporte r = new Reporte();
        r.setId(cursor.getLong(0));
        r.setTipo(cursor.getString(1));
        r.setValor(cursor.getDouble(2));
        r.setComentario(cursor.getString(3));
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        r.setFecha(dateFormat.parse(cursor.getString(4)));
        r.setId_finca(cursor.getLong(5));

        return r;
    }

    private List<Reporte> cursorToList(String sql) throws ParseException {
        Cursor cursor = db.rawQuery(sql, null);
        List<Reporte> data = new ArrayList<>();

        while (cursor.moveToNext()){
            Reporte r = cursorToReporte(cursor);
            data.add(r);
        }
        return data;
    }
}
