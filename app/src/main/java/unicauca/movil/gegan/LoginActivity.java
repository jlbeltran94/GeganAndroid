package unicauca.movil.gegan;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import unicauca.movil.gegan.databinding.ActivityLoginBinding;

/**
 * Created by jlbel on 5/12/2016.
 */

public class LoginActivity extends AppCompatActivity{

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setHandler(this);
    }
}
