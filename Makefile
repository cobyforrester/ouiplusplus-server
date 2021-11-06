local:
	mvn clean package && sam local start-api
build:
	mvn clean package && sam build
deploy:
	sam deploy --config-file samconfig-prod.toml
bd:
	make build && make deploy