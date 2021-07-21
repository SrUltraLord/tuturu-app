package com.example.clift.ui.tutor.ui.Request;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clift.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class requestFragment extends Fragment {
    List<ListElement> elements;
    private RequestViewModel mViewModel;

    public static requestFragment newInstance() {
        return new requestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.request_fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RequestViewModel.class);
        // TODO: Use the ViewModel
    }
    public void request(){
        
    }


    public  void init(){
        elements = new ArrayList<>();
        elements.add(new ListElement("#31BEB2", "Pato","Esparta","Activo"));
        elements.add(new ListElement("#31BEB2", "Jose","Quito","Activo"));
        elements.add(new ListElement("#31BEB2", "Luis","Guayakil","Activo"));
        ListAdapter listAdapter = new ListAdapter(elements,getContext());
        RecyclerView recyclerView = getActivity().findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }
}

