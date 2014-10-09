package ru.sandi.calculator.model.object;

/**
 * Created by gadfil on 25.09.2014.
 */
public class DoorFrameObject {
    private long _id;
    private long id;
    private String srcImage;
    private String title;

    public DoorFrameObject(String srcImage, String title) {
        this.srcImage = srcImage;
        this.title = title;
    }

    @Override
    public String toString() {
        return  title;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
