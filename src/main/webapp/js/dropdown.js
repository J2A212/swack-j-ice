'use strict';
$(function() {
	$('.dropdown-item').on('click',function(){
		alert($(this).text() + "が選択されました")
	});
});