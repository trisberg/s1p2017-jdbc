# JdbcWriter

Spring Cloud Function example for [riff](https://github.com/projectriff/riff) FaaS.

This demo runs using riff v0.0.1 on Minikube.

Start by installing [Minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)

Then start a Minikube instance:

```
minikube start --memory=4096
```

Next install [Helm](https://github.com/kubernetes/helm/blob/master/README.md#install)

Then install the tiller server component:

```
helm init
```

And wait for it to get started `kubectl get pod -l app=helm -n kube-system -w`

Install the riff FaaS and a MySQL instance:

```
./riff/install-riff
./riff/install-mysql
```

Now we can build the app, we use the Minikube docker environment to avoid pushing our Docker image to DockerHub.

```
eval $(minikube docker-env)
./mvnw clean package dockerfile:build
```

Create the Function resource:

```
kubectl apply -f ./riff/jdbc-writer.yaml 
```

Post some test data:

```
./riff/request data '{"name": "Thomas", "description": "hello"}'
```

You should see some output like this:

```
{ "newId": 1 }
```

To view the data in the database use the folowing:

```
./riff/mysql 
mysql> select * from data;
mysql> exit
```

To tear it all down use:

```
kubectl delete -f ./riff/jdbc-writer.yaml 
./riff/uninstall-riff
./riff/uninstall-mysql
```
