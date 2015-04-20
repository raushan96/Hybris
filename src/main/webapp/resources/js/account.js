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
				populateAddrForm(form, res);
			}
		});
	};

	function populateAddrForm(form, addrObj) {
		form.find('#modal_addrCompany').val(addrObj.companyName);
		form.find('#modal_addrCity').val(addrObj.city);
		form.find('#modal_addrPostalCode').val(addrObj.postalCode);
		form.find('#modal_addrAddress').val(addrObj.address);
		form.find('#modal_addrCountry').val(addrObj.country);
		form.find('#modal_addressId').val(addrObj.addressId);
		form.find('#modal_addrUser').val($('#modal_addrUser').val());
	}

	function collectAddrForm(form, ajaxData) {
		ajaxData['companyName'] = form.find('#modal_addrCompany').val();
		ajaxData['city'] = form.find('#modal_addrCity').val();
		ajaxData['postalCode'] = form.find('#modal_addrPostalCode').val();
		ajaxData['address'] = form.find('#modal_addrAddress').val();
		ajaxData['country'] = form.find('#modal_addrCountry').val();
		ajaxData['addressId'] = form.find('#modal_addressId').val();
		ajaxData['userId'] = form.find('#modal_addrUser').val();
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

	$('button[name=edit_address]').bind('click', function() {
		openEditPopUp(this.id);
	});

	$('#add_address').bind('click', function() {
		var form = $('#modal_addrForm')[0];
		form.reset();
		$('#modal_addressId').val('');
	});

	$('#modal_addrForm').submit(function(event) {
		event.preventDefault();
		var ajaxData = {};
		collectAddrForm($('#modal_addrForm'), ajaxData);
		$.ajax({
			type: 'POST',
			url: '/address/modifyAddress',
			dataType: 'json',
			data: $.param(ajaxData),
			success: function(res) {
				if (res.success === 'true') {
					//update/add row
				} else {
					//error logic
				}
			}
		});
		$('#addressModal').modal('hide');
	});
});