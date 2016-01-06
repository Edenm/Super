package model.dropbox.manager;

/**
 * Created by Eden on 06-Jan-16.
 */
public interface NetworkListener {

    public void onDownloadStarted();
    public void onDownloadFinished(String status);
    public void onUploadStarted();
    public void onUploadFinished(String status);

}
