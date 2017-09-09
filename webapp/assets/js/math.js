/************************************************************
 *  浮点数运算:  因为js浮点数运算会损失精度，所以需要自定义方法*
 ************************************************************/
/*浮点数加法*/
function FloatAdd(arg1, arg2) {
	
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	};
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	};
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
};

/*浮点数减法*/
function FloatSub(arg1, arg2) {
	
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	};
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	};
	m = Math.pow(10, Math.max(r1, r2));
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
};

/*浮点数乘法运算*/ 
function FloatMul(arg1, arg2) {
	
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m);
}

/*浮点数除法运算*/
function FloatDiv(arg1, arg2) {
	 
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
////////////////////////////////////////////////////////////////////////////////////////




/*********************************************************
 *  验证格式是否正确          					             *
 *********************************************************/
/*验证身份证*/
function checkIdcard(num) { 
	
        num = num.toUpperCase();  
        //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
        if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))   
        { 
            //alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。'); 
            return false; 
        } 
        //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
        //下面分别分析出生日期和校验位 
        var len, re; 
        len = num.length; 
        if (len == 15) 
        { 
            re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/); 
            var arrSplit = num.match(re);

            //检查生日日期是否正确 
            var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]); 
            var bGoodDay; 
            bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4])); 
            if (!bGoodDay) 
            { 
                //alert('输入的身份证号里出生日期不对！');   
                return false; 
            } 
            else 
            { 
                    //将15位身份证转成18位 
                    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
                    var nTemp = 0, i;   
                    num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6); 
                    for(i = 0; i < 17; i ++) 
                    { 
                        nTemp += num.substr(i, 1) * arrInt[i]; 
                    } 
                    num += arrCh[nTemp % 11];   
                    return true;   
            }   
        } 
        if (len == 18) 
        { 
            re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/); 
            var arrSplit = num.match(re);

            //检查生日日期是否正确 
            var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]); 
            var bGoodDay; 
            bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4])); 
            if (!bGoodDay) 
            { 
                //alert(dtmBirth.getYear()); 
                //alert(arrSplit[2]); 
                //alert('输入的身份证号里出生日期不对！'); 
                return false; 
            } 
        else 
        { 
            //检验18位身份证的校验码是否正确。 
            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
            var valnum; 
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
            var nTemp = 0, i; 
            for(i = 0; i < 17; i ++) 
            { 
                nTemp += num.substr(i, 1) * arrInt[i]; 
            } 
            valnum = arrCh[nTemp % 11]; 
            if (valnum != num.substr(17, 1)) 
            { 
                //alert('18位身份证的校验码不正确！应该为：' + valnum); 
                return false; 
            } 
            return true; 
        } 
        } 
        return false; 
}
////////////////////////////////////////////////////////////////////////////////////////


/*********************************************************
 *  用到的方法              					             *
 *********************************************************/
/*返回*/
function historyback(){
	history.back();
}