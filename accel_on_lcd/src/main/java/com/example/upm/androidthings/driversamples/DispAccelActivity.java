/*
 * Copyright (c) 2017 Intel Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.upm.androidthings.driversamples;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.upm.androidthings.driverlibrary.Mma7660AccelerometerDriver;

import java.io.IOException;

/**
  * Example of using MMA7660 and Grove Jhd1313m1 LCD.
  *
  * This activity initializes the LCD and displays Acceleromter (x,y,z) in different color combinations.
  *
  */

public class DispAccelActivity extends Activity implements SensorEventListener {
    private static final String TAG = "DispAccelActivity";
    static {
        try {
            System.loadLibrary("javaupm_mma7660");
            System.loadLibrary("javaupm_jhd1313m1");
        } catch (UnsatisfiedLinkError e) {
            System.err.println(
                    "Native library failed to load.\n" + e);
            System.exit(1);
        }
    }

    private Mma7660AccelerometerDriver mAccelerometerDriver;
    private SensorManager mSensorManager;
    private upm_jhd1313m1.Jhd1313m1 lcd;
    private int ndx = 0;
    private short[][] rgb = new short[][]{
                {0xd1, 0x00, 0x00},
                {0xff, 0x66, 0x22},
                {0xff, 0xda, 0x21},
                {0x33, 0xdd, 0x00},
                {0x11, 0x33, 0xcc},
                {0x22, 0x00, 0x66},
                {0x33, 0x00, 0x44}};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting DispAccelActivity");
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerDynamicSensorCallback(new SensorManager.DynamicSensorCallback() {
            @Override
            public void onDynamicSensorConnected(Sensor sensor) {
                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    Log.i(TAG, "Accelerometer sensor connected");
                    mSensorManager.registerListener(DispAccelActivity.this, sensor,
                            SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

        try {
            mAccelerometerDriver = new Mma7660AccelerometerDriver(0);
            mAccelerometerDriver.register();
            Log.i(TAG, "Accelerometer driver registered");
            lcd = new upm_jhd1313m1.Jhd1313m1(0, 0x3E, 0x62);
            lcd.clear();
            Log.i(TAG, "Display initialized");
        } catch (IOException e) {
            Log.e(TAG, "Error initializing drivers: ", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAccelerometerDriver != null) {
            mSensorManager.unregisterListener(this);
            mAccelerometerDriver.unregister();
            try {
                mAccelerometerDriver.close();
                if (lcd != null) {
                    lcd.delete();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error closing accelerometer driver: ", e);
            } finally {
                mAccelerometerDriver = null;
                lcd = null;
            }
        }
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "Accelerometer event: " +
                event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
        try {
            // Display text and read count in first row
            lcd.setCursor(0, 0);
            // Change the color
            short r = rgb[ndx % 7][0];
            short g = rgb[ndx % 7][1];
            short b = rgb[ndx % 7][2];
            lcd.setColor(r, g, b);
            lcd.write("Accel(x,y,z) " + ndx++);
            // Display coordinates in second row
            lcd.setCursor(1, 0);
            lcd.write(String.format("%.2f ", event.values[0]) +
                       String.format("%.2f ", event.values[1]) +
                        String.format("%.2f ", event.values[2]));
            Thread.sleep(1000);
        } catch (Exception e) {
            Log.e(TAG, "Error in Callback", e);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "Accelerometer accuracy changed: " + accuracy);
    }
}