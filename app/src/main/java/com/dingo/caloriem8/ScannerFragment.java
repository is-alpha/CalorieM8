package com.dingo.caloriem8;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

//https://www.youtube.com/watch?v=eSjDkxSecLw

public class ScannerFragment extends Fragment {

    private Button btnScanner;

    public ScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        btnScanner = view.findViewById(R.id.btnScanner);

        btnScanner.setOnClickListener(mOnClickListener);

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null)
            if(result.getContents() != null)
                Toast.makeText(getContext(), "Scanned: "+result.getContents(), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           /* if(v.equals(R.id.btnScanner))
                new IntentIntegrator(getActivity()).initiateScan();     //-------! POSIBLE ERROR EN PARAMETRO ACTIVIDAD !---------//*/
           IntentIntegrator intent = IntentIntegrator.forSupportFragment(ScannerFragment.this);
           intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
           intent.setPrompt("Escanear codigo");
           intent.setCameraId(0);
           intent.setBeepEnabled(false);
           intent.setBarcodeImageEnabled(false);
           intent.initiateScan();
        }
    };

}
