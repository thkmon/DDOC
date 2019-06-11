var DateUtil = {};

/**
 * 남에게 보여주기 위해 
 */
DateUtil.makeDateToPrint = function(_dateText) {
	
	// _dateText == 20161214020258
	
	if (_dateText == null || _dateText.length == 0) {
		return "";
	}
	
	var result = "";
	
	if (_dateText.length > 7) {
		result += _dateText.substring(0, 4);
		result += "/";
		result += _dateText.substring(4, 6);
		result += "/";
		result += _dateText.substring(6, 8);
	}
	
	if (_dateText.length > 13) {
		result += " ";
		result += _dateText.substring(8, 10);
		result += ":";
		result += _dateText.substring(10, 12);
		result += ":";
		result += _dateText.substring(12, 14);
	}
	
	return result;
}

DateUtil.getTodayDateTime = function() {
	
	var todayObj = new Date();
	var todayYear = todayObj.getFullYear();
	var todayMonth = (todayObj.getMonth() + 1);
	var todayDay = todayObj.getDate();
	
	var todayHour = todayObj.getHours();
	var todayMin = todayObj.getMinutes();
	var todaySec = todayObj.getSeconds();

	var todayDateAndTime = todayYear + "/";
	todayDateAndTime += DateUtil.make2digits(todayMonth) + "/";
	todayDateAndTime += DateUtil.make2digits(todayDay) + " ";
	todayDateAndTime += DateUtil.make2digits(todayHour) + ":";
	todayDateAndTime += DateUtil.make2digits(todayMin) + ":";
	todayDateAndTime += DateUtil.make2digits(todaySec);
	
	return todayDateAndTime;
}

DateUtil.make2digits = function(_numStr) {
	if (_numStr == null) {
		return "00";
	} else {
		_numStr = _numStr + "";
	}
	
	var len = _numStr.length;
	
	if (len == 0) {
		return "00";
		
	} else  if (len == 1) {
		return "0" + _numStr;
		
	} else if (len == 2) {
		return _numStr;
	}
	
	return _numStr.substring(0, 2);
}