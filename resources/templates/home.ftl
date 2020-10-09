<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@800&family=Roboto+Slab&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="static/css/home.css">
    <title>SounDAW</title>
</head>
<body>
    <div class="container">
        <nav>
            <ul>
                <#if user??>
                <li><a href="#">Home</a></li>
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

        <header>
            <h1>VISITORS SYSTEM</h1>
        </header>

        <section class="section-follow">
            <h3 class="follow-me">Follow Us On</h3>
            <div class="social-media">
                <a href="https://www.facebook.com/amabendo"><i class="fab fa-facebook"></i></a>
                <a href="https://twitter.com/dikachiben"><i class="fab fa-twitter-square"></i></a>
                <a href="https://www.instagram.com/amabenedict/?hl=en"><i class="fab fa-instagram"></i></a>
                <i class="fab fa-spotify"></i>
            </div>
        </section>

        <section>
            <div class="description">
                <h3>Music Programming</h3>
                <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                    Provident sequi sapiente assumenda quo magni saepe maiores necessitatibus non,
                    quas inventore quaerat autem culpa cupiditate id numquam tempora dicta a quam!</p>
            </div>
            <div class="image">
                <img src="static/images/home/musicprogramming.jpg" alt="Music Programming">
            </div>
        </section>

        <section>
            <div class="description">
                <h3>Vocals Recording</h3>
                <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                    Provident sequi sapiente assumenda quo magni saepe maiores necessitatibus non,
                    quas inventore quaerat autem culpa cupiditate id numquam tempora dicta a quam!</p>
            </div>
            <div class="image">
                <img src="static/images/home/vocals2.jpg" alt="Vocals Recording">
            </div>
        </section>

        <section>
            <div class="description">
                <h3>Mixing & Mastering</h3>
                <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                    Provident sequi sapiente assumenda quo magni saepe maiores necessitatibus non,
                    quas inventore quaerat autem culpa cupiditate id numquam tempora dicta a quam!</p>
            </div>
            <div class="image">
                <img src="static/images/home/mixing.jpg" alt="Mixing & Mastering">
            </div>
        </section>

        <footer>
            SounDAW &copy; 2020
        </footer>

    </div>

    <script src="static/js/home.js"></script>

</body>
</html>
