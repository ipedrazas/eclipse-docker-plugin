# eclipse-docker-plugin
Eclipse Pluging for Docker

This plugin displays all the running conatiners.

![Eclipse Docker Screenshot](https://cloud.githubusercontent.com/assets/32796/6859341/ac6d6962-d40f-11e4-9e35-ef8ef2fe0527.png)

There are two actions that can be executed via popup menu:

* Stop Container
* Refresh

When a container is selected, the properties page is populated. Properties are populated based on the `Docker inspect` command.
