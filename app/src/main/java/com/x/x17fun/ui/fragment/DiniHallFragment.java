package com.x.x17fun.ui.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bea.xml.stream.events.BaseEvent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.x.x17fun.R;
import com.x.x17fun.adapter.BillAdapter;
import com.x.x17fun.adapter.DiningVpAdapter;
import com.x.x17fun.base.BaseEventMsg;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.BillEntity;
import com.x.x17fun.entity.ZoneEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.ParamsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiniHallFragment extends BaseFragment implements View.OnClickListener{

    private ImageView back;
    private TextView focus;
    private TextView recommand;
    private TextView near;
    private ViewPager vp;

    private ImageView mBack;
    private RecyclerView mBillList;
    private SmartRefreshLayout mSmart;
    private BillAdapter adapter;
    private ConstraintLayout mLoad_faile;


    public static DiniHallFragment newInstance() {
        return new DiniHallFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dining_hall, container, false);
        StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(getActivity());
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEventMsg event){
        if ("99".equals(event.getType())){
            extraTransaction().startDontHideSelf(DialogZoneFragment.newInstance(event.getMsg(),event.getParamStr()));
        }
    }


    private void initView(View view) {
        back = (ImageView)view.findViewById(R.id.back);
        focus = (TextView)view.findViewById(R.id.focus);
        recommand = (TextView)view.findViewById(R.id.recommand);
        near = (TextView)view.findViewById(R.id.near);
        vp = (ViewPager)view.findViewById(R.id.vp);
        back.setOnClickListener(this);
        focus.setOnClickListener(this);
        recommand.setOnClickListener(this);
        near.setOnClickListener(this);

        near.setVisibility(View.GONE);
        ArrayList<DiningFragment> objects = new ArrayList<>();
        objects.add(DiningFragment.newInstance(0));
        objects.add(DiningFragment.newInstance(1));
       // objects.add(DiningFragment.newInstance(2));
        DiningVpAdapter vpAdapter = new DiningVpAdapter(getChildFragmentManager(), objects);
        vp.setAdapter(vpAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==0){
                    changeTab(focus,recommand,near);
                }else if (i==1){
                    changeTab(recommand,focus,near);
                }else{
                    changeTab(near,recommand,focus);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vp.setCurrentItem(1);
        vp.setOffscreenPageLimit(1);
    }
    private void changeTab(TextView black,TextView gray1,TextView gray2){
        black.setTextSize(18);
        gray1.setTextSize(15);
        gray2.setTextSize(15);
        black.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        gray1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        gray2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                Objects.requireNonNull(getActivity()).finish();
                break;
            case R.id.focus:
                vp.setCurrentItem(0);
                break;
            case R.id.recommand:
                vp.setCurrentItem(1);
                break;
            case R.id.near:
                vp.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
