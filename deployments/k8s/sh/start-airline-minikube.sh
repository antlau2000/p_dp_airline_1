#!/bin/sh
minikube start
# останавливаем существующие поды приложения, чтобы впоследствии закешировать актуальный образ
kubectl --namespace default scale deployment airline-project-deployment --replicas 0
kubectl --namespace default scale deployment airline-payments-deployment --replicas 0
# приостанавливаем выполнение скрипта на 10 секунд, чтобы пода успела удалиться
sleep 15
# удаляем закешированный в миникубе образ приложения (airline-project)
# если на этом шаге в логах видите ошибку, попробуйте увеличить время на предыдущем шаге
minikube image rm airline-project
# загружаем актуальный образ приложения в кеш миникуба из локального репозитория (airline-project)
minikube image load airline-project
# удаляем закешированный в миникубе образ приложения (airline-payments)
minikube image rm airline-payments
# загружаем актуальный образ приложения в кеш миникуба из локального репозитория (airline-payments)
minikube image load airline-payments
# запускаем поду приложения с новым образом
kubectl --namespace default scale deployment airline-project-deployment --replicas 1
kubectl --namespace default scale deployment airline-payments-deployment --replicas 1
minikube dashboard