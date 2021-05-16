package com.sun.myapplication1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sun.myapplication1.model.Position;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentDao extends Fragment {
    private ListView listView;
    List<Map<String,Double>> list;
    SimpleAdapter simpleAdapter;
    private View view_empty;
    private  View view;
    private Button bt_dao_exit;
    List<Map<String,String>> list_obv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dao_listview,container,false);

        list_obv = new ArrayList<>();
        view_empty=view.findViewById(R.id.daoempty);
        listView =view.findViewById(R.id.dao_list);
        listView.setEmptyView(view_empty);
        simpleAdapter = new SimpleAdapter(getActivity(),getData(),R.layout.repository_list,new String[]{"la","lon"},new int[]{R.id.la1,R.id.la2});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //数据库中删除了

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("删除");
                dialog.setMessage(""+list.get(position).get("la")+","+list.get(position).get("lon"));
                dialog.setCancelable(false);
                dialog.setPositiveButton("是",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int witch){
                        LitePal.deleteAll(Position.class,"latitude=?",
                                String.valueOf(list.get(position).get("la"))
                        );
                        list.remove(position);
//                        listView.setAdapter(simpleAdapter);
                        simpleAdapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("否",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int witch){

                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        bt_dao_exit = (Button) getActivity().findViewById(R.id.bt_dao_exit);
        bt_dao_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View xview) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private List<Map<String,Double>> getData() {
        list = new ArrayList<>();
        List<Position> position = LitePal.findAll(Position.class);
        for(Position pp :position){
            Map<String,Double> map = new HashMap<>();
            map.put("la",pp.getLatitude());
            map.put("lon",pp.getLongitude());
            list.add(map);
        }
        return list;
    }
}
