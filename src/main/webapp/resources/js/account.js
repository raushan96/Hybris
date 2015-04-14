$(document).ready(function() {

	function openEditPopUp(address) {
		var addressId = address.split('_')[1];
		var ajaxData = {};
		ajaxData['addressId'] = addressId;
		$.ajax({
			type: 'GET',
			url: '/address/getEditAddress',
			dataType: 'json',
			data: $.param(ajaxData),
			success: function(res) {
				console.log(res);
			}
		});
	};

	$('button[name=edit_address]').bind('click', function() {
		openEditPopUp(this.id);
	})
});