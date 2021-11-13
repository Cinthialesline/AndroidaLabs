package algonquin.cst2335.dz0001;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {

    RecyclerView chatList;
    ArrayList<ChatMessage> messages = new ArrayList<>();//hold our typed messages
    MyChatAdapter adt;
    ImageView imageView;
    Button submit;
    MyOpenHelper opener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout); //load xml

        opener= new MyOpenHelper(this);

        //OPEN DATABASE
        final SQLiteDatabase theDatabase= opener.getWritableDatabase();
        Cursor results =  theDatabase.rawQuery( "Select * from " + MyOpenHelper.TABLE_NAME + ";", null );

        //Convert column names to indices:
        int idIndex = results.getColumnIndex( MyOpenHelper.COL_ID );
        int  messageIndex = results.getColumnIndex( MyOpenHelper.COL_MESSAGE);
        int sOrRIndex = results.getColumnIndex( MyOpenHelper.COL_SEND_RECEIVE);
        int timeIndex = results.getColumnIndex( MyOpenHelper.COL_TIME_SENT );

        while( results.moveToNext() ) //returns false if no more data
        { //pointing to row 2
            long id = results.getInt(idIndex);
            String message = results.getString( messageIndex );
            int sendOrRecieve = results.getInt(sOrRIndex);
            String time = results.getString( timeIndex);

            //add to arrayList:                                     //id
            messages.add(new ChatMessage(message,sendOrRecieve,time,id) );
        }


        EditText edittext = findViewById(R.id.edit);
        submit = findViewById(R.id.sendbutton);
        Button recieve = findViewById(R.id.buttonR);
        imageView=findViewById(R.id.image);
        chatList = findViewById(R.id.myRecycler);


        adt=new MyChatAdapter(messages);
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));


        submit.setOnClickListener(click -> {
            String whatisType=edittext.getText().toString();
            Date timeNow =new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd-MMM-yyyy-hh-mm-ss a", Locale.getDefault());
            String currentDateandTime = sdf.format(timeNow);

            ContentValues newRow = new ContentValues();// like intent or Bundle
//            //Message column:        //name of tabke name
           newRow.put( MyOpenHelper.COL_MESSAGE , whatisType  ); //got tospecify what goes into each rows
          newRow.put(MyOpenHelper.COL_SEND_RECEIVE, 1);
           newRow.put( MyOpenHelper.COL_TIME_SENT, currentDateandTime );
            long id = theDatabase.insert( MyOpenHelper.TABLE_NAME, MyOpenHelper.COL_MESSAGE, newRow );
            //messages.add(cm);
            edittext.setText("");
            ChatMessage cm=new ChatMessage(whatisType,1,currentDateandTime,id);
          messages.add(cm);
            edittext.setText("");
            //refresh the list:
              adt.notifyItemInserted(messages.size() - 1); //just insert the new row:*/
            adt.notifyDataSetChanged();
            Toast.makeText(this, "View Updated", Toast.LENGTH_LONG).show();

        });
    }


    // create a class to represent a row
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageView;
        TextView timeView;
        TextView textView2;
        TextView textView3;
        ImageView imageView;
        View view;

        public MyViewHolder(View itemView) { //the view pass out represnt the constraint layout wc is the root of the row
            super(itemView);
            messageView = itemView.findViewById(R.id.textView);
            timeView = itemView.findViewById(R.id.time);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);
            view = itemView;

           /* itemView.setOnClickListener(click->{
               // int position=getAbsoluteAdapterPosition(); //had to check with the build in gradle
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

            });*/
            //messageView = itemView.findViewById(R.id.message); // qtn so we aren't using the sent_message layout but recycler
            //timeView = itemView.findViewById(R.id.time);      // so were are all this ids coming from
        }

    }


    private class MyChatAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<ChatMessage> messageList;

        public MyChatAdapter(List<ChatMessage> messages){
            this.messageList = messages;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            //LayoutInflater in = getLayoutInflater(); //IS TO LOAD A LAYOUT FROM SEND_MESSAGE.XML FILE
            View loadedRow;
            viewType=1;
            if (viewType==1)
                loadedRow = inflater.inflate(R.layout.sent_message, parent, false);
            else
                loadedRow = inflater.inflate(R.layout.recieve_message, parent, false);// CONSTRUCTOR to inflate the view
            return new MyViewHolder(loadedRow);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) { //need an arraylist to holder

            System.out.println("Position: "+position);
            ChatMessage thisRow = messageList.get(position);
            System.out.println("Holder: "+holder);
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
        int sendOrReceive;
        String timeSent;
        long id;

        public ChatMessage(String message,int sendOrReceive, String timeSent, long id) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
            setId(id);
        }


        public String getTimeSent() {

            return timeSent;
        }
        public int getsendOrReceive() {

            return sendOrReceive;
        }
        public String getMessage() {
            return message;
        }
        public void setId(long id) {
            this.id = id;
        }
        public void getId(long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "ChatMessage{" +
                    "message='" + message + '\'' +
                    ", sendOrReceive=" + sendOrReceive +
                    ", timeSent='" + timeSent + '\'' +
                    ", id=" + id +
                    '}';
        }


    }

}

