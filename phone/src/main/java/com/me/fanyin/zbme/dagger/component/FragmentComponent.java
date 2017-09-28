package com.me.fanyin.zbme.dagger.component;

import com.me.fanyin.zbme.dagger.annotation.PerFragment;
import com.me.fanyin.zbme.dagger.modules.FragmentModule;
import com.me.fanyin.zbme.views.course.CourseFragment;
import com.me.fanyin.zbme.views.course.play.fragment.CourseCommentFragment;
import com.me.fanyin.zbme.views.download.DownloadMoreFragment;
import com.me.fanyin.zbme.views.main.MainFragment;
import com.me.fanyin.zbme.views.main.fragemnt.FreeCourseFragment;
import com.me.fanyin.zbme.views.main.fragemnt.MainTypeFragment;
import com.me.fanyin.zbme.views.main.fragemnt.RecommendedFragment;
import com.me.fanyin.zbme.views.main.fragemnt.SubpageFragment;
import com.me.fanyin.zbme.views.mine.MineFragment;
import com.me.fanyin.zbme.views.user.LoginFragment;

import dagger.Component;

/**
 * Created by xunice on 18/03/2017.
 */

@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MainFragment fragment);
    void inject(LoginFragment fragment);
    void inject(MainTypeFragment fragment);
    void inject(SubpageFragment fragment);
    void inject(CourseFragment fragment);
    void inject(MineFragment fragment);
    void inject(CourseCommentFragment fragment);
    void inject(RecommendedFragment fragment);
    void inject(FreeCourseFragment fragment);
    void inject(DownloadMoreFragment fragment);
}