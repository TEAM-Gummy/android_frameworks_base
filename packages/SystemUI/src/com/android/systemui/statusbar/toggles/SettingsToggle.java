
package com.android.systemui.statusbar.toggles;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.android.systemui.R;

public class SettingsToggle extends BaseToggle {

    public static final String SETTINGS_APP = "com.android.settings";
    public static final String GUMMY_INTERFACE =
            "com.android.settings.Settings$GummyInterfaceActivity";

    @Override
    protected void init(Context c, int style) {
        super.init(c, style);
        setIcon(R.drawable.ic_qs_settings);
        setLabel(R.string.quick_settings_settings_label);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        vibrateOnTouch();
        collapseStatusBar();
        dismissKeyguard();
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        Intent gummyInterface = new Intent().setClassName(SETTINGS_APP, GUMMY_INTERFACE);
        gummyInterface.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FLOATING_WINDOW);
        collapseStatusBar();
        dismissKeyguard();
        startActivity(gummyInterface);
        return super.onLongClick(v);
    }

    @Override
    public int getDefaultIconResId() {
        return R.drawable.ic_qs_settings;
    }
}
