package org.techtowm.classicblt;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // "random" unique identifier

    // defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1;
    // used to identify adding bluetooth names
    public final static int MESSAGE_READ = 2;
    // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3;
    // used in bluetooth handler to identify message status

    // GUI Components
    private TextView mBluetoothStatus;
    private Button mScanBtn;
    private Button moffBtn;
    private Button mListPairedDeviceBtn;
    private Button mDiscoverBtn;
    private ListView mDeviceListView;
    private TextView Data;

    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private ArrayAdapter<String> mBTArrayAdapter;

    private Handler mHandler; // Our main handler that will receive callback notifications
    private ConnectThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothStatus = findViewById(R.id.bluetooth_status);
        mScanBtn = findViewById(R.id.scan);
        moffBtn = findViewById(R.id.off);
        mListPairedDeviceBtn = findViewById(R.id.paired_btn);
        mDeviceListView = findViewById(R.id.devices_list_view);
        mDiscoverBtn = findViewById(R.id.discover);
        Data = findViewById(R.id.data);

        mBTArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

        mDeviceListView.setAdapter(mBTArrayAdapter);
        mDeviceListView.setOnItemClickListener(mDeviceClickListener);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        mHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(@NonNull Message msg) {
              /*  if (msg.what == MESSAGE_READ) {
                    String readMessage = null;

                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
*/
                if (msg.what == CONNECTING_STATUS) {
                    if (msg.arg1 == 1)
                        mBluetoothStatus.setText("Connected to Device : " + msg.obj);
                    else
                        mBluetoothStatus.setText("Connection failed");

                }

                if(msg.what == MESSAGE_READ) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                byte[] readBuf = (byte[]) msg.obj;
                                Data.setText("|" + bytes2String(readBuf, msg.arg1) + "|");
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        };

        if (mBTArrayAdapter == null) {
            // Device does not support bluetooth
            mBluetoothStatus.setText("Status : Bluetooth not found");
            Toast.makeText(getApplicationContext(), "Bluetooth device not found"
                    , Toast.LENGTH_SHORT).show();
        } else {

            mScanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bluetoothOn();
                }
            });

            moffBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bluetoothOff();
                }
            });

            mListPairedDeviceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listPairedDevices();
                }
            });

            mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    discover();
                }
            });
        }
    }

    private String bytes2String(byte[] b, int count)
    {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < count; i++)
        {
            String myInt = Integer.toHexString((int) (b[i] & 0xFF));
            ret.append("0x" + myInt);
        }
        return ret.toString();
    }

    private void bluetoothOn() {
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            mBluetoothStatus.setText("Bluetooth enabled");
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }
    // Enter here after user selects "yes" or "no" to enabling radio
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact
                // the Intent's data Uri identifies which contact was selected
                mBluetoothStatus.setText("Enabled");
            } else {
                mBluetoothStatus.setText("Disabled");
            }
        }
    }

    private void bluetoothOff() {
        mBTAdapter.disable();
        mBluetoothStatus.setText("Bluetooth disabled");
        Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_SHORT).show();
    }
    private void discover() {
        // check if the device is already discovering
        if( mBTAdapter.isDiscovering() ) {
            mBTAdapter.cancelDiscovery();
            Toast.makeText(getApplicationContext(), "Discovery stopped", Toast.LENGTH_SHORT).show();
        }
        else {
            if( mBTAdapter.isEnabled() ) {
                mBTAdapter.startDiscovery();
                mBTArrayAdapter.clear();
                Toast.makeText(getApplicationContext(), "Discovery started", Toast.LENGTH_SHORT).show();
                registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
            else {
                Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if( BluetoothDevice.ACTION_FOUND.equals(action) ) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //ADD THE NAME TO THE LIST
                mBTArrayAdapter.add( device.getName() + "\n" + device.getAddress() );
                mBTArrayAdapter.notifyDataSetChanged();
            }
        }
    };
    private void listPairedDevices() {
        mBTArrayAdapter.clear();
        mPairedDevices = mBTAdapter.getBondedDevices();

        if( mBTAdapter.isEnabled() ) {
            // PUT IT'S ONE TO THE ADAPTER
            for( BluetoothDevice device : mPairedDevices ) {
                mBTArrayAdapter.add( device.getName() + "\n" + device.getAddress() );
                Toast.makeText(getApplicationContext(), "Show paired Devices", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            if( !mBTAdapter.isEnabled() ) {
                Toast.makeText(getBaseContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
                return;
            }

            mBluetoothStatus.setText("Connecting...");
            //Get the device MAC address, which is the last 17 chars in the view
            String info = ((TextView) view).getText().toString();
            final String address = info.substring(info.length() - 17);
            final String name = info.substring(0, info.length() - 17);

            // spawn a new thread to avoid blocking the GUI one
            new Thread()
            {

                @Override
                public void run() {
                    boolean fail = false;

                    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                    try {
                        Log.d("TAG", "try connecting");
                        mBTSocket = creatBluetoothSocket(device);
                    }
                    catch(IOException e) {
                        fail = true;
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                    // establish the Bluetooth socket connection
                    try {
                        Log.d("TAG", "try connecting");
                        mBTSocket.connect();
                    }
                    catch(IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close();
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                            .sendToTarget();

                        }
                        catch(IOException e2) {
                            // insert code to deal with this
                            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if( !fail ) {
                        mConnectedThread = new ConnectThread(mBTSocket, mHandler);
                        mConnectedThread.start();

                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                .sendToTarget();
                    }
                }
            }.start();
        }
    };

    private BluetoothSocket creatBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass()
                    .getMethod("createInsecureRfcommSocketToServiceRecord",
                            UUID.class);
        }
        catch(Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection", e);
        }
        return device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }


}

