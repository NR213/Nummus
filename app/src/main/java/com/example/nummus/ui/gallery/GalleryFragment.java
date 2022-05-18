package com.example.nummus.ui.gallery;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nummus.DBHelper;
import com.example.nummus.DeleteActivity;
import com.example.nummus.MainActivity;
import com.example.nummus.R;
import com.example.nummus.ViewActivity;
import com.example.nummus.databinding.FragmentSlideshowBinding;

import java.util.Calendar;

public class GalleryFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    DatePickerDialog.OnDateSetListener mDateSetListenerview;
    EditText mDisplayDateview;
    String filterlistTxt;
    TextView data1, data2;
    private FragmentSlideshowBinding binding;

    View nav_vw;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        nav_vw = inflater.inflate(R.layout.fragment_gallery, container, false);
        EditText doT;
        Button view;

        view = nav_vw.findViewById(R.id.btnView1);
        data1 = nav_vw.findViewById(R.id.text_data1);

        DBHelper DB = new DBHelper(getContext());
        Spinner filterList = nav_vw.findViewById(R.id.filter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Filterlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterList.setAdapter(adapter);
        filterList.setOnItemSelectedListener(this);
        mDisplayDateview = (EditText) nav_vw.findViewById(R.id.txtview);

        mDisplayDateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerview,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerview = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String dateview = month + "/" + day + "/" + year;
                mDisplayDateview.setText(dateview);
            }
        };

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doTTxt = mDisplayDateview.getText().toString();
                Cursor res = DB.getdata(doTTxt);
                if(res.getCount()==0){
                    Toast.makeText(getContext(), "No Transaction Exists", Toast.LENGTH_SHORT).show();

                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Date: " + res.getString(0) +"\n");
                    buffer.append("Time: "+res.getString(1) +"\n");
                    buffer.append("Amount: "+res.getString(2) +"\n");
                    buffer.append("Reference: "+res.getString(3) +"\n");
                    buffer.append("PaymentMethod: "+res.getString(4) +"\n");
                    buffer.append("Note: "+res.getString(5));

                }



                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });

        return nav_vw;
    }

//        final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        filterlistTxt = adapterView.getItemAtPosition(i).toString();
        DBHelper DBfilter = new DBHelper(getContext());
        Cursor res = DBfilter.getfilterdata(filterlistTxt);
        if(res.getCount()==0){
            //Toast.makeText(getContext(), "No Transaction Exists", Toast.LENGTH_SHORT).show();
            data1.setText("Null");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append(res.getString(0) +" ");
            buffer.append(res.getString(1) +" ");
            buffer.append(res.getString(2) +" ");
            buffer.append(res.getString(4) + "\n");

        }
        data1.setText(buffer.toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        data1.setText("----");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}