package algonquin.cst2335.dz0001;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {

    RecyclerView chatList;
    ArrayList<ChatMessage> messages = new ArrayList<>();//hold our typed messages
    MyChatAdapter adt;
    ImageView imageView;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout); //load xml

        EditText edittext = findViewById(R.id.edit);
       submit = findViewById(R.id.sendbutton);
        Button recieve = findViewById(R.id.buttonR);
        imageView=findViewById(R.id.image);
        chatList = findViewById(R.id.myrecycler);

        adt=new MyChatAdapter();
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));


        submit.setOnClickListener(click -> {
            String whatisType=edittext.getText().toString();
                Date timeNow =new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd-MMM-yyyy-hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);

            messages.add(new ChatMessage(whatisType,currentDateandTime));//adds to array list
                    //clear the edittext:
            edittext.setText("");
                    //refresh the list:
                    adt.notifyItemInserted(messages.size() - 1); //just insert the new row:
                }
        );
    }


    // create a class to represent a row
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageView;
        TextView timeView;
        int position=-1;

        public MyViewHolder(View itemView) { //the view pass out represnt the constraint layout wc is the root of the row
            super(itemView);
            messageView=itemView.findViewById(R.id.textView);
            timeView=itemView.findViewById(R.id.time);

            itemView.setOnClickListener(click->{
                int position=getAbsoluteAdapterPosition(); //had to check with the build in gradle
                ChatMessage whatWasClicked =messages.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setTitle("Question:");
                builder.setMessage("Do you want to delete this message:" + whatWasClicked.getMessage());
                builder.setNegativeButton("No", (dialog,cl1)->{ });

                builder.setPositiveButton("Yes", (dialog,cl2)->{
                    messages.remove(position);
                    adt.notifyItemRemoved(position); //remove and update the recycle view
                                  //anything on screen at the present moment
                    Snackbar.make(submit, "Do you want to delete item #"+ position,  Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk->{
                              //  messages.remove(position,removedMessage); //stoes message before is removed from ArrayList
                                messages.add(position, whatWasClicked);
                                adt.notifyItemRemoved(position);
                            })
                            .show();
                });
                builder.create().show();

            });
            messageView = itemView.findViewById(R.id.message); // qtn so we aren't using the sent_message layout but recycler
            timeView = itemView.findViewById(R.id.time);      // so were are all this ids coming from
        }
        public void setPosition(int p){
            position=p;
        }

    }


    private class MyChatAdapter extends RecyclerView.Adapter<MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater in = getLayoutInflater(); //IS TO LOAD A LAYOUT FROM SEND_MESSAGE.XML FILE
            View loadedRow;
            if (viewType==1)
                loadedRow = in.inflate(R.layout.sent_message, parent, false);
                else
             loadedRow = in.inflate(R.layout.recieve_message, parent, false);// CONSTRUCTOR to inflate the view
            return new MyViewHolder(loadedRow);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) { //need an arraylist to holder

            ChatMessage thisRow=messages.get(position);
            holder.timeView.setText( thisRow.getTimeSent() );//what time goes on row position
            holder.messageView.setText( thisRow.getMessage() );//what message goes on row position


        }

        @Override
        public int getItemCount() {
            return messages.size();
        }


        public int getItemViewType(int position) {
           // messages.get(position).getSentOrRecieve();// will return an interger

            return 0;
        }
    }


    // data object also known as pojo's
    private class ChatMessage {

        String message;
       // int sendOrReceive;
        String timeSent;

        public ChatMessage(String message, String timeSent) {
            this.message = message;
          //  this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }


        public String getTimeSent() {

            return timeSent;
        }

        public String getMessage() {
            return message;
        }

       /// public int getSendOrReceive() {
           // return sendOrReceive;
       // }
    }

}

