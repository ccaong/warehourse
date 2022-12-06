package com.ccaong.warehousingmanager.rfid.other.power;


import com.ccaong.warehousingmanager.rfid.other.tools.dlog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OtherPower {

    public boolean oPowerUp() {
        power_up();
        return true;
    }


    public boolean oPowerDown() {
        return true;

    }

    private void power_up() {
        File com = new File("/sys/devices/platform/odm/odm:exp_dev/gps_com_switch");
        File power = new File("/sys/bus/platform/devices/odm:exp_dev/psam_en");
        dlog.toDlogAPI("power:" + power);

        writeFile(com, "1");
        writeFile(power, "1");
    }


    private synchronized void writeFile(File file, String value) {

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(value.getBytes());
            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
