package unicauca.movil.gegan;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import unicauca.movil.gegan.databinding.ActivityRegisterBinding;

/**
 * Created by jlbel on 10/12/2016.
 */

public class RegisterActivity extends AppCompatActivity{

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setHandler(this);


    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_GALLERY_IMAGE = 2;

    public void takePic() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

 /*   public void getPic(){
        Intent getPictureIntent = new Intent(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(getPictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(getPictureIntent, Exter);
        }
    }*/
}
