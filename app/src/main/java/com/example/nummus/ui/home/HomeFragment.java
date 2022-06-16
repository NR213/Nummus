package com.example.nummus.ui.home;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nummus.DBHelper;
import com.example.nummus.R;
import com.example.nummus.databinding.FragmentHomeBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    TextView text;

    private boolean userIsInteracting;

    private PieChart pieChart;
    private PieChart CostPointer;

    Button mButton;
    EditText mEdit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        DBHelper DB = new DBHelper(getContext());
        text = v.findViewById(R.id.text_home);
        text.setText("Expenses: " + DB.TestAmount());

        pieChart = v.findViewById(R.id.MainActivity_PiechartCost);
        setupGoals();

        mButton = (Button) v.findViewById(R.id.btnrefresh);
        mEdit = (EditText) v.findViewById(R.id.Costgoal);

        //String TestGoalVals = DB.costgoal("expense");
        //if (TestGoalVals == null){
            //boolean Goalinsertion = DB.insertGoalsdata(1,"expense","800");

        //}

        String GoalVals = mEdit.getText().toString();

        if (GoalVals == null){
            GoalVals = "850";
        }

        float GoalVal = Float.parseFloat(GoalVals);

        CostPointer = v.findViewById(R.id.MainActivity_PiechartCostPointer);
        pointersetup(GoalVal);


        mButton.setOnClickListener(

                new View.OnClickListener() {
                    public void onClick(View view) {

                        String GoalVals =  mEdit.getText().toString();

                        boolean Goalinsert = DB.insertGoalsdata(1, "expense",GoalVals);

                        float GoalVal = Float.parseFloat(GoalVals);

                        if (GoalVals.equals("")) {
                            Toast.makeText(v.getContext(), "Set goal!", Toast.LENGTH_SHORT).show();
                        } else {

                            pointersetup(GoalVal);

                            mEdit.setText(String.valueOf(GoalVals));

                            //Toast.makeText(v.getContext(), GoalVals, Toast.LENGTH_SHORT).show();
                        }

                   }
                });

        return v;
    }



    private void pointersetup(float GoalVal){
        CostPointer.setDrawHoleEnabled(false);
        CostPointer.setUsePercentValues(true);
        CostPointer.setEntryLabelTextSize(0);
        CostPointer.setEntryLabelColor(Color.BLACK);
        CostPointer.getDescription().setEnabled(false);
        CostPointer.setRotationAngle(180);
        Legend leg = CostPointer.getLegend();
        leg.setEnabled(false);

        DBHelper DB = new DBHelper(getContext());
        ArrayList<PieEntry> entries = DB.CostPointer(GoalVal);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.TRANSPARENT);
        colors.add(Color.BLACK);
        colors.add(Color.TRANSPARENT);
        colors.add(Color.TRANSPARENT);

        PieDataSet dataSet = new PieDataSet(entries, "pointer");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.BLACK);

        CostPointer.setData(data);
        CostPointer.invalidate();

        CostPointer.animateY(1400, Easing.EaseInOutQuad);


    }


     private void setupGoals(){
         pieChart.setDrawHoleEnabled(true);
         pieChart.setUsePercentValues(true);
         pieChart.setEntryLabelTextSize(0);
         pieChart.setEntryLabelColor(Color.BLACK);
         pieChart.setCenterText("Monthly performance");
         pieChart.setCenterTextSize(14);
         pieChart.getDescription().setEnabled(false);
         pieChart.setRotationAngle(180);

         Legend l = pieChart.getLegend();
         //l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
         //l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
         //l.setOrientation(Legend.LegendOrientation.VERTICAL);
         //l.setDrawInside(false);
         l.setEnabled(false);

         //DBHelper DB = new DBHelper(getContext());

         //ArrayList<PieEntry> entries = DB.CostGoal();


         ArrayList<PieEntry> Gentries = new ArrayList<>();
         Gentries.add(new PieEntry( 0.25f, "Green"));
         Gentries.add(new PieEntry( 0.15f, "Yellow"));
         Gentries.add(new PieEntry( 0.10f, "Red"));
         Gentries.add(new PieEntry( 0.50f, "white"));

        //ArrayList<Integer> colors = new ArrayList<>();
        //for (int color: ColorTemplate.MATERIAL_COLORS){
            //colors.add(color);
        //}
        //for (int color: ColorTemplate.VORDIPLOM_COLORS){
            //colors.add(color);
        //}

         ArrayList<Integer> colors = new ArrayList<>();
         colors.add(Color.GREEN);
         colors.add(Color.YELLOW);
         colors.add(Color.RED);
         colors.add(Color.TRANSPARENT);
         colors.add(Color.BLACK);


         PieDataSet dataSet = new PieDataSet(Gentries, "Expense Category");

         dataSet.setColors(colors);

         PieData data = new PieData(dataSet);
         data.setDrawValues(false);
         data.setValueFormatter(new PercentFormatter(pieChart));
         data.setValueTextSize(8f);
         data.setValueTextColor(Color.BLACK);

         pieChart.setData(data);
         pieChart.invalidate();
         pieChart.animateY(1400, Easing.EaseInOutQuad);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}