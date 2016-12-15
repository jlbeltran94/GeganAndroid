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
        List<Finca> fincas = dao.listByUsr(1);
        L.data = fincas;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFinca(View v) {

    }
}
