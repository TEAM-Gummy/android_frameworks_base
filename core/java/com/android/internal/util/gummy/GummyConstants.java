/*
 * Copyright (C) 2013 AOKP by Mike Wilson - Zaphod-Beeblebrox && Steve Spear - Stevespear426
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

package com.android.internal.util.gummy;

import com.android.internal.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class GummyConstants {

    public static enum GummyConstant {
        ACTION_CLOCKOPTIONS  { @Override public String value() { return "**clockoptions**";}},
        ACTION_VOICEASSIST   { @Override public String value() { return "**voiceassist**";}},
        ACTION_EVENT         { @Override public String value() { return "**event**";}},
        ACTION_TODAY         { @Override public String value() { return "**today**";}},
        ACTION_ALARM         { @Override public String value() { return "**alarm**";}},
        ACTION_NULL          { @Override public String value() { return "**null**";}},
        ACTION_APP           { @Override public String value() { return "**app**";}};
        public String value() { return this.value(); }
    }

    public static GummyConstant fromString(String string) {
        if (!TextUtils.isEmpty(string)) {
            GummyConstant[] allTargs = GummyConstant.values();
            for (int i=0; i < allTargs.length; i++) {
                if (string.equals(allTargs[i].value())) {
                    return allTargs[i];
                }
            }
        }
        // not in ENUM must be custom
        return GummyConstant.ACTION_APP;
    }

    public static String[] GummyActions() {
        return fromGummyActionArray(GummyConstant.values());
    }

    public static String[] fromGummyActionArray(GummyConstant[] allTargs) {
        int actions = allTargs.length;
        String[] values = new String [actions];
        for (int i = 0; i < actions; i++) {
            values [i] = allTargs[i].value();
        }
        return values;
    }

    public static String getProperName(Context context, String actionstring) {
        // Will return a string for the associated action, but will need the caller's context to get resources.
        Resources res = context.getResources();
        String value = "";
        if (TextUtils.isEmpty(actionstring)) {
            actionstring = GummyConstant.ACTION_NULL.value();
        }
        GummyConstant action = fromString(actionstring);
        switch (action) {
            case ACTION_CLOCKOPTIONS:
                value = res.getString(com.android.internal.R.string.action_clockoptions);
                break;
            case ACTION_VOICEASSIST:
                value = res.getString(com.android.internal.R.string.action_voiceassist);
                break;
            case ACTION_EVENT:
                value = res.getString(com.android.internal.R.string.action_event);
                break;
            case ACTION_TODAY:
                value = res.getString(com.android.internal.R.string.action_today);
                break;
            case ACTION_ALARM:
                value = res.getString(com.android.internal.R.string.action_alarm);
                break;
            case ACTION_APP:
                value = res.getString(com.android.internal.R.string.action_app);
                break;
            case ACTION_NULL:
            default:
                value = res.getString(com.android.internal.R.string.action_null);
                break;

        }
        return value;
    }
}
