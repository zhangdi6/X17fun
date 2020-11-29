package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.x.x17fun.R;
import com.x.x17fun.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaleFragment extends BaseFragment {


    private View view;
    private ImageView mBack;
    private TabLayout mTabLayout;
    private ViewPager mVp;

    public static SaleFragment getInstance() {
        return new SaleFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sale, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        ImageView back = inflate.findViewById(R.id.back);
        back.setOnClickListener(v -> pop());
        mBack = (ImageView) inflate.findViewById(R.id.back);
        mTabLayout = (TabLayout) inflate.findViewById(R.id.tabLayout);
        mVp = (ViewPager) inflate.findViewById(R.id.vp);
    }
}
