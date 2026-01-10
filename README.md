# rideshare
Prototype rideshare with a Java Backend, an Angular frontend and a MongoDB.

# Setup backend
1. Install Docker Desktop
2. Install VsCode
3. Install VsCode Extensions
   1. Dev Containers
   2. Extension Pack for Java
   3. Debugger Pack for Java
4. In VsCode open the folder *backend* in its own VsCode Instance -> In the bottom right corner a PopUp shows "Open in DevContainer". CLICK IT!
   1. Make sure you just select the /workspace folder and no subfolder of the workspace (workspace folder means backend folder)
5. In order to start the api now, open a terminal in the workspace and type ```mvn spring-boot:run```

=> Your backend is running with a MongoDb on its side

# Setup frontend
1. Make sure you have nodejs and npm installed