package com.sun.myapplication1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sun.myapplication1.model.Position;
import com.sun.myapplication1.utils.HttpUtil;
import com.sun.myapplication1.utils.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentReceived extends Fragment implements View.OnClickListener{
    private ListView listView;
    public static int control = 0;
    private  View view;
    private Button bt_web_exit;
    private List<Position> positionlist;
    private Button bt_web_flash;
    private Button bt_web_save;
    private SimpleAdapter adapter;
    private List<Map<String,Double>> list;

    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.web_received_listview,container,false);
        bt_web_exit = (Button) view.findViewById(R.id.bt_web_received_exit);
        bt_web_flash = (Button) view.findViewById(R.id.bt_flash_web);
        bt_web_save = (Button) view.findViewById(R.id.bt_save_web);
        listView = (ListView) view.findViewById(R.id.web_list);
        adapter = new SimpleAdapter(getActivity(),getData(),R.layout.web_list_view,new String[]{"la","lon"},new int[]{R.id.web_la1,R.id.web_la2});
        listView.setAdapter(adapter);
        return view;
    }


    public List<Map<String,Double>> getData() {
        list = new ArrayList<>();
        List<Position> position = LitePal.findAll(Position.class);          //遍历了数据库
        for(Position qq :position){
            Map<String,Double> map = new HashMap<>();
            map.put("la",qq.getLatitude());
            map.put("lon",qq.getLongitude());
            list.add(map);
        }
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt_web_exit.setOnClickListener(this);
        bt_web_flash.setOnClickListener(this);
        bt_web_save.setOnClickListener(this);
    }

    private void query(){
        String address = "http://192.168.43.189:8080/getData";
        queryFromServer(address);
    }

    private void queryFromServer(String address) {
        showProgerssDialog();
        HttpUtil.sendOkHttpRequest(address,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = Utility.handleGeopointResponse(responseText,control);
                if(result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_web_received_exit:
                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.bt_flash_web://保存功能
                control = 0;
                query();
                break;
            case R.id.bt_save_web:
                control = 1;
                query();
                break;
            default:
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    private void showProgerssDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
