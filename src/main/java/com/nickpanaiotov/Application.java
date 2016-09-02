package com.nickpanaiotov;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Application {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {

        String pathToFile = "/440Hz_44100Hz_16bit_05sec.wav";
        InputStream inputStream = Application.class.getClass().getResourceAsStream(pathToFile);

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat audioFormat = audioInputStream.getFormat();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int read;
        byte[] buff = new byte[1024];
        while ((read = audioInputStream.read(buff)) > 0) {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] audioBytes = out.toByteArray();

        //double[] audioDoubles = toDoubleArray(audioBytes);

        ///FFT fft = new FFT(audioFormat.getFrameSize() , new Float(audioFormat.getSampleRate()).intValue());
        //fft.transform(audioDoubles);


        FrequencyScanner scanner = new FrequencyScanner();
        double frequency = scanner.extractFrequency(toShortArray(audioBytes), new Float(audioFormat.getSampleRate()).intValue());


        System.out.println();


    }

    public static double[] toDoubleArray(byte[] byteArray) {
        int times = Double.SIZE / Byte.SIZE;
        double[] doubles = new double[byteArray.length / times];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = ByteBuffer.wrap(byteArray, i * times, times).getDouble();
        }
        return doubles;
    }

    public static short[] toShortArray(byte[] byteArray) {
        int times = Double.SIZE / Byte.SIZE;
        short[] doubles = new short[byteArray.length / times];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = ByteBuffer.wrap(byteArray, i * times, times).getShort();
        }
        return doubles;
    }
}
