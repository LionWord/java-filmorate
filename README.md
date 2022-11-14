# java-filmorate

Ссылка на диаграмму - https://app.quickdatabasediagrams.com/#/d/jrF5Ic

Примеры запросов:

Все ужастики:

SELECT f.film_name
FROM Genres_Of_Films AS gof
JOIN Film AS f ON f.film_id=gof.film_id
WHERE genre_id = (id ужастика в таблице genres)

ТОП-5 самых популярных фильмов:

SELECT f.film_name
FROM Users_Liked_Film AS ulf
JOIN Film AS f ON f.film_id=ulf.film_id
GROUP BY film_id
ORDER BY COUNT(user_id) DESC
LIMIT 5

Список всех друзей пользователя:

SELECT u.user_name
FROM Users_Relations AS ur
JOIN User AS u ON u.user_id=ur.second_user_id
WHERE first_user_id = (id пользователя)
