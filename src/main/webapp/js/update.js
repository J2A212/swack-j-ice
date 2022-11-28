'use strict';
$('body').on('click','.update',function(){
	$('.show_mode').hide();
	$('.edit_mode').show();
	$('#updateChatLogId').val($(this).attr('id').slice(6));
	$('#updateChatForm').submit();
});
$('body').on('click','.regist',function(){
	$('.show_mode').show();
	$('.edit_mode').hide();
});
