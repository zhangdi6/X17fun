package com.x.x17fun.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.x.x17fun.R;
import com.x.x17fun.adapter.MsgFunctionRlvAdapter;
import com.x.x17fun.adapter.MsgRlvAdapter;
import com.x.x17fun.base.BaseBarActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.ChatEntity;
import com.x.x17fun.entity.UnReadListEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DateFormatUtils;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MsgActivity extends BaseBarActivity  {

    private ImageView back;
    private RecyclerView rlv;
    private RecyclerView msg_rlv;
    private MsgFunctionRlvAdapter functionRlvAdapter;
    private MsgRlvAdapter msgRlvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_msg);
        initView();
        initData();
        initLogic();
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlv = (RecyclerView) findViewById(R.id.rlv);
        msg_rlv = (RecyclerView) findViewById(R.id.msg_rlv);
    }

    @Override
    protected void onResume() {
        initLogic();
        super.onResume();
    }

    @Override
    protected void initData() {

        ArrayList <BaseEntity> list = new ArrayList<>();
        list.add(new BaseEntity(R.mipmap.zan,"赞",0));
        list.add(new BaseEntity(R.mipmap.pinlun,"评论",0));
        list.add(new BaseEntity(R.mipmap.guanzhu,"关注",0));
        list.add(new BaseEntity(R.mipmap.fensi,"粉丝",0));
        list.add(new BaseEntity(R.mipmap.quanquan,"饭圈",0));

         functionRlvAdapter = new MsgFunctionRlvAdapter();
         rlv.setLayoutManager(new LinearLayoutManager(MsgActivity.this));

         rlv.setAdapter(functionRlvAdapter);
        functionRlvAdapter.addData(list);
         rlv.setHasFixedSize(true);
         rlv.setNestedScrollingEnabled(false);
       /* ArrayList <BaseEntity> list_msg = new ArrayList<>();
        list_msg.add(new BaseEntity(R.drawable.mayun,"马云","17:44","张先生，麻烦您提供一下银行卡账号，我们稍后把尾款结给您"));
        list_msg.add(new BaseEntity(R.drawable.nazha,"娜扎","18:55","晚上真的不跟我一起吃晚餐吗，我房间都订好了"));
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initLogic() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        hashMap.put("userId",userId);
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        DataService.getHomeService().unreadMessages(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UnReadListEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UnReadListEntity> baseResult) {
                        Log.e("result",baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            if (baseResult.getData()!=null && baseResult.getData().getUnReadRecordsBean()!=null
                             && baseResult.getData().getUnReadRecordsBean().size()>0){
                                List<UnReadListEntity.UnReadRecordBean> unReadRecordsBean = baseResult.getData().getUnReadRecordsBean();

                                DeeSpUtil.getInstance().putObject("bean",baseResult.getData());

                                msgRlvAdapter = new MsgRlvAdapter();
                                msg_rlv.setLayoutManager(new LinearLayoutManager(MsgActivity.this));
                                msg_rlv.setAdapter(msgRlvAdapter);
                                msgRlvAdapter.addData(unReadRecordsBean);
                                msg_rlv.setHasFixedSize(true);
                                msg_rlv.setNestedScrollingEnabled(false);

                                msgRlvAdapter.onClick(position -> {
                                    UnReadListEntity.UnReadRecordBean recordBean = unReadRecordsBean.get(position);
                                    recordBean.setUnreadCounts(0);
                                    unReadRecordsBean.set(position,recordBean);
                                    baseResult.getData().setUnReadRecordsBean(unReadRecordsBean);
                                    DeeSpUtil.getInstance().putObject("bean",baseResult.getData());
                                    Intent intent = new Intent(MsgActivity.this, ChatActivity.class);
                                    intent.putExtra("name",unReadRecordsBean.get(position).getNickName());
                                    intent.putExtra("providerId",unReadRecordsBean.get(position).getFromUserId());
                                    startActivity(intent);
                                });
                            }else{
                                UnReadListEntity bean = DeeSpUtil.getInstance().
                                        getObject("bean", UnReadListEntity.class);
                               if (bean!=null){
                                   msgRlvAdapter = new MsgRlvAdapter();
                                   msg_rlv.setLayoutManager(new LinearLayoutManager(MsgActivity.this));
                                   msg_rlv.setAdapter(msgRlvAdapter);
                                   msgRlvAdapter.addData(bean.getUnReadRecordsBean());
                                   msg_rlv.setHasFixedSize(true);
                                   msg_rlv.setNestedScrollingEnabled(false);

                                   msgRlvAdapter.onClick(position -> {
                                       Intent intent = new Intent(MsgActivity.this, ChatActivity.class);
                                       intent.putExtra("name",bean.getUnReadRecordsBean().get(position).getNickName());
                                       intent.putExtra("providerId",bean.getUnReadRecordsBean().get(position).getFromUserId());
                                       startActivity(intent);
                                   });
                               }
                            }
                        }else{
                            UnReadListEntity bean = DeeSpUtil.getInstance().getObject("bean", UnReadListEntity.class);
                            if (bean!=null){
                                msgRlvAdapter = new MsgRlvAdapter();
                                msg_rlv.setLayoutManager(new LinearLayoutManager(MsgActivity.this));
                                msg_rlv.setAdapter(msgRlvAdapter);
                                msgRlvAdapter.addData(bean.getUnReadRecordsBean());
                                msg_rlv.setHasFixedSize(true);
                                msg_rlv.setNestedScrollingEnabled(false);

                                msgRlvAdapter.onClick(position -> {
                                    Intent intent = new Intent(MsgActivity.this, ChatActivity.class);
                                    intent.putExtra("name",bean.getUnReadRecordsBean().get(position).getNickName());
                                    intent.putExtra("providerId",bean.getUnReadRecordsBean().get(position).getFromUserId());
                                    startActivity(intent);
                                });
                            }
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
    }

}
