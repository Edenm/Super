package model.dropbox.manager;

/**
 * Created by Eden on 06-Jan-16.
 */

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.FileReader;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Map;

        import com.dropbox.client2.DropboxAPI;
        import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
        import com.dropbox.client2.DropboxAPI.Entry;
        import com.dropbox.client2.android.AndroidAuthSession;
        import com.dropbox.client2.exception.DropboxException;
        import com.dropbox.client2.exception.DropboxFileSizeException;
        import com.dropbox.client2.exception.DropboxIOException;
        import com.dropbox.client2.exception.DropboxParseException;
        import com.dropbox.client2.exception.DropboxPartialFileException;
        import com.dropbox.client2.exception.DropboxServerException;
        import com.dropbox.client2.exception.DropboxUnlinkedException;
        import com.dropbox.client2.session.AppKeyPair;

        import android.os.Environment;

public class NetworkManager {

    private static final String APP_KEY = "vqh379j1v54b5n9";
    private static final String APP_SECRET = "vqh379j1v54b5n9";;
    private DropboxAPI<AndroidAuthSession> mApi;

    private String appDirName = Environment.getExternalStorageDirectory() + "/dbSuperZol";
    private File mDir = new File(appDirName);
    private String appDirNameOnDropbox = "/SuperZolData/";
    private File resListFile;
    private Map<String, String> mFileNames = new HashMap<String, String>();
    private HashMap<String, String> md5LookUpTable = new HashMap<String, String>();
    private HashMap<String, String> localmd5Table = new HashMap<String, String>();

    private static NetworkManager instance = null;
    private List<NetworkListener> listeners = new ArrayList<NetworkListener>();


    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager() {
        createSession();
        loadResFile();
    }

    public Map<String, String> getmFileNames(){
        return mFileNames;
    }

    public String getAppDirName() {
        return appDirName;
    }

    public String getAppDirNameOnDropbox() {
        return appDirNameOnDropbox;
    }

    public void createSession() {

        try {
            AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
            String oauth2AccessToken = "7daJGIid52AAAAAAAAAABkdDFJucYa2ESqX5H0gPsDYt53qUGvBVAf_01OIhYMwr";
            AndroidAuthSession session = new AndroidAuthSession(appKeyPair, oauth2AccessToken);
            // We create a new AuthSession so that we can use the Dropbox API.
            mApi = new DropboxAPI<AndroidAuthSession>(session);

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public void register(NetworkListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unregister(NetworkListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void notifyAllUploadsStarted() {
        for (NetworkListener listener : listeners) {
            listener.onUploadStarted();
        }
    }

    public void notifyAllUploadsFinished(String status) {
        for (NetworkListener listener : listeners) {
            listener.onUploadFinished(status);
        }
    }



    public void notifyAllDownloadsStarted() {
        for (NetworkListener listener : listeners) {
            listener.onDownloadStarted();
        }
    }

    public void notifyAllDownloadsFinished(String status) {
        for (NetworkListener listener : listeners) {
            listener.onDownloadFinished(status);
        }
    }


    public File getAppDir() {
        // create app dir on local storage
        if (!mDir.exists()) {
            mDir.mkdirs();
        }
        return mDir;

    }

    /**
     * Here we show getting metadata for a directory and downloading a file in a
     * background thread, trying to show typical exception handling and flow of
     * control for an app that downloads a file from Dropbox.
     */
    public void downloadResources() {

        Thread downloadThread = new Thread(new Runnable() {

            @Override
            public void run() {

                notifyAllDownloadsStarted();

                String mErrorMsg = "All data is up to date";

                try {

                    //

                    // Get the metadata for a directory
					/*
					 Returns the metadata for a file, or for a directory and (optionally) its immediate children.
					Which would return an Entry containing the metadata for the file /testing.txt in the user's Dropbox.
					Parameters:
					path - the Dropbox path to the file or directory for which to get metadata.
					fileLimit - the maximum number of children to return for a directory. Default is 25,000 if you pass in 0 or less. If there are too many entries to return, you will get a 406 DropboxServerException. Pass in 1 if getting metadata for a file.
					hash - if you previously got metadata for a directory and have it stored, pass in the returned hash. If the directory has not changed since you got the hash, a 304 DropboxServerException will be thrown. Pass in null for files or unknown directories.
					list - if true, returns metadata for a directory's immediate children, or just the directory entry itself if false. Ignored for files.
					rev - optionally gets metadata for a file at a prior rev (does not apply to folders). Use null for the latest metadata.
					Returns:
					a metadata DropboxAPI.Entry.
					Throws:
					DropboxUnlinkedException - if you have not set an access token pair on the session, or if the user has revoked access.
					DropboxServerException - if the server responds with an error code. See the constants in DropboxServerException for the meaning of each error code. The most common error codes you can expect from this call are 304 (contents haven't changed based on the hash), 404 (path not found or unknown rev for path), and 406 (too many entries to return).
					DropboxIOException - if any network-related error occurs.
					DropboxException - for any other unknown errors. This is also a superclass of all other Dropbox exceptions, so you may want to only catch this exception which signals that some kind of error occurred.
					 */
                    Entry dirent = mApi.metadata(appDirNameOnDropbox, 1000, null, true, null);

                    if (!dirent.isDir || dirent.contents == null) {
                        // It's not a directory, or there's nothing in it
                        mErrorMsg = "File or empty directory";
                        return;
                    }

                    // Make a list of everything in dropbox
                    ArrayList<Entry> data = new ArrayList<Entry>();
                    for (Entry ent : dirent.contents) {
                        // Add it to the list of we can choose from
                        data.add(ent);
                    }

                    if (data.size() == 0) {
                        // No files in that directory
                        mErrorMsg = "No files in that directory";
                        return;
                    }

                    // Now pick files to download
                    for (int i = 0; i < data.size(); i++) {
                        Entry ent = data.get(i);
                        String path = ent.path;
                        String rev = ent.rev;
                        addMd5(path, rev);
                        getUrl(path);

                    }

                    saveResFile();

                } catch (DropboxUnlinkedException e) {
                    // The AuthSession wasn't properly authenticated or user
                    // unlinked.
                } catch (DropboxPartialFileException e) {
                    // We canceled the operation
                    mErrorMsg = "Download canceled";
                } catch (DropboxServerException e) {
                    // Server-side exception. These are examples of what could
                    // happen,
                    // but we don't do anything special with them here.
                    if (e.error == DropboxServerException._304_NOT_MODIFIED) {
                        // won't happen since we don't pass in revision with
                        // metadata
                    } else if (e.error == DropboxServerException._401_UNAUTHORIZED) {
                        // Unauthorized, so we should unlink them. You may want
                        // to
                        // automatically log the user out in this case.
                    } else if (e.error == DropboxServerException._403_FORBIDDEN) {
                        // Not allowed to access this
                    } else if (e.error == DropboxServerException._404_NOT_FOUND) {
                        // path not found (or if it was the thumbnail, can't be
                        // thumbnailed)
                    } else if (e.error == DropboxServerException._406_NOT_ACCEPTABLE) {
                        // too many entries to return
                    } else if (e.error == DropboxServerException._415_UNSUPPORTED_MEDIA) {
                        // can't be thumbnailed
                    } else if (e.error == DropboxServerException._507_INSUFFICIENT_STORAGE) {
                        // user is over quota
                    } else {
                        // Something else
                    }
                    // This gets the Dropbox error, translated into the user's
                    // language
                    mErrorMsg = e.body.userError;
                    if (mErrorMsg == null) {
                        mErrorMsg = e.body.error;
                    }
                } catch (DropboxIOException e) {
                    // Happens all the time, probably want to retry
                    // automatically.
                    mErrorMsg = "Network error";
                } catch (DropboxParseException e) {
                    // Probably due to Dropbox server restarting, should retry
                    mErrorMsg = "Dropbox error";
                } catch (DropboxException e) {
                    // Unknown error
                    mErrorMsg = "Unknown error";

                } catch (Throwable e) {
                    // file not found
                    e.printStackTrace();
                    mErrorMsg = "Unknown error";
                }

                notifyAllDownloadsFinished(mErrorMsg);
            }
        });

        downloadThread.start();
    }

    public static void releaseInstance() {
        if (instance != null) {
            instance.saveResFile();
            instance = null;
        }
    }

    public void loadResFile() {
        String resListFileName = "res_list";

        resListFile = new File(getAppDir(), resListFileName);

        BufferedReader br = null;

        if (resListFile.exists()) {
            try {
                br = new BufferedReader(new FileReader(resListFile));
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] vals = line.split("\t");
                    mFileNames.put(vals[0], vals[1]);
                    localmd5Table.put(vals[0], vals[2]);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {

                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                resListFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveResFile() {
        if (mDir != null) {
            File resListFile = new File(mDir, "res_list");
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(resListFile, false));

                for (Iterator<String> iterator = mFileNames.keySet().iterator(); iterator.hasNext();) {
                    String url = (String) iterator.next();
                    String file = mFileNames.get(url);
                    bw.write(url + "\t" + file + "\t" + getLocalMd5(url) + "\n");
                }
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void addMd5(String fullpathFileName, String md5) {
        String uri = fullpathFileName;
        md5LookUpTable.put(uri, md5);

    }

    protected boolean isChanged(String nameOfFileOnServer) {
        boolean result = true;
        if (localmd5Table.containsKey(nameOfFileOnServer)) {
            String loacalmd5 = localmd5Table.get(nameOfFileOnServer);
            String remotemd5 = md5LookUpTable.get(nameOfFileOnServer);
            if (loacalmd5.equals(remotemd5)) {
                result = false;
            }
        }
        return result;
    }

    private String translateUrl(String pathUrl) {

        if (pathUrl == null) {
            return "";
        }

        int index = pathUrl.lastIndexOf("/");
        if (index == -1) {
            return pathUrl;
        }
        return pathUrl.substring(index + 1, pathUrl.length());
    }

    public byte[] getUrl(String url) {

        byte[] bytes = new byte[0];

        try {

            String fileName = translateUrl(url);

            if (url != null && mFileNames.containsKey(url) && !isChanged(url)) {
                return getLocalCopy(url);

            } else {

                File appDir = getAppDir();
                OutputStream outputStream;

                outputStream = new FileOutputStream(new File(appDir, fileName));

				/*
				 Downloads a file from Dropbox, copying it to the output stream. Returns the DropboxAPI.DropboxFileInfo for the file.


				which would retrieve the file /testing.txt in Dropbox and save it to the file /path/to/new/file.txt on the local filesystem
				Parameters:
				path - the Dropbox path to the file.
				rev - the revision (from the file's metadata) of the file to download, or null to get the latest version.
				os - the OutputStream to write the file to.
				listener - an optional ProgressListener to receive progress updates as the file downloads, or null.
				Returns:
				the DropboxAPI.DropboxFileInfo for the downloaded file.
				Throws:
				DropboxUnlinkedException - if you have not set an access token pair on the session, or if the user has revoked access.
				DropboxServerException - if the server responds with an error code. See the constants in DropboxServerException for the meaning of each error code. The most common error codes you can expect from this call are 404 (path not found) and 400 (bad rev).
				DropboxPartialFileException - if a network error occurs during the download.
				DropboxIOException - for some network-related errors.
				DropboxException - for any other unknown errors. This is also a superclass of all other Dropbox exceptions, so you may want to only catch this exception which signals that some kind of error occurred.
				 */
                DropboxFileInfo resInfo = mApi.getFile(url, null, outputStream, null);

                if (resInfo.getFileSize() > 0) {
                    // add md5
                    mFileNames.put(url, fileName);

                    String value = md5LookUpTable.get(url);

                    localmd5Table.put(url, value);
                    bytes = getLocalCopy(url);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DropboxException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return bytes;

    }

    public boolean writeLocalCopy(File file, byte[] bytes) {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);
            output.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;

    }

    public byte[] getLocalCopy(String url) {

        if (url == null)
            return new byte[0];

        String localUrl = mFileNames.get(url);
        if (localUrl == null) {
            return new byte[0];
        }

        File f = new File(getAppDir(), localUrl);
        ByteArrayOutputStream stream = new ByteArrayOutputStream(4096);

        InputStream in = null;
        try {
            in = new FileInputStream(f);
            byte[] buffer = new byte[4096 * 2];
            int n = -1;
            while ((n = in.read(buffer)) != -1) {
                if (n > 0) {
                    stream.write(buffer, 0, n);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stream.toByteArray();
    }

    private String getLocalMd5(String key) {
        return localmd5Table.get(key);
    }


    public void uploadRes(final String localFilePath) {


        Thread downloadThread = new Thread(new Runnable() {

            @Override
            public void run() {

                String mErrorMsg = "OK";
                try{
                    notifyAllUploadsStarted();

                    File file = new File(localFilePath);
                    FileInputStream inputStream = new FileInputStream(file);
                    /**
                     public DropboxAPI.Entry putFileOverwrite(java.lang.String path,
                     java.io.InputStream is,
                     long length,
                     ProgressListener listener)
                     throws DropboxException
                     Uploads a file to Dropbox. The upload will overwrite any existing version of the file. Use putFileRequest() if you want to be able to cancel the upload.
                     If you expect the user to be able to edit a file remotely and locally, then conflicts may arise and you won't want to use this call: see putFileRequest instead.
                     Parameters:
                     path - the full Dropbox path where to put the file, including directories and filename.
                     is - the InputStream from which to upload.
                     length - the amount of bytes to read from the InputStream.
                     listener - an optional ProgressListener to receive upload progress updates, or null.
                     Returns:
                     a metadata DropboxAPI.Entry representing the uploaded file.
                     Throws:
                     java.lang.IllegalArgumentException - if the file does not exist.
                     DropboxUnlinkedException - if you have not set an access token pair on the session, or if the user has revoked access.
                     DropboxFileSizeException - if the file is bigger than the maximum allowed by the API. See DropboxAPI.MAX_UPLOAD_SIZE.
                     DropboxServerException - if the server responds with an error code. See the constants in DropboxServerException for the meaning of each error code. The most common error codes you can expect from this call are 404 (path to upload not found), 507 (user over quota), and 400 (unexpected parent rev).
                     DropboxIOException - if any network-related error occurs.
                     DropboxException - for any other unknown errors. This is also a superclass of all other Dropbox exceptions, so you may want to only catch this exception which signals that some kind of error occurred.
                     */

                    String uploadToPath = appDirNameOnDropbox + translateUrl(localFilePath);

                    mApi.putFileOverwrite(uploadToPath, inputStream, file.length(), null);

                } catch (DropboxUnlinkedException e) {
                    // This session wasn't authenticated properly or user unlinked
                    mErrorMsg = "This app wasn't authenticated properly.";
                } catch (DropboxFileSizeException e) {
                    // File size too big to upload via the API
                    mErrorMsg = "This file is too big to upload";
                } catch (DropboxPartialFileException e) {
                    // We canceled the operation
                    mErrorMsg = "Upload canceled";
                } catch (DropboxServerException e) {
                    // Server-side exception.  These are examples of what could happen,
                    // but we don't do anything special with them here.
                    if (e.error == DropboxServerException._401_UNAUTHORIZED) {
                        // Unauthorized, so we should unlink them.  You may want to
                        // automatically log the user out in this case.
                    } else if (e.error == DropboxServerException._403_FORBIDDEN) {
                        // Not allowed to access this
                    } else if (e.error == DropboxServerException._404_NOT_FOUND) {
                        // path not found (or if it was the thumbnail, can't be
                        // thumbnailed)
                    } else if (e.error == DropboxServerException._507_INSUFFICIENT_STORAGE) {
                        // user is over quota
                    } else {
                        // Something else
                    }
                    // This gets the Dropbox error, translated into the user's language
                    mErrorMsg = e.body.userError;
                    if (mErrorMsg == null) {
                        mErrorMsg = e.body.error;
                    }
                } catch (DropboxIOException e) {
                    // Happens all the time, probably want to retry automatically.
                    mErrorMsg = "Network error";
                } catch (DropboxParseException e) {
                    // Probably due to Dropbox server restarting, should retry
                    mErrorMsg = "Dropbox error";
                } catch (DropboxException e) {
                    // Unknown error
                    mErrorMsg = "Unknown error";
                } catch (FileNotFoundException e) {
                    mErrorMsg = "FileNotFoundException";
                }

                catch(Throwable t){
                    t.printStackTrace();
                }
                notifyAllUploadsFinished(mErrorMsg);
            }
        });

        downloadThread.start();
    }

}
