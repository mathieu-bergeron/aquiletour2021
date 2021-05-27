if [ "$1" = "" ]; then
    echo "usage $0 path/to/message.json"
    exit
fi


curl -v --header "Content-Type: application/json" --data "@$1"  http://localhost:8080/_http/messages
