package net.wangds.zxingcomp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.wangds.log.helper.LogHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Map;

public class QrCodeGenerator {

    public static class QrCodeGenerateParam {
        /**
         * 宽度.
         */
        private int width=100;
        /**
         * 高度.
         */
        private int height=100;
        /**
         * 前景色.
         */
        private int color = 0xFF000000;
        /**
         * 背景色.
         */
        private int bgColor = 0xFFFFFFFF;
        /**
         * 边距.
         */
        private int margin = 1;
        /**
         * 二维码容错率.
         * <P>默认值: {@link ErrorCorrectionLevel#M}</P>
         */
        private ErrorCorrectionLevel level = ErrorCorrectionLevel.M;
        /**
         * 二维码内容.
         */
        private String text;

        /**
         * 字符集.
         * <p>默认值:UTF-8</p>
         */
        private Charset charset = Charset.forName("UTF-8");
        @SuppressWarnings("unused")
        public int getWidth() {
            return width;
        }

        @SuppressWarnings("unused")
        public void setWidth(int width) {
            this.width = width;
        }

        @SuppressWarnings("unused")
        public int getHeight() {
            return height;
        }

        @SuppressWarnings("unused")
        public void setHeight(int height) {
            this.height = height;
        }

        @SuppressWarnings("unused")
        public int getColor() {
            return color;
        }

        @SuppressWarnings("unused")
        public void setColor(int color) {
            this.color = color;
        }

        @SuppressWarnings("unused")
        public int getBgColor() {
            return bgColor;
        }

        @SuppressWarnings("unused")
        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }

        @SuppressWarnings("unused")
        public int getMargin() {
            return margin;
        }

        @SuppressWarnings("unused")
        public void setMargin(int margin) {
            this.margin = margin;
        }

        @SuppressWarnings("unused")
        public ErrorCorrectionLevel getLevel() {
            return level;
        }

        @SuppressWarnings("unused")
        public void setLevel(ErrorCorrectionLevel level) {
            this.level = level;
        }

        @SuppressWarnings("unused")
        public String getText() {
            return text;
        }

        @SuppressWarnings("unused")
        public void setText(String text) {
            this.text = text;
        }

        /**
         * 工厂函数，创建默认对象.
         * @return 默认对象.
         */
        @SuppressWarnings("unused")
        public static QrCodeGenerateParam of(){
            return new QrCodeGenerateParam();
        }

        /**
         * 设置二维码宽,单位:px;
         */
        @SuppressWarnings("unused")
        public QrCodeGenerateParam width(int width){
            this.width = width;
            return this;
        }

        /**
         * 设置二维码高，单位:px;
         * @param height 高.
         * @return 二维码生成配置.
         */
        @SuppressWarnings("unused")
        public QrCodeGenerateParam height(int height){
            this.height = height;
            return this;
        }

        /**
         * 设置前景色.
         * @param color 前景设.
         * @return 二维码生成配置.
         */
        @SuppressWarnings("unused")
        public QrCodeGenerateParam color(int color){
            this.color = color;
            return this;
        }

        /**
         * 设置背景色.
         * @param color 背景色.
         * @return 二维码生成配置.
         */
        @SuppressWarnings("unused")
        public QrCodeGenerateParam bgColor(int color){
            this.bgColor = color;
            return this;
        }

        /**
         * 设置边距.
         * @param margin 边距.
         * @return 二维码生成配置.
         */
        @SuppressWarnings("unused")
        public QrCodeGenerateParam margin(int margin){
            this.margin = margin;
            return this;
        }

        /**
         * 设置容错等级.
         * @param level 容错等级.
         * @return 二维码生成配置.
         */
        @SuppressWarnings("unused")
        public QrCodeGenerateParam level(ErrorCorrectionLevel level){
            this.level = level;
            return this;
        }

        /**
         * 设置二维码内容.
         * @param text 二维码内容.
         * @return 二维码生成配置.
         */
        @SuppressWarnings("unused")
        public QrCodeGenerateParam text(String text){
            this.text = text;
            return this;
        }

        /**
         * 生成二维码中文本字符集.
         * @param cs 字符集.
         * @return 二维码生成配置.
         */
        @SuppressWarnings("unused")
        public QrCodeGenerateParam charset(Charset cs){
            this.charset = cs;
            return this;
        }


    }

    /**
     * 生成二维码.
     * @param param 生成参数.
     * @return 二维码矩阵.
     */
    @SuppressWarnings("unused")
    public BitMatrix generate(QrCodeGenerateParam param) {

        Map<EncodeHintType, Object> hints = new Hashtable<>();

        hints.put(EncodeHintType.ERROR_CORRECTION, param.level);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, param.charset.name());
        hints.put(EncodeHintType.MARGIN, param.margin);   //设置白边

        //MatrixToImageConfig config = new MatrixToImageConfig(param.color, param.bgColor);
        try {
            return new MultiFormatWriter().encode(param.text, BarcodeFormat.QR_CODE, param.width, param.height, hints);
        } catch (WriterException e) {
            LogHelper.error(e);
        }
        return null;
    }

    public BufferedImage toBufferedImage(BitMatrix mat,QrCodeGenerateParam param){
        int width = mat.getWidth();
        int height = mat.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                image.setRGB(x, y, mat.get(x, y) ? param.color : param.bgColor);
            }
        }
        return image;
    }

}
