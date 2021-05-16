package com.sun.myapplication1;

import android.app.AlertDialog;
import android.os.Bundle;
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
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.Projection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentList extends Fragment {
    private ListView listView;
//    private List<Map<String,Object>> mData;
    private View view_empty;
    List<Map<String,Object>> list;

    List<Map<String,String>> list_obv;

    private Button bt_do;
    private View lview;
    private FragmentList fragmentList;

    private View mView;


    SimpleAdapter simpleAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lview = inflater.inflate(R.layout.listview,container,false);

        list_obv = new ArrayList<>();
        view_empty=lview.findViewById(R.id.list_empty);
        listView =lview.findViewById(R.id.list_view);
        listView.setEmptyView(view_empty);
        simpleAdapter = new SimpleAdapter(getActivity(),getData(),R.layout.fruitlist,new String[]{"title","image"},new int[]{R.id.fruit_name,R.id.fruit_image});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog alertDialog1 = new AlertDialog.Builder(getContext())
                        .setTitle("第"+(position+1)+"个点")//标题
                        .setMessage(list_obv.get(position).toString())//内容
                        .setIcon(R.mipmap.ic_launcher)//图标
                        .create();
                alertDialog1.show();
            }
        });
        mView = lview;
        return lview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt_do = (Button) getActivity().findViewById(R.id.bt_exit);
        bt_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lview.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setContext(GeoPoint pointl){

        Map map = new HashMap();
        Map map1 = new HashMap();
        BigDecimal bl1 = new BigDecimal(pointl.getLatitude());
        BigDecimal bl2 = new BigDecimal(pointl.getLongitude());
        double pl1 = bl1.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
        double pl2 = bl2.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
        map.put("title",list.size()+1+"."+pl1+"   "+pl2);
        map.put("image",R.drawable.marker_node);

        map1.put("纬度",bl1);
        map1.put("经度",bl2);
        list.add(map);
        list_obv.add(map1);
        listView.setAdapter(simpleAdapter);
    }
    public void deleteItem(){

        int size = list.size();
        if( size > 0 ){
            list.remove(list.size()-1);
            listView.setAdapter(simpleAdapter);
        }
    }

    public void CallThisFragment(){
        lview.setVisibility(View.VISIBLE);
    }

    private List<Map<String,Object>> getData() {
        list = new ArrayList<>();
        return list;
    }

}
