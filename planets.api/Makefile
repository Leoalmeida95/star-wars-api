run: rm down build up

rm:
	sudo docker image prune
down:
	sudo docker-compose down
build:
	sudo mvn clean compile package
up:
	sudo docker-compose up -d --build