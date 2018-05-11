package fm.jiecao.jcvideoplayer_lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nathen
 * On 2016/04/18 16:15
 */
public class JCVideoPlayerStandard extends JCVideoPlayer {

    public ImageView backButton;
    public ProgressBar bottomProgressBar, loadingProgressBar;
    public TextView titleTextView;
    public SimpleDraweeView thumbImageView;
    public ImageView coverImageView;

    protected static Timer DISSMISS_CONTROL_VIEW_TIMER;
    protected DismissControlViewTimerTask mDismissControlViewTimerTask;

    protected static JCBuriedPointStandard JC_BURIED_POINT_STANDARD;

    public JCVideoPlayerStandard(Context context) {
        super(context);
    }

    public JCVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        bottomProgressBar = (ProgressBar) findViewById(R.id.bottom_progressbar);
        titleTextView = (TextView) findViewById(R.id.title);
        backButton = (ImageView) findViewById(R.id.back);
        backButton.setVisibility(View.INVISIBLE);
        thumbImageView = (SimpleDraweeView) findViewById(R.id.thumb);
        coverImageView = (ImageView) findViewById(R.id.cover);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading);

        thumbImageView.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }

    @Override
    public boolean setUp(String url, Object... objects) {
        if (objects.length == 0)
            return false;
        if (super.setUp(url, objects)) {
            titleTextView.setText(objects[0].toString());
            if (mIfCurrentIsFullscreen) {
                fullscreenButton.setImageResource(R.drawable.jc_shrink);
                backButton.setVisibility(View.VISIBLE);
            } else {
                fullscreenButton.setImageResource(R.drawable.jc_enlarge);
                backButton.setVisibility(View.GONE);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.jc_layout_standard;
    }

    @Override
    protected void setStateAndUi(int state) {
        super.setStateAndUi(state);
        switch (mCurrentState) {
            case CURRENT_STATE_NORMAL:
                changeUiToNormal();
                break;
            case CURRENT_STATE_PREPAREING:
                changeUiToPrepareingShow();
                startDismissControlViewTimer();
                break;
            case CURRENT_STATE_PLAYING:
                changeUiToPlayingShow();
                startDismissControlViewTimer();
                break;
            case CURRENT_STATE_PAUSE:
                changeUiToPauseShow();
                cancelDismissControlViewTimer();
                break;
            case CURRENT_STATE_ERROR:
                changeUiToError();
                break;
            case CURRENT_STATE_AUTO_COMPLETE:
                changeUiToCompleteShow();
                cancelDismissControlViewTimer();
                bottomProgressBar.setProgress(100);
                break;
            case CURRENT_STATE_PLAYING_BUFFERING_START:
                changeUiToPlayingBufferingShow();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id == R.id.surface_container) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    startDismissControlViewTimer();
                    if (mChangePosition) {
                        int duration = getDuration();
                        int progress = mSeekTimePosition * 100 / (duration == 0 ? 1 : duration);
                        bottomProgressBar.setProgress(progress);
                    }
                    if (!mChangePosition && !mChangeVolume) {
                        onClickUiToggle();
                    }
                    break;
            }
        } else if (id == R.id.progress) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cancelDismissControlViewTimer();
                    break;
                case MotionEvent.ACTION_UP:
                    startDismissControlViewTimer();
                    break;
            }
        }
        return super.onTouch(v, event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.thumb) {
            if (TextUtils.isEmpty(mUrl)) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                return;
            }
            if (mCurrentState == CURRENT_STATE_NORMAL) {
                if (!JCUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(getResources().getString(R.string.tips_not_wifi));
                    builder.setPositiveButton(getResources().getString(R.string.tips_not_wifi_confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startPlayLocic();
                            WIFI_TIP_DIALOG_SHOWED = true;
                        }
                    });
                    builder.setNegativeButton(getResources().getString(R.string.tips_not_wifi_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    return;
                }
                startPlayLocic();
            } else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
                onClickUiToggle();
            }
        } else if (i == R.id.surface_container) {
            if (JC_BURIED_POINT_STANDARD != null && JCMediaManager.instance().listener == this) {
                if (mIfCurrentIsFullscreen) {
                    JC_BURIED_POINT_STANDARD.onClickBlankFullscreen(mUrl, mObjects);
                } else {
                    JC_BURIED_POINT_STANDARD.onClickBlank(mUrl, mObjects);
                }
            }
            startDismissControlViewTimer();
        } else if (i == R.id.back) {
            backFullscreen();
        }
    }

    private void startPlayLocic() {
        if (JC_BURIED_POINT_STANDARD != null) {
            JC_BURIED_POINT_STANDARD.onClickStartThumb(mUrl, mObjects);
        }
        prepareVideo();
        startDismissControlViewTimer();
    }

    private void onClickUiToggle() {
        if (mCurrentState == CURRENT_STATE_PREPAREING) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPrepareingClear();
            } else {
                changeUiToPrepareingShow();
            }
        } else if (mCurrentState == CURRENT_STATE_PLAYING) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPlayingClear();
            } else {
                changeUiToPlayingShow();
            }
        } else if (mCurrentState == CURRENT_STATE_PAUSE) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPauseClear();
            } else {
                changeUiToPauseShow();
            }
        } else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToCompleteClear();
            } else {
                changeUiToCompleteShow();
            }
        } else if (mCurrentState == CURRENT_STATE_PLAYING_BUFFERING_START) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                changeUiToPlayingBufferingClear();
            } else {
                changeUiToPlayingBufferingShow();
            }
        }
    }

    @Override
    protected void setProgressAndTime(int progress, int secProgress, int currentTime, int totalTime) {
        super.setProgressAndTime(progress, secProgress, currentTime, totalTime);
        if (progress != 0) bottomProgressBar.setProgress(progress);
        if (secProgress != 0) bottomProgressBar.setSecondaryProgress(secProgress);
    }

    @Override
    protected void resetProgressAndTime() {
        super.resetProgressAndTime();
        bottomProgressBar.setProgress(0);
        bottomProgressBar.setSecondaryProgress(0);
    }

    //Unified management Ui
    private void changeUiToNormal() {
        topContainer.setVisibility(View.VISIBLE);
        bottomContainer.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        thumbImageView.setVisibility(View.VISIBLE);
        coverImageView.setVisibility(View.VISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
        updateStartImage();
    }

    private void changeUiToPrepareingShow() {
        topContainer.setVisibility(View.VISIBLE);
        bottomContainer.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
        thumbImageView.setVisibility(View.INVISIBLE);
        coverImageView.setVisibility(View.VISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
    }

    private void changeUiToPrepareingClear() {
        topContainer.setVisibility(View.INVISIBLE);
        bottomContainer.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        thumbImageView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
        coverImageView.setVisibility(View.VISIBLE);
    }

    private void changeUiToPlayingShow() {
        topContainer.setVisibility(View.VISIBLE);
        bottomContainer.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        thumbImageView.setVisibility(View.INVISIBLE);
        coverImageView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
        updateStartImage();
    }

    private void changeUiToPlayingClear() {
        changeUiToClear();
        bottomProgressBar.setVisibility(View.VISIBLE);
    }

    private void changeUiToPauseShow() {
        topContainer.setVisibility(View.VISIBLE);
        bottomContainer.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        thumbImageView.setVisibility(View.INVISIBLE);
        coverImageView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
        updateStartImage();
    }

    private void changeUiToPauseClear() {
        changeUiToClear();
        bottomProgressBar.setVisibility(View.VISIBLE);
    }

    private void changeUiToPlayingBufferingShow() {
        topContainer.setVisibility(View.VISIBLE);
        bottomContainer.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
        thumbImageView.setVisibility(View.INVISIBLE);
        coverImageView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
    }

    private void changeUiToPlayingBufferingClear() {
        topContainer.setVisibility(View.INVISIBLE);
        bottomContainer.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
        thumbImageView.setVisibility(View.INVISIBLE);
        coverImageView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(View.VISIBLE);
        updateStartImage();
    }

    private void changeUiToClear() {
        topContainer.setVisibility(View.INVISIBLE);
        bottomContainer.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        thumbImageView.setVisibility(View.INVISIBLE);
        coverImageView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
    }

    private void changeUiToCompleteShow() {
        topContainer.setVisibility(View.VISIBLE);
        bottomContainer.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        thumbImageView.setVisibility(View.VISIBLE);
        coverImageView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
        updateStartImage();
    }

    private void changeUiToCompleteClear() {
        topContainer.setVisibility(View.INVISIBLE);
        bottomContainer.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        thumbImageView.setVisibility(View.VISIBLE);
        coverImageView.setVisibility(View.INVISIBLE);
        bottomProgressBar.setVisibility(View.VISIBLE);
        updateStartImage();
    }

    private void changeUiToError() {
        topContainer.setVisibility(View.INVISIBLE);
        bottomContainer.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        thumbImageView.setVisibility(View.INVISIBLE);
        coverImageView.setVisibility(View.VISIBLE);
        bottomProgressBar.setVisibility(View.INVISIBLE);
        updateStartImage();
    }

    private void updateStartImage() {
        if (mCurrentState == CURRENT_STATE_PLAYING) {
            startButton.setImageResource(R.drawable.jc_click_pause_selector);
        } else if (mCurrentState == CURRENT_STATE_ERROR) {
            startButton.setImageResource(R.drawable.jc_click_error_selector);
        } else {
            startButton.setImageResource(R.drawable.jc_click_play_selector);
        }
    }

    protected Dialog mProgressDialog;
    protected ProgressBar mDialogProgressBar;
    protected TextView mDialogSeekTime;
    protected TextView mDialogTotalTime;
    protected ImageView mDialogIcon;

    @Override
    protected void showProgressDialog(float deltaX, String seekTime, int seekTimePosition, String totalTime, int totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
        if (mProgressDialog == null) {
            View localView = LayoutInflater.from(getContext()).inflate(fm.jiecao.jcvideoplayer_lib.R.layout.jc_progress_dialog, null);
            mDialogProgressBar = ((ProgressBar) localView.findViewById(fm.jiecao.jcvideoplayer_lib.R.id.duration_progressbar));
            mDialogSeekTime = ((TextView) localView.findViewById(fm.jiecao.jcvideoplayer_lib.R.id.tv_current));
            mDialogTotalTime = ((TextView) localView.findViewById(fm.jiecao.jcvideoplayer_lib.R.id.tv_duration));
            mDialogIcon = ((ImageView) localView.findViewById(fm.jiecao.jcvideoplayer_lib.R.id.duration_image_tip));
            mProgressDialog = new Dialog(getContext(), fm.jiecao.jcvideoplayer_lib.R.style.jc_style_dialog_progress);
            mProgressDialog.setContentView(localView);
            mProgressDialog.getWindow().addFlags(Window.FEATURE_ACTION_BAR);
            mProgressDialog.getWindow().addFlags(32);
            mProgressDialog.getWindow().addFlags(16);
            mProgressDialog.getWindow().setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = mProgressDialog.getWindow().getAttributes();
            localLayoutParams.gravity = 49;
            localLayoutParams.y = getResources().getDimensionPixelOffset(fm.jiecao.jcvideoplayer_lib.R.dimen.jc_progress_dialog_margin_top);
            mProgressDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        mDialogSeekTime.setText(seekTime);
        mDialogTotalTime.setText(" / " + totalTime);
        mDialogProgressBar.setProgress(seekTimePosition * 100 / totalTimeDuration);
        if (deltaX > 0) {
            mDialogIcon.setBackgroundResource(R.drawable.jc_forward_icon);
        } else {
            mDialogIcon.setBackgroundResource(R.drawable.jc_backward_icon);
        }

    }

    @Override
    protected void dismissProgressDialog() {
        super.dismissProgressDialog();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    protected Dialog mVolumeDialog;
    protected ProgressBar mDialogVolumeProgressBar;

    @Override
    protected void showVolumDialog(float deltaY, int volumePercent) {
        super.showVolumDialog(deltaY, volumePercent);
        if (mVolumeDialog == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.jc_volume_dialog, null);
            mDialogVolumeProgressBar = ((ProgressBar) localView.findViewById(R.id.volume_progressbar));
            mVolumeDialog = new Dialog(getContext(), R.style.jc_style_dialog_progress);
            mVolumeDialog.setContentView(localView);
            mVolumeDialog.getWindow().addFlags(8);
            mVolumeDialog.getWindow().addFlags(32);
            mVolumeDialog.getWindow().addFlags(16);
            mVolumeDialog.getWindow().setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = mVolumeDialog.getWindow().getAttributes();
            localLayoutParams.gravity = 19;
            localLayoutParams.x = getContext().getResources().getDimensionPixelOffset(R.dimen.jc_volume_dialog_margin_left);
            mVolumeDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }

        mDialogVolumeProgressBar.setProgress(volumePercent);
    }

    @Override
    protected void dismissVolumDialog() {
        super.dismissVolumDialog();
        if (mVolumeDialog != null) {
            mVolumeDialog.dismiss();
        }
    }

    private void startDismissControlViewTimer() {
        cancelDismissControlViewTimer();
        DISSMISS_CONTROL_VIEW_TIMER = new Timer();
        mDismissControlViewTimerTask = new DismissControlViewTimerTask();
        DISSMISS_CONTROL_VIEW_TIMER.schedule(mDismissControlViewTimerTask, 2500);
    }

    private void cancelDismissControlViewTimer() {
        if (DISSMISS_CONTROL_VIEW_TIMER != null) {
            DISSMISS_CONTROL_VIEW_TIMER.cancel();
        }
        if (mDismissControlViewTimerTask != null) {
            mDismissControlViewTimerTask.cancel();
        }

    }

    protected class DismissControlViewTimerTask extends TimerTask {

        @Override
        public void run() {
            if (mCurrentState != CURRENT_STATE_NORMAL
                    && mCurrentState != CURRENT_STATE_ERROR
                    && mCurrentState != CURRENT_STATE_AUTO_COMPLETE) {
                if (getContext() != null && getContext() instanceof Activity) {
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bottomContainer.setVisibility(View.INVISIBLE);
                            topContainer.setVisibility(View.INVISIBLE);
                            bottomProgressBar.setVisibility(View.VISIBLE);
                            startButton.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        }
    }

    public static void setJcBuriedPointStandard(JCBuriedPointStandard jcBuriedPointStandard) {
        JC_BURIED_POINT_STANDARD = jcBuriedPointStandard;
        JCVideoPlayer.setJcBuriedPoint(jcBuriedPointStandard);
    }

//  @Override
//  public void onCompletion() {
//    super.onCompletion();
//    cancelDismissControlViewTimer();
//  }

}
