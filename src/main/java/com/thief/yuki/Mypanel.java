package com.thief.yuki;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * @author by xinghaowen
 * @version 1.0
 * @classname Mypanel
 */
public class Mypanel extends JPanel {
    private Image timg;
    private boolean isTransparent;
    private int filterRgb;

    Mypanel(Image image, boolean isTransparent, int filterRgb) {
        this.timg = image;
        this.isTransparent = isTransparent;
        this.filterRgb = filterRgb;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Image im;
        if (isTransparent) {
            ImageFilter imgf = new MyFilter(255, filterRgb);
            FilteredImageSource fis = new FilteredImageSource(timg.getSource(), imgf);
            im = Toolkit.getDefaultToolkit().createImage(fis);
        } else {
            im = timg;
        }
        Graphics2D g1 = (Graphics2D) g;
        g1.drawImage(im, 0, 0, this);
    }

    static class MyFilter extends RGBImageFilter {// 抽象类RGBImageFilter是ImageFilter的子类，
        // 继承它实现图象ARGB的处理
        int alpha = 0;
        private int filterRgb;


        public MyFilter(int alpha, int filterRgb) {// 构造器，用来接收需要过滤图象的尺寸，以及透明度
            this.canFilterIndexColorModel = true;
            this.filterRgb = filterRgb;
            // TransparentImageFilter类继承自RGBImageFilter，它的构造函数要求传入原始图象的宽度和高度。
            // 该类实现了filterRGB抽象函数
            // ，缺省的方式下，该函数将x，y所标识的象素的ARGB值传入，程序员按照一定的程序逻辑处理后返回该象素新的ARGB值
            this.alpha = alpha;

        }

        @Override
        public int filterRGB(int x, int y, int rgb) {
            DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();
            // DirectColorModel类用来将ARGB值独立分解出来
            int red = dcm.getRed(rgb);
            int green = dcm.getGreen(rgb);
            int blue = dcm.getBlue(rgb);
//            int alp = dcm.getAlpha(rgb);
            int redFilter = dcm.getRed(filterRgb);
            int greenFilter = dcm.getGreen(filterRgb);
            int blueFilter = dcm.getBlue(filterRgb);
            if ((red == redFilter && blue == blueFilter && green == greenFilter)) {//
                alpha = 0;
            } else {
                alpha = 255;
            }
            return alpha << 24 | red << 16 | green << 8 | blue;// 进行标准ARGB输出以实现图象过滤
        }
    }
}
