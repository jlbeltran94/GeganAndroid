package unicauca.movil.gegan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import unicauca.movil.gegan.databinding.ActivityRegisterBinding;

/**
 * Created by jlbel on 10/12/2016.
 */

public class RegisterActivity extends AppCompatActivity{

    ActivityRegisterBinding binding;
    //String typeP;


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String selectedImagePath;
    private static final int SELECT_PICTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setHandler(this);

        //RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)imageView.getLayoutParams;
        //lp.width = x;
        //lp.height = y;
        //imageView.setLayoutParams(lp)

//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) binding.usrImg.getLayoutParams();




    }



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
                      //  typeP = "Galería";
                      //  binding.uname.requestLayout();
                      //  binding.uname.setText(typeP);
                        getPic();

                    }
                })
                .setNegativeButton("Cámara", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // typeP = "Cámara";
                       // binding.uname.requestLayout();
                        //binding.uname.setText(typeP);
                        takePic();

                    }
                })
                .setIcon(R.drawable.ic_insert_photo_24dp)
                .show();
        //binding.addBtn.setVisibility(View.INVISIBLE);

        binding.usrImg.requestLayout();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) binding.usrImg.getLayoutParams();
        lp.width = 500;
        lp.height = 400;
        binding.usrImg.setLayoutParams(lp);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode){
                case SELECT_PICTURE:
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = getPath(selectedImageUri);
                    Context context =  binding.usrImg.getContext();
                    Uri uri = Uri.parse(String.valueOf(selectedImageUri));
                    Picasso.with(context).load(uri).into(binding.usrImg);
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    binding.usrImg.setImageBitmap(imageBitmap);
                    break;

            }
            /*if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Context context =  binding.usrImg.getContext();
                Uri uri = Uri.parse(String.valueOf(selectedImageUri));
                Picasso.with(context).load(uri).into(binding.usrImg);
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
               Bundle extras = data.getExtras();
               Bitmap imageBitmap = (Bitmap) extras.get("data");
               binding.usrImg.setImageBitmap(imageBitmap);
            }*/
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {

            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }



 /*   public void getPic(){
        Intent getPictureIntent = new Intent(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(getPictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(getPictureIntent, Exter);
        }
    }*/
}
