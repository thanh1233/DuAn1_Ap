package com.example.apvexe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    int soluongve = 1;
    int tong = 0;
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

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Xemthongtin.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.thanhtoan);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                TextView tennhaxe, tuyen, ngaydi, giaven, tenkhach, ngaysinh, sdt, next, unnext, tongtien, soluong, thanhtoan, huy;
                tennhaxe = dialog.findViewById(R.id.tenhaxea);
                tuyen = dialog.findViewById(R.id.tuyen);
                ngaydi = dialog.findViewById(R.id.ngay);
                giaven = dialog.findViewById(R.id.gia);
                tenkhach = dialog.findViewById(R.id.textView16);
                ngaysinh = dialog.findViewById(R.id.textView17);
                sdt = dialog.findViewById(R.id.textView18);
                next = dialog.findViewById(R.id.next);
                unnext = dialog.findViewById(R.id.unnexxt);
                tongtien = dialog.findViewById(R.id.tong);
                soluong = dialog.findViewById(R.id.soluong);
                thanhtoan = dialog.findViewById(R.id.thanhtoanngay);
                huy = dialog.findViewById(R.id.huybo);
                soluongve = 1;
                tong=0;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    return;
                }

                reference = FirebaseDatabase.getInstance(linkdatabase).getReference("users").child(user.getUid()).child("Profile");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String ngaysinhkhach = dataSnapshot.child("birthday").getValue(String.class);
                        String sdtkhach = dataSnapshot.child("numberphone").getValue(String.class);
                        ngaysinh.setText("Ngày sinh: "+ngaysinhkhach);
                        sdt.setText("Số điện thoại: "+sdtkhach);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                tenkhach.setText("Tên khách hàng: "+user.getDisplayName());


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
                        tennhaxe.setText("Tên nhà xe: "+ten);
                        tuyen.setText("Tuyến: "+tuyenchay+"    đến    "+diemden);
                        ngaydi.setText("Ngày khởi hành:  " + ngaykhoihanh+ " vào lúc:  "+ giokhoihanh );
                        giaven.setText("Giá vé:  "+fm.format(giave)+ " VND ");
                        tong = giave;
                        tongtien.setText(""+fm.format(tong)+" VND ");

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                soluong.setText(""+ soluongve);
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (soluongve == 1) {
                            Toast.makeText(getApplicationContext(), "Đạt giới hạn nhỏ nhất", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        soluongve--;
                        soluong.setText(""+ soluongve);
                        reference = FirebaseDatabase.getInstance(linkdatabase).getReference("Danhsachxe").child(value1);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int giave = dataSnapshot.child("giave").getValue(Integer.class);
                                tong = tong - giave;
                                tongtien.setText(""+fm.format(tong)+" VND ");
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

                unnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        soluongve++;
                        soluong.setText(""+ soluongve);
                        reference = FirebaseDatabase.getInstance(linkdatabase).getReference("Danhsachxe").child(value1);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int giave = dataSnapshot.child("giave").getValue(Integer.class);
                                tong = tong + giave;
                                tongtien.setText(""+fm.format(tong)+" VND ");
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });




                //phần thanh toán đây nè @ - @
                thanhtoan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Phần này tôi chịu, nhờ thầy bình nhé! =))", Toast.LENGTH_LONG).show();
                    }
                });



                dialog.show();
            }
        });


    }
}

// ahihi