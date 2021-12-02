package com.example.apvexe.fragmant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apvexe.NhaXeAdapter;
import com.example.apvexe.R;
import com.example.apvexe.ThemXe;
import com.example.apvexe.Xe;
import com.example.apvexe.chuyencuatoi1;
import com.example.apvexe.hoadoncuatoi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class homefragmant extends Fragment {
    View view;
    private RecyclerView rcv;
    private String linkdatabase;
    private TextView themxe, thanhtoancuatoi;
    private DatabaseReference reference;
    private ArrayList<Xe> xeArrayList = new ArrayList<>();
    private NhaXeAdapter nhaXeAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fag_home,container,false);
        view.findViewById(R.id.themxe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), chuyencuatoi1.class));
            }
        });
        linkdatabase = getResources().getString(R.string.link_RealTime_Database);
        rcv = view.findViewById(R.id.rcv_NhaTro);
        themxe = view.findViewById(R.id.themxe);
        thanhtoancuatoi = view.findViewById(R.id.thanhtoancuatoi);
        thanhtoancuatoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), hoadoncuatoi.class));
            }
        });
        getLisviewDatabasefirebase("");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance(linkdatabase).getReference("users").child(user.getUid()).child("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ktadmin = dataSnapshot.child("0").getValue(String.class);
                if (ktadmin.equals("")) {
                    themxe.setVisibility(View.GONE);
                    thanhtoancuatoi.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        nhaXeAdapter = new NhaXeAdapter();
        nhaXeAdapter = new NhaXeAdapter(getActivity(), R.layout.itemsanpham, xeArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        xeArrayList = new ArrayList<>();
        nhaXeAdapter.setData(xeArrayList);
        rcv.setAdapter(nhaXeAdapter);
        return view;
    }

    private void getLisviewDatabasefirebase(String key) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        reference = FirebaseDatabase.getInstance(linkdatabase).getReference("Danhsachxe");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               Xe xe = snapshot.getValue(Xe.class);
                if (xe != null) {
                    xeArrayList.add(xe);
                    nhaXeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull   DataSnapshot snapshot, @Nullable   String previousChildName) {
                Xe xe = snapshot.getValue(Xe.class);
                if (xe == null || xeArrayList == null || xeArrayList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < xeArrayList.size(); i++) {
                    if (xe.getId() == xeArrayList.get(i).getId()) {
                        xeArrayList.set(i, xe);
                        break;
                    }
                }
                nhaXeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {
                Xe xe = snapshot.getValue(Xe.class);
                 if (xe == null || xeArrayList == null || xeArrayList.isEmpty()) {
                    return;
                }
                for (int i = 0; i < xeArrayList.size(); i++) {
                    if (xe.getId() == xeArrayList.get(i).getId()) {
                        xeArrayList.remove(xeArrayList.get(i));
                        break;
                    }
                }
                nhaXeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull   DataSnapshot snapshot, @Nullable   String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
