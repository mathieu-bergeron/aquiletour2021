import requests

def send_message(message):
    try:
        res = requests.post('http://localhost:8080/_http/messages/', json=message, timeout=1)
    except (requests.exceptions.Timeout, requests.exceptions.ConnectionError):
        print('aquiletour NOT RESPONDING')
