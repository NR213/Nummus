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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class GalleryFragment extends Fragment {
    DatePickerDialog.OnDateSetListener mDateSetListenerview;
    EditText mDisplayDateview;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        View nav_vw = inflater.inflate(R.layout.fragment_gallery, container, false);
        EditText doT;
        Button view;
        DBHelper DB = new DBHelper(getContext());
        view = nav_vw.findViewById(R.id.btnView1);
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
                    buffer.append("doT : "+res.getString(0)+"\n");
                    buffer.append("Time : "+res.getString(1)+"\n");
                    buffer.append("amount : "+res.getString(2)+"\n");
                    buffer.append("reference : "+res.getString(3)+"\n");
                    buffer.append("paymentMethod : "+res.getString(4)+"\n");
                    buffer.append("note : "+res.getString(5)+"\n");
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}