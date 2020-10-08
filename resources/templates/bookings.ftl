<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@800&family=Roboto+Slab&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/64d58efce2.js" crossorigin="anonymous"></script>
    <title>Bookings</title>
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
                <li><a href="#">Bookings</a></li>
                <li><a href="/schedules">Schedules</a></li>
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

        <div class="table-header" style="overflow-x:auto;">
            <table class="content-table tb-color">
                <thead>
                    <tr>
                        <th>User</th>
                        <th>Whom To Visit</th>
                        <th>Reason</th>
                        <th>Date To Visit</th>
                        <th>Time To Visit</th>
                        <th>Booking Status</th>
                        <th>Date Booked</th>
                        <#if user?? && (user.address =="admin" || user.address == "secretary")>
                        <th>Completed</th>
                        </#if>
                        <th>Edit</th>
                        <#if user?? && (user.address =="admin" || user.address == "secretary")>
                        <th>Delete</th>
                        </#if>
                    </tr>
                </thead>
                <#if booking?? && (booking?size > 0)>
                <tbody>
                <#list booking as booking>
                    <tr>
                        <td>${booking.userId}</td>
                        <td>${booking.whomtovisit}</td>
                        <td>${booking.visitreason}</td>
                        <td>${booking.visitingdate}</td>
                        <td>${booking.visittime}</td>
                        <td>${booking.bookingstatus}</td>
                        <td>${booking.date}</td>
                        <#if user?? && (user.address =="admin" || user.address == "secretary")>
                        <td>
                            <button onclick="document.getElementById('#${booking.id}completed').style.display='block'" class="btn transparent">Completed</button>
                            <div id="#${booking.id}completed" class="modal ">
                                <span  class="close" title="Close Modal">&times;</span>
                                <form method="post" action="/bookings" class="modal-content">
                                <input type="hidden" name="date" value="${date?c}">
                                <input type="hidden" name="code" value="${code}">
                                <input type="hidden" name="id" value="${booking.id}">
                                <input type="hidden" name="action" value="completed">
                                <div class="container">
                                  <h1>Completed Appointment</h1>
                                  <p>Are you sure ${booking.userId} has visited ${booking.whomtovisit}?</p>

                                  <div class="clearfix">
                                    <button type="button" class="btn transparent" onclick="document.getElementById('#${booking.id}completed').style.display='none'">Cancel</button>
                                    <input type="submit" name="submit" value="Complete" class="btn transparent">
                                  </div>
                                </div>
                                </form>
                            </div>
                        </td>
                        </#if>
                        <td>
                            <button onclick="document.getElementById('#${booking.id}edit').style.display='block'" class="btn transparent">Edit</button>
                            <div id="#${booking.id}edit" class="modal ">
                                <span  class="close" title="Close Modal">&times;</span>
                                <form method="post" action="/bookings" class="modal-content">
                                <input type="hidden" name="date" value="${date?c}">
                                <input type="hidden" name="code" value="${code}">
                                <input type="hidden" name="id" value="${booking.id}">
                                <input type="hidden" name="action" value="edit">
                                <div class="container">
                                  <h1>Edit Booking</h1>
                                  <p>Are you sure you want to Edit this Booked Appointment?</p>

                                  <div class="clearfix">
                                    <button type="button" class="btn transparent" onclick="document.getElementById('#${booking.id}edit').style.display='none'">Cancel</button>
                                    <input type="submit" name="submit" value="Edit" class="btn transparent">
                                  </div>
                                </div>
                                </form>
                            </div>
                        </td>
                        <#if user?? && (user.address =="admin" || user.address == "secretary")>
                        <td>
                            <button onclick="document.getElementById('#${booking.id}delete').style.display='block'" class="btn transparent">Delete</button>
                            <div id="#${booking.id}delete" class="modal ">
                                <span  class="close" title="Close Modal">&times;</span>
                                <form method="post" action="/bookings" class="modal-content">
                                <input type="hidden" name="date" value="${date?c}">
                                <input type="hidden" name="code" value="${code}">
                                <input type="hidden" name="id" value="${booking.id}">
                                <input type="hidden" name="action" value="delete">
                                <div class="container">
                                  <h1>Edit Booking</h1>
                                  <p>Are you sure you want to Delete this Booked Appointment?</p>

                                  <div class="clearfix">
                                    <button type="button" class="btn transparent" onclick="document.getElementById('#${booking.id}delete').style.display='none'">Cancel</button>
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
    <script src="static/js/modal.js"></script>
</body>
</html>

