package com.test.kenny.bluetoothtest;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

public class ConnectThread extends Thread {
    private final BluetoothSocket socket;
    private final BluetoothDevice device;
    private final BluetoothAdapter bluetoothAdapter;

    public ConnectThread(BluetoothDevice device, BluetoothAdapter adapter) {
        bluetoothAdapter = adapter;

        BluetoothSocket temp = null;
        this.device = device;

        try {
            // Get BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID == app's UUID
            temp = device.createRfcommSocketToServiceRecord(MainActivity.MY_UUID);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        socket = temp;
    }

    public void run() {
        // Cancel discovery since it slows down the connection
        bluetoothAdapter.cancelDiscovery();

        try {
            // Connect to remote device through socket. Blocks until
            // it succeeds or throws an exception
            socket.connect();
        }
        catch (IOException e) {
            try {
                socket.close();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }

        manageConnectedSocket(socket);
    }

    public void cancel() {
        try {
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
