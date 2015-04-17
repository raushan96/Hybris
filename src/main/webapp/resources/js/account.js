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
				var form = $('#modal_addrForm');
				populateForm(form, res);
			}
		});
	};

	function populateForm(form, addrObj) {
		form.find('#modal_addrCompany').val(addrObj.companyName);
		form.find('#modal_addrCity').val(addrObj.city);
		form.find('#modal_addrPostalCode').val(addrObj.postalCode);
		form.find('#modal_addrAddress').val(addrObj.address);
		form.find('#modal_addrCountry').val(addrObj.country);
		form.find('#modal_addressId').val(addrObj.addressId);
	}

	$('button[name=delete_address]').bind('click', function() {
		var ajaxData = {};
		ajaxData['addressId'] = this.id.split('_')[1];
		$.ajax({
			type: 'POST',
			url: '/address/deleteAddress',
			dataType: 'json',
			data: $.param(ajaxData),
			success: function(res) {
				console.log(res);
			}
		});
	});

	function cleanAddressPopUp() {
		var form = $('#modal_addrForm')[0];
		form.reset();
	};

	$('button[name=edit_address]').bind('click', function() {
		openEditPopUp(this.id);
	});

	$('#add_address').bind('click', function() {
		cleanAddressPopUp();
	});
});