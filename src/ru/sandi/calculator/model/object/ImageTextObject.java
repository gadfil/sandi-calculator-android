package ru.sandi.calculator.model.object;

/**
 * Created by gadfil on 29.09.2014.
 */
public class ImageTextObject {
    private String mText;
    private String mImage;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ImageTextObject() {
    }

    public ImageTextObject(String mText, String mImage, long id) {
        this.mText = mText;
        this.mImage = mImage;
        this.id = id;
    }

    @Override
    public String toString() {
        return  mText;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }
}
