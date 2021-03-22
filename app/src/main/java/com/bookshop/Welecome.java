package com.bookshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.bookshop.adapter.*;

public class Welecome extends AppCompatActivity {
 private ViewPager viewPager;
 private List<View> viewList=new ArrayList<>();
 private RadioButton radioButton1;
 private RadioButton radioButton2;
 private RadioButton radioButton3;
 private TextView textView1;
 private TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welecome);
        viewPager=findViewById(R.id.main_viewpager);
        radioButton1=findViewById(R.id.wel_rad1);
        radioButton2=findViewById(R.id.wel_rad2);
        radioButton3=findViewById(R.id.wel_rad3);
        textView1=findViewById(R.id.wel_t1);
        textView2=findViewById(R.id.wel_t2);
        viewList.add(LayoutInflater.from(Welecome.this).inflate(R.layout.pager_page1,null));
        viewList.add(LayoutInflater.from(Welecome.this).inflate(R.layout.pager_page2,null));
        viewList.add(LayoutInflater.from(Welecome.this).inflate(R.layout.pager_page3,null));
        viewPageAdapter viewPageAdapter=new viewPageAdapter(Welecome.this,viewList);
        viewPager.setAdapter(viewPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioButton1.setChecked(true);
                        textView1.setVisibility(View.INVISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        textView2.setText("Next");
                        break;
                    case 1:
                        radioButton2.setChecked(true);
                        textView1.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        textView2.setText("Next");
                        break;
                    case 2:
                        radioButton3.setChecked(true);
                        textView1.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        textView2.setText("Finish");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager.getCurrentItem())
                {
                    case 0:
                        viewPager.setCurrentItem(1);
                        break;
                    case 1:
                        viewPager.setCurrentItem(2);
                        break;
                    case 2:
                        SharedPreferences.Editor share=getSharedPreferences("data",MODE_PRIVATE).edit();
                        share.putBoolean("is_first",true);
                        share.apply();
                        //界面跳转
                        Intent intent=new Intent(Welecome.this,Login.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        //判断是否第一次打开
        SharedPreferences shre=getSharedPreferences("data", MODE_PRIVATE);
        if (shre.getBoolean("is_first",false))
        {
            //界面跳转
            Intent intent=new Intent(Welecome.this,Login.class);
            startActivity(intent);
        }
    }
}