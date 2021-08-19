print_usage_and_exit(){
    echo "usage $0 [http,https] [aquiletour,git_api,git_hook] path/to/message.json"
    exit 1

}

if [ "$1" = "" -o "$2" = "" -o "$3" = "" ]; then
	print_usage_and_exit
fi

if [ "$1" = "http" ]; then

	ssl=""
	protocol="http"
	port="8080"

elif [ "$1" = "https" ]; then

	ssl="--ssl-reqd"
	protocol="https"
	port="443"

else

	print_usage_and_exit

fi


if [ "$2" = "aquiletour" ]; then

	path="_http/messages"

elif [ "$2" = "git_api" ]; then

	protocol="http"
	path="_git_api"
	port="8000"

elif [ "$2" = "git_hook" ]; then

	protocol="http"
	path="_git_hook"
	port="8000"

else

	print_usage_and_exit

fi

payload_path=$3

curl -v --header $ssl "Content-Type: application/json; charset=utf8" --data "@$payload_path"  $protocol://localhost:$port/$path
