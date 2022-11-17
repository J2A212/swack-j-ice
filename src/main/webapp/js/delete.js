'use strict';
$('body').on('click','.delete',function(){
	const result = window.confirm("このメッセージを削除しますか？");
	if(result){
		$('#deleteChatLogId').val($(this).attr('id').slice(6));
		$('#deleteChatForm').submit();
	}
})