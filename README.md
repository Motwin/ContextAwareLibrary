ContextAwareLibrary
===================
This library shows how to perform context collection on client and server side in a Motwin application. Context information can then be processed server side, or delegated to any CEP (Complex Event Processing) tool.

It includes :

- Server library : enable collection from server side application, and collect informations coming from mobile devices. For each type of context element, you need to define the handler that will process the data.
- Mobile library : defines the context elements, and send them to the server, and sends them. 
- An example of context handler is provided with the library. It stores the context elements as metadata in UIS.
