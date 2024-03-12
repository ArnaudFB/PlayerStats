# PlayerStats
<p align="center">
  <img src="https://datascientest.com/wp-content/uploads/2021/07/DIstribution-gaussienne-1024x563.png" alt="PlayerStats" style="height: 563px; width:1024px;"/>
</p>

## display PVP statistics for each player

This plugin is used to track each player's kill and death.<br>
This plugin supports PlaceholderAPI and IridiumColorAPI.<br>

## Compiling

You can compile the project using gradlew.<br>
Run `gradlew build` in console to build the project.<br>

## Setting-up database

You are free to set-up the database the way you like.<br>
All you have to do is to change database settings in `config.yml`.<br>
The database is only available within MySQL.<br>

## Available commands

/stats - Display your own statistics.<br>
/stats <player> - Display the targeted player statistics.<br>
/stats give <player> <kill/death> <amount> - Give the specified amount of kill/death to targeted player.<br>
/stats reset <player> - Reset the targeted player statistics.<br>
/stats reload - Reload plugin config file.<br>

## License

This plugin is licensed under GNU GPL v3.0<br>
This plugin was made as a project for ENSAI conception logicielle course.

