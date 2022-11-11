/**
 * 
 */
$(window).on('load', function(){ 
	//alert('Hello!!!!');
	$("#logArea").scrollTop($("#logArea")[0].scrollHeight)
 });
function logout(){
	var loginData = {
			mailAddress: $('#mailAddress').val(),
			password: $('#password').val()
			};
	localStorage.removeItem('loginData',JSON.stringify(loginData))
}
