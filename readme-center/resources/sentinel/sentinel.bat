cd  %~dp0
java  -Dserver.port=5020 -Dcsp.sentinel.dashboard.server=localhost:5020 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.5.jar
 