package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.base.IBaseCallBack;
import com.x.x17fun.entity.UserSyncEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends BaseFragment {

    private TextView balance;

    public static WalletFragment getInstance(){
        return new WalletFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_wallet, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        syncUser(new IBaseCallBack<UserSyncEntity>() {
            @Override
            public void onSuccess(UserSyncEntity data) {
                UserSyncEntity.UserInfoBean userInfo = data.getUserInfo();
                balance.setText(userInfo.getCoinAmount()+"");
                App.defaultApp().saveUserMsg(data);
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    @Override
    public void onSupportVisible() {
        initData();
    }

    private void initView(View inflate) {

        ImageView back = inflate.findViewById(R.id.back);
        TextView ok = inflate.findViewById(R.id.ok);
        balance = inflate.findViewById(R.id.balance);
        TextView detail = inflate.findViewById(R.id.detail);
        TextView in = inflate.findViewById(R.id.in);
        TextView out = inflate.findViewById(R.id.out);

        back.setOnClickListener(v -> getActivity().finish());

        ok.setOnClickListener(v -> {
            start(BankCardFragment.getInstance());
        });

        detail.setOnClickListener(v -> start(BillFragment.newInstance()));

        in.setOnClickListener(v -> start(ChargeFragment.getInstance()));

        out.setOnClickListener(v -> start(ReflectFragment.getInstance()));



    }
}
