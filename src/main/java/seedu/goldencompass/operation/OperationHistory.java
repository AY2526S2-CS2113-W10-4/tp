package seedu.goldencompass.operation;

import java.util.ArrayDeque;
import java.util.Deque;

public class OperationHistory {
    private static final int CAPACITY = 10;


    private final Deque<OperationSnapshot> undoStack;
    private final Deque<OperationSnapshot> redoStack;

    public OperationHistory() {
        undoStack = new ArrayDeque<>();
        redoStack = new ArrayDeque<>();
    }

    public void saveSnapshot(OperationSnapshot snapshot) {
        if(undoStack.size() == CAPACITY) {
            undoStack.removeLast();
        }
        undoStack.addFirst(snapshot);
    }

    public OperationSnapshot getUndo() {

        //pop the current version from undo, and push into redo
        OperationSnapshot current = undoStack.pollFirst();
        if(current == null) {
            //there is no undo history
            return null;
        }
        redoStack.addFirst(current);

        //peek the second version, which is one version behind the current version
        //null if there is no such history
        return undoStack.peekFirst();
    }

    public OperationSnapshot getRedo() {
        //pop from redo
        OperationSnapshot current = redoStack.pollFirst();
        if(current == null) {
            //there is no redo history
            return null;
        }

        //push into undo
        saveSnapshot(current);

        return current;
    }
}
