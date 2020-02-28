-Todo
dockerfile
docker-compose file
jenkins job


commands used in development
kafka-console-producer.sh --broker-list localhost:9092 --topic produced-requests
kafka-server-start.sh config/server.properties
zookeeper-server-start.sh config/zookeeper.properties
sudo kill -9 $(sudo lsof -t -i:27017)
mongod
mongo

kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic produced-requests --create --partitions 3 --replication-factor 1
kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic produced-registers --create --partitions 3 --replication-factor 1