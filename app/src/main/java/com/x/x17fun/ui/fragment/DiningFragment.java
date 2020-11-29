package com.x.x17fun.ui.fragment;


import android.content.DialogInterface;
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
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.x.x17fun.R;
import com.x.x17fun.adapter.BillAdapter;

import com.x.x17fun.adapter.ZoneListAdapter;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseEventMsg;
import com.x.x17fun.base.BaseFragment;

import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;

import com.x.x17fun.entity.UserPushListEntity;
import com.x.x17fun.entity.ZoneEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DateFormatUtils;
import com.x.x17fun.utils.DeeSpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiningFragment extends BaseFragment {


    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart;
    private BillAdapter adapter;
    private ConstraintLayout mLoad_faile;
    private ZoneListAdapter viewAdapter;
    private int type;
    private int a = 2;
    private ImageView send;
    private EditText et_pinglun;
    private BaseDialog baseDialog;
    private String s = "";

    public static DiningFragment newInstance() {
        return new DiningFragment();
    }

    public static DiningFragment newInstance(int type) {
        DiningFragment diningFragment = new DiningFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        diningFragment.setArguments(bundle);
        return diningFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dinning, container, false);
        type = getArguments().getInt("type");

        EventBus.getDefault().register(this);
        initView(view);

        return view;
    }

   @Subscribe(threadMode = ThreadMode.MAIN)
   public void onEvent(BaseEventMsg msg){
        if (msg.getParamInt() == 99){
            initData();
        }
   }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //initData();
    }

    private void initView(View view) {


        mRlv = (RecyclerView) view.findViewById(R.id.rlv);
        mSmart = (SmartRefreshLayout) view.findViewById(R.id.smart);
        mLoad_faile = view.findViewById(R.id.load_fail);


        mLoad_faile.setVisibility(View.GONE);
        mSmart.setOnRefreshListener(refreshLayout -> {
            /*page = 1;*/
            initData();
        });

        if (type == 2) {
            mSmart.setVisibility(View.GONE);
            mLoad_faile.setVisibility(View.VISIBLE);
        } else if (type == 0) {
            initData();
        } else {
            initData();
        }


    }


    private void initData() {

        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        hashMap.put("userId", userId);
        hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("lasttime", DateFormatUtils.longToDate(System.currentTimeMillis(), DateFormatUtils.FORMAT_6));
        hashMap.put("refreshPatten", "1");
        if (type == 0) {
            DataService.getHomeService().focusZoneList(hashMap)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResult<ZoneEntity>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResult<ZoneEntity> baseResult) {
                            Log.e("result", baseResult.toString());
                            if (baseResult.getResult_code() == 0) {
                                if (baseResult.getData() != null && baseResult.getData().getZoneList() != null
                                        && baseResult.getData().getZoneList().size() > 0) {
                                    if (viewAdapter != null){
                                        viewAdapter.replaceData(baseResult.getData().getZoneList());
                                        return;
                                    }
                                    mSmart.setVisibility(View.VISIBLE);
                                    mLoad_faile.setVisibility(View.GONE);
                                    mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
                                    mRlv.setHasFixedSize(true);
                                    mRlv.setNestedScrollingEnabled(false);
                                    viewAdapter = new ZoneListAdapter(getContext());
                                    viewAdapter.setList(baseResult.getData().getZoneList());
                                    mRlv.setAdapter(viewAdapter);
                                    initAdapter();

                                } else {
                                    mSmart.setVisibility(View.GONE);
                                    mLoad_faile.setVisibility(View.VISIBLE);
                                }
                            } else {
                                mSmart.setVisibility(View.GONE);
                                mLoad_faile.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            AdiUtils.showToast(e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if (type == 1) {
            DataService.getHomeService().recommendZoneList(hashMap)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResult<ZoneEntity>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResult<ZoneEntity> baseResult) {
                            Log.e("result", baseResult.toString());
                            if (baseResult.getResult_code() == 0) {

                                if (baseResult.getData() != null && baseResult.getData().getZoneList() != null
                                        && baseResult.getData().getZoneList().size() > 0) {
                                    if (viewAdapter != null){
                                        viewAdapter.replaceData(baseResult.getData().getZoneList());
                                        return;
                                    }
                                    mSmart.setVisibility(View.VISIBLE);
                                    mLoad_faile.setVisibility(View.GONE);
                                    mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
                                    mRlv.setHasFixedSize(true);
                                    mRlv.setNestedScrollingEnabled(false);
                                    viewAdapter = new ZoneListAdapter(getContext());
                                    viewAdapter.setList(baseResult.getData().getZoneList());
                                    mRlv.setAdapter(viewAdapter);
                                    initAdapter();
                                } else {
                                    mSmart.setVisibility(View.GONE);
                                    mLoad_faile.setVisibility(View.VISIBLE);
                                }
                            } else {
                                mSmart.setVisibility(View.GONE);
                                mLoad_faile.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            AdiUtils.showToast(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            mSmart.setVisibility(View.GONE);
            mLoad_faile.setVisibility(View.VISIBLE);
        }


    }

    private void initAdapter() {
        viewAdapter.onClick(new ZoneListAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onLoveClick(int position, boolean isSend) {
                ZoneEntity.ZoneListBean zoneListBean = viewAdapter.list.get(position);
                EventBus.getDefault().post(new BaseEventMsg(zoneListBean.getZoneId(), "99", zoneListBean.getUserId(), position));
               /* ZoneEntity.ZoneListBean bean = baseResult.getData().getUserPublishedList().get(position);
                if (bean.isLove()==false){
                    bean.setLove(true);
                    bean.setStarCount(bean.getStarCount()+1);
                    viewAdapter.notifyItemChanged(position,R.id.love);
                    viewAdapter.notifyItemChanged(position,R.id.love_count);
                }else{
                    bean.setLove(false);
                    bean.setStarCount(bean.getStarCount()-1);
                    viewAdapter.notifyItemChanged(position,R.id.love);
                    viewAdapter.notifyItemChanged(position,R.id.love_count);
                }*/
            }

            @Override
            public void onZanClick(int position, boolean isSend) {
                ZoneEntity.ZoneListBean zoneListBean = viewAdapter.list.get(position);
                zan(zoneListBean);
               /* ZoneEntity.ZoneListBean bean = baseResult.getData().getUserPublishedList().get(position);
                String isAppreciate = bean.getIsAppreciate();

                DeeSpUtil.getInstance().putString("isRefresh","1");

                if (bean.getIsAppreciate().equals("1")){
                    bean.setIsAppreciate("0");
                    bean.setAppreciateCount(bean.getAppreciateCount()-1);
                    viewAdapter.notifyItemChanged(position,R.id.zan);
                    viewAdapter.notifyItemChanged(position,R.id.zan_count);
                    if (isSend && !isAppreciate.equals(bean.getIsAppreciate())){

                    }
                }else{
                    bean.setIsAppreciate("1");
                    bean.setAppreciateCount(bean.getAppreciateCount()+1);
                    viewAdapter.notifyItemChanged(position,R.id.zan);
                    viewAdapter.notifyItemChanged(position,R.id.zan_count);
                    if (isSend && !isAppreciate.equals(bean.getIsAppreciate())){

                    }
                }*/
            }

            @Override
            public void onRematkClick(int position) {
                ZoneEntity.ZoneListBean zoneListBean = viewAdapter.list.get(position);
                initPingLunDialog(zoneListBean);
            }
        });
    }

    private void zan(ZoneEntity.ZoneListBean bean) {

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("zoneUserId", bean.getUserId());
        hashMap.put("zoneId", bean.getZoneId());
        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
        DataService.getHomeService().appreciateZone(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getResult_code() != 0) {
                            Log.e("zan", baseResult.getResult_msg());
                            showToast(baseResult.getResult_msg());
                            return;
                        }
                        Log.e("zan", baseResult.toString());
                        a++;
                        if (a % 2 == 1) {
                            DeeSpUtil.getInstance().putString("isZoneRefresh", "1");
                        } else {
                            DeeSpUtil.getInstance().putString("isZoneRefresh", "0");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("zan", e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void initPingLunDialog(ZoneEntity.ZoneListBean zoneListBean) {

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
                            pinglun(zoneListBean);
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

    private void pinglun(ZoneEntity.ZoneListBean zoneListBean) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("zoneId",zoneListBean.getZoneId());
        hashMap.put("zoneUserId",zoneListBean.getUserId());
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
