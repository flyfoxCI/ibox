
var data1=$("#records").val();
var str1=data1.split('|');
var arr1=new Array(str1.length);
for (var i = 0; i <str1.length; i++) {
	
	var element=str1[i].split(',');
	var arr2=new Array(element.length);
	arr2[0]=Date.parse(element[0],"yyyy-mm-dd");
	arr2[1]=parseInt(element[1]);
	arr1[i]=arr2;
}
var data2=[{
        label: "Data ",
        data: arr1,
        color: '#23c6c8'
    }];
$.plot($("#flot-chart3"), data2, {
    xaxis: {
        show:true
    },
    series: {
        lines: {
            show: true,
            fill: true,
            fillColor: {
                colors: [{
                    opacity: 1
                }, {
                    opacity: 1
                }]
            },
        },
        curvedLines: {
            active: true,
            fit: true,
            apply: true
        },
        points: {
            width: 0.1,
            show: false
        },
    },
    grid: {
        show: false,
        borderWidth: 0,
        hoverable: true
    },
    legend: {
        show: false,
    }
    
});

