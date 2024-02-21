package com.example.androidteststudyapp.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidteststudyapp.CreateUpdateFragment;
import com.example.androidteststudyapp.R;
import com.example.androidteststudyapp.pojo.Person;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<Person> people;
    private Context context;

    public CustomAdapter(ArrayList<Person> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Person person = people.get(position);
        holder.fname.setText(person.getFname());
        holder.lname.setText(person.getLname());
        holder.age.setText(person.getAge());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extra = new Bundle();
                extra.putInt(CreateUpdateFragment.ACTION_TYPE,
                        CreateUpdateFragment.UPDATE);
                extra.putParcelable(CreateUpdateFragment.PERSON,
                        people.get(holder.getLayoutPosition()));
                Navigation.findNavController(view)
                        .navigate(R.id.createUpdateFragment, extra);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (people != null){
            return people.size();
        }
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        protected TextView fname;
        protected TextView lname;
        protected TextView age;
        protected ImageView edit;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.fname = view.findViewById(R.id.fname);
            this.lname = view.findViewById(R.id.lname);
            this.age = view.findViewById(R.id.age);
            this.edit = view.findViewById(R.id.edit);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete " +
                            people.get(getLayoutPosition()).getFname() + "?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PersonDatabase db = new PersonDatabase(context);
                            db.deletePerson(people.get(getLayoutPosition()).getId());
                            people.remove(getLayoutPosition());
                            notifyItemRemoved(getAdapterPosition());
                            db.close();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return false;
        }
    }
}
