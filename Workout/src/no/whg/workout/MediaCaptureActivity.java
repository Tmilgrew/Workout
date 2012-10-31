package no.whg.workout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

public class MediaCaptureActivity extends Activity {
	private static final int IMAGE_REQUEST_CODE = 100;
	private static final int VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private Uri fileUri;
	
	public void onCreate(Bundle savedInstanceState, Intent intent) {
        super.onCreate(savedInstanceState);
        
        int i = intent.getIntExtra("MEDIA_TYPE", 0);
        captureMedia(intent, i);
	}
	
	private void captureMedia(Intent intent, int i) {
		if (i == MEDIA_TYPE_IMAGE){
			captureImage();
		} else if (i == MEDIA_TYPE_VIDEO) {
			captureVideo(intent);
		} else {
			
		}
	}
	
	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getMediaFileUri(MEDIA_TYPE_IMAGE, "SL_IMG_");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(intent, IMAGE_REQUEST_CODE);
	}
	
	private void captureVideo(Intent i) {
		String lift = "SL_VID_";
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		//Exercise[] exercises = MainActivity.SLCalc.getABworkouts();
		int etype = i.getIntExtra("VIDEO_TYPE", 3);
		if (etype <= 3) {
			//lift += exercises[etype].getName();
			fileUri = getMediaFileUri(MEDIA_TYPE_VIDEO, lift);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	
			startActivityForResult(intent, IMAGE_REQUEST_CODE);
		} else {
			// error
			finish();
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// saved
			} else if (resultCode == RESULT_CANCELED) {
				// cancelled
			} else {
				// something went wrong
			}
		} else if (requestCode == VIDEO_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// saved
			} else if (resultCode == RESULT_CANCELED) {
				// cancelled
			} else {
				// something went wrong
			}
		}
		finish();
	}

	private Uri getMediaFileUri(int type, String lift) {
		return Uri.fromFile(getMediaFile(type, lift));
	}

	private File getMediaFile(int type, String lift) {
		File dir;
		if (type == MEDIA_TYPE_IMAGE) {
			dir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"StrongLifts"); // set destination folder
		} else {
			dir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
					"StrongLifts"); // set destination folder
		}
		if (!dir.exists()) { // if dir does not exist
			if (!dir.mkdirs()) { // create dir
				// something went horribly wrong
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			// mediaFile = new File(dir.getPath() + File.separator + lift +
			// timeStamp + ".jpg");
			mediaFile = new File(dir.getPath() + File.separator + lift
					+ timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			// mediaFile = new File(dir.getPath() + File.separator + lift +
			// timeStamp + ".mp4");
			mediaFile = new File(dir.getPath() + File.separator + lift + ".mp4");
		} else {
			return null; // something went wrong
		}
		return mediaFile;
	}
}