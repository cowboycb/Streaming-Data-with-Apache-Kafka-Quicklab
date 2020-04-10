## Streaming-Data-with-Apache-Kafka-Quicklab 

Welcome to this Quicklab on getting started with Quarkus! We will walk through the basics of building a server-side Java appliction using the Quarkus framework.

Quarkus is Supersonic, Subatomic Java development - rapid dev that sparks joy! We will see that in action here as we learn about hot-reload and other features of Quarkus that make developing with it fun and efficient.

## Objectives
* Build a basic REST app 
* Use hot reloading to code faster!
* Create a basic RESTful endpoint using RESTeasy annotations

## Prerequisites (optional)
Basic knowledge of Java and command line operation is required. 

## Get the source code and template project

Open a new terminal if it isn't open already:

`Terminal -> New Terminal`

Clone the source code:

`git clone https://gitlab.com/ibm-skills-network/quicklabs/cloud-native-java-with-quarkus`

## Start Quarkus in dev mode and hack some code

Start Quarkus in dev mode:

`mvn quarkus:dev`

Open a second terminal window:

`Terminal -> New Terminal`

Execute this command to hit the "/hello" endpoint in the Quarkus project:

`curl http://localhost:8080/hello`

You should see the message "hello" returned!

Now, open the file:

`src/main/java/org/acme/ExampleResource.java`

Using either the File->Open menu or in the Project browser pane.

You'll see a line where we print out that "hello" message 

`public String hello() {
         return "hello";
     }`

Now change that "hello" to "HELLO FROM ATLANTA"  or where ever you live... run the curl command to hit our endpoint again and see what gets printed out; it should be the new greeting you typed in.

`curl http://localhost:8080/hello`

Notice how you didn't need to restart anything! If you have a look at the terminal where Quarkus is running you'll see a message like this:
`Hot replace total time ...`

## RESTing with Quarkus

Have a look at the source file:

`src/main/java/org/acme/ExampleResource.java`

You'll see that the class has a top-level annotation for its URI specified by @Path:

```java
@Path("/hello")
 public class ExampleResource {
```

Change the path to "/books", as we'll use a simple library/bookstore metaphor to build our sample application:
 `@Path("/books")`

### Parameters

Let's see how we get a URI parameter to use later to retrieve a book by Id. We simply do two things:
1. Annotate a method with: `@Path("/{id}")`
2. Use this to extract into a method parameter `@PathParam("id") Long id`

> Create a new method called "getBook" and use the annotations above to echo back the parameter (we'll use this method later to return a Book object)

```
   @Path("/{id}")
   public Response getOne(@PathParam("id") int id) {
        return id
    }
```

Test it using this:

`curl http://localhost:8080/books/123`

You should see the ID of 123 returned.

## Add the rest of the RESTful endpoints

Let's highlight what else you need to do to make this a complete RESTful endpoint. You can copy and paste the code below into your ExampleResource.java file, or use it as a cheat-sheet to implement these REST verbs:
* CREATE
* UPDATE
* DELETE
* Get all

In the source below, we've created a mock List collection of the Book class/type (already in the repo) so you have some data to play with... copy and paste that so you can focus on the REST part of building out the Quarkus endpoints.

> Notice that we've added these annotations as appropriate:
> @Produces("application/json") 
> @Consumes("application/json")

> Also notice that we're now using the Response object. It has a fluent API for building a proper response in a quick, type-safe manner!

You can exercise these new RESTful endpoints using a curl command line this:

### List all
`curl http://localhost:8080/books`

### Get one by id
`curl http://localhost:8080/books/1`

### Create
`curl -v -X POST -H "Content-type: application/json" -d '{"author":"Pratik Patel", "title":"Testcontainer for Integration Tests", "ISBN":"0-4321-12378"}'  http://localhost:8080/books`

### Update
`curl -v -X PUT -H "Content-type: application/json" -d '{"author":"Pratik Patel", "title":"Testing with Quarkus", "ISBN":"0-4321-12378"}'  http://localhost:8080/books/3`

### Delete
`curl -v -X DELETE -H "Content-type: application/json" http://localhost:8080/books/4`



```java

@Path("/books")
@Produces("application/json")
@Consumes("application/json")
public class ExampleResource {


    private List<Book> books = new ArrayList<Book>() {{
        add(new Book("Quarkus Quick Start", "Pratik Patel", "0-1234-12345")) ;
        add(new Book("Quarkus and Micrometer", "Erin Schnabel", "0-1234-54321")) ;
        add(new Book("Mastering Microprofile with Quarkus", "Sebastian Daschner", "0-1234-34522")) ;
    }};

    @GET
    public List<Book> getAll() {
        return books;
    }

    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") int id) {
        Book entity = books.get(id);
        if (entity == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(entity)
                .build();
    }

    @POST
    public Response create(Book book) {
        books.add(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(Book book, @PathParam("id") int id) {
        books.set(id, book);
        return Response.ok(book).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") int id) {
        Book entity = books.get(id);
        if (entity == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        books.remove(id);
        return Response.noContent().build();
    }
}


```

## Extra Credit!

Have a look at the Book object and how we serialize/deserialize to JSON from the Resource / REST endpoint class. Super cool that we don't have to write any helper objects to do this!

## Summary 

When you're ready, continue your journey with the next Quicklab!
