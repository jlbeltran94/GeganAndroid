package unicauca.movil.gegan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import unicauca.movil.gegan.database.ReporteDao;
import unicauca.movil.gegan.databinding.ActivityAddReporteBinding;
import unicauca.movil.gegan.models.Reporte;

/**
 * Created by jlbel on 16/12/2016.
 */

public class AddReportesActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    ActivityAddReporteBinding binding;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_EDIT = "edit";
    Reporte reporte;
    ReporteDao dao;
    int edit;
    Long idfinca;
    Long id;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_reporte);
        binding.setHandler(this);
        reporte = new Reporte();
        dao = new ReporteDao(this);
        preferences = getSharedPreferences("preferencias", MODE_PRIVATE);
        edit = getIntent().getExtras().getInt(EXTRA_EDIT);
        if(edit == 1){

            try {
                id = getIntent().getExtras().getLong(EXTRA_ID);
                reporte = dao.getById(id);
                binding.tipo.getEditText().setText(reporte.getTipo());
                binding.valor.getEditText().setText(""+reporte.getValor());
                binding.fecha.getEditText().setText(""+reporte.getFecha());
                binding.comentario.getEditText().setText(reporte.getComentario());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    //region select type
    public void selectType(View view){

        final String[] value = new String[]{
                "Ingreso",
                "Egreso"

        };

        new AlertDialog.Builder(this)
                .setTitle("Tipo:")
                .setItems(value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selectedText = Arrays.asList(value).get(i);
                        binding.tipo.getEditText().setText(selectedText);
                    }
                })
                .show();

    }
    //endregion

    public void save(){
        String tipo = binding.tipo.getEditText().getText().toString();
        Double valor = Double.valueOf(binding.valor.getEditText().getText().toString());
        String fecha = binding.fecha.getEditText().getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        try {
            Date date = dateFormat.parse(fecha);
            reporte.setFecha(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String comentario = binding.comentario.getEditText().getText().toString();

        idfinca =  preferences.getLong("idfinca", 1);
        reporte.setId_finca(idfinca);
        reporte.setTipo(tipo);
        reporte.setValor(valor);

        if(edit == 0){
            dao.insert(reporte);
            finish();
        }else {
            dao.update(reporte);
            finish();
        }
    }

    //region Datepicker
    /**  public Dialog onCreateDialog(Bundle savedInstanceState) {
     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
     builder.setTitle(R.string.pick_color)
     .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int which) {
     // The 'which' argument contains the index position
     // of the selected item
     }
     });
     return builder.create();
     }**/

    /**
     * This callback method, call DatePickerFragment class,
     * DatePickerFragment class returns calendar view.
     * @param view
     */
    public void datePicker(View view){

        AddAnimalActivity.DatePickerFragment fragment = new AddAnimalActivity.DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    /**
     * To set date on TextView
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        binding.fecha.getEditText().setText(dateFormat.format(calendar.getTime()));

    }


    /**
     * To receive a callback when the user sets the date.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    /**
     * Create a DatePickerFragment class that extends DialogFragment.
     * Define the onCreateDialog() method to return an instance of DatePickerDialog
     */
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }
    //endregion
}
