package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.Button;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class NewEventFragment extends Fragment {

    private OnSubmitListener mListener;

    private Button mFromDateButton, mToDateButton, mFromTimeButton, mToTimeButton;

    public NewEventFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_event, container, false);

        mFromDateButton = (Button) root.findViewById(R.id.from_date_button);
        mToDateButton = (Button) root.findViewById(R.id.to_date_button);
        mFromTimeButton = (Button) root.findViewById(R.id.from_time_button);
        mToTimeButton = (Button) root.findViewById(R.id.to_time_button);

        setupButtonListener(mFromDateButton, true);
        setupButtonListener(mFromTimeButton, false);
        setupButtonListener(mToDateButton, false);
        setupButtonListener(mToTimeButton, false);

        return root;
    }

    private void setupButtonListener(Button b, boolean isForDate) {

        Calendar now = Calendar.getInstance();

        final DialogFragment picker;
        if (isForDate) {
            picker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            );
        }
        else {
            picker = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

                     }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
            );
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getFragmentManager(), "");
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_event, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            mListener.onSubmit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFromDatePressed() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSubmitListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Parent must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSubmitListener {
        public void onSubmit();
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("New Event");
    }
}
