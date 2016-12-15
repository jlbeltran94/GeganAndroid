package unicauca.movil.gegan;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import unicauca.movil.gegan.database.FincaDao;
import unicauca.movil.gegan.databinding.ActivityFincaDetailBinding;
import unicauca.movil.gegan.models.Finca;

/**
 * Created by jlbel on 15/12/2016.
 */

public class FincaDetailActivity extends AppCompatActivity {

    ActivityFincaDetailBinding binding;
    Finca finca;
    FincaDao dao;
    Long idfinca;
    public static final String EXTRA_IDFINCA = "idfinca";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = new FincaDao(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finca_detail);
        binding.setHandler(this);
        idfinca =  getIntent().getExtras().getLong(EXTRA_IDFINCA);
        finca = dao.getById(idfinca);
        binding.setFinca(finca);



    }

    public void goToAnimales(){
        Intent intent = new Intent(this, AnimalesActivity.class);
        startActivity(intent);
    }
}
