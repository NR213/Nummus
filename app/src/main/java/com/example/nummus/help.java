package com.example.nummus;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nummus.databinding.FragmentHomeBinding;

import java.util.Calendar;

public class help extends Fragment {

    private FragmentHomeBinding binding;

    EditText helpmenu;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        View add = inflater.inflate(R.layout.fragment_help, container, false);
        String text = "Home tab\n" +
                "On this page, you can check out how to use the \"Home tab\" more efficiently, and with these great features, you can fully enjoy Nummus.\n" +
                "1. Add records \n" +
                "You can input new data for Income/Expenses.\n" +
                "How to add an income & expense:\n" +
                "How to add an income\n" +
                "Choose the Add tab from the menu tab to open up the page to record your income and expenses.\n" +
                "Select the “Income” button, the input page for registering income consists of:\n" +
                "* Date\n" +
                "* Time\n" +
                "* Currency\n" +
                "* Amount \n" +
                "* Payment Method\n" +
                "* Note\n" +
                "It is mandatory for you to specify the date, time, and amount. \n" +
                "How to add an expense\n" +
                "Income/expense follows somewhat similar recording methods.\n" +
                "Just like the income registration, tap the “Expense” button. Fill in the information just like the income registration.\n" +
                "* Date\n" +
                "* Time\n" +
                "* Currency\n" +
                "* Amount \n" +
                "* Payment Method\n" +
                "* Note\n" +
                "* You can fill in the note if you wish to specify the details of the spending that you have just recorded. \n" +
                " \n" +
                "2. View & Delete Transactions\n" +
                "How to view & Delete transaction:\n" +
                "With this feature, you can view or delete your transactions.\n" +
                "1) Choose the view & delete tab from the menu tab to open up the page.\n" +
                "2) you can view the transactions using the below options:\n" +
                "* Date\n" +
                "* type of transaction (Income-expense)\n" +
                "* payment methods\n" +
                "* Minimum or maximum amount \n" +
                "Then you have to click the “view details” button to view the transactions.\n" +
                "3. Update or delete a transaction.\n" +
                "with this feature, you can delete a transaction by inputting the date of the transaction and viewing them and deleting the desired transactions.\n" +
                "4. Reports \n" +
                "How to use the reports feature for income & expenses\n" +
                "Using the reports feature, you can review your transaction for the last 7 days, 15 days, 30 days, 6 months and 1 year.\n" +
                "Home tab > Reports button\n" +
                "On the top, you can choose the desired period and review the data based on that.\n" +
                "Below you can see the charts which will generate the data. You can also review your data using many other charts by swapping the chart.\n" +
                " \n" +
                " \n" +
                "How to set up a Cost Goal\n" +
                "Having a cost goal helps you manage your money, control your spending, savings , pay off debt, or stay out of debt.  \n" +
                "On the \"Home Page\", you can find the \"cost goal\" on the top\n" +
                "You will also see the total amount of your expenses as well as a gage chart which will help you to see the flow.\n";
        helpmenu = add.findViewById(R.id.help1);
        helpmenu.setText(text);


        return add;
    }




//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}