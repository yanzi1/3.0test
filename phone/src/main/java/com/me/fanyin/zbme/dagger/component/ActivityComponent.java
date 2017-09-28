package com.me.fanyin.zbme.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.me.fanyin.zbme.dagger.annotation.PerActivity;
import com.me.fanyin.zbme.dagger.modules.ActivityModule;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.views.course.notes.NoteDetailsActivity;
import com.me.fanyin.zbme.views.course.notes.NotesActivity;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.SystemPlayer.PlayerActivity;
import com.me.fanyin.zbme.views.course.play.audition.AuditionPlayActivity;
import com.me.fanyin.zbme.views.course.play.courselist.CourseListActivity;
import com.me.fanyin.zbme.views.course.studyplan.StudyCaseActivity;
import com.me.fanyin.zbme.views.download.DownloadManagerActivity;
import com.me.fanyin.zbme.views.download.MyDownloadActivity;
import com.me.fanyin.zbme.views.download.MyDownloadCourseActivity;
import com.me.fanyin.zbme.views.main.activity.BooksErrataActivity;
import com.me.fanyin.zbme.views.main.activity.CaptureActivity;
import com.me.fanyin.zbme.views.main.activity.CourseTypeChangeActivity;
import com.me.fanyin.zbme.views.main.activity.MainTypeChangeActivity;
import com.me.fanyin.zbme.views.main.activity.StudentsAppraiseActivity;
import com.me.fanyin.zbme.views.main.activity.SubpageActivity;
import com.me.fanyin.zbme.views.mine.settings.AboutUsActivity;
import com.me.fanyin.zbme.views.mine.settings.FeedbackActivity;
import com.me.fanyin.zbme.views.mine.settings.ResetEmailOrPhoneActivity;
import com.me.fanyin.zbme.views.mine.settings.ResetPswActivity;
import com.me.fanyin.zbme.views.mine.settings.SettingsActivity;
import com.me.fanyin.zbme.views.user.GetBackPswActivity;
import com.me.fanyin.zbme.views.user.LoginActivity;
import com.me.fanyin.zbme.views.user.LoginsActivity;
import com.me.fanyin.zbme.views.user.RegisterActivity;

import dagger.Component;

/**
 * Created by xunice on 18/03/2017.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    AppCompatActivity activity();

    void inject(MainActivity activity);
    void inject(LoginActivity activity);
    void inject(LoginsActivity activity);
    void inject(PlayActivity activity);
    void inject(PlayerActivity activity);
    void inject(AuditionPlayActivity activity);
    void inject(NotesActivity activity);
    void inject(NoteDetailsActivity activity);
    void inject(MainTypeChangeActivity activity);
    void inject(RegisterActivity activity);
    void inject(GetBackPswActivity activity);
    void inject(MyDownloadActivity activity);
    void inject(DownloadManagerActivity activity);
    void inject(MyDownloadCourseActivity activity);
    void inject(CourseListActivity activity);
    void inject(CourseTypeChangeActivity activity);
    void inject(BooksErrataActivity activity);
    void inject(StudentsAppraiseActivity activity);
    void inject(SubpageActivity activity);
    void inject(FeedbackActivity activity);
    void inject(SettingsActivity activity);
    void inject(AboutUsActivity activity);
    void inject(ResetEmailOrPhoneActivity activity);
    void inject(ResetPswActivity activity);
    void inject(CaptureActivity activity);
    void inject(StudyCaseActivity activity);

}
