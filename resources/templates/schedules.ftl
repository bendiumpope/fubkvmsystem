<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@800&family=Roboto+Slab&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/64d58efce2.js" crossorigin="anonymous"></script>
    <title>Add Schedule</title>
    <link rel="stylesheet" href="static/css/home.css">
    <link rel="stylesheet" href="static/css/table.css">
    <link rel="stylesheet" href="static/css/modal.css">
</head>
<body>
    <div class="container">
        <nav>
            <ul>
                <#if user??>
                <li><a href="/">Home</a></li>
                <li><a href="/book">Book</a></li>
                <li><a href="/bookings">Bookings</a></li>
                <li><a href="#">Schedules</a></li>
                <#if user?? && (user.address =="admin" || user.address == "officer")>
                <li><a href="/addschedule">Add Schedule</a></li>
                </#if>
                <#if user?? && (user.address =="admin")>
                <li><a href="/users">Users</a></li>
                <li><a href="/active">Active Users</a></li>
                </#if>
                <li><a href="/records">Records</a></li>
                <li><a href="/signout">Sign Out</a></li>
                <#else>
                <li><a href="/signin">Sign In</a></li>
                </#if>
            </ul>
        </nav>

        <div class="logo">
            <img src="static/images/home/logo.png" alt="SounDAW">
        </div>

        <div class="menu-btn">
            +
        </div>

        <div class="table-header">
        <div style="overflow-y:auto;">
            <table class="content-table tb-color">
                <thead>
                    <tr>

                        <th>UserName</th>
                        <th>SurName</th>
                        <th>FirstName</th>
                        <th>Office</th>
                        <th>Available Date</th>
                        <th>Available Time</th>
                        <th>Date</th>
                    <#if user?? && (user.address =="admin" || user.address == "officer")>
                        <th>Edit</th>
                        <th>Delete</th>
                    </#if>
                    </tr>
                </thead>
                <#if schedules?? && (schedules?size > 0)>
                <tbody>
                    <#list schedules as schedule>
                    <tr>
                        <td>${schedule.userId}</td>
                        <td>${user.firstName}</td>
                        <td>${schedule.office}</td>
                        <td>${schedule.date}</td>
                        <td>${schedule.timeduration}</td>
                        <td>${schedule.datebooked}</td>

                        <#if user?? && (user.address =="admin" || user.address == "officer")>
                        <td>
                            <button onclick="document.getElementById('#${schedule.id}edit').style.display='block'" class="btn transparent">Edit</button>
                            <div id="#${schedule.id}edit" class="modal ">
                                <span  class="close" title="Close Modal">&times;</span>
                                <form method="post" action="/schedules" class="modal-content">
                                <input type="hidden" name="date" value="${date?c}">
                                <input type="hidden" name="code" value="${code}">
                                <input type="hidden" name="id" value="${schedule.id}">
                                <input type="hidden" name="action" value="edit">
                                <div class="container">
                                  <h1>Edit Schedule</h1>
                                  <p>Are you sure you want to Edit this Scheduled Appointment?</p>

                                  <div class="clearfix">
                                    <button type="button" class="btn transparent" onclick="document.getElementById('#${schedule.id}edit').style.display='none'">Cancel</button>
                                    <input type="submit" name="submit" value="Edit" class="btn transparent">
                                  </div>
                                </div>
                                </form>
                            </div>
                        </td>
                        <td>
                            <button onclick="document.getElementById('#${schedule.id}delete').style.display='block'" class="btn transparent">Delete</button>
                            <div id="#${schedule.id}delete" class="modal ">
                                <span  class="close" title="Close Modal">&times;</span>
                                <form method="post" action="/schedules" class="modal-content">
                                <input type="hidden" name="date" value="${date?c}">
                                <input type="hidden" name="code" value="${code}">
                                <input type="hidden" name="id" value="${schedule.id}">
                                <input type="hidden" name="action" value="delete">
                                <div class="container">
                                  <h1>Delete Schedule</h1>
                                  <p>Are you sure you want to Delete this Scheduled Appointment?</p>

                                  <div class="clearfix">
                                    <button type="button" class="btn transparent" onclick="document.getElementById('#${schedule.id}delete').style.display='none'">Cancel</button>
                                    <input type="submit" name="submit" value="Delete" class="btn transparent">
                                  </div>
                                </div>
                                </form>
                            </div>
                        </td>
                        </#if>
                    </tr>
                    </#list>
                </tbody>
                </#if>
            </table>
        </div>
        </div>
    </div>
    <script src="static/js/home.js"></script>
</body>
</html>