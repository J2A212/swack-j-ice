/**
 * 
 */
$(window).on('load', function(){ 
	//alert('Hello!!!!');
	$("#logArea").scrollTop($("#logArea")[0].scrollHeight)
 });
function logout(){	
		localStorage.removeItem('loginData')
	}

