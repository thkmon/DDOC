var AjaxUtil = {};

/**
 * AjaxUtil.sendPost
 * 
 * _url : 대상 주소
 * _dataForm : 태그 아이디 혹은 태그 클래스(폼, 인풋박스)
 * _callback : 콜백함수
 * _etcParams (선택적) : 추가 파라미터. "name=genius", "a=111&b=222" 식으로 써야 한다.
 */
AjaxUtil.sendPost = function sendPost (_url, _dataForm, _etcParams, _callback) {
	
	if (_url == null || _url.length == 0) {
		alert("sendPost : 대상 주소를 알 수 없습니다.");
		return false;
	}
	
	var tgUrl = _url; 
	var params = "";
	
	if (_dataForm == null || _dataForm.length == 0) {
		alert("sendPost : 폼 이름을 알 수 없습니다.");
		return false;
		
	} else if (_dataForm.substring(0, 1) != "." && _dataForm.substring(0, 1) != "#") {
		// 맨 앞 글자가 .도 #도 아닐 경우
		// 파라미터에 그대로 정보 박는다.
		params = _dataForm;
		
	} else {
		// 맨 앞 글자가 .이거나 #일 경우
		var tagName = $(_dataForm).prop("tagName");
		// 태그가 존재할 경우만 수행
		if (tagName != null && tagName.length > 0) {
			tagName = tagName.toLowerCase();
			
			if (tagName == "form") {
				// 폼일 경우
				// 시리얼라이즈해서 다 붙인다.
				params = $(_dataForm).serialize();
				
			} else {
				// 폼이 아닐 경우
				// 한 개의 value만 가져온다.
				var paramKey = _dataForm.replace(".","").replace("#","");
				if (paramKey != null && paramKey.length > 0) {
					var paramValue = $(_dataForm).val();
					if (paramValue != null && paramValue.length > 0) {
						params = paramKey + "=" + paramValue;
					}
				}
			}	
		}
	}
	
	// _etcParams이 존재할 경우만 덧붙임.
	if (_etcParams != null && _etcParams.length > 0) {
		if (params == null || params.length == 0) {
			// 기존 파라미터가 없으면 그대로 박음.
			params = _etcParams;
			
		} else {
			// 기존 파라미터가 있으면 덧붙임.
			params = params + "&" + _etcParams;
		}
	}
	
	alert("input params : " + params);
	
    $.ajax({
        type : "POST",  
        url : tgUrl,
        data : params,      
        success : function(result) {
             if (_callback == null || _callback.length == 0) {
            	 alert("sendPost 결과 : " + result);
             } else {
            	 alert("함수 실행");
            	 _callback(result);
             }
        },
        // beforeSend : showRequest,  
        error : function(e){
        	
        	if(e != null) {
        		var eMsg = e.responseText;
        		
        		if (eMsg != null && eMsg.length > 0) {
        			eMsg = eMsg.toLowerCase();
        			if (eMsg.indexOf("http status 404")) {
        				alert("페이지를 찾을 수 없습니다. (404)");
        			} else {
        				alert("에러 발생 : " + e.responseText);
        			}
        		}
        	}
        	
        }
    });      
}
