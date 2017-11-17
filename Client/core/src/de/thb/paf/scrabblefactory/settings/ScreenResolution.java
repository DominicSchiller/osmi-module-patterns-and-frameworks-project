package de.thb.paf.scrabblefactory.settings;

/**
 * Enumeration of all supported screen resolutions.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public enum ScreenResolution {
    SD("sd", 0.125f, 480, 320, 570, 360),
    HD("hd", 0.25f, 960, 640, 1140, 720),
    HD2("hd2", 0.5f, 1920, 1280, 2280, 1440),
    UHD("uhd", 0.75f, 2880, 1920, 3420, 2160),
    DCI_4K("4k", 1.0f, 3840, 2560, 4560, 2880);

    /**
     * Constructor
     *
     * @param name        The screen resolutions's id name
     * @param scaleFactor virtual scale factor (reaching from 4K = 1.0 to SD = 0.125)
     * @param minWidth    The screen resolutions's minimal landscape width
     * @param minHeight   The screen resolutions's minimal landscape height
     * @param maxWidth    The screen resolutions's maximal landscape width
     * @param maxHeight   The screen resolutions's maximal landscape height
     */
    ScreenResolution(String name, float scaleFactor, int minWidth, int minHeight, int maxWidth, int maxHeight) {
        this.name = name;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.virtualScaleFactor = scaleFactor;
    }

    /**
     * The screen density's name
     */
    public final String name;

    /**
     * The screen resolution's minimal landscape width
     */
    public final int minWidth;

    /**
     * The screen resolution's maximal landscape width
     */
    public final int maxWidth;

    /**
     * The screen resolution's minimal landscape height
     */
    public final int minHeight;

    /**
     * The screen resolution's maximal landscape height
     */
    public final int maxHeight;

    /**
     * Virtual scale factor (reaching from 4K = 1.0 to SD = 0.125)
     */
    public final float virtualScaleFactor;
}
