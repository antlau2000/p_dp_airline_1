<h2>Описание проекта "Авиакомпания"</h2>

- [Summary](#summary)
- [Stack](#stack)
- [MVP](#mvp)
- [Backlog](#backlog)
- [Структура проекта](#структура-проекта)
    - [Бэкенд](#бэкенд)
    - [Фронтенд](#фронтенд)
- [Работа на проекте](#работа-на-проекте)
    - [С чего начинать](#с-чего-начинать)
    - [О таскборде](#о-таскборде)
    - [Как выполнять задачи](#как-выполнять-задачи)
    - [Проверка задач](#проверка-задач)
    - [Требования к коду](#требования-к-коду)
    - [Созвоны по проекту](#созвоны-по-проекту)


### Summary

Реализуем функционал авиакомпании на основе прототипа - [S7 Airlines](https://s7.ru/ru).

Студент находится на Проекте от одного месяца для полутора.

В конце Проекта студент сдает короткое ревью по [Kubernetes](./guides/guide_kubernetes.md) ментору Проекта.

### Stack

Проект пишется на базе `Java 11`, `Spring Boot 2`, `Kubernetes` и микросервисной архитектуре. Работаем с базой данных `PostgreSQL` через `Hibernate`.

Чтобы не писать boilerplate-код, используем на проекте [Lombok](https://projectlombok.org/features/all).

Все контроллеры и их методы нужно сразу описывать аннотациями [Swagger](https://docs.swagger.io/swagger-core/v1.5.0/apidocs/allclasses-noframe.html).
Swagger UI при запущенном приложении крутится [здесь](http://localhost:8080/swagger-ui/).

Таск-борд находится прямо на [Gitlab](https://gitlab.com/de.pronin/p_dp_airline_1/-/boards).

Dev-stand будем поднимать и разворачивать через Minikube.

### MVP

[MVP](https://ru.wikipedia.org/wiki/%D0%9C%D0%B8%D0%BD%D0%B8%D0%BC%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE_%D0%B6%D0%B8%D0%B7%D0%BD%D0%B5%D1%81%D0%BF%D0%BE%D1%81%D0%BE%D0%B1%D0%BD%D1%8B%D0%B9_%D0%BF%D1%80%D0%BE%D0%B4%D1%83%D0%BA%D1%82) - API (полностью описанное в Swagger), которое будет уметь продавать, менять, возвращать авиабилеты.
Работать с таким API можно будет через веб-интерфейс Swagger и Postman.

### Backlog

Фичи:
<ul>
<li>диверсификация перевозок (багаж, животные, oversized-вещи, грузы)</li>
<li>создание личного кабинета пассажира, добавление аутентификации через логин/пароль, Google, социальные сети</li>
<li>реализация функционала обратной связи с пассажиром через e-mail и Telegram</li>
<li>создание личного кабинета администратора</li>
<li>внедрение бонусной системы (мили), кэшбека и акций</li>
<li>внедрение проверок пассажиров по стоп-листам - общение с микросервисом "МВД"</li>
<li>добавление смежных сервисов - подбора гостиниц, аренды квартир, тренферов, каршеринга, экскурсий. Все - микросервисы.</li>
</ul>

Импрувменты:

<ul>
<li>логирование через Slf4j + log4j2</li>
<li>юнит-тесты и интеграционные тесты</li>
<li>анализ качества кода через SonarQube</li>
</ul>


## Структура проекта
### Бэкенд

Проект основан на архетипе webapp.
Слои:
<ul>
<li><code>config</code> конфигурационные классы, в т.ч. Spring Security, инструменты аутентификации</li>
<li><code>entities</code> сущности базы данных</li>
<li><code>repositories</code> dao-слой приложения, реализуем в виде интерфейсов Spring Data, имплементирующих JpaRepository.</li>
<li><code>services</code> бизнес-логика приложения, реализуем в виде интерфейсов и имплементирующих их классов.</li>
<li><code>controllers</code> обычные и rest-контроллеры приложения.</li>
<li><code>util</code> пакет для утилитных классов: валидаторов, шаблонов, хэндлеров.</li>
</ul>

### Фронтенд
Параллельно с нами есть команда фронтов, которая пишет фронт для наших API, поэтому нам не приходится писать вьюшки. Посмотреть на макеты, по которым фронты реализуют свой код с помощью наших апишек, можно [тут](https://www.figma.com/file/E1CJkCGFZSNeskU6jMWtRt/Airways?type=design&node-id=0%3A1&t=3PsFo7ns1gOnm8l1-1).

Update: Возможно, нагенерим свой фронт с помощью GPT.

## Работа на проекте
### С чего начинать

0. Доступы. Если ты читаешь это, значит доступ к проекту у тебя уже есть :)
<ol>
<li>загрузи проект себе в среду разработки</li>
<li>изучи весь проект - начни с pom, properties файлов и конфигурационных классов</li>
<li>изучи гайд guide_docker из директории guides</li>
<li>если на предыдущем шаге не получилось создать контейнер с БД, то создай локальную базу данных с названием <code>airline_db</code></li>
<li>добейся успешного запуска проекта. <a href="http://localhost:8080/"> Проверить</a>.</li>
<li>изучи <a href="https://gitlab.com/de.pronin/p_dp_airline_1/-/boards">таск-борд</a>
<li>изучи гайд guide_kubernetes.md из директории guides</li>
<li>ознакомься с остальными гайдами из директории guides</li>
</ol>

### О таскборде

Таск-борд строится по принципу Kanban - он разделён на столбцы, каждый из которых соответствует определённому этапу работы с задачей:
<ul>
<li><code>Backlog</code> задачи на <b>новый функционал</b>, выполнение которых отложено неопределенный срок</li>
<li><code>TODO</code> задачи, требующие выполнения</li>
<li><code>In Progress</code> выполняемые в данный момент задачи</li>
<li><code>Cross-review</code> задачи на этапе перекрёстной проверки студентами</li>
<li><code>Final Review</code> задачи на проверке у техлида</li>
<li><code>Done</code> выполненные задачи</li>
</ul>


### Как выполнять задачи

- напиши ментору, что тебе нужна задача, либо сам в графе <code>TODO</code> на таск-борде выбери карточку с задачей и напиши ментору о том, что хочешь ее взять
- загрузи себе последнюю версию ветки <code>master</code> 
- создай от <code>master</code> свою собственную ветку для выполнения взятой задачи. Свою ветку назови так, чтобы было понятно, чему посвящена задача. В начале имени ветки проставь номер задачи с Gitlab. Например, <code>313_adding_new_html_pages</code>
- выполни задачу, оттестируй и, если всё ок, залей её в репозиторий проекта
- создай на своей ветке merge request (pull request), в теле реквеста укажи <code><i>#здесь-номер-таски суть-задачи-в-двух-словах"</i></code>. Например, <code>#313 my awesome feature</code>
- обязательно добавьте вашу задачу в [CHANGELOG](./CHANGELOG.MD), в раздел Unreleased в заданном стиле: коротко и человекочитаемо
- перенеси задачу в столбец <code>Cross-review</code>

### Проверка задач

На этапе кросс-ревью студенты проверяют задачи, выполненные друг другом.
В случае, если к коду есть замечания, проверяющий как можно более подробно описывает их в комментарии к коду в самом реквесте.
Если к коду претензий нет, проверяющий студент ставит к карточке лайк.

**Каждая карточка (студенческая задача) должна быть проверена как минимум 2 другими студентами и одобрена ими (т.е. собрать не менее 2 лайков) на этапе `Cross-review`.**

Только после этого карточку можно переносить в столбец `Final Review`.

Затем код проверяет тимлид (ментор) и в случае обнаружения ошибок переносит её в столбец `In Progress`.
Если всё ок - merge request принимается, ветка студента сливается с основной веткой проекта, а карточка переносится в столбец `Done`.

Примечание: если ментор перенес задачу из `Final Review` в `In Progress`, то после исправления оставленных менторов замечаний повторно проходить `Cross-review` не надо.

### Требования к коду

- сделайте себе понятные никнеймы (имя + фамилия) в Git. Не хочу гадать, кто, где и что писал.
- в REST-контроллерах пользоваться аннотациями Swagger - причём как сами контроллеры в целом, так и их отдельные методы.
- на полях сущностей можно и нужно расставлять констрейнты для проверки формата, длины введённых значений, проверки чисел на положительность и т.д.
- названия сущностей в единственном числе. названия колонок разделены нижним подчеркиванием. Пример: <code>column_name</code>
- пишите Commit message как можно более подробно и с номером задачи! Желательно на английском. Пример: <code>#3 added some functionality</code>
- не должно быть импортов всего пакета <code>import some.packet.*</code>. Выключить такие импорты можно в настройках Идеи
- не жалейте времени на нейминг классов, методов и параметров. Вашим коллегам должно понятно, что делает ваш класс/метод, по одному неймингу без необходимости заглядывать в реализацию
- написанный вамми код должен быть покрыт тестами, если это возможно по условиям задачи
- рекомендую установить плагин SonarLint для Идеи. Он укажет на неочевидные ошибки в коде

Если в процессе разработки появилась идея для новой задачи - пишите ментору про эту идею, создавайте карточку и вперёд. Инициатива приветствуется!

### Созвоны по проекту

Созвоны проходят по вторникам и четвергам оговорённое время.
Регламент:
- длительность до 15 минут
- формат: доклады по 3 пунктам:
    - что сделано с прошлого созвона
    - какие были/есть трудности
    - что будешь делать до следующего созвона
- техлид (ментор) на созвонах код не ревьюит

Такой формат называется stand-up или дэйлик (daily meeting) и является стандартом для всех команд, разрабатывающих софт. 

Любые другие рабочие созвоны команда проводит без ограничений, т.е. в любое время без участия техлида. 
Договаривайтесь сами :) 

### Что делать, если ждете задачу

В порядке приоритетности:
- Изучить используемые на проекте технологии
- Изучить гайд по [докеру](./guides/guide_docker.md)
- Изучить гайд по [куберу](./guides/guide_kubernetes.md)
- Изучить гайд по [микросервисам](./guides/microservices_development.md)
- Изучить остальные гайды из директории guides
- Изучить курс Advanced на Платформе (модули 1, 2, 5, 7, 8, 9, 10)