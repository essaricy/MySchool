package com.myschool.user.constants;

/**
 * The Enum ColorSwatch.
 */
public enum ColorSwatch {

	/** The CONCRETE. */
	CONCRETE("CONCRETE - ASBESTOS", "#7F8C8D", "#95A5A6"),

    /** The SILVER. */
    SILVER("SILVER - CLOUDS", "#BDC3C7", "#ECF0F1"),

    /** The POMEGRANATE. */
    POMEGRANATE("POMEGRANATE - ALIZARIN", "#C0392B", "#E74C3C"),

    /** The PUMPKIN. */
    PUMPKIN("PUMPKIN - CARROT", "#D35400", "#E67E22"),

    /** The ORANGE. */
    ORANGE("ORANGE - SUN FLOWER", "#F39C12", "#F1C40F"),

    /** The MIDNIGH t_ blue. */
    MIDNIGHT_BLUE("MIDNIGHT BLUE - WET ASPHALT", "#2C3E50", "#34495E"),

    /** The WISTERIA. */
    WISTERIA("WISTERIA - AMETHYST", "#8E44AD", "#9B59B6"),

    /** The BELIZ e_ hole. */
    BELIZE_HOLE("BELIZE HOLE - PETER RIVER", "#2980B9", "#3498DB"),

    /** The NEPHRITIS. */
    NEPHRITIS("NEPHRITIS - EMERALD", "#27AE60", "#2ECC71"),

    /** The GREE n_ sea. */
    GREEN_SEA("GREEN SEA - TURQUOISE", "#16A085", "#1ABC9C");

	/** The name. */
	private String name;

	/** The primary color code. */
	private String primaryColorCode;

	/** The secondary color code. */
	private String secondaryColorCode;

	/**
	 * Instantiates a new color swatch.
	 *
	 * @param name the name
	 * @param primaryColorCode the primary color code
	 * @param secondaryColorCode the secondary color code
	 */
	private ColorSwatch(String name, String primaryColorCode, String secondaryColorCode) {
		this.name=name;
		this.primaryColorCode=primaryColorCode;
		this.secondaryColorCode=secondaryColorCode;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the primary color code.
	 *
	 * @return the primary color code
	 */
	public String getPrimaryColorCode() {
		return primaryColorCode;
	}

	/**
	 * Gets the secondary color code.
	 *
	 * @return the secondary color code
	 */
	public String getSecondaryColorCode() {
		return secondaryColorCode;
	}

	/**
	 * Gets the.
	 *
	 * @param name the name
	 * @return the color swatch
	 */
	public static ColorSwatch get(String name) {
		if (name != null) {
			for (ColorSwatch colorSwatch : values()) {
				if (colorSwatch.toString().equalsIgnoreCase(name)) {
					return colorSwatch;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the by name.
	 *
	 * @param name the name
	 * @return the by name
	 */
	public static ColorSwatch getByName(String name) {
		if (name != null) {
			for (ColorSwatch colorSwatch : values()) {
				if (name.equals(colorSwatch.getName())) {
					return colorSwatch;
				}
			}
		}
		return null;
	}
	
}
