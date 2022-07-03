package com.example.nummus.ui.gallery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nummus.DBHelper;
import com.example.nummus.MyAdapter;
import com.example.nummus.R;
import com.example.nummus.RecyclerInterface;
import com.example.nummus.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class GalleryFragment extends Fragment implements AdapterView.OnItemSelectedListener, RecyclerInterface {
    DatePickerDialog.OnDateSetListener mDateSetListenerview;
    EditText mDisplayDateview;
    String filterlistTxt, doTTxt, categoryincome, categoryexpense, paymentlist;
    TextView data1, data2;
    RecyclerView recyclerView;
    Spinner categoryspincome, categorysp;
    ArrayList<String> Date, Time, Amount, PaymentMethod, Note, Category, Currency, key, Category1;
    MyAdapter adapter1;
    private FragmentSlideshowBinding binding;

    View nav_vw;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        nav_vw = inflater.inflate(R.layout.fragment_gallery, container, false);
        EditText doT, min, max;
        Button view;

        view = nav_vw.findViewById(R.id.btnView1);
        //data1 = nav_vw.findViewById(R.id.text_data1);

        DBHelper DB = new DBHelper(getContext());
        Spinner filterList = nav_vw.findViewById(R.id.filter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Filterlist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterList.setAdapter(adapter);
        filterList.setOnItemSelectedListener(this);
        Spinner paymentlist = nav_vw.findViewById(R.id.paymentMethoddrop);
        ArrayAdapter<CharSequence> adapterpm = ArrayAdapter.createFromResource(getContext(), R.array.pmdrop, android.R.layout.simple_spinner_item);
        adapterpm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentlist.setAdapter(adapterpm);
        paymentlist.setOnItemSelectedListener(this);

        categoryspincome = nav_vw.findViewById(R.id.incomecat);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getContext(), R.array.CategoryEarnings, android.R.layout.simple_spinner_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryspincome.setAdapter(adapters);
        categoryspincome.setOnItemSelectedListener(this);
        categoryspincome.setEnabled(true);

        categorysp = nav_vw.findViewById(R.id.expensecat);
        ArrayAdapter<CharSequence> adapterc = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorysp.setAdapter(adapterc);
        categorysp.setOnItemSelectedListener(this);
        categorysp.setEnabled(false);
        Date = new ArrayList<>();
        Time = new ArrayList<>();
        Amount = new ArrayList<>();
        PaymentMethod = new ArrayList<>();
        Note = new ArrayList<>();
        Category = new ArrayList<>();
        Currency = new ArrayList<>();
        key = new ArrayList<>();

        recyclerView = nav_vw.findViewById(R.id.recyclerview);
        adapter1 = new MyAdapter(getContext(),key, Date, Time, Amount,PaymentMethod,Note,Category, Currency, this);
        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


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

//

        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
               doTTxt = mDisplayDateview.getText().toString();

                Date.clear();
                        Time.clear();
                        Amount.clear();
                        PaymentMethod.clear();
                        Note.clear();
                        Category.clear();
                        Currency.clear();
                        key.clear();


                displaydata();
                adapter1.notifyDataSetChanged();

//
            }
            });

        return nav_vw;
    }

//        final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.filter){
            filterlistTxt = adapterView.getItemAtPosition(i).toString();
            if(filterlistTxt.equals("expense")){
                categorysp.setEnabled(true);
                categoryspincome.setEnabled(false);
            }else{

                categorysp.setEnabled(false);
                categoryspincome.setEnabled(true);
            }
        }
        if(adapterView.getId() == R.id.paymentMethoddrop){
            paymentlist = adapterView.getItemAtPosition(i).toString();
        }
        if(adapterView.getId() == R.id.incomecat){
            categoryincome = adapterView.getItemAtPosition(i).toString();
        }

        if(adapterView.getId() == R.id.expensecat){
            categoryexpense = adapterView.getItemAtPosition(i).toString();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void displaydata()
    {
        DBHelper DB = new DBHelper(getContext());
        Cursor cursor = DB.getViewdata(doTTxt, filterlistTxt, categoryincome, categoryexpense, paymentlist);
        if(cursor.getCount()==0)
        {
            Toast.makeText(getContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                key.add(cursor.getString(0));
                Date.add(cursor.getString(1));
                Time.add(cursor.getString(2));
                Amount.add(cursor.getString(3));
                Currency.add(cursor.getString(4));
                PaymentMethod.add(cursor.getString(6));
              Note.add(cursor.getString(7));
              Category.add(cursor.getString(8));

            }
        }
    }

    @Override
    public void onClick(int position) {
        String val = key.get(position);
        datadelete(val);
        key.remove(position);
        Time.remove(position);
        Date.remove(position);
        Amount.remove(position);
        Currency.remove(position);
        PaymentMethod.remove(position);
        Note.remove(position);
        Category.remove(position);
        adapter1.notifyItemRemoved(position);
        adapter1.notifyItemRangeChanged(position, key.size());


    }

    public void datadelete(String val){
        DBHelper DB = new DBHelper(getContext());
        Boolean checkudeletedata = DB.deletedata(val);

        if(checkudeletedata==true)
            Toast.makeText(getContext(), "Transaction Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), "Transaction Doesn't Exist", Toast.LENGTH_SHORT).show();
    }
}