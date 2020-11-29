package com.x.x17fun.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.x.x17fun.R;
import com.x.x17fun.adapter.ItemAdapter;
import com.x.x17fun.adapter.SelectCityHotItemAdapter;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.addressrlv.AZItemEntity;
import com.x.x17fun.custom.addressrlv.AZSideBarView;
import com.x.x17fun.custom.addressrlv.AZWaveSideBarView;
import com.x.x17fun.custom.addressrlv.AZTitleDecoration;
import com.x.x17fun.custom.addressrlv.LettersComparator;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SelectCityActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 请输入城市
     */
    private EditText mEtAddress;
    /**
     * 当前定位城市  北京
     */
    private TextView mTotalCity;
    private RecyclerView mRlvHot;
    private RecyclerView mRlvCity;
    private AZWaveSideBarView mBarList;
    private ItemAdapter mAdapter;
    private SmartRefreshLayout mSmart;
    private int a = 1;
    private List<String> date = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_select_city);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mEtAddress = (EditText) findViewById(R.id.et_address);
        mTotalCity = (TextView) findViewById(R.id.total_city);
        mRlvHot = (RecyclerView) findViewById(R.id.rlv_hot);
        mRlvCity = (RecyclerView) findViewById(R.id.rlv_city);
        mSmart = (SmartRefreshLayout) findViewById(R.id.smart);
        /*mBarList = (AZSideBarView) findViewById(R.id.bar_list);*/
        /*mBarList = (AZWaveSideBarView) findViewById(R.id.bar_list);*/
    }

    @Override
    protected void initData() {
        ArrayList<BaseEntity> objects = new ArrayList<>();
        objects.add(new BaseEntity("北京"));
        objects.add(new BaseEntity("上海"));
        objects.add(new BaseEntity("广州"));
        objects.add(new BaseEntity("深圳"));
        objects.add(new BaseEntity("成都"));
        objects.add(new BaseEntity("杭州"));
        objects.add(new BaseEntity("南京"));
        objects.add(new BaseEntity("苏州"));
        objects.add(new BaseEntity("重庆"));
        objects.add(new BaseEntity("天津"));
        objects.add(new BaseEntity("武汉"));
        objects.add(new BaseEntity("南京"));

        mRlvHot.setLayoutManager(new GridLayoutManager(SelectCityActivity.this, 4));
        SelectCityHotItemAdapter cityHotItemAdapter = new SelectCityHotItemAdapter();
        mRlvHot.setHasFixedSize(true);
        mRlvHot.setNestedScrollingEnabled(false);
        mRlvHot.setAdapter(cityHotItemAdapter);
        cityHotItemAdapter.addData(objects);

        cityHotItemAdapter.onClick(new SelectCityHotItemAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                BaseEntity baseEntity = cityHotItemAdapter.mList.get(position);
                Intent intent = new Intent();
                intent.putExtra("address",baseEntity.getTitle());
                setResult(400,intent);
                finish();
            }
        });
        mRlvCity.setHasFixedSize(true);
        mRlvCity.setNestedScrollingEnabled(false);
        mRlvCity.setLayoutManager(new LinearLayoutManager(SelectCityActivity.this));
        List<String> dateList = fillData(getResources().getStringArray(R.array.region));
        mRlvCity.setAdapter(mAdapter = new ItemAdapter());
        mAdapter.addNewData(dateList);
        mAdapter.onClick(position -> {
            String stringAZItemEntity = mAdapter.mList.get(position);
            Intent intent = new Intent();
            intent.putExtra("address",stringAZItemEntity);
            setResult(400,intent);
            finish();
        });
        mSmart.setOnLoadMoreListener(refreshLayout -> {
            mSmart.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (a){
                        case 1:
                            date = fillData(getResources().getStringArray(R.array.region2));
                            a++;
                            mAdapter.addNewData(date);

                            break;case 2:
                            date = fillData(getResources().getStringArray(R.array.region3));
                            a++;
                            mAdapter.addNewData(date);
                            break;case 3:
                            date = fillData(getResources().getStringArray(R.array.region4));
                            a++;
                            mAdapter.addNewData(date);
                            break;
                        case 4:
                            break;

                    }
                    mSmart.finishLoadMore();

                }
            },1000);

        });
    }

    @Override
    protected void initLogic() {

     /*   mBarList.setOnLetterChangeListener(new AZWaveSideBarView.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = mAdapter.getSortLettersFirstPosition(letter);
                if (position != -1) {
                    if (mRlvCity.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager manager = (LinearLayoutManager) mRlvCity.getLayoutManager();
                        manager.scrollToPositionWithOffset(position, 0);
                    } else {
                        mRlvCity.getLayoutManager().scrollToPosition(position);
                    }
                }
            }
        });*/
       /* mBarList.setOnLetterChangeListener(new AZSideBarView.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = mAdapter.getSortLettersFirstPosition(letter);
                if (position != -1) {
                    if (mRlvCity.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager manager = (LinearLayoutManager) mRlvCity.getLayoutManager();
                        manager.scrollToPositionWithOffset(position, 0);
                    } else {
                        mRlvCity.getLayoutManager().scrollToPosition(position);
                    }
                }
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private List<String> fillData(String[] date) {
        List<String> strings = Arrays.asList(date);
        return strings;

    }
}
