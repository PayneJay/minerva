package com.minerva.business.article.detail;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.minerva.common.Constants;

class MySynthesizerListener implements SynthesizerListener {
    private Context context;
    private SpeakListener speakListener;

    MySynthesizerListener(Context context, SpeakListener speakListener) {
        this.context = context;
        this.speakListener = speakListener;
    }

    @Override
    public void onSpeakBegin() {
        if (speakListener != null) {
            speakListener.onSpeakBegin();
        }
    }

    @Override
    public void onSpeakPaused() {
        Toast.makeText(context, "暂停播放", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSpeakResumed() {
        Toast.makeText(context, "继续播放", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        // 合成进度
        Log.e(Constants.TAG, "合成进度**********percent = " + percent + " beginPos = " + beginPos + " endPos = " + endPos + " info = " + info);
    }

    @Override
    public void onSpeakProgress(int percent, int beginPos, int endPos) {
        // 播放进度
        Log.e(Constants.TAG, "播放进度**********beginPos = " + beginPos + "  endPos = " + endPos);
    }

    @Override
    public void onCompleted(SpeechError error) {
        if (error == null) {
            if (speakListener != null) {
                speakListener.onCompleted();
            }
        } else {
            Log.e(Constants.TAG, "************SpeechError : " + error.getPlainDescription(true));
        }
    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        // 若使用本地能力，会话id为null
        //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
        //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
        //		Log.d(TAG, "session id =" + sid);
        //	}

        //当设置SpeechConstant.TTS_DATA_NOTIFY为1时，抛出buf数据
			/*if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
						byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
						Log.e("MscSpeechLog", "buf is =" + buf);
					}*/

    }


    public interface SpeakListener {
        void onSpeakBegin();

        void onCompleted();
    }
}
