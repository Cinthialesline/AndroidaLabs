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
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {

    RecyclerView chatList;
    ArrayList<ChatMessage> messages = new ArrayList<>();//hold our typed messages
    MyChatAdapter adt;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        EditText messageTyped = findViewById(R.id.edit);
        Button send = findViewById(R.id.sendbutton);
        Button recieve = findViewById(R.id.buttonR);
        imageView=findViewById(R.id.image);
        chatList = findViewById(R.id.myrecycler);

        adt=new MyChatAdapter();
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));


        send.setOnClickListener(click -> {
            //ChatMessage thisMessage = new ChatMessage
            //( /* What string is in the EditText */, /* 1 for Send, 2 for Receive */,  /* The current time */ );
                    ChatMessage nextMessage = new ChatMessage(messageTyped.getText().toString(), );
                    messages.add(nextMessage);//adds to array list
                    //clear the edittext:
                    messageTyped.setText("");
                    //refresh the list:
                    adt.notifyItemInserted(messages.size() - 1); //just insert the new row:
                }
        );
    }


    // create a class to represent a row
    private class MyRowViews extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        int position=-1;

        public MyRowViews(View itemView) { //the view pass out represnt the constraint layout wc is the root of the row
            super(itemView);

            itemView.setOnClickListener(click->{
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setTitle("Question:");
                builder.setMessage("Do you want to delee the message:" + messageText.getText());
                builder.setNegativeButton("No", (dialog,cl)->{ });

                builder.setPositiveButton("Yes", (dialog,cl)->{

                    position=getAbsoluteAdapterPosition();
                    ChatMessage removedMessage=messages.get(position);

                    messages.remove(position);
                    adt.notifyItemRemoved(position); //remove and update the recycle view
                    Snackbar.make(messageText, "Do you want to delete the mssage:"+ position,  Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk->{
                                messages.remove(position,removedMessage); //stoes message before is removed from ArrayList
                                adt.notifyItemRemoved(position);
                            })
                            .show();
                });
                builder.create().show();

            });
            messageText = itemView.findViewById(R.id.message); // qtn so we aren't using the sent_message layout but recycler
            timeText = itemView.findViewById(R.id.time);      // so were are all this ids coming from
        }
        public void setPosition(int p){
            position=p;
        }

    }


    private class MyChatAdapter extends RecyclerView.Adapter {


        @Override
        public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater(); //IS TO LOAD A LAYOUT FROM SEND_MESSAGE.XML FILE
            int layoutID;
            if (viewType==1)
                //setImageBitmap(imageView);
                layoutID=R.layout.sent_message;
            else
                layoutID=R.layout.recieve_message;
            View loadedRow = inflater.inflate(R.layout.sent_message, parent, false);
            MyRowViews initRow = new MyRowViews(loadedRow); // CONSTRUCTOR NEEDED A VIEW? DON'T GET IT
            return initRow;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyRowViews thisRowLayout = (MyRowViews)holder;
            thisRowLayout.messageText.setText(messages.get(position).getMessage());
            thisRowLayout.timeText.setText((CharSequence) messages.get(position).getTimeSent()); //required me to return a java charsequence
            thisRowLayout.setPosition(position);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        @Override
             //ChatMessage
        public int getItemViewType(int position) {
            ChatMessage thisRow = messages.get(position);
           // return super.getItemViewType(position);
            return thisRow;
        }
    }


    // data object also known as pojo's
    private class ChatMessage {

        String message;
        int sendOrReceive;
        Date timeSent;

        public ChatMessage(String s) {
            message = s;
        }

        public ChatMessage(String message, int sendOrReceive, Date timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }


        public Date getTimeSent() {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(new Date());
            // timeSent =currentDateandTime;
            return timeSent;
        }

        public String getMessage() {
            return message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }
    }
}

