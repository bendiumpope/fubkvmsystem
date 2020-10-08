<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@800&family=Roboto+Slab&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/64d58efce2.js" crossorigin="anonymous"></script>
    <title>Active Users</title>
    <link rel="stylesheet" href="static/css/home.css">
    <link rel="stylesheet" href="static/css/table.css">
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
                <li><a href="#">Active Users</a></li>
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

        <div class="table-header" style="overflow-x:auto;">
            <table class="content-table tb-color">
                <thead>
                    <tr>
                        <th>UserName</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <#list actives as actives>
                    <tr>
                        <td>${actives.userId}</td>
                        <td>${actives.status}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>

    </div>
    <script src="static/js/home.js"></script>
</body>
</html>

