import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;



public class AllStacksChapter {

    public static void main(String[] args) {
        System.out.println(" 1. HI  WELCOME");
        stackBasicsDemo();

        System.out.println("\n 2. GENERICS / BOX ");
        boxDemo();

        System.out.println("\n 3. UNDO LAST BANK DEPOSIT ");
        undoDepositDemo();

        System.out.println("\n========== 4. UNDO LAST COURSE ==========");
        undoCourseDemo();

        System.out.println("\n========== 5. REVERSE A STRING ==========");
        reverseStringDemo();

        System.out.println("\n========== 6. ARRAY STACK ==========");
        arrayStackDemo();

        System.out.println("\n========== 7. LINKED STACK ==========");
        linkedStackDemo();

        System.out.println("\n========== 8. LINKED LIST OPERATIONS ==========");
        linkedListOperationsDemo();
    }


    // 1. Built-in Java Stack: push(), pop(), peek(), isEmpty()
    private static void stackBasicsDemo() {
        Stack<Integer> stack = new Stack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Original stack: " + stack);
        System.out.println("Top element: " + stack.peek());
        System.out.println("After peek: " + stack);
        System.out.println("Removed element: " + stack.pop());
        System.out.println("After pop: " + stack);
        System.out.println("Size: " + stack.size());
        System.out.println("Is empty? " + stack.isEmpty());
    }

    // 2. Generics: one Box class can store different object types
    static class Box<T> {
        private T value;

        public void set(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }
    }

    private static void boxDemo() {
        Box<Integer> integerBox = new Box<>();
        integerBox.set(25);
        System.out.println("Integer box: " + integerBox.get());

        Box<String> stringBox = new Box<>();
        stringBox.set("Ali");
        System.out.println("String box: " + stringBox.get());
    }

    // 3. Bank account example: undo the most recent deposit
    static class BankAccount {
        private double balance;
        private final Stack<Double> history = new Stack<>();

        public BankAccount(double balance) {
            this.balance = balance;
        }

        public void deposit(double amount) {
            balance = balance + amount;
            history.push(amount);
            System.out.println("Deposit " + amount
                    + " -> balance " + balance);
        }

        public void undoLastDeposit() {
            if (history.isEmpty()) {
                System.out.println("Nothing to undo");
                return;
            }

            double lastDeposit = history.pop();
            balance = balance - lastDeposit;
            System.out.println("Undo " + lastDeposit
                    + " -> balance " + balance);
        }
    }

    private static void undoDepositDemo() {
        BankAccount account = new BankAccount(100);

        account.deposit(50);
        account.deposit(20);
        account.deposit(30);

        account.undoLastDeposit();
        account.undoLastDeposit();
    }

    // ------------------------------------------------------------
    // 4. Student registration example: undo the last added course
    // ------------------------------------------------------------
    static class Registration {
        private final String studentName;
        private final Stack<String> courses = new Stack<>();

        public Registration(String studentName) {
            this.studentName = studentName;
        }

        public void addCourse(String code) {
            courses.push(code);
            System.out.println(studentName + " added " + code
                    + " -> " + courses);
        }

        public void undoLastCourse() {
            if (courses.isEmpty()) {
                System.out.println("Nothing to undo");
                return;
            }

            String removed = courses.pop();
            System.out.println("Removed " + removed
                    + " -> " + courses);
        }
    }

    private static void undoCourseDemo() {
        Registration registration = new Registration("Hodan");

        registration.addCourse("CA314");
        registration.addCourse("CA301");
        registration.addCourse("MA210");

        registration.undoLastCourse();
    }


    // 5. Reverse a String using a Stack
    private static void reverseStringDemo() {
        String input = "JAVA";
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            stack.push(input.charAt(i));
        }

        StringBuilder reversed = new StringBuilder();

        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

        System.out.println("Input: " + input);
        System.out.println("Output: " + reversed);
    }


    // 6. Custom exception shown in the chapter
    static class EmptyCollectionException extends RuntimeException {
        public EmptyCollectionException(String collection) {
            super("The " + collection + " is empty.");
        }
    }


    // 7. Array-based Stack implementation

    static class ArrayStack<T> {
        private static final int DEFAULT_CAPACITY = 2;
        private int top;
        private T[] stack;

        public ArrayStack() {
            this(DEFAULT_CAPACITY);
        }

        @SuppressWarnings("unchecked")
        public ArrayStack(int initialCapacity) {
            if (initialCapacity <= 0) {
                throw new IllegalArgumentException(
                        "Capacity must be greater than zero.");
            }

            top = 0;
            stack = (T[]) new Object[initialCapacity];
        }

        public void push(T element) {
            if (size() == stack.length) {
                expandCapacity();
            }

            stack[top] = element;
            top++;
        }

        private void expandCapacity() {
            stack = Arrays.copyOf(stack, stack.length * 2);
        }

        public int size() {
            return top;
        }

        public int capacity() {
            return stack.length;
        }

        public boolean isEmpty() {
            return top == 0;
        }

        public T pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            top--;
            T result = stack[top];
            stack[top] = null;
            return result;
        }

        public T peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            return stack[top - 1];
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder("[");
            for (int i = 0; i < top; i++) {
                if (i > 0) {
                    result.append(", ");
                }
                result.append(stack[i]);
            }
            result.append("]");
            return result.toString();
        }
    }

    private static void arrayStackDemo() {
        ArrayStack<String> stack = new ArrayStack<>();

        stack.push("A");
        stack.push("B");
        System.out.println("After A and B: " + stack
                + ", size=" + stack.size()
                + ", capacity=" + stack.capacity());

        stack.push("C");
        System.out.println("After C: " + stack
                + ", size=" + stack.size()
                + ", capacity=" + stack.capacity());

        System.out.println("Peek: " + stack.peek());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Pop: " + stack.pop());

        try {
            System.out.println("Pop: " + stack.pop());
        } catch (EmptyStackException exception) {
            System.out.println("Underflow: the stack is empty.");
        }
    }


    // 8. Node class used by the linked-list implementation
    static class Node<T> {
        private T element;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
            this.next = null;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }
    }


    // 9. Linked-list-based Stack implementation
    static class LinkedStack<T> {
        private int count;
        private Node<T> top;

        public LinkedStack() {
            count = 0;
            top = null;
        }

        public void push(T element) {
            Node<T> newNode = new Node<>(element);
            newNode.setNext(top);
            top = newNode;
            count++;
        }

        public T pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            T result = top.getElement();
            top = top.getNext();
            count--;
            return result;
        }

        public T peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            return top.getElement();
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public int size() {
            return count;
        }
    }

    private static void linkedStackDemo() {
        LinkedStack<String> stack = new LinkedStack<>();

        stack.push("A");
        stack.push("B");
        stack.push("C");

        System.out.println("Size: " + stack.size());
        System.out.println("Top: " + stack.peek());
        System.out.println("Removed: " + stack.pop());
        System.out.println("Top after pop: " + stack.peek());
        System.out.println("Is empty? " + stack.isEmpty());
    }


    // 10. Linked-list traversal, insertion at head, deletion at head

    private static void linkedListOperationsDemo() {
        // Create B -> C
        Node<String> head = new Node<>("B");
        head.setNext(new Node<>("C"));

        // Insert A at the head: A -> B -> C
        Node<String> newNode = new Node<>("A");
        newNode.setNext(head);
        head = newNode;

        System.out.println("After inserting A:");
        printList(head);

        // Delete the first node: B -> C
        head = head.getNext();

        System.out.println("After deleting the first node:");
        printList(head);
    }

    private static void printList(Node<String> head) {
        Node<String> current = head;

        while (current != null) {
            System.out.println(current.getElement());
            current = current.getNext();
        }
    }
}
