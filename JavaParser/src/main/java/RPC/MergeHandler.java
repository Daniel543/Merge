package RPC;


import java.util.Vector;


public class MergeHandler {
    public MergeHandler() {

    }

    public Vector<String> moveMethod(Vector args) {

        return Merger.CodeMerger.moveMethod(args);
    }

}
