
package com.android.systemui.statusbar.toggles;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import com.android.systemui.R;

public class HoverToggle extends StatefulToggle {
    HoverObserver mObserver = null;

    public static final String SETTINGS_APP = "com.android.settings";
    public static final String HOVER_OPTIONS =
            "com.android.settings.Settings$HoverOptionsActivity";

    @Override
    public void init(Context c, int style) {
        super.init(c, style);
        mObserver = new HoverObserver(mHandler);
        mObserver.observe();
    }

    @Override
    protected void cleanup() {
        if (mObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(mObserver);
            mObserver = null;
        }
        super.cleanup();
    }

    @Override
    protected void doEnable() {
        Settings.System.putInt(mContext.getContentResolver(),
                Settings.System.HOVER_STATE, 1);
    }

    @Override
    protected void doDisable() {
        Settings.System.putInt(mContext.getContentResolver(),
                Settings.System.HOVER_STATE, 0);
    }

    @Override
    public boolean onLongClick(View v) {
        Intent hoverOptions = new Intent().setClassName(SETTINGS_APP, HOVER_OPTIONS);
        hoverOptions.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FLOATING_WINDOW);
        collapseStatusBar();
        dismissKeyguard();
        startActivity(hoverOptions);
        return super.onLongClick(v);
    }

    @Override
    protected void updateView() {
        boolean enabled = Settings.System.getBoolean(mContext.getContentResolver(),
                Settings.System.HOVER_STATE, false);
        setIcon(enabled
                ? R.drawable.ic_qs_hover_on
                : R.drawable.ic_qs_hover_off);
        setLabel(enabled
                ? R.string.quick_settings_hover_on_label
                : R.string.quick_settings_hover_off_label);
        updateCurrentState(enabled ? State.ENABLED : State.DISABLED);
        super.updateView();
    }

    protected class HoverObserver extends ContentObserver {
        HoverObserver(Handler handler) {
            super(handler);
            observe();
        }

        void observe() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.HOVER_STATE), false, this);
            onChange(false);
        }

        @Override
        public void onChange(boolean selfChange) {
            scheduleViewUpdate();
        }
    }

    @Override
    public int getDefaultIconResId() {
        return R.drawable.ic_qs_hover_on;
    }
}
