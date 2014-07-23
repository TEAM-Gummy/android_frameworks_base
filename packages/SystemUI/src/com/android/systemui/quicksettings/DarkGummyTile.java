/*
 * Copyright (C) 2014 Team Gummy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.quicksettings;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;

import com.android.internal.util.gummy.cmdProcessor.Helpers;
import com.android.internal.util.gummy.ButtonsConstants;
import com.android.internal.util.gummy.TGActions;

import com.android.systemui.R;
import com.android.systemui.statusbar.phone.QuickSettingsContainerView;
import com.android.systemui.statusbar.phone.QuickSettingsController;

public class DarkGummyTile extends QuickSettingsTile {

    private boolean mDarkGummy = false;

    public DarkGummyTile(Context context, QuickSettingsController qsc) {
        super(context, qsc);

        mOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TGActions.processAction(mContext, ButtonsConstants.ACTION_TG_UI_SWITCH, false);
            }
        };

        qsc.registerObservedContent(Settings.Secure.getUriFor(
                    Settings.Secure.UI_INVERTED_MODE), this);
    }

    @Override
    void onPostCreate() {
        updateTile();
        super.onPostCreate();
    }

    @Override
    public void updateResources() {
        updateTile();
        super.updateResources();
    }

    @Override
    public void onChangeUri(ContentResolver resolver, Uri uri) {
        updateResources();
    }

    private synchronized void updateTile() {
        mDarkGummy = mContext.getResources().getConfiguration().uiInvertedMode
                         == Configuration.UI_INVERTED_MODE_YES;
        if (mDarkGummy) {
            mDrawable = R.drawable.ic_qs_dark_gummy_on;
            mLabel = mContext.getString(R.string.quick_settings_dark_gummy_on_label);
        } else {
            mDrawable = R.drawable.ic_qs_dark_gummy_off;
            mLabel = mContext.getString(R.string.quick_settings_dark_gummy_off_label);
        }
    }
}
