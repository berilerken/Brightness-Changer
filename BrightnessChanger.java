package bright;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class BrightnessChanger {

    public static int Truncate(int value)
    {
        if (value < 0) {
            value = 0;
        }
        else if (value > 255) {
            value = 255;
        }
        return value;
    }


    public static void SetBrightness(String iPath, String oPath, int brightnessValue) throws IOException
    {

        File f = new File(iPath);
        BufferedImage image = ImageIO.read(f);

        int rgb[];


        for (int i = 0; i < image.getWidth(); i++) {


            for (int j = 0; j < image.getHeight(); j++) {

                rgb = image.getRaster().getPixel(i, j, new int[3]);


                int red = Truncate(rgb[0] + brightnessValue);
                int green = Truncate(rgb[1] + brightnessValue);
                int blue = Truncate(rgb[2] + brightnessValue);

                int arr[] = { red, green, blue };

                image.getRaster().setPixel(i, j, arr);
            }
        }

        ImageIO.write(image, "jpg", new File(oPath));
    }


    public static void main(String[] args) throws Exception
    {
        int brightnessValue;


        String inputPath = "/Users/berilerken/Desktop/bright/src/bright/photo.jpg";
        String outputPath = "/Users/berilerken/Desktop/bright/src/bright/lighter.jpg";
        String outputPath2 = "/Users/berilerken/Desktop/bright/src/bright/darker.jpg";


        Thread thread1 = new Thread() {
            public void run() {
                try {
                    SetBrightness(inputPath, outputPath2, -88);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                try {
                    SetBrightness(inputPath, outputPath, 88);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }
}