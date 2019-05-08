# Woodstock Components

## What are the Woodstock Components?

The Woodstock components are a set of general purpose UI components
that follow Sun Web Application Guidelines (to be supplied), and can be
used for developing web applications and portlets at Sun.

The components are built on the JavaServer(TM) Faces library, a JSR standard
 technology for building web components with Java. The components will work in
 any web container that complies with Servlets 2.5, JSP 2.1, and JSF 1.2.
Currently, only GlassFish (Application Server 9) meets all these requirements.

The Woodstock components are available in the Visual Web Pack plugin module for
 Netbeans 5.5, but can also be used standalone to build web applications. This
 readme describes how to build and use the standalone Woodstock components.

## Related Documentation

 - Javadocs for the components are created when you build the
components. An online version will be available in the future.
 - TLD (tag library descriptor) documentation describes how to use the
 components, is included in the components jar.


## Getting Started

You should first get a good background in JavaServer Faces. Here are a few
 suggestions:

 - Java EE documentation: http://java.sun.com/javaee/javaserverfaces/index.jsp
 - Tutorial: http://java.sun.com/javaee/5/docs/tutorial/doc/index.html

Once you know JSF, you need to decide how you want to build your application.
 There are a few ways to do this, but this document only discusses Netbeans. You
 can also use a text editor if you code manually.You can also use the Visual
 Web Pack for Netbeans.

Netbeans 5.5 is recommended because of its source level debugging, and full
featured editor for building web applications.

## Build

You must build using JDK 8. You also need Maven. We recommend 3.5 or
 newer.

**Full build**
```bash
$ mvn install
```

**Checkstyle**
```bash
$ mvn validate  -Pcheckstyle
```

**Copyright**

```bash
$ mvn validate  -Pcopyright
```