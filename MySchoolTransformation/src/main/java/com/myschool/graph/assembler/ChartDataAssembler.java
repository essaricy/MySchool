package com.myschool.graph.assembler;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

import com.myschool.common.assembler.CollectionDataAssembler;
import com.myschool.graph.dto.AxisDto;
import com.myschool.graph.dto.LineChartDto;

/**
 * The Class ChartDataAssembler.
 */
public class ChartDataAssembler {

    /**
     * Creates the.
     * 
     * @param lineChart the line chart
     * @return the jSON object
     */
    public static JSONObject create(LineChartDto lineChart) {
        JSONObject jsonObject = null;

        if (lineChart != null) {
            jsonObject = new JSONObject();
            jsonObject.put("CHART_TYPE", "LINE_CHART");
            AxisDto xAxis = lineChart.getXAxis();
            if (xAxis != null) {
                JSONObject xAxisJSON = new JSONObject();
                xAxisJSON.put("Label", xAxis.getLabel());
                xAxisJSON.put("Markers", CollectionDataAssembler.createJSONArray(xAxis.getMarkers()));
                jsonObject.put("X-Axis", xAxisJSON);
            }
            AxisDto yAxis = lineChart.getYAxis();
            if (yAxis != null) {
                JSONObject yAxisJSON = new JSONObject();
                yAxisJSON.put("Label", yAxis.getLabel());
                yAxisJSON.put("Markers", CollectionDataAssembler.createJSONArray(yAxis.getMarkers()));
                jsonObject.put("Y-Axis", yAxisJSON);
            }
            List<String> lineSeriesNames = lineChart.getSeriesNames();
            if (lineSeriesNames != null) {
                jsonObject.put("LineSeriesNames", CollectionDataAssembler.createJSONArray(lineSeriesNames));
            }
            List<List<BigDecimal>> lineSeries = lineChart.getLineSeries();
            if (lineSeries != null) {
                jsonObject.put("LineSeries", CollectionDataAssembler.createJSONArrayOfArray(lineSeries));
            }
        }
        return jsonObject;
    }

}
