import requests

def send_message(message):
    try:
        res = requests.post('http://localhost:8088/_messages/http/', json=message, timeout=1)
    except (requests.exceptions.Timeout, requests.exceptions.ConnectionError):
        print('aquiletour NOT RESPONDING')
