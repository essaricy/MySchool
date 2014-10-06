/*
jQuery.linechart plugin 
modified by Srikanth Kumar, 2013.
*/

(function($, window, document) {
  $.fn.chart = function(settings) {
    var id = null;
    var url = null;
    var chartType = null;

    var defaultSettings = {
      id: 'Graph',
      url: null,
      width: 500,
      height: 500,
	  lineWidth: 5
    };

    initChart();

    function initChart() {
      if (settings == null) {
        throw('chart - no settings specified.');
      }
      id = (settings.id == 'undefined') ? defaultSettings.id : settings.id;
      if (id == null) {
        throw('chart - id is not specified.');
      }
      url = (settings.url == 'undefined') ? defaultSettings.url : settings.url;
      if (url == null) {
        throw('chart - url is not specified.');
      }
      width = (settings.width == 'undefined') ? defaultSettings.width : settings.width;
      height = (settings.height == 'undefined') ? defaultSettings.height : settings.height;

      getChartData();
    }

    function getChartData() {
      $.ajax({
        url: url,
        data: {
          sid: new Date().getTime()
        },
        success: function(response) {
          $('#' + id).width(width).height(height);
          var chartData = response.CHART_DATA;
          if (chartData != null) {
            var chartType = chartData.CHART_TYPE;
            if (chartType == 'LINE_CHART') {
              generateLineChart(chartData);
            }
          }
        }
      });
    }

    function getAttribute(chartData, parent, child) {
      // read it from the data. If not present then read it from settings.
      if (chartData != null && chartData != 'undefined') {
        var parentData = chartData[parent];
        if (parentData == null || parentData == 'undefined') {
          if (settings[parent] != null) {
            return settings[parent][child];
          }
        } else {
          if (parentData[child] == null || parentData[child] == 'undefined') {
            if (settings[parent] != null) {
              return settings[parent][child];
            }
          } else {
            return parentData[child];
          }
        }
      }
      return null;
    }

    function generateLineChart(chartData) {
      var graphXAxis = new Object();
      var graphYAxis = new Object();
      var graphSeriesData = null;
      var graphSeriesNames = null;

      var graphXAxisLabel = getAttribute(chartData, 'X-Axis', 'label');
      if (graphXAxisLabel != null) {
        graphXAxis['label']=graphXAxisLabel;
      }
      var graphXAxisMarkers = getAttribute(chartData, 'X-Axis', 'Markers');
      if (graphXAxisMarkers != null) {
        graphXAxis['Markers']=graphXAxisMarkers;
      }

      var graphYAxisLabel = getAttribute(chartData, 'Y-Axis', 'label');
      if (graphYAxisLabel != null) {
       graphYAxis['label']=graphYAxisLabel;
      }
      var graphYAxisMarkers = getAttribute(chartData, 'Y-Axis', 'Markers');
      if (graphYAxisMarkers != null) {
        graphYAxis['Markers']=graphYAxisMarkers;
      }

      var graphSeriesData = (chartData.LineSeries == null || chartData.LineSeries == 'undefined') ? settings.LineSeries : chartData.LineSeries;
      if (graphSeriesData == null) {
        throw('chart - no data to draw graph.');
      }

      var LineSeriesNames = (chartData.LineSeriesNames == null || chartData.LineSeriesNames == 'undefined') ? settings.LineSeriesNames : chartData.LineSeriesNames;
      if (LineSeriesNames != null) {
        graphSeriesNames = new Array();
        for (var index=0; index < LineSeriesNames.length; index++) {
          var LineSeriesName = new Object();
          LineSeriesName.label=LineSeriesNames[index];
          graphSeriesNames[graphSeriesNames.length] = LineSeriesName;
        }
      }
	  var lineWidth = (chartData.lineWidth == null || chartData.lineWidth == 'undefined') ? settings.lineWidth : chartData.lineWidth;
      $.jqplot(id, graphSeriesData,
        {
          animate: true,
          legend: {
            show: true,
            renderer: $.jqplot.EnhancedLegendRenderer,
            rendererOptions: {
              numberRows: 1
            }
          },
          axesDefaults: {
            tickRenderer: $.jqplot.CanvasAxisTickRenderer ,
            tickOptions: {
              angle: 0,
              fontSize: '8pt',
            }
          },
          axes: {
            xaxis: {
              label: graphXAxis['label'],
              renderer: $.jqplot.CategoryAxisRenderer,
              ticks: graphXAxis['Markers'],
			  },
            yaxis: {
              label: graphYAxis['label'],
              labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
            }
          },
          series: graphSeriesNames,
          seriesDefaults: {
            show: true,
            lineWidth: lineWidth,
            showMarker: true,
          },
          highlighter: {
            show: true,
            sizeAdjust: 7.5, 
            tooltipLocation: 'n', 
            tooltipAxes: 'y', 
          },
          cursor: {
            show: false,
          }
        }
      );
    }
  }
})(jQuery, window, document);
