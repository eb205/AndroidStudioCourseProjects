package com.example.android.fragmentexample2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleFragment extends Fragment {

    public SimpleFragment() {
        // Required empty public constructor
    }

    public static SimpleFragment newInstance() {
        SimpleFragment fragment = new SimpleFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragment = inflater.inflate(R.layout.fragment_simple, container, false);
        final TextView text = fragment.findViewById(R.id.fragment_header);
        RadioGroup radioGroup = fragment.findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_button_yes:
                        text.setText(R.string.yes_message);
                        break;
                    case R.id.radio_button_no:
                        text.setText(R.string.no_message);
                        break;

                }
            }
        });

        return fragment;
    }
}