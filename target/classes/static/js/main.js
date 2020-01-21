$(function(){

    const appendTask = function (data){
        var taskCode = '<a href="#" class="task-link" data-id="'+ data.id + '">' + data.name + '</a><br>';
        $('#task-list').append('<div>' + taskCode + '</div>');

    };

//    Loading tasks on load page
    $.get('/tasks/', function(response)
    {
        for(i in response) {
            appendTask(response[i]);
        }
    });

    //Show adding task form
    $('#show-add-task-form').click(function(){
        $('#add-task-form').css('display', 'flex');
    });

    //Closing adding task form
    $('#add-task-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    // Show all do form
    $('#do-list').click(function (){
        $('#all-do-form').css('display','flex');
    });

    // Close all do list
    $('#all-do-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });


    //Getting task
    $(document).on('click', '.task-link', function(){
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/tasks/' + taskId,
            success: function(response)
            {
                var code = '<span>' + response.description + '</span><br>'
                           + '<button id="delete-task" data-id="'+ taskId +'">Удалить</button>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Задача не найдена!');
                }
            }
        });
        return false;
    });

    // Deleting task

    $('#delete-task').click(function () {
            var link = $(this);
            var taskID = link.data('taskId');
           // console.log(taskID);
            s.ajax({
               method: "DELETE",
               url: 'tasks' + taskID
            });
    });


    //Adding task
    $('#save-task').click(function()
    {
        var data = $('#add-task-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tasks/',
            data: data,
            success: function(response)
            {
                $('#add-task-form').css('display', 'none');
                var task = {};
                task.id = response;
                var dataArray = $('#add-task-form form').serializeArray();
                for(i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);
            }
        });
        return false;
    });

    //Clear list
    $('#clear-list').click(function () {
        $.ajax({
            method: "DELETE",
            url: '/tasks/all',
            caches: false
        });
        $('form[name=myForm]').trigger('reset');
    });
});