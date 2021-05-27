if [ "$1" = "" -o "$2" = "" ]; then
    echo "usage $0 [aquiletour,git_api,git_hook] path/to/message.json"
    exit
fi

if [ "$1" = "aquiletour" ]; then

    curl -v --header "Content-Type: application/json" --data "@$2"  http://localhost:8080/_http/messages

elif [ "$1" = "git_api" ]; then

    curl -v --header "Content-Type: application/json" --data "@$2"  http://localhost:8000/_git_api

elif [ "$1" = "git_hook" ]; then

    curl -v --header "Content-Type: application/json" --data "@$2"  http://localhost:8000/_git_hook

fi


