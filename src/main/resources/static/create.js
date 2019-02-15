$('#create-submit').on('click', createToDo);

function createToDo() {
    var description = $('#create-description').val();
    if (!description.trim()) {
        alert('내용을 입력해주세요');
        return;
    }
    var todo = {'description': description, 'references': [], 'status': 'open'};
    $.ajax({
        url: '/todos',
        type: 'POST',
        contentType: 'application/json;utf-8',
        data: JSON.stringify(todo),
        success: function () {
            alert('처리되었습니다.');
            $('#delete-modal').modal('hide');
            location.href = '/todos';
        },
        error: function () {
            console.log(arguments);
            alert('처리에 실패 했습니다.');
        }
    });
}