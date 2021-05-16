package com.sun.myapplication1;

import androidx.fragment.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import java.util.ArrayList;

public class TalkRoom extends Fragment {
    private ArrayList<Msg> msgs;
    private EditText et_input;
    private MyAdapter myAdapter;
    private ListView lv;
    private View tview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tview = inflater.inflate(R.layout.talk_room,container,false);
        return tview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        msgs = new ArrayList<>();
        Button bt_talkBack = view.findViewById(R.id.talk_back);
        bt_talkBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tview.setVisibility(View.INVISIBLE);
                getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });
        lv = (ListView) view.findViewById(R.id.listview_chat);
        et_input = (EditText) view.findViewById(R.id.et_input);
        msgs.add(new Msg("hello", Msg.TYPE_RECEIVE));
        msgs.add(new Msg("who is that?", Msg.TYPE_SEND));
        msgs.add(new Msg("this is LiLei,nice to meet you!", Msg.TYPE_RECEIVE));
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
        view.findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_input.getText().toString();
                if (!content.isEmpty()) {
                    msgs.add(new Msg(content, Msg.TYPE_SEND));
                    myAdapter.notifyDataSetChanged();
                    lv.setSelection(msgs.size() - 1);
                    et_input.setText("");
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Msg getItem(int position) {
            return msgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getContext().getApplicationContext(), R.layout.talk_item, null);
                holder.tv_receive = (TextView) convertView.findViewById(R.id.tv_receive);
                holder.tv_send = (TextView) convertView.findViewById(R.id.tv_send);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Msg msg = getItem(position);
            if (msg.type == Msg.TYPE_RECEIVE) {
                holder.tv_receive.setVisibility(View.VISIBLE);
                holder.tv_send.setVisibility(View.GONE);
                holder.tv_receive.setText(msg.content);
            } else if (msg.type == Msg.TYPE_SEND) {
                holder.tv_send.setVisibility(View.VISIBLE);
                holder.tv_receive.setVisibility(View.GONE);
                holder.tv_send.setText(msg.content);
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tv_receive;
        TextView tv_send;
    }
}
