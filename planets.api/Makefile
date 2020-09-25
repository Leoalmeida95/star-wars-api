clear: clear.images clear.volumes

run: down build up

clear.images:
	sudo docker image prune
clear.volumes:
	sudo docker volume prune
down:
	sudo docker-compose down
build:
	sudo mvn clean compile package
up:
	sudo docker-compose up -d --build