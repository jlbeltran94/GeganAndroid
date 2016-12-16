package unicauca.movil.gegan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import unicauca.movil.gegan.adapters.ReporteAdapter;
import unicauca.movil.gegan.database.ReporteDao;
import unicauca.movil.gegan.databinding.ActivityReportesBinding;
import unicauca.movil.gegan.models.Animal;
import unicauca.movil.gegan.models.Reporte;
import unicauca.movil.gegan.util.L;

/**
 * Created by jlbel on 16/12/2016.
 */

public class ReportesActivity extends AppCompatActivity implements ReporteAdapter.OnReporteListener {

    ActivityReportesBinding binding;

    ReporteAdapter adapter;
    ReporteDao dao;
    Reporte reporte;
    SharedPreferences preferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reportes);
        binding.setHandler(this);

        dao = new ReporteDao(this);
        L.datar = new ArrayList<>();
        adapter = new ReporteAdapter(getLayoutInflater(), this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));


        preferences = getSharedPreferences("preferencias", MODE_PRIVATE);

        try {
            loadData();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    private void loadData() throws ParseException {

        Long idfinca =  preferences.getLong("idfinca", 1);
        List<Reporte> reportes = dao.listByFinca(idfinca);
        L.datar = reportes;
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReporte(Long id) {

    }

    @Override
    public void onDelete(final Long id) throws ParseException {
        reporte = dao.getById(id);
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Reporte")
                .setMessage("¿Esta seguro que desea eliminar este reporte ?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            yesDelete(id);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_error_outline_24dp)
                .show();

    }

    @Override
    public void onEdit(Long id) {
        Intent intent = new Intent(this, AddReportesActivity.class);
        intent.putExtra(AddReportesActivity.EXTRA_ID, id);
        intent.putExtra(AddReportesActivity.EXTRA_EDIT, 1);

        startActivity(intent);

    }

    public void yesDelete(Long id) throws ParseException {
        dao.delete(id);
        loadData();
        adapter.notifyDataSetChanged();
    }

    public void goToAdd(){
        Intent intent = new Intent(this, AddReportesActivity.class);
        intent.putExtra(AddFincaActivity.EXTRA_EDIT, 0);
        startActivity(intent);
    }
}
