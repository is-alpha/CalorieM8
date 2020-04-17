package com.dingo.caloriem8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private String placeholder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            placeholder = savedInstanceState.getString("placeholder");
    }

    /* Lo Original es solo el onCreateView lo demas es parte del TEST */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    /* Original */

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view != null) {
            TextView someText = (TextView)view.findViewById(R.id.placeholder);
            someText.setText(placeholder);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putString("placeholder", placeholder);
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
