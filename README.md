# queue-triage

An application to manage dead lettered messages across multiple brokers

## Building [![Build Status](https://travis-ci.org/dwpdigitaltech/queue-triage.svg?branch=master)](https://travis-ci.org/dwpdigitaltech/queue-triage)
* JDK 8
* Mongo 3.2+
* [Gradle](https://gradle.org/).  Please refer to the [Getting Started Guide](https://gradle.org/guides/#getting-started) for installation and usgae of Gradle.

### Setup
To configure the project for IntelliJ run:

```
./gradlew idea
```

To create the relevant Mongo Roles and Users execute the following (this assumes Mongo is running locally on port 27017 and an `admin` user exists with password `Passw0rd`.  These values can be overridden using environment variables, see [mongo-queue-triage-roles.sh](core/dao-mongo/src/main/resources/mongo-queue-triage-roles.sh) or [mongo-queue-triage-users.sh](core/dao-mongo/src/main/resources/mongo-queue-triage-users.sh)):
```bash
buck build //core/dao-mongo:create-users-and-roles
```

### Testing
To test all the modules run the following commands:

```bash
buck test --all --exclude component-test
buck test --all --include component-test
```
#### Testing - Configuration
The web component-test will run against a local firefox.  There are a number of options that can be configuration by adding a `[selenium]` section to a `.buckconfig.local` file.

##### `browser`
Execute the selenium tests against a given browser
```
[selenium]
  browser = firefox|phantomjs
```
##### `remote_url`
Can be used to execute the selenium tests against a remote selenium instance
```
[selenium]
  remote_url = http://localhost:4444/wd/hub
```

### Starting
To run the `queue-triage-core-server` from the command line run:

```bash
./start.sh core
```

To run the `queue-triage-web-server` from the command line run:

```bash
./start.sh ldap
./start.sh web
```

### Troubleshooting
#### External Dependencies
Your organisation may not allow direct access to the Central Maven repository (https://repo1.maven.org/maven2), if this is the case you will need to create a second "local" 
configuration file (filename: `~/.gradle/init.gradle`) in the project's root directory and the following snippet to the `init.gradle` file:  
```
allprojects {
  repositories {
    maven {
	  url "https://repo.internal.com/maven2"
    }
  }
}
```
where <https://repo.internal.com/maven2> is your internal local repo location.

NOTE: This file **should not** be under version-control.

