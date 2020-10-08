<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@800&family=Roboto+Slab&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/64d58efce2.js" crossorigin="anonymous"></script>
    <title>Update Form</title>
    <link rel="stylesheet" href="static/css/signin.css">
    <link rel="stylesheet" href="static/css/book.css">
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
    <div class="container">
        <div class="forms-container">
            <div class="signin-signup">
                <form action="/updateuser" method="post" class="sign-in-form">
                     <input type="hidden" name="date" value="${date?c}" />
                     <input type="hidden" name="code" value="${code}" />
                     <input type="hidden" name="action" value="update" />
                     <input type="hidden" name="id" value="${id}" />
                    <h2 class="title">Update User Details</h2>

                    <div class="input-field2">
                        <i class="fas fa-user"></i>
                        <input type="text" name="userName" placeholder="Username" value="${updateUser.userId}" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-user"></i>
                        <input type="text" name="surName" placeholder="Surname" value="${updateUser.surName}" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-user"></i>
                        <input type="text" name="firstName" placeholder="First Name" value="${updateUser.firstName}" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-envelope"></i>
                        <input type="email" name="emailAddress" placeholder="Email Address" value="${updateUser.emailAddress}" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-phone"></i>
                        <input type="text" name="phoneNumber" placeholder="Phone Number" value="${updateUser.phoneNumber}" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-home"></i>
                        <input type="address" name="address" placeholder="Address" value="${updateUser.address}" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-user"></i>
                        <input type="text" name="role" placeholder="Role" value="${updateUser.role}" required>
                    </div>

                        <input type="hidden" name="passwordHash" placeholder="Password Hash" value="${updateUser.passwordHash}" required>


                    <input type="submit" value="Update" class="btn solid">

                </form>
            </div>
        </div>

        <div class="panels-container">
            <div class="panel left-panel">
                <div class="content">
                    <h3>New here ?</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Expedita esse fugit laboriosam?</p>
                    <button class="btn transparent" id=""><a href="/users">Users</a></button>
                </div>

                <img src="static/images/signin/log.svg" class="image" alt="">
            </div>
        </div>
    </div>

    <script src="static/js/home.js"></script>
</body>
</html>