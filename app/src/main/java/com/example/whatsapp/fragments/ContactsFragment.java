package com.example.whatsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsapp.Contacts;
import com.example.whatsapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactsFragment extends Fragment {
    private View CountactsView;
    private RecyclerView recyclerView;
    private DatabaseReference ContactsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;




    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      CountactsView=  inflater.inflate(R.layout.fragment_contacts, container, false);
      recyclerView = CountactsView.findViewById(R.id.contacts_list);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      mAuth = FirebaseAuth.getInstance();
      currentUserID = mAuth.getCurrentUser().getUid();
      ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserID);
        return CountactsView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(ContactsRef, Contacts.class)
                .build();
        FirebaseRecyclerAdapter<Contacts, >

    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userStatus;
        ImageView profileImage;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textView3);
        }
    }
}