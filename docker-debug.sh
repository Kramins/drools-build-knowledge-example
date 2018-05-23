docker build . -t poc-drools

docker run -p 5005:5005 -p 9090:8080 -it --rm poc-drools