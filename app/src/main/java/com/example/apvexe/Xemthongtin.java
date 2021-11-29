package com.example.apvexe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Xemthongtin extends AppCompatActivity {
    private TextView tent, gia, thoigian, chuyen, thanhtoan, lienhe, datcoc, sdt, tento;
    ImageView imgavatar;
    private String linkdatabase;
    private DatabaseReference reference;
    private NumberFormat fm = new DecimalFormat("#,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemthongtin);
        getSupportActionBar().setTitle("Thông tin chuyến");
        tent = findViewById(R.id.tennhaxe);
        gia = findViewById(R.id.giatien);
        thoigian = findViewById(R.id.ngaygio);
        chuyen = findViewById(R.id.chuyendi);
        thanhtoan = findViewById(R.id.thanhtoan);
        lienhe = findViewById(R.id.goi);
        datcoc = findViewById(R.id.coc);
        sdt = findViewById(R.id.sddt);
        imgavatar = findViewById(R.id.imgavatar);
        tento = findViewById(R.id.til_hoTen_capNhatActivity);
        linkdatabase = getResources().getString(R.string.link_RealTime_Database);
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("IDPHONGTRO");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        reference = FirebaseDatabase.getInstance(linkdatabase).getReference("Danhsachxe").child(value1);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String diemden = dataSnapshot.child("diemden").getValue(String.class);
                String ten = dataSnapshot.child("ten").getValue(String.class);
                String ngaykhoihanh = dataSnapshot.child("ngaykhoihanh").getValue(String.class);
                String giokhoihanh = dataSnapshot.child("giokhoihanh").getValue(String.class);
                int soLuong = dataSnapshot.child("soluongve").getValue(Integer.class);
                String tuyenchay = dataSnapshot.child("tuyenchay").getValue(String.class);
                int giave = dataSnapshot.child("giave").getValue(Integer.class);
                String sdta = dataSnapshot.child("sdt").getValue(String.class);
                String linkHanhDB = dataSnapshot.child("anh").getValue(String.class);
                Picasso.get().load(linkHanhDB).into(imgavatar);
                tento.setText(ten);
                tent.setText("Đại lý bán vé:  "+ten);
                gia.setText("Giá vé:  "+fm.format(giave)+ " VND        Số lượng vé:  "+String.valueOf(soLuong) );
                thoigian.setText("Khởi hành lúc:  " + giokhoihanh+ "p  --  Ngày: "+ ngaykhoihanh );
                chuyen.setText("Tuyến:  "+tuyenchay+"   -->   "+diemden);
                sdt.setText("Hotline:  "+sdta);

//                gio.setText(giokhoihanh);
//                tilHoTen.setText(ten);
//                soluong.setText(String.valueOf(soLuong));
//                tilMail.setText(tuyenchay);
//                tilMail2.setText(diemden);
//                tilBirthday.setText(ngaykhoihanh);
//                tilPhone.setText(sdt);
//                gia.setText(String.valueOf(giave));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}