# Woodstock Components

## What are the Woodstock Components?

The Woodstock components are a set of general purpose UI components
that follow Sun Web Application Guidelines, and were
used for developing web applications and portlets at Sun. As such the Eclipse GlassFish
admin console is build using these components.

The components are built on the Jakarta Faces library, an Eclipse standard
 technology for building web components with Java. The components will work in
 any web container that complies with Jakarta EE 9.


## Related Documentation

 - Javadocs for the components are created when you build the
components. An online version will be available in the future.
 - TLD (tag library descriptor) documentation describes how to use the
 components, is included in the components jar.


## Getting Started

You should first get a good background in Jakarta Faces. Here are a few
 suggestions:
 
 - Tutorial: https://eclipse-ee4j.github.io/jakartaee-tutorial/

Once you know Jakarta Faces, you need to decide how you want to build your application.
 There are a few ways to do this, but this document only discusses Netbeans. You
 can also use a text editor if you code manually.
 

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