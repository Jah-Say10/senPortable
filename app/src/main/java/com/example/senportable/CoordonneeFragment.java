package com.example.senportable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CoordonneeFragment extends Fragment
{
    private FirebaseFirestore db;
    List<String> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_coordonnee, container, false);

        Bundle bundle = getArguments();
        String  coordonnees = bundle.getString("data");

        data = Arrays.asList(coordonnees.split("\\s*;\\s*"));

        TextView latVal = view.findViewById(R.id.lat_val);
        latVal.setText(data.get(0));

        TextView lonVal = view.findViewById(R.id.lon_val);
        lonVal.setText(data.get(1));

        TextView paysVal = view.findViewById(R.id.pays_val);
        paysVal.setText(data.get(2));

        TextView villeVal = view.findViewById(R.id.ville_val);
        villeVal.setText(data.get(3));

        TextView addVal = view.findViewById(R.id.add_val);
        addVal.setText(data.get(4));

        db = FirebaseFirestore.getInstance();

        Button btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String date = new SimpleDateFormat("dd-MM-YYYY: HH:mm:ss").format(Calendar.getInstance().getTime());
                Coordonnee coordonnee = new Coordonnee(Double.valueOf(data.get(0)), Double.valueOf(data.get(1)), data.get(2), data.get(3), data.get(4), date);

                CollectionReference reference = db.collection("Coordonnee");
                reference.document().set(coordonnee).addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void unused)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Les donnes sont enregistrees avex success.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Echec dans l'enregistrement, Reessayer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}