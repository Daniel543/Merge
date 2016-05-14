package RPC;


import helma.xmlrpc.*;


public class RPCServer {

    public static void main(String[] args) {

        try {

            System.out.println("Attempting to start XML-RPC Server...");
            XmlRpc.setDriver("org.apache.xerces.parsers.SAXParser");
            WebServer server = new WebServer(80);

            server.addHandler("parseHandler", new ParseHandler());
            server.addHandler("mergeHandler", new MergeHandler());
            server.start();
            System.out.println("Started successfully.");
            System.out.println("Accepting requests. (Halt program to stop.)");

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }
    }
}
