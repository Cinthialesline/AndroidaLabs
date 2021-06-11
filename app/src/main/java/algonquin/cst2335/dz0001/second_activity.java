package algonquin.cst2335.dz0001;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class second_activity extends AppCompatActivity {
    EditText editText;
    Button profileImage = findViewById(R.id.button2);
    ImageView captureMage=findViewById(R.id.captureMage);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView textView = findViewById(R.id.textView);


        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        textView.setText("welcome back" + emailAddress);


        Button cal = findViewById(R.id.button);
        editText = findViewById(R.id.editTextPhone);


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
            startActivityForResult( cameraIntent, 5432);
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==3456){
        if (resultCode==RESULT_OK){
         //   Bundle extras = data.getExtras();
            Bitmap thumbnail =data.getParcelableExtra("data");
            captureMage.setImageBitmap(thumbnail);


        }
            }
           /* if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mImageButton.setImageBitmap(imageBitmap);
            }*/
        }

    }


