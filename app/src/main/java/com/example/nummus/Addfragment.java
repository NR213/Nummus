package com.example.nummus;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nummus.DBHelper;
import com.example.nummus.R;
import com.example.nummus.databinding.FragmentHomeBinding;

import java.util.Calendar;

public class Addfragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private FragmentHomeBinding binding;
    EditText amount, reference, doT, paymentMethod, note;
    Button insert, update, delete, view;
    String paymentmethodTXT;
    DBHelper DB;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText mDisplayDate;
    String date;
    EditText timeButton;
    int hour, minute;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        View add = inflater.inflate(R.layout.fragment_add2, container, false);

        amount = add.findViewById(R.id.amount);
        reference = add.findViewById(R.id.reference);

        //paymentMethod = findViewById(R.id.paymentMethod);
        Spinner paymentMethod = add.findViewById(R.id.paymentMethod);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.paymentMethodlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethod.setAdapter(adapter);
        paymentMethod.setOnItemSelectedListener(this);
        note = add.findViewById(R.id.note);
        insert = add.findViewById(R.id.btnInsert);
        update = add.findViewById(R.id.btnUpdate);

        DB = new DBHelper(getContext());

        timeButton = (EditText) add.findViewById(R.id.timemain);


        mDisplayDate = (EditText) add.findViewById(R.id.doTmain);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        String time = hour + ":" + minute;
                        timeButton.setText(time);
                    }
                };


                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true);

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountTXT = amount.getText().toString();
                String referenceTXT = reference.getText().toString();
                String time = timeButton.getText().toString();
                String dotTXT = mDisplayDate.getText().toString();
                //String paymentmethodTXT = paymentMethod.toString();

                String noteTXT = note.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(dotTXT, time, amountTXT, referenceTXT, paymentmethodTXT, noteTXT);
                if (checkinsertdata == true)
                    Toast.makeText(getContext(), "New Transaction Added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "New Transaction Not Added", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountTXT = amount.getText().toString();
                String referenceTXT = reference.getText().toString();
                String time = timeButton.getText().toString();
                String dotTXT = mDisplayDate.getText().toString();
                // String paymentmethodTXT = paymentMethod.toString();
                String noteTXT = note.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(dotTXT, time, amountTXT, referenceTXT, paymentmethodTXT, noteTXT);
                if (checkupdatedata == true)
                    Toast.makeText(getContext(), "Transaction Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "New Transaction Not Updated", Toast.LENGTH_SHORT).show();
            }
        });
        return add;
    }




//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        paymentmethodTXT = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}