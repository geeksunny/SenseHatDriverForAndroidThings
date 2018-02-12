package com.eon.androidthinks.sensehat;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.eon.androidthinks.sensehat.demos.JoystickDemo;
import com.eon.androidthinks.sensehat.gui.IGui;
import com.eon.androidthinks.sensehat.uitils.NetworkUtils;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class HomeActivity extends Activity {

    private JoystickDemo joystickDemo;
    private TextView cursorCoordTextView;
    private TextView cursorColorTextView;
    private TextView ipAdressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            // first init Views, so that the following method could use the UI
            this.setContentView(R.layout.activity_home);
            this.cursorCoordTextView = this.findViewById(R.id.cursorCoordTextView);
            this.cursorColorTextView = this.findViewById(R.id.cursorColorTextView);
            this.ipAdressTextView = this.findViewById(R.id.ipAdressTextView);

            String myIP = "********************** IP: " + NetworkUtils.getIPAddress(true) + " **********************";
            this.ipAdressTextView.setText(myIP);
            System.out.println("**** myIP:" + myIP);


            SensorManager sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
//            SenseHat senseHat = new SenseHat(sensorManager);
//            final LedMatrix ledMatrix = senseHat.getLedMatrix();
//            ledMatrix.draw(Color.BLACK);    // trun off


            this.joystickDemo = new JoystickDemo(sensorManager, new IGui() {
                @Override
                public void setCursorInformations(final String xCoord, final String yCoord, final String color)

                {

                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String coord = xCoord + "/" + yCoord;
                            HomeActivity.this.cursorCoordTextView.setText(coord);
                            HomeActivity.this.cursorColorTextView.setText(color);
                        }
                    });


                }
            });

        } catch (Exception e) {
            // TODO Exception Handling
            e.printStackTrace();
        }
    }

}