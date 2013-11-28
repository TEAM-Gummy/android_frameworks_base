/*
 * Copyright (C) 2013 Gummy
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

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import static com.android.internal.util.gummy.GummyConstants.*;
import java.net.URISyntaxException;

public class GummyHelpers {

    // These items will be subtracted from NavBar Actions when RC requests list of
    // Available Actions
    private final GummyConstant[] EXCLUDED_FROM_NAVBAR = {
            GummyConstant.ACTION_CLOCKOPTIONS,
            GummyConstant.ACTION_SILENT,
            GummyConstant.ACTION_VIB,
            GummyConstant.ACTION_SILENT_VIB,
            GummyConstant.ACTION_EVENT,
            GummyConstant.ACTION_TODAY,
            GummyConstant.ACTION_ALARM
    };

    private GummyHelpers() {
    }

    public static Drawable getIconImage(Context mContext, String uri) {
        Drawable actionIcon;
        if (TextUtils.isEmpty(uri)) {
            uri = GummyConstants.GummyConstant.ACTION_NULL.value();
        }
        if (uri.startsWith("**")) {
            return GummyConstants.getActionIcon(mContext, uri);
        } else {  // This must be an app 
            try {
                actionIcon = mContext.getPackageManager().getActivityIcon(Intent.parseUri(uri, 0));
            } catch (NameNotFoundException e) {
                e.printStackTrace();
                actionIcon = GummyConstants.getActionIcon(mContext,
                       GummyConstants.GummyConstant.ACTION_NULL.value());
            } catch (URISyntaxException e) {
                e.printStackTrace();
                actionIcon = GummyConstants.getActionIcon(mContext,
                        GummyConstants.GummyConstant.ACTION_NULL.value());
            }
        }
        return actionIcon;
    }

    public static String[] getGummyActions() {
        // I need to find a good way to subtract the Excluded array from All actions.
        // for now, just return all Actions.
        return GummyConstants.GummyActions();
    }

    public static String getProperSummary(Context mContext, String uri) {
        if (TextUtils.isEmpty(uri)) {
            uri = GummyConstants.GummyConstant.ACTION_NULL.value();
        }
        if (uri.startsWith("**")) {
            return GummyConstants.getProperName(mContext, uri);
        } else {  // This must be an app 
            try {
                Intent intent = Intent.parseUri(uri, 0);
                if (Intent.ACTION_MAIN.equals(intent.getAction())) {
                    return getFriendlyActivityName(mContext, intent);
                }
                return getFriendlyShortcutName(mContext, intent);
            } catch (URISyntaxException e) {
                return GummyConstants.getProperName(mContext, GummyConstants.GummyConstant.ACTION_NULL.value());
            }
        }
    }

    private static String getFriendlyActivityName(Context mContext, Intent intent) {
        PackageManager pm = mContext.getPackageManager();
        ActivityInfo ai = intent.resolveActivityInfo(pm, PackageManager.GET_ACTIVITIES);
        String friendlyName = null;

        if (ai != null) {
            friendlyName = ai.loadLabel(pm).toString();
            if (friendlyName == null) {
                friendlyName = ai.name;
            }
        }

        return (friendlyName != null) ? friendlyName : intent.toUri(0);
    }

    private static String getFriendlyShortcutName(Context mContext, Intent intent) {
        String activityName = getFriendlyActivityName(mContext, intent);
        String name = intent.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);

        if (activityName != null && name != null) {
            return activityName + ": " + name;
        }
        return name != null ? name : intent.toUri(0);
    }
}
