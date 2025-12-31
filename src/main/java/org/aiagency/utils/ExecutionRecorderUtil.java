package org.aiagency.utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecutionRecorderUtil {

    private static FFmpegFrameRecorder recorder;
    private static Thread recordingThread;
    private static volatile boolean recording = false;
    private static String recordingFolderPath;

    /* ---------------- START RECORDING ---------------- */

    public static void startRecording() {
        // redirect stderr to a dummy PrintStream
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {}
        }));
        try {
            String timestamp =
                    new SimpleDateFormat("MM-dd-yyyy_hh-mm-a")
                            .format(new Date());

            recordingFolderPath =
                    "ExecutionRecorder/Recorder_" + timestamp;

            new File(recordingFolderPath).mkdirs();

            String outputFile =
                    recordingFolderPath + "/execution.mp4";

            Dimension screenSize =
                    Toolkit.getDefaultToolkit().getScreenSize();

            recorder = new FFmpegFrameRecorder(
                    outputFile,
                    screenSize.width,
                    screenSize.height
            );

            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFormat("mp4");
            recorder.setFrameRate(10);
            recorder.setVideoQuality(0);

            recorder.start();
            recording = true;

            Robot robot = new Robot();
            Java2DFrameConverter converter =
                    new Java2DFrameConverter();

            recordingThread = new Thread(() -> {
                try {
                    while (recording) {
                        BufferedImage screen =
                                robot.createScreenCapture(
                                        new Rectangle(screenSize));

                        recorder.record(converter.convert(screen));
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            recordingThread.start();

            System.out.println("Screen recording started (JavaCV)");

        } catch (Exception e) {
            throw new RuntimeException("Failed to start recording", e);
        }
    }

    /* ---------------- STOP RECORDING ---------------- */

    public static void stopRecording() {
        try {

            recording = false;

            if (recordingThread != null) {
                recordingThread.join();
            }

            if (recorder != null) {
                recorder.stop();
                recorder.release();
            }

            System.out.println("Screen recording stopped");
            System.out.println("Saved at: " + recordingFolderPath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to stop recording", e);
        }
    }
}
