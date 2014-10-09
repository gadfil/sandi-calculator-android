package ru.sandi.calculator.controller;

/**
 * Created by gadfil on 30.09.2014.
 */
public interface FirstStep {
    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated. Must be used to final initialization.
     */
    public void init();

    /**
     * Called when user select box.
     */
    public void selectBox();

    /**
     * Called when user select color to current box.
     * @param imageColor path to  color's image {@link ru.sandi.calculator.model.table.ImageBoxTable#IMAGE }
     * @param colorId id color {@link ru.sandi.calculator.model.table.BoxColorTable#ID_COLOR}
     */
    public void setBoxColor(String imageColor, long colorId, int position);

    /**
     * Called when user select arguments for query
     * @param width
     * @param height
     * @param depth
     * @param typeOfLaminate
     * @param typeOfSystemDoor
     */
    public void setArg(int width, int height, int depth, String typeOfLaminate, String typeOfSystemDoor);

    public void selectBox(long _id, long id);
}
