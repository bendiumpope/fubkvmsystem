<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@800&family=Roboto+Slab&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/64d58efce2.js" crossorigin="anonymous"></script>
    <title>Users</title>
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
                <li><a href="/schedules">Schedules</a></li>
                <#if user?? && (user.address =="admin" || user.address == "officer")>
                <li><a href="/addschedule">Add Schedule</a></li>
                </#if>
                <#if user?? && (user.address =="admin")>
                <li><a href="#">Users</a></li>
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
                        <th>Email</th>
                        <th>Phone No.</th>
                        <th>Role</th>
                        <th class="res-hide">Address</th>
                        <th class="res-hide" >Password Hash</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                <#list users as users>
                    <tr>
                        <td>${users.userId}</td>
                        <td>${users.surName}</td>
                        <td>${users.firstName}</td>
                        <td>${users.emailAddress}</td>
                        <td>${users.phoneNumber}</td>
                        <td class="res-hide">${users.address}</td>
                        <td>${users.role}</td>
                        <td class="res-hide">${users.passwordHash}</td>
                        <td>
                            <button onclick="document.getElementById('#${users.userId}edit').style.display='block'" class="btn transparent">Edit</button>
                            <div id="#${users.userId}edit" class="modal ">
                                <span  class="close" title="Close Modal">&times;</span>
                                <form method="post" action="/users" class="modal-content">
                                <input type="hidden" name="date" value="${date?c}">
                                <input type="hidden" name="code" value="${code}">
                                <input type="hidden" name="id" value="${users.userId}">
                                <input type="hidden" name="action" value="edit">
                                <div class="container">
                                  <h1>Edit Schedule</h1>
                                  <p>Are you sure you want to Edit ${users.firstName} Account Details?</p>

                                  <div class="clearfix">
                                    <button type="button" class="btn transparent" onclick="document.getElementById('#${users.userId}edit').style.display='none'">Cancel</button>
                                    <input type="submit" name="submit" value="Edit" class="btn transparent">
                                  </div>
                                </div>
                                </form>
                            </div>
                        </td>
                        <td>
                            <button onclick="document.getElementById('#${users.userId}delete').style.display='block'" class="btn transparent">Delete</button>
                            <div id="#${users.userId}delete" class="modal ">
                                <span  class="close" title="Close Modal">&times;</span>
                                <form method="post" action="/users" class="modal-content">
                                <input type="hidden" name="date" value="${date?c}">
                                <input type="hidden" name="code" value="${code}">
                                <input type="hidden" name="id" value="${users.userId}">
                                <input type="hidden" name="action" value="delete">
                                <div class="container">
                                  <h1>Delete User</h1>
                                  <p>Are you sure you want to Delete ${users.firstName}?</p>

                                  <div class="clearfix">
                                    <button type="button" class="btn transparent" onclick="document.getElementById('#${users.userId}delete').style.display='none'">Cancel</button>
                                    <input type="submit" name="submit" value="Delete" class="btn transparent">
                                  </div>
                                </div>
                                </form>
                            </div>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            </div>
        </div>

    </div>
    <script src="static/js/home.js"></script>
</body>
</html>

