package algonquin.cst2335.dz0001;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class second_activity extends AppCompatActivity {

    EditText editText;
    Button profileImage ;
    ImageButton captureMage;
    String filename = "Picture.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView textView = findViewById(R.id.textView);
        profileImage = findViewById(R.id.button2);
        captureMage = findViewById(R.id.captureMage);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        textView.setText("welcome back  " + emailAddress);


        Button cal = findViewById(R.id.button);
        editText = findViewById(R.id.editTextPhone);

        captureMage.setOnClickListener(e -> {
            dispatchTakePictureIntent();
        });


        cal.setOnClickListener(clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            String phoneNumber = editText.getText().toString(); //input user text
            call.setData(Uri.parse("tel:" + phoneNumber));

            startActivity(call);
        });

        profileImage.setOnClickListener(clk -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String phoneNumber = editText.getText().toString();

            //intent
            startActivityForResult(cameraIntent, 5432);
        });

     File file = new File(filename);
        if(file.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile( filename);
            captureMage.setImageBitmap( theImage );
        }
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            FileOutputStream fOut = null;

            File dest = new File(Environment.getExternalStorageDirectory(), filename);
            File sd = Environment.getExternalStorageDirectory();

            Bitmap bitmap = (Bitmap)data.getExtras().get("data");

            try {
                fOut = openFileOutput( filename, Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }




            private void dispatchTakePictureIntent() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 3456);
                }
            }

        }








