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

import unicauca.movil.gegan.adapters.AnimalAdapter;
import unicauca.movil.gegan.database.AnimalDao;
import unicauca.movil.gegan.databinding.ActivityAnimalesBinding;
import unicauca.movil.gegan.models.Animal;
import unicauca.movil.gegan.util.L;

/**
 * Created by jlbel on 15/12/2016.
 */

public class AnimalesActivity extends AppCompatActivity implements AnimalAdapter.OnAnimalListener {

    ActivityAnimalesBinding binding;

    AnimalAdapter adapter;
    AnimalDao dao;
    Animal animal;
    SharedPreferences preferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animales);
        binding.setHandler(this);

        dao = new AnimalDao(this);
        L.dataa = new ArrayList<>();
        adapter = new AnimalAdapter(getLayoutInflater(),this);
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
        List<Animal> animales = dao.listByFinca(idfinca);
        L.dataa = animales;
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume()  {
        super.onResume();
        try {
            loadData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onAnimal(Long id){
        Intent intent = new Intent(this, AnimalDetailActivity.class);
        intent.putExtra(AnimalDetailActivity.EXTRA_ID, id);
        startActivity(intent);


    }

    @Override
    public void onDelete(final Long id) throws ParseException {
        animal = dao.getById(id);
        String name = animal.getNombre();
        new AlertDialog.Builder(this)
                .setTitle("Esta a punto de eliminar un animal")
                .setMessage("¿Esta seguro que desea eliminar el animal "+name+"?")
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
        Intent intent = new Intent(this, AddAnimalActivity.class);
        intent.putExtra(AddFincaActivity.EXTRA_ID, id);
        intent.putExtra(AddFincaActivity.EXTRA_EDIT, 1);

        startActivity(intent);
    }

    public void goToAdd(){
        Intent intent = new Intent(this, AddAnimalActivity.class);
        intent.putExtra(AddFincaActivity.EXTRA_EDIT, 0);
        startActivity(intent);
    }

    public void yesDelete(Long id) throws ParseException {
        dao.delete(id);
        loadData();
        adapter.notifyDataSetChanged();
    }
}
