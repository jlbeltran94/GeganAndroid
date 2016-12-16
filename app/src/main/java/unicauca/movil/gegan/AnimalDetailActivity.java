package unicauca.movil.gegan;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.text.ParseException;

import unicauca.movil.gegan.database.AnimalDao;
import unicauca.movil.gegan.databinding.ActivityAnimalDetailBinding;
import unicauca.movil.gegan.models.Animal;

/**
 * Created by jlbel on 15/12/2016.
 */

public class AnimalDetailActivity extends AppCompatActivity {
    ActivityAnimalDetailBinding binding;
    Animal animal;
    AnimalDao dao;
    Long idanimal;
    public static final String EXTRA_ID = "idanimal";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new AnimalDao(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animal_detail);
        binding.setHandler(this);
        idanimal = getIntent().getExtras().getLong(EXTRA_ID);
        try {
            animal = dao.getById(idanimal);
            binding.setAnimal(animal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
