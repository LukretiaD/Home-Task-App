// function displayTaskDeadlineNotification(taskNotificationName, deadlineDate) {
//     const taskNotificatonName = task.TaskName;
//     const deadlineDate = task.EndDate;
//     const notification = document.createElement('a');
//     notification.className = 'notification - item';
//     notification.textContent = 'Task "${taskName}" is due on ${deadlineDate}';
//     const notificationContainer = document.getElementById('taskAssigned');


//     setTimeout(() => {
//         notificationContainer.removeChild(notification);
//     }, 5000);
// }

function notify() {
    Object.values(document.getElementById(
        'member').options).
       forEach(function (option) {
            alert("Value - " + option.value
                + ", Text - " + option.text);
            })
        };





