let table = document.getElementById('customer_list');

$(table).on('click', '#delete_element_button', function () {
    $(this).closest('tr').remove();
});
