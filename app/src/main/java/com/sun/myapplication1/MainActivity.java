package com.sun.myapplication1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sun.myapplication1.model.Position;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.osmdroid.util.GeoPoint;

import java.math.BigDecimal;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FirstFragment.ListChangeListener{
    public static double l1;
    public static double l2;
    private FragmentList fragmentList;
    private FragmentPaintArea fragmentPaintArea;
    private TalkRoom talkRoom;
    private FragmentReceived fragmentReceived;

    private Button bt_material;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //数据库LitePal
        LitePal.initialize(this);
        SQLiteDatabase db = LitePal.getDatabase();
        setContentView(R.layout.activity_main);
        //························
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        fragmentList = (FragmentList) getSupportFragmentManager().findFragmentById(R.id.list);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航", Toast.LENGTH_SHORT).show();
            //返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }


        //定义Criteria对象
        Criteria criteria = new Criteria();
        // 定位的精准度
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 海拔信息是否关注
        criteria.setAltitudeRequired(false);
        // 对周围的事情是否进行关心
        criteria.setBearingRequired(false);
        // 是否支持收费的查询
        criteria.setCostAllowed(true);
        // 是否耗电
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 对速度是否关注
        criteria.setSpeedRequired(false);

        //得到最好的定位方式
        String provider = locationManager.getBestProvider(criteria, true);
        bt_material = (Button) findViewById(R.id.bt_material);
        bt_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentMaterial(),R.id.f4_kongtou);
            }
        });



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 2000, 2, new MyLocationListener());

        //````````
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentTalk fragmentTalk= new FragmentTalk();
                fragmentTransaction.replace(R.id.f1, fragmentTalk);
                fragmentTransaction.commit();

                //通过commit进入fragment生命周期进行操作
                fab.setVisibility(View.INVISIBLE);
                Snackbar.make(view, "正在获取当前位置信息,正在发给服务器", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.db_scan:
                replaceFragment(new FragmentDao(),R.id.f2);
                break;
            case R.id.action_settings:
                Toast.makeText(this,"You clicked set",Toast.LENGTH_SHORT).show();
                break;
            case R.id.web_scan:
                replaceFragment(new FragmentReceived(),R.id.f3);
                break;
            default:
                break;
        }
        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    //这是fragment1接口中的实现
    @Override
    public void fragment1Change(GeoPoint p) {
        fragmentList.setContext(p);
    }

    @Override
    public void fragment1Back() {
        fragmentList.deleteItem();
    }

    @Override
    public void fragment1Call(){
        fragmentList.CallThisFragment();
    }


    private void replaceFragment(Fragment fragment , int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //实现监听接口
    private  final class MyLocationListener implements LocationListener {
        @Override// 位置的改变
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            double latitude = location.getLatitude();// 维度
            double longitude = location.getLongitude();// 经度
            //显示当前坐标
            Toast.makeText(MainActivity.this, "location:("+latitude+","+longitude+")", Toast.LENGTH_LONG).show();
            l1 = latitude;
            l2 = longitude;
        }

        @Override// gps卫星有一个没有找到
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }

        @Override// 某个设置被打开
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override// 某个设置被关闭
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

    }
}