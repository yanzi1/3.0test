package com.me.fanyin.zbme.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by xd on 2017/6/14.
 */

public class BanPasteEdittext extends android.support.v7.widget.AppCompatEditText implements ActionMode.Callback {

    public BanPasteEdittext(Context context) {
        this(context,null);
    }

    public BanPasteEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCustomSelectionActionModeCallback(this);
    }


    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste)
            return false;
        return super.onTextContextMenuItem(id);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
