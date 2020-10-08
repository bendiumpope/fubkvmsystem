<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/64d58efce2.js" crossorigin="anonymous"></script>
    <title>Sign in & Sign Up Form</title>
    <link rel="stylesheet" href="static/css/signin.css">
</head>
<body>
    <div class="container">
        <div class="forms-container myDIV">
            <div class="signin-signup">
                <form action="/signin" method="post" class="sign-in-form">
                    <input type="hidden" name="action" value="add">
                    <h2 class="title">Sign in</h2>
                    <#if error??>
                        ${error}<br>
                    </#if>

                    <div class="input-field">
                        <i class="fas fa-user"></i>
                        <input type="text" name="userName" id="userName" placeholder="Username" required>
                    </div>

                    <div class="input-field">
                        <i class="fas fa-lock"></i>
                        <input type="password" name="password" id="password" placeholder="Password" required>
                    </div>
                    <input type="submit" name="submit" id="contact-submit" value="Login" class="btn solid">

                    <p class="social-text">Or Sign in with social platforms</p>

                    <div class="social-media">
                        <a href="#" class="social-icon">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a href="#" class="social-icon">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a href="#" class="social-icon">
                            <i class="fab fa-google"></i>
                        </a>
                        <a href="#" class="social-icon">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                    </div>
                </form>

                <form action="/signup" method="post" class="sign-up-form">
                    <input type="hidden" name="action" value="add">
                    <h2 class="title">Sign up</h2>
                    <#if error??>
                        ${error}<br>
                    </#if>
                    <div class="input-field2">
                        <i class="fas fa-user"></i>
                        <input type="text" name="userName" placeholder="Username" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-user"></i>
                        <input type="text" name="surName" placeholder="Surname" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-user"></i>
                        <input type="text" name="firstName" placeholder="First Name" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-envelope"></i>
                        <input type="email" name="emailAddress" placeholder="Email Address" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-phone"></i>
                        <input type="text" name="phoneNumber" placeholder="Phone Number" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-home"></i>
                        <input type="address" name="address" placeholder="Address" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-lock"></i>
                        <input type="password" name="password" placeholder="Password" required>
                    </div>

                    <div class="input-field2">
                        <i class="fas fa-lock"></i>
                        <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
                    </div>

                    <input type="submit" name="submit" id="contact-submit" value="Sign up" class="btn solid">

                    <p class="social-text">Or Sign up with social platforms</p>

                    <div class="social-media">
                        <a href="#" class="social-icon">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a href="#" class="social-icon">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a href="#" class="social-icon">
                            <i class="fab fa-google"></i>
                        </a>
                        <a href="#" class="social-icon">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <div class="panels-container">
            <div class="panel left-panel">
                <div class="content">
                    <h3>New here ?</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Expedita esse fugit laboriosam?</p>
                    <button class="btn transparent" id="sign-up-btn">Sign up</button>
                </div>

                <img src="static/images/signin/log.svg" class="image" alt="">
            </div>

            <div class="panel right-panel">
                <div class="content">
                    <h3>One of us ?</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Expedita esse fugit laboriosam?</p>
                    <button class="btn transparent" id="sign-in-btn">Sign in</button>
                </div>

                <img src="static/images/signin/register.svg" class="image" alt="">
            </div>
        </div>
    </div>

    <script src="static/js/signin.js"></script>
</body>
</html>