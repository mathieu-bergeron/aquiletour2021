# Introduction d'une JsonValue

JsonValue jsonValue = Ntro.jsonParser().fromJson(jsonString);

String javaValue = jsonValue.toJava(String.class);

## Désérialiser un modèle

JsonValue jsonValue = Ntro.jsonParser().fromJson(jsonString); // mais gson demande un type, est-ce qu'on peut mettre JsonValue et voir ensuite

QueueModel model = jsonValue.toJava(QueueModel.class);        // et ça fait quelque chose de différent en JSweet et Jdk

// EN FAIT, pour désérialier les références

QueueModel model = jsonValue.toJava(QueueModel.class, Map<String, Object> jsonHeap);

## JsonValue.toJava(...)

Si c'est une liste, on va à l'intérieur

Si c'est un map, on va à l'intérieur


## Désérialisation Json

JsonObject jsonObject = Ntro.jsonParser().fromJson(jsonString);

String fullClassName = jsonObject.typeName();

Class<?> clazz = Ntro.introspector().findClass(fullClassName);

NtroClass ntroClass = Ntro.introspector().ntroClass(clazz)

QueueModel model = ntroClass.newInstance(QueueModel.class, jsonObject);
{
    for(NtroMethod userDefinedSetter :){
        userDefinedSetter.invoke(this, jsonValue1, jsonValue2, ..., jsonValueN);
    }
}

Dans NtroMethod.invoke(Object target, Map<String, Object> jsonHeap, JsonValue... args){

    // il faut désérialiser chaque JsonValue, 
    // possiblement selon le type de l'argument de la méthode

    // En Jdk, on a un type pour chaque argument
    TypeArg javaArg = jsonArg.toJava(TypeArg);

    // En JSweet, on a pas de type
    TypeArg javaArg = jsonArg.toJava(null);

}
