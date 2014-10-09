package ru.sandi.calculator.model.object;

/**
 * Created by gadfil on 30.09.2014.
 */
public class Box3dObject {
    private long _id;
    private long id;
    private long idColor;
    private String number;
    private String image;
    private String color = null;

    public Box3dObject() {
        idColor = -1;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public long getIdColor() {
        return idColor;
    }

    public void setIdColor(long idColor) {
        this.idColor = idColor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
