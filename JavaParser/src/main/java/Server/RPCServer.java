package Server;

import helma.xmlrpc.*;

/**
 * Created by daniel on 4/17/16.
 */
public class RPCServer {

    public static void main(String[] args) {

        try {
            AstParser aa = new AstParser();
            // aa.testMethod();
            // aa.getAst("/root/vstup.java");
            System.out.println("Attempting to start XML-RPC Server...");
            XmlRpc.setDriver("org.apache.xerces.parsers.SAXParser");
            WebServer server = new WebServer(80);

            server.addHandler("parser", new AstParser());
            server.start();
            System.out.println("Started successfully.");
            System.out.println("Accepting requests. (Halt program to stop.)");

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }
    }
}
