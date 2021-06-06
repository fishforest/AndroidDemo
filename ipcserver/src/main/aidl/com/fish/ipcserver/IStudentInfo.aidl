// IStudentInfo.aidl
package com.fish.ipcserver;

import com.fish.ipcserver.Student;
import com.fish.ipcserver.RemoteCallback;

interface IStudentInfo {
    //主动获取
    Student getStudentInfo();
    //注册回调
    oneway void register(in RemoteCallback callback);
}