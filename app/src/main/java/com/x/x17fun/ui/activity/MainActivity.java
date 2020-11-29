package com.x.x17fun.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.NavigationView;

import androidx.navigation.ui.AppBarConfiguration;

import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureConfig;
import com.x.x17fun.R;
import com.x.x17fun.adapter.MenuAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.base.IBaseCallBack;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.ui.fragment.HomeFragment;
import com.x.x17fun.ui.fragment.SetUserProfirtFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView user_name;
    private TextView cool;
    private TextView follower;
    private TextView love;
    private ImageView user_head_img;
    private HomeFragment homeFragment;
    private BaseDialog baseDialog;
    private GeoCoder geoCoder;

    private LatLng latLng;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MenuAdapter adapter;
    private RecyclerView mRlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*StatusBarCompat.setStatusBarColor(this, Color.parseColor("#333333"));
        StatusBarCompat.cancelLightStatusBar(this);*/
        setContentView(R.layout.activity_main);
        getPermission();

        EventBus.getDefault().register(this);

        //注册监听函数
        initView();
        initData();
    }

    @Override
    protected void initView() {

        homeFragment = HomeFragment.newInstance();
         drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        /*navigationView.setItemHorizontalPadding(10);*/
        /*navigationView.setItemIconPadding(10);*/
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        user_name = headerView.findViewById(R.id.user_head);
        user_head_img = headerView.findViewById(R.id.imageView);
        follower = headerView.findViewById(R.id.follower);
        love = headerView.findViewById(R.id.love);
        cool = headerView.findViewById(R.id.cool);
         mRlv = headerView.findViewById(R.id.rlv);


        ArrayList<BaseEntity> objects = new ArrayList<>();
        objects.add(new BaseEntity(R.mipmap.friend,"食堂",""));
        objects.add(new BaseEntity(R.mipmap.wallet,"我的钱包",""));
        objects.add(new BaseEntity(R.mipmap.fabu2,"我发布的",""));
        objects.add(new BaseEntity(R.mipmap.sle,"我卖出的",""));
        objects.add(new BaseEntity(R.mipmap.dingdan,"我买入的",""));
        objects.add(new BaseEntity(R.mipmap.kefu,"客服",""));
        objects.add(new BaseEntity(R.mipmap.setting,"设置",""));

        if (adapter==null){
            adapter = new MenuAdapter();
            mRlv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mRlv.setAdapter(adapter);
            adapter.addData(objects);
            adapter.onClick(new MenuAdapter.onItemClickListener() {
                @Override
                public void onClick(int position) {
                    switch (position) {
                        case 0:
                            startToActivity(MainActivity.this, DiniingHallActivityActivity.class);
                            break;
                        case 1:
                            startToActivity(MainActivity.this, MyWalletActivity.class);
                            break;
                        case 2:
                            startToActivity(MainActivity.this, SaleActivity.class, "2");
                            break;
                        case 3:
                            startToActivity(MainActivity.this, SaleActivity.class, "1");
                            break;
                        case 4:
                            startToActivity(MainActivity.this, SaleActivity.class, "3");
                            break;
                        case 5:
                            showDialog();
                            break;
                        case 6:
                            startToActivity(MainActivity.this, SettingActivity.class);
                            break;
                    }
                }
            });
        }else{
            adapter.addData(objects);
        }

        /*loadCircleImg(R.mipmap.header, user_head_img);*/

      /*  //设置状态栏透明
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
*/


       /* navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.wallet:
                        startToActivity(MainActivity.this, MyWalletActivity.class);
                        break;
                    case R.id.push:
                        startToActivity(MainActivity.this, SaleActivity.class, "2");
                        break;
                    case R.id.out:
                        startToActivity(MainActivity.this, SaleActivity.class, "1");
                        break;
                    case R.id.buyin:
                        startToActivity(MainActivity.this, SaleActivity.class, "3");
                        break;
                    case R.id.call:
                        showDialog();
                        break;
                    case R.id.setting:
                        startToActivity(MainActivity.this, SettingActivity.class);
                        break;
                }
                return false;
            }
        });
*/
        user_head_img.setOnClickListener(v -> startToActivity(MainActivity.this, UserProfileActivity.class));

        user_name.setOnClickListener(v -> startToActivity(MainActivity.this, UserProfileActivity.class));

        loadRootFragment(R.id.frag, HomeFragment.newInstance());
    }

    private void showDialog() {

        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_kefu, null);
        TextView phone = inflate.findViewById(R.id.phone);
        baseDialog = new BaseDialog(MainActivity.this, inflate, Gravity.CENTER);
        baseDialog.show();
    }

    private void getPermission() {
        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);

        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {

            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
    }


    @Override
    protected void initData() {


        syncUser(new IBaseCallBack<UserSyncEntity>() {
            @Override
            public void onSuccess(UserSyncEntity data) {
                App.defaultApp().saveUserMsg(data);
                if (data.getUserInfo() != null && data.getUserInfo().getPortait() != null) {
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.mipmap.yiqifan)
                            .error(R.mipmap.yiqifan)
                            .circleCrop();
                    Glide.with(MainActivity.this)
                            .load(data.getUserInfo().getPortait())
                            .apply(requestOptions)
                            .into(user_head_img);
                }
                user_name.setText(data.getUserInfo().getNickName());
                follower.setText(data.getUserInfo().getFansCount() + "");
                love.setText(data.getUserInfo().getFocusCount() + "");
                cool.setText(data.getUserInfo().getAppreciateAmount() + "");
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }


    private void load() {
    }

    @Override
    protected void initLogic() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiver(EventMessage event){
        if (event.getType() == 1){
           drawer.openDrawer(navigationView);
        }else if(event.getType()==2){
            initData();
        }else{

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == 200) {
            if (resultCode == 700){
                EventBus.getDefault().post(new EventMessage(2,data.getStringExtra("address")));
                App.mLocationClient.unRegisterLocationListener(myListener);
            }
        }*/  if (requestCode == 1101) {
            if (resultCode == Activity.RESULT_OK) {
                EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_QRCODE, data.getStringExtra("result")));
            }
        } else if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (data != null) {
                        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_FEEDBACK, data));
                    }
                    break;
            }

        }
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

  /*  @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    @Override
    public void onBackPressedSupport() {
        if (drawer.isDrawerVisible(navigationView)){
            drawer.closeDrawers();
        }else{
            super.onBackPressedSupport();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);

    }
}
