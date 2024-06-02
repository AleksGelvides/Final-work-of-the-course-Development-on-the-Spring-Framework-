# Final-work-of-the-course-Development-on-the-Spring-Framework

### Развертывание:
1) Запустите скрипт `./dockerStart.sh` (Обратите внимание, dockerhub больше не доступен на территории нашей страны. VPN решает проблему)
2) Запустите сервис.

По дефолту в приложении создаются 2 пользователя со следующими правами: </br>
`admin/admin - ROLE_ADMIN, ROLE_USER` </br>
`user/user - ROLE_USER` </br>

Описание и взаимодействие с API: </br>
[swagger](http://localhost:8080/v3/api-docs) или [файлик для постмана](api-docs.json)