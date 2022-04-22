package com.mister_chan.camera;

import android.media.Image;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageSaver implements Runnable {

    private final Image image;

    public ImageSaver(Image image) {
        this.image = image;
    }

    @Override
    public void run() {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        if (!MainActivity.OUTPUT_FILE.exists()) {
            MainActivity.OUTPUT_FILE.mkdir();
        }
        String fileName = MainActivity.OUTPUT_PATH + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.close();
    }
}