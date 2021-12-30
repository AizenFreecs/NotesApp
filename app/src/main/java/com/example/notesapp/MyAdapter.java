package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Notes> notes;
    private OnNoteListener mOnNoteListener;

    DatabaseReference database ;


    public MyAdapter(Context context, ArrayList<Notes> notes,OnNoteListener onNoteListener) {
        this.context = context;
        this.notes = notes;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(v,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference(id);
      Notes note = notes.get(position);
      holder.titleOutput.setText(note.getTitle());
      holder.contentOutput.setText(note.getContent());
      String formatedTime = DateFormat.getDateTimeInstance().format(note.createTime);
      holder.timeOutput.setText(formatedTime);
      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(context,v);
                popup.inflate(R.menu.popup_menu);
                popup.show();
                Toast.makeText(context, "Note deleted.", Toast.LENGTH_SHORT).show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.delete){
                             String title = note.title;
                            database.child(String.valueOf(title)).removeValue();
                        }
                        return true;
                    };
                });
                return true;};
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleOutput ,contentOutput , timeOutput;
        OnNoteListener onNoteListener;
        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            contentOutput = itemView.findViewById(R.id.contentoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{

         void onNoteClick(int position);
    }
}
