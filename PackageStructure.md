# Introduction #

The destal-open-world game contains two applications, client and server. Since we are planning to separate these two applications, we created two packages, destal.client and destal.server.
Of course, there are classes both application have in common. For theses files, there is a third package named destal.shared

# Details #

Each of the three mentioned packages contains further sub packages. These are:
[list](list.md)
  * event
  * net
  * util

In addition, there are further package-specific sub packages

## The package "destal.client" ##
Besides the sub packages mentioned above, the client package contains the following additional packages:
[list](list.md)
  * gp (= gameplay)
  * ui (= user interface)


## The package "destal.server" ##
Besides the sub packages mentioned above, the client package contains the following additional packages:
[list](list.md)
  * ui (= user interface)


## The package "destal.shared" ##
Besides the sub packages mentioned above, the client package contains the following additional packages:
[list](list.md)
  * entity
  * world

# Full structure of all classes in destal open world #

![http://destal-open-world.googlecode.com/files/destal_dir-1.png](http://destal-open-world.googlecode.com/files/destal_dir-1.png)