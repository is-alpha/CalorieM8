package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MetasFragment extends Fragment {

    private DatabaseReference dbRef;
    public static ArrayList<String> metas = new ArrayList<>();
    private FirebaseAuth fAuth;
    private Button btnMeta;
    private ListView listaview;
    private static int i=1;


    public MetasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_metas, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        btnMeta = view.findViewById(R.id.frp_btnNewMeta);
        listaview = (ListView) view.findViewById(R.id.frp_lista);

        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                metas
        );
        listaview.setAdapter(listViewAdapter);

        btnMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new CrearMeta()).commit();
                metas.add("META"+i++);
                listViewAdapter.notifyDataSetChanged();
            }
        });

        listaview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }

        });


        return  view;

    }

    @Override
    public void onStart() {

        super.onStart();


    }

}
