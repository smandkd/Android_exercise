package org.techtowm.classicblt_tabs_fragment;

import static android.app.Activity.RESULT_OK;

import static org.techtowm.classicblt_tabs_fragment.R.layout.*;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class Fragment1 extends Fragment {

    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // "random" unique identifier

    // defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1;
    // used to identify adding bluetooth names
    public final static int MESSAGE_READ = 2;
    // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3;
    // used in bluetooth handler to identify to identify message status

    // GUI Components
    private TextView mBluetoothStatus;
    private TextView ex;
    private Button mScanBtn;
    private Button moffBtn;
    private Button mListPairedDeviceBtn;
    private Button mDiscoverBtn;
    private ListView mDeviceListView;

    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private ArrayAdapter<String> mBTArrayAdapter;

    private Handler mHandler; // Our main handler that will receive callback notifications
    private ConnectThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBTArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

         if (mBTArrayAdapter == null) {
            // Device does not support bluetooth
            mBluetoothStatus.setText("Status : Bluetooth not found");

        } else {
            mScanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bluetoothOn();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(fragment1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mBluetoothStatus = (TextView) v.findViewById(R.id.bluetooth_status);
        mScanBtn = (Button)v.findViewById(R.id.scan);
        moffBtn =(Button)v.findViewById(R.id.off);
        mListPairedDeviceBtn = (Button)v.findViewById(R.id.paired_btn);
        mDeviceListView = (ListView) v.findViewById(R.id.devices_list_view);
        mDiscoverBtn = (Button)v.findViewById(R.id.discover);

    }

    private void bluetoothOn() {
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            mBluetoothStatus.setText("Bluetooth enabled");
            Toast.makeText(this.getContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this.getContext(), "Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

}