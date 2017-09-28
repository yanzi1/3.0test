package com.me.fanyin.zbme.views;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.views.user.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.guide_layout)
    View guide_layout;

    private int[] imageIds={R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3};

    @Override
    protected void initView() {
        List<ImageView> ivs=new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView=new ImageView(this);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(imageIds[i]);
            ivs.add(imageView);

        }
        viewPager.setAdapter(new GuidePagerAdapter(ivs));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == imageIds.length-1){
                    guide_layout.setVisibility(View.VISIBLE);
                }else{
                    guide_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.go_main_bt,R.id.go_login_bt})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.go_main_bt:
                gotoActivity(MainActivity.class,true);
                break;
            case R.id.go_login_bt:
                gotoActivity(MainActivity.class);
                gotoActivity(LoginActivity.class,true);
                break;
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_guide;
    }

    class GuidePagerAdapter extends PagerAdapter{
        private List<? extends View> ivs;

        public GuidePagerAdapter(List<? extends View> ivs) {
            this.ivs = ivs;
        }

        @Override
        public int getCount() {
            return ivs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(ivs.get(position));
            return ivs.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(ivs.get(position));
        }
    }
}
