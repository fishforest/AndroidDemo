package com.fish.ipcserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class StudentService extends Service {

    private Student student;
    private RemoteCallback remoteCallback;
    private MyStudent myStudent;

    @Override
    public void onCreate() {
        super.onCreate();
        student = new Student();
        student.setAge(19);
        student.setName("小明");
        myStudent = new MyStudent();
    }

    class MyStudent extends IStudentInfo.Stub {
        @Override
        public Student getStudentInfo() throws RemoteException {
            return student;
        }

        @Override
        public void register(RemoteCallback callback) throws RemoteException {
            //客户端注册的回调实例保存到成员变量 remoteCallback
            remoteCallback = callback;
        }

        public void changeScore() {
            //学生成绩发生改变
            student.setScore((float)(Math.random() * 100));
            try {
                if (remoteCallback != null)
                    //调用回调实例方法，将变化后的学生信息传递给客户端
                    remoteCallback.onCallback(student);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //将Stub 返回给客户端
        Toast.makeText(StudentService.this, "收到客户端连接请求", Toast.LENGTH_SHORT).show();
        return myStudent.asBinder();
    }
}
