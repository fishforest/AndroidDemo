// RemoteCallback.aidl
package com.fish.ipcserver;

import com.fish.ipcserver.Student;
interface RemoteCallback {
    //回调
    oneway void onCallback(in Student student);
}