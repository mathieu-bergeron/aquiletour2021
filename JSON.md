# Attention à l'ordre

À cause des cycles, il faut sérialiser les clés d'un map dans l'ordre alphabétique

Donc:

* trier les clé d'un map
* trier les getter selon leur nom (ou mieux appeler toJsonValueFromMap)

## JsonSerializable

Extraire un Map puis appeler toJsonValueFromMap (qui va trier les clés)

