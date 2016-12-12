package unicauca.movil.gegan;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    public void goToReg(){
        String usr =  binding.idUsr.getEditText().getText().toString();
        String pass =  binding.pass.getEditText().getText().toString();

        Log.i("Restaurante", "Usr:"+usr+" Pass:"+pass);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
