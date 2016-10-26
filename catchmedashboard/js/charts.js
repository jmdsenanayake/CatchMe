$(function () {
    $('#container').highcharts({
        title: {
            text: '',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: 'lkr'
        },
        legend: {
            layout: 'horizontal',
            align: 'left',
            verticalAlign: 'top',
            borderWidth: 0
        },
        series: [{
            name: 'Quantity',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'Amount',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });


    $('#ordercontainer').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: ''
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: '',
            colorByPoint: true,
            data: [{
                name: 'Pending',
                y: 56.33
            }, {
                name: 'Processing',
                y: 24.03,
                sliced: true,
                selected: true
            }, {
                name: 'Onhold',
                y: 10.38
            }, {
                name: 'Refunded',
                y: 4.77
            }, {
                name: 'Completed',
                y: 0.2
            }]
        }]
    });
});