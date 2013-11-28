
package com.android.systemui.statusbar.toggles;

import android.content.Context;
import android.media.AudioManager;
import android.view.View;

import static com.android.internal.util.gummy.GummyConstants.*;
import com.android.systemui.R;
import com.android.systemui.gummy.GummyAction;

public class VibrateToggle extends StatefulToggle {

    private AudioManager mAudioManager;

    @Override
    public void init(Context c, int style) {
        super.init(c, style);
    }

    @Override
    protected void doEnable() {
        GummyAction.launchAction(mContext, GummyConstant.ACTION_VIB.value());
    }

    @Override
    protected void doDisable() {
        GummyAction.launchAction(mContext, GummyConstant.ACTION_VIB.value());
    }

    @Override
    public boolean onLongClick(View v) {
        startActivity(android.provider.Settings.ACTION_SOUND_SETTINGS);
        return super.onLongClick(v);
    }

    @Override
    protected void updateView() {
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        switch (mAudioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_VIBRATE:
                updateCurrentState(State.ENABLED);
                setLabel(R.string.quick_settings_vibrate_on_label);
                setIcon(R.drawable.ic_qs_vibrate_on);
                break;
            default:
                updateCurrentState(State.DISABLED);
                setLabel(R.string.quick_settings_vibrate_off_label);
                setIcon(R.drawable.ic_qs_vibrate_off);
        }
        mAudioManager = null;
        super.updateView();
    }

}
