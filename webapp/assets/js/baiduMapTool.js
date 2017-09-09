//2011-7-25
function my_load_script(xyUrl, callback){
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = xyUrl;
    /*借鉴了jQuery的script跨域方法*/
    script.onload = script.onreadystatechange = function(){
        if((!this.readyState || this.readyState === "loaded" || this.readyState === "complete")){
            callback && callback();
            // Handle memory leak in IE
            script.onload = script.onreadystatechange = null;
            if ( head && script.parentNode ) {
                head.removeChild( script );
            }
        }
    };
    head.insertBefore( script, head.firstChild );// Use insertBefore instead of appendChild  to circumvent an IE6 bug.
}
function my_translate(point,type,callback){
	/*地图的坐标转换,默认从gps，转成百度地图经纬度*/
    var xyUrl =  "http://api.map.baidu.com/geoconv/v1/?coords="+point.lng+","+point.lat+"&from="+type+"&to=5&ak=D7bc6a051e5ac6d616f273528ac5616a&callback=getTranslateLocation";
    /*动态创建script标签*/
    my_load_script(xyUrl);
    getTranslateLocation=function(obj){
        var point = new BMap.Point(obj.result[0].x, obj.result[0].y);
        callback && callback(point);
    };
}

function getResultByPoints(point,type,callback){
	/*通过经纬度，查询地址的详细信息和附近的poi兴趣点*/
	var xyUrl = "http://api.map.baidu.com/geocoder/v2/?ak=D7bc6a051e5ac6d616f273528ac5616a&location="+point.lat+","+point.lng+"&coordtype="+type+"&output=json&pois=1&callback=getResultByPointsJsCallback";
    my_load_script(xyUrl);
    getResultByPointsJsCallback=function(obj){
        callback && callback(obj);
    };
}

/*进行经纬度转换为距离的计算*/
function myRad(d){
	return d * Math.PI / 180.0;/*经纬度转换成三角函数中度分表形式。*/
}
/*计算距离，参数分别为第一点的纬度，经度；第二点的纬度，经度*/
function MyGetDistance(lat1,lng1,lat2,lng2){

	var radLat1 = myRad(lat1);
	var radLat2 = myRad(lat2);
	var a = radLat1 - radLat2;
	var  b = myRad(lng1) - myRad(lng2);
	var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	s = s *6378.137 ;// EARTH_RADIUS;
	s = Math.round(s * 10000) / 10; //输出为公里
	//s=s.toFixed(4);
	return s;
 }