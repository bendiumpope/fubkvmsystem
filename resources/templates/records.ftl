<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@800&family=Roboto+Slab&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/64d58efce2.js" crossorigin="anonymous"></script>
    <title>Visit Records</title>
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
                <li><a href="/users">Users</a></li>
                <li><a href="/active">Active Users</a></li>
                </#if>
                <li><a href="#">Records</a></li>
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

        <div class="table-header" style="overflow-x:auto;">
            <table class="content-table tb-color">
                <thead>
                    <tr>
                    <#if user?? && (user.address =="admin")>
                        <th>UserName</th>
                    </#if>
                        <thName</th>
                        <th>Visit Date</th>
                        <th>Visit Time</th>
                        <th>Visited</th>
                        <#if user?? && (user.address =="admin")>
                        <th>Delete</th>
                        </#if>
                    </tr>
                </thead>
                <#if visitrecords?? && (visitrecords?size > 0)>
                <tbody>
                    <#list visitrecords as visitrecord>
                    <td>${visitrecord.userId}</td>
                    <td>${visitrecord.date}</td>
                    <td>${visitrecord.timein}</td>
                    <td>${visitrecord.visitedwhom}</td>
                    <#if user?? && (user.address =="admin")>
                    <td>
                            <button onclick="document.getElementById('#${visitrecord.id}delete').style.display='block'" class="btn transparent">Delete</button>
                            <div id="#${visitrecord.id}delete" class="modal ">
                                <span  class="close" title="Close Modal">&times;</span>
                                <form method="post" action="/records" class="modal-content">
                                <input type="hidden" name="date" value="${date?c}">
                                <input type="hidden" name="code" value="${code}">
                                <input type="hidden" name="id" value="${visitrecord.id}">
                                <input type="hidden" name="action" value="delete">
                                <div class="container">
                                  <h1>Delete User</h1>
                                  <p>Are you sure you want to Delete this Record?</p>

                                  <div class="clearfix">
                                    <button type="button" class="btn transparent" onclick="document.getElementById('#${visitrecord.id}delete').style.display='none'">Cancel</button>
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
    <script src="static/js/home.js"></script>
</body>
</html>

