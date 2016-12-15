package unicauca.movil.gegan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import unicauca.movil.gegan.database.FincaDao;
import unicauca.movil.gegan.models.Finca;

/**
 * Created by jlbel on 15/12/2016.
 */

public class DetailFragment extends FragmentActivity {

    public static final String EXTRA_IDFINCA = "idfinca";
    FincaDao dao;
    Finca finca;
    Long idfinca;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idfinca = getIntent().getExtras().getLong(EXTRA_IDFINCA);
        dao = new FincaDao(this);
        finca = dao.getById(idfinca);


    }


}
