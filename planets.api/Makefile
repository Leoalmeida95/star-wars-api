clean: clean.solution clean.docker.images clean.docker.volumes

run: down build up

clean.solution:
	sudo mvn clean
clean.docker.images:
	sudo docker image prune
clean.docker.volumes:
	sudo docker volume prune
down:
	sudo docker-compose down
build:
	sudo mvn compile package
up:
	sudo docker-compose up -d --build