package model.dropbox.manager;

/**
 * Created by Eden on 06-Jan-16.
 */

/**
 * This interface use by all activities that want to by in sync to download of data
 */
public interface NetworkListener {

    public void onDownloadStarted();
    public void onDownloadFinished(String status);
    public void onUploadStarted();
    public void onUploadFinished(String status);

}
