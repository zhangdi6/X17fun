package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.x.x17fun.R;
import com.x.x17fun.adapter.SaleProManAdapter;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.MyPushedEntity;
import com.x.x17fun.ui.activity.ProductDetailActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;
import com.x.x17fun.utils.ParamsUtils;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */

public class SaleProductManageFragment extends BaseFragment {

    private View view;
    private ImageView mBack;
    private SaleProManAdapter saleProManAdapter;
    private ConstraintLayout load_fail;

    public static SaleProductManageFragment getInstance(){
       return new SaleProductManageFragment();
   }

    private SmartRefreshLayout smart;
    private RecyclerView rlv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sale_product_manage, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();

        String userId = DeeSpUtil.getInstance().getString("userId");
        String ticket = DeeSpUtil.getInstance().getString("ticket");
        hashMap.put("userId", userId);
        hashMap.put("ticket", ticket);
        DataService.getHomeService().getMyPushList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<MyPushedEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<MyPushedEntity> baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            Log.e("home",baseResult.toString());
                            if (baseResult.getData()!=null && baseResult.getData().getUserPublishedList()!=null &&
                            baseResult.getData().getUserPublishedList().size()>0){
                                smart.setVisibility(View.VISIBLE);
                                load_fail.setVisibility(View.GONE);
                                rlv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                saleProManAdapter = new SaleProManAdapter();
                                saleProManAdapter.addData(baseResult.getData().getUserPublishedList());
                                rlv.setAdapter(saleProManAdapter);
                                saleProManAdapter.onClick(new SaleProManAdapter.onItemClickListener() {
                                    @Override
                                    public void onClick(int position) {
                                        /*startToActivity(getContext(), ProductDetailActivity.class);*/
                                    }

                                    @Override
                                    public void onBtnClick(int position) {
                                        start(RePushMealFragment.newInstance(saleProManAdapter.mList.get(position).getFoodId()));
                                    }

                                    @Override
                                    public void onMoreClick(int position) {

                                    }
                                });
                            }else{
                                smart.setVisibility(View.GONE);
                                load_fail.setVisibility(View.VISIBLE);
                            }
                        } else {
                            smart.setVisibility(View.GONE);
                            load_fail.setVisibility(View.VISIBLE);
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        smart.setVisibility(View.GONE);
                        load_fail.setVisibility(View.VISIBLE);
                        AdiUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
      /*  ArrayList<BaseEntity> objects = new ArrayList<>();
        objects.add(new BaseEntity("疯狂烤翅",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589193116702&di=5be0e774d179cd518def675fd5131d63&imgtype=0&src=http%3A%2F%2Fimg.improve-yourmemory.com%2Fpic%2F72071791ca96de6e8983c1310190e0fa-2.jpg",
                12,2,1,1314));
        objects.add(new BaseEntity("猪猪侠：红烧猪肘",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589193116701&di=202cd363fca9935a77b44a13e33e80c9&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F9ea2ef5ed854a3d3754c4e6abe95bda8d1cbc8e4.jpg",
                19,0,1,100));
        objects.add(new BaseEntity("佛跳墙",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589198355094&di=c053aa72ee722ad191af51a65e0b090c&imgtype=0&src=http%3A%2F%2Fwww.lovehhy.net%2Flib%2Fimg%2F3444770%2F782824_0033361303.jpg",
                14,0,2,520));*/

    }

    private void initView(View inflate) {
        smart = inflate.findViewById(R.id.smart);
        rlv = inflate.findViewById(R.id.rlv);
        load_fail = inflate.findViewById(R.id.load_fail);
        mBack = (ImageView) inflate.findViewById(R.id.back);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
