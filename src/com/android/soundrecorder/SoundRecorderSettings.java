/*
 * Copyright (C) 2007 The Android Open Source Project
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

package com.android.soundrecorder;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

/**
 *  SoundRecorderSettings
 */
public class SoundRecorderSettings extends PreferenceActivity
    implements OnSharedPreferenceChangeListener
{
    public static final String KEY_AUDIO_ENCODER = "pref_soundrecorder_audioencoder_key";
    public static final int DEFAULT_AUDIO_ENCODER_VALUE = 1;

    private ListPreference mAudioEncoder;

    public SoundRecorderSettings()
    {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.soundrecorder_preferences);

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initUI() {
        mAudioEncoder = (ListPreference) findPreference(KEY_AUDIO_ENCODER);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    private void updateAudioEncoder() {
        int vidAudioEncoderValue = getIntPreference(mAudioEncoder,DEFAULT_AUDIO_ENCODER_VALUE );
        int vidAudioEncoderIndex = 0;
        if(vidAudioEncoderValue == 1){        // MediaRecorder.AudioEncoder.AMR_NB
            vidAudioEncoderIndex = 0;
        }
        else if(vidAudioEncoderValue == 2){   // MediaRecorder.AudioEncoder.AMR_WB
            vidAudioEncoderIndex = 1;
        }
        else if(vidAudioEncoderValue == 3){   // MediaRecorder.AudioEncoder.AMR_WB
            vidAudioEncoderIndex = 2;
        }
        String[] vidAudioEncoders =
            getResources().getStringArray(R.array.pref_soundrecorder_audioencoder_entries);
        String vidAudioEncoder = vidAudioEncoders[vidAudioEncoderIndex];
        mAudioEncoder.setSummary(vidAudioEncoder);
    }

    private static int getIntPreference(ListPreference preference, int defaultValue) {
        String s = preference.getValue();
        int result = defaultValue;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            // Ignore, result is already the default value.
        }
        return result;
    }

    private boolean getBooleanPreference(ListPreference preference, boolean defaultValue) {
        return getIntPreference(preference, defaultValue ? 1 : 0) != 0;
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
           if (key.equals(KEY_AUDIO_ENCODER)) {
               updateAudioEncoder();
           }
    }
}

