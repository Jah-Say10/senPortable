package com.example.senportable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IPFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_i_p, container, false);

        Bundle bundle = getArguments();
        String ipAddress = bundle.getString("data");

        TextView textView = view.findViewById(R.id.ip_view);
        textView.setText(ipAddress);

        // Inflate the layout for this fragment
        return view;
    }
}