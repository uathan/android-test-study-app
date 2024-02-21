package com.example.androidteststudyapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidteststudyapp.RecyclerView.PersonDatabase;
import com.example.androidteststudyapp.pojo.Person;

public class CreateUpdateFragment extends Fragment {

    public static final int UPDATE = 1;
    public static final int CREATE = 2;
    public static final String PERSON = "person"; //name for the name/value pair that represent the location
    public static final String ACTION_TYPE = "action_type";
    //name for name/value pair that will represent the action (UPDATE OR CREATE)
    Person person;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_update, container, false);
        EditText fname = view.findViewById(R.id.fnameText);
        EditText lname = view.findViewById(R.id.lnameText);
        EditText age = view.findViewById(R.id.ageText);
        Button submit = view.findViewById(R.id.submitButton);

        if(getArguments() != null){
            if(getArguments().getInt(ACTION_TYPE) == UPDATE) {
                submit.setText("Update Person");
                person = getArguments().getParcelable(PERSON);
                fname.setText(fname.getText());
                lname.setText(lname.getText());
                age.setText(age.getText());
            }
            else{
                person = new Person();
                submit.setText("Create Person");
            }
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    person.setFname(fname.getText().toString());
                    person.setLname(lname.getText().toString());
                    person.setAge(age.getText().toString());

                    PersonDatabase db = new PersonDatabase(getContext());
                    if(getArguments().getInt(ACTION_TYPE )== UPDATE){
                        db.updatePerson(person);
                    }
                    else if(getArguments().getInt(ACTION_TYPE )== CREATE){
                        db.addPerson(person);
                    }
                    db.close();
                    Navigation.findNavController(view).popBackStack();
                    ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        }


        return view;
    }
}