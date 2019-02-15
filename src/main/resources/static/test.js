$('body').on('click', '.update-btn', function(event) {
    event.preventDefault();

    var id = $(this).attr('data-id');
    var description = $(this).attr('data-description');
    $('#update-description').val(description);
    $('#update-modal').modal('show')
    var status = $(this).attr('data-status');
});