# laner

Local Area Network Operations, listen, detect, connect, byte transfer, e.t.c.</p>

---
This is not a replacement for library like Spring, the project is best suited for runtime server creation such as 
a game app that need to spawn a server for a game session and destroy the server when the session has end, or an 
app that share data over a network. 

https://tools.ietf.org/rfc/
___

## Table of content
- [Installation](#installation)
    - [Maven](#maven)
    - [Gradle](#gradle)
- [Examples](#examples)
    - [Start a server](#start-a-server)
    - [Monitor connected LAN devices](#monitor-connected-lan-devices)
    
## Installation

Download the jar file from the [releases](https://github.com/bytesaim/laner/releases) and add the downloaded `laner-$.jar` to your java or android project class path or library folder.

### Maven 

Add the following repository and dependency detail to your pom.xml

Using mvn-repo:

```xml
<dependencies>
    <dependency>
        <groupId>com.bytesaim</groupId>
        <artifactId>laner</artifactId>
        <version>0.2.1</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>laner</id>
        <url>https://raw.github.com/bytesaim/laner/mvn-repo/</url>
    </repository>
</repositories>
```

Using jitpack.io:

```xml
<dependencies>
    <dependency>
        <groupId>com.github.bytesaim</groupId>
        <artifactId>laner</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### Gradle

Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency:

```gradle
dependencies {
        implementation 'com.github.bytesaim:laner:master-SNAPSHOT'
}
```

## Examples

### Start a server

A basic route on the main endpoint and greet_endpoit over GET method

```java
import com.bytesaim.http.*;

public class TestNetworkDevices {
    public static void main(String[] args) throws UnknownHostException {
        Server server = new Server("127.0.0.1",4000);
        EndpointRouter endpointRouter = new EndpointRouter(server);
        endpointRouter.get("/", (request, response) -> {
            try {
                response.write("hello how are your".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        server.run();
    }
}
```

### Monitor connected LAN devices

The following example monitors devices that connects and disconnect to the network 

```java
import com.bytesaim.lan.LanerListener;
import com.bytesaim.lan.LanerNetworkInterface;
import com.bytesaim.lan.NetworkDevices;
import java.net.UnknownHostException;

public class TestNetworkDevices {
    public static void main(String[] args) throws UnknownHostException {
        new NetworkDevices(LanerNetworkInterface.getIPV4Address(), (o) -> {
            if (o instanceof NetworkDevices.NetworkDevice) {
                System.out.println(o);
            }
        }).run();
    }
}
```