package com.me.fanyin.zbme.views.course.notes;

import com.me.data.model.play.NoteClassDetail;
import com.me.data.model.play.NoteDetail;
import com.me.data.mvp.MvpView;

/**
 * Created by wenpeng on 30/03/2017.
 */

public interface NotesDetailView extends MvpView {
	void showResult(NoteDetail noteDetail);
	void showClassResult(NoteClassDetail noteDetail);
	void showResult(String data);
	void classDelResult(String msg);
}
