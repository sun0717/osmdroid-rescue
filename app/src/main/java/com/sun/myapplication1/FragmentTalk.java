package com.sun.myapplication1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentTalk extends Fragment {
    private View view;
    private List<Msg1> msgList  = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private Button backTalkMsg;


    private  void initMsgs() {
        Msg1 msg1 = new Msg1("Hello guy.",Msg1.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg1 msg2  = new Msg1("Hello.Who is that?",Msg1.TYPE_SENT);
        msgList.add(msg2);
        Msg1 msg3 = new Msg1("This is Tom.Nice talking to you.  ",Msg1.TYPE_RECEIVED);
        msgList.add(msg3);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_talk ,container ,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMsgs();
        inputText = (EditText) getActivity().findViewById (R.id.input_text);
        send = (Button) getActivity().findViewById(R.id.send);
        backTalkMsg = (Button) getActivity().findViewById(R.id.backMsgBt) ;
        msgRecyclerView = (RecyclerView) getActivity().findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    Msg1 msg = new Msg1(content,Msg1.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);

                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                }
            }
        });
        backTalkMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.INVISIBLE);
                getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });
    }
}
