### curl samples (application deployed at application context `topjava`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/topjava/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -s http://localhost:8080/topjava/rest/admin/users/100001 --user admin@gmail.com:admin`

#### register Users
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/profile/register`

#### register User with duplicated email
`curl -s -i -X POST -d '{"name":"New User","email":"user@yandex.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/profile/register/ --user admin@gmail.com:admin`

#### update User with duplicated email
`curl -s -i -X PUT -d '{"name":"New User","email":"user@yandex.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/profile --user admin@gmail.com:admin`

#### get Profile
`curl -s http://localhost:8080/topjava/rest/profile --user test@mail.ru:test-password`

#### get All Meals
`curl -s http://localhost:8080/topjava/rest/profile/meals --user user@yandex.ru:password`

#### get Meals 100003
`curl -s http://localhost:8080/topjava/rest/profile/meals/100003  --user user@yandex.ru:password`

#### filter Meals
`curl -s "http://localhost:8080/topjava/rest/profile/meals/filter?startDate=2020-01-30&startTime=07:00:00&endDate=2020-01-31&endTime=11:00:00" --user user@yandex.ru:password`

#### get Meals not found
`curl -s -v http://localhost:8080/topjava/rest/profile/meals/100008 --user user@yandex.ru:password`

#### delete Meals
`curl -s -X DELETE http://localhost:8080/topjava/rest/profile/meals/100002 --user user@yandex.ru:password`

#### create Meals
`curl -s -X POST -d '{"dateTime":"2020-02-01T12:00","description":"Created lunch","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/profile/meals --user user@yandex.ru:password`

#### update Meals
`curl -s -X PUT -d '{"dateTime":"2020-01-30T07:00", "description":"Updated breakfast", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100003 --user user@yandex.ru:password`

#### create Meal with duplicate datetime
`curl -s -X POST -d '{"dateTime":"2020-01-31T21:00", "description":"meal with duplicate datetime", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals --user admin@gmail.com:admin`

#### update Meal with not valid field
`curl -s -X PUT -d '{"dateTime":"", "description":"not valid meal", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100010 --user admin@gmail.com:admin`

#### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/admin/users --user admin@gmail.com:admin`
