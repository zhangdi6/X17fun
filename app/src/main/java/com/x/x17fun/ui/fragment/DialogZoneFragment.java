package com.x.x17fun.ui.fragment;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.x.x17fun.R;
import com.x.x17fun.adapter.BillAdapter;

import com.x.x17fun.adapter.MyTalkListAdapter;
import com.x.x17fun.adapter.MyZoneTalkListAdapter;
import com.x.x17fun.adapter.ZoneListAdapter;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseEventMsg;
import com.x.x17fun.base.BaseFragment;

import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;

import com.x.x17fun.entity.RemarkListEntity;
import com.x.x17fun.entity.RemarkZoneEntity;
import com.x.x17fun.entity.UserPushListEntity;
import com.x.x17fun.entity.ZoneEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DateFormatUtils;
import com.x.x17fun.utils.DeeSpUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogZoneFragment extends BaseFragment {


    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart;
    private BillAdapter adapter;
    private ConstraintLayout mLoad_faile;
    private MyZoneTalkListAdapter viewAdapter;
    private String zoneId;
    private String zoneUserId;
    private RelativeLayout layout;
    private ImageView send;
    private EditText et_pinglun;
    private BaseDialog baseDialog;
    private String s = "";
    private LinearLayout layout_remark;
    private TextView tv_remark_count;
    private int type;

    public static DialogZoneFragment newInstance() {
        return new DialogZoneFragment();
    }

    public static DialogZoneFragment newInstance(String type , String type2) {
        DialogZoneFragment diningFragment = new DialogZoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("type2", type2);
        diningFragment.setArguments(bundle);
        return diningFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zone_dialog, container, false);
        zoneId = getArguments().getString("type");
        zoneUserId = getArguments().getString("type2");


        initView(view);
        initData();
        return view;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        StatusBarCompat.setStatusBarColor(getActivity(), Color.parseColor("#80000000"));
        StatusBarCompat.cancelLightStatusBar(getActivity());
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(getActivity());
    }

    private void initView(View view) {


        mRlv = (RecyclerView) view.findViewById(R.id.rlv);
        mSmart = (SmartRefreshLayout) view.findViewById(R.id.smart);
        mLoad_faile = view.findViewById(R.id.load_fail);
        layout = view.findViewById(R.id.layout);
        layout_remark = view.findViewById(R.id.layout_remark);
        tv_remark_count = view.findViewById(R.id.tv_remark_count);
        layout.setOnClickListener(v -> pop());
        layout_remark.setOnClickListener(v -> initPingLunDialog());
        mLoad_faile.setVisibility(View.GONE);
        mLoad_faile.setOnClickListener(v -> {
            Log.e("a","");
        });
        mSmart.setOnRefreshListener(refreshLayout -> {
            /*page = 1;*/
            initData();
        });
        mSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }
        });


    }
    private void initPingLunDialog() {

        View inflate = getLayoutInflater().inflate(R.layout.dialog_pinglun, null);
        send = inflate.findViewById(R.id.send);
        RelativeLayout null_layout = inflate.findViewById(R.id.null_layout);
        ConstraintLayout layout = inflate.findViewById(R.id.layout);
        et_pinglun = inflate.findViewById(R.id.et_pinglun);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog.dismiss();
            }
        });

        et_pinglun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_pinglun.getText().toString().length()>0){
                    send.setBackgroundResource(R.mipmap.feijihong);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pinglun();
                        }
                    });
                }else {
                    send.setBackgroundResource(R.mipmap.feijihui);
                    send.setOnClickListener(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        null_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog.dismiss();
            }
        });
        mSmart.postDelayed(new Runnable() {
            @Override
            public void run() {
                baseDialog.showKeyboard(et_pinglun);
            }
        },300);
        baseDialog = new BaseDialog(getActivity(), inflate, Gravity.BOTTOM);
        baseDialog.setOnDismissListener(dialog -> s = et_pinglun.getText().toString());
        baseDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                et_pinglun.setText(s);
                et_pinglun.setSelection(s.length());
                baseDialog.showKeyboard(et_pinglun);
            }
        });
        baseDialog.show();

    }

    private void pinglun() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("zoneId",zoneId);
        hashMap.put("zoneUserId",zoneUserId);
        hashMap.put("remarkContent",et_pinglun.getText().toString());
        hashMap.put("userId",DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));

        DataService.getHomeService().remarkZone(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            showToast("已发布");
                            s = "";
                            et_pinglun.setText("");
                            initData();
                            EventBus.getDefault().post(new BaseEventMsg(99));
                            baseDialog.dismiss();
                        } else {
                            showToast(baseResult.getResult_msg());
                            Log.e("result",baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("result",e.getMessage());
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        hashMap.put("userId", userId);
        hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("zoneId", zoneId);

        DataService.getHomeService().remarkZoneList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<RemarkZoneEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<RemarkZoneEntity> baseResult) {
                        Log.e("result", baseResult.toString());
                        if (baseResult.getResult_code() != 0 || baseResult.getData() == null
                                || baseResult.getData().getCommentList() == null || baseResult.getData().getCommentList().size() == 0) {
                            mSmart.setVisibility(View.GONE);
                            mLoad_faile.setVisibility(View.VISIBLE);
                            return;
                        }
                        mSmart.setVisibility(View.VISIBLE);
                        mLoad_faile.setVisibility(View.GONE);
                        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
                        mRlv.setHasFixedSize(true);
                        tv_remark_count.setText("评论 "+ baseResult.getData().getCommentList().size());
                        mRlv.setNestedScrollingEnabled(false);
                        viewAdapter = new MyZoneTalkListAdapter();
                        viewAdapter.addData((ArrayList<RemarkZoneEntity.CommentListBean>) baseResult.getData().getCommentList());
                        mRlv.setAdapter(viewAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        AdiUtils.showToast(e.getMessage());
                        mSmart.setVisibility(View.GONE);
                        mLoad_faile.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }



}
