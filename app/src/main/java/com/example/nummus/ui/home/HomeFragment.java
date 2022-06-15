package com.example.nummus.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nummus.DBHelper;
import com.example.nummus.R;
import com.example.nummus.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    TextView text;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
         View v = inflater.inflate(R.layout.fragment_home, container, false);
        DBHelper DB = new DBHelper(getContext());
        text = v.findViewById(R.id.text_home);
        text.setText("Euro: " + DB.sumAmount());

        Cursor res = DB.getprimarykeyearnings();
        if(res.getCount()==0){



        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
            buffer.append("costkey: " + res.getString(0) + "\n");

            buffer.append("-------------------------------" + "\n");
        }
        text = v.findViewById(R.id.textView2);
        text.setText(buffer.toString());
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}