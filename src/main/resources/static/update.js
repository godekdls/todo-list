$('.status-btn').on('click', patchStatus);
$('.update-btn').on('click', showUpdateModal);
$('#update-submit').on('click', updateToDo);


function patchStatus() {
    var id = $(this).attr('data-id');
    var status = $(this).attr('data-status');
    if (status === 'open') {
        status = 'closed';
    } else {
        status = 'open';
    }
    var todo = {'id': id, 'status': status};
    $.ajax({
        url: '/todos/' + id,
        type: 'PATCH',
        contentType: 'application/json;utf-8',
        data: JSON.stringify(todo),
        success: function () {
            alert('처리되었습니다.');
            $('#delete-modal').modal('hide')
            location.reload();
        },
        error: function () {
            console.log(arguments);
            alert('처리에 실패 했습니다.');
        }
    });
}

function showUpdateModal() {
    var id = $(this).attr('data-id');
    var description = $(this).attr('data-description');
    var status = $(this).attr('data-status');
    var references = $(this).attr('data-references');
    var referenceDesc = '';
    if (references) {
        $.each(JSON.parse(references), function (index, reference) {
            if (index > 0) {
                referenceDesc += ' ';
            }
            referenceDesc += ('@' + reference);
        })
    }
    $('#update-description').val(description);
    $('#update-references').val(referenceDesc);
    $('#update-references').attr('data-references', references);
    $('#update-id').val(id);
    $('#update-status').val(status);
    $('#update-modal').modal('show')
}

function updateToDo() {
    var id = $('#update-id').val();
    var description = $('#update-description').val();
    if (!description.trim()) {
        alert('내용을 입력해주세요');
        return;
    }
    var reference = $('#update-references').val();
    if (reference.trim()) {
        reference = reference.replace(/(\s*)/g, "");
        refArray = reference.split("@").filter(ref => ref);
        var isValid = true;
        $.each(refArray, function (index, ref) {
            if (isNaN(ref)) {
                alert('참조 형식이 잘못되었습니다.');
                isValid = false;
            }
        });
        if (!isValid) {
            return;
        }
        reference = refArray.map(i => Number(i));
    } else {
        reference = []
    }
    var status = $('#update-status').val();
    var todo = {'id': id, 'description': description, 'references': reference, 'status': status};

    $.ajax({
        url: '/todos/' + id,
        type: 'PUT',
        contentType: 'application/json;utf-8',
        data: JSON.stringify(todo),
        success: function () {
            alert('처리되었습니다.');
            $('#update-modal').modal('hide')
            location.reload();
        },
        error: function () {
            console.log(arguments);
            alert('처리에 실패 했습니다.');
        }
    });
}