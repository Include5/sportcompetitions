<html>
<meta charset="UTF-8">

<head>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <title>Index</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        a {
            width: 100%;
            position: relative;
            font-size: 14px;
            line-height: 20px;
            justify-content: center;
            border-radius: 6px;
            transition-property: color, background-color, border-color, text-decoration-color, fill, stroke, opacity, box-shadow, transform, filter, backdrop-filter;
            transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
            transition-duration: 150ms;
            text-align: center;
            align-items: center;
            font-weight: 500;
            display: inline-flex;
            white-space: nowrap;
            vertical-align: middle;
            user-select: none;
            padding: 5px 16px;
            letter-spacing: 0.025em;

        }

        a {
            border: 1px solid rgba(240, 246, 252, 0.1);
            background-color: #21262d;
            color: #c9d1d9;
        }

        a:hover {
            background-color: #30363d;
            border-color: #8b949e;
        }

        h2 {
            display: block;
            font-weight: 500;
            cursor: default;
            margin-bottom: 6px;
        }
    </style>
</head>

<body class="bg-[#0d1117] text-[#c9d1d9] text-sm leading-[1.5] break-words flex justify-center h-screen">
    <div class="lg:px-8 md:px-6 px-4 my-6 max-w-md w-full mx-auto block space-y-6">
        <h2>Автоматизацию и хранения данных о спортивных мероприятиях</h2>
        <a href="/members">Участники</a>
        <br />
        <a href="/teams">Команды</a>
        <br />
        <a href="/disciplines">Дисциплины</a>
        <br />
        <a href="/competitions">Спортивные мероприятия</a>
    </div>
</body>

</html>