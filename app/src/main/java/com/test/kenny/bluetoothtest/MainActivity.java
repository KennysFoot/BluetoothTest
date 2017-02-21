package com.test.kenny.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String UUID_STRING = "92051e52-0366-4a88-a014-abb6056d4095";
    public static final UUID MY_UUID = UUID.fromString(UUID_STRING);
    private static final int REQUEST_ENABLE_BT = 1;

    private TextView tvPaired;
    private TextView tvDiscovered;
    private Button bStart;
    private Button bEnableDisc;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();

                // Add device to list of discovered devices
                String text = tvDiscovered.getText().toString() + "\n";
                text += deviceName + ", " + deviceHardwareAddress;
                tvDiscovered.setText(text);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPaired = (TextView) findViewById(R.id.tvPaired);
        tvDiscovered = (TextView) findViewById(R.id.tvDiscovered);
        bStart = (Button) findViewById(R.id.bStart);
        bEnableDisc = (Button) findViewById(R.id.bEnableDisc);
        bStart.setOnClickListener(this);
        bEnableDisc.setOnClickListener(this);

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    // Yay
                }
                else {
                    // Nooooooooo
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bStart) {
            // Should check that it is not null
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            enableBluetooth(mBluetoothAdapter);

            // Before starting device discovery, query the set of paired devices to see if the desired device is already known
            listPairedDevices(mBluetoothAdapter);

            // Discovery - scanning for Bluetooth-enabled devices
            discoverDevices(mBluetoothAdapter);
        }
        else if (view.getId() == R.id.bEnableDisc) {
            enableDiscoverability();
        }
    }

    private void enableBluetooth(BluetoothAdapter mBluetoothAdapter) {
        if (mBluetoothAdapter == null) {
            Toast toast = Toast.makeText(this, "Bluetooth not supported on this device.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        else {
            Toast toast = Toast.makeText(this, "Bluetooth is supported.", Toast.LENGTH_LONG);
            toast.show();
        }

        // Enable Bluetooth
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            // REQUEST_ENABLE_BT Must be > 0. System passes this back in onActivityResult() as the
            // requestCode param. If Bluetooth was enabled, the resultCode is RESULT_OK.
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void listPairedDevices(BluetoothAdapter bluetoothAdapter) {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // Get the name and address of each paired device
            String text = "Paired Devices:\n";
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                text += deviceName + ", " + deviceHardwareAddress + "\n";
            }
            tvPaired.setText(text);
        }
        else {
            tvPaired.setText("No paired devices.");
        }
    }

    private void discoverDevices(BluetoothAdapter bluetoothAdapter) {
        bluetoothAdapter.startDiscovery();
        Toast.makeText(this, "Starting Discovery...", Toast.LENGTH_SHORT).show();
    }

    private void enableDiscoverability() {
        // Sets the device to be discoverable for 2 minutes (120 seconds)
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        startActivity(discoverableIntent);
    }
}
