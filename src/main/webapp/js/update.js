'use strict';
$('body').on('click','.update',function(){
	$('.show_mode').hide();
	$('.edit_mode').show();
});
$('body').on('click','.regist',function(){
	$('.show_mode').show();
	$('.edit_mode').hide();
});