<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <meta name="author" content="Team89 (C) 2022">
    <meta name="description" content="Software Engineering 1 (SE1)">
    <meta name="license" content="The MIT License (MIT)">
    <meta name="theme-color" content="#239BD1"/>
    <meta property="og:title" content="Software Engineering 1 Project(SE1)">
    <meta property="og:description" content="Bachelor Course Software Engineering 1 (SE1), Hochschule Bonn-Rhein-Sieg.">
    <link rel="shortcut icon" href="https://kaul.inf.h-brs.de/favicon.ico" />
    <title>Tomcat Parkhaus</title>
    <script src="https://kaul.inf.h-brs.de/ccmjs/mkaul-components/parkhaus/versions/ccm.parkhaus-11.2.0.js"></script>
    <style>
        * {
            font-family: sans-serif, Helvetica, Arial;
        }
        .grey-background {
            background-color: lightgrey;
        }
        .center {
            text-align: center;
        }
        .box {
            border: thin solid black;
            margin: 0.5rem 0;
            padding: 1rem;
        }
        .lightblue {
            background-color: #d0ebf6;
        }
        .lightyellow {
            background-color: lightgoldenrodyellow;
        }
        .lightgreen {
            background-color: #ccfdcc;
        }
    </style>
</head>
<body>
<div class="box center grey-background">
    <h1>Parkhaus Team89</h1>
    <p>Tomcat Version : <%= application.getServerInfo() %></p>
</div>
<div class="box lightblue">
    <h2><a href="kasse.jsp">Kasse</a></h2>
</div>
<div class="box lightyellow">
    <h1>Parkhaus Frauenparkplätze</h1>
    <ccm-parkhaus-11-2-0 server_url="./level1-servlet"
                         debug="true"
                         name="Etage1"
                         license_max="15"
                         extra_buttons='["sum","min"]'
                         client_categories='["Frau"]'
                         space_color='{"1":"pink"}'
                         vehicle_types='["PKW","SUV"]'
                         price_factor='{"SUV":2,"Family":0.5}'
                         max="11">
    </ccm-parkhaus-11-2-0>
</div>
<div class="box lightgreen">
    <h1>Parkhaus Etage 2</h1>
    <ccm-parkhaus-11-2-0 server_url="./level2-servlet"
                         name="Etage2"
                         license_min="16"
                         license_max="30"
                         client_categories='["any","Business"]'
                         space_color='{"1":"yellow","6":"black"}'
                         vehicle_types='["PKW","SUV"]'
                         price_factor='{"SUV":2,"Business":1}'
                         debug="true"
                         hide_table="true"
                         SALT="456">
    </ccm-parkhaus-11-2-0>
</div>
</body>
</html>