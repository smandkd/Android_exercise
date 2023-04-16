package org.techtowm.classicblt;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.SystemClock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectThread extends Thread{
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler mHandler;


    public ConnectThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        mHandler = handler;
        InputStream rpmIn = null;
        OutputStream rpmOut = null;

        // Get the input and output streams, using temp objects because
        // member stream are final
        try {
            rpmIn = socket.getInputStream();
            rpmOut = socket.getOutputStream();
        }
        catch (IOException e) {

        }

        mmInStream = rpmIn;
        mmOutStream = rpmOut;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;

        while( true ) {
            try {
                bytes = mmInStream.available();

                if( bytes != 0 ) {
                    // Read from InputStream
                    buffer = new byte[1024];
                    SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.

                    bytes = mmInStream.available();
                    bytes = mmInStream.read(buffer, 0, bytes);

                    mHandler.obtainMessage(MainActivity.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget(); // Send the obtained bytes to the UI activity
                }


            }
            catch( IOException e ) {
                e.printStackTrace();

                break;
            }
        }
    }

    public void write( String input ) {
        byte[] bytes = input.getBytes();
        try {
            mmOutStream.write(bytes);
        }
        catch(IOException e) {

        }
    }

    //Call this from the main activity to shutdown the connection
    public void cancel() {
        try{
            mmSocket.close();
        }
        catch(IOException e){}
    }

}



















