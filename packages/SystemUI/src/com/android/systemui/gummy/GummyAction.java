/*
 * Copyright (C) 2013 Android Open Kang Project
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

package com.android.systemui.gummy;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.widget.Toast;

import static com.android.internal.util.gummy.GummyConstants.*;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.R;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

public class GummyAction {

    public final static String TAG = "GummyAction";

    private GummyAction() {
    }

    public static boolean launchAction(Context mContext, String action) {
        if (TextUtils.isEmpty(action) || action.equals(GummyConstant.ACTION_NULL)) {
            return false;
        }

            GummyConstant GummyEnum = fromString(action);
            AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            switch(GummyEnum) {
            case ACTION_TODAY:
                long startMillis = System.currentTimeMillis();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intentToday = new Intent(Intent.ACTION_VIEW)
                    .setData(builder.build());
                intentToday.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentToday);
                break;
            case ACTION_CLOCKOPTIONS:
                Intent intentClock = new Intent(Intent.ACTION_QUICK_CLOCK);
                intentClock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentClock);
                break;
            case ACTION_EVENT:
                Intent intentEvent = new Intent(Intent.ACTION_INSERT)
                    .setData(Events.CONTENT_URI);
                intentEvent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentEvent);
                break;
            case ACTION_VOICEASSIST:
                Intent intentVoice = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
                intentVoice.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentVoice);
                break;
            case ACTION_ALARM:
                Intent intentAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                intentAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentAlarm);
                break;
            case ACTION_APP:
                try {
                    Intent intentapp = Intent.parseUri(action, 0);
                    intentapp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intentapp);
                } catch (URISyntaxException e) {
                    Log.e(TAG, "URISyntaxException: [" + action + "]");
                } catch (ActivityNotFoundException e){
                    Log.e(TAG, "ActivityNotFound: [" + action + "]");
                }
                break;
            }
            return true;
    }

    private static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            }
        }
    };
}
