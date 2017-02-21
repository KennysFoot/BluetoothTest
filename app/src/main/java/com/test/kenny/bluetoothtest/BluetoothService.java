package com.test.kenny.bluetoothtest;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Handler;

public class BluetoothService {
    private static final String TAG = "TAG";
    private Handler handler;

    // Constants used when transmitting messages between service and UI
    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WIRTE = 1;
        public static final int MESSAGE_TOAST = 2;
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final InputStream inStream;
        private final OutputStream outStream;
        private byte[] buffer;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;

            // Get input and output streams. Use temp objects since members are final.
            try {
                tempIn = socket.getInputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            try {
                tempOut = socket.getOutputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            inStream = tempIn;
            outStream = tempOut;
        }

        public void run() {

        }

        public void read(byte[] bytes) {

        }

        public void write(byte[] bytes) {

        }

        public void cancel() {

        }
    }

}
