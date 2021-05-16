package com.sun.myapplication1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sun.myapplication1.model.Position;

import org.litepal.LitePal;
import org.w3c.dom.Text;

public class FragmentMaterial extends Fragment {

    private Button bt_person_inc;
    private Button bt_person_dis;
    private Button bt_material_inc;
    private Button bt_material_dis;
    private Button bt_medicine_inc;
    private Button bt_medicine_dis;
    private Button bt_material_back;
    private Button bt_material_finish;

    private TextView tv_position_get;
    private EditText ed_supply;
    private EditText ed_person;
    private EditText ed_material;
    private EditText ed_medicine;

    private int person = 0;
    private int material = 0;
    private int medicine = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material , container , false);
        bt_person_inc = (Button) view.findViewById(R.id.bt_person_increase);
        bt_person_dis = (Button) view.findViewById(R.id.bt_person_discrease);
        bt_material_inc = (Button) view.findViewById(R.id.bt_material_increase);
        bt_material_dis = (Button) view.findViewById(R.id.bt_material_discrease);
        bt_medicine_inc = (Button) view.findViewById(R.id.bt_medicine_increase);
        bt_medicine_dis = (Button) view.findViewById(R.id.bt_medicine_discrease);
        bt_material_back = (Button) view.findViewById(R.id.bt_material_back);
        bt_material_finish = (Button) view.findViewById(R.id.bt_material_finish);
        ed_supply = (EditText) view.findViewById(R.id.ed_supply);
        ed_person = (EditText) view.findViewById(R.id.ed_person_count);
        ed_material = (EditText) view.findViewById(R.id.ed_material_count);
        ed_medicine = (EditText) view.findViewById(R.id.ed_medicine_count);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bt_person_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person = Integer.parseInt(ed_person.getText().toString());
                if( person >= 0) {
                    ed_person.setText(String.valueOf(++person));
                } else {
                    ed_person.setText(String.valueOf(0));
                }
            }
        });
        bt_person_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person = Integer.parseInt(ed_person.getText().toString());
                if( person > 0) {
                    ed_person.setText(String.valueOf(--person));
                } else {
                    ed_person.setText(String.valueOf(0));
                }
            }
        });
        bt_material_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                material = Integer.parseInt(ed_material.getText().toString());
                if( material >= 0) {
                    ed_material.setText(String.valueOf(++material));
                } else {
                    ed_material.setText(String.valueOf(0));
                }
                Toast.makeText(getContext(),"inc",Toast.LENGTH_SHORT).show();
            }
        });
        bt_material_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                material = Integer.parseInt(ed_material.getText().toString());
                if( material > 0) {
                    ed_material.setText(String.valueOf(--material));
                } else {
                    ed_material.setText(String.valueOf(0));
                }
                Toast.makeText(getContext(),"inc",Toast.LENGTH_SHORT).show();
            }
        });
        bt_medicine_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicine = Integer.parseInt(ed_medicine.getText().toString());
                if ( medicine >= 0) {
                    ed_medicine.setText(String.valueOf(++medicine));
                } else {
                    ed_material.setText(String.valueOf(0));
                }
                Toast.makeText(getContext(),"inc",Toast.LENGTH_SHORT).show();
            }
        });
        bt_medicine_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicine = Integer.parseInt(ed_medicine.getText().toString());
                if ( medicine > 0) {
                    ed_medicine.setText(String.valueOf(--medicine));
                } else {
                    ed_medicine.setText(String.valueOf(0));
                }
                Toast.makeText(getContext(),"inc",Toast.LENGTH_SHORT).show();
            }
        });
        bt_material_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"back",Toast.LENGTH_SHORT).show();
            }
        });
        bt_material_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("提交");
                dialog.setMessage("消息提供完毕");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int witch){

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
    }

    public String get_supply() {
        return ed_supply.getText().toString();
    }


//    EditText editText1 =(EditText) findViewById (R.id.editText1);
//    c=Integer.parseInt(editText1.getText().toString());
//
//
//    EditText editText2 =(EditText) findViewById (R.id.editText2);
//    f=(9.0*c)/5.0+32.0;
//editText2.setText(String.valueOf(f));
}
