package com.example.administrator.myapplication22;

/**
 * Created by Administrator on 2017/5/11.
 */

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
