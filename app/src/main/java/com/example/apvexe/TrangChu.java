package com.example.apvexe;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.apvexe.fragmant.caiDat;
import com.example.apvexe.fragmant.homefragmant;
import com.example.apvexe.fragmant.nhantin;
import com.example.apvexe.fragmant.caiDat;
import com.example.apvexe.fragmant.homefragmant;
import com.example.apvexe.fragmant.nhantin;
import com.google.android.material.navigation.NavigationView;

public class TrangChu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private  static final int FRAGMENT_HOME = 0;
    private  static final int FRAGMENT_NHANTIN = 1;
    private  static final int FRAGMENT_CAIDAT = 2;
    private int mCurrenrFragent = FRAGMENT_HOME;

    private DrawerLayout mDramerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDramerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDramerLayout,toolbar,R.string.open,R.string.close);

        mDramerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragnent(new homefragmant());
        navigationView.getMenu().findItem(R.id.chonChuyen).setChecked(true);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.chonChuyen){
            if (mCurrenrFragent != FRAGMENT_HOME){
                replaceFragnent(new homefragmant());
                mCurrenrFragent = FRAGMENT_HOME;
            }

        }else  if (id == R.id.nhanTin){
            if (mCurrenrFragent != FRAGMENT_NHANTIN){
                replaceFragnent(new nhantin());
                mCurrenrFragent = FRAGMENT_NHANTIN;
            }

        }else  if (id == R.id.caiDat){
            if (mCurrenrFragent != FRAGMENT_CAIDAT){
                replaceFragnent(new caiDat());
                mCurrenrFragent = FRAGMENT_CAIDAT;
            }

        }
        mDramerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDramerLayout.isDrawerOpen(GravityCompat.START)){
            mDramerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void  replaceFragnent(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frane,fragment);
        transaction.commit();
    }
}