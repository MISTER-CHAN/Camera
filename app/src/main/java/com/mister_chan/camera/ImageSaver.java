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
        String path = Environment.getExternalStorageDirectory() + "/DCIM/Camera2/";
        File imageFile = new File(path);
        if (!imageFile.exists()) {
            imageFile.mkdir();
        }
        String fileName = path + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.close();
    }
}