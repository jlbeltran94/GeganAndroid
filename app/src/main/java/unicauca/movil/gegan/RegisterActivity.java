package unicauca.movil.gegan;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import unicauca.movil.gegan.databinding.ActivityRegisterBinding;

/**
 * Created by jlbel on 10/12/2016.
 */

public class RegisterActivity extends AppCompatActivity{

    ActivityRegisterBinding binding;



    private static final int SELECT_PICTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setHandler(this);

        //RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)imageView.getLayoutParams;
        //lp.width = x;
        //lp.height = y;
        //imageView.setLayoutParams(lp)





    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_GALLERY_IMAGE = 2;

    public void takePic() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void getPic() {

        // in onCreate or any event where your want the user to
        // select a file
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    public void picSelect(){

        new AlertDialog.Builder(this)
                .setTitle("Ingresar Imagen")
                .setMessage("Selecciona una imagen o tómala ahora mismo")
                .setPositiveButton("Galería", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getPic();
                    }
                })
                .setNegativeButton("Cámara", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        takePic();
                    }
                })
                .setIcon(R.drawable.ic_insert_photo_24dp)
                .show();

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) binding.usrImg.getLayoutParams();

        binding.usrImg.requestLayout();
        binding.usrImg.setLayoutParams(lp);
    }



 /*   public void getPic(){
        Intent getPictureIntent = new Intent(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(getPictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(getPictureIntent, Exter);
        }
    }*/
}
