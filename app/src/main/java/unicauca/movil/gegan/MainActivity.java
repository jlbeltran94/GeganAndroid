package unicauca.movil.gegan;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.gegan.adapters.FincaAdapter;
import unicauca.movil.gegan.database.FincaDao;
import unicauca.movil.gegan.databinding.ActivityMainBinding;
import unicauca.movil.gegan.models.Finca;
import unicauca.movil.gegan.util.L;

public class MainActivity extends AppCompatActivity implements FincaAdapter.OnFincaListener {

    ActivityMainBinding binding;

    FincaAdapter adapter;
    FincaDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dao = new FincaDao(this);
        L.data = new ArrayList<>();
        adapter = new FincaAdapter(getLayoutInflater(), this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        loadData();


    }

    private void loadData(){


        //L.data.addAll(dao.listByUsr(1));
        //L.data.add(dao.getById(1));
        //L.data.addAll(dao.lista());
        //Log.i("Fincas", ""+dao.listByUsr(1));
        //Log.i("Fincass", ""+dao.lista());

       /* Finca f1 = new Finca();
        f1.setId(1);
        f1.setIdusr(1);
        f1.setNombre("Finca prueba list");
        f1.setDireccion("calle falsa 12342");
        f1.setImagen("http://static.panoramio.com/photos/original/7549472.jpg");

        L.data.add(f1);

        */

        List<Finca> fincas = dao.listByUsr(1);
        Log.i("Fincas", String.valueOf(fincas));

        L.data = fincas;


        adapter.notifyDataSetChanged();



    }

    @Override
    protected void onResume() {
        super.onResume();
       // L.data.addAll(dao.lista());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFinca(View v) {

    }
}
