package com.example.senportable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoricFragment extends Fragment
{
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_historic, container, false);

        db = FirebaseFirestore.getInstance();
        db.collection("Coordonnee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    ListView listView = (ListView) view.findViewById(R.id.list);
                    List<String> donnees = new ArrayList<>();

                    for(QueryDocumentSnapshot snapshot : task.getResult())
                    {
                        String d = snapshot.getData().get("address").toString() + "\n" + snapshot.getData().get("latitude").toString() + " " + snapshot.getData().get("longitude").toString() + "\n" + snapshot.getData().get("date").toString();
                        donnees.add(d);
                    }

                    ArrayAdapter listAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, donnees);
                    listView.setAdapter(listAdapter);
                }
                else
                {

                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}