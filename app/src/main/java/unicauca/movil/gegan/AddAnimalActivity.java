package unicauca.movil.gegan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import unicauca.movil.gegan.database.AnimalDao;
import unicauca.movil.gegan.databinding.ActivityAddAnimalBinding;
import unicauca.movil.gegan.models.Animal;

/**
 * Created by jlbel on 15/12/2016.
 */

public class AddAnimalActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener   {

    ActivityAddAnimalBinding binding;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_EDIT = "edit";
    Animal animal;
    AnimalDao dao;
    String encoded;
    int edit;
    Long idfinca;
    Long id;
    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_animal);
        binding.setHandler(this);
        encoded = new String();
        animal = new Animal();
        dao = new AnimalDao(this);
        preferences = getSharedPreferences("preferencias", MODE_PRIVATE);

        edit = getIntent().getExtras().getInt(EXTRA_EDIT);
        if(edit == 1){
            try {
                id = getIntent().getExtras().getLong(EXTRA_ID);
                animal = dao.getById(id);
                encoded = animal.getImagen();
                binding.name.getEditText().setText(animal.getNombre());
                binding.raza.getEditText().setText(animal.getRaza());
                binding.sexo.getEditText().setText(animal.getSexo());
                binding.tipo.getEditText().setText(animal.getTipo());
                binding.nacimiento.getEditText().setText(""+animal.getNacimiento());
                binding.pesoNac.getEditText().setText(""+animal.getPeso_al_nacer());
                binding.pesoAc.getEditText().setText(""+animal.getPeso());
                binding.litros.getEditText().setText(""+animal.getLitros_diarios());

                byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                binding.image.setImageBitmap(decodedByte);

                binding.image.requestLayout();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.image.getLayoutParams();
                lp.width = 600;
                lp.height = 500;
                binding.image.setLayoutParams(lp);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

    //region pics
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
    //endregion

    //region selecter pic
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

                        takePic();

                    }
                })
                .setIcon(R.drawable.ic_insert_photo2_24dp)
                .show();


        binding.image.requestLayout();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) binding.image.getLayoutParams();
        lp.width = 600;
        lp.height = 500;
        binding.image.setLayoutParams(lp);

    }
    //endregion

    //region select sex
  public void selectSex(View view){

        final String[] value = new String[]{
                "Macho",
                "Hembra"

        };

        new AlertDialog.Builder(this)
                .setTitle("Sexo:")
                .setItems(value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selectedText = Arrays.asList(value).get(i);
                        binding.sexo.getEditText().setText(selectedText);
                        if(selectedText == "Macho"){
                            binding.litros.setVisibility(View.INVISIBLE);
                            animal.setLitros_diarios(0);
                        }else if(selectedText == "Hembra"){
                            binding.litros.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .setIcon(R.drawable.ic_gender)
                .show();

    }
    //endregion

    //region select type
    public void selectType(View view){

        final String[] value = new String[]{
                "Carne",
                "Leche"

        };

        new AlertDialog.Builder(this)
                .setTitle("Tipo:")
                .setItems(value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selectedText = Arrays.asList(value).get(i);
                        binding.tipo.getEditText().setText(selectedText);
                    }
                })
                .show();

    }
    //endregion

    //region results picture
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


                        binding.image.setImageBitmap(image);



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

                    binding.image.setImageBitmap(imageBitmap);
                    break;

            }
        }
    }
    //endregion

    //region Save
    public void save(){
        String nombre = binding.name.getEditText().getText().toString();
        String raza = binding.raza.getEditText().getText().toString();
        String sexo = binding.sexo.getEditText().getText().toString();
        String tipo = binding.tipo.getEditText().getText().toString();
        String nacimiento = binding.nacimiento.getEditText().getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date nacAni = null;
        try {
            nacAni = dateFormat.parse(nacimiento);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String peso_nac =  binding.pesoNac.getEditText().getText().toString();

        Float peso_n = Float.valueOf(peso_nac);

        String peso_act = binding.pesoAc.getEditText().getText().toString();

        Float peso_a = Float.valueOf(peso_act);

        String litros = binding.litros.getEditText().getText().toString();

        Float litros_ani = Float.valueOf(litros);

        Date hoy = new Date();

        long diferencia = ( hoy.getTime() - nacAni.getTime() )/MILLSECS_PER_DAY;


        Float ganancia = (peso_a - peso_n)/diferencia;

        Log.i("Ganancia", ""+ganancia);


        String imagen = encoded;
        idfinca =  preferences.getLong("idfinca", 1);

        animal.setNombre(nombre);
        animal.setNacimiento(nacAni);
        animal.setGanancia(ganancia);
        animal.setPeso_al_nacer(peso_n);
        animal.setPeso(peso_a);
        animal.setSexo(sexo);
        animal.setId_finca(idfinca);
        animal.setImagen(imagen);
        animal.setLitros_diarios(litros_ani);
        animal.setRaza(raza);
        animal.setTipo(tipo);


        if(edit == 0){
            dao.insert(animal);
            finish();
        }else {
            dao.update(animal);
            finish();
        }



    }
    //endregion

    //region Datepicker
    /**  public Dialog onCreateDialog(Bundle savedInstanceState) {
     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
     builder.setTitle(R.string.pick_color)
     .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int which) {
     // The 'which' argument contains the index position
     // of the selected item
     }
     });
     return builder.create();
     }**/

    /**
     * This callback method, call DatePickerFragment class,
     * DatePickerFragment class returns calendar view.
     * @param view
     */
    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    /**
     * To set date on TextView
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        binding.nacimiento.getEditText().setText(dateFormat.format(calendar.getTime()));

    }


    /**
     * To receive a callback when the user sets the date.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    /**
     * Create a DatePickerFragment class that extends DialogFragment.
     * Define the onCreateDialog() method to return an instance of DatePickerDialog
     */
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }
    //endregion



}
