package model.dropbox;

/**
 * Created by Eden on 06-Jan-16.
 */

import java.io.File;

import ViewLogic.slidingmenu.R;
import model.dropbox.manager.*;
import view.MainActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/** This class is the connector activity that downloading all files from dropbox after the login
 * This class show rendering on the screen until finish downloading */
public class DBSuper extends Activity implements NetworkListener {
    private static final String TAG = "DBAuper";
    private Context ctx = null;
    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Basic Android widgets
        setContentView(R.layout.dropbox_activity);

        ctx = this;

        NetworkManager.getInstance().register(this);

        NetworkManager.getInstance().downloadResources();

    }

    @Override
    public void onDownloadStarted() {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog = new ProgressDialog(ctx);
                    String downloadmessage = "downloading";
                    dialog.setMessage(downloadmessage);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDownloadFinished(String status) {

        final String state = status;

        if (dialog != null) {
            dialog.dismiss();
        }

        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ctx, state, Toast.LENGTH_LONG).show();
            }
        });

        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
        //uploadFile();


    }

    private void uploadFile() {

        // create testFile
        String localFilePath=NetworkManager.getInstance().getAppDirName()+"/"+"test.txt";
        File localfile = new File(localFilePath);
        String content = "abc2";
        NetworkManager.getInstance().writeLocalCopy(localfile, content.getBytes());

        // try to upload
        NetworkManager.getInstance().uploadRes(localFilePath);

    }

    @Override
    public void onUploadStarted() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUploadFinished(String status) {
        final String state = status;

        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ctx, "uploading " + state, Toast.LENGTH_LONG).show();
            }
        });

    }

}
