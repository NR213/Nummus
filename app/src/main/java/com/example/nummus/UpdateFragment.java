package com.example.nummus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nummus.DBHelper;
import com.example.nummus.DeleteActivity;
import com.example.nummus.MainActivity;
import com.example.nummus.R;
import com.example.nummus.databinding.FragmentSlideshowBinding;

import java.util.Calendar;

public class UpdateFragment extends Fragment {
    DatePickerDialog.OnDateSetListener mDateSetListenerdelete;
    EditText mDisplayDatedelete;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        View v = inflater.inflate(R.layout.fragment_update, container, false);
        EditText doT;
        Button update;
        DBHelper DB = new DBHelper(getContext());
        update = v.findViewById(R.id.btndelupdate);

        mDisplayDatedelete = (EditText) v.findViewById(R.id.txtdelupdate);

        mDisplayDatedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerdelete,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerdelete = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String dateview = month + "/" + day + "/" + year;
                mDisplayDatedelete.setText(dateview);
            }
        };

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dotTXT = mDisplayDatedelete.getText().toString();
                Boolean checkudeletedata = DB.deletedata(dotTXT);
                if(checkudeletedata==true)
                    Toast.makeText(getContext(), "Transaction Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Transaction Doesn't Exist", Toast.LENGTH_SHORT).show();
            }        });

        return v;
    }

//        final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}