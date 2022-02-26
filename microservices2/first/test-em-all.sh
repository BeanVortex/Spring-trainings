# starting command:
#  env  HOST=localhost PORT=8080  ./test-em-all.sh start stop

if [[ $* == *"start"* ]]; then
  echo "Restarting the test environment"
  echo "$ docker-compose down --remove-orphans"
  docker-compose down --remove-orphans
  echo "$ gradle clean build"
  gradle clean build
  echo "$ docker-compose up -d"
  docker-compose up --build -d
fi

function testUrl() {
  url=$*
  if curl "$url" -ks -f -o /dev/null; then
    return 0
  else
    return 1
  fi
}

function waitForService() {
  url=$*
  echo -n "Wait for: $url ..."
  n=0
  until testUrl "$url"; do
    n=$((n + 1))
    if [ $n == 100 ]; then
      echo " Give up"
      exit 1
    else
      sleep 3
      echo -n ", retry #$n"
    fi
  done
  echo "DONE, continues..."
}

waitForService http://"$HOST":"$PORT"/product-composite/5

if [[ $* == *"stop"* ]]; then
  echo "Stopping the environment"
  echo "$ docker-compose down"
  docker-compose down
fi
