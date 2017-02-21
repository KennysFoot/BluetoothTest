package com.test.kenny.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class AcceptThread extends Thread {

    public static final String APP_NAME = "BLUETOOTH TEST";

    private final BluetoothServerSocket serverSocket;

    public AcceptThread(BluetoothAdapter bluetoothAdapter) {
        BluetoothServerSocket temp = null;
        try {
            temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MainActivity.MY_UUID);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket = temp;
    }

    public void run() {
        BluetoothSocket socket = null;

        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                // Will return the connected socket
                // So don't have to call connect() method
                socket = serverSocket.accept();
            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            }

            if (socket != null) {
                // Connection was accepted.
                // Perform work associated with the connection in a separate thread.


                //manageConnectedSocket(socket);
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    public void cancel() {
        try {
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
