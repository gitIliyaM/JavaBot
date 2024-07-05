package utils;
import functions.ImageOperation;
import java.awt.image.BufferedImage;

public class RgbMaster {
    private int height;
    private int width;
    private boolean hasAlphaChannel;
    private int[] pixels;
    static BufferedImage image;


    public RgbMaster(BufferedImage image) {
        this.image = image;
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.hasAlphaChannel = image.getAlphaRaster() != null;
        pixels = new int[width * height];
        this.pixels = image.getRGB(0,0,width,height,pixels,0,width);
    }
    public BufferedImage getImage(){
        return image;
    }
    public void changeImage(ImageOperation operation) throws Exception {
        for (int i = 0; i < pixels.length; i++) {
            float[] pixel = ImageUtils.rgbIntToArray(pixels[i]);
            float[] newPixel = operation.execute(pixel);
            pixels[i] = ImageUtils.arrayToRgbInt(newPixel);
        }
        // final int type = hasAlphaChannel ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        // image = new BufferedImage(width, height, type);
        image.setRGB(0,0,width,height,pixels,0,width);
    }
}
