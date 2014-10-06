package com.myschool.infra.report.constants;

import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;

/**
 * The Class ReportStyles.
 */
public class ReportStyles {

    /** The Constant REPORT_FONT. */
    public static final String REPORT_FONT = "SansSerif";

    /** The Constant STYLE_BUILDERS. */
    public static final StyleBuilders STYLE_BUILDERS = DynamicReports.stl;

    /** The Constant SERIF_12_BOLD_CENTER. */
    public static final StyleBuilder SERIF_12_BOLD_CENTER = STYLE_BUILDERS.style()
            .setFont(DynamicReports.stl.font(REPORT_FONT, true, false, 12))
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setHorizontalAlignment(HorizontalAlignment.CENTER);

    /** The Constant SERIF_8_BOLD_CENTER. */
    public static final StyleBuilder SERIF_8_BOLD_CENTER = STYLE_BUILDERS.style()
            .setFont(DynamicReports.stl.font(REPORT_FONT, true, false, 8))
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setHorizontalAlignment(HorizontalAlignment.CENTER);

    /** The Constant SERIF_8_REGULAR_CENTER. */
    public static final StyleBuilder SERIF_8_REGULAR_CENTER = STYLE_BUILDERS.style()
            .setFont(DynamicReports.stl.font(REPORT_FONT, false, false, 8))
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setHorizontalAlignment(HorizontalAlignment.CENTER);

    /** The Constant SERIF_8_REGULAR_RIGHT. */
    public static final StyleBuilder SERIF_8_REGULAR_RIGHT = STYLE_BUILDERS.style()
            .setFont(DynamicReports.stl.font(REPORT_FONT, false, false, 8))
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setHorizontalAlignment(HorizontalAlignment.RIGHT);

    /** The Constant TABLE_HEADER_STYLE. */
    public static final StyleBuilder TABLE_HEADER_STYLE = STYLE_BUILDERS
            .style(SERIF_8_BOLD_CENTER).setBorder(STYLE_BUILDERS.penThin());

}
