package com.viviant.earngold.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.viviant.earngold.R;
import com.viviant.earngold.base.BaseActivity;
import com.viviant.earngold.bean.Gank;
import com.viviant.earngold.mvp.main.contract.BSMainContract;
import com.viviant.earngold.mvp.main.presenter.BSMainPresenter;
import com.viviant.earngold.ui.fragment.BSMainFragment;
import com.viviant.earngold.ui.fragment.ShopsMainFragment;
import com.viviant.earngold.ui.fragment.OwnerMainFragment;
import com.viviant.earngold.widget.customtoast.CustomToast;

import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseActivity<BSMainPresenter>
        implements BSMainContract.View, /*NavigationView.OnNavigationItemSelectedListener,*/ BottomNavigationBar.OnTabSelectedListener {


    @Bind(R.id.bottomNavigationBar)
    BottomNavigationBar bottomNavigationBar;

    private static final String TAG = MainActivity.class.getSimpleName();

    private BSMainFragment mBSMainFragment;
    private OwnerMainFragment mOwnerMainFragment;
//    private IntroduceFlowMainFragment mIntroduceFlowMainFragment;
//    private DayNiceGoodsMainFragment mDayNiceGoodsMainFragment;
    private ShopsMainFragment mShopsMainFragment;
    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

//        android.os.Debug.startMethodTracing("traceview1");
        initBottomNavigationBar();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        initFragment(savedInstanceState);
//        android.os.Debug.stopMethodTracing();

    }

    private void initBottomNavigationBar() {
        bottomNavigationBar
                .setActiveColor(R.color.colorBG)
                .setInActiveColor(R.color.colorGray)
                .setBarBackgroundColor(R.color.colorText);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_camera, "camera"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_share, "Share"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_manage, "manage"))
                .initialise();


        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //开启事务
        switch (position) {
            case 0:

                transaction.hide(mOwnerMainFragment);
//                transaction.hide(mIntroduceFlowMainFragment);
//                transaction.hide(mDayNiceGoodsMainFragment);
                transaction.hide(mShopsMainFragment);
                transaction.show(mBSMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.show(mOwnerMainFragment);
//                transaction.hide(mIntroduceFlowMainFragment);
//                transaction.hide(mDayNiceGoodsMainFragment);
                transaction.hide(mShopsMainFragment);
                transaction.hide(mBSMainFragment);
                transaction.commitAllowingStateLoss();
                break;
//            case 2:
//                transaction.hide(mOwnerMainFragment);
//                transaction.show(mIntroduceFlowMainFragment);
//                transaction.hide(mDayNiceGoodsMainFragment);
//                transaction.hide(mShopsMainFragment);
//                transaction.hide(mBSMainFragment);
//                transaction.commitAllowingStateLoss();
//                break;
//            case 3:
//                transaction.hide(mOwnerMainFragment);
//                transaction.hide(mIntroduceFlowMainFragment);
//                transaction.show(mDayNiceGoodsMainFragment);
//                transaction.hide(mShopsMainFragment);
//                transaction.hide(mBSMainFragment);
//                transaction.commitAllowingStateLoss();
//                break;
            case 1:
                transaction.hide(mOwnerMainFragment);
//                transaction.hide(mIntroduceFlowMainFragment);
//                transaction.hide(mDayNiceGoodsMainFragment);
                transaction.show(mShopsMainFragment);
                transaction.hide(mBSMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {
        Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");
    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState != null) {
            mBSMainFragment = (BSMainFragment) getSupportFragmentManager().findFragmentByTag("mBSMainFragment");
            mOwnerMainFragment = (OwnerMainFragment) getSupportFragmentManager().findFragmentByTag("mOwnerMainFragment");
//            mIntroduceFlowMainFragment = (IntroduceFlowMainFragment)  getSupportFragmentManager().findFragmentByTag("mIntroduceFlowMainFragment");
//            mDayNiceGoodsMainFragment = (DayNiceGoodsMainFragment)  getSupportFragmentManager().findFragmentByTag("mDayNiceGoodsMainFragment");
            mShopsMainFragment = (ShopsMainFragment)  getSupportFragmentManager().findFragmentByTag("mShopsMainFragment");
        } else {
            mBSMainFragment = new BSMainFragment();
            mOwnerMainFragment = new OwnerMainFragment();
//            mIntroduceFlowMainFragment = new IntroduceFlowMainFragment();
//            mDayNiceGoodsMainFragment = new DayNiceGoodsMainFragment();
            mShopsMainFragment = new ShopsMainFragment();

            transaction.add(R.id.fl_body, mBSMainFragment, "mBSMainFragment");
//            transaction.add(R.id.fl_body, mIntroduceFlowMainFragment, "mIntroduceFlowMainFragment");
            transaction.add(R.id.fl_body, mOwnerMainFragment, "mOwnerMainFragment");
//            transaction.add(R.id.fl_body, mDayNiceGoodsMainFragment, "mDayNiceGoodsMainFragment");
            transaction.add(R.id.fl_body, mShopsMainFragment, "mShopsMainFragment");

        }
        transaction.commit();
        setMainFragment();
    }

    private void setMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mOwnerMainFragment);
//        transaction.hide(mIntroduceFlowMainFragment);
//        transaction.hide(mDayNiceGoodsMainFragment);
        transaction.hide(mShopsMainFragment);
        transaction.show(mBSMainFragment);

        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit() {
//        boolean hasRated = CommonUtils.getSharedPreference(this).getBoolean("hasRated",false);
//        int count = CommonUtils.getSharedPreference(this).getInt("app_exit_count", 1);
//        if (!hasRated&&(count==5||count%10==0)) {
//            count++;
//            CommonUtils.getSharedPreference(this).edit().putInt("app_exit_count", count).commit();
//            showRatingDialog();
//            return;
//        }
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            CustomToast.makeTextWarningShort(this, "再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
//            count++;
//            CommonUtils.getSharedPreference(this).edit().putInt("app_exit_count", count).commit();
            finish();
            //System.exit(0);
        }
    }

//    public void showRatingDialog() {
//        new MaterialDialog.Builder(this)
//                .title("给个好评")
//                .content("陛下，如果您喜欢这个应用，请给个好评！您的鼓励是我们做得更好的动力！")
//                .negativeText("继续退出")
//                .positiveText("去评分")
//                .cancelable(false)
//                .canceledOnTouchOutside(false)
//                .onPositive((dialog, which) -> {
//                    CommonUtils.getSharedPreference(this).edit().putBoolean("hasRated",true).commit();
//                    openToRate();
//
//                })
//                .onNegative((dialog, which) -> finish()).show();
//    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        super.onCreateOptionsMenu(menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_cpa) {
//            transaction.hide(mOwnerMainFragment);
//            transaction.hide(mIntroduceFlowMainFragment);
//            transaction.hide(mDayNiceGoodsMainFragment);
//            transaction.hide(mShopsMainFragment);
//            transaction.show(mBSMainFragment);
//            transaction.commitAllowingStateLoss();
//        } else if (id == R.id.nav_self_media) {
//            transaction.hide(mBSMainFragment);
//            transaction.hide(mIntroduceFlowMainFragment);
//            transaction.show(mOwnerMainFragment);
//            transaction.hide(mDayNiceGoodsMainFragment);
//            transaction.hide(mShopsMainFragment);
//            transaction.commitAllowingStateLoss();
//        } else if (id == R.id.nav_introduce_flow) {
//            transaction.hide(mBSMainFragment);
//            transaction.show(mIntroduceFlowMainFragment);
//            transaction.hide(mOwnerMainFragment);
//            transaction.hide(mShopsMainFragment);
//            transaction.hide(mDayNiceGoodsMainFragment);
//            transaction.commitAllowingStateLoss();
//        } else if (id == R.id.nav_day_nice_goods) {
//            transaction.hide(mBSMainFragment);
//            transaction.hide(mIntroduceFlowMainFragment);
//            transaction.show(mDayNiceGoodsMainFragment);
//            transaction.hide(mShopsMainFragment);
//            transaction.hide(mOwnerMainFragment);
//            transaction.commitAllowingStateLoss();
//        } else if (id == R.id.nav_bmob) {
//            transaction.hide(mBSMainFragment);
//            transaction.hide(mIntroduceFlowMainFragment);
//            transaction.hide(mDayNiceGoodsMainFragment);
//            transaction.show(mShopsMainFragment);
//            transaction.hide(mOwnerMainFragment);
//            transaction.commitAllowingStateLoss();
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public void showDialog() {
    }

    @Override
    public void onFail(String err) {
        Log.e(TAG, err);
//        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSucceed(Gank data) {
//        Toast.makeText(this, "请求成功", Toast.LENGTH_SHORT).show();
        List<Gank.Result> results = data.getResults();
//        mTextView.setText(results.get(new Random().nextInt(10)).toString());

        for (Gank.Result result : results) {
            Log.e(TAG, result.toString());
        }
    }

    @Override
    protected BSMainPresenter onCreatePresenter() {
        return new BSMainPresenter(this);
    }

    @Override
    public void hideDialog() {
    }


}
