package unicauca.movil.gegan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
    Finca finca;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setHandler(this);

        dao = new FincaDao(this);
        L.data = new ArrayList<>();
        adapter = new FincaAdapter(getLayoutInflater(), this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        preferences = getSharedPreferences("preferencias", MODE_PRIVATE);
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
    public void onFinca(Long id) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("idfinca", id);
        editor.apply();
        Intent intent = new Intent(this, FincaDetailActivity.class);
        intent.putExtra(FincaDetailActivity.EXTRA_IDFINCA, id);

        startActivity(intent);


    }


    public void yesDelete(Long id){
        dao.delete(id);
        loadData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete(final Long id){
        finca = dao.getById(id);
        String name = finca.getNombre();
        new AlertDialog.Builder(this)
                .setTitle("Esta a punto de eliminar una finca")
                .setMessage("¿Esta seguro que desea eliminar la finca "+name+"?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    yesDelete(id);


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
    public void onEdit(Long id){
        Intent intent = new Intent(this, AddFincaActivity.class);
        intent.putExtra(AddFincaActivity.EXTRA_ID, id);
        intent.putExtra(AddFincaActivity.EXTRA_EDIT, 1);

        startActivity(intent);
    }

    public void goToAdd(){
        Intent intent = new Intent(this, AddFincaActivity.class);
        intent.putExtra(AddFincaActivity.EXTRA_EDIT, 0);
        startActivity(intent);
    }
}
