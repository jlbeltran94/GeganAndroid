package unicauca.movil.gegan;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.text.ParseException;

import unicauca.movil.gegan.database.ReporteDao;
import unicauca.movil.gegan.databinding.ActivityReporteDetailBinding;
import unicauca.movil.gegan.models.Reporte;

/**
 * Created by jlbel on 16/12/2016.
 */

public class ReporteDetailActivity extends AppCompatActivity {
    ActivityReporteDetailBinding binding;
    Reporte reporte;
    ReporteDao dao;
    Long idreporte;
    public static final String EXTRA_ID = "idreporte";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new ReporteDao(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reporte_detail);
        binding.setHandler(this);
        idreporte = getIntent().getExtras().getLong(EXTRA_ID);
        try {
            reporte = dao.getById(idreporte);
            binding.setReporte(reporte);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
