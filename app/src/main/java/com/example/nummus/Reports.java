package com.example.nummus;//package com.example.nummus.ui.slideshow;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.util.SparseIntArray;
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
import com.example.nummus.databinding.FragmentReportsBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
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
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Reports extends Fragment implements AdapterView.OnItemSelectedListener{

    private FragmentReportsBinding binding;
    TextView text;
    private boolean userIsInteracting;

    private PieChart pieChart;
    private PieChart CostPointer;
    private LineChart lineChart;

    Button mButton;
    EditText mEdit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReportsBinding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.fragment_reports, container, false);

        Spinner Duration;

        Duration = v.findViewById(R.id.duration);

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(getContext(), R.array.rduration, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Duration.setAdapter(adapterType);
        Duration.setOnItemSelectedListener(this);

        String SelDuration = Duration.getSelectedItem().toString();

        lineChart = v.findViewById(R.id.MainActivity_Linechart);

        drawLineChart(SelDuration);


        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        drawLineChart(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void drawLineChart(String SelDuration)  {

        DBHelper DB = new DBHelper(getContext());

        ArrayList<Entry> lineEntries = DB.TimeSeriesData(SelDuration);

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Expense amount");
        LineData linedata = new LineData(lineDataSet);


        lineChart.setData(linedata);
        lineChart.notifyDataSetChanged();
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleRadius(7);
        lineDataSet.setCircleHoleRadius(4);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setHighLightColor(Color.BLUE);
        lineDataSet.setValueTextSize(20);
        lineDataSet.setValueTextColor(Color.BLUE);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);


        lineChart.getDescription().setTextSize(12);
        lineChart.getDescription().setEnabled(false);
        lineChart.animateY(1000);


        // Setup X Axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(false);
        xAxis.setGranularity(1.0f);
        xAxis.setXOffset(2f);
        xAxis.setDrawGridLines(false);

        // Setup Y Axis
        YAxis yAxis = lineChart.getAxisLeft();

        yAxis.setGranularity(2f);
        yAxis.setGridColor(Color.BLACK);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setGridLineWidth(1);
        yAxis.setTextSize(12);
        yAxis.setSpaceBottom(15);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
