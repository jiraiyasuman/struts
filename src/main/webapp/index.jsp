
<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Management System</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f3f6fb;
            color: #243247;
        }

        header {
            background: #173b67;
            color: white;
            padding: 22px 8%;
        }

        header h1 {
            margin: 0;
            font-size: 25px;
        }

        main {
            min-height: 75vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 30px 20px;
        }

        .welcome-card {
            width: 100%;
            max-width: 620px;
            background: white;
            padding: 45px 35px;
            border-radius: 12px;
            text-align: center;
            box-shadow: 0 8px 28px rgba(0, 0, 0, 0.08);
        }

        .welcome-card h2 {
            margin-top: 0;
            font-size: 30px;
        }

        .welcome-card p {
            color: #637083;
            line-height: 1.7;
        }

        .btn {
            display: inline-block;
            margin-top: 18px;
            padding: 13px 25px;
            background: #1769e0;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }

        .btn:hover {
            background: #0d4fae;
        }

        footer {
            text-align: center;
            padding: 20px;
            color: #6c7788;
        }
    </style>
</head>

<body>
    <header>
        <h1>Employee Management System</h1>
    </header>

    <main>
        <section class="welcome-card">
            <h2>Welcome!</h2>
            <p>
                Manage your employee records in one place.
                Create new employees, view existing records,
                update details and delete records when necessary.
            </p>

            <a class="btn"
               href="<%= request.getContextPath() %>/employees">
                Manage Employees
            </a>
        </section>
    </main>

    <footer>
        Employee Management System &copy; 2026
    </footer>
</body>
</html>
