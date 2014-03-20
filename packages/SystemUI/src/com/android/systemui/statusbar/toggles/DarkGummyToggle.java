/*
* Copyright (C) 2014 Gummy
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.android.systemui.statusbar.toggles;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import com.android.internal.util.gummy.cmdProcessor.Helpers;
import com.android.systemui.R;

import java.util.List;

public class DarkGummyToggle extends StatefulToggle {

    private boolean mDarkGummy = false;

    @Override
    public void init(Context c, int style) {
        super.init(c, style);
        scheduleViewUpdate();
    }

    @Override
    protected void doEnable() {
        Settings.Secure.putInt(mContext.getContentResolver(),
                Settings.Secure.UI_INVERTED_MODE, 2);
        onInvertedModeChange();
    }

    @Override
    protected void doDisable() {
        Settings.Secure.putInt(mContext.getContentResolver(),
                Settings.Secure.UI_INVERTED_MODE, 1);
        onInvertedModeChange();
    }

    @Override
    protected void updateView() {
        mDarkGummy = mContext.getResources().getConfiguration().uiInvertedMode
                         == Configuration.UI_INVERTED_MODE_YES;
        if (mDarkGummy) {
            setIcon(R.drawable.ic_qs_dark_gummy_on);
            setLabel(R.string.quick_settings_dark_gummy_on_label);
            updateCurrentState(State.ENABLED);
        } else {
            setIcon(R.drawable.ic_qs_dark_gummy_off);
            setLabel(R.string.quick_settings_dark_gummy_off_label);
            updateCurrentState(State.DISABLED);
        }
        super.updateView();
    }

    private void onInvertedModeChange() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> pids = am.getRunningAppProcesses();
           for(int i = 0; i < pids.size(); i++) {
               ActivityManager.RunningAppProcessInfo info = pids.get(i);
               if(info.processName.equalsIgnoreCase("com.android.contacts")) {
                    am.killBackgroundProcesses("com.android.contacts");
               }
               if(info.processName.equalsIgnoreCase("com.google.android.gm")) {
                    am.killBackgroundProcesses("com.google.android.gm");
               }
               if(info.processName.equalsIgnoreCase("com.android.email")) {
                    am.killBackgroundProcesses("com.android.email");
               }
               if(info.processName.equalsIgnoreCase("com.android.vending")) {
                    am.killBackgroundProcesses("com.android.vending");
               }
               if(info.processName.equalsIgnoreCase("com.google.android.talk")) {
                    am.killBackgroundProcesses("com.google.android.talk");
               }
               if(info.processName.equalsIgnoreCase("com.android.mms")) {
                    am.killBackgroundProcesses("com.android.mms");
               }
               if(info.processName.equalsIgnoreCase("com.google.android.googlequicksearchbox")) {
                    am.killBackgroundProcesses("com.google.android.googlequicksearchbox");
               }
               if(info.processName.equalsIgnoreCase("com.google.android.youtube")) {
                    am.killBackgroundProcesses("com.google.android.youtube");
               }
               if(info.processName.equalsIgnoreCase("com.google.android.apps.plus")) {
                    am.killBackgroundProcesses("com.google.android.apps.plus");
               }
           }
        Helpers.restartSystemUI();
    }

    @Override
    public int getDefaultIconResId() {
        return R.drawable.ic_qs_dark_gummy_off;
    }
}
