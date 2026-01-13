# rideshare
Prototype rideshare with a Java Backend, an Angular frontend and a DerbyDB Database.

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

=> Your backend is running with a DerbyDB integrated

# Setup frontend
1. Make sure you have node.js installed (```node -v```) - See here: https://nodejs.org/en/download
2. Make sure you have the Angular CLI installed (```ng version```) - if you dont have this installed, you can either install by running ```npm install -g @angular/cli``` once you have node.js installed it or use a cached version by using the prefix ```npx``` before every ```ng``` command e.g. ```npx ng serve```
3. Open a terminal in the repository root and enter over to ```cd frontend/share-a-ride-ui```
4. execute ```npm install```
5. to start the frontend enter ```npm start``` or ```npx ng serve``` or if you have the angular-cli installed ```ng serve```

=> Now you can open your browser at http://localhost:4200 and enjoy!

# TODO
- Post a ride
- Rent a car
- Offer your car
- Login & Signup & Logout (should be done before post a ride and offer your car)
  - Includes changing the header, depending on which user is logged in
- User-Profile Settings (should be done before post a ride and offer your car)
  - includes removing all occurences of person_passenger_1 from the code and replacing it with the current users data
- Upcoming rides / my trips
  - includes a passenger-perspective(page) where it shows ride details of upcoming and historical rides
  - includes a driver-perspective(page) where a driver can accept ride-requests from potential passengers
- Reviews (mini-feature)
- Subscribe to newsletter (mini-feature) -> tho technically difficult
- AI recommendations
  - maybe add a whole mini-llm that runs locally into a container in the devcontainers setup. upon login it gets all the users data and writes some current-user recommendations into a cache-table in the database.