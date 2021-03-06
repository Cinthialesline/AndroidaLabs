package algonquin.cst2335.dz0001;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    SharedPreferences prefs ;
    String savedString;
    String lol;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);  //sharepref

         savedString = prefs.getString("VariableName", "");

        Log.w("MainActivity", "In onCreate() - Loading Widgets" );


        Button loginButton= findViewById(R.id.loginButton);
         emailEditText= findViewById(R.id.emailEditText);

        emailEditText.setText(savedString);

        loginButton.setOnClickListener(  clk -> {

            saveSharedPrefs(emailEditText.getText().toString());

            Intent nextPage = new Intent( this, second_activity.class);


            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString());
            startActivity(nextPage);
        } );
    }

    private void saveSharedPrefs(String toString) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ReserveName", toString);
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( "MainActivity","The application is now visible on the screen");

    }
    @Override
    protected void onResume() { //application is now responding to user input
        super.onResume();
        Log.w( "MainActivity","called when activity will start interacting with the user.");

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.w( "MainActivity","called when activity is not visible to the user");


    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.w( "MainActivity","called when activity is no longer visible to the user");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( "MainActivity","called before the activity is destroyed.");

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w( "MainActivity","called after your activity is stopped, prior to start");

    }


}