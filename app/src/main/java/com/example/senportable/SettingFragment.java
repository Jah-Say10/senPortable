package com.example.senportable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class SettingFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        Switch s1 = view.findViewById(R.id.s1);
        Switch s2 = view.findViewById(R.id.s2);
        Switch s3 = view.findViewById(R.id.s3);
        Switch s4 = view.findViewById(R.id.s4);
        Switch s5 = view.findViewById(R.id.s5);
        Switch s6 = view.findViewById(R.id.s6);
        Switch s7 = view.findViewById(R.id.s7);

        s1.setChecked(true);
        s2.setChecked(true);
        s3.setChecked(true);
        s4.setChecked(true);
        s5.setChecked(true);
        s6.setChecked(true);
        s7.setChecked(true);

        // Inflate the layout for this fragment
        return view;
    }
}