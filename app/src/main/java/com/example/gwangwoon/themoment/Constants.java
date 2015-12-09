package com.example.gwangwoon.themoment;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Gwangwoon on 2015-12-03.
 */
public interface Constants extends BaseColumns{
    public static final String TABLE_NAME = "events";

    //Events 데이터베이스의 행들
    public static final String TIME = "time";
    public static final String TITLE = "title";
    public static final String MEMO = "memo";
    public static final String PHOTO ="photo";
    public static final String TYPE ="type";
    public static final String DIRECTORY = "directory";
}
