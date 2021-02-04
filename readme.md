# Library

## Documentation:  
[SRS(RU)](https://github.com/Virotor/Library/blob/master/SRS/SRS.md) 

# Инструкция по запуску
## Установка БД
1. Скачать и установить MS SQL Server 2014 или более поздний.
2. Восстановить БД. Файл лежит в папке БД

## Установка intellij idea
1. Скачать intellij idea и установить
2. Скачать JRE, JDK и установить их
3. Скачать JavaFX и настроить его для проекта https://www.jetbrains.com/help/idea/javafx.html

## Подключение БД к проекту
1. https://www.jetbrains.com/help/idea/db-tutorial-connecting-to-ms-sql-server.html#step-3-connect-to-microsoft-sql-server-with-datagrip
2. Поменять в классе DatabaseConnector в методе connectToDatabase URL название SQL сервера и порт."jdbc:sqlserver://localhost\\[Название SQL Server]:[Порт для работы];database=Library;integratedSecurity=true;"

## Данные для авторизации в приложении
Логин - Inna
Пароль - 22031995
