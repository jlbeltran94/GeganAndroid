package unicauca.movil.gegan;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import unicauca.movil.gegan.database.FincaDao;
import unicauca.movil.gegan.databinding.ActivityAddFincaBinding;
import unicauca.movil.gegan.models.Finca;

/**
 * Created by jlbel on 15/12/2016.
 */

public class AddFincaActivity extends AppCompatActivity{

    ActivityAddFincaBinding binding;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_EDIT = "edit";
    Finca finca;
    FincaDao dao;
    String encoded;
    int edit;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_finca);
        binding.setHandler(this);
        encoded = new String();
        finca = new Finca();
        dao = new FincaDao(this);

        edit = getIntent().getExtras().getInt(EXTRA_EDIT);
        if (edit == 1){
            id =  getIntent().getExtras().getLong(EXTRA_ID);
            finca = dao.getById(id);
            encoded = finca.getImagen();
            binding.address.getEditText().setText(finca.getDireccion());
            binding.name.getEditText().setText(finca.getNombre());
            binding.button.setText("EDITAR");

            byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            binding.finImg.setImageBitmap(decodedByte);

            binding.finImg.requestLayout();
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.finImg.getLayoutParams();
            lp.width = 600;
            lp.height = 500;
            binding.finImg.setLayoutParams(lp);


        }







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
                .setIcon(R.drawable.ic_insert_photo2_24dp)
                .show();
        //binding.addBtn.setVisibility(View.INVISIBLE);

        binding.finImg.requestLayout();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.finImg.getLayoutParams();
        lp.width = 600;
        lp.height = 500;
        binding.finImg.setLayoutParams(lp);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode){
                case SELECT_PICTURE:
                    Uri selectedImageUri = data.getData();
                    //selectedImagePath = getPath(selectedImageUri);

                    ParcelFileDescriptor parcelFileDescriptor =
                            null;
                    try {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        parcelFileDescriptor.close();

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.WEBP, 50, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();

                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


                        binding.finImg.setImageBitmap(image);



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    /*Context context =  binding.usrImg.getContext();
                    Uri uri = Uri.parse(String.valueOf(selectedImageUri));
                    Picasso.with(context).load(uri).into(binding.usrImg);*/
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.WEBP, 50, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();

                    encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    binding.finImg.setImageBitmap(imageBitmap);
                    break;

            }
        }
    }
    
    public void save(){
        String nombre = binding.name.getEditText().getText().toString();
        String direccion = binding.address.getEditText().getText().toString();
        String imagen = encoded;
        Long idusr = Long.valueOf(1);


        finca.setIdusr(idusr);
        finca.setNombre(nombre);
        finca.setDireccion(direccion);
        finca.setImagen(imagen);

        if(edit == 0){
            dao.insert(finca);
            finish();
        }else {
            dao.update(finca);
            finish();
        }




    }
}
