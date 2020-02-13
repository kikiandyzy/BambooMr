package com.example.bamboomr;




import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog dpd;//日期选择器
    private Context context;
    private static FragmentManager fragmentManager;

    public ScheduleFragment(Context context,FragmentManager f) {
        // Required empty public constructor
        this.context = context;
        fragmentManager = f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
               this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection

        );
        dpd.setAccentColor(getResources().getColor(R.color.bamboo));


        return view;


    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            dpd.show(fragmentManager, "Datepickerdialog");
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }


}
