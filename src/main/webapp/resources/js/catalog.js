$(document).ready(function() {

	$('.number-plus').bind('click', function() {
		var plusCounter = $(this).prev('input');
		var curQuantity = parseInt(plusCounter.val());
		if (curQuantity < 8) {
			curQuantity += 1;
			plusCounter.val(curQuantity);
		}
	});

	$('.number-minus').bind('click', function() {
		var plusCounter = $(this).next('input');
		var curQuantity = parseInt(plusCounter.val());
		if (curQuantity > 1) {
			curQuantity -= 1;
			plusCounter.val(curQuantity);
		}
	});

	$('.addQuantity').bind('click', function(event) {
		event.preventDefault();
		var quantity = parseInt($(this).parent().find("input[name=quantity]").val());
		var link = $(this).attr('href');
		var ajaxData = {};
		ajaxData['quantity'] = quantity;
		$.ajax({
			type: 'POST',
			url: link,
			dataType: 'json',
			data: $.param(ajaxData),
			success: function(res) {
				console.log(res);
			}
		});
	})
});